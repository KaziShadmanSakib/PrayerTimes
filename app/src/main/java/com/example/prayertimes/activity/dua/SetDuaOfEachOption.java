package com.example.prayertimes.activity.dua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.prayertimes.R;

public class SetDuaOfEachOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_dua_of_each_option);

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Setting images of specific dua in their specific options */

        setDua();




    }

    private void setDua(){

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {

            Integer i =(int) bundle.get("index");
            if(i!=null){
                ImageView imageView = findViewById(R.id.dua01);
                setImage(i,imageView);
            }

            Integer j =(int) bundle.get("index2");
            if(j!=null){
                ImageView imageView2 = findViewById(R.id.dua02);
                setImage(j,imageView2);
            }


        }


    }

    private void setImage(int i,ImageView imageView) {

        if(i==0){
            return;
        }

        if(i==1){

            /*App bar config */

            getSupportActionBar().setTitle("Ramadan");

            imageView.setImageResource(R.drawable.ramadan01_01);


        }
        else if(i==2){

            /*App bar config */

            getSupportActionBar().setTitle("Ramadan");

            imageView.setImageResource(R.drawable.ramadan01_02);


        }
        else if(i==3){

            /*App bar config */

            getSupportActionBar().setTitle("Ramadan");

            imageView.setImageResource(R.drawable.ramadan02);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setTranslationY(-100);

        }
        else if(i==4){

            /*App bar config */

            getSupportActionBar().setTitle("Ramadan");

            imageView.setImageResource(R.drawable.ramadan03);

            imageView.setTranslationY(-100);



        }
        else if(i==5){

            /*App bar config */

            getSupportActionBar().setTitle("Ramadan");

            imageView.setImageResource(R.drawable.ramadan04);

            imageView.setTranslationY(-100);

        }

    }
}

