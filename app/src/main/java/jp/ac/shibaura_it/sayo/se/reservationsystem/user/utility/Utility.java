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
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH),
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        calendar.get(Calendar.SECOND)
        );

        return formattedString;
    }

}
