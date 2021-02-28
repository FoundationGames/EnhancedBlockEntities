package foundationgames.enhancedblockentities.util;

import java.util.Calendar;

public class DateUtil {
    public static boolean isChristmas() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26);
    }
}
