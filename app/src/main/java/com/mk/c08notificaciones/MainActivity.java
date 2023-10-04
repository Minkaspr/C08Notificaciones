package com.mk.c08notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private static final String CHANNEL_ID = "channel_id";
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final int NOTIFICATION_ID = 1;
    private static final String REPLY_ACTION = "reply_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(this);
        Button button = findViewById(R.id.notificationButton);
        button.setOnClickListener(v -> showNotification());
    }

    private void showNotification() {
        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        Intent replyIntent = new Intent(this, MainActivity.class);
        replyIntent.setAction(REPLY_ACTION);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, replyIntent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Mensaje")
                .setContentText("Este es un mensaje de prueba.")
                .addAction(new NotificationCompat.Action.Builder(R.drawable.ic_reply, replyLabel, replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build());

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
