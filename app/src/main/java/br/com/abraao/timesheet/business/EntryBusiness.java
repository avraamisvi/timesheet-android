package br.com.abraao.timesheet.business;

import com.activeandroid.query.Select;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.abraao.timesheet.entities.Entry;

/**
 * Created by abraao on 10/26/15.
 */
public class EntryBusiness {

    public static void checkIn() {

        checkOut();

        Date date = new Date();
        Entry entry = new Entry();
        entry.date = date.getTime();

        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.setTime(date);

        entry.startHour = cal.get(GregorianCalendar.HOUR_OF_DAY);
        entry.startMinute = cal.get(GregorianCalendar.MINUTE);

        entry.endHour = -1;
        entry.endMinute = -1;

        entry.year = cal.get(GregorianCalendar.YEAR);
        entry.month = cal.get(GregorianCalendar.MONTH);
        entry.day = cal.get(GregorianCalendar.DAY_OF_MONTH);

        entry.save();
    }

    public static void checkOut() {

        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.setTime(new Date());

        long year = cal.get(GregorianCalendar.YEAR);
        long month = cal.get(GregorianCalendar.MONTH);
        long day = cal.get(GregorianCalendar.DAY_OF_MONTH);

        Entry entry = new Select().from(Entry.class).where("year = ?").and("month = ?").and("day = ?").and("endHour = -1", year, month, day).executeSingle();

        if (entry != null) {
            entry.endHour = cal.get(GregorianCalendar.HOUR_OF_DAY);
            entry.endMinute = cal.get(GregorianCalendar.MINUTE);

            entry.save();
        }
    }

    public static List<Entry> listByDate() {
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.setTime(new Date());

        long year = cal.get(GregorianCalendar.YEAR);
        long month = cal.get(GregorianCalendar.MONTH);
        long day = cal.get(GregorianCalendar.DAY_OF_MONTH);

        return new Select().from(Entry.class).where("year = ?").and("month = ?").and("day = ?", year, month, day).execute();
    }
}
