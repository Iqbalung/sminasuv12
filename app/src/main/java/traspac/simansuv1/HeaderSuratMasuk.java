package traspac.simansuv1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class HeaderSuratMasuk extends Fragment {
    FragmentActivity myContent;
    private TextView title1,title2,un_read;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContent =(FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.header_content,container,false);
        title1 = (TextView) view.findViewById(R.id.txtTitle1);
        title2 = (TextView) view.findViewById(R.id.txtTitle2);
        un_read = (TextView) view.findViewById(R.id.un_read);
        Bundle params = getArguments();
        if (params != null) {
            title2.setText("");
            un_read.setText("");
            title1.setText("Pengaturan");
            if (params.getString("Layout") == "chart") {
                title1.setText("Statistik");
            }
        }
        return view;
    }

}
