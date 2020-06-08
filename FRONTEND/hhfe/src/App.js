import React, { useState, useEffect } from 'react';
import { Route, Switch } from "react-router-dom";
import { withCookies, useCookies } from 'react-cookie';
import axios from 'axios'

import MyAppBar from './components/mainpage/MyAppBar'
import Login from './components/account/Login'
import Register from './components/account/Register'


const App = () => {

  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const [hasCookie, setHasCookie] = useState(false);
  const [mode, setMode] = useState(0)
  const [userInfo, setUserInfo] = useState({})

  useEffect(() => {
    const reloaduser = (idx, config) => {
      axios.get('http://k02c1101.p.ssafy.io:9090/api/member/users/' + idx, config)
        .then((res) => {
          setUserInfo(res.data.Member)
          setMode(parseInt(res.data.Member.isteacher))
        })
        .catch((e) => console.log(e))
    }
    if (cookies.token && cookies.token !== 'undefined') {

      const config = {
        headers: { 'Authorization': cookies.token },
      }

      reloaduser(cookies.memberIdx, config)
      setHasCookie(true);
    }
  }, [cookies]);

  const logout = () => {
    removeCookie('token', { path: '/', expires: 0 });
    removeCookie('memberIdx', { path: '/', expires: 0 });
    setHasCookie(false);
  }

  return (
    <div>

      <Switch>
        <Route
          exact path="/"
          render={routerProps => {
            return (
              <Login
                {...routerProps}
                hasCookie={hasCookie}
                setCookie={setCookie}
                setHasCookie={setHasCookie}
                setMode={setMode}
                setUserInfo={setUserInfo}
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
                hasCookie={hasCookie}
                mode={mode}
                userInfo={userInfo}
                setHasCookie={setHasCookie}
                logout={logout}
              />
            );
          }}
        />
      </Switch>
    </div>
  );
};

export default withCookies(App);