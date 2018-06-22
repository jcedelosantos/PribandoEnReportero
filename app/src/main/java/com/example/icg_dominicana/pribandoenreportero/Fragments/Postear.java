package com.example.icg_dominicana.pribandoenreportero.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.icg_dominicana.pribandoenreportero.Objects.Report;
import com.example.icg_dominicana.pribandoenreportero.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class Postear extends Fragment implements View.OnClickListener {

    // private MapView mapView;
    //  private GoogleMap gMap;
    private View rootView;
    List<Address> addresses;

    private EditText editText_description;
    private TextView textView_latitud;
    private TextView textView_longitud;
    private ImageView imageView;
    private ImageButton imageButton_Camara;
    private Button buttonReportar;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageRef;
    private DatabaseReference dbRef;

    private LocationManager locationManager;
    private Location lastLocation;
    private LocationListener locationListener;
    FusedLocationProviderClient mFusedLocationClient;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 71;

    double lat;
    double longitud;

    private ProgressDialog myprogressdialog;

    private static final int GALLERY_INTENT = 1;
    private static final int RESULT_OK = -1;



    public Postear() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate( R.layout.fragment_postear, container, false );

        editText_description = (EditText) rootView.findViewById( R.id.id_editTextReporte );
        imageView = (ImageView) rootView.findViewById( R.id.id_imageView );
        imageButton_Camara = (ImageButton) rootView.findViewById( R.id.id_buttonCamara );
        buttonReportar = (Button) rootView.findViewById( R.id.id_buttonReportar );

        locationManager = (LocationManager)
                getActivity().getSystemService( Context.LOCATION_SERVICE );
//       Location lastLocation = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            getLocation();
        } else {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            }
        }


        long minTime = 1000; // in milliseconds
        float minDistance = 0.f; //in meters

////        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,
//                minTime, minDistance, locationListener);

//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null){
//                            final TextView longitud = rootView.findViewById( R.id.id_textView_longitud );
//
//                        }
//                    }
//                } );

//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                textView_latitud.append( "\n"+location.getLongitude() );
//                textView_longitud.append( "\n"+location.getLongitude() );
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//                Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
//                startActivity( intent );
//
//            }
//        };
//        locationManager.requestLocationUpdates( "gps", 5000, 0, locationListener );

        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference();
        dbRef = FirebaseDatabase.getInstance().getReference();

        imageButton_Camara.setOnClickListener( this );
        buttonReportar.setOnClickListener( this );

        myprogressdialog = new ProgressDialog( getContext() );

        return rootView;
    }

   //  mDatabaseReference.child("reportando").push().setValue( new Report(   user.getDisplayName();

    @SuppressLint("MissingPermission")
    public void getLocation(){
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            lat = location.getLatitude();
                            longitud = location.getLongitude();


                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent( Intent.ACTION_PICK );
        intent.setType( "image/*" );
        startActivityForResult( intent,GALLERY_INTENT );

    }

    Uri uri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user;

        final Report report;

         uri = data.getData();
        user = mFirebaseAuth.getCurrentUser();

        Glide.with( Postear.this )
                .load( uri )
                .fitCenter()
                .centerCrop()
                .into( imageView );


//        String ltd,lgt;
//        ltd = "Lat:"+String.valueOf(lastLocation.getLatitude());
//        lgt = "Lgt:"+String.valueOf( lastLocation.getLongitude());

//        textView_latitud.setText( ltd );
//        textView_longitud.setText( lgt );

        final String reportDescription  =  editText_description.getText().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reportDetails = database.getReference();
        buttonReportar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subirfoto(reportDescription);
               //
            }
        } );

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){


  //          String reportDescription  =  editText_description.getText().toString();



// reportDetails.setValue( textView_latitud );
//          reportDetails.setValue( textView_longitud );
           // reportDetails.setValue( reportDescription );
           // reportDetails.child("reportando").push().setValue( new Report(user.getDisplayName()));

            editText_description.setText( "" );


//            textView_latitud.setText( "" );
//            textView_longitud.setText( "" );
        }
    }


//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        double latitud =  location.getLatitude();
//        double longitud = location.getLongitude();
//
//    }

    UploadTask uploadTask;
    public void subirfoto(final String desc){

        myprogressdialog.setTitle( "Subiendo" );
        myprogressdialog.setMessage( "Publicando reporte" );
        myprogressdialog.setCancelable( false );
        myprogressdialog.show();

        final StorageReference fileDestination = storageRef.child("photos").child( uri.getLastPathSegment());
        uploadTask = fileDestination.putFile(uri);

        fileDestination.putFile( uri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                myprogressdialog.dismiss();

                Toast.makeText( getContext(), "Reporte Subido correctamente", Toast.LENGTH_LONG ).show();
            }
        } );

        Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {

                    throw task.getException();
                }else {


                }

                // Continue with the task to get the download URL
                return fileDestination.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    fileDestination.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Report report = new Report(uri.toString(),desc,lat,longitud);
                            dbRef.child( "reportando").push().setValue( report );

                            myprogressdialog.dismiss();
                        }
                    } );

//                    final  Uri downloadUri = task.getResult();


                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getContext(), "You can't Post without your location", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}