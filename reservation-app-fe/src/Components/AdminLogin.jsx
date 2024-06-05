import React, { useState } from 'react'
import '../Styles/AdminLogin.css'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios';

function AdminLogin() {
let [email,setEmail] = useState("")
let [password,setPassword] = useState("")

let navigate = useNavigate()

function verify(e){
  e.preventDefault()
  axios.post(`http://localhost:8080/api/admins/verify-by-email?email=${email}&password=${password}`)
  .then((res)=>{
    alert("Verify Succesfull")
    navigate('/adminhomepage')
    console.log(res.data.data);
    localStorage.setItem("Admin",JSON.stringify(res.data.data))

  })
  .catch((err)=>{
    alert("Verify Failed")
    console.log(err);
  })
}

// function verify(){
//   if(email=="abcd" && password==1234){
//     alert("Login Succefull")
//   }
//   else{
//     alert("Login failed")
//   }
// }

  return (
    <div className='adminLogin'>
        <form onSubmit={verify} action="">
            <label htmlFor="">User Name</label>
            <input type="text" value={email} onChange={(e)=>{setEmail(e.target.value)}} placeholder='Enter User Name'/>

            <label htmlFor="">Password</label>
            <input type="text" value={password} onChange={(e)=>{setPassword(e.target.value)}} placeholder='Enter Password'/>

            <button>Login</button>
            <p>Are you new User ?<Link to="/AdminSignUp">Register here..</Link></p>
        </form>
    </div>
  )
}
export default AdminLogin
