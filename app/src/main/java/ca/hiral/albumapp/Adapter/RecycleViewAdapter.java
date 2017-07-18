package ca.hiral.albumapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ca.hiral.albumapp.Model.Datamodel;
import ca.hiral.albumapp.R;

/**
 * Created by Admin on 16-07-2017.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {


    private List<Datamodel> moviesList;
Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView imagename;

        public MyViewHolder(View view) {
            super(view);
            LinearLayout ll = (LinearLayout)view.findViewById(R.id.linear);

//            ImageView i1 = new ImageView(view.getContext());
//            i1.setId(R.id.imagview);
//            i1.setLayoutParams(new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.MATCH_PARENT));
//            ll.addView(i1);


            TextView tv = new TextView(view.getContext());
            // tv.setText("Dynamic TextView" + i);
            tv.setId(R.id.textViewName);
            tv.setLayoutParams(
                    new RelativeLayout.LayoutParams(
                            RecyclerView.LayoutParams.WRAP_CONTENT,
                            RecyclerView.LayoutParams.MATCH_PARENT));
            //   tv.setla(5,10,20,5);
            tv.setTextColor(Color.BLACK);

            ll.addView(tv);


            TextView tv1 = new TextView(view.getContext());
            tv1.setLayoutParams(
                    new RelativeLayout.LayoutParams(
                            RecyclerView.LayoutParams.WRAP_CONTENT,
                            RecyclerView.LayoutParams.MATCH_PARENT));
            tv.setPadding(30,0,0,0);
            tv1.setId(R.id.textViewEmail);
            ll.addView(tv1);


            title = (TextView) view.findViewById(R.id.textViewName);
           // genre = (TextView) view.findViewById(R.id.textViewEmail);
            imagename =(ImageView)view.findViewById(R.id.imageactor);

        }
    }
    public RecycleViewAdapter(List<Datamodel> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catlayout, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Datamodel movie = moviesList.get(position);
        holder.title.setText(movie.getDirector());
//
//        URL newurl = null;
//        try {
//            newurl = new URL(movie.getPoster());
//        Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
//            holder.imagename.setImageBitmap(mIcon_val);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



String url =movie.getPoster();
        Glide.with(context).load(url)
                .thumbnail(0.9f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher)
                .into(holder.imagename);


//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeFile(String.valueOf(new URL(movie.getPoster()).openStream()));
//            holder.imagename.setImageBitmap(bmp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //   holder.imagename.setImageURI(Uri.parse(movie.getPoster()));
   //     holder.genre.setText(movie.getCity());
//        Log.d("imagebfd", String.valueOf(Uri.parse(movie.getPoster())));
        //Picasso.with(context).load(movie.getPoster()).into(holder.imagename);
       ////Picasso.with(context).load(movie.getPoster()).placeholder(R.mipmap.ic_launcher).into(holder.imagename);
//      //  Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);


    }




    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}

