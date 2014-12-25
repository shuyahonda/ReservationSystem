package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.model.Reserve;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility.Utility;

public class ReserveCompleteActivity extends ActionBarActivity implements Reserve.ReserveCallbacks, DialogInterface.OnClickListener {
    private Reserve reserve;
    private ProgressDialog progressDialog;

    @InjectView(R.id.purpose)
    public EditText purposeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_complete);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff009688));

        ButterKnife.inject(this);

        reserve = (Reserve)getIntent().getSerializableExtra("reserve");
        progressDialog = new ProgressDialog(this);

        //日時を表示
        TextView useDay = (TextView)findViewById(R.id.useDayTextView);
        String resYear = String.valueOf(this.reserve.getStartTime().get(Calendar.YEAR));
        String resMonth = String.valueOf(this.reserve.getStartTime().get(Calendar.MONTH) + 1);
        String resDay = String.valueOf(this.reserve.getStartTime().get(Calendar.DAY_OF_MONTH));

        useDay.setText(String.format("%s年%s月%s日（%s）",
                resYear,
                resMonth,resDay,
                Utility.getDayOfWeek(this.reserve.getStartTime().get(Calendar.DAY_OF_WEEK))));

        //使用時間を表示
        TextView useTime = (TextView)findViewById(R.id.useTimeTextView);
        String startTime = String.format("%d:%02d",reserve.getStartTime().get(Calendar.HOUR_OF_DAY),reserve.getStartTime().get(Calendar.MINUTE));
        String endTime = String.format("%d:%02d",reserve.getEndTime().get(Calendar.HOUR_OF_DAY),reserve.getEndTime().get(Calendar.MINUTE));
        useTime.setText(String.format("%s ~ %s",startTime,endTime));

        //申請日を表示
        TextView requestDay = (TextView)findViewById(R.id.requestDayTextView);
        Calendar calendar = Calendar.getInstance();
        String year = String.format("%s",calendar.get(Calendar.YEAR));
        String month = String.format("%s",calendar.get(Calendar.MONTH) + 1);
        String day = String.format("%s",calendar.get(Calendar.DAY_OF_MONTH));
        String dayOfWeek = String.format("%s",calendar.get(Calendar.DAY_OF_WEEK)); //曜日

        TextView room = (TextView)findViewById(R.id.room);
        room.setText(this.reserve.getRoom());

        requestDay.setText(String.format("%s年%s月%s日（%s）",
                year,
                month,
                day,
                Utility.getDayOfWeek(this.reserve.getStartTime().get(Calendar.DAY_OF_WEEK))));
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
    public void onDecisionClick(View view) {
        this.progressDialog.setTitle("登録");
        this.progressDialog.setMessage("予約情報を登録中です");
        this.progressDialog.show();

        this.reserve.setPurpose(this.purposeEditText.getText().toString());
        this.reserve.regist(this);
    }

    public void didRegist(boolean success) {
        this.progressDialog.dismiss();

        if (success) {
            Log.d("test","通信に成功しました");

            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("予約登録が完了しました")
                    .setPositiveButton("OK", this)
                    .show();

        } else {
            Log.d("test","通信に失敗しました");

            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("予約登録することができませんでした")
                    .setPositiveButton("OK", this)
                    .show();
        }
    }

    public void onClick(DialogInterface i, int hoge) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void didDelete(boolean success) {

    }

    @OnTextChanged(R.id.responsiblePerson)
    public void responsiblePersonChanged(CharSequence s, int start, int before, int count) {
        this.reserve.setResponsiblePerson(s.toString());
    }

    @OnTextChanged(R.id.responsiblePersonContact)
    public void responsiblePersonContactChanged(CharSequence s, int start, int before, int count) {
        this.reserve.setResponsiblePersonContact(s.toString());
    }

    @OnTextChanged(R.id.purpose)
    public void purposeChanged(CharSequence s, int start, int before, int count) {
        this.reserve.setPurpose(s.toString());
    }
}
