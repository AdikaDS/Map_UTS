package com.example.mapsislamiclite;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

public class SettingFragment extends Fragment {

    private GoogleMap mMap;
    private MapsFragment mapsFragment = new MapsFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Button untuk mengganti bahasa
        view.findViewById(R.id.change_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
            }
        });

        // Button untuk mengganti style Normal
        view.findViewById(R.id.change_style_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}