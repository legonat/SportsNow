package ru.legonat.sportsnow;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getDateDifference(Date thenDate) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        now.setTime(new Date());
        then.setTime(thenDate);


        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;

        // Calculate difference in seconds
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffMinutes < 60) {
            if (diffMinutes == 1)
                return diffMinutes + " ������ �����";
            else
                return diffMinutes + " ���. �����";
        } else if (diffHours < 24) {
            if (diffHours == 1)
                return diffHours + " ��� �����";
            else
                return diffHours + " �. �����";
        } else if (diffDays < 30) {
            if (diffDays == 1)
                return diffDays + " ���� �����";
            else
                return diffDays + " �. �����";
        } else {
            return "����� �����...";

        }

    }


}
