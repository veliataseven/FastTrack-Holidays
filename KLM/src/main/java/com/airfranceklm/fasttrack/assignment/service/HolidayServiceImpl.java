package com.airfranceklm.fasttrack.assignment.service;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayException;
import com.airfranceklm.fasttrack.assignment.mapper.HolidayMapper;
import com.airfranceklm.fasttrack.assignment.repository.HolidayRepository;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the HolidayService interface.
 * Contains business logic for managing holidays, including validation for holiday overlaps,
 * minimum lead time, and gap between holidays.
 */
@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

    private final HolidayRepository holidayRepository;

    /**
     * Retrieves all holidays and converts them to HolidayDTOs.
     *
     * @return List of all holidays as HolidayDTOs.
     */
    @Override
    public List<HolidayDTO> getAllHolidays() {
        logger.info("Fetching all holidays.");
        List<HolidayDTO> holidays = holidayRepository.findAll().stream()
                .map(HolidayMapper::toDTO)
                .toList();
        logger.info("Fetched {} holidays.", holidays.size());
        return holidays;
    }

    /**
     * Retrieves a holiday by its unique ID and converts it to a HolidayDTO.
     *
     * @param holidayId The ID of the holiday.
     * @return The requested holiday as a HolidayDTO.
     * @throws InvalidHolidayException If the holiday is not found.
     */
    @Override
    public HolidayDTO getHolidayById(UUID holidayId) {
        logger.info("Fetching holiday with ID: {}", holidayId);
        try {
            HolidayDTO holiday = holidayRepository.findById(holidayId)
                    .map(HolidayMapper::toDTO)
                    .orElseThrow(() -> new InvalidHolidayException("Holiday not found"));
            logger.info("Successfully fetched holiday with ID: {}", holidayId);
            return holiday;
        } catch (InvalidHolidayException ex) {
            logger.error("Holiday with ID: {} not found.", holidayId);
            throw ex;
        }
    }

    /**
     * Creates a new holiday after performing necessary validations.
     *
     * @param holidayDTO The holiday data to be created.
     * @return The created holiday as a HolidayDTO.
     * @throws InvalidHolidayException If the holiday overlaps with existing holidays
     * or doesn't meet the required criteria.
     */
    @Override
    public HolidayDTO createHoliday(HolidayDTO holidayDTO) {
        logger.info("Creating new holiday with label: {}", holidayDTO.getHolidayLabel());
        validateHoliday(holidayDTO);

        Holiday holiday = HolidayMapper.toEntity(holidayDTO);
        Holiday savedHoliday = holidayRepository.save(holiday);
        logger.info("Successfully created holiday with ID: {}", savedHoliday.getHolidayId());
        return HolidayMapper.toDTO(savedHoliday);
    }

    /**
     * Deletes a holiday by its unique ID.
     *
     * @param holidayId The ID of the holiday to be deleted.
     * @throws InvalidHolidayException If the holiday is not found.
     */
    @Override
    public void deleteHoliday(UUID holidayId) {
        logger.info("Deleting holiday with ID: {}", holidayId);
        try {
            if (!holidayRepository.existsById(holidayId)) {
                throw new InvalidHolidayException("Holiday not found");
            }
            holidayRepository.deleteById(holidayId);
            logger.info("Successfully deleted holiday with ID: {}", holidayId);
        } catch (InvalidHolidayException ex) {
            logger.error("Failed to delete holiday with ID: {}. Reason: {}", holidayId, ex.getMessage());
            throw ex;
        }
    }

    /**
     * Updates an existing holiday after performing necessary validations.
     *
     * @param holidayId The ID of the holiday to be updated.
     * @param holidayDTO The holiday data to be updated.
     * @return The updated holiday as a HolidayDTO.
     * @throws InvalidHolidayException If the holiday is not found or invalid.
     */
    @Override
    public HolidayDTO updateHoliday(UUID holidayId, HolidayDTO holidayDTO) {
        logger.info("Updating holiday with ID: {}", holidayId);

        // Retrieve the existing holiday
        Holiday existingHoliday = holidayRepository.findById(holidayId)
                .orElseThrow(() -> new InvalidHolidayException("Holiday not found"));

        // Check if dates are updated (whether the start or end date has been changed)
        boolean isDateUpdated = !existingHoliday.getStartOfHoliday().equals(holidayDTO.getStartOfHoliday()) ||
                !existingHoliday.getEndOfHoliday().equals(holidayDTO.getEndOfHoliday());

        // If dates are updated, validate them (check for overlap, gap, etc.)
        if (isDateUpdated) {
            logger.info("Dates updated, validating holiday.");
            validateHoliday(holidayDTO);
        }

        // Update the fields of the existing holiday entity
        existingHoliday.setHolidayLabel(holidayDTO.getHolidayLabel());  // Update label (non-date field)
        existingHoliday.setStartOfHoliday(holidayDTO.getStartOfHoliday());  // Update start date
        existingHoliday.setEndOfHoliday(holidayDTO.getEndOfHoliday());  // Update end date
        existingHoliday.setStatus(holidayDTO.getStatus());  // Update status (non-date field)

        // Save the updated holiday
        Holiday updatedHoliday = holidayRepository.save(existingHoliday);
        logger.info("Successfully updated holiday with ID: {}", holidayId);

        // Convert the updated entity to DTO and return it
        return HolidayMapper.toDTO(updatedHoliday);
    }

    /**
     * Retrieves all my holidays and converts them to HolidayDTOs.
     *
     * @return List of all holidays as HolidayDTOs.
     */
    @Override
    public List<HolidayDTO> getMyHolidays(String employeeId) {
        logger.info("Fetching all holidays.");
        List<HolidayDTO> holidays = holidayRepository.findByEmployeeId(employeeId).stream()
                .map(HolidayMapper::toDTO)
                .toList();
        logger.info("Fetched {} holidays.", holidays.size());
        return holidays;
    }

    /**
     * Validates the holiday data before creating or modifying a holiday.
     * Ensures there is no overlap, a gap of 3 working days between holidays,
     * and that the holiday is planned at least 5 working days in advance.
     *
     * @param holidayDTO The holiday data to be validated.
     * @throws InvalidHolidayException If any validation fails.
     */
    private void validateHoliday(HolidayDTO holidayDTO) {
        logger.info("Validating holiday data.");
        // Fetch all holidays from all employees for overlap check
        List<Holiday> existingHolidays = holidayRepository.findAll();

        // Check for overlapping holidays
        checkForHolidayOverlap(holidayDTO, existingHolidays);

        // Ensure there is a gap of at least 3 working days between holidays
        checkForGapBetweenHolidays(holidayDTO, existingHolidays);

        // Ensure the holiday is planned at least 5 working days in advance
        checkLeadTimeForHoliday(holidayDTO);
    }

    /**
     * Checks if the holiday overlaps with any existing holidays for the same or different employees.
     * This method validates that the given holiday does not overlap with any other holidays for the same employee
     * or any other employee, excluding the holiday being updated (its own dates).
     *
     * @param holidayDTO The holiday data to be validated.
     * @param existingHolidays The list of existing holidays for all employees.
     * @throws InvalidHolidayException If there is an overlap with an existing holiday.
     */
    private void checkForHolidayOverlap(HolidayDTO holidayDTO, List<Holiday> existingHolidays) {
        for (Holiday existingHoliday : existingHolidays) {
            // Skip the current holiday being updated (exclude it from the overlap check)
            if (existingHoliday.getHolidayId().equals(holidayDTO.getHolidayId())) {
                continue;
            }

            // Check for overlap with holidays of the same employee or different employees
            if (holidayDTO.getStartOfHoliday().isBefore(existingHoliday.getEndOfHoliday()) &&
                    holidayDTO.getEndOfHoliday().isAfter(existingHoliday.getStartOfHoliday())) {
                throw new InvalidHolidayException("Holiday overlaps with an existing holiday.");
            }
        }
    }

    /**
     * Checks that there is a gap of at least 3 working days between the new holiday and existing holidays.
     *
     * @param holidayDTO The holiday to be validated.
     * @param existingHolidays The list of existing holidays for the employee.
     * @throws InvalidHolidayException If the gap is less than 3 working days.
     */
    private void checkForGapBetweenHolidays(HolidayDTO holidayDTO, List<Holiday> existingHolidays) {
        for (Holiday existingHoliday : existingHolidays) {
            long gapBetweenHolidays = Math.abs(existingHoliday.getEndOfHoliday().until(holidayDTO.getStartOfHoliday(),
                    ChronoUnit.DAYS));
            if (gapBetweenHolidays < 3) {
                throw new InvalidHolidayException("There must be a gap of at least 3 working days between holidays.");
            }
        }
    }

    /**
     * Ensures that the holiday is planned at least 5 working days in advance.
     *
     * @param holidayDTO The holiday to be validated.
     * @throws InvalidHolidayException If the holiday is planned less than 5 working days in advance.
     */
    private void checkLeadTimeForHoliday(HolidayDTO holidayDTO) {
        long daysBetweenNowAndStart = Math.abs(LocalDate.now().until(holidayDTO.getStartOfHoliday().toLocalDate(),
                ChronoUnit.DAYS));
        if (daysBetweenNowAndStart < 5) {
            throw new InvalidHolidayException("Holiday must be planned at least 5 working days in advance.");
        }
    }
}
