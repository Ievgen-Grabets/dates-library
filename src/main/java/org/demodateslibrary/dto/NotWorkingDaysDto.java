package org.demodateslibrary.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


public class NotWorkingDaysDto {

    public NotWorkingDaysDto() {
    }

    public NotWorkingDaysDto(Set<LocalDate> holidays, Set<LocalDate> weekends, Set<LocalDate> otherNotWorkingDays) {
        this.holidays = Set.copyOf(holidays);
        this.weekends = Set.copyOf(weekends);
        this.otherNotWorkingDays = Set.copyOf(otherNotWorkingDays);
    }

    private Set<LocalDate> holidays;
    private Set<LocalDate> weekends;
    private Set<LocalDate> otherNotWorkingDays;

    public Set<LocalDate> getHolidays() {
        return Objects.isNull(holidays) ? Collections.emptySet() : Set.copyOf(holidays);
    }

    public void setHolidays(Set<LocalDate> holidays) {
        this.holidays = Set.copyOf(holidays);
    }

    public Set<LocalDate> getWeekends() {
        return Objects.isNull(weekends) ? Collections.emptySet() : Set.copyOf(weekends);
    }

    public void setWeekends(Set<LocalDate> weekends) {
        this.weekends = Set.copyOf(weekends);
    }

    public Set<LocalDate> getOtherNotWorkingDays() {
        return Objects.isNull(otherNotWorkingDays) ? Collections.emptySet() : Set.copyOf(otherNotWorkingDays);
    }

    public void setOtherNotWorkingDays(Set<LocalDate> otherNotWorkingDays) {
        this.otherNotWorkingDays = Set.copyOf(otherNotWorkingDays);
    }
}
