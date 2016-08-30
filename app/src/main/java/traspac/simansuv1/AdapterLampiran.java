package traspac.simansuv1;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by UI-21 on 30/08/2016.
 */
public class AdapterLampiran extends RecyclerView.Adapter<AdapterLampiran.ViewHolder> {
    private List<Lampiran> lampiran;
    private Context content;
    private final OnItemClickListener listener;

    public AdapterLampiran(Context daftarLampiran, List<Lampiran> data_lampiran, OnItemClickListener listener) {
        this.content = daftarLampiran;
        this.lampiran = data_lampiran;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_lampiran,null);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lampiran data = lampiran.get(position);
        holder.txtFileName.setText(data.getFileName());
        holder.bind(lampiran.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (null != lampiran ? lampiran.size() : 0);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFileSize,txtFileName;
        public ViewHolder(View itemView) {
            super(itemView);
            /*txtFileSize = (TextView) itemView.findViewById(R.id.txtFileSize);*/
            txtFileName = (TextView) itemView.findViewById(R.id.txtFileName);
        }
        public void bind(final Lampiran item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Lampiran item);
    }
}