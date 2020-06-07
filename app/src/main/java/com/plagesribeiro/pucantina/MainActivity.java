package com.plagesribeiro.pucantina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private EditText email;
    private EditText senha;

    private CheckBox checkBox;
    private Button btn_Cadastrar;
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editText_email);
        senha = (EditText) findViewById(R.id.editText_senha);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btn_Cadastrar = findViewById(R.id.button_Cadastrar);
        btn_Login = findViewById(R.id.button_Login);

        btn_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crud_usuario = new Intent(MainActivity.this, Crud_Usuario.class);
                startActivity(crud_usuario);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salvar preferencia da checkbox
                if(checkBox.isChecked()){
                    //setar checkbox quando o app comeca
                    edit.putString(getString(R.string.checkbox_SharedPreferences), "True");
                    edit.commit();

                    //salvar email
                    String email_SharedPreferences = email.getText().toString();
                    edit.putString(getString(R.string.email_SharedPreferences), email_SharedPreferences);
                    edit.commit();

                    //salvar senha
                    String senha_SharedPreferences = senha.getText().toString();
                    edit.putString(getString(R.string.senha_SharedPreferences), senha_SharedPreferences);
                    edit.commit();

                }else{
                    //setar checkbox quando o app comeca
                    edit.putString(getString(R.string.checkbox_SharedPreferences), "False");
                    edit.commit();

                    //salvar email
                    edit.putString(getString(R.string.email_SharedPreferences), "");
                    edit.commit();

                    //salvar senha
                    edit.putString(getString(R.string.senha_SharedPreferences), "");
                    edit.commit();

                }

                final String email_validacao = email.getText().toString();

                banco.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario usuario = new Usuario();
                        String idUsuario = Base64.encodeToString(email_validacao.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                        if (dataSnapshot.child("usuario").child(idUsuario).exists()) {
                            String senhaValidacao = senha.getText().toString();
                            String senhaUsuario = dataSnapshot.child("usuario").child(idUsuario).child("senha").getValue().toString();

                            if(senhaValidacao.equals(senhaUsuario)){
                                //Redirecionar para página do Menu
                                Intent user_menu = new Intent(MainActivity.this, UserNavigation.class);
                                startActivity(user_menu);

                            }else{
                                Toast.makeText(getApplicationContext(), "Senha inválida", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Usuario não existe", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        pref = getSharedPreferences("com.plagesribeiro.pucantina", Context.MODE_PRIVATE);
        edit = pref.edit();

        checkSharedPreferences();
    }

    private void checkSharedPreferences(){
        String checkbox_SharedPreferences = pref.getString(getString(R.string.checkbox_SharedPreferences), "False");
        String email_SharedPreferences = pref.getString(getString(R.string.email_SharedPreferences), "");
        String senha_SharedPreferences = pref.getString(getString(R.string.senha_SharedPreferences), "");

        email.setText(email_SharedPreferences);
        senha.setText(senha_SharedPreferences);

        if(checkbox_SharedPreferences.equals("True")){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }


    }
}