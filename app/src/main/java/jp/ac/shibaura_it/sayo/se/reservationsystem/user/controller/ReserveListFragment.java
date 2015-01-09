package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.User;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveCard;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveDetailDialog;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends Fragment implements ReserveList.ReserveListCallbacks, ReserveCard.ReserveCancelListener, Reserve.ReserveCallbacks {
    /**
     * 予約リスト
     * 取得はここを通して行う
     */
    private ReserveList mReserveList = new ReserveList();

    private ArrayList<Card> mCards;
    private CardArrayAdapter mCardArrayAdapter;
    private ProgressDialog progressDialog;
    private ReserveDetailDialog reserveDetailDialog;


    public ReserveListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_list, container, false);
        ButterKnife.inject(this,view);

        this.mCards = new ArrayList<Card>();
        this.mCardArrayAdapter = new CardArrayAdapter(this.getActivity(), mCards);

        progressDialog = new ProgressDialog(getActivity());

        // Fragmentが表示されたときに処理をするため
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);

        this.progressDialog.show();
        mReserveList.fetchAllReserve(this, new User());
    }

    /*
    @OnClick(R.id.dialogTestButton)
    public void OnClick(View view) {
        mReserveList.fetchAllReserve(this, new User());

        this.progressDialog.setTitle("取得");
        this.progressDialog.setMessage("予約情報を取得中です");
        this.progressDialog.show();
    }
    */

    public void finishedReserveFetch(boolean success) {
        Log.i("ReserveListFragment.finishedReserveFetch()", "予約の読込が終了しました");
        mCards.clear();

        CardListView cardListView = (CardListView)getActivity().findViewById(R.id.cardListView);
        cardListView.setAdapter(mCardArrayAdapter);

        for (int i = 0; i < mReserveList.length(); i++) {
            Log.i("ReserveListFragment.finishedReserveFetch()","予約をリストに追加しました");
            ReserveCard card = new ReserveCard(getActivity(), mReserveList.get(i));
            card.setOnReserveCancelListener(this);
            final Reserve reserve = mReserveList.get(i);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    ReserveCard reserveCard = (ReserveCard)card;

                    // 予約詳細ダイアログを表示
                    reserveDetailDialog = new ReserveDetailDialog(getActivity(), reserve);
                    reserveDetailDialog.setTitle("予約詳細");
                    reserveDetailDialog.setPositiveButton("閉じる", new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            reserveDetailDialog.dismiss();
                        }
                    });


                    reserveDetailDialog.show();

                }
            });

            mCards.add(card);
        }

        this.progressDialog.dismiss();
    }

    public void onReserveCancel(Reserve reserve) {
        this.progressDialog.show();
        Log.i("ReserveCancel",reserve.toString());
        reserve.delete(this);
    }

    public void didDelete(boolean success) {
        if (success) {
            Log.i("ReserveDelete","予約をキャンセルしました");
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("確認")
                    .setMessage("予約をキャンセルしました")
                    .setPositiveButton("OK", null)
                    .show();

        } else {
            Log.i("ReserveDelete","予約を削除することができませんでした");
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("確認")
                    .setMessage("予約を削除することができませんでした")
                    .setPositiveButton("OK", null)
                    .show();

        }

        this.progressDialog.dismiss();
        this.mReserveList.fetchAllReserve(this, null);
    }

    public void didRegist(boolean success) {

    }
}
