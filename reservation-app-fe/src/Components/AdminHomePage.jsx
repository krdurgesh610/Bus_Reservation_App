import React from "react";
import { Route, Routes } from "react-router-dom";
import AdminDashBoard from "./AdminDashBoard";
import AdminNavbar from "./AdminNavbar";
import AddBus from "./AddBus";

function AdminHomePage() {
  return (
    <div>
      <AdminNavbar />
      <Routes>
        <Route path="/" element={<AdminDashBoard/>}/>
        <Route path="/addbus" element={<AddBus/>}/>
      </Routes>
    </div>
  );
}

export default AdminHomePage;
