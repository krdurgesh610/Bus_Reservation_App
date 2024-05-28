import React from 'react'
import { Link } from 'react-router-dom'
import '../Styles/LandingPage.css'

function LandingPage() {
  return (
    <div className='landingPage'>
        <Link to="/adminlogin">
            <img src="https://www.pngmart.com/files/21/Admin-Profile-Vector-PNG-Clipart.png" alt="" />
            <h2>Admin</h2>
        </Link>
        <Link to="/userlogin">
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-VLNNe21fRCrEEMk1TF0i8BzrjxqDR5s6zL89sa28-ouSiB8aBVH2VuPqG_4sNNf_NUQ&usqp=CAU" alt="" />
            <h2>User</h2>
        </Link>
      
    </div>
  )
}

export default LandingPage
