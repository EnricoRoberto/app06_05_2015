package com.example.xpmuser.app06_05_2015.ToastFuntion;

import android.content.Context;
import android.widget.Toast;

public class ToastReady {

    public void upLong(Context context, CharSequence charsequenze) {
        int durationToast = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, charsequenze, durationToast);
        toast.show();
     }
    
    public void upShort(Context context, CharSequence charsequenze) {
        int durationToast = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, charsequenze, durationToast);
        toast.show();
    }

        
}
