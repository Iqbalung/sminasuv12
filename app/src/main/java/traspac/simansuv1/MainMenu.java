package traspac.simansuv1;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FragmentManager fragManager;
    private FragmentTransaction fragTransaction;
    private DaftarSuratMasuk daftarsuratmasuk;
    private HeaderSuratMasuk headsuratmasuk;
    private SessionManager session;
    private TextView txtUsernmae;
    private Button btnKeluar;
    private ImageButton btnSearch;
    private EditText etSearch;
    private Toolbars toolbars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpFragmnet();

        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        View header = navigation.getHeaderView(0);
        txtUsernmae = (TextView) header.findViewById(R.id.txtUsername);
        session = new SessionManager(header.getContext());
        HashMap<String,String> sess_login = session.getDataUser();
        String username = sess_login.get(Config.TAG_USERNAME);
        /*Log.d("Data","Data User "+sess_login);*/
        txtUsernmae.setText(username);

        btnKeluar = (Button) findViewById(R.id.btnKeluar);






    }


    private void setUpFragmnet() {
        daftarsuratmasuk = new DaftarSuratMasuk();
        headsuratmasuk = new HeaderSuratMasuk();
        toolbars = new Toolbars();

        fragManager = getSupportFragmentManager();
        fragTransaction = fragManager.beginTransaction();

        fragTransaction.add(R.id.content_fragment,daftarsuratmasuk,"Daftar Surat Masuk");
        fragTransaction.add(R.id.header_fragment,headsuratmasuk,"Header Surat Masuk");
        fragTransaction.replace(R.id.frameToolbar,toolbars);
        fragTransaction.commit();
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment content = null;
        toolbars = new Toolbars();
        Bundle bundle = new Bundle();
        String title = "";
        if (id == R.id.nav_statistic) {
            content = new Chart();
            title = "Statistik";
            bundle.putString("Layout","chart");
            toolbars.setArguments(bundle);
        }else if(id == R.id.nav_kotak_masuk) {
            content = new DaftarSuratMasuk();
            title = "Daftar Surat Masuk";
            DaftarSuratMasuk.search = "";
        }
        fragManager = getSupportFragmentManager();
        fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.frameToolbar,toolbars);
        fragTransaction.replace(R.id.content_fragment,content,title);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btnKeluar) {
            session.clearSession();
            finishAffinity();
        }
    }

}
