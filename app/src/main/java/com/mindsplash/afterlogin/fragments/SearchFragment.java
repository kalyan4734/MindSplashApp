package com.mindsplash.afterlogin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.HomeActivity;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.fragments.home.Home;
import com.mindsplash.afterlogin.fragments.search.adapter.PagerAdapter;

public class SearchFragment extends Fragment implements HomeActivity.OnBackPressedListener {
    ImageView backButton;
    TextView header_title;
    EditText search;
    ImageView profile;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private ImageView mLogo;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        backButton = view.findViewById(R.id.backButton);
        header_title = (TextView) view.findViewById(R.id.header_title);
        search = (EditText) view.findViewById(R.id.search_brand);
        profile = (ImageView)view.findViewById(R.id.profile);
        header_title.setText("SEARCH");
        mLogo = view.findViewById(R.id.logo);
        mLogo.setVisibility(View.GONE);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("MindSplash"));
        tabLayout.addTab(tabLayout.newTab().setText("Google"));
        tabLayout.addTab(tabLayout.newTab().setText("Youtube"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        profile.setOnClickListener(vieww ->{
            startActivity(new Intent(requireContext(), MyAccount.class));
        });
         viewPager = (ViewPager) view.findViewById(R.id.tab_viewpager);
             adapter = new PagerAdapter(getParentFragmentManager(), tabLayout.getTabCount(),search.getText().toString());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
                doBack();
            }
        });


        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    adapter = new PagerAdapter(getParentFragmentManager(), tabLayout.getTabCount(),search.getText().toString());
                    viewPager.setAdapter(adapter);

                    return true;
                }
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//              try {
//                  adapter = new PagerAdapter(getParentFragmentManager(), tabLayout.getTabCount(),search.getText().toString());
//                  viewPager.setAdapter(adapter);
//              }catch (Exception e){
//e.printStackTrace();
//              }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                closeKeyboard();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void closeKeyboard()
    {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    requireContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
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

}