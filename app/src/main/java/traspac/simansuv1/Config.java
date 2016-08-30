package traspac.simansuv1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ALLIANCES on 8/11/2016.
 */
public class Config {

    /*public static final String URL_PATH = "http://192.168.0.56/android/simansu/service_simansu.php?act=";
    public static final String URL_LOGIN = URL_PATH+"login";
    public static final String URL_GET_DATA = URL_PATH+"get_surat_masuk&dari=";
    public static final String URL_GET_BACA_SURAT = URL_PATH+"baca_surat";
    public static final String URL_GET_RIWAYAT_DISPOSISI = URL_PATH+"get_riw_surat";*/

    //URL
    public static final String URL_PATH = "http://192.168.1.11/mako/sms_mako/index.php/service/";
    public static final String URL_PATH1 = "http://192.168.1.103/app/index.php/service/";
    public static final String URL_LOGIN = URL_PATH+"do_login";
    public static final String URL_GET_DATA = URL_PATH+"kotak_masuk?v_userid=";
    public static final String URL_GET_BACA_SURAT = URL_PATH+"lihat_baca_surat";
    public static final String URL_GET_FILE_LAMPIRAN = URL_PATH+"service_simansu.php?act=get_file";
    public static final String URL_GET_TUJUAN_DISPO = "http://192.168.1.103/android/service_simansu.php?act=get_tujuan_dispo";
    public static final String URL_GET_TINDAKAN_DISPO = URL_PATH1+"get_tindakan_disposisi";
    public static final String URL_SIMPAN_DISPOSISI = URL_PATH1+"simpan_disposisi";
    public static final String URL_GET_RIWAYAT_DISPOSISI = URL_PATH+"riwayat_disposisi";


    /*public static final String URL_GET_BACA_SURAT = URL_PATH+"lihat_baca_surat";*/


    public static final String TAG_JSON_ARRAY = "data";



    //TAG Login
    public static final String TAG_ID_USER = "userid";
    public static final String TAG_USERNAME = "uname";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_PASSWORD = "pwd";
    public static final String LOGIN_SUCCESS = "success";


    //Tag Surat Masuk
    public static final String TAG_SURAT_ID = "surat_id";
    public static final String TAG_JENIS_ID = "jenis_id";
    public static final String TAG_REVISI_ID = "revisi_id";
    public static final String TAG_JENIS = "jenis";
    public static final String TAG_KEPADA = "kepada";
    public static final String TAG_KLASIFIKASI = "kodeklasifikasi";
    public static final String TAG_TANGGAL_REMITEN = "tgl_remitten";
    public static final String TAG_DARI = "dari";
    public static final String TAG_NOSURAT = "nosurat";
    public static final String TAG_JAM = "tgl_";
    public static final String TAG_HAL = "hal";
    public static final String TAG_TANGGAL = "tgl";
    public static final String TAG_TANGGAL_SURAT = "tgl_surat";
    public static final String TAG_TANGGAL_TERIMA = "tgl_terima";
    public static final String TAG_DISPOSISI = "terdisposisi";
    public static final String TAG_SIFAT = "sifat";
    public static final String TAG_LAMPIRAN = "lampiran";
    public static final String TAG_LAMPIRAN_FILE = "nama_file";
    public static final String TAG_DIBACA = "dibaca";
    public static final String TAG_TINDAKAN = "tindakan";
    public static final String TAG_ISI = "isi";
    public static final String TAG_TEMBUSAN = "tembusan";
    public static final String TAG_REFERENSI = "referensi";
    public static final String TAG_JABATAN = "jabatan";

    //Tag V Disposisi
    public static final String TAG_V_USER_ID_TUJUAN = "v_useridtujuan";
    public static final String TAG_V_ENTRY_SURAT_ID = "v_entrysurat_id";
    public static final String TAG_V_DISPOSISI_ID = "v_disposisi_id";
    public static final String TAG_V_KEPADA = "v_kepada";
    public static final String TAG_V_TANGGAL_REMITEN = "v_tgl_remiten";
    public static final String TAG_V_ISI = "v_isi";
    public static final String TAG_V_TINDAKAN = "v_tindakan";
    public static final String TAG_V_FILE_ORIGINAL = "v_fileoriginal";
    public static final String TAG_V_FILE_RENAME = "v_filerename";
    public static final String TAG_V_FILE_SIZE = "v_filesize";
    public static final String TAG_V_USER_ID_PEMBUAT = "v_useridpembuat";


    //Tag Riwayat Disposisi
    public static final String TAG_DISPOSISI_ID = "disposisi_id";
    public static final String TAG_ENTRY_SURAT_ID = "entrysurat_id";
    public static final String TAG_TUJUAN = "tujuan";
    public static final String TAG_TANGGAL_DISPOSISI = "tgl_disposisi";
    public static final String TAG_TINDAKAN_DISPOSISI = "tindakandisposisi";










    public static final void alertView(String title,String pesan, Activity activity)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title)
                .setMessage(pesan)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }


    public static String lihat_baca_surat(String user_id,String surat_id,String jenis_id,String revisi_id)
    {
        String url = URL_GET_BACA_SURAT+"?user_id="+user_id+"&surat_id="+surat_id+"&revisi_id="+revisi_id+"&jenis_id="+jenis_id;
        return url;
    }


    private static boolean getKoneksi(Context context)
    {
        NetworkInfo koneksi = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (koneksi == null) {
            Log.d("Data","No Internet Connection");
            return false;
        } else {
            if(koneksi.isConnected()) {
                Log.d("Data"," internet connection available...");
                return true;
            } else {
                Log.d("Data"," internet connection");
                return true;
            }
        }
    }

    public static void checkKoneksi(Context context){
        if (!getKoneksi(context)){

            Toast.makeText(context,"Koneksi gagal terhubung",Toast.LENGTH_LONG).show();
        }

    }


}
