package com.division_zone.divisionvendors;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class GearModsFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rv;
    private List<Mods> modsList = new ArrayList<>();
    private ModsAdapter modsAdapter;

    public GearModsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gearmods_recycler, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_recycler_container_mods);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                new retrieveGearModsJSON().execute();
            }
        });

        // Configure the refreshing colors
        swipeLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        rv = (RecyclerView) view.findViewById(R.id.recyclerviewMods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        new retrieveGearModsJSON().execute();

        return view;
    }

    private class retrieveGearModsJSON extends AsyncTask<String, Integer, Toast> {

        protected void onPreExecute() {
            super.onPreExecute();
            modsList.clear();
        }

        protected Toast doInBackground(String... vars) {

            OkHttpClient httpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://rubenalamina.mx/division/gear-mods.json")
                    .build();

            try {
                Response response = httpClient.newCall(request).execute();

                final int code = response.code();

                if (code == 200) {
                    try {

                        JSONArray mods = new JSONArray(response.body().string());

                        for (int i = 0; i < mods.length(); i++) {
                            JSONObject mod = mods.getJSONObject(i);

                            modsList.add(new Mods(mod.getString("type"),mod.getString("vendor"), mod.getString("name"), mod.getString("stat"), mod.getString("price"), mod.getString("attribute")));
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Toast result) {
            swipeLayout.setRefreshing(false);

            modsAdapter = new ModsAdapter(modsList);
            rv.setAdapter(modsAdapter);
            modsAdapter.notifyDataSetChanged();
        }
    }
}
