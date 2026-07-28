import {SHOWS_BASE_URL} from './consts';

function status(response) {
    console.log('Response status: ' + response.status);
    if(response.status >= 200 && response.status < 300)
        return Promise.resolve(response)
    else
        return Promise.reject(new Error(response.statusText))
}

function json(response) {
    return response.json();
}

export function GetShows(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let myInit = {
        method: 'GET',
        headers: headers,
        mode : 'cors'
    };
    let request = new Request(SHOWS_BASE_URL, myInit);

    console.log('Inainte de fetch GET pentru ' + SHOWS_BASE_URL);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}

export function DeleteShow(id){
    console.log('Inainte de fetch DELETE pentru ' + id);
    let myHeaders = new Headers();
    myHeaders.append('Accept', 'application/json');

    let antet= {
        method: 'DELETE',
        headers: myHeaders,
        mode : 'cors'
    };

    const showDelUrl =SHOWS_BASE_URL+'/'+id;
    console.log('URL pentru Delete: ' + showDelUrl);
    return fetch(showDelUrl,antet)
        .then(status)
        .then(response => {
            console.log('Delete status: ' + response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });
}

export function AddShow(show){
    console.log('Inainte de fetch POST pentru ' + JSON.stringify(show));

    let myHeaders = new Headers();
    myHeaders.append('Accept', 'application/json');
    myHeaders.append('Content-Type', 'application/json');

    let antet= {
        method: 'POST',
        headers: myHeaders,
        mode : 'cors',
        body: JSON.stringify(show)
    };

    return fetch(SHOWS_BASE_URL,antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error=>{
            console.log('Request failed '+error);
            return Promise.reject(error);
        });
}

export function UpdateShow(id, show){
    console.log('Inainte de fetch PuT pentru ' + id);
    console.log(JSON.stringify(show));

    let myHeaders = new Headers();
    myHeaders.append('Accept', 'application/json');
    myHeaders.append('Content-Type', 'application/json');

    let antet= {
        method: 'PUT',
        headers: myHeaders,
        mode : 'cors',
        body: JSON.stringify(show)
    }

    const showUpdateUrl = SHOWS_BASE_URL + '/' + id;
    console.log('URL pentru Update: ' + showUpdateUrl);

    return fetch(showUpdateUrl,antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error=>{
            console.log('Request failed'+error);
            return Promise.reject(error);
        });
}

export function GetShowByArtist(artistName){
    let myHeaders = new Headers();
    myHeaders.append('Accept', 'application/json');

    let antet= {
        method: 'GET',
        headers: myHeaders,
        mode : 'cors'
    }

    const filterUrl = SHOWS_BASE_URL + '/artist/' + artistName;
    console.log('Inainte de fetch GET pentru filtrare: ' + filterUrl);

    return fetch(filterUrl, antet)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Filter request succeeded', data);
            return data;
        }).catch(error => {
            console.log('Filter request failed', error);
            return Promise.reject(error);
        });
}