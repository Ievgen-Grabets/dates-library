package org.demodateslibrary;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.demodateslibrary.dao.DayOffDao;
import org.demodateslibrary.dto.NotWorkingDaysDto;
import org.demodateslibrary.exception.StorageException;
import org.demodateslibrary.helpers.WorkingDatesHelper;
import org.demodateslibrary.helpers.WorkingDatesHelperTypeInsensitiveImpl;
import org.demodateslibrary.helpers.WorkingDatesStorageProxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**
 * Working dates initialization  case
 */
public class WorkingDatesInitialization {
    private final ObjectMapper objectMapper;

    public WorkingDatesInitialization() {
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class,
                                       new LocalDateDeserializer(DateTimeFormatter.ofPattern("MMMM d, yyyy")
                                                                                          .withLocale(Locale.ENGLISH)));
        this.objectMapper = JsonMapper.builder()
                .addModule(javaTimeModule)
                .build();
    }

    /**
     * init type insensitive working dates helper
     * @param path - json path
     * @return WorkingDatesHelper
     * @throws IOException
     */
    public WorkingDatesHelper initWorkingDatesHelper(String path) throws IOException {
        var notWorkingDaysDto = getNotWorkingDaysDto(path);
        return new WorkingDatesHelperTypeInsensitiveImpl(
                notWorkingDaysDto.getWeekends(),
                notWorkingDaysDto.getHolidays(),
                notWorkingDaysDto.getOtherNotWorkingDays());
    }

    /**
     * init type insensitive working dates helper with storage proxy
     * @param path - json path
     * @return WorkingDatesHelper
     * @throws IOException
     */
    public WorkingDatesHelper initWorkingDatesHelperWithStorage(String path) throws IOException, StorageException {
        var notWorkingDaysDto = getNotWorkingDaysDto(path);
        var workingDateHelper = new WorkingDatesHelperTypeInsensitiveImpl(
                notWorkingDaysDto.getWeekends(),
                notWorkingDaysDto.getHolidays(),
                notWorkingDaysDto.getOtherNotWorkingDays());
        var dayOffDao = new DayOffDao();
        return new WorkingDatesStorageProxy(workingDateHelper, dayOffDao);
    }

    private NotWorkingDaysDto getNotWorkingDaysDto(String path) throws IOException {
        return objectMapper.readValue(new File(path), NotWorkingDaysDto.class);
    }
}
