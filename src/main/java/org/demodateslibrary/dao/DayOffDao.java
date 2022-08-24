package org.demodateslibrary.dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.demodateslibrary.enums.DayOffType;
import org.demodateslibrary.exception.StorageException;

/**
 * Future day off storage class
 */
public class DayOffDao {

    public void saveSingle(Set<LocalDate> dateSet, DayOffType type) throws StorageException {
        //TODO:  future impl of storage
    }

    public void saveBatch(Map<DayOffType, Set<LocalDate>> dayOffTypeSetMap) throws StorageException {
        //TODO:  future impl of storage
    }

    public Map<DayOffType, Set<LocalDate>> loadBatch() throws StorageException {
        //TODO:  future impl of storage
        return Collections.emptyMap();
    }

}
