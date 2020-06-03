
import React, { useState, useEffect } from 'react';
import { Route, Redirect, Switch } from "react-router-dom";
import { withCookies, useCookies } from 'react-cookie';

import MyAppBar from './components/mainpage/MyAppBar'
import Login from './components/account/Login'
import Register from './components/account/Register'
const App = () => {

  const [cookies, removeCookie] = useCookies(['user']);
  const [hasCookie, setHasCookie] = useState(false);
  const [mode, setMode] = useState(0)

  useEffect(() => {
    if (cookies.user && cookies.user !== 'undefined') {
      console.log(cookies)
      setHasCookie(true);
    }
  }, [cookies]);
  const logout = () => {
    removeCookie('user');
    setHasCookie(false);
  }
  return (
    <>
      {!hasCookie ? <Redirect to="/" /> : <Redirect to='/main' />}
      <Switch>
        <Route
          exact path="/"
          render={routerProps => {
            return (
              <Login
                {...routerProps}
                setHasCookie={setHasCookie}
                setMode={setMode}
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
          render={() => {
            return (
              <MyAppBar
                mode={mode}
                setHasCookie={setHasCookie}
                removeCookie={logout}
              />
            );
          }}
        />
      </Switch>
    </>
  );
};

export default withCookies(App);