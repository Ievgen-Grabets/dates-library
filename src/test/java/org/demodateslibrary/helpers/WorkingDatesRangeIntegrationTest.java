package org.demodateslibrary.helpers;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.demodateslibrary.WorkingDatesInitialization;
import org.demodateslibrary.exception.StorageException;
import org.demodateslibrary.exception.WrongDatesRange;
import org.junit.Test;

/**
 * Integration test for WorkingDatesRangeHelperTest
 */
public class WorkingDatesRangeIntegrationTest {

    @Test
    public void testFromDescription() throws IOException, StorageException, WrongDatesRange {
        var workingDatesInitialization = new WorkingDatesInitialization();
        var workingDatesHelper = workingDatesInitialization.initWorkingDatesHelperWithStorage(
                "src/test/resources/test-data/testData.json");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
                .withLocale(Locale.ENGLISH);


        assertEquals("By example we should have 5 days",
                     5,
                     workingDatesHelper.workDaysBetween(
                             LocalDate.parse("June 27, 2022", formatter),
                             LocalDate.parse("July 4, 2022", formatter)));
    }
}
