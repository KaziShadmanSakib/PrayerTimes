package com.example.prayertimes.others;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class QuoteGetter {
    Context context;
    long currentTime;
    public QuoteGetter(Context c, long currentTime){
        context = c;
        this.currentTime = currentTime;
    }
    public String  quoteOfTheDayFunction() {

        int min = 0;
        int max = 100;
        String text = "";
        try {

            InputStream is = context.getAssets().open("quoteOfTheDay.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);


            String[] lines = text.split(System.getProperty("line.separator"));

            if(currentTime >= 0){

                int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                String quote = lines[random_int];

                PrefConfig.saveQuoteOfTheDay(context, quote);

            }



        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String quote = PrefConfig.loadQuoteOfTheDay(context);
        return quote;

    }
}
