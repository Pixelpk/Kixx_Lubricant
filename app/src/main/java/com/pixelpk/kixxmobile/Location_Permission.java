package com.pixelpk.kixxmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pixelpk.kixxmobile.Salesman.HomeScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.Splash;
import com.pixelpk.kixxmobile.User.TutorialScreen;

import static com.pixelpk.kixxmobile.User.Fragments.MapsFragment.MY_PERMISSIONS_REQUEST_LOCATION;

public class Location_Permission extends AppCompatActivity
{
    Button permission_btn,cancel_btn;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;//    TextView test;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_permission);

        initializeview();


    }

    private void initializeview()
    {
        permission_btn = findViewById(R.id.location_permission_btn);
        cancel_btn     = findViewById(R.id.location_cancel_btn);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void ask_permission(View view)
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED)
        {
            //You can show permission rationale if shouldShowRequestPermissionRationale() returns true.
            //I will skip it for this demo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    displayNeverAskAgainDialog();
                }

                else
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }

        }

        if (ContextCompat.checkSelfPermission(Location_Permission.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            editor.putString(Shared.permission_location,"1").apply();
        }
    }

    public void close_app(View view)
    {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(Location_Permission.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {
                        Intent mainIntent = new Intent(Location_Permission.this, HomeScreen.class);
                        startActivity(mainIntent);
                        finish();
                    }

                }

                else
                {
                    PermissionUtils.setShouldShowStatus(this, Manifest.permission.ACCESS_FINE_LOCATION);
//                    finish();
                }

                return;
            }}}

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //Note: I have placed this code in onResume for demostration purpose. Be careful when you use it in
//        // production code
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
//                .PERMISSION_GRANTED)
//        {
//            //You can show permission rationale if shouldShowRequestPermissionRationale() returns true.
//            //I will skip it for this demo
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            {
//                if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.ACCESS_FINE_LOCATION))
//                {
//                    displayNeverAskAgainDialog();
//                }
//
//                else
//                {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            MY_PERMISSIONS_REQUEST_LOCATION);
//                }
//            }
//
//        }
//    }

    private void displayNeverAskAgainDialog()

    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Location Access is required for the app to work. Please provide location Access. Please permit the permission through"
                + "Settings screen.\n\nSelect Permissions -> Enable permission");
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}