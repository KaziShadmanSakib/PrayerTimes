package com.example.prayertimes.datetime;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.prayertimes.others.AppController;
import com.example.prayertimes.others.LoadingDialog;
import com.example.prayertimes.options.PrefConfig;

import org.json.JSONObject;

public class JasonFetcher {
    private static final String TAG = "tag";
    private String fazrNamazTime, sunriseTime, dhuhrNamazTime, asarNamazTime, sunsetTime, magribNamazTime, ishaNamazTime, imsakTime;
    private String fajrNamazTimeAMPM, sunriseTimeAMPM, dhuhrNamazTimeAMPM, asarNamazTimeAMPM, sunsetTimeAMPM, magribNamazTimeAMPM, ishaNamazTimeAMPM, imsakTimeAMPM;
    String url;
    Context context;
    Boolean isOk = true;
    String city,country;

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    public JasonFetcher(Context context){
        this.context = context;
        city = PrefConfig.loadCurrentCity(context);;
        country = PrefConfig.loadCurrentCountry(context);

    }

    public boolean isNoError(){
        return isOk;
    }
    public void getTempData(String tempCity , String tempCountry){
        city = tempCity;
        country = tempCountry;
        getData();

    }


    public void getData(){
        LoadingDialog loadingDialog = new LoadingDialog((Activity) context);

        if(PrefConfig.loadFirstTime(context)=="FirstTime") {
            Toast toast = Toast.makeText(context,"Please check if the internet is on",Toast.LENGTH_SHORT);
            toast.show();
            loadingDialog.startLoadingDialog();
        }

        String schholType = String.valueOf(PrefConfig.loadMazhabType(context));



        url = "";
        url = "http://api.aladhan.com/v1/timingsByCity?city="+ city +"&country="+ country +"&method=1&school="+schholType;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        /* Get data from JSON */

                        String urlJson = response.toString();

                        JsonParser jsonParser = new JsonParser();

                        jsonParser.setUrlString(urlJson);


                        fazrNamazTime = jsonParser.fazrTime();
                        sunriseTime = jsonParser.sunrise();
                        dhuhrNamazTime = jsonParser.dhuhrTime();
                        asarNamazTime = jsonParser.asarTime();
                        sunsetTime = jsonParser.sunset();
                        magribNamazTime = jsonParser.magribTime();
                        ishaNamazTime = jsonParser.ishaTime();
                        imsakTime = jsonParser.imsakTime();



                        /* Converting time to AMPM */

                        TimeParser timeParser = new TimeParser();


                        fajrNamazTimeAMPM = timeParser.timeParseToAMPM(fazrNamazTime);
                        sunriseTimeAMPM = timeParser.timeParseToAMPM(sunriseTime);
                        dhuhrNamazTimeAMPM = timeParser.timeParseToAMPM(dhuhrNamazTime);
                        asarNamazTimeAMPM = timeParser.timeParseToAMPM(asarNamazTime);
                        sunsetTimeAMPM = timeParser.timeParseToAMPM(sunsetTime);
                        magribNamazTimeAMPM = timeParser.timeParseToAMPM(magribNamazTime);
                        ishaNamazTimeAMPM = timeParser.timeParseToAMPM(ishaNamazTime);
                        imsakTimeAMPM = timeParser.timeParseToAMPM(imsakTime);


                        /* Saving all the waqts in PrefConfig (SharedPreferences) */

                        /* Time in 24hr format */

                        PrefConfig.saveFajrTime(context, fazrNamazTime);
                        PrefConfig.saveSunriseTime(context, sunriseTime);
                        PrefConfig.saveDhuhrTime(context, dhuhrNamazTime);
                        PrefConfig.saveAsarTime(context, asarNamazTime);
                        PrefConfig.saveSunsetTime(context, sunsetTime);
                        PrefConfig.saveMagribTime(context, magribNamazTime);
                        PrefConfig.saveIshaTime(context, ishaNamazTime);
                        PrefConfig.saveImsakTime(context, imsakTime);

                        /* Time in 12hr format */

                        PrefConfig.saveFajrTimeAMPM(context, fajrNamazTimeAMPM);
                        PrefConfig.saveSunriseTimeAMPM(context, sunriseTimeAMPM);
                        PrefConfig.saveDhuhrTimeAMPM(context, dhuhrNamazTimeAMPM);
                        PrefConfig.saveAsarTimeAMPM(context, asarNamazTimeAMPM);
                        PrefConfig.saveSunsetTimeAMPM(context, sunsetTimeAMPM);
                        PrefConfig.saveMagribTimeAMPM(context, magribNamazTimeAMPM);
                        PrefConfig.saveIshaTimeAMPM(context, ishaNamazTimeAMPM);
                        PrefConfig.saveImsakTimeAMPM(context, imsakTimeAMPM);
                        //pDialog.hide();
                        if(PrefConfig.loadFirstTime(context)=="FirstTime") {
                            NowAndNextPrayer nowAndNextPrayer = new NowAndNextPrayer(context);
                            nowAndNextPrayer.setNowAndNext();
                            PrefConfig.savefirstTime(context,"NotFirstTime");



                            loadingDialog.dismissDialog();




                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                isOk = false;
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(context, "Please turn on location or internet to be always updated", Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                //pDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


}
