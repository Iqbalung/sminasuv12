package traspac.simansuv1;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by UI-21 on 15/08/2016.
 */
public class DaftarLampiran extends Fragment implements AdapterView.OnItemClickListener {

    View view;
    private ListView listLampiran;
    private ArrayList<String> lampiran;
    JSONObject list_lampiran;
    String  file,stat,path_download,path_folder,daftar_lampiran;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daftar_lampiran, container, false);

        Bundle bundle = getArguments();
        daftar_lampiran = null;
        if (bundle != null) {
            daftar_lampiran = bundle.getString(Config.TAG_LAMPIRAN);
        }



        lampiran = new ArrayList<String>();
        listLampiran = (ListView) view.findViewById(R.id.lvDaftarLampiran);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_data, R.id.txtList, lampiran);
        listLampiran.setAdapter(adapter);
        listLampiran.setOnItemClickListener(this);
        getDaftarLampiran();
        return view;

    }

    private void getDaftarLampiran() {
        list_lampiran = new JSONObject();
        JSONArray data = null;
        try {
            data = new JSONArray(daftar_lampiran);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error",e.getMessage());
        }

        for (int i = 0; i < data.length(); i++) {
            String nama_file = null;
            try {
                nama_file = data.getJSONObject(i).getString(Config.TAG_LAMPIRAN_FILE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lampiran.add(nama_file);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       file = parent.getItemAtPosition(position).toString();
        /*Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Config.URL_PATH + file));
        startActivity(intent);*/
        path_folder = Environment.getExternalStorageDirectory().getPath()+"/Download/";
        path_download = path_folder+ file;

        new DownloadFileFromURL().execute(Config.URL_PATH+"lampiran/"+file);

    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(view.getContext());
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onCreateDialog(progress_bar_type);
        }


        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);


                OutputStream output = new FileOutputStream(path_download);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {

                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);

                }

                output.flush();

                output.close();
                input.close();
                stat = "success";
            } catch (Exception e) {
                stat = e.getMessage();
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {

            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            if (stat == "success"){
                Toast.makeText(view.getContext(),"Download success",Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                File directory = new File(path_folder);
                directory.mkdirs();
                File file_view = new File(path_download);
                intent.setDataAndType(Uri.fromFile(file_view), "*/*");


                PendingIntent pIntent = PendingIntent.getActivity(view.getContext(), 0, intent, 0);

                NotificationCompat.Builder notif = (NotificationCompat.Builder) new NotificationCompat.Builder(view.getContext());
                notif.setSmallIcon(R.drawable.ic_menu_camera);
                notif.setContentTitle("Download");
                notif.setContentText(file);
                notif.setAutoCancel(true);

                notif.setContentIntent(pIntent);
                NotificationManager notifManager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.notify("Notif Download",1,notif.getNotification());

            }else{
                Toast.makeText(view.getContext(),"Download failed \n" +stat,Toast.LENGTH_LONG).show();
            }

            pDialog.dismiss();

        }
    }
}

