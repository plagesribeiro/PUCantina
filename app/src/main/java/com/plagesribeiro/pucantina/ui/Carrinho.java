package com.plagesribeiro.pucantina.ui;

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
    private Button botaoDeletar;
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
        botaoDeletar = view.findViewById(R.id.button_deletarDoCarrinho);
        botaoDeletar.setVisibility(View.GONE);

        listView.setAdapter(null);

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                if(dataSnapshot.child("carrinho").child(idCarrinho).getValue() == null){
                    CarrinhoEntidade carrinho = new CarrinhoEntidade();
                    carrinho.setIdUsuario(idUsuario);
                    carrinho.setIdCarrinho(idCarrinho);
                    banco.child("carrinho").child(idCarrinho).setValue(carrinho);
                    atualizaListView();
                }else{
                    atualizaListView();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                        String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                        String nomePedido = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Pedido";
                        String idPedido = Base64.encodeToString(nomePedido.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                        CarrinhoEntidade carrinhoAtualizado = dataSnapshot.child("carrinho").child(idCarrinho).getValue(CarrinhoEntidade.class);
                        banco.child("carrinho").child(idCarrinho).setValue(null);

                        PedidoEntidade pedido = new PedidoEntidade();
                        Toast.makeText(getActivity(), pedido.getHoraPedido(), Toast.LENGTH_SHORT).show();
                        pedido.fromCarrinho(carrinhoAtualizado, idPedido);

                        banco.child("pedido").child(idPedido).setValue(pedido);
                        banco.child("pedido").child(idPedido).child("horaPedido").setValue(pedido.getHoraPedido());

                        pedido = null;

                        List limpa = new ArrayList<Produto>();

                        adapter = new ArrayAdapter<CarrinhoEntidade>(getActivity(),android.R.layout.simple_list_item_1,limpa);
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Produto selectedItem = (Produto) parent.getItemAtPosition(position);
                botaoDeletar.setVisibility(View.VISIBLE);

                botaoDeletar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        banco.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                                String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                                int qtFilhos = (int) dataSnapshot.child("carrinho").child(idCarrinho).child("produtos").getChildrenCount();
                                Produto teste;
                                for(int i = position ; i<qtFilhos ; i++){
                                    if(i == qtFilhos-1){
                                        banco.child("carrinho").child(idCarrinho).child("produtos").child(Integer.toString(i)).setValue(null);
                                    }else{
                                        teste = dataSnapshot.child("carrinho").child(idCarrinho).child("produtos").child(Integer.toString(i+1)).getValue(Produto.class);
                                        banco.child("carrinho").child(idCarrinho).child("produtos").child(Integer.toString(i)).setValue(teste);
                                    }
                                }

                                atualizaListView();

                                Toast.makeText(getContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                                botaoDeletar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                });
            }
        });
    }

    public void atualizaListView(){

        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeCarrinho = dataSnapshot.child("usuario").child(idUsuario).child("email").getValue().toString() + "Carrinho";
                String idCarrinho = Base64.encodeToString(nomeCarrinho.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                listView.setAdapter(null);
                List listaProdutos;
                CarrinhoEntidade carrinhoAtualizar = dataSnapshot.child("carrinho").child(idCarrinho).getValue(CarrinhoEntidade.class);

                listaProdutos = carrinhoAtualizar.getProdutos();
                adapter = new ArrayAdapter<CarrinhoEntidade>(getActivity(),android.R.layout.simple_list_item_1,listaProdutos);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
