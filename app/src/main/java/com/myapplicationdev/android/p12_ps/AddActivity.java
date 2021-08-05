package com.myapplicationdev.android.p12_ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    int notificationId = 001;
    EditText etName, etDesc;
    Button btnAddTask, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acticity);

        etName = findViewById(R.id.editTextName);
        etDesc = findViewById(R.id.editTextDesc);
        btnAddTask = findViewById(R.id.buttonAddTask);
        btnCancel = findViewById(R.id.buttonCancel);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());
                String taskName = "";
                if (etName.getText().toString().equalsIgnoreCase("")){
                    etName.setError("Please enter task name");
                } else {
                    taskName = etName.getText().toString();
                }
                String taskDesc = "";
                if (etDesc.getText().toString().equalsIgnoreCase("")){
                    etDesc.setError("Please enter description");
                } else {
                    taskDesc = etDesc.getText().toString();
                }
                if (!(taskName.equals("") || taskDesc.equals(""))){
                    long inserted_id = db.insertTask(taskName, taskDesc);
                    if (inserted_id != -1){
                        Toast.makeText(getApplicationContext(), "Inserted Successfully",
                                Toast.LENGTH_LONG).show();
                        NotificationManager nm = (NotificationManager)
                                getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new
                                    NotificationChannel("default", "Default Channel",
                                    NotificationManager.IMPORTANCE_DEFAULT);

                            channel.setDescription("This is for default notification");
                            nm.createNotificationChannel(channel);
                        }

                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        PendingIntent pendingIntent =
                                PendingIntent.getActivity(AddActivity.this, 0,
                                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Action action = new
                                NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher,
                                "This is an Action",
                                pendingIntent).build();

                        Intent intentreply = new Intent(AddActivity.this,
                                ReplyActivity.class);
                        PendingIntent pendingIntentReply = PendingIntent.getActivity
                                (AddActivity.this, 0, intentreply,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

                        RemoteInput ri = new RemoteInput.Builder("status")
                                .setLabel("Status report")
                                .setChoices(new String [] {"Done", "Not yet"})
                                .build();

                        NotificationCompat.Action action2 = new
                                NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher,
                                "Reply",
                                pendingIntentReply)
                                .addRemoteInput(ri)
                                .build();

                        NotificationCompat.WearableExtender extender = new
                                NotificationCompat.WearableExtender();
                        extender.addAction(action);
                        extender.addAction(action2);

                        String text = getString(R.string.basic_notify_msg);
                        String title = getString(R.string.notification_title);

                        NotificationCompat.Builder builder = new
                                NotificationCompat.Builder(AddActivity.this, "default");
                        builder.setContentText(text);
                        builder.setContentTitle(title);
                        builder.setSmallIcon(android.R.drawable.btn_star_big_off);

                        // Attach the action for Wear notification created above
                        builder.extend(extender);

                        Notification notification = builder.build();

                        nm.notify(notificationId, notification);
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}