package com.example.adminpanel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class productup extends AppCompatActivity {

    TextInputEditText text1, text2, text3, text4;
    TextView txt1;
    CardView card1, card2, card3;
    ImageView Imageview,x;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference db;
    public static final int Image_Request=1;
    Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productup);

        text1 = findViewById(R.id.inputtext1);
        text2 = findViewById(R.id.inputtext2);
        text3 = findViewById(R.id.inputtext3);
        txt1 = findViewById(R.id.textView1);
        db= FirebaseDatabase.getInstance().getReference("food1");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        Imageview=findViewById(R.id.imageView);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();
            }
        });
        x=findViewById(R.id.imageView3);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(productup.this, admin_home.class));
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= text1.getText().toString().trim();
                String p=text2.getText().toString().trim();
                String q=text3.getText().toString().trim();
                String r=txt1.getText().toString().trim();
                if(s.isEmpty()){
                    text1.setError("Information Missing");
                    return;
                }
                if(p.isEmpty()){
                    text2.setError("Information Missing");
                    return;
                }
                if(q.isEmpty()){
                    text3.setError("Information Missing");
                    return;
                }
                else{
                    Map<String,String>container=new HashMap<>();
                    container.put("name",s);
                    container.put("price",p);
                    container.put("discount",q);
                    container.put("profileimage",r);
                    db.child("item").setValue(container).addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(productup.this, "Uploaded Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(productup.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        });

    }

    public void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Image_Request && resultCode==RESULT_OK && data!= null && data.getData()!= null){
            imageuri=data.getData();
            Glide.with(this)
                    .load(imageuri)
                    .into(Imageview);

        }
    }
    public void uploadtofirebase(){
        if(imageuri!=null)
        {
            String fileextension = getfileextension(imageuri);
            StorageReference filereference = storageReference.child("uploads/"+System.currentTimeMillis()+"."+fileextension);

            filereference.putFile(imageuri). addOnSuccessListener(taskSnapshot -> {
                filereference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();
                    txt1.setText(downloadUrl);
                    Toast.makeText(productup.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(productup.this, "Failed to get Download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(productup.this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
    public String getfileextension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

}
