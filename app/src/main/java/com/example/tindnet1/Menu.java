package com.example.tindnet1;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tindnet1.databinding.ActivityMenuBinding;
// importaciones para lo de las tarjetas
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // codigo para lo de las tarjetas
    /*
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        List<Company> companies = new ArrayList<>();
        companies.add(new Company(R.drawable.foto_empresa_random, "Description for Company 1"));
        companies.add(new Company(R.drawable.random_foto_2, "Description for Company 2"));
        // Añade más empresas según sea necesario

        CompanyAdapter adapter = new CompanyAdapter(companies);
        viewPager.setAdapter(adapter);
     */
    }

}