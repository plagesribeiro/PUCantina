package com.plagesribeiro.pucantina.ui;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.CarrinhoEntidade;
import com.plagesribeiro.pucantina.PedidoEntidade;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends Fragment {
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();
    private ListView listView;
    private ArrayAdapter<Produto> adapter;
    private List<Produto> produtos = new ArrayList<Produto>();
    private String idUsuario;
    private View root;

    public Menu(String id) {
        idUsuario = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.listView_menu);
        listView.setAdapter(null);

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.child("produto").getChildren()) {
                    Produto produto = postSnapshot.getValue(Produto.class);
                    produtos.add(produto);
                    produto = null;
                }

                adapter = new ArrayAdapter<Produto>(root.getContext(),android.R.layout.simple_list_item_1, produtos);
                produtos = null;
                produtos = new ArrayList<Produto>();
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                        String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                        final Produto selectedItem = (Produto) parent.getItemAtPosition(position);
                        CarrinhoEntidade carrinho = dataSnapshot.child("carrinho").child(idCarrinho).getValue(CarrinhoEntidade.class);
                        carrinho.addProduto(selectedItem,1);
                        banco.child("carrinho").child(idCarrinho).setValue(carrinho);
                        carrinho = null;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}