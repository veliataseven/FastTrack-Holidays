package com.airfranceklm.fasttrack.assignment.service;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing holiday-related operations.
 * Contains method signatures for CRUD operations on holidays.
 */
public interface HolidayService {

    /**
     * Retrieves all holidays.
     *
     * @return List of all holidays as HolidayDTO objects.
     */
    List<HolidayDTO> getAllHolidays();

    /**
     * Retrieves a holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be retrieved.
     * @return The requested holiday as a HolidayDTO.
     */
    HolidayDTO getHolidayById(UUID holidayId);

    /**
     * Creates a new holiday.
     *
     * @param holidayDTO The holiday data to be created.
     * @return The created holiday as a HolidayDTO.
     */
    HolidayDTO createHoliday(HolidayDTO holidayDTO);

    /**
     * Deletes a holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be deleted.
     */
    void deleteHoliday(UUID holidayId);

    /**
     * Updates an existing holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be updated.
     * @param holidayDTO The holiday data to be updated.
     * @return The updated holiday as a HolidayDTO.
     */
    HolidayDTO updateHoliday(UUID holidayId, HolidayDTO holidayDTO);

    List<HolidayDTO> getMyHolidays(String employeeId);
}
