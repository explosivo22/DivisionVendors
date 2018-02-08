package com.division_zone.divisionvendors;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Brad on 2/5/2018.
 */

public class GearFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rv;
    private List<Gear> gearList = new ArrayList<>();
    private GearAdapter gearAdapter;
    //private ListView gearListView;

    public GearFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gear_recycler, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_recycler_container);
        //gearListView = (ListView) view.findViewById(R.id.lvItems);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                new retrieveGearJSON().execute();
            }
        });

        // Configure the refreshing colors
        swipeLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        new retrieveGearJSON().execute();

        return view;
    }

    private class retrieveGearJSON extends AsyncTask<String, Integer, Toast> {

        protected void onPreExecute() {
            super.onPreExecute();
            gearList.clear();
        }

        protected Toast doInBackground(String... vars){

            //use OkHttpClient to do a get request
            OkHttpClient httpClient = new OkHttpClient();

            //build the request
            Request request = new Request.Builder()
                    .url("http://rubenalamina.mx/division/gear.json")
                    .build();

            try{
                //make the OkHttpClient Call
                Response response = httpClient.newCall(request).execute();

                //get the return code from the call
                final int code = response.code();

                //code of 200 = OK
                if(code == 200){
                    try{
                        //get the response body string which is JSON
                        JSONArray test = new JSONArray(response.body().string());

                        for(int i=0; i<test.length(); i++){

                            JSONObject c = test.getJSONObject(i);
                            //filter through the data to just test the list
                            gearList.add(new Gear(c.getString("rarity"),c.getString("vendor"),c.getString("score"),c.getString("name"),c.getString("armor"),c.getString("price"),c.getString("fire"),c.getString("stam"),c.getString("elec"),c.getString("major"),c.getString("minor")));
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Toast result){

            swipeLayout.setRefreshing(false);

            gearAdapter = new GearAdapter(gearList);
            rv.setAdapter(gearAdapter);
            gearAdapter.notifyDataSetChanged();

        }
    }
}
