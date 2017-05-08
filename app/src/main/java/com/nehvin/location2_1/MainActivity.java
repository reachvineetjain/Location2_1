package com.nehvin.location2_1;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private TextView mLatitudeTxt;
    private TextView mLongitudeTxt;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOG_TAG = "VKJ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG,"On Create Method");
        mLatitudeTxt = (TextView) findViewById(R.id.latitude);
        mLongitudeTxt = (TextView) findViewById(R.id.longitude);
        buildGoogleApiClient();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOG_TAG,"On Connected Method");
//        mLocationrequest = LocationRequest.create();
//        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationrequest.setInterval(1000);
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationrequest, (LocationListener) this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_TAG,"Check for Permissions");
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLastLocation != null)
            {
                Log.i(LOG_TAG,"Print the latitude and longitude");
                mLatitudeTxt.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeTxt.setText(String.valueOf(mLastLocation.getLongitude()));
            }
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationrequest, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Log.i(LOG_TAG, location.toString());
//                    locUpdateView.setText(location.getLatitude()+"\n"+location.getLongitude()+"\n"+location.getAccuracy());
//                }
//            });
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG,"Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG,"Connection Failed");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        if(mGoogleApiClient.isConnected()){
            Log.i(LOG_TAG,"OnStart");
            Log.i(LOG_TAG,"Connection Established");
        }
        else
        {
            Log.i(LOG_TAG,"On Start");
            Log.i(LOG_TAG,"Connection Failed");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"On Stop");
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }

    }

    private void buildGoogleApiClient()
    {
        Log.i(LOG_TAG,"Build Google API Client");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }
}
