package com.mindsplash.afterlogin.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.fragments.home.Home;


public class Learn extends Fragment implements HomeActivity.OnBackPressedListener {
    ImageView backButton;
    TextView header_title;
    private ImageView mLogo;
    public Learn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        backButton = view.findViewById(R.id.backButton);
        header_title = (TextView) view.findViewById(R.id.header_title);

        header_title.setText("LEARN");
        mLogo = view.findViewById(R.id.logo);
        mLogo.setVisibility(View.GONE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                doBack();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void doBack() {
        if (getActivity()!=null) {
            Home home = new Home();
            ((HomeActivity) getActivity()).backFragment(home, "Home");
        }
    }
}