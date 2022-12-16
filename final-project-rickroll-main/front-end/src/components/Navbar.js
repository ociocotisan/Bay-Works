import React from 'react'
import { Link } from 'react-router-dom';
import Logo from '../assets/hummingbird.png';
import '../styles/Navbar.css';



const Navbar = ()=> { 
    const [seen, setSeen] = React.useState(false);
    const storage2 = window.sessionStorage;

    if(storage2.getItem("isSuccess") != null){
        return (
            <div className = "navbar">
            <div className = "leftSide">
                <img src = {Logo} alt="uh" />
            </div>
            <div className = "rightSide">
                <Link to= "/"> Home</Link>
                <Link to= "/about"> About Us</Link>
            
            </div>
        </div>
        )
    }
    
    return (
        <div className = "navbar">
            <div className = "leftSide">
                <img src = {Logo} alt="uh" />
            </div>
            <div className = "rightSide">
                <Link to= "/"> Home</Link>
                <Link to= "/about"> About Us</Link>
                <Link to= "/login"> Log In</Link>
                <Link to= "/signup"> Register</Link>
       
            </div>
        </div>
    )
}

export default Navbar
