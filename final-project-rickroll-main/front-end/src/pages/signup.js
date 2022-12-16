import React from "react";
import { Redirect } from "react-router";

//import './signup.css';
const SignUp = () => {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [signUpSuccess, setsignUpSuccess] = React.useState(false);
    const [messages, setMessages] = React.useState('');
    const [redirect, setRedirect] = React.useState(false);


    const handleSignUp = () => {
        console.log('Sign Up Clicked', username, password);
        const body = {
            username: username,
            password: password,
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body), //to json string
        };
        fetch('/api/SignUp', settings) // makes http client calls 
            .then(res => res.json())
            .then(data => {
                console.log(data);
                if (!data.isSuccess) {
                    setsignUpSuccess(false);
                    setMessages("×Account already exists×")
                }
                else {
                    setsignUpSuccess(true);
                    setRedirect(true);
                }
            });

    };

    if (redirect) {
        return <Redirect to="/signupsuccess" />;
    }

    return (
        <div className="signup">
            <main className="form-signup">
                <form>
                    <h1 className="sign">Register Here</h1>
                    <h2 className="title">Username</h2>
                    <input type="username" id="inputusername" className="form-control"
                        placeholder="Rick" required autoFocus
                        value={username} onChange={(e) => setUsername(e.target.value)} />

                    <h2 className="title">Password</h2>
                    <input id="inputPassword" value={password} type="password"
                        className="form-control" placeholder="" requiredvalue={password}
                        onChange={(e) => setPassword(e.target.value)} />

                    <div className="" style={{ paddingTop: "5px" }}>
                        <div>{setsignUpSuccess ? <div style={{ color: "red" }}>{messages}</div>
                            : <div style={{ color: "green" }}>{messages}</div>}</div>
                    </div>

                    <div style={{ padding: "0px" }}>
                        <button type="button" className="submitButtonsig"
                            onClick={handleSignUp}>Sign Up</button>
                    </div>


     


                </form>
            </main>
        </div>
    );
};

// manually export in js
export default SignUp;