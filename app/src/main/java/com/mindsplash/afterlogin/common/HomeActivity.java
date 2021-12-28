package com.mindsplash.afterlogin.common;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.mindsplash.R;
import com.mindsplash.afterlogin.common.account.BookMarks;
import com.mindsplash.afterlogin.common.account.MyAccount;
import com.mindsplash.afterlogin.common.account.MyBookMarks;
import com.mindsplash.afterlogin.fragments.Ask;
import com.mindsplash.afterlogin.fragments.Download;
import com.mindsplash.afterlogin.fragments.SearchFragment;
import com.mindsplash.afterlogin.fragments.home.Home;
import com.mindsplash.afterlogin.fragments.Search;
import com.mindsplash.beforelogin.Splash;
import com.mindsplash.helper.AppSharedPreference;
import com.mindsplash.helper.AppUtils;
import com.mindsplash.helper.CallBack;
import com.mindsplash.helper.ConnectionDetector;
import com.mindsplash.network.model.Student;
import com.mindsplash.network.model.StudentDetails;
import com.mindsplash.network.model.Subject;
import com.mindsplash.services.student_services.StudentService;
import com.mindsplash.services.student_services.studentservice_impl.StudeServiceImpl;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView jtv_AppName;
    private ConnectionDetector connectionDetector;
    private EditText edOldPass;
    private EditText edNewPass;
    private EditText edConfPass;
    private RelativeLayout changePasswordLayout;
    private ImageView showPassword;
    private ImageView hidePassword;
    private SharedPreferences shared;
    private String mUserName, mUserMobile, oldPass, newPass, confPass;
    private Dialog changeDialog;
    private String mUserType;
    Menu nav_Menu;
    private TextView textView;
    RelativeLayout backButton;
    TextView header_title;
    private ImageView mLogo;
    private Toolbar toolbar;
    private AppSharedPreference appSharedPreference;
    private SharedPreferences sharedPreferences;
    private String mobileNumber;
    private TextView textUsername;
    private Home home;
    private     DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        changeStatusBarColor();
        home = new Home();
        changefragment(home, "Home");
        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }
    }

    private void init() {
        connectionDetector = new ConnectionDetector(this);
        //Toolbar
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        jtv_AppName = toolbar.findViewById(R.id.tvTitle);
        //Drawer
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        nav_Menu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);
        textUsername = header.findViewById(R.id.userNText);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);
        //Bottomnav
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getUserDetails();
    }


    private void setSubjectDetails(ArrayList<Subject> data) {
        home.setSubjects(data);
    }

    private void getUserDetails() {
        appSharedPreference = AppSharedPreference.getMInstance();
        sharedPreferences = AppSharedPreference.getInstance(this);
        if (appSharedPreference.getStudentDetails().equalsIgnoreCase("") || appSharedPreference.getStudentDetails() == null)
            callApiTogetDetails();
        else
            setStudentDetails();
    }

    private void setStudentDetails() {
        Student student = new Gson().fromJson(appSharedPreference.getStudentDetails(), Student.class);
        textUsername.setText(student.getFirstname() + " " + student.getLastname());
    }

    private void setStudentDetails(Student student) {
        textUsername.setText(student.getFirstname() + " " + student.getLastname());
    }

    private void callApiTogetDetails() {
        StudentService studentService = new StudeServiceImpl();
        studentService.getStudentDetails(appSharedPreference.getStudentMobileNo(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    StudentDetails student = (StudentDetails) object;
                    appSharedPreference.addStudentDetails(new Gson().toJson(student.getStudent()));
                    setStudentDetails(student.getStudent());
                } else
                    new AppUtils().showError(HomeActivity.this, "Student Not Found");
            }

            @Override
            public void onError(Object object) {
                new AppUtils().showError(HomeActivity.this, object.toString());
            }
        });

    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }


    protected OnBackPressedListener onBackPressedListener;


    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }

    public void changefragmentWithoutAnim(Fragment frag, String Name) {
        jtv_AppName.setText(Name);
        Fragment frag1 = new Fragment();
        frag1 = frag;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, frag);
        transaction.commit();
    }

    public void changefragment(Fragment frag, String Name) {
//        jtv_AppName.setText(Name);
        Fragment frag1 = new Fragment();
        frag1 = frag;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, frag);
        transaction.commit();
    }

    public void backFragment(Fragment frag, String Name) {
//        jtv_AppName.setText(Name);
        Fragment frag1 = new Fragment();
        frag1 = frag;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_container, frag);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, MyAccount.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_account:
                startActivity(new Intent(this, MyAccount.class));
                break;
            case R.id.nav_bookmark:
                startActivity(new Intent(this, MyBookMarks.class));
                break;
            case R.id.nav_download:
                drawer.close();
                Download down = new Download();
                changefragment(down, "Download");
                break;
            case R.id.nav_remove:
                removeMyData();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removeMyData() {
        Dialog progressDialog = new AppUtils().getProgressDialog(this);
        progressDialog.show();
        StudentService studentService = new StudeServiceImpl();
        AppSharedPreference appSharedPreference = AppSharedPreference.getMInstance();
        AppSharedPreference.getInstance(this);
        Student student = new Gson().fromJson(appSharedPreference.getStudentDetails(), Student.class);
        if (student != null)
            studentService.removeAccount(student.getId(), new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    progressDialog.dismiss();
                    AppUtils.showSuccess(HomeActivity.this, "Account deleted Successfully.");
                    appSharedPreference.clearAll(AppSharedPreference.getInstance(HomeActivity.this));
                    Intent intent = new Intent(HomeActivity.this, Splash.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finishAffinity();
                    if (object != null) {

                    }
                }

                @Override
                public void onError(Object object) {

                }
            });
    }

    public void addfragment(Fragment frag, String Name) {
//        jtv_AppName.setText(Name);
        Fragment frag1 = new Fragment();
        frag1 = frag;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.add(R.id.fragment_container, frag);
        transaction.commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    SearchFragment hm = new SearchFragment();
                    changefragment(hm, "Search");
                    return true;
                case R.id.navigation_home:
                    Home Learn = new Home();
                    changefragment(Learn, "Learn");
                    return true;

                case R.id.navigation_ask:
                    Ask ask = new Ask();
                    changefragment(ask, "Ask");
                    return true;
                case R.id.navigation_download:
                    Download down = new Download();
                    changefragment(down, "Download");
                    return true;
            }
            return false;
        }
    };


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA))
            {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("permission_necessary");
                alertBuilder.setMessage("storage_permission_is_encessary_to_wrote_event");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE,CAMERA}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,CAMERA}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
//                if (flag) {
//                    openActivity();
//                } else {
//                    finish();
//                }

            } else {
//                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        Log.e("permoission","granted");
        //add your further process after giving permission or to download images from remote server.
    }
}
