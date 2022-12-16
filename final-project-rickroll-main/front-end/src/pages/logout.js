import React from 'react'
import { Redirect } from "react-router";

function Logout() {


    setTimeout(function () { window.location.href = '/'; }, 1000);

    return (
        <div>
            <h1 style={{ paddingBottom: '120px', color: 'white' }}>Successfully logged out</h1>
            <div style={{ color: 'lightblue', paddingBottom: '300px' }}>
                Redirecting to the home page in a hot second..
            </div>
        </div>
    )

}

export default Logout;