package com.airfranceklm.fasttrack.assignment.controller;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayException;
import com.airfranceklm.fasttrack.assignment.service.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing holidays via RESTful API.
 * Provides endpoints to get, create, and delete holidays.
 */
@Controller
@RequestMapping("/holidays")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class HolidaysApi {

    private static final Logger logger = LoggerFactory.getLogger(HolidaysApi.class); // Logger

    private final HolidayService holidayService;

    /**
     * Retrieves a list of all holidays.
     *
     * @return List of all holidays as HolidayDTO objects.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<HolidayDTO>> getHolidays() {
        logger.info("Fetching all holidays.");
        List<HolidayDTO> holidays = holidayService.getAllHolidays();
        logger.info("Successfully fetched {} holidays.", holidays.size());
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    /**
     * Retrieves a holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be retrieved.
     * @return The requested holiday as a HolidayDTO.
     * @throws InvalidHolidayException If the holiday is not found.
     */
    @RequestMapping(value = "/{holidayId}", method = RequestMethod.GET)
    public ResponseEntity<HolidayDTO> getHoliday(@PathVariable("holidayId") UUID holidayId) {
        logger.info("Fetching holiday with ID: {}", holidayId);
        try {
            HolidayDTO holiday = holidayService.getHolidayById(holidayId);
            logger.info("Successfully fetched holiday with ID: {}", holidayId);
            return new ResponseEntity<>(holiday, HttpStatus.OK);
        } catch (InvalidHolidayException ex) {
            logger.error("Holiday with ID: {} not found.", holidayId);
            throw ex;
        }
    }

    /**
     * Retrieves a list of all my holidays.
     *
     * @return List of all holidays as HolidayDTO objects.
     */
    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<List<HolidayDTO>> getMyHolidays(@PathVariable("employeeId") String employeeId) {
        logger.info("Fetching all my holidays.");
        List<HolidayDTO> holidays = holidayService.getMyHolidays(employeeId);
        logger.info("Successfully fetched {} holidays.", holidays.size());
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    /**
     * Creates a new holiday with validation.
     *
     * @param holidayDTO The holiday data to be created.
     * @return The created holiday as a HolidayDTO.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HolidayDTO> createHoliday(@Valid @RequestBody HolidayDTO holidayDTO) {
        logger.info("Creating a new holiday with label: {}", holidayDTO.getHolidayLabel());
        HolidayDTO createdHoliday = holidayService.createHoliday(holidayDTO);
        logger.info("Successfully created holiday with ID: {}", createdHoliday.getHolidayId());
        return new ResponseEntity<>(createdHoliday, HttpStatus.CREATED);
    }

    /**
     * Deletes a holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be deleted.
     * @throws InvalidHolidayException If the holiday is not found.
     */
    @RequestMapping(value = "/{holidayId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteHoliday(@PathVariable("holidayId") UUID holidayId) {
        logger.info("Deleting holiday with ID: {}", holidayId);
        try {
            holidayService.deleteHoliday(holidayId);
            logger.info("Successfully deleted holiday with ID: {}", holidayId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidHolidayException ex) {
            logger.error("Holiday with ID: {} not found for deletion.", holidayId);
            throw ex;
        }
    }

    /**
     * Updates an existing holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be updated.
     * @param holidayDTO The holiday data to be updated.
     * @return The updated holiday as a HolidayDTO.
     * @throws InvalidHolidayException If the holiday is not found or invalid.
     */
    @RequestMapping(value = "/{holidayId}", method = RequestMethod.PUT)
    public ResponseEntity<HolidayDTO> updateHoliday(@PathVariable("holidayId") UUID holidayId,
                                                    @Valid @RequestBody HolidayDTO holidayDTO) {
        logger.info("Updating holiday with ID: {}", holidayId);
        try {
            HolidayDTO updatedHoliday = holidayService.updateHoliday(holidayId, holidayDTO);
            logger.info("Successfully updated holiday with ID: {}", holidayId);
            return new ResponseEntity<>(updatedHoliday, HttpStatus.OK);
        } catch (InvalidHolidayException ex) {
            logger.error("Failed to update holiday with ID: {}. Reason: {}", holidayId, ex.getMessage());
            throw ex;
        }
    }
}
