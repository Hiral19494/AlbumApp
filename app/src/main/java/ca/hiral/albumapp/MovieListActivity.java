package ca.hiral.albumapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.hiral.albumapp.Adapter.MovieViewAdapter;
import ca.hiral.albumapp.Adapter.RecycleViewAdapter;
import ca.hiral.albumapp.Handler.RestApi;
import ca.hiral.albumapp.Model.Datamodel;
import ca.hiral.albumapp.Model.Moviemodel;

/**
 * Created by Admin on 17-07-2017.
 */
public class MovieListActivity extends AppCompatActivity{

        private List<Moviemodel> movieList = new ArrayList<>();
        private String TAG = MainActivity.class.getSimpleName();
        private RecyclerView recyclerView;
        private MovieViewAdapter mAdapter;
        private static String url = "https://netflixroulette.net/api/api.php?director=";
    String url1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);

            RelativeLayout layout1 = new RelativeLayout(this);
            layout1.setId(R.id.movielist_main);
            setContentView(layout1);


            RelativeLayout layout = (RelativeLayout)findViewById(R.id.movielist_main);
            RecyclerView recycle = new RecyclerView(getApplicationContext());
            recycle.setId(R.id.recycler_view);
            layout.addView(recycle);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mAdapter = new MovieViewAdapter(movieList,getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            new GetMovielist().execute();

            Bundle extras = getIntent().getExtras();
            url1 = extras.getString("name");

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // TODO Handle item click

                            Intent i = new Intent(getApplicationContext(), DisplayActivity.class);

                            //  i.putExtra("id", movieList.get(position).getId());
                            i.putExtra("name", movieList.get(position).getDirector());
                             i.putExtra("show", movieList.get(position).getShow_title());
                            i.putExtra("poster", movieList.get(position).getPoster());
                            i.putExtra("release",movieList.get(position).getRelease_year());
                            i.putExtra("summary", movieList.get(position).getSummary());
                            //i.putExtra("signature", ListArray.get(position).getSignature());
                            //   i.putExtra("city", movieList.get(position).getCity());
                            //i.putExtra("latitude", ListArray.get(position).getLatitude());
                            //i.putExtra("longitude", ListArray.get(position).getLongitude());

                            startActivity(i);


                        }
                    })
            );


        }

private class GetMovielist extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
//            pDialog = new ProgressDialog(JavaActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        RestApi sh = new RestApi();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url+url1);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                // JSONObject jsonObj = new JSONObject(jsonStr);
                Log.d("hiral","hiral");
                // Getting JSON Array node
                JSONArray contacts = new JSONArray(jsonStr);


                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {



                    Moviemodel moviemodel = new Moviemodel();
                    Log.d("hiral1","hiral1");
                    JSONObject c = contacts.getJSONObject(i);

                      String dname = c.getString("director");
                    String name = c.getString("show_title");

                    Log.d("value",name);
                     String release = c.getString("release_year");
                    String image = c.getString("poster");
                    String summary = c.getString("summary");
                    Log.d("image",image);
                    // String gender = c.getString("gender");



                    //  datamodel.setId(id);
                    moviemodel.setShow_title(name);
                    moviemodel.setDirector(dname);
                    moviemodel.setRelease_year(release);
                    moviemodel.setPoster(image);
                    moviemodel.setSummary(summary);
                    movieList.add(moviemodel);
                    Log.d("data", String.valueOf(movieList));


                    // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");

                    // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        contact.put("id", id);
//                        contact.put("name", name);
//                        contact.put("email", email);
////                        contact.put("mobile", mobile);
//
//                        // adding contact to contact list
//                        contactList.add(contact);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        if(movieList.size() > 0) {
            mAdapter.notifyDataSetChanged();
            Log.d("hi","hi");
        } else {
            Toast.makeText(getBaseContext(),"No data Found",Toast.LENGTH_LONG).show();
            //Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
        }
    }

}
}


