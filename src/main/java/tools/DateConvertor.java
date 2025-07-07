package tools;

import com.github.mfathi91.time.PersianDate;

import java.time.LocalDate;

public class DateConvertor {
    public static LocalDate shamsiToMiladi(String shamsiDate){
        return PersianDate.parse(shamsiDate).toGregorian();
    }

    public static PersianDate miladiToShamsi(LocalDate miladiDate){
        return PersianDate.fromGregorian(miladiDate);
    }
}
