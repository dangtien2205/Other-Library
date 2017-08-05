package com.otherlibrary.tiplibrary;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.List;

public class OtherMethor {
    public static Typeface getTypeface(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), name);
    }

    public static void runVibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50L);
    }

    public static void privacyPolicy(Context context,String pack) {
        Uri uri = Uri.parse(pack);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static boolean checkService(Context context,String str) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.getClassName().toUpperCase().equals(str.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static void sentEmail(Context context,String email){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void checkPermission(final Context context) {
        String manufacturerXiaomi = "xiaomi";
        String manufacturerHuawei = "huawei";
        if (manufacturerXiaomi.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            if (!AppPref.getPref(context).getBoolean(AppPref.START_ON, false))
                new AlertDialog.Builder(context)
                        .setTitle("Notification")
                        .setMessage("Device Xiaomi need auto start permission ,you can turn on this permission ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                                context.startActivity(intent);
                                AppPref.getPref(context).putBoolean(AppPref.START_ON, true);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
        }
        if (manufacturerHuawei.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            if (!AppPref.getPref(context).getBoolean(AppPref.START_ON, false))
                new AlertDialog.Builder(context)
                        .setTitle("Notification")
                        .setMessage("Device Huawei need auto start permission ,you can turn on this permission ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
                                context.startActivity(intent);
                                AppPref.getPref(context).putBoolean(AppPref.START_ON, true);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
        }
    }

    public static void click3DTouch(final Context context, int time, final OnClick onClick){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runVibrate(context);
                onClick.click3DTouch();
            }
        }, time);
    }

    public interface OnClick{
        void click3DTouch();
    }
}
