package com.plagesribeiro.pucantina.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plagesribeiro.pucantina.R;

public class Pedido extends Fragment {

    public Pedido() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedido, container, false);
        // Inflate the layout for this fragment
        return root;
    }
}