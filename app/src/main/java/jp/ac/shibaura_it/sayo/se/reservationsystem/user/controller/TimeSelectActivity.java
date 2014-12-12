package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.app.TimePickerDialog;
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
        reserve = new Reserve();
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
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hourOfDay,minute);

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
                        Calendar endTime = Calendar.getInstance();
                        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hourOfDay,minute);

                        reserve.setEndTime(endTime);

                        Button endTimeButton = (Button)findViewById(R.id.endTimeButton);
                        endTimeButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                        endTimeButton.setText(String.valueOf(hourOfDay) + ":" + String.format("%02d",minute));

                    }
                },0,0,true);

        dialog.setTitle("使用終了時間");
        dialog.show();
    }
}
