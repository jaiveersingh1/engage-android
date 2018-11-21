package com.mao.engage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Hardcoded instance variables (that should not be hardcoded)
    final static String USER_ID = "user_id_1";
    final static String USERNAME = "Michelle Mao";

    final static String START = "2018-12-31-2000";
    final static String END = "2018-12-31-2200";

    final static String TA_NAME = "John Denero";
    final static String SECTION_ID = "CS70134A";
    final static int MAGICKEY = 420;


    DatabaseReference mSectionRef = FirebaseDatabase.getInstance().getReference("/Sections");
    DatabaseReference mUsersRef = FirebaseDatabase.getInstance().getReference("/UserSessions");

    // Global Variables
    String mSectionRefKey;
    String studentMagicKey;
    String teacherMagicKey;
    UserSesh mUser;
    SectionSesh mSection;
    ArrayList<String> mListUsers = new ArrayList<>();

    // Views to set
    TextView readValue; //TA-side: read slider_val from DB TODO: Read from DB (UserSesh's slider_val)
    TextView magicValue; //randomly generated magic key
    TextView refValue; //FireBase-generated ref key
    TextView refView; //Student-side: read section_ref from DB via. magic_key


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        readValue = findViewById(R.id.readView);
        magicValue = findViewById(R.id.codeView);
        refValue = findViewById(R.id.keyView);
        refView = findViewById(R.id.keyView2);

        setButtons();
        setSlider();

    }

    void setButtons() {
        // "START SECTION"
        Button teacherOK = findViewById((R.id.teacherOK));
        teacherOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSectionRefKey = mSectionRef.push().getKey(); //create empty node to get key of it
                refValue.setText(mSectionRefKey);

                teacherMagicKey = String.valueOf(MAGICKEY); //TODO: implement magic key generating alg
                magicValue.setText(teacherMagicKey);

                // Create new SectionSesh & store in DB
                mSection = new SectionSesh(START, END, TA_NAME, SECTION_ID, mSectionRefKey, MAGICKEY, mListUsers);
                mSectionRef.child(mSectionRefKey).setValue(mSection);
            }
        });
        // "JOIN"
        // This assumes SectionSesh with corresponding magic_key has been created
        final Button studentOK = findViewById(R.id.studentOK);
        studentOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText code = findViewById(R.id.codeText);
                studentMagicKey = code.getText().toString();

                // Create new UserSesh & store in DB
                mUser = new UserSesh(USER_ID, USERNAME,
                        Integer.valueOf(studentMagicKey), null);
                setSectionWithMagicKey(mUser);
                mUsersRef.child(USER_ID).setValue(mUser);

                // Update SectionSesh's user_ids
                mListUsers.add(mUser.user_id);
                mSectionRef.child(mSectionRefKey).child("user_ids").setValue(mListUsers);
            }
        });
    }

    void setSlider() {
        // slider
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(MainActivity.this,
                        "Seekbar vale " + i, Toast.LENGTH_SHORT).show();
                String key = mUser.getSection_ref_key();
                if (key != null) {
                    mSectionRef.child(key);
                    TextView index = findViewById(R.id.indexView);
                    index.setText(String.valueOf(i));
                    mUser.slider_val = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,
                        "Seekbar touch started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,
                        "Seekbar touch stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // takes in a UserSesh and sets .section_ref_key based on .magic_key
    void setSectionWithMagicKey(final UserSesh user) {
        mSectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SectionSesh section = snapshot.getValue(SectionSesh.class);
                    if (section.magic_key == user.magic_key) {
                        //TODO: could do this more elegantly.
                        // Reflect change in section_ref_key in both DB and UserSesh object
                        user.setSection_ref_key(section.getRef_key());
                        mUsersRef.child(user.user_id).child("section_ref_key").setValue(section.getRef_key());
                        refView.setText(user.section_ref_key);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}