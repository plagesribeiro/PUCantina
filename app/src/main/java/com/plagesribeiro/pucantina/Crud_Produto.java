package com.plagesribeiro.pucantina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import static android.app.Activity.RESULT_OK;

public  class Crud_Produto extends Fragment {
/*
    public Crud_Produto() {
        // Required empty public constructor
    }

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    private EditText edtNome, edtDescricao, edtValor;
    private ImageView imagem;
    private Button btnBuscarFoto, btnCadastrarProduto;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    public static View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_crud__produto, container, false);
        // Inflate the layout for this fragment

        edtNome = View.findViewById(R.id.editTextNome_id);
        edtDescricao = findViewById(R.id.editTextDescricao_id);
        edtValor = findViewById(R.id.editTextValor_id);
        btnBuscarFoto = findViewById(R.id.buttonBuscar_id);
        btnCadastrarProduto = findViewById(R.id.buttonCadastrar_id);
        imagem = findViewById(R.id.imageProduto_id);
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
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

                    Intent main = new Intent(Crud_Produto.this, MainActivity.class);
                    startActivity(main);
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha todos os dados", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;


    }

   private void cadastrarProduto(final Produto produto) {
        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idProduto = Base64.encodeToString(produto.getNome().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                produto.setIdProduto(idProduto);

                if (dataSnapshot.child("produto").child(idProduto).exists()) {
                    Toast.makeText(getApplicationContext(), "Produto já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                } else {
                    banco.child("produto").child(idProduto).setValue(produto);
                    Toast.makeText(getApplicationContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
           imageUri = data.getData();
           imagem.setImageURI(imageUri);
       }
    }*/
}