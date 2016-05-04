package com.nomader.weather.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nomader.weather.MainActivity;
import com.nomader.weather.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by nomader on 23.01.16.
 */
public class MapFragment extends Fragment {
    public static String TAG = "MapFragment";

    private MapView mapView;
    private GoogleMap googleMap;
    private MainActivity activity;
    private double lat, lng;
    private SearchView searchView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        Log.d(TAG, this.getTag());
        this.activity = (MainActivity) getActivity();
        lat = -1337;
        lng = -1337;

        View view = layoutInflater.inflate(R.layout.fragment_map, viewGroup, false);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchLocation(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Bundle bundle = getArguments();
        if(savedInstanceState != null){
            lat = savedInstanceState.getDouble("lat");
            lng = savedInstanceState.getDouble("lng");
        }else if(bundle != null){
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
        }

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                Log.d(TAG, "map ready");
                googleMap = map;
                setupMap();
            }
        });



        return view;
    }

    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putDouble("lat", lat);
        state.putDouble("lng", lng);
    }

    private void setupMap() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, );
            Toast.makeText(getActivity(), "Brak uprawnień", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "no permissions");
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng));
                activity.getWeather(latLng.latitude, latLng.longitude);
                activity.onBackPressed();
            }
        });
        mapView.onResume();
        if(lat != -1337 && lng != -1337){
            LatLng location = new LatLng(lat, lng);
            googleMap.addMarker(new MarkerOptions().position(location));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }

    public void searchLocation(String address){
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try{
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if(!addresses.isEmpty()){
                double lat = addresses.get(0).getLatitude();
                double lng = addresses.get(0).getLongitude();
                Log.d(TAG, " search lat: " + lat + " lng:" + lng);
                LatLng location = new LatLng(lat, lng);

                googleMap.addMarker(new MarkerOptions().position(location));
                activity.getWeather(lat, lng);
                activity.onBackPressed();
            }else {
                Toast.makeText(getActivity(), "Nie znaleziono lokalizacji", Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
            }
        }catch (Exception ex){
            Log.e(TAG, "search error");
        }
    }

}
