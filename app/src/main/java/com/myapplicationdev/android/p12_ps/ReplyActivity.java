package com.myapplicationdev.android.p12_ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReplyActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Task> al;
    int actReqCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        DBHelper dbh = new DBHelper(this);
        al = dbh.getAllTasks();


        if (remoteInput != null){
        }

        if(reply != null){
            reply = remoteInput.getCharSequence("status");
            if (reply == "Done") {

            }
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
                    Toast.LENGTH_SHORT).show();
        }

    }
}