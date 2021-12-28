package com.mindsplash.helper;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.mindsplash.R;
import java.util.regex.Pattern;
import com.andrognito.flashbar.anim.FlashAnim;
import com.mindsplash.beforelogin.RegisterActivity;

public class AppUtils {
    public Dialog getProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        return progressDialog;
    }
    public static void showSuccess(Context context, String toast_text){

        new Flashbar.Builder((Activity) context)
                .gravity(Flashbar.Gravity.TOP)
                .duration(2000)
                //                .title("Error!!")
                .message(toast_text)
                .enableSwipeToDismiss()
                .dismissOnTapOutside()
                .backgroundDrawable(R.drawable.success_bg)
                //                .icon(R.drawable.ic_app_logo)
                .titleColorRes(R.color.white)
                .messageColorRes(R.color.white)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build()
                .show();
    }

        public void showError(Context context, String toast_text){
            new Flashbar.Builder((Activity) context)
                    .gravity(Flashbar.Gravity.TOP)
                    .duration(2000)
    //                .title("Error!!")
                    .message(toast_text)
                    .enableSwipeToDismiss()
                    .dismissOnTapOutside()
                    .backgroundDrawable(R.drawable.error_bg)
    //                .icon(R.drawable.ic_app_logo)
                    .titleColorRes(R.color.white)
                    .messageColorRes(R.color.white)
                    .enterAnimation(FlashAnim.with(context)
                            .animateBar()
                            .duration(750)
                            .alpha()
                            .overshoot())
                    .exitAnimation(FlashAnim.with(context)
                            .animateBar()
                            .duration(400)
                            .accelerateDecelerate())
                    .build()
                    .show();

        }

    public static boolean isValidEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();

    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
