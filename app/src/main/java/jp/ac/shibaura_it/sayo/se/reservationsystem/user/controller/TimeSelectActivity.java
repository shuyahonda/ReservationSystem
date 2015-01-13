package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.RectF;
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
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.ReserveList;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Room;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility.Utility;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveCard;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.view.ReserveDetailDialog;

public class TimeSelectActivity extends ActionBarActivity implements ReserveList.ReserveListCallbacks, AdapterView.OnItemSelectedListener, WeekView.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener  {
    private Reserve reserve;
    private ReserveList reserveList = new ReserveList();
    private ArrayList<Room> roomList = new ArrayList<Room>();
    private ProgressDialog progressDialog;

    private boolean isOnceCalled;

    private static final int DEFAULT_SELECTED_HOUR = 9;

    @InjectView(R.id.weekView)
    public WeekView mWeekView;

    private OneDayFragment oneDayFragment;

    @InjectView(R.id.roomSpinner)
    public Spinner roomSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);
        ButterKnife.inject(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff009688));
        progressDialog = new ProgressDialog(this);

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
        Room selectedRoom = (Room)roomSpiner.getSelectedItem();
        reserve.setRoom(selectedRoom.getName());

        //this.oneDayFragment = (OneDayFragment)getFragmentManager().findFragmentById(R.id.oneDayFragment);
        //this.oneDayFragment.addReserve(false,null);

        this.mWeekView.setOnEventClickListener(this);
        this.mWeekView.setMonthChangeListener(this);
        this.mWeekView.setEventLongPressListener(this);

        Calendar selectedDay = Calendar.getInstance();

        selectedDay.set(this.reserve.getStartTime().get(Calendar.YEAR),
                this.reserve.getStartTime().get(Calendar.MONTH),
                this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH)
        );

        Log.i("selectedDay", selectedDay.toString());
        this.mWeekView.goToDate(selectedDay);
        this.mWeekView.goToHour(DEFAULT_SELECTED_HOUR);
        //this.reserveList.fetchAllReserve(this, null);
        this.isOnceCalled = false;

        this.reserveList.fetchAllReserve(this,this.reserve.getStartTime().get(Calendar.YEAR),
                                         this.reserve.getStartTime().get(Calendar.MONTH),
                                         this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH),
                                         this.reserve.getRoom());
        this.progressDialog.show();


    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        //ここでイベントを返せばいい
        if (!isOnceCalled) {
            ArrayList<Reserve> reserves = this.reserveList.getReserves();
            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
            WeekViewEvent event;

            for (Reserve reserve : reserves) {
                Log.i("test", String.format("%d", reserves.size()));
                Log.i("onMonthChange", "Eventを追加しました");
                event = new WeekViewEvent(1, this.getEventTitle(reserve), reserve.getStartTime(), reserve.getEndTime());
                event.setColor(0xcaE57373);
                events.add(event);
            }

            Log.i("test", String.format("events.count %d", events.size()));

            this.isOnceCalled = true;
            return events;
        } else {
            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
            return events;

        }
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
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
        this.progressDialog.show();

        Room selectedRoom = (Room)roomSpiner.getSelectedItem();
        reserve.setRoom(selectedRoom.getName());

        this.reserveList.fetchAllReserve(this,this.reserve.getStartTime().get(Calendar.YEAR),
                this.reserve.getStartTime().get(Calendar.MONTH),
                this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH),
                this.reserve.getRoom());
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void finishedReserveFetch(boolean success) {
        if (success) {
            this.mWeekView.notifyDatasetChanged();
            Log.i("Test","既存予約の読み込みが完了しました");
        }
        this.progressDialog.dismiss();

        Calendar selectedDay = Calendar.getInstance();

        selectedDay.set(this.reserve.getStartTime().get(Calendar.YEAR),
                this.reserve.getStartTime().get(Calendar.MONTH),
                this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH)
        );
        this.mWeekView.goToDate(selectedDay);
        this.isOnceCalled = false;
    }

    private String getEventTitle(Reserve reserve) {
        return String.format("予約済 (%02d:%02d 〜 %02d:%02d)",reserve.getStartTime().get(Calendar.HOUR_OF_DAY),reserve.getStartTime().get(Calendar.MINUTE),reserve.getEndTime().get(Calendar.HOUR_OF_DAY),reserve.getEndTime().get(Calendar.MINUTE));
    }
}