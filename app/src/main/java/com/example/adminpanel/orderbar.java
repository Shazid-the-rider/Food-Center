package com.example.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class orderbar extends AppCompatActivity {
    RecyclerView recyleview;
    DatabaseReference databaseReference;
    ArrayList<orderdibo> list;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderbar);
        CardView c=findViewById(R.id.cardView);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(orderbar.this, admin_home.class));
            }
        });
        recyleview = findViewById(R.id.recycle);
        list=new ArrayList<>();
        recyleview.setHasFixedSize(true);
        recyleview.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("order");
       myAdapter = new MyAdapter(this, list);
        recyleview.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<orderdibo> orderList = new ArrayList<>();
                for(DataSnapshot db :snapshot.getChildren()){
                    for(DataSnapshot ds:db.getChildren()){
                        DataSnapshot dx = ds.child("items");
                        String name = dx.child("name").getValue(String.class);
                        String price = dx.child("price").getValue(String.class);
                        String profileImage = dx.child("profileimage").getValue(String.class);
                        DataSnapshot dx1 = ds.child("info");
                        String phone=dx1.child("phone").getValue(String.class);
                        String address=dx1.child("address").getValue(String.class);
                        // String name,String address, String phone, String price, String profileimage
                        orderdibo d = new orderdibo(name,address,phone,price, profileImage);

                        if (d != null) {
                            list.add(d);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}