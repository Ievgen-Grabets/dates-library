package org.demodateslibrary.helpers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.demodateslibrary.exception.WrongDatesRange;

public class WorkingDatesHelper {

    private static final String VALUE_SHOULD_NOT_BE_NULL_MESSAGE_PATTERN = "%s should not be null";
    private static final String FROM_SHOULD_BE_BEFORE_TILL_MESSAGE_PATTERN = "from {%s} should be before till {%s}";

    private final Set<LocalDate> notWorkingDays = new HashSet<>();

    public WorkingDatesHelper(Set<LocalDate> holidays, Set<LocalDate> weekends){
        notWorkingDays.addAll(holidays);
        notWorkingDays.addAll(weekends);
    }

    /**
     * Get working days between dates.
     * @param from - from
     * @param till - till
     * @return int working days between range
     * @throws WrongDatesRange - if dates is missing or in wrong order
     */
    public int workDaysBetween(final LocalDate from, final LocalDate till) throws WrongDatesRange {
        validateDates(from, till);
        var pointer = LocalDate.from(from);
        int workDaysBetween = 0;
        final int daysStep = 1;
        final var tillComparing = till.plusDays(1);
        do {
            if(!notWorkingDays.contains(pointer)){
                workDaysBetween++;
            }
            pointer = pointer.plusDays(daysStep);
        } while (pointer.isBefore(tillComparing));
        return workDaysBetween;
    }

    /**
     * Validate dates range.
     *
     * @param from - from
     * @param till - till
     * @throws WrongDatesRange  - if dates is missing or in wrong order
     */
    private void validateDates(final LocalDate from, final LocalDate till) throws WrongDatesRange {
        if (Objects.isNull(from)) {
            throw new WrongDatesRange(String.format(VALUE_SHOULD_NOT_BE_NULL_MESSAGE_PATTERN, "from"));
        }
        if (Objects.isNull(till)) {
            throw new WrongDatesRange(String.format(VALUE_SHOULD_NOT_BE_NULL_MESSAGE_PATTERN, "till"));
        }
        if (from.isAfter(till)) {
            throw new WrongDatesRange(String.format(FROM_SHOULD_BE_BEFORE_TILL_MESSAGE_PATTERN, from, till));
        }
    }

}
