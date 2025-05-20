package com.example.tiendaar_pdm.VistasAdmin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import com.example.tiendaar_pdm.fragments.CategoriasFragment;
import com.example.tiendaar_pdm.fragments.ProductosFragment;
import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.fragments.OrdenDetalleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuAdminActivity extends AppCompatActivity {
    public FragmentContainerView fragmnetContainer;
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AsociarElemntosXML();

        // Cargar el fragmento por defecto al iniciar
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmnetContainer, new ProductosFragment())
                    .commit();
            // Establecer también el ítem seleccionado en la barra de navegación
            bottomNavigationView.setSelectedItemId(R.id.navGestionarProductos);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //consultar a que se le esta dando clic
                switch (item.getItemId()){
                    case R.id.navGestionarProductos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmnetContainer, new ProductosFragment()).commit();
                        break;
                    case R.id.navCtegorias:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmnetContainer, new CategoriasFragment()).commit();
                        break;
                    case R.id.navOrdenes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmnetContainer, new OrdenDetalleFragment()).commit();
                        break;
                    default:
                        System.out.println("Opcion no calida");
                }
                return true;
            }
        });
    }
    public void AsociarElemntosXML(){
        bottomNavigationView = findViewById(R.id.bottomnavigation);
    }
}