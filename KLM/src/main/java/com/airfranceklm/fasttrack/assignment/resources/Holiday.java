package com.airfranceklm.fasttrack.assignment.resources;

import com.airfranceklm.fasttrack.assignment.enums.HolidayStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Represents a holiday for an employee.
 * This class is used as a JPA entity to map to the "Holiday" table in the database.
 * It contains information such as the holiday label, employee ID, start and end dates, and status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Holiday {

    /**
     * The unique identifier for the holiday.
     * This ID is automatically generated using a UUID for each holiday.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID holidayId;

    /**
     * The label describing the holiday (e.g., "Summer Holidays").
     * This is a simple text field used to identify the type of holiday.
     */
    private String holidayLabel;

    /**
     * The unique employee ID associated with this holiday.
     * This ID links the holiday to a specific employee.
     */
    private String employeeId;

    /**
     * The start date and time of the holiday (in UTC).
     * This date is when the holiday officially begins for the employee.
     */
    private OffsetDateTime startOfHoliday;

    /**
     * The end date and time of the holiday (in UTC).
     * This date is when the holiday officially ends for the employee.
     */
    private OffsetDateTime endOfHoliday;

    /**
     * The status of the holiday (e.g., "DRAFT", "REQUESTED", "SCHEDULED", "ARCHIVED").
     * This field helps track the state of the holiday in the system.
     */
    private HolidayStatus status;
}
