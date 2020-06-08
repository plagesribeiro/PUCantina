package com.plagesribeiro.pucantina.ui;

import android.content.Context;
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

    public Pedido() {
        // Required empty public constructor
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

        //idProduto, valor, nome, descricao, imagem;
        final Produto prod1 = new Produto();
        prod1.setIdProduto("ID prod1");
        prod1.setNome("Nome  prod1");
        prod1.setDescricao("Desc prod1");
        prod1.setValor("preco prod1");
        prod1.setImagem("imagem prod1");

        final Produto prod2 = new Produto();
        prod2.setIdProduto("ID prod2");
        prod2.setNome("Nome  prod2");
        prod2.setDescricao("Desc prod2");
        prod2.setValor("preco prod2");
        prod2.setImagem("imagem prod2");

        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(null);

                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<PedidoEntidade> pedidos = new ArrayList<PedidoEntidade>();

                        /*for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Contato contato = new Contato();

                            contato.setNome(postSnapshot.child("nome").getValue().toString());
                            contato.setEmail(postSnapshot.child("email").getValue().toString());
                            contato.setEndereco(postSnapshot.child("endereco").getValue().toString());
                            contato.setCep(postSnapshot.child("cep").getValue().toString());
                            contato.setTelefone(postSnapshot.child("telefone").getValue().toString());
                            contato.setNascimento(postSnapshot.child("nascimento").getValue().toString());

                            contatos.add(contato);

                            contato = null;
                        }*/

                        for(int i=0 ; i<3 ; i++){
                            PedidoEntidade pedido = new PedidoEntidade();

                            pedido.setIdPedido("sdfuysbdfj");
                            pedido.setValorTotal("1 milhao de dolares");
                            pedido.setHoraPedido("hora de dar tchau");
                            pedido.addProduto(prod1,2);
                            pedido.addProduto(prod2,2);

                            pedido.removeProduto(prod2,1);

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
    }
}