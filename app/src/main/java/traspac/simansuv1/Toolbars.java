package traspac.simansuv1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by ALLIANCES on 8/27/2016.
 */
public class Toolbars extends Fragment implements View.OnClickListener {

    private FragmentActivity myContext;
    private View view;
    private int layout = R.layout.toolbar_search;
    private ImageButton btnSearch;
    private EditText etSearch;


    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        Bundle params = getArguments();
        if (params != null){
            if (params.get("Layout") == "chart") {
                layout = R.layout.toolbar_chart;
            }
        }
        view = inflater.inflate(layout,container,false);
        if (layout == R.layout.toolbar_search) {
            etSearch = (EditText) view.findViewById(R.id.etSearch);
            btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btnSearch) {
            DaftarSuratMasuk.search = etSearch.getText().toString();
            DaftarSuratMasuk.TAG_SEACRH = 1;
            Fragment frg = null;
            frg = myContext.getSupportFragmentManager().findFragmentByTag("Daftar Surat Masuk");
            FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }
    }
}
