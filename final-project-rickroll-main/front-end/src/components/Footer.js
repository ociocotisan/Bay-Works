import React from 'react'
import '../styles/Footer.css';
import { Link } from 'react-router-dom';

function Footer() {
    return (
        <div className ="footer">
            <p> &copy; 2022 HummingBird, by Bay Works</p>
            <div className = "termofuse">
                <Link to= "/termofuse">Term Of Use</Link>
            </div>
        </div>
    )
}

export default Footer
