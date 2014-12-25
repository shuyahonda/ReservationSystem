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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Room;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility.Utility;

public class TimeSelectActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    private Reserve reserve;
    private ReserveList reserveList = new ReserveList();
    private ArrayList<Room> roomList = new ArrayList<Room>();


    @InjectView(R.id.roomSpinner)
    public Spinner roomSpiner;

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
                String.format("%d年%d月%d日（%s）",
                        this.reserve.getStartTime().get(Calendar.YEAR),
                        this.reserve.getStartTime().get(Calendar.MONTH) + 1,
                        this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH),
                        Utility.getDayOfWeek(this.reserve.getStartTime().get(Calendar.DAY_OF_WEEK))
                ));

        this.roomSpiner.setOnItemSelectedListener(this);

        this.initSpiner();
    }

    private void initSpiner() {
        //jsonからroomオブジェクトを生成してroomListに格納
        InputStream is = null;
        String json = null;
        int size;
        byte[] buffer;
        JSONObject roomsJson = null;
        JSONArray roomArray = null;

        try {
            is = getAssets().open("room.json");
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            roomsJson = new JSONObject(json);
            roomArray = roomsJson.getJSONArray("room");

            for (int i = 0; i < roomArray.length(); i++) {
                JSONObject roomJson = roomArray.getJSONObject(i);
                String name = roomJson.getString("name");
                int capacity = roomJson.getInt("capacity");
                Room room = new Room(name,capacity);
                this.roomList.add(room);
            }

            ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<Room>(this, R.layout.support_simple_spinner_dropdown_item, roomList);
            this.roomSpiner.setAdapter(arrayAdapter);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


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
        Intent intent = new Intent(this,ReserveCompleteActivity.class);
        intent.putExtra("reserve",reserve);
        startActivity(intent);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Room selectedRoom = (Room)roomSpiner.getSelectedItem();
        reserve.setRoom(selectedRoom.getName());
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
