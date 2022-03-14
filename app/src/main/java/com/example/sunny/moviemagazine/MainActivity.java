package com.example.sunny.moviemagazine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    public ArrayList<Movie> isMovie;
    private LinearLayout lsnak;
    private RecyclerView myrv;
    private ProgressDialog pd;
    private RecyclerViewAdapter myrevad;
    private String geted_Data;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lsnak = findViewById(R.id.id_LinerSank);
        myrv = findViewById(R.id.id_recyclerviwe);
        myrv.setHasFixedSize(true);
        pd = new ProgressDialog(MainActivity.this);
        geted_Data = "http://api.themoviedb.org/3/movie/popular?api_key=24978f2b10f3bb01cbf78c013b33ca26";
        retry();
    }

    private void retry() {
        if (!Connection(MainActivity.this)) {
            pd.setMessage("Connecting....");
            pd.show();
            displaydialog(MainActivity.this);
        } else {
            pd.dismiss();
            isMovie = new ArrayList<Movie>();
            requestQueue = Volley.newRequestQueue(this);
            pd.setMessage("Loading....");
            pd.show();
            pjson(geted_Data);
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager gm = new GridLayoutManager(this, 2);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                myrv.setLayoutManager(gm);
            } else {
                GridLayoutManager gm = new GridLayoutManager(this, 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                myrv.setLayoutManager(gm);
            }
        }
    }

    private void snakBar() {
        Snackbar snackbar = Snackbar.make(lsnak, "Swipe Left to Scroll", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private AlertDialog.Builder displaydialog(MainActivity mainActivity) {
        Snackbar snackbar = Snackbar.make(lsnak, " No Network Connection", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Loading....");
                pd.show();
                Intent i;
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
        return null;
    }

    public boolean Connection(MainActivity mainActivity) {
        ConnectivityManager ce = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo ni = ce.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            android.net.NetworkInfo wcont = ce.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mcont = ce.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mcont != null && ni.isConnectedOrConnecting()) || (wcont != null && ni.isConnectedOrConnecting())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_popular) {
            geted_Data = "http://api.themoviedb.org/3/movie/popular?api_key=24978f2b10f3bb01cbf78c013b33ca26";
            isMovie = new ArrayList<Movie>();
            requestQueue = Volley.newRequestQueue(this);
            retry();
        }
        if (item.getItemId() == R.id.id_top) {
            geted_Data = "http://api.themoviedb.org/3/movie/top_rated?api_key=24978f2b10f3bb01cbf78c013b33ca26";
            isMovie = new ArrayList<Movie>();
            requestQueue = Volley.newRequestQueue(this);
            retry();
        }
        return super.onOptionsItemSelected(item);
    }

    private void pjson(String geted_data) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, geted_data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String imagurl = results.getString("poster_path");
                        String imagurl2 = results.getString("backdrop_path");
                        String title_path = results.getString("original_title");
                        String rate = results.getString("vote_average");
                        String relese_date = results.getString("release_date");
                        String descrip = results.getString("overview");
                        isMovie.add(new Movie(imagurl, imagurl2, title_path, rate, relese_date, descrip));
                    }
                    myrevad = new RecyclerViewAdapter(MainActivity.this, isMovie);
                    myrv.setAdapter(myrevad);
                    snakBar();
                    pd.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
