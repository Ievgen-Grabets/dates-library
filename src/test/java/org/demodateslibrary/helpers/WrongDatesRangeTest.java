package org.demodateslibrary.helpers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import org.demodateslibrary.exception.WrongDatesRange;
import org.junit.Test;

public class WrongDatesRangeTest {

    /**
     * Final test from description
     */
    @Test
    public void passingByExampleFromDescription() throws WrongDatesRange {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
                .withLocale(Locale.ENGLISH);

        LocalDate holiday1 = LocalDate.parse("July 1, 2022", formatter);
        LocalDate holiday2 = LocalDate.parse("July 2, 2022", formatter);
        LocalDate weekend1 = LocalDate.parse("July 3, 2022", formatter);

        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(
                Set.of(holiday1, holiday2),
                Set.of(weekend1));

        assertEquals("By example we should have 5 days",
                     5,
                     workingDatesHelper.workDaysBetween(
                             LocalDate.parse("June 27, 2022", formatter),
                             LocalDate.parse("July 4, 2022", formatter)));
    }

    @Test(expected = WrongDatesRange.class)
    public void passingFromAsNullValue() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(Collections.emptySet(), Collections.emptySet());
        workingDatesHelper.workDaysBetween(null, LocalDate.now());
    }

    @Test(expected = WrongDatesRange.class)
    public void passingTillAsNullValue() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(Collections.emptySet(), Collections.emptySet());
        workingDatesHelper.workDaysBetween(LocalDate.now(), null);
    }

    @Test(expected = WrongDatesRange.class)
    public void passingFromBAfterTill() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(Collections.emptySet(), Collections.emptySet());
        workingDatesHelper.workDaysBetween(LocalDate.now().plusDays(2), LocalDate.now());
    }

    @Test
    public void oneDayAndOneHoliday() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(Set.of(LocalDate.now()), Collections.emptySet());
        assertEquals("This day is holiday",
                     0, workingDatesHelper.workDaysBetween(LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void tenDaysAndOneHolidayAndTwoDaysAsWeekend() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(
                Set.of(LocalDate.now().plusDays(1)),
                Set.of(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6)));

        assertEquals("This day is holiday",
                     7, workingDatesHelper.workDaysBetween(
                        LocalDate.now(),
                        LocalDate.now().plusDays(9)));
    }

    @Test
    public void tenDaysAndOneHolidayAndTwoDaysAsWeekendNotInRange() throws WrongDatesRange {
        WorkingDatesHelper workingDatesHelper = new WorkingDatesHelper(
                Set.of(LocalDate.now().plusDays(12)),
                Set.of(LocalDate.now().plusDays(15), LocalDate.now().plusDays(16)));

        assertEquals("This day is holiday",
                     10, workingDatesHelper.workDaysBetween(
                        LocalDate.now(),
                        LocalDate.now().plusDays(9)));
    }

}
