package com.example.myapplication.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.View.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class FragmentMap extends Fragment{
    SupportMapFragment supportMapFragment;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView tvNameResidence;
    ImageView btBack;
    String addressResidence=null,nameResidence=null;

    private static final int REQUEST_CODE = 44;
    private static final String TAG = "FragmentMap";
    public static FragmentMap newInstance(String nameResidence) {

        Bundle args = new Bundle();
        args.putSerializable("nameResidence", nameResidence);
        FragmentMap fragment = new FragmentMap();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_map, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        nameResidence = (String) getArguments().getSerializable("nameResidence");
        tvNameResidence=view.findViewById(R.id.tvNameResidence);
        btBack=view.findViewById(R.id.btBack);
        tvNameResidence.setText(nameResidence);
        fetchLastLocation();
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.myMap);
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng s= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

                            MarkerOptions markerLocation= new MarkerOptions().position(s)
                                    .title("Vị trí hiện tại");
                            MarkerOptions markerResidence= new MarkerOptions().position(geocode(addressResidence))
                                    .title(nameResidence);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(s,15));
                            googleMap.addMarker(markerLocation);
                            googleMap.addMarker(markerResidence);
                            Polyline line = googleMap.addPolyline(new PolylineOptions()
                                    .add(s, geocode(addressResidence))
                                    .width(5)
                                    .color(Color.RED));
                        }
                    });
                }
                
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
    public LatLng geocode(String address){
        //bla bla
        return new LatLng(21.0296,105.8503);
    }
}
