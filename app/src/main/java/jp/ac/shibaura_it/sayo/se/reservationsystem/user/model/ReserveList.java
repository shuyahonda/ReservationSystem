package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shuya on 14/12/10.
 * 予約記録簿
 * 予約記録の取得などはこのクラスを通して行う
 */
public class ReserveList {
    /**
     * このクラス内のメソッドはすべてこのリストを更新する
     */
    private ArrayList<Reserve> reserves;

    public void ReserveList() {

    }

    /**
     * 特定の日付の予約だけを取得する
     * 学事課や守警が使う
     *
     * @param calendar
     */
    public void fetchAllReserve(Calendar calendar) {

    }

    /**
     * 特定の日付・会議室の予約だけを取得する
     * 申請者が時間選択画面で既存予約を表示する際に使う
     *
     * @param calendar
     * @param roomName
     */
    public void fetchAllReserve(Calendar calendar, String roomName) {

    }

    /**
     * 利用者の全ての予約を取得する
     * 申請者だけが使うメソッド
     *
     * @param user
     */
    public void fetchAllReserve(User user) {

    }
}
