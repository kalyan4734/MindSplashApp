package com.mindsplash.afterlogin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.learn.CreditPackaeActivity;
import com.mindsplash.afterlogin.fragments.home.Home;
import com.mindsplash.afterlogin.fragments.home.adapters.AdapterDownload;
import com.mindsplash.afterlogin.fragments.search.adapter.AdapterGoogle;
import com.mindsplash.afterlogin.fragments.search.beans.Item;

import java.util.ArrayList;

public class Download extends Fragment implements HomeActivity.OnBackPressedListener {
    ImageView backButton;
    TextView header_title;
    private ImageView mLogo;

    private RecyclerView rcList;
    public Download() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_download, container, false);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        backButton = view.findViewById(R.id.backButton);
        rcList = view.findViewById(R.id.rcl_notificatin);
        header_title = (TextView) view.findViewById(R.id.header_title);

        header_title.setText("SEARCH");
        mLogo = view.findViewById(R.id.logo);
        mLogo.setVisibility(View.GONE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                doBack();
            }
        });

        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), MyAccount.class));
            }
        });
        setChapters(new ArrayList());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void doBack() {
        if (getActivity() != null) {
            Home home = new Home();
            ((HomeActivity) getActivity()).backFragment(home, "Home");
        }
    }
    private void setChapters(ArrayList<Item> data) {
        try {
            AdapterDownload   adapterGoogle = new AdapterDownload(data, requireContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
            rcList.setLayoutManager(layoutManager);
            rcList.setHasFixedSize(true);
            rcList.setAdapter(adapterGoogle);
        } catch (Exception e) {

        }
    }
}