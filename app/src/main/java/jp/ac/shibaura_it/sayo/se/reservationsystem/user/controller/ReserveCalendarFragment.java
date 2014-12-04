package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveCalendarFragment extends Fragment implements OnDateChangeListener {
    @InjectView(R.id.calendarView)
    CalendarView calendarView;

    public ReserveCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserve_calendar, container, false);
        ButterKnife.inject(this,view);
        /*
        // TODO : ButterKnifeがCalendarViewに対応していないため
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 日付が選択されたとき画面遷移
                Intent intent = new Intent(ReserveCalendarFragment.this,TimeSelectActivity.class);
                startActivity(intent);
            }
        });
            */

        calendarView.setOnDateChangeListener(this);
        return view;
    }

    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent(this,TimeSelectActivity.class);
        startActivity(intent);
    }
}
