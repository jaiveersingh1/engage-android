package com.mao.engage;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import info.hoang8f.android.segmented.SegmentedGroup;

public class StudentClassActivity extends AppCompatActivity implements MeFragment.OnFragmentInteractionListener, ClassFragment.OnFragmentInteractionListener {

    SegmentedGroup segmentedBar;
    RadioButton meTabBtn;
    RadioButton classTabBtn;
    MeFragment meFragment;
    ClassFragment classFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }

        setContentView(R.layout.activity_student_class);

        segmentedBar = findViewById(R.id.segmentedBar);
        meTabBtn = findViewById(R.id.meTabBtn);
        classTabBtn = findViewById(R.id.classTabBtn);

        segmentedBar.setTintColor(getResources().getColor(R.color.colorPrimary));
        meTabBtn.setTextColor(Color.WHITE);
        classTabBtn.setTextColor(Color.WHITE);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        meFragment = new MeFragment();
        classFragment = new ClassFragment();
        fragmentTransaction.replace(R.id.constraintLayout, meFragment);
        fragmentTransaction.commit();

        meTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.constraintLayout, meFragment);
                fragmentTransaction.commit();
            }
        });

        classTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.constraintLayout, classFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("BOBOB", "onFragmentInteraction: " + uri.toString());
    }
}
