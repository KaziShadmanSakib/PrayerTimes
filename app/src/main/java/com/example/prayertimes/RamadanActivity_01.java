package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class RamadanActivity_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadan01);



        /*App bar config */

        getSupportActionBar().setTitle("Ramadan");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {



            Integer i =(int) bundle.get("index");
            if(i!=null){
                ImageView imageView = findViewById(R.id.ramadanDua_01_01);
                setImage(i,imageView);
            }

            Integer j =(int) bundle.get("index2");
            if(j!=null){
                ImageView imageView2 = findViewById(R.id.imageView4);
                setImage(j,imageView2);
            }


        }




    }

    private void setImage(int i,ImageView imageView) {

        if(i==0){
            return;
        }

        if(i==1){
            imageView.setImageResource(R.drawable.ramadan01_01);
        }
        else if(i==2){
            imageView.setImageResource(R.drawable.ramadan01_02);
        }
        else if(i==3){
            imageView.setImageResource(R.drawable.kaaba);
        }
        else if(i==4){
            imageView.setImageResource(R.drawable.imagecompass);
        }
        else if(i==5){
            imageView.setImageResource(R.drawable.background);
        }

    }
}

