package com.example.prayertimes;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class QuoteGetter {
    Context context;
    Boolean isFirstTimeQuote;
    String currentTime;
    QuoteGetter(Context c,Boolean b,String currentTime){
        context = c;
        isFirstTimeQuote = b;
        this.currentTime = currentTime;
    }
    public String  quoteOfTheDayFunction() {

        int min = 0;
        int max = 99;
        String text = "";
        try {

            InputStream is = context.getAssets().open("quoteOfTheDay.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);

            TimeParser timeParser = new TimeParser();
            long currentTime1 = timeParser.timeParserMethodForCurrentTime(currentTime);
            String[] lines = text.split(System.getProperty("line.separator"));

            if(isFirstTimeQuote){
                isFirstTimeQuote = false;
                PrefConfig.saveQuoteOfTheDay(context, "There is no god but Allah, and Muhammad is the messenger of Allah.");
            }

            if(currentTime1 >= 0){

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
