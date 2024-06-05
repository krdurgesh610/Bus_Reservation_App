import axios from 'axios'
import React, { useEffect, useState } from 'react'

function ViewBus() {

    let [bus,setBus] = useState([])

    useEffect(()=>{
        axios.get(`http://localhost:8080/api/BUS`)
        .then((res)=>{
            console.log(res);
            setBus(res.data)
        })
        .catch((err)=>{
            console.log(err);
        })
    },[])

  return (
    <div className='ViewBus'>
      {bus.map((item)=>{
        return(
            <div className="bus_details">
                <h3>{item.name}</h3>
                <i>Seats : {item.noOfSeats}</i>
                <p>From : {item.from}</p>
                <p>To : {item.to}</p>
                <p>Date : {item.dateofdeparture}</p>
                <span>Bus Number : {item.busNumber}</span>
                <button>Book Seats</button>
            </div>
        )
      })}
    </div>
  )
}

export default ViewBus
