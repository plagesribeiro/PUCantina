package com.plagesribeiro.pucantina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Crud_Usuario extends AppCompatActivity {
    private EditText edtEmail, edtNome, edtTelefone, edtCurso;
    private Button btnCadastrar;

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioBanco = banco.child("Usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuario);

        edtEmail = findViewById(R.id.email_id);
        edtNome = findViewById(R.id.nome_id);
        edtTelefone = findViewById(R.id.telefone_id);
        edtCurso = findViewById(R.id.curso_id);
        btnCadastrar = findViewById(R.id.button_cadastrar_id);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String nome = edtNome.getText().toString();
                String telefone = edtTelefone.getText().toString();
                String curso = edtCurso.getText().toString();

                if ((email != "" && nome != "" && telefone != "" && curso != "")) {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setNome(nome);
                    usuario.setTelefone(telefone);
                    usuario.setCurso(curso);

                    //cadastrarUsuario(usuario);
                }
            }
        });
    }



}
