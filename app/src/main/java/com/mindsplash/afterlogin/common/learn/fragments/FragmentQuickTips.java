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
import com.mindsplash.afterlogin.common.learn.adapters.AdapterDefinition;
import com.mindsplash.helper.CallBack;
import com.mindsplash.network.model.Concept;
import com.mindsplash.network.model.ConceptResponse;
import com.mindsplash.services.subject.concepimpl.ConceptService;
import com.mindsplash.services.subject.concepimpl.ConceptServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class FragmentQuickTips extends Fragment {

    private static FragmentQuickTips fragmentQuickTips;
    private RecyclerView rclDefinitions;
    private static String subjId, chapId;
    private AdapterDefinition adapterDefinition;
    private static List<String> quick_tips;

    public static Fragment getInstance(List<String> quick_tipsList) {
        quick_tips = quick_tipsList;
        if (fragmentQuickTips == null)
            fragmentQuickTips = new FragmentQuickTips();
        return fragmentQuickTips;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);
        rclDefinitions = view.findViewById(R.id.rcl_definition);
        setDefConcepts(quick_tips);
        return view;
    }

    private void setDefConcepts(List<String> data) {
        adapterDefinition = new AdapterDefinition(getActivity(), data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rclDefinitions.setLayoutManager(layoutManager);
        rclDefinitions.setHasFixedSize(true);
        rclDefinitions.setAdapter(adapterDefinition);
    }

}
