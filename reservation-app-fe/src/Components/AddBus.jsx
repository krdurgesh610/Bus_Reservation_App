import React, { useState } from 'react'
import '../Styles/AddBus.css'
import axios from 'axios'

function AddBus() {

    let [name, setName] = useState("")
    let [date_of_departure, setdateofdeparture] = useState("")
    let [bus_number, setbusNumber] = useState("")
    let [from_location, setFrom] = useState("")
    let [to_location, setTo] = useState("")
    let [number_of_seat, setnoOfSeats] = useState("")

let BusData={
    name, date_of_departure, bus_number, from_location, to_location, number_of_seat
}

let admin = JSON.parse(localStorage.getItem("Admin"))
console.log(admin);
console.log(typeof(admin));

function addBusData(e){
    e.preventDefault()
    axios.post(`http://localhost:8080/api/BUS/${admin.id}`, BusData)
    .then((res)=>{
        console.log(res);
        alert('Bus Details have been added Successfully')
    })
    .catch((err)=>{
        console.log(err);
        alert('Invalid Data Formate')
    })
}

    return (
        <div className='AddBus'>
            <form onSubmit={addBusData} action="">
                <label htmlFor="">Name</label>
                <input type="text" required placeholder='Enter the bus Name' value={name} onChange={(e) => { setName(e.target.value) }} />

                <label htmlFor="">Date of Departure</label>
                <input type="date" required placeholder='Enter the bus dateofdeparture' value={date_of_departure} onChange={(e) => { setdateofdeparture(e.target.value) }} />

                <label htmlFor="">Bus Number</label>
                <input type="text" required placeholder='Enter the bus busNumber' value={bus_number} onChange={(e) => { setbusNumber(e.target.value) }} />

                <label htmlFor="">From</label>
                <input type="text" required placeholder='Enter the bus from' value={from_location} onChange={(e) => { setFrom(e.target.value) }} />

                <label htmlFor="">To</label>
                <input type="text" required placeholder='Enter the bus To' value={to_location} onChange={(e) => { setTo(e.target.value) }} />

                <label htmlFor="">No Of Seats</label>
                <input type="number" required placeholder='Enter the bus noOfSeats' value={number_of_seat} onChange={(e) => { setnoOfSeats(e.target.value) }} />

                <button className='btn btn-info'>Add Bus</button>
            </form>

        </div>
    )
}

export default AddBus
