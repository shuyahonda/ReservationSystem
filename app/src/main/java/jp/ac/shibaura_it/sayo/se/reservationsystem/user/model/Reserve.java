package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by Shuya on 14/12/10.
 * 予約記録
 *
 * @author Shuya
 */
public class Reserve implements Serializable {

    public interface ReserveCallbacks {
        public void didRegist(boolean success);
        public void didDelete(boolean success);
    }

    /**
     * Callback
     */
    private ReserveCallbacks reserveCallbacks;

    /**
     * 使用責任者
     * 利用している人とは別に入力させる
     */
    private String responsiblePerson;

    /**
     * 使用責任者の連絡先
     * 予約詳細入力画面で電話番号を入力させる
     */
    private String responsiblePersonContact;

    /**
     * 使用人数
     */
    private Integer peopleNum;

    /**
     * 学事課がチェック済みかどうか
     */
    private Boolean isManagerCheck;

    /**
     * 予約の利用者
     */
    private User user;

    /**
     * 使用開始時間
     */
    private Calendar startTime;

    /**
     * 使用終了時間
     */
    private Calendar endTime;


    /**
     * 使用目的
     */

    private String purpose;


    public void Reserve() {

    }

    /**
     * 予約記録を削除する
     * サーバと通信する
     * @return 削除が成功したかどうか
     */
    public void delete(ReserveCallbacks callback) {
    }

    /**
     * 予約記録をサーバに登録する
     * サーバと通信する
     * 通信終了時にcallback
     */
    public void regist(final ReserveCallbacks callback) {
        String url = "http://10.0.2.2:8080/rs/reserve/test";

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("key","test");

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;

        try {
            //パラメータ設定
            jsonParams.put("responsiblePerson", this.getResponsiblePerson());
            jsonParams.put("contactOfresponsiblePerson", this.getResponsiblePersonContact());
            jsonParams.put("isManagerCheck", "false");
            jsonParams.put("peopleNum", 0);
            jsonParams.put("room","5号館第1会議室");

            JSONObject time = new JSONObject();
            time.put("start","2013/12/24 10:00"); // 2013/12/24 10:00
            time.put("end","2013/12/24 12:00");   // 2013/12/24 12:00

            jsonParams.put("time",time);

            JSONObject user = new JSONObject();
            //ユーザー情報を保持する部分ができていない
            user.put("lastName","本田");
            user.put("firstName","修也");
            user.put("mailAddress","bp12110@shibaura-it.ac.jp");
            user.put("affiliation","申請者");

            jsonParams.put("user",user);

            entity = new StringEntity(jsonParams.toString());

        } catch (JSONException ex) {

        } catch (UnsupportedEncodingException ex) {

        }

        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        client.post(null, url, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Reserve.regist()","登録に成功しました");
                        callback.didRegist(true);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Reserve.regist()","登録に失敗しました");
                        callback.didRegist(false);
                    }
                });
    }

    /**
     * 予約記録をサーバに登録するために必要な情報が全て登録されているか判定する
     * @return 予約記録に不備がないかどうか
     */
    private Boolean isInputCheckError(){
        return false;
    }

    // Getter and Setter

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePersonContact() {
        return responsiblePersonContact;
    }

    public void setResponsiblePersonContact(String responsiblePersonContact) {
        this.responsiblePersonContact = responsiblePersonContact;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Boolean getIsManagerCheck() {
        return isManagerCheck;
    }

    public void setIsManagerCheck(Boolean isManagerCheck) {
        this.isManagerCheck = isManagerCheck;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
