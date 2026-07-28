import React from 'react';
import'./ShowApp.css'

function ShowRow({show, deleteFunction, selectShowFunction }) {

    function handleDelete(event) {
        console.log('delete button pentru ' + show.id);
        deleteFunction(show.id);
    }

    function handleUpdate() {
        console.log('update button pentru ' + show.id);
        selectShowFunction(show);
    }

    return (
        <tr>
            <td>{show.id}</td>
            <td>{show.artistName}</td>
            <td>{show.date}</td>
            <td>{show.location}</td>
            <td>{show.availableSeats}</td>
            <td>{show.soldSeats}</td>
            <td>
                <button onClick={handleUpdate}>Update</button>
                <button onClick={handleDelete}>Delete</button>
            </td>

        </tr>
    );
}

export default function ShowTable({showsList, deleteFunction, selectShowFunction }) {
    console.log("In ShowTable");
    console.log(showsList);
    let rows= [];
    let functieStergere=deleteFunction;
    showsList.forEach(function(show) {
        rows.push(<ShowRow show={show} key={show.id} deleteFunction={functieStergere} selectShowFunction={selectShowFunction} />);
    });

    return (
        <div className = "ShowTable">
            <table className="center">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>ArtistName</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>AvailableSeats</th>
                    <th>SoldSeats</th>

                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        </div>
    );
}