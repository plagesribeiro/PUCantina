package com.plagesribeiro.pucantina.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Carrinho extends Fragment {
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();
    private ListView listView;
    private Button botaoFinalizar;
    private ArrayAdapter<CarrinhoEntidade> adapter;
    private String idUsuario;

    public Carrinho(String id) {
        idUsuario = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrinho, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.listView_Carrinho);
        botaoFinalizar = (Button) view.findViewById(R.id.button_finalizar);

        listView.setAdapter(null);

        final Produto prod1 = new Produto();
        prod1.setIdProduto("ID prod1");
        prod1.setNome("kibe");
        prod1.setDescricao("Desc prod1");
        prod1.setValor("25");

        final Produto prod2 = new Produto();
        prod2.setIdProduto("ID prod2");
        prod2.setNome("feijao");
        prod2.setDescricao("Desc prod2");
        prod2.setValor("30");

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CarrinhoEntidade carrinho = new CarrinhoEntidade();

                carrinho.setIdCarrinho("sdfuysbdfj");
                carrinho.setValorTotal("1 milhao de dolares");

                carrinho.addProduto(prod1,1);
                carrinho.addProduto(prod2,2);

                List produtos = carrinho.getProdutos();

                adapter = new ArrayAdapter<CarrinhoEntidade>(getActivity(),android.R.layout.simple_list_item_1,produtos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
