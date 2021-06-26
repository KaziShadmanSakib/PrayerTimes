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

        else if(i==6){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");


            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj01);

            imageView.setTranslationY(-100);

        }

        else if(i==7){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj02);

            imageView.setTranslationY(-100);

        }

        else if(i==8){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj03);

            imageView.setTranslationY(-100);

        }

        else if(i==9){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setImageResource(R.drawable.hajj04);

            imageView.setTranslationY(-100);

        }

        else if(i==10){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj05);

            imageView.setTranslationY(-100);

        }

        else if(i==11){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj06);

            imageView.setTranslationY(-100);

        }


        else if(i==12){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj07);

            imageView.setTranslationY(-100);

        }

        else if(i==13){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.hajj08);

            imageView.setTranslationY(-100);

        }

        else if(i==14){

            /*App bar config */

            getSupportActionBar().setTitle("Hajj / Umrah");

            imageView.setImageResource(R.drawable.hajj09);

            imageView.setTranslationY(-100);

        }

        else if(i==15){

            /*App bar config */

            getSupportActionBar().setTitle("Mosque");

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setImageResource(R.drawable.mosque01);

            imageView.setTranslationY(-100);

        }

        else if(i==16){

            /*App bar config */

            getSupportActionBar().setTitle("Mosque");

            imageView.setImageResource(R.drawable.mosque02_01);

        }

        else if(i==17){

            /*App bar config */

            getSupportActionBar().setTitle("Mosque");

            imageView.setImageResource(R.drawable.mosque02_02);

        }

        else if(i==18){

            /*App bar config */

            getSupportActionBar().setTitle("Mosque");

            imageView.setImageResource(R.drawable.mosque03_01);

        }

        else if(i==19){

            /*App bar config */

            getSupportActionBar().setTitle("Mosque");

            imageView.setImageResource(R.drawable.mosque03_02);

        }

        else if(i==20){

            /*App bar config */

            getSupportActionBar().setTitle("Home");

            imageView.setImageResource(R.drawable.homedua01_01);


        }

        else if(i==21){

            /*App bar config */

            getSupportActionBar().setTitle("Home");

            imageView.setImageResource(R.drawable.homedua01_02);



        }

        else if(i==22){

            /*App bar config */

            getSupportActionBar().setTitle("Home");

            imageView.setImageResource(R.drawable.homedua02);

            imageView.setTranslationY(-100);




        }

        else if(i==23){

            /*App bar config */

            getSupportActionBar().setTitle("Home");

            imageView.setImageResource(R.drawable.homedua03);

            imageView.setTranslationY(-100);




        }

        else if(i==24){

            /*App bar config */

            getSupportActionBar().setTitle("Home");

            imageView.setImageResource(R.drawable.homedua04);

            imageView.setTranslationY(-100);




        }

        else if(i==25){

            /*App bar config */

            getSupportActionBar().setTitle("Sleeping");

            imageView.setImageResource(R.drawable.sleepingdua01);

            imageView.setTranslationY(-100);




        }

        else if(i==26){

            /*App bar config */

            getSupportActionBar().setTitle("Sleeping");

            imageView.setImageResource(R.drawable.sleepingdua02);

            imageView.setTranslationY(-100);




        }

    }
}

