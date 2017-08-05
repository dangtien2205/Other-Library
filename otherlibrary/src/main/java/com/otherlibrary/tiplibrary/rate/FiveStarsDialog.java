package com.otherlibrary.tiplibrary.rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.otherlibrary.tiplibrary.R;

public class FiveStarsDialog implements DialogInterface.OnClickListener {

    private final static String DEFAULT_TITLE = "Rate this app";
    private final static String DEFAULT_TEXT = "How much do you love our app?";
    private final static String DEFAULT_POSITIVE = "Ok";
    private final static String DEFAULT_NEGATIVE = "Not Now";
    private static final String KEY_IS_RATE = "key_is_rate";
    private final Context context;
    SharedPreferences sharedPrefs;
    private String supportEmail;
    private TextView contentTextView;
    private RatingBar ratingBar;
    private String title = null;
    private String rateText = null;
    private AlertDialog alertDialog;
    private View dialogView;
    private int upperBound = 2;

    private boolean isRateAppTemp = false;

    public FiveStarsDialog(Context context, String supportEmail) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        this.supportEmail = supportEmail;
    }

    /**
     * this method just to check have user rate app yet ?
     *
     * @return
     */
    public boolean isRate() {
        return sharedPrefs.getBoolean(KEY_IS_RATE, false);
    }


    private void build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.stars, null);
        String titleToAdd = (title == null) ? DEFAULT_TITLE : title;
        String textToAdd = (rateText == null) ? DEFAULT_TEXT : rateText;
        contentTextView = (TextView) dialogView.findViewById(R.id.text_content);
        contentTextView.setText(textToAdd);
        ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                isRateAppTemp = true;
                if (isRateAppTemp) {
                    if (ratingBar.getRating() > upperBound) {
                        alertDialog.dismiss();
                        openMarket();
                        notShowDialogWhenUserRateHigh();
                    } else {
                        alertDialog.dismiss();
                        sendEmail();
                        notShowDialogWhenUserRateHigh();
                    }
                }
            }
        });

        alertDialog = builder.setTitle(titleToAdd)
                .setView(dialogView)
                .setNegativeButton(DEFAULT_NEGATIVE, this)
                .setPositiveButton(DEFAULT_POSITIVE, this)
                .create();
    }


    /**
     * update share not show rate when user rate this app > 3 *
     */
    private void notShowDialogWhenUserRateHigh() {
        SharedPreferences shared = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_IS_RATE, true);
        editor.apply();
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

    /**
     * show dialog rate
     */
    public void showDialogRate() {
        build();
        alertDialog.show();

    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            if (isRateAppTemp) {
                if (ratingBar.getRating() > upperBound) {
                    openMarket();
                    notShowDialogWhenUserRateHigh();
                } else {
                    sendEmail();
                    notShowDialogWhenUserRateHigh();
                }
            }
        }
        if (i == DialogInterface.BUTTON_NEGATIVE) {
            ((Activity) context).finish();

        }
        alertDialog.hide();
    }


}
