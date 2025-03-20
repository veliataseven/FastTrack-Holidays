# FastTrack Holidays Frontend

This is the frontend application for the FastTrack Holidays project, built with React. It allows users to manage their holidays by providing features such as viewing, adding, editing, and deleting holiday records.

## Installation

Follow the steps below to get the frontend application running on your local machine:

1. Clone the repository:
   ```bash
   git clone <your-repository-url>

   cd fasttrack-holiday-front

   npm install

   npm start

This will start the application on http://localhost:3000.

## Usage

- **Add Holiday**: You can add a holiday for a specific employee.
- **View Holidays**: View all holidays scheduled by employees.
- **Edit Holiday**: Modify the details of an existing holiday.
- **Delete Holiday**: Remove a scheduled holiday from the system.

The application interfaces with the backend, which provides endpoints to manage holidays.

## Features

- **Add a Holiday**: The user can add a new holiday by entering the details such as holiday label, employee ID, start and end dates, and status.
- **Edit a Holiday**: Users can edit the details of an existing holiday.
- **View Holiday Details**: Users can view the full details of a holiday, including the holiday label, employee ID, status, and the start and end dates.
- **Delete Holiday**: Users can delete a holiday, which will remove it from the system.

## Technologies Used

- **React**: A JavaScript library for building user interfaces.
- **React Router**: For routing and navigation between components.
- **Axios**: For making HTTP requests to the backend API.
- **Bootstrap**: For styling and responsive layout.

## API Endpoints

Ensure that the backend server is running on `http://localhost:8080`.

The frontend interacts with the following API endpoints:

- **GET /holidays**: Retrieves a list of all holidays.
- **GET /holidays/:id**: Retrieves the details of a specific holiday by `holidayId`.
- **POST /holidays**: Adds a new holiday.
- **PUT /holidays/:id**: Updates the details of a specific holiday.
- **DELETE /holidays/:id**: Deletes a holiday by `holidayId`.




