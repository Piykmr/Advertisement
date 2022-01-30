package com.example.advertisment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Admin extends AppCompatActivity {
    public static int flag = 0;
    VideoView videoView;
    EditText editTextDesign, editTextPrice;
    ImageView imageViewBag, imageViewButton;
    TextView textViewImage;
    ProgressDialog progressDialog ;
    private Uri imageUri;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("BagDetails");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("BagDetails");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        videoView = findViewById(R.id.videoView);
        editTextDesign = findViewById(R.id.editTextDesign);
        editTextPrice = findViewById(R.id.editTextPrice);
        imageViewBag = findViewById(R.id.imageViewBag);
        imageViewButton = findViewById(R.id.imageButton);
        textViewImage = findViewById(R.id.TextViewImage);
        progressDialog=new ProgressDialog(Admin.this);
        if (flag == 0) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout2, new AdminLogin());
            ft.commit();

        }

       textViewImage.setOnClickListener(v -> {
           Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(openGallary, 1000);
       });
        imageViewButton.setOnClickListener(v -> {
            String design = editTextDesign.getText().toString();
            String price = editTextPrice.getText().toString();
            if(imageUri!=null && design!=null && price!=null)
            {
                uploadFirebase();
            }
            else
            {
                Toast.makeText(Admin.this,"Please Select Bag Image",Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data.getData();
                imageViewBag.setImageURI(imageUri);
            }
        }
    }


    private void uploadFirebase()
    {
        if (imageUri != null)
        {
            progressDialog.setTitle("Data is Uploading...");
            progressDialog.show();
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + " . " + getFileExtension(imageUri));
            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
            {
                String design = editTextDesign.getText().toString().trim();
                String price = editTextPrice.getText().toString().trim();
                progressDialog.dismiss();
                Toast.makeText(Admin.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                @SuppressWarnings("VisibleForTests")
                BagDetails bagDetails = new BagDetails(design, price, taskSnapshot.getUploadSessionUri().toString());
                String uploadId = root.push().getKey();
                root.child(uploadId).setValue(bagDetails);
            });
        }
        else
        {
            Toast.makeText(Admin.this, "Please Select First", Toast.LENGTH_LONG).show();
        }
    }



    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}