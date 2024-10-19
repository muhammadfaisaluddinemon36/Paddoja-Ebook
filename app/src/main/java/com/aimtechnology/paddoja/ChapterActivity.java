package com.aimtechnology.paddoja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        String chapterTitle = getIntent().getStringExtra("title");
        String chapterFileUrl = getIntent().getStringExtra("fileUrl");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(chapterTitle);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        TextView textViewChapterContent = findViewById(R.id.tvChapterContent);

        fetchChapterContent(chapterFileUrl);


        SeekBar fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        final TextView tvChapterContent = findViewById(R.id.tvChapterContent);

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float newTextSize = 10 + (float)progress * 3; // Change the multiplier to adjust sensitivity
                tvChapterContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, newTextSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

    }

    private void fetchChapterContent(String chapterFileUrl) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(chapterFileUrl);

        storageRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String fileContent = new String(bytes);
                displayFileContent(fileContent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChapterActivity.this, "Failed to fetch chapter content", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFileContent(String fileContent) {
        TextView textViewChapterContent = findViewById(R.id.tvChapterContent);
        textViewChapterContent.setText(fileContent);
    }


}

