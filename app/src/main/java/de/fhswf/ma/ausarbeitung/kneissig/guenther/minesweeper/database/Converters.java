package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database;


import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Evtl kann man die Nutzen um einen Spielstand zu speichern aber kp.
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
