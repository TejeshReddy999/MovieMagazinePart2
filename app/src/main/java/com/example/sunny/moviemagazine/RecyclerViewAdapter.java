package com.example.sunny.moviemagazine;

/**
 * Created by sunny on 10-05-2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context movieContext;
    ArrayList<Movie> movieData;

    public RecyclerViewAdapter(Response.Listener<JSONObject> mcontext, ArrayList<Movie> mdata) {
        this.movieContext = (Context) mcontext;
        this.movieData = mdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(movieContext);
        view = mInflater.inflate(R.layout.card_view_frame, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movi = movieData.get(position);
        final String imageurl = (movi.getTumbnail());
        final String imageurl2 = (movi.getBackDrop());
        final String title = (movi.getTitle());
        final String rating = (movi.getRating());
        final String releaseing = (movi.getRelease());
        final String decriptipon = (movi.getDescription());
        Picasso.with(movieContext).load("http://image.tmdb.org/t/p/w500" + imageurl + "?api_key=24978f2b10f3bb01cbf78c013b33ca26").fit().centerInside().into(holder.imgbookThumb);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(movieContext, MovieInfo.class);
                i.putExtra("imageinfo", imageurl2);
                i.putExtra("titleinfo", title);
                i.putExtra("rateinfo", rating);
                i.putExtra("releaseinfo", releaseing);
                i.putExtra("decinfo", decriptipon);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgbookThumb;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgbookThumb = itemView.findViewById(R.id.id_MovieImage);
            cardView = itemView.findViewById(R.id.id_cardview);
        }
    }
}