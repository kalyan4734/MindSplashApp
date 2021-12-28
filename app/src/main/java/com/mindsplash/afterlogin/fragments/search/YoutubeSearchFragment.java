package com.mindsplash.afterlogin.fragments.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterGoogle;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterYoutube;
import com.mindsplash.afterlogin.fragments.search.adapter.OnClickVideo;
import com.mindsplash.afterlogin.fragments.search.beans.GoogleResult;
import com.mindsplash.afterlogin.fragments.search.beans.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class YoutubeSearchFragment extends Fragment implements OnClickVideo {

    private static final String ARG_PARAM1 = "param1";
    private String searchTerm;
    private AdapterYoutube adapterGoogle;
    private GoogleResult googleResult;
    static String result = null;
    private RecyclerView rcList = null;
    Integer responseCode = null;
    String responseMessage = "";

    public YoutubeSearchFragment() {
        // Required empty public constructor
    }


    public static YoutubeSearchFragment newInstance(String param1) {
        YoutubeSearchFragment fragment = new YoutubeSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchTerm = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_google_search, container, false);
        rcList = v.findViewById(R.id.rv_list);
        String searchStringNoSpaces = searchTerm.replace(" ", "+");
        String key="AIzaSyAWe8kMdRe46mdgt34E2bJMnTsy5Mk6jeQ";
//        String cx = "7f03f7a75a852fb40";
      String cx = "87e319d5a706116e6";


        String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e("error", "ERROR converting String to URL " + e.toString());
        }
        Log.d("url", "Url = "+  urlString);


        // start AsyncTask
        YoutubeSearchFragment.GoogleSearchAsyncTask searchTask = new YoutubeSearchFragment.GoogleSearchAsyncTask();
        if(searchTerm!=null && searchTerm.length()>0 && !searchTerm.equals(" ")) {
            searchTask.execute(url);
        }

        return v;
    }

    @Override
    public void onClick(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @SuppressLint("StaticFieldLeak")
    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e("TAG", "Http connection ERROR " + e.toString());
            }


            try {
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e("TAG", "Http getting response code ERROR " + e.toString());

            }

            Log.d("TAG", "Http response code =" + responseCode + " message=" + responseMessage);

            try {

                if(responseCode != null && responseCode == 200) {
                    // response OK
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    rd.close();

                    conn.disconnect();

                    result = sb.toString();

                    Log.d("TAG", "result=" + result);

                    return result;

                }else{

                    // response problem

                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e("TAG", errorMsg);
                    result = errorMsg;
                    return  result;

                }
            } catch (IOException e) {
                Log.e("TAG", "Http Response ERROR " + e.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d("TAG", "AsyncTask - onProgressUpdate, progress=" + progress);
        }


        protected void onPostExecute(String result) {
            if(result!=null) {
                googleResult = new Gson().fromJson(result, GoogleResult.class);
                Log.d("item", googleResult.items.toString());
                setChapters((ArrayList<Item>) googleResult.items);
            }
        }
    }

    private void setChapters(ArrayList<Item> data) {
        adapterGoogle = new AdapterYoutube(data,requireContext(),this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        rcList.setLayoutManager(layoutManager);
        rcList.setHasFixedSize(true);
        rcList.setAdapter(adapterGoogle);
    }

}