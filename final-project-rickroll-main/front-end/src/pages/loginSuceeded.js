import React from 'react'
import { Redirect } from "react-router";

function LoginSuceeded() {
    var storage2 = window.sessionStorage;
    const [username, setUsername] = React.useState('');
    const [boolean, setBoolean] = React.useState(true);
    const [redirect, setRedirect] = React.useState(false);

    if(boolean){
        setUsername(storage2.getItem("username"));
        setBoolean(false);
        setRedirect(true);
    }

    if(redirect){
        setTimeout(function() {
            window.location.href = '/';
            setBoolean(false);
         }, 3000);
    }


    return (
        <div>
            <h1 style = {{paddingBottom:'120px'}}>Welcome back!</h1>
            <div style = {{color :'lightblue', paddingBottom:'300px'}}>
                Redirecting to the home page in 3 seconds..
            </div>

        </div>
        
    )
}

export default LoginSuceeded;
