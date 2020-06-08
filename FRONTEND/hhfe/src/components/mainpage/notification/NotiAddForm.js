import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle } from '@material-ui/core';
import { Grid, TextField, Button, IconButton } from '@material-ui/core';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import axios from 'axios';
import getCookieValue from '../../getCookie'


const NotiAddForm = ({ open, setOpen, setNotilist, notilist, userInfo }) => {
  const [notititle, setNotititle] = useState("")
  const [notiimg, setNotiimg] = useState(null)
  const [notiimgname, setNotiimgname] = useState("")


  const handleChange = (e) => {
    setNotititle(e.target.value)
  }


  const handleClose = (e) => {
    setOpen(false)
  }

  const onChangeFile = (e) => {
    setNotiimg(e.target.files[0])
    if (e.target.files[0]) {
      setNotiimgname(e.target.files[0].name)
    } else {
      setNotiimgname("사진")
    }
  }


  const onClick = () => {

    const config = { headers: { 'Authorization': getCookieValue('token') } }
    const data = {
      noticeTitle: notititle
    }
    return axios.post("http://k02c1101.p.ssafy.io:9090/api/board/notice", data, config)
      .then(res => {
        const newnotilist = notilist.concat(res.data.Notice)
        setNotilist(newnotilist)
        setOpen(false)
        setNotiimg(null)
        setNotiimgname("사진")

        const formData = new FormData();
        formData.append('file', notiimg);
        formData.append('title', notititle)
        formData.append('grade', userInfo.grade)
        formData.append('classnum', userInfo.classnum)

        axios.post("http://k02c1101.p.ssafy.io:8000/api/v1/addnoti/", formData)
          .then(res => { })
          .catch(err => { console.log(err) })

      })
      .catch(err => { console.log(err) })
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
            <TextField label="사진" value={notiimgname} disabled fullWidth />
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