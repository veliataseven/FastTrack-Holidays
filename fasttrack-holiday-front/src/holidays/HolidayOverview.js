import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function HolidayOverview({ employeeId }) {
  const [holidays, setHolidays] = useState([]);

  useEffect(() => {
    // Fetch holidays only when the employeeId is available
    if (employeeId) {
      loadHolidays();
    }
  }, [employeeId]);

  const loadHolidays = async () => {
    try {
      const result = await axios.get(
        `http://localhost:8080/holidays/employee/${employeeId}`
      );
      setHolidays(result.data); // Set fetched holidays
    } catch (error) {
      console.error("Error fetching holidays:", error);
    }
  };

  const deleteHoliday = async (holidayId) => {
    try {
      await axios.delete(`http://localhost:8080/holidays/${holidayId}`);
      loadHolidays(); // Reload holidays after deletion
    } catch (error) {
      console.error("Error deleting holiday:", error);
    }
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Holiday Label</th>
              <th scope="col">Employee Id</th>
              <th scope="col">Start Of Holiday</th>
              <th scope="col">End Of Holiday</th>
              <th scope="col">Status</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {holidays.map((holiday, index) => (
              <tr key={holiday.holidayId}>
                <td>{holiday.holidayLabel}</td>
                <td>{holiday.employeeId}</td>
                <td>{holiday.startOfHoliday}</td>
                <td>{holiday.endOfHoliday}</td>
                <td>{holiday.status}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewholiday/${holiday.holidayId}`}
                  >
                    View
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/editholiday/${holiday.holidayId}`}
                  >
                    Edit
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteHoliday(holiday.holidayId)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
