import React from 'react'


function SignupSuceeded() {


    setTimeout(function () {
        window.location.href = '/';
    }, 5000);



    return (
        <div >
            <h1 style={{ paddingBottom: '120px', paddingTop: '100px', color: 'white' }}>Welcome to HummingBird Solutions!</h1>

            <h1 style={{ paddingBottom: '120px', color: 'white' }}>Sign in to manage your new security system</h1>
            <div style={{ color: 'lightblue', paddingBottom: '300px' }}>
                Redirecting to the home page in 5 seconds..
            </div>

        </div>
    )
}

export default SignupSuceeded;
