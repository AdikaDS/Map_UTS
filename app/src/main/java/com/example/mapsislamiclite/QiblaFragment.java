package com.example.mapsislamiclite;

import static android.content.Context.SENSOR_SERVICE;

import androidx.fragment.app.Fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class QiblaFragment extends Fragment implements SensorEventListener {

    private SensorManager mSensorManager;

    private ImageView kompasKiblat;

    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;

    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];

    private float azimuth = 0f;
    private float currentAzimuth = 0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qibla, container, false);

        kompasKiblat = view.findViewById(R.id.image_compass_qibla);

        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);

        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha = 0.97f;
        synchronized (this) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mAccelerometerData[0] = alpha * mAccelerometerData[0] + (1 - alpha) * sensorEvent.values[0];
                mAccelerometerData[1] = alpha * mAccelerometerData[1] + (1 - alpha) * sensorEvent.values[1];
                mAccelerometerData[2] = alpha * mAccelerometerData[2] + (1 - alpha) * sensorEvent.values[2];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mMagnetometerData[0] = alpha * mMagnetometerData[0] + (1 - alpha) * sensorEvent.values[0];
                mMagnetometerData[1] = alpha * mMagnetometerData[1] + (1 - alpha) * sensorEvent.values[1];
                mMagnetometerData[2] = alpha * mMagnetometerData[2] + (1 - alpha) * sensorEvent.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometerData, mMagnetometerData);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;

                Animation animate = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzimuth = azimuth;

                animate.setDuration(500);
                animate.setRepeatCount(0);
                animate.setFillAfter(true);

                kompasKiblat.startAnimation(animate);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}