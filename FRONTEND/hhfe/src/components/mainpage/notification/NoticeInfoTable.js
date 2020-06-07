import React, { useState , useEffect} from 'react'
import { Typography, Grid } from '@material-ui/core';
import { List, ListItem, ListItemText, Divider, IconButton  } from '@material-ui/core';
import useStyles from './NoticeInfoTableCSS'
import AddIcon from '@material-ui/icons/Add';
import axios from 'axios'
import getCookieValue from '../../getCookie'
import NotiAddForm from './NotiAddForm'

const NoticeInfoTable = ({mode}) => {
    const classes = useStyles()
    const [openForm, setOpenForm] = useState(false)

    useEffect(()=>{
        const config = {header:{'Authorization':getCookieValue('token')}}
        const loadNotification = () => {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/board/notices", config)
            .then(res => {console.log(res)})
            .catch(err => {console.log(err)})
        }
        loadNotification()
    }, [])

    const addNotification = () => {
        setOpenForm(true)
    }

    return (
        <>
            <Grid container className={classes.title}>
                <Grid item xs={1}/>
                <Grid container justify="center" item xs={10} alignItems="center"><Typography >공지사항</Typography></Grid>
                {mode === 1 ? <Grid item xs={1}><IconButton onClick={addNotification}><AddIcon/></IconButton></Grid> : <Grid item xs={1}/>}
                
            </Grid>
            <Grid className={classes.listitem}>
                <Divider />
                <List>
                    <ListItem button divider>
                        <ListItemText primary="Inbox" />
                    </ListItem>
                    <ListItem button divider>
                        <ListItemText primary="Drafts" />
                    </ListItem>
                    <ListItem button divider>
                        <ListItemText primary="Trash" />
                    </ListItem>
                    <ListItem button divider>
                        <ListItemText primary="Spam" />
                    </ListItem>
                </List>
                <NotiAddForm open={openForm} setOpen={setOpenForm}/>
            </Grid>

        </>
    )

}
export default NoticeInfoTable