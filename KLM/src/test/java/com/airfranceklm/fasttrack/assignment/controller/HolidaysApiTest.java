package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.enums.HolidayStatus;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayException;
import com.airfranceklm.fasttrack.assignment.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HolidaysApiTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private HolidaysApi holidaysApi;

    private HolidayDTO holidayDTO;

    private UUID holidayId;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    public void testGetHolidays() {
        List<HolidayDTO> holidayDTOList = Arrays.asList(holidayDTO);

        when(holidayService.getAllHolidays()).thenReturn(holidayDTOList);

        ResponseEntity<List<HolidayDTO>> response = holidaysApi.getHolidays();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(holidayDTO, response.getBody().get(0));
    }

    @Test
    public void testGetHolidayById() {
        when(holidayService.getHolidayById(holidayId)).thenReturn(holidayDTO);

        ResponseEntity<HolidayDTO> response = holidaysApi.getHoliday(holidayId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayDTO, response.getBody());
    }

    @Test
    public void testCreateHoliday() {
        when(holidayService.createHoliday(holidayDTO)).thenReturn(holidayDTO);

        ResponseEntity<HolidayDTO> response = holidaysApi.createHoliday(holidayDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(holidayDTO, response.getBody());
    }

    @Test
    public void testDeleteHoliday() {
        doNothing().when(holidayService).deleteHoliday(holidayId);

        ResponseEntity<Void> response = holidaysApi.deleteHoliday(holidayId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(holidayService, times(1)).deleteHoliday(holidayId);
    }

    @Test
    public void testUpdateHoliday() {
        when(holidayService.updateHoliday(holidayId, holidayDTO)).thenReturn(holidayDTO);

        ResponseEntity<HolidayDTO> response = holidaysApi.updateHoliday(holidayId, holidayDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayDTO, response.getBody());
    }

    @Test
    public void testGetHolidayById_NotFound() {
        when(holidayService.getHolidayById(holidayId)).thenThrow(new InvalidHolidayException("Holiday not found"));

        try {
            holidaysApi.getHoliday(holidayId);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidHolidayException);
            assertEquals("Holiday not found", e.getMessage());
        }
    }
}
