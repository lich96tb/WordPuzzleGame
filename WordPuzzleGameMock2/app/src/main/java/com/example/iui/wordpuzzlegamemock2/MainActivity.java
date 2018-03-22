package com.example.iui.wordpuzzlegamemock2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.iui.wordpuzzlegamemock2.module1.FragmentHome;
import com.example.iui.wordpuzzlegamemock2.utils.ViewModelMainActivity;

public class MainActivity extends AppCompatActivity {
    private ViewModelMainActivity viewModelMainActivity;
    private FragmentManager fragmentManager;
    private FragmentHome fragmentHome;
    private FragmentTransaction transaction;
    private String TAG = "SSS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelMainActivity = ViewModelProviders.of(this).get(ViewModelMainActivity.class);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragmentHome = new FragmentHome();
        transaction.replace(R.id.contaner_fragment, fragmentHome, "home");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.findFragmentById(R.id.contaner_fragment) == fragmentManager.findFragmentByTag("home")) {
            finish();
        } else {
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                super.onBackPressed();
            }else {
                super.onBackPressed();
            }
        }
    }
}
