package com.plagesribeiro.pucantina.ui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.PedidoEntidade;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends ListFragment {

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    private ListView listView;

    private ArrayAdapter<Produto> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        List<Produto> produtos;

        listView = (ListView) view.findViewById(R.id.listView_Menu);

        final Produto prod1 = new Produto();
        prod1.setIdProduto("ID prod1");
        prod1.setNome("Nome  prod1");
        prod1.setDescricao("Desc prod1");
        prod1.setValor("20");

        final Produto prod2 = new Produto();
        prod2.setIdProduto("ID prod2");
        prod2.setNome("Nome  prod2");
        prod2.setDescricao("Desc prod2");
        prod2.setValor("30");

        listView.setAdapter(null);

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Produto> produtos = new ArrayList<Produto>();

                        /*for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            PedidoEntidade pedido = new PedidoEntidade();

                            pedido.setIdPedido(postSnapshot.child("idPedido").getValue().toString());
                            pedido.setValorTotal(postSnapshot.child("valorTotal").getValue().toString());
                            pedido.setHoraPedido(postSnapshot.child("horaPedido").getValue().toString());
                            pedido.setProdutos(postSnapshot.child("produtos").getValue());

                            pedido.add(pedido);

                            pedido = null;
                        }*/


                produtos.add(prod1);
                produtos.add(prod2);

                adapter = new ArrayAdapter<Produto>(getActivity(),android.R.layout.simple_list_item_1, produtos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}