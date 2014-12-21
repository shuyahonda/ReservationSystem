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
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.User;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveCard;
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

        /*
        Card card = new Card(getActivity());
        card.setTitle("test");
        CardHeader header = new CardHeader(getActivity());
        card.addCardHeader(header);

        CardView cardView = (CardView)getActivity().findViewById(R.id.cardView);
        cardView.setCard(card);
        */
        for (int i = 0;i < 3; i++){
            Card card = new ReserveCard(getActivity());
            CardView cardView = (CardView)getActivity().findViewById(R.id.cardView);
            cardView.setCard(card);

        }


    }

    public void finishedReserveFetch(boolean success) {
        Log.i("ReserveListFragment.finishedReserveFetch()","予約の読込が終了しました");
        Log.i("reserve length",String.valueOf(this.reserveList.length()));
    }
}
