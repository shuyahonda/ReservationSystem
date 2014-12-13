package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;

public class ReserveCompleteActivity extends ActionBarActivity {
    private Reserve reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_complete);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff009688));

        ButterKnife.inject(this);

        reserve = (Reserve)getIntent().getSerializableExtra("reserve");

        //使用時間を表示
        TextView useTime = (TextView)findViewById(R.id.useTimeTextView);
        String startTime = String.format("%02d:%02d",reserve.getStartTime().get(Calendar.HOUR_OF_DAY),reserve.getStartTime().get(Calendar.MINUTE));
        String endTime = String.format("%02d:%02d",reserve.getEndTime().get(Calendar.HOUR_OF_DAY),reserve.getEndTime().get(Calendar.MINUTE));
        useTime.setText(String.format("%s ~ %s",startTime,endTime));

        //申請日を表示
        TextView requestDay = (TextView)findViewById(R.id.requestDayTextView);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_reserve_complete, menu);
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

    @OnClick(R.id.detailDecisionButton)
    public void onClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
