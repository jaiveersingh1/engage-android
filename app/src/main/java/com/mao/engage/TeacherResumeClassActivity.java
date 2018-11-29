package com.mao.engage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherResumeClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SectionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_resume_class);

        recyclerView = findViewById(R.id.recyclerView);



        ArrayList<SectionSesh> dummySections = new ArrayList<>();
        SectionSesh dummySectionSesh = new SectionSesh("10-27-2018-01:09PM", "10-27-2018-02:09PM", "ffffffff-c819-e197-ffff-ffffef05ac4a", "Michelle's Section", "-LSO8fBtXE-iC9A_ccLw", 990, new ArrayList<java.lang.String>());
        SectionSesh dummySectionSesh2 = new SectionSesh("10-27-2018-01:09PM", "10-27-2018-02:09PM", "ffffffff-c819-e197-ffff-ffffef05ac4a", "Another Section", "-LSO8fBtX E-iC9A_ccLw", 890, new ArrayList<java.lang.String>());
        SectionSesh dummySectionSesh3 = new SectionSesh("10-27-2018-01:09PM", "10-27-2018-02:09PM", "ffffffff-c819-e197-ffff-ffffef05ac4a", "A third Section", "-LSO8fBtX E-iC9A_ccLw", 890, new ArrayList<java.lang.String>());

        dummySections.add(dummySectionSesh);
        dummySections.add(dummySectionSesh2);
        dummySections.add(dummySectionSesh3);

        layoutManager = new LinearLayoutManager(this);
        adapter = new SectionAdapter(this, dummySections);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
