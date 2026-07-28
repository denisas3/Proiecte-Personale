//import React, {useEffect, useRef} from  'react';
import {useEffect, useRef} from  'react';
import { useState } from 'react';
import ShowTable from "./ShowTable.jsx";
import './ShowApp.css'
import ShowForm from './ShowForm.jsx'
import {GetShows, AddShow, UpdateShow, DeleteShow, GetShowByArtist} from "./utils/rest-calls.js";

export default function ShowApp() {
    const [shows, setShows] = useState([]);
    const [selectedShow, setSelectedShow] = useState(null);
    const [artistFilter, setArtistFilter] = useState('');
    const connection = useRef(null);

    function addFunc(show) {
        console.log('Inside Add Function ' + show);
        AddShow(show)
            // .then(result => GetShows())
            // .then(shows => setShows(shows))
            .catch(error => console.log('Error Add  ', error));
    }

    function deleteFunc(show){
        console.log('Inside Delete Function ' + show);
        DeleteShow(show)
            // .then(result => GetShows())
            // .then(shows => setShows(shows))
            .catch(error => console.log('Error Delete  ',error));
    }

    function updateFunc(id, show) {
        console.log('Inside Update Function ' + show);

        UpdateShow(id,show)
            .then(()=>{
                setSelectedShow(null);
            })
            // .then(result => GetShows())
            // .then(shows => {
            //     setShows(shows);
            //     setSelectedShow(null);})
            .catch(error => console.log('Error Update  ',error));
    }

    function filterFunc() {
        console.log('Filter by artist: ' + artistFilter);

        GetShowByArtist(artistFilter)
            .then(shows => setShows(shows))
            .catch(error => console.log('Error Filter ', error));
    }

    function resetFilter() {
        setArtistFilter('');
        GetShows()
            .then(shows => setShows(shows))
            .catch(error => console.log('Error Reset Filter ', error));
    }

    useEffect(() => {
        console.log('Inside useEffect')
        const socket = new WebSocket('ws://localhost:8080/showsws');
        // conection opened
        socket.addEventListener("open",(event) => { //am sters event din paranteze
            socket.send("Connection established");
        })

        //listen for messages
        socket.addEventListener("message", (event) => {
            console.log("Message from server ", event.data);
            setShows(JSON.parse(event.data));
        })

        connection.current = socket
        GetShows().then(shows => setShows(shows))

        //sa inchida conexiunea cand componenta se inchide
        return () => {
            socket.close();
        };
    }, []);

    return (<div className="ShowApp">
        <h1>Show App</h1>
        <ShowForm addFunction={addFunc}
                  updateFunction={updateFunc}
                  selectedShow={selectedShow}
        />
        <br/>
        <div>
            <label>
                Filter by artist:
                <input
                    type="text"
                    value={artistFilter}
                    onChange={event => setArtistFilter(event.target.value)}
                />
            </label>

            <button onClick={filterFunc}>Filter</button>
            <button onClick={resetFilter}>Show All</button>
        </div>
        <br/>
        <ShowTable showsList={shows}
                   deleteFunction={deleteFunc}
                   selectShowFunction={setSelectedShow}/>
    </div>);


}