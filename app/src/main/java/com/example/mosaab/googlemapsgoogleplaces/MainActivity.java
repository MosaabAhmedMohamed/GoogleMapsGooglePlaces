package com.example.mosaab.googlemapsgoogleplaces;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="MainAcivity";
    public static final int Error_Dialog_request =9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isServiceSDk())
        {
            init();
        }

    }

    private void init()
    {
        Intent Reg_intent =new Intent(MainActivity.this,LocationActivity.class);
        startActivity(Reg_intent);
        finish();
    }

    public boolean isServiceSDk()
    {
        Log.d(TAG, "isServiceOk:checking google services version ");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS)
        {
            //everything is fine and the user can make map requests

            Log.d(TAG, "isServiceOk: Google play serviecs is working ");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //an error occured but we can fix it

            Log.d(TAG, "isServiceOk: an error occured but we can fix it");

           Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,Error_Dialog_request);
             dialog.show();
        }
        else
        {
            Toast.makeText(this, "you cant map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
