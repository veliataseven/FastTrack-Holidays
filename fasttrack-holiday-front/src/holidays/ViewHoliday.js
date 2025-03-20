import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewHoliday() {
  const [holiday, setHoliday] = useState({
    holidayLabel: "",
    employeeId: "",
    startOfHoliday: "",
    endOfHoliday: "",
    status: "",
  });

  // Extract holidayId from the URL parameters
  const { holidayId } = useParams();

  useEffect(() => {
    loadHoliday();
  }, [holidayId]); // Reload when holidayId changes

  const loadHoliday = async () => {
    try {
      // Fetch holiday details from the backend using the holidayId from the route
      const result = await axios.get(`http://localhost:8080/holidays/${holidayId}`);
      setHoliday(result.data); // Set the holiday data to state
    } catch (error) {
      console.error("Error fetching holiday details:", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Holiday Details</h2>

          <div className="card">
            <div className="card-header">
              <h3>Details of holiday id : {holiday.holidayId}</h3>
            </div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item">
                <b>Holiday Label:</b> {holiday.holidayLabel}
              </li>
              <li className="list-group-item">
                <b>Employee Id:</b> {holiday.employeeId}
              </li>
              <li className="list-group-item">
                <b>Start Of Holiday:</b> {holiday.startOfHoliday}
              </li>
              <li className="list-group-item">
                <b>End Of Holiday:</b> {holiday.endOfHoliday}
              </li>
              <li className="list-group-item">
                <b>Status:</b> {holiday.status}
              </li>
            </ul>
          </div>

          {/* Back to Home button */}
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}
