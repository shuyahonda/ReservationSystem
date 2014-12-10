package jp.ac.shibaura_it.sayo.se.reservationsystem.user.model;

/**
 * Created by Shuya on 14/12/10.
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
