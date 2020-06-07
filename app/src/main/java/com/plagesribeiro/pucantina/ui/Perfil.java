package com.plagesribeiro.pucantina.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plagesribeiro.pucantina.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {

    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        return root;
    }
}
