package com.otherapp.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.otherlibrary.tiplibrary.IntroductionActivity;
import com.otherlibrary.tiplibrary.OtherMethor;
import com.otherlibrary.tiplibrary.rate.DialogFiveStars;
import com.otherlibrary.tiplibrary.rate.DialogFiveStarsNotFinish;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {
    private DialogFiveStars fiveStarsDialog;
    private DialogFiveStarsNotFinish dialogFiveStarsNotFinish;
    private int numBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);

        OtherMethor.checkPermission(this);
    }

    private void init() {
        fiveStarsDialog = new DialogFiveStars(this, "");
        dialogFiveStarsNotFinish = new DialogFiveStarsNotFinish(this, "");
    }

    @OnLongClick(R.id.btn_3dtouch)
    public boolean submit1(View view) {
        OtherMethor.click3DTouch(this, 300, new OtherMethor.OnClick() {
            @Override
            public void click3DTouch() {
                Toast.makeText(MainActivity.this,"3D touch",Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

    @OnClick({R.id.btn_rate, R.id.btn_intro})
    public void submit(View view) {
        switch (view.getId()){
            case R.id.btn_rate:
                dialogFiveStarsNotFinish.show();
                break;
            case R.id.btn_intro:
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(R.drawable.bg1);
                arrayList.add(R.drawable.bg2);
                arrayList.add(R.drawable.bg3);
                arrayList.add(R.drawable.bg4);
                arrayList.add(R.drawable.bg5);
                IntroductionActivity.startIntroduction(this,arrayList);
                break;
            case R.id.btn_3dtouch:
                OtherMethor.click3DTouch(this, 300, new OtherMethor.OnClick() {
                    @Override
                    public void click3DTouch() {
                        Toast.makeText(MainActivity.this,"3D touch",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        numBack = 1;
    }

    @Override
    public void onBackPressed() {
        if (numBack == 1) {
            numBack--;
            Toast.makeText(this, "Double click to quit", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!fiveStarsDialog.isRate()) {
            fiveStarsDialog.show();
        } else {
            super.onBackPressed();
        }
    }
}
