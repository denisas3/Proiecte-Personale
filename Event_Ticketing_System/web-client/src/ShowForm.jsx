import React from 'react';
import {useState} from 'react'
import { useEffect } from 'react';

export default function ShowForm({addFunction, updateFunction, selectedShow}) {

    const [artistName, setArtistName] = useState('');
    const [date, setDate] = useState('');
    const [location, setLocation] = useState('');
    const [availableSeats, setAvailableSeats] = useState('');
    const [soldSeats, setSoldSeats] = useState('');

    function handleSubmit(event) {
        let show= {
            artistName:artistName,
            date:date,
            location:location,
            availableSeats:availableSeats,
            soldSeats:soldSeats,
        }
        console.log('A show was submitted: ');
        console.log(show);

        event.preventDefault();

        if (selectedShow) {
            updateFunction(selectedShow.id, show);
        } else {
            addFunction(show);
        }

    }

    useEffect(() => {
        if (selectedShow) {
            setArtistName(selectedShow.artistName);
            setDate(selectedShow.date);
            setLocation(selectedShow.location);
            setAvailableSeats(selectedShow.availableSeats);
            setSoldSeats(selectedShow.soldSeats);
        }
    }, [selectedShow]);

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Artist Name:
                <input type="text" value={artistName} onChange={event => setArtistName(event.target.value)}/>
            </label><br/>
            <label>
                Date:
                <input type="datetime-local" value={date} onChange={event => setDate(event.target.value)}/>
            </label><br/>
            <label>
                Location:
                <input type="text" value={location} onChange={event => setLocation(event.target.value)}/>
            </label><br/>
            <label>
                Available Seats:
                <input type="number" value={availableSeats} onChange={event => setAvailableSeats(event.target.value)}/>
            </label><br/>
            <label>
                Sold Seats:
                <input type="number" value={soldSeats} onChange={event => setSoldSeats(event.target.value)}/>
            </label><br/>

            <input type="submit" value={selectedShow ? "Save Update" : "Add show"} />
        </form>
    );
}