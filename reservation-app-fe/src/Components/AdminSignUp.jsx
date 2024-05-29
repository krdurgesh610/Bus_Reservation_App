import React, { useState } from 'react'
import '../Styles/AdminSignUp.css'
function AdminSignUp() {

    let [name,setName] = useState("")
    let [email,setEmail] = useState("")
    let [phone,setPhone] = useState("")
    let [gstno,setGstno] = useState("")
    let [travels,setTravels] = useState("")
    let [password,setPassword] = useState("")

  return (
    <div className='adminSignUp'>
      <form action="">
        <label htmlFor="">Name</label>
        <input type="text" value={name} onChange={(e)=>{setName(e.target.value)}} required placeholder='Enter the Name'/>

        <label htmlFor="">Email</label>
        <input type="text" value={email} onChange={(e)=>{setEmail(e.target.value)}} required placeholder='Enter the Email Id'/>

        <label htmlFor="">Phone</label>
        <input type="text" value={phone} onChange={(e)=>{setPhone(e.target.value)}} required placeholder='Enter the Phone number'/>

        <label htmlFor="">GST Number</label>
        <input type="text" value={gstno} onChange={(e)=>{setGstno(e.target.value)}} required placeholder='Enter the GST NUmber'/>

        <label htmlFor="">Travels Name</label>
        <input type="text" value={travels} onChange={(e)=>{setTravels(e.target.value)}} required placeholder='Enter the Travels Name'/>

        <label htmlFor="">Password</label>
        <input type="text" value={password} onChange={(e)=>{setPassword(e.target.value)}} required placeholder='Enter the Password'/>

        <button>SignUp</button>
      </form>
    </div>
  )
}

export default AdminSignUp
