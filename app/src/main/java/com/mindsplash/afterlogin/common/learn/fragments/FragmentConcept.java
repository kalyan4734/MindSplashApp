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
import com.mindsplash.afterlogin.common.learn.adapters.AdapterChapter;
import com.mindsplash.afterlogin.common.learn.adapters.AdapterConcepts;

import java.util.ArrayList;
import java.util.List;

public class FragmentConcept extends Fragment {
    private static FragmentConcept fragmentConcept;
    private static List<String> conceptsList;
    private AdapterConcepts adapter;
    private RecyclerView rclConcepts;

    public static Fragment getInstance(List<String> concepts) {
        conceptsList = concepts;
        if (fragmentConcept == null)
            fragmentConcept = new FragmentConcept();
        return fragmentConcept;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concepts,container,false);
        rclConcepts = view.findViewById(R.id.rcl_concepts);
        setConcepts();
        return view;
    }

    private void setConcepts() {
        adapter = new AdapterConcepts(getActivity(), (ArrayList<String>) conceptsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rclConcepts.setLayoutManager(layoutManager);
        rclConcepts.setHasFixedSize(true);
        rclConcepts.setAdapter(adapter);
    }
}
