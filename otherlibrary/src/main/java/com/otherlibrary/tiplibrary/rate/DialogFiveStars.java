package com.otherlibrary.tiplibrary.rate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.otherlibrary.tiplibrary.R;

public class DialogFiveStars extends Dialog {

    private Context context;
    SharedPreferences sharedPrefs;
    private Button btnOk, btnNotnow;
    private RatingBar mBar;

    private String supportEmail;

    private int upperBound = 2;
    private static final String KEY_IS_RATE = "key_is_rate";
    private boolean isRateAppTemp = false;

    public DialogFiveStars(Context context, String supportEmail) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rate_data);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.context = context;
        this.supportEmail = supportEmail;
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        initDialog();
    }

    private void initDialog() {
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnNotnow = (Button) findViewById(R.id.btn_not_now);
        mBar = (RatingBar) findViewById(R.id.ratingBar);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRateAppTemp) {
                    if (mBar.getRating() > upperBound) {
                        openMarket();
                        notShowDialogWhenUserRateHigh();
                    } else {
//                        sendEmail();
                        notShowDialogWhenUserRateHigh();
                    }
                }
                Toast.makeText(context, "please rate 5 stars", Toast.LENGTH_SHORT).show();
            }
        });
        btnNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        mBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                isRateAppTemp = true;
                if (isRateAppTemp) {
                    if (ratingBar.getRating() > upperBound) {
                        dismiss();
                        openMarket();
                        notShowDialogWhenUserRateHigh();
                    } else {
                        dismiss();
//                        sendEmail();
                        notShowDialogWhenUserRateHigh();

                    }
                }
            }
        });
    }

    public boolean isRate() {
        return sharedPrefs.getBoolean(KEY_IS_RATE, false);
    }


    /**
     * update share not show rate when user rate this app > 3 *
     */
    private void notShowDialogWhenUserRateHigh() {
        SharedPreferences shared = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_IS_RATE, true);
        editor.apply();
        ((Activity) context).finish();
    }

    private void openMarket() {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void sendEmail() {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Report (" + context.getPackageName() + ")");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Send mail Report App !"));
    }


}
