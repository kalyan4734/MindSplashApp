package com.mindsplash.beforelogin.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.mindsplash.R;
import com.mindsplash.beforelogin.RegisterActivity;

public class DialogClassSelection extends DialogFragment {
    private static Context context;
    private ConstraintLayout cl3,cl4,cl5,cl6,cl7,cl8,cl9,cl10;
    private static String classSelected;
    public static DialogClassSelection newInstance(Context contet) {
        context = contet;
        DialogClassSelection dialogFragment = new DialogClassSelection();
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_selection_dialog, container, false);
        cl3 = view.findViewById(R.id.cl_3);
        cl4 = view.findViewById(R.id.cl_4);
        cl5 = view.findViewById(R.id.cl_5);
        cl6 = view.findViewById(R.id.cl_6);
        cl7 = view.findViewById(R.id.cl_7);
        cl8 = view.findViewById(R.id.cl_8);
        cl9 = view.findViewById(R.id.cl_9);
        cl10 = view.findViewById(R.id.cl_10);
        onClick3();
        onClick4();
        onClick5();
        onClick6();
        onClick7();
        onClick8();
        onClick9();
        onClick10();
        return view;
    }

    private void onClick3() {
        cl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("3");
                cl3.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick4() {
        cl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("4");
                cl4.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick5() {
        cl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("5");
                cl5.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick6() {
        cl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("6");
                cl6.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick7() {
        cl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("7");
                cl7.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick8() {
        cl8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("8");
                cl8.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick9() {
        cl9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("9");
                cl9.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    private void onClick10() {
        cl10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) context).setClassSelected("10");
                cl10.setBackground(getResources().getDrawable(R.drawable.border_rounded_orange));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },600);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void onClickCancel() {
    }

    private void onClickCall() {

    }
}
