import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AddHoliday() {
  let navigate = useNavigate();

  const [holiday, setHoliday] = useState({
    holidayLabel: "",
    employeeId: "",
    startOfHoliday: "",
    endOfHoliday: "",
    status: "",
  });

  const { holidayLabel, employeeId, startOfHoliday, endOfHoliday, status } = holiday;

  const onInputChange = (e) => {
    setHoliday({ ...holiday, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/holidays", holiday);
    navigate("/");
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Register Holiday</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="holidayLabel" className="form-label">
              Holiday Label
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter holidayLabel"
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
                type={"text"}
                className="form-control"
                placeholder="Enter employeeId"
                name="employeeId"
                value={employeeId}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="startOfHoliday" className="form-label">
              Start Of Holiday
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter startOfHoliday"
                name="startOfHoliday"
                value={startOfHoliday}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="endOfHoliday" className="form-label">
              End Of Holiday
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter endOfHoliday"
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
                type={"text"}
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
