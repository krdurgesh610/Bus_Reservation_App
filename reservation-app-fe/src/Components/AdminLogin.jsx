import Button from 'react-bootstrap/Button';
import React from 'react'
import '../Styles/AdminLogin.css'

function AdminLogin() {
  return (
    <div className='adminLogin'>
        <form action="">
            <label htmlFor="">User Name</label>
            <input type="text" placeholder='Enter User Name'/>

            <label htmlFor="">Password</label>
            <input type="text" placeholder='Enter Password'/>

            <Button variant="outline-info">Login</Button>

        </form>
      
    </div>
  )
}

export default AdminLogin
