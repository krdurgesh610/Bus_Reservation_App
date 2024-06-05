import React from 'react'
import Dropdown1 from './Dropdown'
import '../Styles/AdminNavbar.css'

function AdminNavbar() {
  return (
    <div className='adminNavbar'>
      <div className='logo'>
        <h1>
            <i>yatra</i><sup><i>.in</i></sup>
        </h1>
      </div>
      <div className='options'>
        <Dropdown1/>
      </div>
    </div>
  )
}

export default AdminNavbar
