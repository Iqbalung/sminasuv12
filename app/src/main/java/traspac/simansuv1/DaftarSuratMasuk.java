package traspac.simansuv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class DaftarSuratMasuk extends Fragment implements AdapterView.OnItemClickListener
{

    private ListView lvSuratMasuk;
    private String user_id,surat_id,jenis_id,revisi_id,cari;
    private ArrayList<SuratMasuk> dataSuratMasuk;
    private AdapterSuratMasuk adapter;
    private View view;
    public static int TAG_SEACRH = 0;
    public static String search = "";
    public static String dataLampiran;
    HashMap<String, String> detailSuratMasuk;
    private SessionManager session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_daftar_surat_masuk,container,false);

        lvSuratMasuk = (ListView) view.findViewById(R.id.lvSuratMasuk);
        dataSuratMasuk = new ArrayList<SuratMasuk>();
        detailSuratMasuk = new HashMap<String, String>();
        adapter = new AdapterSuratMasuk(view.getContext(),R.layout.daftar_surat_masuk,dataSuratMasuk);
        lvSuratMasuk.setAdapter(adapter);
        lvSuratMasuk.setOnItemClickListener(this);
        session = new SessionManager(view.getContext());
        HashMap<String,String> data_user = session.getDataUser();
        user_id = data_user.get(Config.TAG_ID_USER);
        getSuratMasuk();
        return view;
    }

    public void getSuratMasuk()
    {
        class getData extends AsyncTask<Void,String,String>
        {

            ProgressDialog pd;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                pd = ProgressDialog.show(view.getContext(),"Proses","Mengambil data...",false,false);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                pd.dismiss();
                showSuratMasuk(s);
            }

            @Override
            protected String doInBackground(Void... params)
            {
                RequestHandler request = new RequestHandler();
                String url = Config.URL_GET_DATA+user_id;
                if (TAG_SEACRH != 0) {
                    url = Config.URL_GET_DATA+user_id+"&v_cari="+search;
                }
                String data = request.sendGetRequest(url);
                Log.d("Data",url);
                return data;
            }
        }
        getData getdata = new getData();
        getdata.execute();
    }

    private void showSuratMasuk(String JSON)
    {
        /*Log.d("Data Surat Msuk",JSON);*/
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length() ; i++) {

                JSONObject json = result.getJSONObject(i);

                String dibaca = json.getString(Config.TAG_DIBACA).toString();
                String disposisi = json.getString(Config.TAG_DISPOSISI);
                String jam = json.getString(Config.TAG_JAM.toString());
                String format_jam = jam.substring(jam.length()-8,jam.length());
                String tanggal = json.getString(Config.TAG_TANGGAL.toString());
                String format_tanggal = tanggal.substring(0,10);

                int font_weight = Typeface.NORMAL;
                String dispo = " ";
                int visible = 0x00000004;

                if (dibaca.equals("0")) {
                    font_weight = Typeface.BOLD;
                }

                if (disposisi.equals("1")){
                    dispo = "Disposisi";
                    visible = 0x00000000;
                }

                SuratMasuk sm = new SuratMasuk(
                        json.getString(Config.TAG_SURAT_ID),
                        json.getString(Config.TAG_SIFAT),
                        json.getString(Config.TAG_DARI),
                        format_tanggal,
                        format_jam,
                        json.getString(Config.TAG_NOSURAT),
                        json.getString(Config.TAG_HAL),
                        dispo,
                        visible,
                        font_weight,
                        json.getString(Config.TAG_JENIS_ID),
                        json.getString(Config.TAG_REVISI_ID)
                );
                /*SuratMasuk sm = new SuratMasuk();
                sm.setDari(json.getString(Config.TAG_DARI));
                sm.setSurat_id(json.getString(Config.TAG_SURAT_ID));
                sm.setRevisi_id(json.getString(Config.TAG_REVISI_ID));
                sm.setRevisi_id(json.getString(Config.TAG_JENIS_ID));*/
                dataSuratMasuk.add(sm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new AdapterSuratMasuk(view.getContext(),R.layout.daftar_surat_masuk,dataSuratMasuk);
        lvSuratMasuk.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        SuratMasuk data = (SuratMasuk) parent.getItemAtPosition(position);
        String[] params = {user_id,data.getSurat_id(),data.getJenis_id(),data.getRevisi_id()};
        getDetailSuratMasuk(params);

    }


    private void getDetailSuratMasuk(final String[] params)
    {
        String url = Config.lihat_baca_surat(params[0],params[1],params[2],params[3]);
        Log.d("Data",url);
        final StringRequest request = new StringRequest(Request.Method.GET ,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);
                            showDetailSuratMasuk(json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Detail Surat Masuk: " + e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "(Volley) Detail Surat Masuk:: " + error.getMessage());
                    }
                }
        );

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);

    }

    private void showDetailSuratMasuk(JSONObject data)
    {
        Intent intent = new Intent(view.getContext(),DetailSuratMasuk.class);
        try {

            String[] TAG_DATA = {Config.TAG_SURAT_ID,Config.TAG_DARI,Config.TAG_NOSURAT,Config.TAG_HAL,Config.TAG_KEPADA,Config.TAG_TANGGAL_SURAT,Config.TAG_SIFAT,Config.TAG_KLASIFIKASI,Config.TAG_JENIS,Config.TAG_TANGGAL_TERIMA,Config.TAG_ISI,Config.TAG_REVISI_ID,Config.TAG_REFERENSI,Config.TAG_TEMBUSAN};

            intent.putExtra(Config.TAG_JENIS,data.getString(Config.TAG_JENIS));
            /*Log.d("Data", "showDetailSuratMasuk: "+data.toString());*/
            for (int i = 0; i < TAG_DATA.length; i++) {
                /*Log.d("Data", "showDetailSuratMasuk: " + data.getString(TAG_DATA[i]));*/
                intent.putExtra(TAG_DATA[i],data.getString(TAG_DATA[i]));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        startActivity(intent);

    }
}
