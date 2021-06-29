package com.kssabd.prayertimes.options;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.IBinder;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class QiblaDirectionCompass extends Service implements SensorEventListener {
    public static ImageView image, arrow;

    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float currentDegreeNeedle = 0f;
    Context context;
    Location userLoc = new Location("service Provider");
    // device sensor manager
    private static SensorManager mSensorManager;
    private Sensor sensor;
    public static TextView tvHeading;

    public QiblaDirectionCompass(Context context, ImageView compass, ImageView needle, TextView heading, double longi, double lati, double alti) {

        image = compass;
        arrow = needle;


        // TextView that will tell the user what degree is he heading
        tvHeading = heading;
        userLoc.setLongitude(longi);
        userLoc.setLatitude(lati);
        userLoc.setAltitude(alti);

        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            // for the system's orientation sensor registered listeners
            mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);//SensorManager.SENSOR_DELAY_Fastest
        } else {
            Toast.makeText(context, "Not Supported", Toast.LENGTH_SHORT).show();
        }
        // initialize your android device sensor capabilities
        this.context = context;
    }

    @Override
    public void onCreate () {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME); //SensorManager.SENSOR_DELAY_Fastest
        super.onCreate();
    }

    @Override
    public void onDestroy () {
        mSensorManager.unregisterListener(this);
        Toast.makeText(context, "Destroy", Toast.LENGTH_SHORT).show();

        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        float head = Math.round(event.values[0]);
        float bearTo;
        Location destinationLoc = new Location("service Provider");

        destinationLoc.setLatitude(21.422487); //kaaba latitude setting
        destinationLoc.setLongitude(39.826206); //kaaba longitude setting
        bearTo = userLoc.bearingTo(destinationLoc);


        GeomagneticField geoField = new GeomagneticField(Double.valueOf(userLoc.getLatitude()).floatValue(), Double
                .valueOf(userLoc.getLongitude()).floatValue(),
                Double.valueOf(userLoc.getAltitude()).floatValue(),
                System.currentTimeMillis());
        head -= geoField.getDeclination(); // converts magnetic north into true north

        if (bearTo < 0) {
            bearTo = bearTo + 360;
            //bearTo = -100 + 360  = 260;
        }

//This is where we choose to point it
        float direction = bearTo - head;

// If the direction is smaller than 0, add 360 to get the rotation clockwise.
        if (direction < 0) {
            direction = direction + 360;
        }
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        RotateAnimation raQibla = new RotateAnimation(currentDegreeNeedle, direction, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        raQibla.setDuration(210);
        raQibla.setFillAfter(true);

        arrow.startAnimation(raQibla);

        currentDegreeNeedle = direction;

// create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

// how long the animation will take place
        ra.setDuration(210);


// set the animation after the end of the reservation status
        ra.setFillAfter(true);

// Start the animation
        image.startAnimation(ra);

        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}