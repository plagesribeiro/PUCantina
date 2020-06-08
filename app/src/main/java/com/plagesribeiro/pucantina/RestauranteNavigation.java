package com.plagesribeiro.pucantina;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.plagesribeiro.pucantina.ui.CadastroProduto;
import com.plagesribeiro.pucantina.ui.Pedido;
import com.plagesribeiro.pucantina.ui.Perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class RestauranteNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view_restaurante);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_cadastrar_produto, R.id.navigation_pedidos, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_restaurante);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_cadastrar_produto:
                        selectedFragment = new CadastroProduto();
                        break;
                    case R.id.navigation_pedidos:
                        selectedFragment = new Pedido();
                        break;
                    case R.id.navigation_perfil:
                        String idUsuario = getIntent().getStringExtra("id_User");
                        selectedFragment = new Perfil(idUsuario);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_restaurante, selectedFragment).commit();
                return true;
            }
        });
    }
}