package traspac.simansuv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DaftarRiwayatDisposisi extends AppCompatActivity
{

    private ListView list_riwayat_disposisi;
    private SessionManager session;
    private String surat_id,user_id,revisi_id;
    private ArrayList<RiwayatDisposisi> data_riwayat_disposisi;
    private AdapterRiwayatDisposisi adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_disposisi);
        data_riwayat_disposisi = new ArrayList<RiwayatDisposisi>();
        setUpComponent();
        getRiwayatDisposisi();
    }

    private void getRiwayatDisposisi()
    {
        class getData extends AsyncTask<Void,String,String>
        {

            ProgressDialog pd;
            String url = Config.URL_GET_RIWAYAT_DISPOSISI+"?surat_id="+revisi_id+"&user_id="+user_id;


            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                pd = ProgressDialog.show(DaftarRiwayatDisposisi.this,"Proses","Mengambil data...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pd.dismiss();

                showRiwayatDisposisi(s);
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler request = new RequestHandler();
                String data = request.sendGetRequest(url);
                return data;
            }
        }
        getData getdata = new getData();
        getdata.execute();
    }

    private void showRiwayatDisposisi(String JSON)
    {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length() ; i++) {
                JSONObject json = result.getJSONObject(i);

                Log.d("Data",json.toString());
                String tanggal = json.getString(Config.TAG_TANGGAL_DISPOSISI);
                String format_jam = tanggal.substring(tanggal.length()-8,tanggal.length());
                String format_tanggal = tanggal.substring(0,10);

                Log.d("Data",json.getString(Config.TAG_DISPOSISI_ID));

                RiwayatDisposisi rd = new RiwayatDisposisi();
                rd.setDari(json.getString(Config.TAG_DARI));
                rd.setJam(format_jam);
                rd.setTgl_disposisi(format_tanggal);

                data_riwayat_disposisi.add(rd);

            }
        } catch (JSONException e) {
            Log.e("Data",e.getMessage());
            e.printStackTrace();

        }

        adapter = new AdapterRiwayatDisposisi(getApplicationContext(),R.layout.daftar_riwayat_disposisi,data_riwayat_disposisi);
        list_riwayat_disposisi.setAdapter(adapter);

    }

    private void setUpComponent()
    {
        session = new SessionManager(getApplicationContext());
        HashMap<String,String> data_user = session.getDataUser();
        user_id = data_user.get(Config.TAG_ID_USER);

        Intent intent = getIntent();
        surat_id = intent.getStringExtra(Config.TAG_SURAT_ID);
        revisi_id = intent.getStringExtra(Config.TAG_REVISI_ID);

        list_riwayat_disposisi = (ListView) findViewById(R.id.list_riwayat_disposisi);
    }

}
