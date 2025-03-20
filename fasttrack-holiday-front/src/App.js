import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./layout/Navbar";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AddHoliday from "./holidays/AddHoliday";
import EditHoliday from "./holidays/EditHoliday";
import ViewHoliday from "./holidays/ViewHoliday";

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />

        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/addholiday" element={<AddHoliday />} />
          <Route exact path="/editholiday/:id" element={<EditHoliday />} />
          <Route exact path="/viewholiday/:id" element={<ViewHoliday />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;