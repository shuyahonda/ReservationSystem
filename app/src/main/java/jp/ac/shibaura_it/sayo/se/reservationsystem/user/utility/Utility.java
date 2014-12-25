package jp.ac.shibaura_it.sayo.se.reservationsystem.user.utility;

import java.util.Calendar;

/**
 * Created by Shuya on 14/12/15.
 */
public class Utility {
    /**
     * return Format -> [2014/10/10 12:10:10]
     * @param calendar
     * @return
     */
    public static String formatDateJsonStyle (Calendar calendar) {
        String formattedString;

        formattedString = String.format("%02d/%02d/%02d %02d:%02d:%02d",
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH)+1,
                                        calendar.get(Calendar.DAY_OF_MONTH),
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        calendar.get(Calendar.SECOND)
        );

        return formattedString;
    }

    /**
     * 曜日を文字列で取得する
     */
    public static String getDayOfWeek(int day) {
        switch (day) {
            case 1 : return "日";
            case 2 : return "月";
            case 3 : return "火";
            case 4 : return "水";
            case 5 : return "木";
            case 6 : return "金";
            case 7 : return "土";
        }
        return null;
    }
}
