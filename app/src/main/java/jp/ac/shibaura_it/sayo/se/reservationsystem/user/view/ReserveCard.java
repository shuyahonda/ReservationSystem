package jp.ac.shibaura_it.sayo.se.reservationsystem.user.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.gmariotti.cardslib.library.internal.Card;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

/**
 * Created by Shuya on 14/12/21.
 */
public class ReserveCard extends Card {
    private Reserve reserve;

    public ReserveCard(Context context) {
        this(context, R.layout.reserve_card_layout);
    }

    public ReserveCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        // No Header

        // Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card =", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

    }

}
