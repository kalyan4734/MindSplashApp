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
import com.mindsplash.afterlogin.common.learn.fragments.FragmentFormula;

import java.util.ArrayList;
import java.util.List;

public class FragmentQuation extends Fragment {
    private static FragmentQuation fragmentQuation;
    private static List<String> questionsList;
    private RecyclerView rclQuations;
    private AdapterConcepts adapter;

    public static Fragment getInstance(List<String> questions) {
        questionsList = questions;
        if (fragmentQuation==null)
            fragmentQuation = new FragmentQuation();
        return fragmentQuation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quations,container,false);
        rclQuations = view.findViewById(R.id.rcl_quations);
        setConcepts();
        return view;
    }

    private void setConcepts() {
        adapter = new AdapterConcepts(getActivity(), (ArrayList<String>) questionsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclQuations.setLayoutManager(layoutManager);
        rclQuations.setHasFixedSize(true);
        rclQuations.setAdapter(adapter);
    }
}
