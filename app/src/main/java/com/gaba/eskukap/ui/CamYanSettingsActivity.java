package com.gaba.eskukap.ui;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CamYanSettingsActivity extends Activity {
    private static final int REQUEST_PICK_IMAGE = 1001;
    private static final int REQUEST_PERM = 2001;
    private static final String CHANNEL_ID = "camyan_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setPadding(20,20,20,20);
        Button btn = new Button(this);
        btn.setText("Выбрать фото");
        ll.addView(btn);
        setContentView(ll);

        btn.setOnClickListener(v -> {
            if (needPermission()) {
                requestStoragePermission();
            } else {
                openPicker();
            }
        });

        createChannelIfNeeded();
    }

    private boolean needPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERM);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERM);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openPicker();
            } else {
                Toast.makeText(this, "Нужен доступ к фотографиям", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openPicker() {
        Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pick.setType("image/*");
        startActivityForResult(Intent.createChooser(pick, "Выберите фото"), REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    String filename = "selected.jpg";
                    File outInternal = new File(getFilesDir(), filename);
                    try (InputStream is = getContentResolver().openInputStream(uri);
                         OutputStream os = new BufferedOutputStream(new FileOutputStream(outInternal))) {
                        byte[] buf = new byte[8192];
                        int r;
                        while ((r = is.read(buf)) != -1) os.write(buf, 0, r);
                        os.flush();
                    }

                    File publicDir = new File(Environment.getExternalStorageDirectory(), "CamYan");
                    if (!publicDir.exists()) publicDir.mkdirs();
                    File outPublic = new File(publicDir, filename);
                    try (InputStream is2 = getContentResolver().openInputStream(uri);
                         OutputStream os2 = new BufferedOutputStream(new FileOutputStream(outPublic))) {
                        byte[] buf = new byte[8192];
                        int r;
                        while ((r = is2.read(buf)) != -1) os2.write(buf, 0, r);
                        os2.flush();
                    }

                    File meta = new File(publicDir, "selected.meta");
                    try (FileOutputStream fos = new FileOutputStream(meta)) {
                        fos.write(outPublic.getAbsolutePath().getBytes("UTF-8"));
                        fos.flush();
                    }

                    Intent b = new Intent("com.gaba.eskukap.ACTION_IMAGE_SELECTED");
                    b.putExtra("path", outPublic.getAbsolutePath());
                    sendBroadcast(b);

                    showNotification("Фото выбрано", "Путь: " + outPublic.getAbsolutePath());

                    Toast.makeText(this, "Фото сохранено", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (nm != null) {
                NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "CamYan", NotificationManager.IMPORTANCE_DEFAULT);
                nm.createNotificationChannel(ch);
            }
        }
    }

    private void showNotification(String title, String text) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) nm.notify(1001, b.build());
    }
}
