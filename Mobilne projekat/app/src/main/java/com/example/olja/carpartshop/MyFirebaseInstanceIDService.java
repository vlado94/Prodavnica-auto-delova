package com.example.olja.carpartshop;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = getSharedPreferences("appData", MODE_PRIVATE).edit();
        editor.putString("firebaseToken", firebaseToken);
        editor.apply();
    }

}
