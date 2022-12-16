import React from "react";
// import { useState } from "react";
// import ReactDOM from "react-dom"

const CashPayment = () => {
    const [from, setFrom] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [to, setTo] = React.useState('');
    const [type, setType] = React.useState('cash');
    const [amount, setAmount] = React.useState('');
    const [notes, setNotes] = React.useState('');
    var [messages, setMessages] = React.useState('');
    const [red, setRed] = React.useState(false);

    const [recipientRed, setRecipientRed] = React.useState(false);
    var [recipientMessage, setRecipientMessage] = React.useState(false);

    const handleCashPayment = () => {
        console.log('test', from, password, to, type, amount);
        if (from == "" || password == "" || to == "" || amount == "") {
            alert("Something goes wrong, check if you have completed the form");
        }
        const body = {
            from: from,
            password: password,
            to: to,
            type: type,
            amount: amount,
            notes: notes
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body), //to json string
        };
        fetch('/api/cashPayment', settings) // makes http client calls 
            .then(res => res.json())
            .then(data => {
                console.log(data);
                if (!data.isSuccess) {
                    alert(data.message);
                }
                else {
                    window.location.href = '/';
                }
            });
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
        fetch('/api/cashPayment-verifyAccount', settings) // makes http client calls 
            .then(res => res.json())
            .then(data => {
                var obj = Object.entries(data).map(([key, value]) => ({ key, value }));
                console.log(obj[0].value);
                if (obj[0].value == false) {
                    setRed(true);
                }
                else {
                    setRed(false);
                }
                console.log(obj[1].value);
                setMessages(obj[1].value);
                console.log(messages);
            });
    };

    const handleRecipient = () => {
        console.log('test', to);
        const body = {
            to: to
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body), //to json string
        };
        fetch('/api/cashPayment-verifyRecipient', settings) // makes http client calls 
            .then(res => res.json())
            .then(data => {
                var obj = Object.entries(data).map(([key, value]) => ({ key, value }));
                console.log(obj[0].value);
                if (obj[0].value == false) {
                    setRecipientRed(true);
                }
                else {
                    setRecipientRed(false);
                }
                setRecipientMessage(obj[1].value);
            });
    };

    return (


        <div>
            <div>
                <h1 className="header">Control Center</h1>
            </div>


            <div className="userbox0">
                <h1 className="paymentSelect1"></h1>
                <div className="paymentType1">
                </div>

            </div>

            <div className="paymentPage1">
                <div className="userbox1">
                    <a href="https://dweet.io/dweet/for/hummingB?state=magniStart" class="myButton" target="_blank" rel="noreferrer noopener">STREAM</a>

                    <a href="https://dweet.io/dweet/for/hummingB?state=alarm" class="Alarm" target="_blank" rel="noreferrer noopener">Activate Alarm</a>
                    <a href="https://dweet.io/dweet/for/hummingB?state=alarmoff" class="Alarm" target="_blank" rel="noreferrer noopener">Turn Off Alarm</a>
                </div>

                <div className="userbox1">
                    <h2>Controls</h2>
                    <div className="panup">
                        <a href="https://dweet.io/dweet/for/hummingB?state=fronth" class="panning" target="_blank" rel="noreferrer noopener">Front Horizontal</a>
                        <a href="https://dweet.io/dweet/for/hummingB?state=up" class="panning" target="_blank" rel="noreferrer noopener">Up</a>
                        <a href="https://dweet.io/dweet/for/hummingB?state=frontv" class="panning" target="_blank" rel="noreferrer noopener">Front Vertical</a>
                    </div>
                    <div className="panleftright">
                        <a href="https://dweet.io/dweet/for/hummingB?state=left" class="panning" target="_blank" rel="noreferrer noopener">Left</a>
                        <a href="https://dweet.io/dweet/for/hummingB?state=right" class="panning" target="_blank" rel="noreferrer noopener">Right</a>
                    </div>
                    <div className="pandown">
                        <a href="https://dweet.io/dweet/for/hummingB?state=down" class="panning" target="_blank" rel="noreferrer noopener">Down</a>
                    </div>
                    <div classname="pandown">
                        <a href="https://dweet.io/dweet/for/hummingB?state=resetCam"  target="_blank" rel="noreferrer noopener">RESET CAMERA</a>
                    </div>

                </div>
            </div>
            <div className="space1">
            </div>
        </div>
    );
};

export default CashPayment;