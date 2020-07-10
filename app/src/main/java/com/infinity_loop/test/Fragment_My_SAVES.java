package com.infinity_loop.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Fragment_My_SAVES extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView tvStatus;
    private static final String TAG = null;
    private AppCompatImageView imgView, imgLoaded;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fragment__my__s_a_v_e_s );
        dbHelper = new DBHelper( this );
        ListView listView = (ListView)findViewById( R.id.listView);
        ArrayList<Person> dolist = dbHelper.getli();
        listadapter listadapter = new listadapter( Fragment_My_SAVES.this,R.layout.image_view,dolist );
        listView.setAdapter(listadapter);
    }

}

