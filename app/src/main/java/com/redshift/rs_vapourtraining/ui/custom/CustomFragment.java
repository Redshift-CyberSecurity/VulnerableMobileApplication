package com.redshift.rs_vapourtraining.ui.custom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.redshift.rs_vapourtraining.R;

public class CustomFragment extends Fragment {

    TextView cText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_custom, container, false);
        cText = root.findViewById(R.id.text_custom);

        cText.setText("Custom Fragment");
        return root;
    }
}
