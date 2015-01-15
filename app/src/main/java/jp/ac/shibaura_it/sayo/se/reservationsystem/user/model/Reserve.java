package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility.Utility;

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
     * 申請日
     */
    private Calendar requestDay = Calendar.getInstance();

    /**
     * 使用目的
     */
    private String purpose;

    /**
     * 使用会議室
     */
    private String room;


    public void Reserve() {
    }

    /**
     * 予約記録を削除する
     * サーバと通信する
     * @return 削除が成功したかどうか
     */
    public void delete(final ReserveCallbacks callback) {
        //String url = "http://10.0.2.2:8080/rs/reserve/delete";    // Emulator
        //String url = "http://172.30.60.156:8080/ReservationSystemServer/reserve"; //竹内
        String url = "http://172.30.54.138:8080/rs/reserve/delete"; // 実機から
        //String url =  "http://android.sayo.se.shibaura-it.ac.jp/ReservationSystemServlet/reserve/delete"; //本番用

        AsyncHttpClient client = new AsyncHttpClient();

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;

        try {
            //パラメータ設定
            jsonParams.put("responsiblePerson", this.getResponsiblePerson());
            jsonParams.put("contactOfResponsiblePerson", this.getResponsiblePersonContact());
            jsonParams.put("isManagerCheck", "false");
            jsonParams.put("peopleNum", 0); //まだできていないので固定
            jsonParams.put("room",this.getRoom());
            jsonParams.put("purpose",this.getPurpose());

            jsonParams.put("requestDay", Utility.formatDateJsonStyle(this.requestDay));

            //時間を整形して渡す必要がある
            JSONObject time = new JSONObject();
            time.put("start", Utility.formatDateJsonStyle(this.startTime));
            time.put("end", Utility.formatDateJsonStyle(this.endTime));
            jsonParams.put("time",time);

            JSONObject user = new JSONObject();
            //ユーザー情報を保持する部分ができていない（後回しにして固定）
            user.put("lastName","本田");
            user.put("firstName","修也");
            user.put("mailAddress","bp12110@shibaura-it.ac.jp");
            user.put("affiliation","申請者");
            jsonParams.put("user",user);

            entity = new StringEntity(jsonParams.toString(),"UTF-8");
        } catch (JSONException ex) {

        } catch (UnsupportedEncodingException ex) {

        }

        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        client.post(null, url, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d("Reserve.regist()","削除に成功しました");
                        callback.didDelete(true);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("Reserve.regist()","削除に失敗しました");
                        callback.didDelete(false);
                    }
                });
    }

    /**
     * 予約記録をサーバに登録する
     * サーバと通信する
     * 通信終了時にcallback
     */
    public void regist(final ReserveCallbacks callback) {
        //String url = "http://10.0.2.2:8080/rs/reserve";    // Emulator
        //String url = "http://172.30.60.156:8080/ReservationSystemServer/reserve"; //竹内
        String url = "http://172.30.54.138:8080/rs/reserve"; // 実機から
        //String url =  "http://android.sayo.se.shibaura-it.ac.jp/ReservationSystemServlet/reserve"; //本番用

        AsyncHttpClient client = new AsyncHttpClient();

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;

        try {
            //パラメータ設定
            jsonParams.put("responsiblePerson", this.getResponsiblePerson());
            jsonParams.put("contactOfResponsiblePerson", this.getResponsiblePersonContact());
            jsonParams.put("isManagerCheck", "false");
            jsonParams.put("peopleNum", 0); //まだできていないので固定
            jsonParams.put("room",this.getRoom()); //まだできていないので固定
            jsonParams.put("purpose",this.getPurpose());

            jsonParams.put("requestDay", Utility.formatDateJsonStyle(this.requestDay));

            //時間を整形して渡す必要がある
            JSONObject time = new JSONObject();
            time.put("start", Utility.formatDateJsonStyle(this.startTime));
            time.put("end", Utility.formatDateJsonStyle(this.endTime));
            jsonParams.put("time",time);

            JSONObject user = new JSONObject();
            //ユーザー情報を保持する部分ができていない（後回しにして固定）
            user.put("lastName","本田");
            user.put("firstName","修也");
            user.put("mailAddress","bp12110@shibaura-it.ac.jp");
            user.put("affiliation","申請者");
            jsonParams.put("user",user);

            entity = new StringEntity(jsonParams.toString(),"UTF-8");
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

    public String getStartTimeHourOnly () {
        return String.format("%d:%02d",this.startTime.get(Calendar.HOUR_OF_DAY),this.startTime.get(Calendar.MINUTE));
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public void setStartTimeString(String startTime) {
        // string format -> 2014/12/17 01:18:25
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;

        try {
            date = sdf.parse(startTime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.startTime = cal;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public String getEndTimeHourOnly() {
        return String.format("%d:%02d",this.endTime.get(Calendar.HOUR_OF_DAY),this.endTime.get(Calendar.MINUTE));
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public void setEndTimeString(String endTime) {
        // string format -> 2014/12/17 01:18:25
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;

        try {
            date = sdf.parse(endTime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.endTime = cal;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Calendar getRequestDay() {
        return requestDay;
    }

    public void setRequestDay(Calendar requestDay) {
        this.requestDay = requestDay;
    }

    public void setRequestDayString(String requestDay) {
        // string format -> 2014/12/17 01:18:25
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;

        try {
            date = sdf.parse(requestDay);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.requestDay = cal;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
