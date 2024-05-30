import React, { useState } from 'react'
import '../Styles/AdminSignUp.css'
import axios from 'axios'
function AdminSignUp() {

    let [name,setName] = useState("")
    let [email,setEmail] = useState("")
    let [phone,setPhone] = useState("")
    let [gst_number,setGst_number] = useState("")
    let [travels_name,setTravels_name] = useState("")
    let [password,setPassword] = useState("")

    let data={
      name,email,phone,gst_number,travels_name,password
    }

    function createAdmin(e){
      e.preventDefault()
      axios.post('http://localhost:8080/api/admins',data)
      .then((res)=>{
        alert("data Added Succesfully")
        console.log(res);
      })
      .catch((err)=>{
        alert("Invalid Data")
        console.log(err);
      })
    }

  return (
    <div className='adminSignUp'>
      <form onSubmit={createAdmin} action="">
        <label htmlFor="">Name</label>
        <input type="text" value={name} onChange={(e)=>{setName(e.target.value)}} required placeholder='Enter the Name'/>

        <label htmlFor="">Email</label>
        <input type="email" value={email} onChange={(e)=>{setEmail(e.target.value)}} required placeholder='Enter the Email Id'/>

        <label htmlFor="">Phone</label>
        <input type="tel"  value={phone} onChange={(e)=>{setPhone(e.target.value)}} required placeholder='Enter the Phone number'/>

        <label htmlFor="">GST Number</label>
        <input type="text" value={gst_number} onChange={(e)=>{setGst_number(e.target.value)}} required placeholder='Enter the GST NUmber'/>

        <label htmlFor="">Travels Name</label>
        <input type="text" value={travels_name} onChange={(e)=>{setTravels_name(e.target.value)}} required placeholder='Enter the Travels Name'/>

        <label htmlFor="">Password</label>
        <input type="password" value={password} onChange={(e)=>{setPassword(e.target.value)}} required placeholder='Enter the Password'/>

        <button>SignUp</button>
      </form>
    </div>
  )
}

export default AdminSignUp
