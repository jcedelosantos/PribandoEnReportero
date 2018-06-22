package com.example.icg_dominicana.pribandoenreportero.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icg_dominicana.pribandoenreportero.Adapters.MyAdapter;
import com.example.icg_dominicana.pribandoenreportero.Objects.Report;
import com.example.icg_dominicana.pribandoenreportero.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyReport extends Fragment {

       private RecyclerView myRecyclerView;
       private RecyclerView.Adapter myAdapter;
//    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<Report> myReportsArryList = new ArrayList<>() ;

   // private RecyclerView myRecyclerView;

    //MyAdapter myAdapter;

    public MyReport() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_report, container, false );

        myRecyclerView =  (RecyclerView) view.findViewById( R.id.id_recyclerView );
        myRecyclerView.setLayoutManager(  new LinearLayoutManager( getContext()) );


        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = database.getReference();

  //      myReportsArryList.add( new Report( "https://lh4.googleusercontent.com/-7E2a2VWZStY/AAAAAAAAAAI/AAAAAAAAAdo/Alta6gri1GY/s96-c/photo.jpg","comentario"
    //    ,18.486058,-69.931212) );
       // mDatabaseReference.child("reportando").push().setValue( new Report( user.getDisplayName(); ) )
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user;

        user = mFirebaseAuth.getCurrentUser();

        mDatabaseReference.child( "reportando" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myReportsArryList.clear();
                for(DataSnapshot snapshot:
                    dataSnapshot.getChildren()){
                    Report report = snapshot.getValue(Report.class);
                    myReportsArryList.add(report);
                }
                Log.d("saber",""+myReportsArryList);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        myAdapter = new MyAdapter(myReportsArryList,getContext());

        myRecyclerView.setAdapter( myAdapter );
        return  view;
    }

}
