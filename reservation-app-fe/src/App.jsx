import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./Components/LandingPage";
import UserLogin from "./Components/UserLogin";
import AdminLogin from "./Components/AdminLogin";

function App() {
  return (
    <div className="app">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LandingPage/>}/>
          <Route path="/adminlogin"element={<AdminLogin/>}/>
          <Route path="/userlogin" element={<UserLogin/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
