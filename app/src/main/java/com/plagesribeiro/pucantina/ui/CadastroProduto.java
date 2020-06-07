package com.plagesribeiro.pucantina.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plagesribeiro.pucantina.Produto;
import com.plagesribeiro.pucantina.R;

import static android.app.Activity.RESULT_OK;

public class CadastroProduto extends Fragment {

    public CadastroProduto() {
        // Required empty public constructor
    }
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    private static EditText edtNome, edtDescricao, edtValor;
    private ImageView imagem;
    private Button btnBuscarFoto, btnCadastrarProduto;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cadastro_produto, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNome = view.findViewById(R.id.editTextNome_id);
        edtDescricao = view.findViewById(R.id.editTextDescricao_id);
        edtValor = view.findViewById(R.id.editTextValor_id);
        btnBuscarFoto = view.findViewById(R.id.buttonBuscar_id);
        btnCadastrarProduto = view.findViewById(R.id.buttonCadastrar_id);
        imagem = view.findViewById(R.id.imageProduto_id);
        btnBuscarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();

            }
        });
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String descricao = edtDescricao.getText().toString();
                String valor = edtValor.getText().toString();


                if ((nome.equals("") == false && descricao.equals("") == false && valor.equals("") == false )) {

                    Produto produto = new Produto();

                    produto.setNome(nome);
                    produto.setDescricao(descricao);
                    produto.setValor(valor);
                    produto.setImagem(nome);

                    cadastrarProduto(produto);


                }else{
                    Toast.makeText(getActivity(), "Preencha todos os dados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cadastrarProduto(final Produto produto) {
        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idProduto = Base64.encodeToString(produto.getNome().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                produto.setIdProduto(idProduto);

                if (dataSnapshot.child("produto").child(idProduto).exists()) {
                    Toast.makeText(getActivity(), "Produto já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                } else {
                    banco.child("produto").child(idProduto).setValue(produto);
                    Toast.makeText(getActivity(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagem.setImageURI(imageUri);
        }
    }
}