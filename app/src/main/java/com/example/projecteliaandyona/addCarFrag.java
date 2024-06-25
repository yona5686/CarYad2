package com.example.projecteliaandyona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addCarFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addCarFrag extends Fragment {
    EditText cName, cYear, cPrice, cPhone;
    Button addCarBtn;
    FirebaseDatabase database;
    DatabaseReference carDbRef;
    ImageView cImg;
    ActivityResultLauncher<Intent> resultLauncher;
    Uri imageUri;
    String uid;
    FirebaseUser currentFirebaseUser;
    private boolean imgPicked = false;


    public addCarFrag() {
        // Required empty public constructor
    }

    public static addCarFrag newInstance(String param1, String param2) {
        addCarFrag fragment = new addCarFrag();
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
        return inflater.inflate(R.layout.fragment_add_car, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        cName = view.findViewById(R.id.etCarName);
        cYear = view.findViewById(R.id.etCarYear);
        cPrice = view.findViewById(R.id.etCarPrice);
        cPhone = view.findViewById(R.id.etPhone);

        addCarBtn = view.findViewById(R.id.btnAddCar);

        cImg = view.findViewById(R.id.imageViewCar);
        registerResult();

        cImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });



        database = FirebaseDatabase.getInstance();
        carDbRef = database.getReference().child("Cars");

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, year, price, phone;

                name = String.valueOf(cName.getText());
                year = String.valueOf(cYear.getText());
                price = String.valueOf(cPrice.getText());
                phone = String.valueOf(cPhone.getText());



                try{
                    int test = Integer.parseInt(year);//Checks if the year is integer
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Year must be a whole number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(year) < 1990 || Integer.parseInt(year) > 2024) {//Checks if the year between those values
                    Toast.makeText(getActivity(), "Year must be a between 1990 to 2024", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    int test = Integer.parseInt(price);//Checks if the price is integer
                    int test2 = 10/test;//Checks if the price is above 0
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Price must be a whole number above 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(year) || TextUtils.isEmpty(price) || TextUtils.isEmpty(phone)){
                    Toast.makeText(getActivity(), "Missing fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentFirebaseUser != null) {//avoids null refrence
                    uid = currentFirebaseUser.getUid();
                }




                try{//checks that img has been picked for verification
                    if(imgPicked){
                        addCar(name, year, price, phone);//add the car
                    }else{
                        Toast.makeText(getActivity(), "Must select picture", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Must select picture", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addCar(String name, String year, String price, String phone){
        Car car = new Car(uid, name, year, price, phone);//create new car object

        carDbRef.push().setValue(car);//add to the data base
        Toast.makeText(getActivity(), "Car added", Toast.LENGTH_SHORT).show();

    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult res) {
                        try{
                            imageUri = res.getData().getData();//gets the img picked by the user
                            cImg.setImageURI(imageUri);//apply the image to the img view
                            Toast.makeText(getActivity(), "Verified", Toast.LENGTH_SHORT).show();
                            imgPicked = true;//updates the state of the img pick
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

}