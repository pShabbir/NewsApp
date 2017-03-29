package com.vissionarray.shabbirhussain.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.drawable.stat_notify_sync;

public class Main2Activity extends AppCompatActivity {

    JSONObject jsonObject, jsonObject1;
    JSONArray news;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.spin);
        rotation.setRepeatCount(Animation.INFINITE);

        Bundle b = getIntent().getExtras();
        String s = b.getString("test");
        int pos = b.getInt("pos");


        try {

            jsonObject = new JSONObject(s);
            news = jsonObject.getJSONArray("articles");
            jsonObject1 = news.getJSONObject(pos);
            String description = jsonObject1.getString("description");
            String imageURL = jsonObject1.getString("urlToImage");

            TextView t = (TextView) findViewById(R.id.textView3);
            t.setText(description);


            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            Ion.with(this)
                    .load(imageURL)
                    .withBitmap()
                    .error(R.mipmap.sh)
                    .animateLoad(rotation)
                    .intoImageView(imageView);
//            //txt.setText(returnNews);
//             .animateLoad(spinAnimation)
//                    .animateIn(fadeInAnimation)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}