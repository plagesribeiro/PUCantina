package com.plagesribeiro.pucantina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Crud_Usuario extends AppCompatActivity {
    private EditText edtEmail, edtNome, edtTelefone, edtCurso, edtSenha;
    private Button btnCadastrar;
    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuario);

        edtEmail = findViewById(R.id.email_id);
        edtNome = findViewById(R.id.nome_id);
        edtTelefone = findViewById(R.id.telefone_id);
        edtCurso = findViewById(R.id.curso_id);
        edtSenha = findViewById(R.id.senha_id);
        btnCadastrar = findViewById(R.id.button_cadastrar_id);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String nome = edtNome.getText().toString();
                String telefone = edtTelefone.getText().toString();
                String curso = edtCurso.getText().toString();
                String senha = edtSenha.getText().toString();

                if ((email != "" && nome != "" && telefone != "" && curso != "" && senha != "")) {

                    Usuario usuario = new Usuario();

                    usuario.setEmail(email);
                    usuario.setNome(nome);
                    usuario.setTelefone(telefone);
                    usuario.setCurso(curso);
                    usuario.setSenha(senha);

                    cadastrarUsuario(usuario);

                    Intent main = new Intent(Crud_Usuario.this, MainActivity.class);
                    startActivity(main);
                }
            }
        });

    }

    private void cadastrarUsuario(final Usuario usuario) {
        banco.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                usuario.setId(idUsuario);
                boolean contatoJaCadastrado = dataSnapshot.child("Usuario").hasChild(idUsuario);
                if (contatoJaCadastrado) {
                    Toast.makeText(getApplicationContext(), "Usuario já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                } else {
                    banco.child("Usuarios").child(idUsuario).setValue(usuario);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
