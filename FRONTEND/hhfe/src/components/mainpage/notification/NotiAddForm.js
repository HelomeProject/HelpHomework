import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle } from '@material-ui/core';
import { Grid, TextField, Button, IconButton } from '@material-ui/core';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import axios from 'axios';
import getCookieValue from '../../getCookie'


const NotiAddForm = ({ open, setOpen, userInfo }) => {
  const [notititle, setNotititle] = useState("")
  const [notiimg, setNotiimg] = useState(null)

  const config = { header: { 'Authorization': getCookieValue('token') } }
  const handleChange = (e) => {
    setNotititle(e.target.value)
  }

  const handleClose = (e) => {
    setOpen(false)
  }

  const onChangeFile = (e) => {
    setNotiimg(e.target.files[0])
  }

  const onClick = () => {
    const formData = new FormData();
    formData.append('file', notiimg);
    formData.append('classnum', userInfo.classnum)
    formData.append('grade', userInfo.grade)

    return axios.post("http://k02c1101.p.ssafy.io:8000/api/v1/addnoti/", formData)
      .then(res => {
        axios.post("http://k02c1101.p.ssafy.io:9090/api/board/notice", {
          "memberClassNum": userInfo.classnum,
          "memberGrade": userInfo.grade,
          "noticeTitle": notititle
        }, config)
          .then(res => { console.log(res) })
          .catch(err => { console.log(err) })
      })
      .catch(err => {
      })
  }
  return (
    <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="form-dialog-title">공지사항</DialogTitle>
      <DialogContent>

        <TextField
          autoFocus
          margin="dense"
          name="title"
          label="제목"
          type="text"
          onChange={handleChange}
          fullWidth
        />

        <input accept="image/*" style={{ display: 'none' }} onChange={onChangeFile} id="icon-button-file" type="file" />
        <Grid container>
          <Grid item xs={10}>
            <TextField label="사진" value={notititle} disabled fullWidth />
          </Grid>
          <Grid item xs={2}>
            <label htmlFor="icon-button-file">
              <IconButton color="primary" aria-label="upload picture" component="span">
                <PhotoCamera style={{ fontSize: 40 }} />
              </IconButton>
            </label>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          취소
          </Button>
        <Button onClick={onClick} color="primary">
          올리기
          </Button>
      </DialogActions>
    </Dialog>
  )

}
export default NotiAddForm