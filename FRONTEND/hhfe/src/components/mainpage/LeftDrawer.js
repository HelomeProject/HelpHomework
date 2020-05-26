import React, { useState } from 'react';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import DateRangeIcon from '@material-ui/icons/DateRange';
import DoneIcon from '@material-ui/icons/Done';
import clsx from 'clsx';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';

import HomeworkContent from './HomeworkContent'
import useStyles from './LeftDrawerCSS'

import Notification from './notification/Notification'

const LeftDrawer = (props) => {
    const classes = useStyles();
    const [tap, setTap] = useState(0);
    const handleTap = (tap) => {
        setTap(tap);
    };
    const ContentControl = () => {
        switch (tap) {
            case 0:
                return <Notification />
            case 1:
                return <HomeworkContent />
            default:
                return <div />
        }
    }
    return (
        <div className={classes.root}>
            <CssBaseline />
            <Drawer
                className={classes.drawer}
                variant="persistent"
                anchor="left"
                open={props.open}
                classes={{
                    paper: classes.drawerPaper,
                }}
            >
                <div className={classes.drawerHeader}>

                </div>
                <Divider />
                <List>
                    <ListItem button onClick={() => { handleTap(0) }}>
                        <ListItemIcon><DateRangeIcon /></ListItemIcon>
                        <ListItemText>학사일정</ListItemText>
                    </ListItem>
                    <ListItem button onClick={() => { handleTap(1) }}>
                        <ListItemIcon><DoneIcon /></ListItemIcon>
                        <ListItemText>과제</ListItemText>
                    </ListItem>
                </List>
            </Drawer>
            <main
                className={clsx(classes.content, {
                    [classes.contentShift]: props.open,
                })}
            >
                <ContentControl />
            </main>
        </div >
    );
}
export default LeftDrawer