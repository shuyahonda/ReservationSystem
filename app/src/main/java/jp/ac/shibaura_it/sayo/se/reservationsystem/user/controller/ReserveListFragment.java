package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

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
        return inflater.inflate(R.layout.fragment_reserve_list, container, false);
    }

    @OnClick(R.id.dialogTestButton)
    public void OnClick(View view) {
        ReserveDetailFragment fragment = new ReserveDetailFragment();
        fragment.show(this.getFragmentManager(),"test");
    }
}
