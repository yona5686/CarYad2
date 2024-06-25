package com.example.projecteliaandyona;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class myCarsFrag extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Car> list;
    FirebaseUser currentFirebaseUser;
    String uid;


    public myCarsFrag() {
        // Required empty public constructor
    }


    public static myCarsFrag newInstance(String param1, String param2) {
        myCarsFrag fragment = new myCarsFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();//create the current user refrence
        recyclerView = view.findViewById(R.id.MycarsListRV);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cars");//get refrence of the db
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (currentFirebaseUser != null) {
            uid = currentFirebaseUser.getUid();
            Log.d("is in",uid);
        } else {
            Log.d("is not signed in","");
        }
        list = new ArrayList<>();
        myAdapter = new MyAdapter(requireContext(), list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {//
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){//get data from db
                    Car car = dataSnapshot.getValue(Car.class);//get the data from the firebase in the form of a car class

                    if(Objects.equals(car.getOwner(), uid)){//checks if the car owner id is the same as the current user's id
                        list.add(car);//add the car to the list which applied to the recycler view
                    }

                }
                myAdapter.notifyDataSetChanged();//render the adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}