package ca.hiral.albumapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.hiral.albumapp.Adapter.RecycleViewAdapter;
import ca.hiral.albumapp.Handler.RestApi;
import ca.hiral.albumapp.Model.Datamodel;

public class MainActivity extends AppCompatActivity {

    private List<Datamodel> movieList = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecycleViewAdapter mAdapter;
    private static String url = "https://netflixroulette.net/api/api.php?actor=Nicolas%20Cage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        RelativeLayout layout1 = new RelativeLayout(this);
        layout1.setId(R.id.activity_main);
        setContentView(layout1);


        RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_main);
        RecyclerView recycle = new RecyclerView(getApplicationContext());
        recycle.setId(R.id.recycler_view);
        layout.addView(recycle);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RecycleViewAdapter(movieList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        new GetContacts().execute();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent i = new Intent(getApplicationContext(), MovieListActivity.class);

                      //  i.putExtra("id", movieList.get(position).getId());
                        i.putExtra("name", movieList.get(position).getDirector());
                        //  i.putExtra("gender", LismtArray.get(position).getGender());
                        //i.putExtra("date", ListArray.get(position).getBirthday());
                        //i.putExtra("height", ListArray.get(position).getHeight());
                        //i.putExtra("signature", ListArray.get(position).getSignature());
                     //   i.putExtra("city", movieList.get(position).getCity());
                        //i.putExtra("latitude", ListArray.get(position).getLatitude());
                        //i.putExtra("longitude", ListArray.get(position).getLongitude());

                        startActivity(i);


                    }
                })
        );

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

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
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("hiral","hiral");
                    // Getting JSON Array node
                    JSONArray contacts = new JSONArray(jsonStr);


                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {



                        Datamodel datamodel = new Datamodel();
                        Log.d("hiral1","hiral1");
                        JSONObject c = contacts.getJSONObject(i);

                      //  String id = c.getString("id");
                        String name = c.getString("director");

                        Log.d("value",name);
                       // String email = c.getString("city");
                         String image = c.getString("poster");
                        Log.d("image",image);
                        // String gender = c.getString("gender");



                      //  datamodel.setId(id);
                        datamodel.setDirector(name);
                      //  datamodel.setCity(email);
                        datamodel.setPoster(image);
                        movieList.add(datamodel);
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


