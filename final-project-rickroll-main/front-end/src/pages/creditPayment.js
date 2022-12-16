
import react from "react";
import React from "react";
import { useState } from "react";
import ReactDOM from "react-dom"

const CreditPayment = () => {
    const [from, setFrom] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [to, setTo] = React.useState('');
    const [type, setType] = React.useState('credit');
    const [amount, setAmount] = React.useState('');
    const [creditNumber, setCreditNumber] = React.useState('');

    const handleCreditPayment = () => {
        console.log('test', from, password, to, type, amount, creditNumber);
        const body = {
            from: from,
            password: password,
            to: to,
            type: type,
            creditNumber: creditNumber,
            amount: amount


        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body), //to json string
        };
        fetch('/api/creditPayment', settings) // makes http client calls 
            .catch(console.log);
    };

    const handleVerify = () => {
        console.log('test', from, password);
        const body = {
            from: from,
            password: password
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body), //to json string
        };
        fetch('/api/creditPayment-verifyAccount', settings) // makes http client calls 
            .catch(console.log);
    };




    return (
        <div>
            <div>
                <h1 className="header">Get Quote</h1>
            </div>

            <div className="paymentPage">
                <div className="userbox">
                    <div className="user">
                        <div className="textedit">Username</div>
                        <input className="inputBox" type="text" id="inputBox"
                            value={from} onChange={(e) => setFrom(e.target.value)} />
                        <div className="textedit">Password</div>
                        <input className="passBox2" style={{ width: "400px" }} type="password" value={password}
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="verify">
                        <button type="button" className="verifybutton"
                            onClick={handleVerify}>Verify Account</button>
                    </div>
                </div>

                <div className="userbox1">
                    <div className="textedit">First Name</div>
                    <input className="inputBox" type="text" id="inputBox" />
                    <div className="textedit">Last Name</div>
                    <input className="inputBox" type="text" id="inputBox" />
                    <div className="textedit">Email address</div>
                    <input className="inputBox" type="text" id="inputBox" />
                    <div className="textedit">Company Name</div>
                    <input className="inputBox" type="text" id="inputBox" />
                    <div className="textedit">Phone Number</div>
                    <input className="inputBox" type="text" id="inputBox"/>
                </div>

                <div >
                    <button type="button" className="submitButton"
                        onClick={handleCreditPayment}>Submit</button>
                </div>
            </div>

            <div className="space">

            </div>
        </div>
    );
};

export default CreditPayment;