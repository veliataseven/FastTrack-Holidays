import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function EditHoliday() {
  let navigate = useNavigate();
  const { holidayId } = useParams();  // Get holidayId from the URL params

  const [holiday, setHoliday] = useState({
    holidayLabel: "",
    employeeId: "",
    startOfHoliday: "",
    endOfHoliday: "",
    status: "",
  });

  const { holidayLabel, employeeId, startOfHoliday, endOfHoliday, status } = holiday;

  // Handle form input changes
  const onInputChange = (e) => {
    setHoliday({ ...holiday, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadHoliday();  // Load holiday details on component mount
  }, []);

  // Handle form submission to update the holiday
  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(`http://localhost:8080/holidays/${holidayId}`, holiday);  // PUT request to update holiday
    navigate("/");  // Redirect to home page after successful update
  };

  // Fetch holiday details based on the holidayId
  const loadHoliday = async () => {
    try {
      const result = await axios.get(`http://localhost:8080/holidays/${holidayId}`);
      setHoliday(result.data);  // Set the fetched holiday data into state
    } catch (error) {
      console.error("Error fetching holiday details:", error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit Holiday</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="holidayLabel" className="form-label">
                Holiday Label
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter holiday label"
                name="holidayLabel"
                value={holidayLabel}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="employeeId" className="form-label">
                Employee Id
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter employee ID"
                name="employeeId"
                value={employeeId}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="startOfHoliday" className="form-label">
                Start of Holiday
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter start of holiday"
                name="startOfHoliday"
                value={startOfHoliday}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="endOfHoliday" className="form-label">
                End of Holiday
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter end of holiday"
                name="endOfHoliday"
                value={endOfHoliday}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="status" className="form-label">
                Status
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter status"
                name="status"
                value={status}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to="/">
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
