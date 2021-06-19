package com.example.prayertimes;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class JasonFetcher {
    private static final String TAG = "tag";
    private String fazrNamazTime, sunriseTime, dhuhrNamazTime, asarNamazTime, sunsetTime, magribNamazTime, ishaNamazTime, imsakTime;

    String url;
    Context context;

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    JasonFetcher(Context context){
        this.context = context;

    }


    public void getData(){

        String city = PrefConfig.loadCurrentCity(context);;
        String country = PrefConfig.loadCurrentCountry(context);


        url = "http://api.aladhan.com/v1/timingsByCity?city="+ city +"&country="+ country +"&method=1&school=1";

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


                        /* Saving all the waqts in PrefConfig (SharedPreferences) */

                        PrefConfig.saveFajrTime(context, fazrNamazTime);
                        PrefConfig.saveSunriseTime(context, sunriseTime);
                        PrefConfig.saveDhuhrTime(context, dhuhrNamazTime);
                        PrefConfig.saveAsarTime(context, asarNamazTime);
                        PrefConfig.saveSunsetTime(context, sunsetTime);
                        PrefConfig.saveMagribTime(context, magribNamazTime);
                        PrefConfig.saveIshaTime(context, ishaNamazTime);
                        PrefConfig.saveImsakTime(context, imsakTime);

                        //pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(context, "Please turn on location or internet to be always updated", Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                //pDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


}
