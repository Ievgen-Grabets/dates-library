package org.demodateslibrary.helpers;

import java.time.LocalDate;
import java.util.Set;

import org.demodateslibrary.exception.WrongDatesRange;

public interface WorkingDatesHelper {

    void addHolidays(Set<LocalDate> holidays);

    void addWeekends(Set<LocalDate> weekends);

    void addOtherNotWorkingDays(Set<LocalDate> notWorkingDays);

    int workDaysBetween(final LocalDate from, final LocalDate till) throws WrongDatesRange;

}
