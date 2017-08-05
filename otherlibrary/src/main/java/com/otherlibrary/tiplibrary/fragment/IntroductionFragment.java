package com.otherlibrary.tiplibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.otherlibrary.tiplibrary.R;

/**
 * Created by TienBi on 05/08/2017.
 */

public class IntroductionFragment extends Fragment {
    ImageView imageBG;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int stt=getArguments().getInt("IMAGE");
        imageBG=(ImageView) view.findViewById(R.id.image_bg);
        imageBG.setImageResource(stt);
        super.onViewCreated(view, savedInstanceState);
    }
}
