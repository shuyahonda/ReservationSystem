package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

/**
 * Created by Shuya on 14/12/10.
 * ユーザー情報を保持する
 * 予約の登録時などに利用する
 */
public class User {
    private static final User user = new User();

    private String mailAddress;
    private String affiliation;
    private String lastName;
    private String firstName;

    public void User() {
        //ここで初期化処理

    }

    public static User getInstance() {
        return user;
    }
}
