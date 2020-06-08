package com.plagesribeiro.pucantina.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.PedidoEntidade;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

import java.util.ArrayList;
import java.util.List;

public class Pedido extends Fragment {
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();
    private ListView listView;
    private Button botaoAtualizar;
    private ArrayAdapter<PedidoEntidade> adapter;
    private Button botaoDeletar;

    public Pedido() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedido, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        List<PedidoEntidade> pedidos;

        listView = (ListView) view.findViewById(R.id.listView_Pedidos);
        botaoAtualizar = (Button) view.findViewById(R.id.button_atualizar_id);
        botaoDeletar = view.findViewById(R.id.btn_deletar_banco);

        botaoDeletar.setVisibility(View.GONE);

        final Produto prod1 = new Produto();
        prod1.setIdProduto("ID prod1");
        prod1.setNome("Nome  prod1");
        prod1.setDescricao("Desc prod1");
        prod1.setValor("preco prod1");

        final Produto prod2 = new Produto();
        prod2.setIdProduto("ID prod2");
        prod2.setNome("Nome  prod2");
        prod2.setDescricao("Desc prod2");
        prod2.setValor("preco prod2");

        listView.setAdapter(null);

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<PedidoEntidade> pedidos = new ArrayList<PedidoEntidade>();

                for (DataSnapshot postSnapshot : dataSnapshot.child("pedido").getChildren()) {
                    PedidoEntidade pedido = postSnapshot.getValue(PedidoEntidade.class);

                    pedidos.add(pedido);

                    pedido = null;
                }

                adapter = new ArrayAdapter<PedidoEntidade>(getActivity(),android.R.layout.simple_list_item_1, pedidos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(null);

                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<PedidoEntidade> pedidos = new ArrayList<PedidoEntidade>();

                        for (DataSnapshot postSnapshot : dataSnapshot.child("pedido").getChildren()) {
                            PedidoEntidade pedido = postSnapshot.getValue(PedidoEntidade.class);

                            pedidos.add(pedido);

                            pedido = null;
                        }

                        adapter = new ArrayAdapter<PedidoEntidade>(getActivity(),android.R.layout.simple_list_item_1, pedidos);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PedidoEntidade selectedItem = (PedidoEntidade) parent.getItemAtPosition(position);
                botaoDeletar.setVisibility(View.VISIBLE);

                botaoDeletar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), selectedItem.getValorTotal(), Toast.LENGTH_SHORT).show();
                        banco.child("pedido").child(selectedItem.getIdPedido()).setValue(null);
                        botaoDeletar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}