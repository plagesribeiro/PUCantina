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
public class Carrinho extends Fragment {

    public Carrinho() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrinho, container, false);
        // Inflate the layout for this fragment
        return root;
    }
}
