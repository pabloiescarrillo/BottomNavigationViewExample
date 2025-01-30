package es.iescarrillo.android.bottomnavigationviewexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import es.iescarrillo.android.bottomnavigationviewexample.R;
import es.iescarrillo.android.bottomnavigationviewexample.fragments.HomeFragment;
import es.iescarrillo.android.bottomnavigationviewexample.fragments.ProfileFragment;
import es.iescarrillo.android.bottomnavigationviewexample.fragments.SettingsFragment;
import es.iescarrillo.android.bottomnavigationviewexample.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity {

    // Views components
    private BottomNavigationView bottomMenu;
    private FrameLayout containerFrame;
    private DrawerLayout drawerLayout;
    private Toolbar appToolbar;
    private NavigationView lateralMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadComponents();

        // Indicar que la barra de acción contenga el botón de hamburguesa
        setSupportActionBar(appToolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                appToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        lateralMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectdFragment = new Fragment();

                if(item.getItemId() == R.id.nav_lateral_home){
                    selectdFragment = new HomeFragment();
                } else if(item.getItemId() == R.id.nav_lateral_profile){
                    selectdFragment = new ProfileFragment();
                } else if(item.getItemId() == R.id.nav_lateral_settings){
                    selectdFragment = new SettingsFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(containerFrame.getId(), selectdFragment).commit();

                drawerLayout.closeDrawers();

                return true;
            }
        });

        // Managing navigation
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = new Fragment();

                if(item.getItemId() == R.id.nav_home){
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_profile){
                    selectedFragment = new ProfileFragment();
                } else if (item.getItemId() == R.id.nav_settings){
                    selectedFragment = new SettingsFragment();
                } else if (item.getItemId() == R.id.nav_wishlist){
                    selectedFragment = new WishlistFragment();
                } else if (item.getItemId() == R.id.nav_add){
                    Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("MainActivity - error", "Acción no controlada del menú");
                }

                getSupportFragmentManager().beginTransaction().replace(containerFrame.getId(), selectedFragment).commit();

                return true;
            }
        });
    }

    private void loadComponents(){
        bottomMenu = findViewById(R.id.bottomMenu);
        containerFrame = findViewById(R.id.containerFrame);
        drawerLayout = findViewById(R.id.drawer_main);
        appToolbar = findViewById(R.id.toolbar);
        lateralMenu = findViewById(R.id.lateral_nav);

        // Load default fragment
        getSupportFragmentManager().beginTransaction().replace(containerFrame.getId(), new HomeFragment()).commit();

        // Encuentra el ítem central
        View specialItem = bottomMenu.findViewById(R.id.nav_add);

        // Cambia su tamaño
        specialItem.setScaleX(2.5f); // Aumenta el ancho
        specialItem.setScaleY(2.5f); // Aumenta el alto
    }
}