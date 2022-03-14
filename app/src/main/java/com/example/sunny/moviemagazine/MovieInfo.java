package com.example.sunny.moviemagazine;

import android.annotation.TargetApi;
import android.content.Intent;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieInfo extends AppCompatActivity {
    private ImageView imageView;
    private TextView title, relese, rating, description;
    private CollapsingToolbarLayout ctbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        imageView = findViewById(R.id.id_Moviiiimg);
        title = findViewById(R.id.id_MOVIETITLE);
        relese = findViewById(R.id.id_MOVIEREALEASEDATE);
        rating = findViewById(R.id.id_MOVIERATING);
        description = findViewById(R.id.id_DESCRIPTION);
        ctbl = findViewById(R.id.id_collapsingtoolbar);
        Intent i = getIntent();
        String s1 = "http://image.tmdb.org/t/p/w500" + i.getStringExtra("imageinfo");
        Picasso.with(getApplicationContext()).load(s1).fit().centerInside().into(imageView);
        title.setText(i.getStringExtra("titleinfo"));
        relese.setText("Release Date :  " + i.getStringExtra("releaseinfo"));
        rating.setText("Rating       :  " + i.getStringExtra("rateinfo"));
        description.setText(i.getStringExtra("decinfo"));
        String s = i.getStringExtra("titleinfo");
        ctbl.setTitle(s);
    }
}
