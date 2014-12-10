package jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility;

/**
 * Created by Shuya on 14/12/11.
 */
public class ContentValues {
    private static ContentValues ourInstance = new ContentValues();

    public static ContentValues getInstance() {
        return ourInstance;
    }

    private ContentValues() {
    }
}
