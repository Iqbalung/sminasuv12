package traspac.simansuv1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class HeaderSuratMasuk extends Fragment {
    View view;
    public TextView t;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_header_surat_masuk,container,false);
        t = (TextView) view.findViewById(R.id.txtTitle);
        return view;
    }

}
