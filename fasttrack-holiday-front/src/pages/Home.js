import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";
import HolidayOverview from "../holidays/HolidayOverview";

export default function Home() {
  const [employeeId, setEmployeeId] = useState(""); // State for storing the employee ID input
  const [showOverview, setShowOverview] = useState(false); // State to handle showing the HolidayOverview

  const handleEmployeeIdChange = (e) => {
    setEmployeeId(e.target.value);
  };

  const handleSearchClick = () => {
    if (employeeId) {
      setShowOverview(true); // Display the overview if employeeId is provided
    } else {
      alert("Please enter a valid Employee ID.");
    }
  };

  return (
    <div className="container">
      {/* EmployeeId input field */}
      <div className="mb-3">
        <label htmlFor="employeeId" className="form-label">
          Enter Employee ID
        </label>
        <input
          type="text"
          className="form-control"
          id="employeeId"
          placeholder="Enter employee ID"
          value={employeeId}
          onChange={handleEmployeeIdChange}
        />
      </div>

      {/* Search Button to trigger the display of HolidayOverview */}
      <button className="btn btn-primary" onClick={handleSearchClick}>
        Search
      </button>

      {/* Render HolidayOverview only if showOverview is true */}
      {showOverview && employeeId && (
        <HolidayOverview employeeId={employeeId} /> // Pass employeeId to HolidayOverview
      )}
    </div>
  );
}