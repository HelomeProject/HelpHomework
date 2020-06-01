import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { Redirect } from "react-router-dom";
import { Link, Grid, Box } from '@material-ui/core';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import useStyles from './RegisterCSS'
import axios from 'axios'


function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'© '}
      <Link color="inherit" href="https://material-ui.com/">
        홈런(Home-Learn)~!!
      </Link>{' '}
    </Typography>
  );
}

const Register = () => {
  const classes = useStyles();
  const [isJoinSuccess, setJoinSuccess] = useState(false);
  const [registerInfo, setRegisterInfo] = useState({
    "password": "",
    "username": "",
    "email": "",
    "grade": "0",
    "school": "",
  })

  const handleChange = (e) => {
    const { value, name } = e.target
    setRegisterInfo({
      ...registerInfo,
      [name]: value
    })
  };
  const createUserApi = (user) => {
    return axios.post("http://k02c1101.p.ssafy.io:9090/api/member/register", user)
      .then((res) => {
        return res
      })
      .catch((error) => { console.log(error) })
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await createUserApi(registerInfo);
      if (response.status === 200) {
        setJoinSuccess(true);
      }
    } catch (err) {
      console.error('login error', err);
      alert('회원가입에 실패하였습니다. 잠시 후 다시 시도해주세요.')
    }
  };

  return (
    <>
      {!isJoinSuccess && (<Container component="main" maxWidth="xs">

        <CssBaseline />
        <div className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            회원가입
  </Typography>
          <form className={classes.form} method="POST" onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} >
                <TextField
                  autoComplete="fname"
                  name="username"
                  variant="outlined"
                  required
                  fullWidth
                  id="Name"
                  label="이름"
                  autoFocus
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  variant="outlined"
                  required
                  fullWidth
                  id="email"
                  label="이메일 주소"
                  name="email"
                  autoComplete="email"
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  variant="outlined"
                  required
                  fullWidth
                  name="password"
                  label="비밀번호"
                  type="password"
                  id="password"
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  variant="outlined"
                  required
                  fullWidth
                  name="school"
                  label="학교"
                  type="School"
                  id="School"
                  autoComplete="School"
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel
                  control={<Checkbox value="allowExtraEmails" color="primary" />}
                  label="회원정보 제공에 대한 내용에 동의합니다."
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              회원가입
    </Button>
            <Grid container justify="flex-end">
              <Grid item>
                <Link href="/" variant="body2">
                  이미 가입이 되어있으신가요?? 로그인하기
        </Link>
              </Grid>

            </Grid>
          </form>
        </div>
        <Box mt={5}>
          <Copyright />
        </Box>
      </Container>)}
      {
        isJoinSuccess && (<Redirect to="/" />)
      }
}
    </>

  );
}

export default Register