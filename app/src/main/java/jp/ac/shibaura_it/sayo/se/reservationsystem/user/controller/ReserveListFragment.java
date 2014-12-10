package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveDetailDialogFragment;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends Fragment {


    public ReserveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_list, container, false);
        ButterKnife.inject(this,view);
        return view;
    }

    @OnClick(R.id.dialogTestButton)
    public void OnClick(View view) {
        MaterialDialog mMaterialDialog = new MaterialDialog(this.getActivity())
                .setTitle("MaterialDialog")
                .setMessage("Hello world!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mMaterialDialog.dismiss();

                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();
    }
}
