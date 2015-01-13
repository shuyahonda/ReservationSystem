package jp.ac.shibaura_it.sayo.se.reservationsystem.user.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import it.gmariotti.cardslib.library.internal.Card;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility.Utility;

/**
 * Created by Shuya on 14/12/21.
 */
public class ReserveCard extends Card implements Button.OnClickListener {
    private ReserveCancelListener reserveCancelListener;
    private Reserve reserve;
    private TextView reserveCardDay;
    private TextView reserveCardPurpose;
    private TextView reserveCardRoom;
    private TextView reserveCardTime;

    public interface ReserveCancelListener{
        public void onReserveCancel(Reserve reserve);
    }

    public ReserveCard(Context context) {
        this(context, R.layout.reserve_card_layout);
    }

    public ReserveCard(Context context, Reserve reserve) {
        this(context, R.layout.reserve_card_layout);
        this.reserve = reserve;
    }

    public ReserveCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    public void setOnReserveCancelListener(ReserveCancelListener listener) {
        this.reserveCancelListener = listener;
    }

    private void init() {
        // Set a OnClickListener listener

        /*
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card =", Toast.LENGTH_LONG).show();

            }
        });
        */
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        this.reserveCardPurpose = (TextView)parent.findViewById(R.id.reserveCardPurpose);
        this.reserveCardDay = (TextView)parent.findViewById(R.id.reserveCardDay);
        this.reserveCardTime = (TextView)parent.findViewById(R.id.reserveCardTime);
        this.reserveCardRoom = (TextView)parent.findViewById(R.id.reserveCardRoom);

        this.reserveCardPurpose.setText(this.reserve.getPurpose());
        this.reserveCardRoom.setText(this.reserve.getRoom());
        this.reserveCardTime.setText(this.reserve.getStartTimeHourOnly() + " 〜 " + this.reserve.getEndTimeHourOnly());

        this.reserveCardDay.setText(String.format("%d年%d月%02d日（%s）",
                this.reserve.getStartTime().get(Calendar.YEAR),
                this.reserve.getStartTime().get(Calendar.MONTH) + 1,
                this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH),
                Utility.getDayOfWeek(this.reserve.getStartTime().get(Calendar.DAY_OF_WEEK))
        ));

        Button cancelButton = (Button)parent.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        parent.setBottom(10);
        view.setPadding(0,0,0,30);
    }

    public Reserve getReserve() {
        return this.reserve;
    }

    public void onClick(View view) {
        this.reserveCancelListener.onReserveCancel(this.reserve);
    }
}
