package com.example.mad_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class product_details extends AppCompatActivity {

    TextView name,price,description;
    Button addWishlist;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Product Product=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Product){
            Product = (Product) object;
        }


        name = findViewById(R.id.textView6);
        price = findViewById(R.id.textView7);
        description = findViewById(R.id.enter_product_description);

        if(Product != null){
            name.setText(Product.getProductName());
            price.setText(Product.getProductPrice());
            description.setText(Product.getProductDescription());
        }

        addWishlist = findViewById(R.id.add_wishlist_btn);
        addWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToWish();
            }
        });


    }

    private void addedToWish() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> wishListMap = new HashMap<>();

        wishListMap.put("Name",Product.getProductName());
        wishListMap.put("Price",Product.getProductPrice());
        wishListMap.put("Description",Product.getProductDescription());
        wishListMap.put("currentDate",saveCurrentDate);
        wishListMap.put("currentTime",saveCurrentTime);

        fStore.collection("WishList").document(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())
                .collection("CurrentUser").add(wishListMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(product_details.this, "Added To A WishList!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(product_details.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}