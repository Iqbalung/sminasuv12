package traspac.simansuv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class DetailSuratMasuk extends AppCompatActivity implements View.OnClickListener {

    String surat_id,pengirim,nosurat,hal,sifat,jam,tanggal,lampiran,klasifikasi,jenis,kepada,tanggalsurat,isi,referensi,tembusan,revisi_id;
    TextView txtPengirim,txtNoSurat,txtHal,txtSifat,txtJam,txtTanggal,txtKlasifikasi,txtJenis,txtKepada,txtTanggalSurat,txtIsi,txtReferensi,txtTembusan;
    ImageButton btnDisposisi,btnRiwayatDispo;
    android.support.v4.app.FragmentManager fragManager;
    FragmentTransaction fragTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);
        setUpComponent();
        getParamsData();
        showDetailSuratMasuk();
        setUpFragment();
        showLampiran();
    }




    private void setUpFragment() {

    }

    private void setUpComponent() {
        txtPengirim = (TextView) findViewById(R.id.txtPengirim);
        txtNoSurat = (TextView) findViewById(R.id.txtNomorSurat);
        txtHal = (TextView) findViewById(R.id.txtHal);
        txtSifat = (TextView) findViewById(R.id.txtSifatSurat);
        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        txtJam = (TextView) findViewById(R.id.txtJam);
        txtKepada = (TextView) findViewById(R.id.txtKepada);
        txtJenis = (TextView) findViewById(R.id.txtJenis);
        txtIsi = (TextView) findViewById(R.id.txtIsi);
        txtReferensi = (TextView) findViewById(R.id.txtReferensi);
        txtTembusan = (TextView) findViewById(R.id.txtTembusan);
        txtKlasifikasi = (TextView) findViewById(R.id.txtKlasifikasi);
        txtTanggalSurat = (TextView) findViewById(R.id.txtTanggalSurat);

        btnDisposisi = (ImageButton) findViewById(R.id.btnDisposisi);
        btnDisposisi.setOnClickListener(this);

        btnRiwayatDispo = (ImageButton) findViewById(R.id.btnRiwayatDispo);
        btnRiwayatDispo.setOnClickListener(this);


    }

    private void getParamsData() {
        Intent intent = getIntent();
        surat_id = intent.getStringExtra(Config.TAG_SURAT_ID);
        pengirim = intent.getStringExtra(Config.TAG_DARI);
        nosurat = intent.getStringExtra(Config.TAG_NOSURAT);
        hal = intent.getStringExtra(Config.TAG_HAL);
        sifat = intent.getStringExtra(Config.TAG_SIFAT);
        tanggal = intent.getStringExtra(Config.TAG_TANGGAL_TERIMA);
        tanggalsurat = intent.getStringExtra(Config.TAG_TANGGAL_SURAT);
        jam = intent.getStringExtra(Config.TAG_JAM);
        isi = intent.getStringExtra(Config.TAG_ISI);
        referensi = intent.getStringExtra(Config.TAG_REFERENSI);
        kepada = intent.getStringExtra(Config.TAG_KEPADA);
        jenis = intent.getStringExtra(Config.TAG_JENIS);
        klasifikasi = intent.getStringExtra(Config.TAG_KLASIFIKASI);
        tembusan = intent.getStringExtra(Config.TAG_TEMBUSAN);
        revisi_id = intent.getStringExtra(Config.TAG_REVISI_ID);
    }

    private void showDetailSuratMasuk() {
        txtPengirim.setText(pengirim);
        txtSifat.setText(sifat);
        txtHal.setText(hal);
        txtNoSurat.setText(nosurat);
        txtJam.setText(jam);
        txtTanggal.setText(tanggal);
        txtTanggalSurat.setText(tanggalsurat);
        txtKepada.setText(kepada);
        txtJenis.setText(jenis);
        txtIsi.setText(isi);
        if (referensi == "null"){
            referensi = "-";
        }
        txtReferensi.setText(referensi);
        if(tembusan == " "){
            tembusan = "-";
        }
        txtTembusan.setText(tembusan);
        txtKlasifikasi.setText(klasifikasi);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDisposisi) {
            Intent intent = new Intent(getApplicationContext(),Disposisi.class);
            intent.putExtra(Config.TAG_DARI,pengirim);
            intent.putExtra(Config.TAG_SIFAT,sifat);
            intent.putExtra(Config.TAG_NOSURAT,nosurat);
            intent.putExtra(Config.TAG_JAM,jam);
            intent.putExtra(Config.TAG_TANGGAL_SURAT,tanggalsurat);
            intent.putExtra(Config.TAG_KEPADA,kepada);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(),DaftarRiwayatDisposisi.class);
            intent.putExtra(Config.TAG_SURAT_ID,surat_id);
            intent.putExtra(Config.TAG_REVISI_ID,revisi_id);
            Log.d("Revisi Id",revisi_id);
            startActivity(intent);
        }

    }

    private void showLampiran() {
        final StringRequest request = new StringRequest(Request.Method.GET, Config.URL_GET_FILE_LAMPIRAN+surat_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray json = new JSONArray(response);

                            DaftarLampiran daftar_lampiran = new DaftarLampiran();

                            Bundle bundle = new Bundle();
                            bundle.putString(Config.TAG_LAMPIRAN,json.toString());
                            daftar_lampiran.setArguments(bundle);
                            fragManager = getSupportFragmentManager();
                            fragTransaction = fragManager.beginTransaction().add(R.id.frameLampiran,daftar_lampiran);
                            fragTransaction.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "onResponse: " + e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "onErrorResponse: " + error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<>();
                params.put(Config.TAG_SURAT_ID,surat_id);
                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(request);
    }
}
