package com.airfranceklm.fasttrack.assignment.mapper;

import com.airfranceklm.fasttrack.assignment.dto.HolidayDTO;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;

/**
 * Mapper class for converting between the `Holiday` entity and the `HolidayDTO`.
 * This class provides methods to map a `Holiday` object to a `HolidayDTO` and vice versa.
 */
public class HolidayMapper {

    /**
     * Converts a `HolidayDTO` to a `Holiday` entity.
     *
     * @param holidayDTO The `HolidayDTO` to be converted.
     * @return The `Holiday` entity.
     */
    public static Holiday toEntity(HolidayDTO holidayDTO) {
        return Holiday.builder()
                .holidayLabel(holidayDTO.getHolidayLabel())
                .employeeId(holidayDTO.getEmployeeId())
                .startOfHoliday(holidayDTO.getStartOfHoliday())
                .endOfHoliday(holidayDTO.getEndOfHoliday())
                .status(holidayDTO.getStatus())
                .build();
    }

    /**
     * Converts a `Holiday` entity to a `HolidayDTO`.
     *
     * @param holiday The `Holiday` entity to be converted.
     * @return The `HolidayDTO` object.
     */
    public static HolidayDTO toDTO(Holiday holiday) {
        return HolidayDTO.builder()
                .holidayId(holiday.getHolidayId())
                .holidayLabel(holiday.getHolidayLabel())
                .employeeId(holiday.getEmployeeId())
                .startOfHoliday(holiday.getStartOfHoliday())
                .endOfHoliday(holiday.getEndOfHoliday())
                .status(holiday.getStatus())
                .build();
    }
}

