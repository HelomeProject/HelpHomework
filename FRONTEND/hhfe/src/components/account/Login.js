import React, { useState } from 'react';
import { Avatar, Button } from '@material-ui/core';
import CssBaseline from '@material-ui/core/CssBaseline';
import { TextField, Link } from '@material-ui/core';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import useStyles from './LoginCSS'
import axios from 'axios'
import sha256 from './encryptSHA256';
import { Redirect } from "react-router-dom";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'© '}
      <Link color="inherit" href="">
        홈런(Home-Learn)
      </Link>{' '}
    </Typography>
  );
}

const Login = ({ setMode, setCookie, hasCookie, setHasCookie, setUserInfo }) => {
  const classes = useStyles();
  const [loginInfo, setLoginInfo] = useState({
    username: "",
    password: ""
  }
  );

  const handleChange = (e) => {
    const { value, name } = e.target
    setLoginInfo({
      ...loginInfo,
      [name]: value
    })
  };

  const loginApi = async (user) => {
    const pushuser = {
      email: String(user.username),
      password: sha256(String(user.password))
    }

    return await axios.post("http://k02c1101.p.ssafy.io:9090/api/auth/login", pushuser)
      .then((res) => {
        return res
      })
      .catch((error) => { console.log(error) })
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!loginInfo.username || !loginInfo.password) {
      return;
    }
    try {
      const response = await loginApi(loginInfo);
      if (response.status === 200) {
        setCookie('memberIdx', response.data.memberIdx)
        setCookie('token', response.data.token)
        setHasCookie(true);
        setUserInfo(response.data)
        setMode(parseInt(response.data.isteacher))
        // setMode(1)
      } else {
        console.log(response.error)
        // throw new Error(response.error);
      }
    } catch (err) {
      setLoginInfo({
        username: "",
        password: ""
      })
      alert('로그인에 실패했습니다.');
      console.error('login error', err);
    }
  };

  return (
    <>
      {
        hasCookie ? <Redirect to='/main' /> :
          <Grid container component="main" className={classes.root}>
            <CssBaseline />
            <Grid item xs={false} sm={4} md={7} className={classes.image} />
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
              <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                  <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                  로 그 인
          </Typography>
                <form className={classes.form} onSubmit={handleSubmit} method="POST">
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="Email Address"
                    name="username"
                    autoComplete="email"
                    autoFocus
                    onChange={handleChange}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    onChange={handleChange}
                  />
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className={classes.submit}
                  >
                    로그인
            </Button>
                  <Grid container>
                    <Grid item>
                      <Link href="/register" variant="body2">
                        {"아직회원이 아니세요??"}
                      </Link>
                    </Grid>
                  </Grid>
                  <Box mt={5}>
                    <Copyright />
                  </Box>
                </form>
              </div>
            </Grid>
          </Grid>
      }
    </>
  );
}

export default Login