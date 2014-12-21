package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


/**
 * Created by Shuya on 14/12/10.
 * 予約記録簿
 * 予約記録の取得などはこのクラスを通して行う
 */
public class ReserveList {

    public interface ReserveListCallbacks {
        public void finishedReserveFetch(boolean success);
    }

    private static final String BASE_URL = "http://10.0.2.2:8080/rs/reserve";
    /**
     * このクラス内のメソッドはすべてこのリストを更新する
     */
    private ArrayList<Reserve> reserves = new ArrayList<Reserve>();

    /**
     * コールバック
     */
    private ReserveListCallbacks callbacks;

    /**
     * 通信用
     */
    private AsyncHttpClient client = new AsyncHttpClient();

    public void ReserveList(ReserveListCallbacks callback) {
        this.callbacks = callback;
    }

    /**
     * 特定の日付の予約だけを取得する
     * 学事課や守警が使う
     *
     * @param calendar
     */
    public void fetchAllReserve(int year, int month, int day) {

    }

    /**
     * 特定の日付・会議室の予約だけを取得する
     * 申請者が時間選択画面で既存予約を表示する際に使う
     *
     * @param calendar
     * @param roomName
     */
    public void fetchAllReserve(int year, int month, int day, String roomName) {

    }

    /**
     * 利用者の全ての予約を取得する
     * 申請者だけが使うメソッド
     *
     * @param user
     */
    public void fetchAllReserve(final ReserveListCallbacks callback, User user) {
        final String URL = "/my";
        //メールアドレスの保存が出来ていないので固定
        final String MAIL_ADDRESS = "bp12110@shibaura-it.ac.jp";

        RequestParams params = new RequestParams();
        params.put("mailAddress",MAIL_ADDRESS);

        this.client.get(BASE_URL + URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String strJson = new String(responseBody,"UTF-8");
                    HashMap<String,Object> map;
                    System.out.println(strJson);

                    JSONObject json = new JSONObject(strJson);
                    JSONArray list = json.getJSONArray("data");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject reserveJson = list.getJSONObject(i);
                        Reserve reserve = new Reserve();
                        initJsonObjectToReserve(reserveJson,reserve);
                        reserves.add(reserve);
                    }

                } catch (UnsupportedEncodingException ex) {

                } catch (JSONException ex) {

                }
                callback.finishedReserveFetch(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.finishedReserveFetch(false);
            }
        });
    }

    private void initJsonObjectToReserve(JSONObject jsonObject, Reserve reserve) {
        try {
            reserve.setResponsiblePerson(jsonObject.getString("responsiblePerson"));
            reserve.setIsManagerCheck(jsonObject.getBoolean("isManagerCheck"));
            reserve.setPeopleNum(jsonObject.getInt("peopleNum"));
            reserve.setResponsiblePersonContact(jsonObject.getString("contactOfResponsiblePerson"));
            reserve.setRoom(jsonObject.getString("room"));
            reserve.setRequestDayString(jsonObject.getString("requestDay"));

            JSONObject user = jsonObject.getJSONObject("user");
            reserve.setUser(new User(user.getString("mailAddress"), user.getString("affiliation"), user.getString("lastName"), user.getString("firstName")));

            JSONObject time = jsonObject.getJSONObject("time");
            reserve.setStartTimeString(time.getString("start"));
            reserve.setEndTimeString(time.getString("end"));
        } catch (JSONException ex) {

        }
    }

    public int length() {
        return this.reserves.size();
    }

    public Reserve get(int index) {
        return this.reserves.get(index);
    }
}
