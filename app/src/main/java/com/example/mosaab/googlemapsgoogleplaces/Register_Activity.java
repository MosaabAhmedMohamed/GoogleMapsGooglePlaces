package com.example.mosaab.googlemapsgoogleplaces;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity {
    private EditText name_ET,email_ET,password_ET;
    private Button create_account_Bu,have_account_BU;
    private CircleImageView circleImageView;

    private final int  PICK_IMAGE = 1;
    private Uri image_uri = null;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        Init_UI();
    }

    private void Init_UI() {

        storageReference = FirebaseStorage.getInstance().getReference().child("images");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        name_ET = findViewById(R.id.name_TV);
        email_ET = findViewById(R.id.email_reg_TV);
        password_ET = findViewById(R.id.password_reg_TV);
        create_account_Bu = findViewById(R.id.create_account_BU);
        have_account_BU = findViewById(R.id.have_account_BU);
        circleImageView = findViewById(R.id.profile_image);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pick_Image();

            }
        });

        create_account_Bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (image_uri != null) {
                    Register_Process();
                }
                else {
                    Toast.makeText(Register_Activity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        have_account_BU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login_intent = new Intent(Register_Activity.this,SignIn_Activity.class);
                startActivity(login_intent);
                finish();
            }
        });
    }

    private void Register_Process()
    {
        final String name = name_ET.getText().toString();
        String email = email_ET.getText().toString();
        String passwprd = password_ET.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(passwprd))
        {
            firebaseAuth.createUserWithEmailAndPassword(email,passwprd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        final String user_id = firebaseAuth.getCurrentUser().getUid();

                        StorageReference user_profile = storageReference.child(user_id+".jpg");

                        user_profile.putFile(image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful())
                                {
                                    final String dwonload_url =  task.getResult().getUploadSessionUri().toString();



                                    Map<String,Object> user_Map = new HashMap<>();

                                    user_Map.put("name",name);
                                    user_Map.put("image",dwonload_url);


                                    firebaseFirestore.collection("Users").document(user_id).set(user_Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            SendTo_MAP();
                                        }
                                    });
                                }
                                else
                                {
                                    Toast.makeText(Register_Activity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(Register_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void SendTo_MAP() {

        Intent Map_Intent = new Intent(Register_Activity.this,MapActivity.class);
        startActivity(Map_Intent);
        finish();

    }

    private void Pick_Image() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE)
        {
            image_uri = data.getData();
            circleImageView.setImageURI(image_uri);
        }
    }
}
