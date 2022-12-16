import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import LoginSuceeded from './pages/loginSuceeded';
import SignupSuceeded from './pages/signupSuceeded';
import Logout from './pages/logout';
import Login from './pages/login';
import SignUp from './pages/signup';
import Home from './pages/home';
import About from './pages/about';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import './styles/home.css';
import './App.css';
import './styles/login.css';
import './styles/signup.css';
import './styles/cashPayment.css';
import './styles/creditPayment.css';
import CashPayment from './pages/cashPayment';
import CreditPayment from './pages/creditPayment';
import React, { useEffect, useState } from 'react';


function App() {
  var [login, setLogin] = React.useState(false);
  const storage2 = window.sessionStorage;

  if (storage2.getItem("isSuccess") == 'true') {
    console.log("TEST");
  }

  return (
    <div class="app-mount-point">
      <Router>
        <Navbar />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/SignUp" exact component={SignUp} />
          <Route path="/Login" exact component={Login} />
          <Route path="/About" exact component={About} />

        </Switch>
        <Switch>
          <Route path="/CashPayment" exact component={CashPayment} />
          <Route path="/CreditPayment" exact component={CreditPayment} />
          <Route path="/Loginsuccess" exact component={LoginSuceeded} />
          <Route path="/Logoutsuccess" exact component={Logout} />
          <Route path="/signupsuccess" exact component={SignupSuceeded} />
        </Switch>
        <Footer />
      </Router>
    </div>

  );
}
export default App;


















// function App() {

// const myHandler = () =>{
//   console.log('User has clicked')
// };
//   return (
//     <div className="app-mount-point">
//       <header className="active-links">
//         <div className="button-wrappers"> 
//           <nav id = "main-menu" className="nav-menu">
//             <ul className="main-menu-list">
//               <li>
//                   <Link to="/" className="Home" onClick={myHandler}>
//                     Home
//                   </Link>
//               </li>
//               <li>
//                 <Link to="/personal" className="Personal" onClick={myHandler}>
//                   Personal
//                 </Link>
//               </li>
//               { <li>
//                 <Link to="/business" className="Business" onClick={myHandler}>
//                   Business
//                 </Link>
//               </li> }
//               {/* <li>
//                 <Link to="/help" className="Help" onClick={myHandler}>
//                   Help
//                 </Link>
//               </li> */}
//             </ul>
//             <div className="header-buttons">
//               <div className="button-list">
//                 <button>
//                   <Link to="/Login" class="Login" onClick={myHandler}>
//                     Log In
//                   </Link>
//                 </button>
//                 <button>
//                   <Link to="/Signup" class = "Sign-up" onClick={myHandler}>
//                     Sign Up
//                   </Link>
//                 </button>
//               </div>
//           </div>
//           </nav>

//           <Switch>
//           <Route path="/"><Home /></Route>
//             <Route path="/Personal"><personal/></Route>
//             {/* <Route path="/business"><business/></Route> */}
//             {/* <Route path="/Help"><Help/></Route> */}
//             <Route path="/login"><Login /></Route>
//             <Route path="/SignUp"><SignUp /></Route>
//           </Switch>


//         </div>
//       </header>
//       <div className="restofPage">
//       </div>
//     </div>
//   );
// }

// export default App;