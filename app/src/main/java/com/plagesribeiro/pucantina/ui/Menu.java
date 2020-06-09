package com.plagesribeiro.pucantina.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.ListAdapter;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

public class Menu extends Fragment {

    public Menu(String id) {
        idUsuario = id;
    }

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference().child("produto");
    private RecyclerView mRecycleview;
    private ListAdapter mAdapter;
    private String idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        init(root);
        adapter();
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mAdapter.addList(ds.getValue(Produto.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init(View view) {
        mRecycleview = view.findViewById(R.id.recyclierView);
    }

    private void adapter() {
        mAdapter = new ListAdapter(getActivity(), idUsuario);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleview.setAdapter(mAdapter);
    }
}