import React from 'react';
import {BrowserRouter as Router, Route } from 'react-router-dom';


// import MyAppBar from './components/mainpage/MyAppBar'
import Signin from './components/signin/signin'
import Signup from './components/signup/signup'

const App = () => {
  return (
    <Router>
      <div>
        <Route exact path='/' component={Signin}/>
        <Route exact path='/Signup' component={Signup}/>
      </div>
    </Router>
  );
}

export default App;
