package com.example.projecteliaandyona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projecteliaandyona.databinding.ActivityLoggedInBinding;
import com.example.projecteliaandyona.databinding.ActivityMainBinding;

public class LoggedIn extends AppCompatActivity {

    ActivityLoggedInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoggedInBinding.inflate(getLayoutInflater());// Use view binding to inflate the layout
        setContentView(binding.getRoot());// Set the content view to the root view of the layout

        replaceFrag(new allCarsFrag());//starts with the allcars fragment
        binding.botNav.getMenu().findItem(R.id.allCars).setChecked(true);//select the allCars option in the navbar

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.botNav.setOnItemSelectedListener(item -> {//manage the navigation bar

            if(item.getItemId() == R.id.myCars){
                replaceFrag(new myCarsFrag());
            } else if (item.getItemId() == R.id.allCars) {
                replaceFrag(new allCarsFrag());
            } else if (item.getItemId() == R.id.addCar) {
                replaceFrag(new addCarFrag());
            } else if (item.getItemId() == R.id.logout) {
                Intent intent = new Intent(LoggedIn.this, Login.class);
                startActivity(intent);
                finish();
            }

            return true;
        });
    }

    public void replaceFrag(Fragment frag) {//switch between fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, frag);
        fragmentTransaction.commit();
    }
}