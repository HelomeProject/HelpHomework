import React, { useState, useEffect } from 'react'
import { Typography, Grid } from '@material-ui/core';
import { List, ListItem, ListItemText, Divider, IconButton } from '@material-ui/core';
import useStyles from './NoticeInfoTableCSS'
import AddIcon from '@material-ui/icons/Add';
import axios from 'axios'
import getCookieValue from '../../getCookie'
import NotiAddForm from './NotiAddForm'
import ViewNotice from './ViewNotice'
import DeleteForeverIcon from '@material-ui/icons/DeleteForever';

const NotiList = ({ mode, notilist, setTitle, setOpenView, setImgurl, setDel }) => {
    const fronturl = 'http://k02c1101.p.ssafy.io:8000'
    const config = { headers: { 'Authorization': getCookieValue('token') } }

    const deleteNoti = (idx) => {
        return axios.delete('http://k02c1101.p.ssafy.io:9090/api/board/notice/' + String(idx), config)
            .then(res => {
                setDel(true)
            })
            .catch(err => { console.log(err) })

    }
    return (
        <>
            {notilist === [] ? (
                <div />
            ) : (
                    <>
                        {notilist.map((val, idx) => (
                            <ListItem button divider key={idx}>
                                <ListItemText primary={val.noticeTitle} onClick={() => {
                                    setTitle(val.noticeTitle)
                                    setImgurl(fronturl + val.noticeImgUrl)
                                    setOpenView(true)
                                }} />
                                {
                                    mode === 0 ? <div />
                                        :
                                        <IconButton onClick={() => { deleteNoti(val.idx) }}><DeleteForeverIcon /></IconButton>
                                }

                            </ListItem>
                        ))}
                    </>
                )
            }
        </>
    )

}

const NoticeInfoTable = ({ mode, userInfo }) => {
    const classes = useStyles()
    const [openForm, setOpenForm] = useState(false)
    const [notilist, setNotilist] = useState([])
    const [openView, setOpenView] = useState(false)
    const [title, setTitle] = useState('')
    const [imgurl, setImgurl] = useState('')
    const [del, setDel] = useState(false)

    useEffect(() => {
        const config = { headers: { 'Authorization': getCookieValue('token') } }
        const loadNotification = () => {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/board/notices", config)
                .then(res => { setNotilist(res.data.NoticeList) })
                .catch(err => { console.log(err) })
        }
        loadNotification()
        setDel(false)
    }, [del])

    const addNotification = () => {
        setOpenForm(true)
    }

    return (
        <>
            <Grid container className={classes.title}>
                <Grid item xs={1} />
                <Grid container justify="center" item xs={10} alignItems="center"><Typography >공지사항</Typography></Grid>
                {mode === 1 ? <Grid item xs={1}><IconButton onClick={addNotification}><AddIcon /></IconButton></Grid> : <Grid item xs={1} />}

            </Grid>
            <Grid className={classes.listitem}>
                <Divider />
                <List>
                    <NotiList mode={mode} notilist={notilist} setTitle={setTitle} setOpenView={setOpenView} setImgurl={setImgurl} setDel={setDel} />
                </List>
                <NotiAddForm userInfo={userInfo} open={openForm} setOpen={setOpenForm} notilist={notilist} setNotilist={setNotilist} />
                <ViewNotice openView={openView} setOpenView={setOpenView} title={title} imgurl={imgurl} setImgurl={setImgurl} />
            </Grid>

        </>
    )

}
export default NoticeInfoTable