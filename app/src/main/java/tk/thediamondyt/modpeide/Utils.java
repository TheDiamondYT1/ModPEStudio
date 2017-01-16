package tk.thediamondyt.modpeide;

import android.support.v7.app.AlertDialog;

import android.app.Activity;
import android.util.Log;

public class Utils {
    
    private static final String TAG = "ModPE IDE";
    
    public static void error(Activity context, String error) {
        Log.e(TAG, "There was an error: " + error);
        
        new AlertDialog.Builder(context)
            .setTitle("There was an error").setMessage(error).create().show();
    }
}
