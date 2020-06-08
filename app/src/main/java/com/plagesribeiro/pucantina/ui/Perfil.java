package com.plagesribeiro.pucantina.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.MainActivity;
import com.plagesribeiro.pucantina.PerfilEntidade;
import com.plagesribeiro.pucantina.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    private String idUsuario;
    private TextView nome;
    private TextView email;
    private TextView telefone;
    private TextView curso;

    public Perfil(String id) {
        idUsuario = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nome = view.findViewById(R.id.textView_Perfil_nome);
                email = view.findViewById(R.id.textView_Perfil_email);
                telefone = view.findViewById(R.id.textView_Perfil_telefone);
                curso = view.findViewById(R.id.textView_Perfil_curso);

                nome.setText(dataSnapshot.child("usuario").child(idUsuario).child("nome").getValue().toString());
                email.setText(dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString());
                telefone.setText(dataSnapshot.child("usuario").child(idUsuario).child("telefone").getValue().toString());
                curso.setText(dataSnapshot.child("usuario").child(idUsuario).child("curso").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
