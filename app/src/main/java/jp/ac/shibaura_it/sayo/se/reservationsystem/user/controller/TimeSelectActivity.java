package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.support.v7.app.ActionBar;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;

public class TimeSelectActivity extends ActionBarActivity {
    private Reserve reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);
        ButterKnife.inject(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff009688));

        Intent intent = getIntent();
        Reserve reserve = (Reserve)intent.getSerializableExtra("reserve");
        this.reserve = reserve;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(
                String.format("%d年%d月%d日（）",
                        this.reserve.getStartTime().get(Calendar.YEAR),
                        this.reserve.getStartTime().get(Calendar.MONTH) + 1,
                        this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH)
                ));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_select, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.startTimeButton)
    public void onStartTimeClick(View view) {
        Log.d("TimeSelectActivity","selected startButton");

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        // reserveに予約開始時間を設定する
                        Calendar startTime = reserve.getStartTime();
//                        startTime.set(hourOfDay,minute);
                        startTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        startTime.set(Calendar.MINUTE,minute);

                        reserve.setStartTime(startTime);


                        Button startTimeButton = (Button)findViewById(R.id.startTimeButton);
                        startTimeButton.setText(String.valueOf(hourOfDay) + ":" + String.format("%02d",minute));
                    }
                },0,0,true);
        dialog.setTitle("使用開始時間");
        dialog.show();
    }

    @OnClick(R.id.endTimeButton)
    public void onEndTimeClick(View view) {
        Log.d("TimeSelectActivity","selected endButton");

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        // reserveに予約終了時間を設定する
                        Calendar endTime = reserve.getEndTime();
//                        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hourOfDay,minute);
                        endTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        endTime.set(Calendar.MINUTE,minute);

                        reserve.setEndTime(endTime);

                        Button endTimeButton = (Button)findViewById(R.id.endTimeButton);
                        endTimeButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                        endTimeButton.setText(String.valueOf(hourOfDay) + ":" + String.format("%02d",minute));

                    }
                },0,0,true);

        dialog.setTitle("使用終了時間");
        dialog.show();
    }

    @OnClick(R.id.decisionButton)
    public void onDecisionButtonClick(View view) {
        Log.d("test","test");

        //画面遷移する
        //開始時間と終了時間を何とかして予約に設定する必要がある

        Intent intent = new Intent(this,ReserveCompleteActivity.class);
        intent.putExtra("reserve",reserve);
        startActivity(intent);
    }
}
