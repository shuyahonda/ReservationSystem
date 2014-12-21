package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.User;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends Fragment implements ReserveList.ReserveListCallbacks {
    /**
     * 予約リスト
     * 取得はここを通して行う
     */
    ReserveList reserveList = new ReserveList();

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
        reserveList.fetchAllReserve(this,new User());
        Log.i("test","testLoad");
    }

    public void finishedReserveFetch(boolean success) {
        Log.i("ReserveListFragment.finishedReserveFetch()","予約の読込が終了しました");
        Log.i("reserve length",String.valueOf(this.reserveList.length()));
    }
}
