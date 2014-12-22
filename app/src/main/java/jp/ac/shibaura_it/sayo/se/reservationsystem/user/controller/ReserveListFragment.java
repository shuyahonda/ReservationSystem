package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.app.ProgressDialog;
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
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.User;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveListFragment extends Fragment implements ReserveList.ReserveListCallbacks {
    /**
     * 予約リスト
     * 取得はここを通して行う
     */
    private ReserveList mReserveList = new ReserveList();

    private ArrayList<Card> mCards;
    private CardArrayAdapter mCardArrayAdapter;
    private ProgressDialog progressDialog;


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
            Card card = new ReserveCard(getActivity(), mReserveList.get(i));
            mCards.add(card);
        }

        this.progressDialog.dismiss();
    }
}
