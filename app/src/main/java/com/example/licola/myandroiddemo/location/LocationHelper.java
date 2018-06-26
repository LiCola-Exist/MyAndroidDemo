package com.example.licola.myandroiddemo.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.licola.llogger.LLogger;

/**
 * Created by LiCola on 2018/6/13.
 */
public class LocationHelper implements LocationListener {

  private static final String TAG = "LocationHelper";

  private LocationManager mLocationManager;
  private MyLocationListener mListener;
  private Context mContext;

  public static boolean isLocationProviderEnabled(@NonNull Context context) {
    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  }


  public LocationHelper(Context context) {
    mContext = context.getApplicationContext();
    mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  public void initLocation(MyLocationListener listener) {
    mListener = listener;
    Location location;

    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }

    if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      if (location != null) {
        mListener.updateLastLocation(location);
      }
      mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
      location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      if (location != null) {
        mListener.updateLastLocation(location);
      }
      mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }
  }

  public void removeLocationUpdatesListener() {
    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    if (mLocationManager != null) {
      mLocationManager.removeUpdates(this);
    }
  }

  // 定位坐标发生改变
  @Override
  public void onLocationChanged(Location location) {
    LLogger.d("onLocationChanged: ");
    if (mListener != null) {
      mListener.updateLocation(location);
    }
  }

  // 定位服务状态改变
  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    LLogger.d("onStatusChanged: ");
    if (mListener != null) {
      mListener.updateStatus(provider, status, extras);
    }
  }

  // 定位服务开启
  @Override
  public void onProviderEnabled(String provider) {
    LLogger.d("onProviderEnabled: " + provider);
  }

  // 定位服务关闭
  @Override
  public void onProviderDisabled(String provider) {
    LLogger.d("onProviderDisabled: " + provider);
  }

}
