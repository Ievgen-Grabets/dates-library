package org.demodateslibrary.helpers;

import java.time.LocalDate;
import java.util.Set;

import org.demodateslibrary.dao.DayOffDao;
import org.demodateslibrary.enums.DayOffType;
import org.demodateslibrary.exception.StorageException;
import org.demodateslibrary.exception.WrongDatesRange;

/**
 * proxy class for between WorkingDatesHelper and storage
 */
public class WorkingDatesStorageProxy implements WorkingDatesHelper {

    private final WorkingDatesHelper workingDatesHelper;
    private final DayOffDao dao;

    public WorkingDatesStorageProxy(WorkingDatesHelper workingDatesHelper, DayOffDao dao) throws StorageException {
        this.workingDatesHelper = workingDatesHelper;
        this.dao = dao;
        dao.loadBatch().forEach((dayOffType, set) -> {
            switch (dayOffType) {
                case Holiday:
                    workingDatesHelper.addHolidays(set);
                    break;
                case Weekend:
                    workingDatesHelper.addWeekends(set);
                    break;
                default:
                    workingDatesHelper.addOtherNotWorkingDays(set);
                    break;
            }
        });
    }

    @Override
    public void addHolidays(Set<LocalDate> holidays) {
        saveHolidays(holidays);
        workingDatesHelper.addHolidays(holidays);
    }

    @Override
    public void addWeekends(Set<LocalDate> weekends) {
        saveWeekends(weekends);
        workingDatesHelper.addWeekends(weekends);
    }

    @Override
    public void addOtherNotWorkingDays(Set<LocalDate> notWorkingDay) {
        saveOtherNotWorkingDays(notWorkingDay);
        workingDatesHelper.addOtherNotWorkingDays(notWorkingDay);
    }

    public int workDaysBetween(final LocalDate from, final LocalDate till) throws WrongDatesRange {
      return workingDatesHelper.workDaysBetween(from, till);
    }

    private void saveHolidays(Set<LocalDate> holidays){
        try {
            dao.saveSingle(holidays, DayOffType.Holiday);
        } catch (StorageException ignore) {
            //TODO: log here
        }
    }

    private void saveWeekends(Set<LocalDate> weekends){
        try {
            dao.saveSingle(weekends, DayOffType.Weekend);
        } catch (StorageException ignore) {
            //TODO: log here
        }
    }

    private void saveOtherNotWorkingDays(Set<LocalDate> notWorkingDay){
        try {
            dao.saveSingle(notWorkingDay, DayOffType.Other);
        } catch (StorageException ignore) {
            //TODO: log here
        }
    }

}
