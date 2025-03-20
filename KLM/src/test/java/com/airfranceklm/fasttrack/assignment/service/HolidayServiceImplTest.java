package com.airfranceklm.fasttrack.assignment.service;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.enums.HolidayStatus;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayException;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class HolidayServiceImplTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    private HolidayDTO holidayDTO;
    private Holiday holiday;
    private UUID holidayId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        holidayId = UUID.randomUUID();
        holidayDTO = new HolidayDTO(
                holidayId,
                "Christmas Holidays",
                "klm123456",
                OffsetDateTime.parse("2025-12-24T08:00:00+00:00"),
                OffsetDateTime.parse("2025-12-31T08:00:00+00:00"),
                HolidayStatus.REQUESTED
        );

        holiday = new Holiday(
                holidayId,
                "Christmas Holidays",
                "klm123456",
                OffsetDateTime.parse("2025-12-24T08:00:00+00:00"),
                OffsetDateTime.parse("2025-12-31T08:00:00+00:00"),
                HolidayStatus.REQUESTED
        );
    }

    @Test
    public void testCreateHoliday_Success() {
        when(holidayRepository.save(Mockito.any(Holiday.class))).thenReturn(holiday);

        HolidayDTO result = holidayService.createHoliday(holidayDTO);

        assertNotNull(result);
        assertEquals(holidayDTO.getHolidayId(), result.getHolidayId());
    }

    @Test
    public void testCreateHoliday_OverlapException() {
        // Simulating an existing holiday with the same employeeId
        when(holidayRepository.findAll()).thenReturn(List.of(holiday));

        // Creating a holiday with an overlapping date
        HolidayDTO newHolidayDTO = new HolidayDTO(
                UUID.randomUUID(),
                "New Year's Holidays",
                "klm123456", // same employee ID
                OffsetDateTime.parse("2025-12-25T08:00:00+00:00"),
                OffsetDateTime.parse("2025-12-28T08:00:00+00:00"),
                HolidayStatus.REQUESTED
        );

        InvalidHolidayException exception = assertThrows(InvalidHolidayException.class, () -> holidayService.createHoliday(newHolidayDTO));
        assertEquals("Holiday overlaps with an existing holiday.", exception.getMessage());
    }

    @Test
    public void testUpdateHoliday_Success() {
        when(holidayRepository.findById(holidayId)).thenReturn(Optional.of(holiday));
        when(holidayRepository.save(Mockito.any(Holiday.class))).thenReturn(holiday);

        holidayDTO.setHolidayLabel("Updated Holiday Label");

        HolidayDTO result = holidayService.updateHoliday(holidayId, holidayDTO);

        assertNotNull(result);
        assertEquals("Updated Holiday Label", result.getHolidayLabel());
    }

//    @Test
//    public void testUpdateHoliday_OverlapException() {
//        // Given an existing holiday with the same employeeId
//        UUID holidayId = UUID.randomUUID(); // Mock holiday ID
//        Holiday existingHoliday = new Holiday(
//                holidayId,
//                "Christmas Holidays",
//                "klm123456",
//                OffsetDateTime.parse("2025-12-24T08:00:00+00:00"),
//                OffsetDateTime.parse("2025-12-31T08:00:00+00:00"),
//                HolidayStatus.REQUESTED
//        );
//
//        // Create a mock holidayDTO with overlapping dates
//        HolidayDTO holidayDTO = new HolidayDTO(
//                holidayId,
//                "New Christmas Holidays",
//                "klm123456",
//                OffsetDateTime.parse("2025-12-25T08:00:00+00:00"), // Overlapping start date
//                OffsetDateTime.parse("2025-12-30T08:00:00+00:00"), // Overlapping end date
//                HolidayStatus.REQUESTED
//        );
//
//        // Mock the repository behavior
//        when(holidayRepository.findById(holidayId)).thenReturn(Optional.of(existingHoliday));
//        when(holidayRepository.findAll()).thenReturn(List.of(existingHoliday));
//
//        // Simulate the exception being thrown for overlapping holidays
//        InvalidHolidayException exception = assertThrows(InvalidHolidayException.class, () -> holidayService.updateHoliday(holidayId, holidayDTO));
//
//        // Assert the exception message is correct
//        assertEquals("Holiday overlaps with an existing holiday.", exception.getMessage());
//    }




    @Test
    public void testDeleteHoliday_Success() {
        when(holidayRepository.existsById(holidayId)).thenReturn(true);

        holidayService.deleteHoliday(holidayId);

        Mockito.verify(holidayRepository).deleteById(holidayId);
    }

    @Test
    public void testDeleteHoliday_NotFound() {
        when(holidayRepository.existsById(holidayId)).thenReturn(false);

        InvalidHolidayException exception = assertThrows(InvalidHolidayException.class, () -> holidayService.deleteHoliday(holidayId));
        assertEquals("Holiday not found", exception.getMessage());
    }
}
