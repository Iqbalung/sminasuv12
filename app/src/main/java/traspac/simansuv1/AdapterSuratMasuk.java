package traspac.simansuv1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class AdapterSuratMasuk extends ArrayAdapter<SuratMasuk>
{

    private Context mContext;
    private int mLayout;
    private ArrayList<SuratMasuk> data;

    public AdapterSuratMasuk(Context context, int daftar_surat_masuk, ArrayList<SuratMasuk> dataSuratMasuk)
    {
        super(context,daftar_surat_masuk,dataSuratMasuk);
        this.mContext = context;
        this.mLayout = daftar_surat_masuk;
        this.data = dataSuratMasuk;
    }

    protected class ViewHolder
    {
        TextView txtDari,txtTanggal,txtDisposisi,txtJam,txtHal,txtSifat,txtNoSurat;
        ImageView imgDispo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = new ViewHolder();

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        }

        /*int length = hAl.length();
        if (length > 16){
            holder.txtTitik.setText("...");
        }*/

        holder.txtDari = (TextView) convertView.findViewById(R.id.txtNamaPengirim);
        holder.txtNoSurat = (TextView) convertView.findViewById(R.id.txtNomor);
        holder.txtDisposisi = (TextView) convertView.findViewById(R.id.txtDisposisi);
        holder.txtJam = (TextView) convertView.findViewById(R.id.txtJam);
        holder.txtTanggal = (TextView) convertView.findViewById(R.id.txtTanggal);
        holder.txtHal = (TextView) convertView.findViewById(R.id.txtJudul);
        holder.txtSifat = (TextView) convertView.findViewById(R.id.txtStatus);
        holder.imgDispo = (ImageView) convertView.findViewById(R.id.imgDispo);

        holder.txtDari.setTypeface(null, data.get(position).getDibaca());
        holder.txtDari.setText(data.get(position).getDari());
        holder.txtNoSurat.setText(data.get(position).getNosurat());
        holder.txtDisposisi.setText(data.get(position).getDisposisi());
        holder.txtJam.setText(data.get(position).getJam());
        holder.txtTanggal.setText(data.get(position).getTanggal());
        holder.txtHal.setText(data.get(position).getHal());
        holder.txtSifat.setText(data.get(position).getSifat());
        holder.imgDispo.setVisibility(data.get(position).getVisible());

        convertView.setTag(holder);
        return convertView;
    }

    private boolean isTooLarge (TextView text, String newText) {
        float textWidth = text.getPaint().measureText(newText);
        return (textWidth >= text.getMeasuredWidth ());
    }

}
