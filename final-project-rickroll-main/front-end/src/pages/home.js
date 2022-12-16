import React, { useEffect, useState } from 'react';
import CashTree from '../assets/HummingbirdLogo.png';

const Home = () => {
    const [name, setName] = React.useState('');
    var [check, setCheck] = React.useState(true);
    const [amount, setAmount] = React.useState('');
    const storage = window.localStorage;
    const storage2 = window.sessionStorage;
    const username = storage2.getItem("username");
    const pocket = storage2.getItem("amount");
    const [seen, setSeen] = React.useState(false);




    console.log(storage2.getItem("username"));
    if (check) {
        console.log("HHHHHHHELLO" + username);
        if (username != null) {
            setName(username);
            setAmount(pocket);
            setCheck(false);
            setSeen(true);
            if (username == "Stranger") {
                setSeen(false);
            }
        }
        else {
            console.log(storage2.getItem("username"));
            storage2.setItem("username", "Stranger");
            storage2.setItem("amount", "0");
            setCheck(true);
        }
    }

    const handleLogout = () => {
        storage2.clear();
    };

    const handleRefresh = () => {
        const body = {
            username: storage2.getItem("username"),
        };
        const settings = {
            method: 'post',
            body: JSON.stringify(body),
        };
        fetch('/api/refresh', settings)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                if (data.isSuccess) {
                    console.log("asdadssadasdasdas");
                    storage2.setItem("amount", JSON.stringify(data.amount));
                    window.location.href = '/';
                }
                else {
                }
            }).catch(console.log);

    }

    return (
        <div className="restofPage">

            <div className='beforepic'>

            </div>
            <img src={CashTree} alt="pho" />
            <h1 class="motto">
                <div><h2>{seen ? 'Welcome ' + name + '\n ' : 'Let us put your mind at ease! '}
                </h2></div>

            </h1>

            <h2 class="motto">
                <div>
                    {seen ? <div></div> : "The future of warehouse security is at your command. " +
                        "Register Today" }
                </div>
            </h2>

            <div style={{ alignContent: 'center', paddingLeft: "40%" }}>
                {seen ? <div>
                    <div style={{ textAlign: 'center' }}>
                        <form action="http://localhost:3000/cashPayment" >
                            <button className='button1' type="submit">View Life Feed</button>
                        </form>
                    </div>
                    <form action="http://localhost:3000/creditPayment">
                        <button className='button1' type="credit">Get Quote</button>
                    </form>

                    <button className='button1' type="submit" onClick={handleRefresh}>Guards in Use</button> </div> : <div></div>}

                <div>{seen ? <form action="http://localhost:3000/Logoutsuccess">
                    <button type="submit" className='button1' style={{ backgroundColor: "#e63f3f" }} onClick={handleLogout}>Sign out</button>
                </form> : <div></div>} </div>

            
            </div>
        </div>
    );

}
export default Home;