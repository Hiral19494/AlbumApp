package ca.hiral.albumapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 17-07-2017.
 */
public class DisplayActivity extends AppCompatActivity {


    TextView Name, Des,dob,co,summary;
    int height = 50;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);



        Name = (TextView) findViewById(R.id.name);
       Des = (TextView) findViewById(R.id.show);
        img = (ImageView) findViewById(R.id.imageView);
        dob = (TextView)findViewById(R.id.Release);
        summary= (TextView)findViewById(R.id.summary);


        Bundle extras = getIntent().getExtras();

        Name.setText(extras.getString("name"));
        dob.setText(extras.getString("release"));
        summary.setText(extras.getString("summary"));
        Des.setText(extras.getString("show"));
        Log.d("bdh",extras.getString("poster"));
        Glide.with(this).load(extras.getString("poster"))
                .thumbnail(0.9f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
       // Picasso.with(this).load(extras.getString("poster")).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(img);
    }
}




