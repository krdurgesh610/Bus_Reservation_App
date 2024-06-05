import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./Components/LandingPage";
import UserLogin from "./Components/UserLogin";
import AdminLogin from "./Components/AdminLogin";
import AdminSignUp from "./Components/AdminSignUp";
import AdminHomePage from "./Components/AdminHomePage";
import PageNotFound from "./Components/PageNotFound";

function App() {
  return (
    <div className="app">
      <BrowserRouter>
        <Routes>
          <Route path="/*" element={<PageNotFound/>}/>
          <Route path="/" element={<LandingPage/>}/>
          <Route path="/adminlogin"element={<AdminLogin/>}/>
          <Route path="/userlogin" element={<UserLogin/>}/>
          <Route path="/adminsignup" element={<AdminSignUp/>}/>
          <Route path="/adminhomepage/*" element={<AdminHomePage/>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
