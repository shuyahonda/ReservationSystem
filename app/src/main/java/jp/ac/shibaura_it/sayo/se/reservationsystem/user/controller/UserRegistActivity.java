package jp.ac.shibaura_it.sayo.se.reservationsystem.user.controller;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.ac.shibaura_it.sayo.se.reservationsystem.user.R;

public class UserRegistActivity extends ActionBarActivity {
    @InjectView(R.id.editTextFirstName)
    public EditText firstName;

    @InjectView(R.id.editTextLastName)
    public EditText lastName;

    @InjectView(R.id.editTextMailAddress)
    public EditText mailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_regist);
        ButterKnife.inject(this);

        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff009688));

        SharedPreferences pref = getSharedPreferences("name_and_address", MODE_PRIVATE);
        this.firstName.setText(pref.getString("FIRST_NAME",""));
        this.lastName.setText(pref.getString("LAST_NAME",""));
        this.mailAddress.setText(pref.getString("MAIL_ADDRESS",""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_regist, menu);
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

    @OnClick(R.id.userRegist)
    public void onClickRegist(View view) {
        SharedPreferences pref = getSharedPreferences("name_and_address", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("FIRST_NAME",this.firstName.getText().toString());
        editor.putString("LAST_NAME",this.lastName.getText().toString());
        editor.putString("MAIL_ADDRESS",this.mailAddress.getText().toString());
        editor.commit();

        Log.i("PrefSaves", pref.getString("FIRST_NAME",""));
        Log.i("PrefSaves", pref.getString("LAST_NAME",""));
        Log.i("PrefSaves", pref.getString("MAIL_ADDRESS",""));

        this.finish();
    }
}
