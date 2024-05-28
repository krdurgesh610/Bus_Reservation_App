import Button from 'react-bootstrap/Button';
import React from 'react'
import '../Styles/UserLogin.css'

function UserLogin() {
  return (
    <div className='userLogin'>
      <form action="">
            <label htmlFor="">User Name</label>
            <input type="text" placeholder='Enter User Name'/>

            <label htmlFor="">Password</label>
            <input type="text" placeholder='Enter Password'/>

            <Button variant="outline-light">Login</Button>

        </form>
    </div>
  )
}

export default UserLogin
