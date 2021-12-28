package com.mindsplash.afterlogin.common.learn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterConcepts;

import java.util.ArrayList;
import java.util.List;

public class FragmentSubject extends Fragment {
    private static FragmentSubject fragmentSubject;
    private static List<String> subkectsList;
    private RecyclerView rclQuations;
    private AdapterConcepts adapter;

    public static Fragment getInstance(List<String> subkects) {
        subkectsList = subkects;
        if (fragmentSubject==null)
            fragmentSubject = new FragmentSubject();
        return fragmentSubject;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects,container,false);
        rclQuations = view.findViewById(R.id.rcl_subjects);
        setConcepts();
        return view;
    }

    private void setConcepts() {
        adapter = new AdapterConcepts(getActivity(), (ArrayList<String>) subkectsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclQuations.setLayoutManager(layoutManager);
        rclQuations.setHasFixedSize(true);
        rclQuations.setAdapter(adapter);
    }
}
