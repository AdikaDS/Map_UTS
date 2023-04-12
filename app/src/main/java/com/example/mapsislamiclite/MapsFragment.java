package com.example.mapsislamiclite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            LatLng masjidHidayah = new LatLng(-7.796606393543465, 110.3791378681881);
            googleMap.addMarker(new MarkerOptions().position(masjidHidayah).title("Masjid Hidayah"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(masjidHidayah, 15));

            LatLng masjidDarulHusna = new LatLng(-7.793251244432667, 110.38061538017364);
            googleMap.addMarker(new MarkerOptions().position(masjidDarulHusna).title("Masjid Darul Husna"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(masjidDarulHusna));

            LatLng masjidAlAmna = new LatLng(-7.793734336598871, 110.37560207955647);
            googleMap.addMarker(new MarkerOptions().position(masjidAlAmna).title("Masjid Al-Amna"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(masjidAlAmna));

            LatLng masjidMaruf = new LatLng(-7.794120078349114, 110.37732629235752);
            googleMap.addMarker(new MarkerOptions().position(masjidMaruf).title("Masjid Al-Ma'ruf Ronodigdayan"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(masjidMaruf));

            LatLng masjidMustaqim = new LatLng(-7.793872101550484, 110.37960670283633);
            googleMap.addMarker(new MarkerOptions().position(masjidMustaqim).title("Masjid Mustaqim"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(masjidMustaqim));

            // My location button
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            mMap.setMyLocationEnabled(true);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}