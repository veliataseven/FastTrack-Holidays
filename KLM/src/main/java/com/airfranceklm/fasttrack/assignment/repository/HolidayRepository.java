package com.airfranceklm.fasttrack.assignment.repository;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing `Holiday` entities.
 * Extends `JpaRepository` to provide CRUD operations and custom queries for holiday data.
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {

    /**
     * Finds holidays for a specific employee by their employee ID.
     *
     * @param employeeId The ID of the employee.
     * @return A list of holidays associated with the employee.
     */
    List<Holiday> findByEmployeeId(String employeeId);

    /**
     * Finds a holiday by its unique ID.
     * This is a custom method to use UUID instead of String.
     *
     * @param holidayId The ID of the holiday to be retrieved.
     * @return A holiday object, or empty if not found.
     */
    @Override
    Optional<Holiday> findById(UUID holidayId);

    /**
     * Checks if a holiday exists by its unique ID.
     * This is a custom method to use UUID instead of String.
     *
     * @param holidayId The ID of the holiday to be checked.
     * @return true if the holiday exists, false otherwise.
     */
    @Override
    boolean existsById(UUID holidayId);

    /**
     * Deletes a holiday by its unique ID.
     * This is a custom method to use UUID instead of String.
     *
     * @param holidayId The ID of the holiday to be deleted.
     */
    @Override
    void deleteById(UUID holidayId);
}
