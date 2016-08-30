package traspac.simansuv1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UI-21 on 24/08/2016.
 */
public class AdapterRiwayatDisposisi extends ArrayAdapter<RiwayatDisposisi> {
    private Context context;
    private int layout;
    private ArrayList<RiwayatDisposisi> data;


    public AdapterRiwayatDisposisi(Context context, int daftar_riwayat_disposisi, ArrayList<RiwayatDisposisi> data_riwayat_disposisi) {
        super(context,daftar_riwayat_disposisi,data_riwayat_disposisi);
        this.context = context;
        this.layout = daftar_riwayat_disposisi;
        this.data = data_riwayat_disposisi;
    }


    protected class ViewHolder
    {
        TextView tv_dari,tv_jam,tv_tanggal;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,parent,false);
        }

        holder.tv_dari = (TextView) convertView.findViewById(R.id.tv_dari);
        holder.tv_jam = (TextView) convertView.findViewById(R.id.tv_jam);
        holder.tv_tanggal = (TextView) convertView.findViewById(R.id.tv_tanggal);

        holder.tv_dari.setText(data.get(position).getDari());
        holder.tv_jam.setText(data.get(position).getJam());
        holder.tv_tanggal.setText(data.get(position).getTgl_disposisi());
        Log.d("Data",data.get(position).getDari());
        return convertView;
    }
}
