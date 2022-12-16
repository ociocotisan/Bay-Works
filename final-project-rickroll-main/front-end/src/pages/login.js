import React from "react";
import { Redirect } from "react-router";



const Login = () => {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [redirect, setRedirect] = React.useState(false);
    const [red, setRed] = React.useState(false);
    const [messages, setMessages] = React.useState('');
    var storage = window.localStorage;
    var storage2 = window.sessionStorage;

    const handleSubmit = () => {
        console.log("test", username, password);
        const body = {
            username: username,
            password: password
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        fetch('/api/login', settings)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                if (data.isSuccess) {
                    setRedirect(true);
                    storage2.setItem("isSuccess", JSON.stringify(data.isSuccess));
                    storage2.setItem("username", JSON.stringify(data.username));
                    storage2.setItem("amount", JSON.stringify(data.amount));
                    setRed(false);
                }
                else {
                    setRed(true);
                    setMessages("Invalid username/password!");
                }
            }).catch(console.log);
    };

    if (redirect) {
        setTimeout(function () {
            window.location.href = '/';
            setRedirect(false);
        }, 1000);

        return (
            <div>
                <h1 style={{ paddingBottom: '120px' }}>Welcome back! {username}</h1>
                <div style={{ color: 'lightblue', paddingBottom: '300px' }}>
                    Redirecting to the home page in 1 hot seconds..
                </div>
            </div>
        )
    }

    return (
        <div className="login">
            <main className="form-signin">
                <form>
                    <h1 className="">Log in</h1>
                    <h2 className="title">Username</h2>
                    <input type="username" id="inputUsername" className="form-control" placeholder="Rick" required autoFocus
                        value={username} onChange={(e) => setUsername(e.target.value)} />

                    <h2 className="title">Password</h2>
                    <input type="password" id="inputPassword" className="form-control" placeholder="" required
                        value={password} onChange={(e) => setPassword(e.target.value)} />

                    <div className="" style={{ paddingTop: "5px" }}>
                        <div>{red ? <div style={{ color: "red" }}>{messages}</div>
                            : <div style={{ color: "green" }}>{messages}</div>}</div>
                    </div>

                    <button className="submitButtonlog" type="button" onClick={handleSubmit}>Submit</button>
                </form>
            </main>
        </div>
    );
}


export default Login;
