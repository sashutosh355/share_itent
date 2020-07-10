package com.infinity_loop.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = null;
    private ImageView mImageView;
    private TextView mTextView;
    private Button btnOpenGallery, btnSaveImage, btnLoadImage;
    private DBHelper dbHelper;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        btnSaveImage = findViewById(R.id.save);
        dbHelper = new DBHelper(this);
        btnSaveImage.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    MainActivity.this.saveImageInDB();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }else if (type.startsWith("video/")) {
                handleSendVideo(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }

    }

    private void handleSendVideo(Intent intent) {
        mImageView = (ImageView)findViewById(R.id.imageView);
        Uri videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (videoUri != null){
            mImageView.setImageURI(videoUri);
        }
    }

    void handleSendText(Intent intent) {
        mTextView = (TextView) findViewById(R.id.discription);
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            mTextView.setText(sharedText);
        }
    }

    void handleSendImage(Intent intent) {
        mImageView = (ImageView)findViewById(R.id.imageView);
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            mImageView.setImageURI(imageUri);
        }
    }
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }

    boolean saveImageInDB() {

        try {
            dbHelper.open();
            InputStream iStream = getContentResolver().openInputStream(imageUri);
            byte[] inputData = Utils.getBytes(iStream);
            dbHelper.insertImage(inputData);
            dbHelper.close();
            startActivity(new Intent(MainActivity.this, Fragment_My_SAVES.class));
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            dbHelper.close();
            return false;
        }

    }


    }
