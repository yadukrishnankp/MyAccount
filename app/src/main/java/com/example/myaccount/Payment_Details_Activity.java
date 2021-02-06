package com.example.myaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Payment_Details_Activity extends AppCompatActivity {
    String ptype, uid, bid;
    EditText amount, description;
    ImageView upload;
    String currentDate,currentTime,month;
    Button insert;

    //
    String Storage_Path = "Payment_Details";
    // Root Database Name for Firebase Database.
    String Database_Path = "Payment_Details";

    // Creating URI.
    Uri FilePathUri;
    public FirebaseDatabase mDatabase;
    private DatabaseReference mrefeReference;
    String newKey;
    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    private FirebaseAuth mAuth;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__details_);
        ptype = getIntent().getExtras().getString("ptype");
        uid = getIntent().getExtras().getString("uid");
        bid = getIntent().getExtras().getString("bid");
        Log.e(getClass().getSimpleName(), "type=" + ptype);
        Log.e(getClass().getSimpleName(), "uid=" + currentDate);
        Log.e(getClass().getSimpleName(), "bid=" + month);
        mAuth = FirebaseAuth.getInstance();

        amount = findViewById(R.id.amount_Pd);
        description = findViewById(R.id.description_PD);
        upload = findViewById(R.id.imageview_PD);
        insert = findViewById(R.id.submit_PD);

        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(Payment_Details_Activity.this);
        // Adding click listener to Choose image .
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling imageupload method with firebase insert
                UploadImageFileToFirebaseStorage();
//                Log.e("qqq", "=" + loc);
                insert("1", "ijmum");

            }
        });


    }

    //Image selecting function
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                upload.setImageBitmap(bitmap);

                // After selecting image change choose button above text.


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            //  StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            final StorageReference storageReference2nd = storageReference.child("images/" + UUID.randomUUID().toString() + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // StorageReference httpsReference = storage.getReferenceFromUrl("images/"+ UUID.randomUUID().toString()+System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            UploadTask uploadTask = storageReference2nd.putFile(FilePathUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.e("bbbb", "=" + url);
                            final String ImageUploadId = databaseReference.push().getKey();
                            //calling firebase insert method
                            insert(ImageUploadId, url);

                        }
                    });
                    // Hiding the progressDialog after done uploading.
                    progressDialog.dismiss();
                    // Showing toast message after done uploading.
                    Toast.makeText(getApplicationContext(), " Uploaded Successfully ", Toast.LENGTH_LONG).show();

                    // Adding image upload id s child element into databaseReference.
                    //databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                }
            })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(Payment_Details_Activity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle(" Uploading...");
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");

                        }
                    });
        } else {

            Toast.makeText(Payment_Details_Activity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

    }

    private String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void insert(String imageUploadId, String url) {

        if (ptype.equals("earnings"))
        {
            if (validation() == true) {
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                month=currentDate.substring(3);
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                mrefeReference = mDatabase.getReference("Earning_tbl");
                String newKey = mrefeReference.push().getKey();
                DatabaseReference refs = FirebaseDatabase.getInstance().getReference("Earning_tbl").child(newKey);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("earnid", newKey);
                hashMap.put("amount", amount.getText().toString());
                hashMap.put("description", description.getText().toString());
                hashMap.put("imageurl", url);
                hashMap.put("uid", uid);
                hashMap.put("bid",bid);
                hashMap.put("date",currentDate);
                hashMap.put("time",currentTime);
                hashMap.put("month",month);
                refs.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), Business_Activity.class);
                            i.putExtra("uid", uid);
                            i.putExtra("bid",bid);
                            startActivity(i);
                            Log.e("Msg", "Success");
                        } else {
                            Log.e("Msg", "Failed");

                        }
                    }
                });

            }
        }
        else if (ptype.equals("expense"))
        {
            if (validation() == true) {
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                month=currentDate.substring(3);
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                mrefeReference = mDatabase.getReference("expense_tbl");
                String newKey = mrefeReference.push().getKey();
                DatabaseReference refs = FirebaseDatabase.getInstance().getReference("expense_tbl").child(newKey);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("expenseid", newKey);
                hashMap.put("amount", amount.getText().toString());
                hashMap.put("description", description.getText().toString());
                hashMap.put("imageurl", url);
                hashMap.put("uid", uid);
                hashMap.put("bid",bid);
                hashMap.put("date",currentDate);
                hashMap.put("time",currentTime);
                hashMap.put("month",month);
                refs.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), Business_Activity.class);
                            i.putExtra("uid", uid);
                            i.putExtra("bid",bid);
                            startActivity(i);
                            Log.e("Msg", "Success");
                        } else {
                            Log.e("Msg", "Failed");

                        }
                    }
                });

            }
        }
    }

    private boolean validation() {
        boolean val=true;
        if (amount.getText().toString().length()==0)
        {
            Toast.makeText(this, "enter the amount", Toast.LENGTH_SHORT).show();
            amount.requestFocus();
            val=false;
        }
        else if (description.getText().toString().length()==0)
        {
            Toast.makeText(this, "enter starting date", Toast.LENGTH_SHORT).show();
            description.requestFocus();
            val=false;
        }

        return  val;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Business_Activity.class);
        i.putExtra("uid",uid);
        i.putExtra("bid",bid);
        startActivity(i);
    }
}
