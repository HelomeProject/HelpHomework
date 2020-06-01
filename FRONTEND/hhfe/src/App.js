
import React, { useState, useEffect } from 'react';
import { Route } from "react-router-dom";
import { withCookies, useCookies } from 'react-cookie';

import MyAppBar from './components/mainpage/MyAppBar'
import Login from './components/account/Login'
import Register from './components/account/Register'
const App = () => {

  const [cookies, removeCookie] = useCookies(['user']);
  const [hasCookie, setHasCookie] = useState(false);

  useEffect(() => {
    if (cookies.user && cookies.user !== 'undefined') {
      setHasCookie(true);
    }
  }, [cookies]);

  return (
    <>
      <Route
        exact path="/"
        render={routerProps => {
          return (
            <Login
              {...routerProps}
              hasCookie={hasCookie}
              setHasCookie={setHasCookie}
            />
          );
        }}
      />
      <Route
        exact path="/register"
        component={Register}
      />
      <Route
        exact path="/main"
        render={routerProps => {
          return (
            <MyAppBar
              {...routerProps}
              setHasCookie={setHasCookie}
              removeCookie={() => {
                removeCookie('user');
                setHasCookie(false);
              }}
            />
          );
        }}
      />
    </>
  );
};

export default withCookies(App);