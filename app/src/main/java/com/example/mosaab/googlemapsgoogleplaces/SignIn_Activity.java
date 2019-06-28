package com.example.mosaab.googlemapsgoogleplaces;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn_Activity extends AppCompatActivity {

    private Button login_Bu,need_account_Bu;
    private EditText email_ET,password_ET;



    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        Init_UI();

    }

    private void Init_UI() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        email_ET = findViewById(R.id.email_loging_ET);
        password_ET = findViewById(R.id.password_login_ET);
        login_Bu = findViewById(R.id.login_BU);
        need_account_Bu = findViewById(R.id.need_account_BU);

        login_Bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_Process();
            }
        });

        need_account_Bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent register_intent = new Intent(SignIn_Activity.this,Register_Activity.class);
                startActivity(register_intent);
                finish();
            }
        });

    }

    private void Login_Process() {

        String email = email_ET.getText().toString();
        String password = password_ET.getText().toString();

        if(!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password))
        {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {

                        String current_id = firebaseAuth.getCurrentUser().getUid();
                                SendTO_Map();

                    }
                    else
                    {
                        Toast.makeText(SignIn_Activity.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void SendTO_Map() {

        Intent Main_intent = new Intent(SignIn_Activity.this,MapActivity.class);
        startActivity(Main_intent);
        finish();
    }
}
