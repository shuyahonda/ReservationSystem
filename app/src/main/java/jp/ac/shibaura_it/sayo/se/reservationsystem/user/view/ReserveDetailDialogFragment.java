package jp.ac.shibaura_it.sayo.se.reservationsystem.user.view;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveDetailDialogFragment extends DialogFragment {


    public ReserveDetailDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserve_detail_dialog2, container, false);
    }


}
