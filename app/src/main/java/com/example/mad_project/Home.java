package com.example.mad_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    String userId, userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        productArrayList = new ArrayList<Product>();
        myAdapter = new MyAdapter(Home.this,productArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        }

    private void EventChangeListener() {

        db.collection("products").orderBy("productName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();

                            }

                            Log.e("Firestore error",error.getMessage());
                                return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){

                                productArrayList.add(dc.getDocument().toObject(Product.class));
                            }

                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                        }

                    }
                });
    }

    public void profile(View view) {
        Intent intent1 = new Intent(Home.this, profile.class);
        startActivity(intent1);
    }

    public void AddProd(View view) {
        Intent intent2 = new Intent(Home.this, Add_Products.class);
        startActivity(intent2);
    }

    public void Home2(View view) {
        Intent intent1 = new Intent(Home.this, Home.class);
        //finish();
        startActivity(intent1);
        finish();
    }

    public void ProfileIcon(View view) {
        Intent intent2 = new Intent(Home.this, profile.class);
        startActivity(intent2);
    }

    public void homeWishlist(View view) {
        Intent intent3 = new Intent(Home.this, whishlist.class);
        startActivity(intent3);
    }
}