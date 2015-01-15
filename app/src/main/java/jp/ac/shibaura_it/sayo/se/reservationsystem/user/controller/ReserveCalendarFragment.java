package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveCalendarFragment extends Fragment {
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

        // TODO : ButterKnifeがCalendarViewに対応していないため
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 日付が選択されたとき画面遷移
                Log.i("ReserveCalendarFragment", "onSelectedDayChange");
                Log.i("ReserveCalendarFragment", String.format("%d年%d月%d日",year,month,dayOfMonth));

                Intent intent = new Intent(getActivity(),TimeSelectActivity.class);
                Reserve reserve = new Reserve();

                Calendar startTime = Calendar.getInstance();
                startTime.set(year,month,dayOfMonth);
                reserve.setStartTime(startTime);

                Calendar endTime = Calendar.getInstance();
                endTime.set(year,month,dayOfMonth);
                reserve.setEndTime(endTime);

                intent.putExtra("reserve",reserve);
                startActivity(intent);
            }
        });

        return view;
    }
}
