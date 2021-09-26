package com.example.mad_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WishListViewAdapter extends RecyclerView.Adapter<WishListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<WishListModel> wishModelList;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    public WishListViewAdapter(Context context, ArrayList<WishListModel> wishModelList) {
        this.context = context;
        this.wishModelList = wishModelList;

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(wishModelList.get(position).getName());
        holder.price.setText(wishModelList.get(position).getPrice());
        holder.description.setText(wishModelList.get(position).getDescription());
        holder.date.setText(wishModelList.get(position).getCurrentDate());
        holder.time.setText(wishModelList.get(position).getCurrentTime());

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("WishList").document(fAuth.getCurrentUser().getUid()).collection("CurrentUser")
                        .document(wishModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    wishModelList.remove(wishModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Error Occured"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return wishModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,description,date,time;
        Button deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.wish_pName);
            price = itemView.findViewById(R.id.wish_pPrice);
            description = itemView.findViewById(R.id.wish_pDescription);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            deleteItem = itemView.findViewById(R.id.trash);
        }
    }
}
