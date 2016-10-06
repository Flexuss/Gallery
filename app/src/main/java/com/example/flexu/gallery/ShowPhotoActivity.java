package com.example.flexu.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ShowPhotoActivity extends AppCompatActivity {

    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        photo= (ImageView) findViewById(R.id.iv);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        File a =new File(path);
        Picasso.with(this).load(a).into(photo);
    }
}
