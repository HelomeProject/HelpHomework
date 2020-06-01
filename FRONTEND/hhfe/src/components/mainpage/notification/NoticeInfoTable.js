import React from 'react'
import { Typography, Grid } from '@material-ui/core';
import { List, ListItem, ListItemText, Divider } from '@material-ui/core';
import useStyles from './NoticeInfoTableCSS'

const NoticeInfoTable = () => {
    const classes = useStyles()
    return (
        <>
            <Grid container className={classes.title}>
                <Typography >공지사항</Typography>

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
            </Grid>

        </>
    )

}
export default NoticeInfoTable