package com.plagesribeiro.pucantina;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plagesribeiro.pucantina.ui.Carrinho;
import com.plagesribeiro.pucantina.ui.Menu;
import com.plagesribeiro.pucantina.ui.Perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu, R.id.navigation_carrinho, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_menu:
                        selectedFragment = new Menu();
                        break;
                    case R.id.navigation_carrinho:
                        String idUser = getIntent().getStringExtra("id_User");
                        selectedFragment = new Carrinho(idUser);
                        break;
                    case R.id.navigation_perfil:
                        String idUsuario = getIntent().getStringExtra("id_User");
                        selectedFragment = new Perfil(idUsuario);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            }
        });

    }

}
