package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class whishlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whishlist);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutWish,new WishListFragment()).commit();
    }

    public void wishBack(View view) {
        Intent intent1 = new Intent(whishlist.this, Home.class);
        startActivity(intent1);
        finish();
    }

    public void homeWishlist(View view) {
        Intent intent1 = new Intent(whishlist.this, whishlist.class);
        startActivity(intent1);
    }

    public void ProfileIcon(View view) {
        Intent intent1 = new Intent(whishlist.this, profile.class);
        startActivity(intent1);
    }

    public void Home2(View view) {

    }
}