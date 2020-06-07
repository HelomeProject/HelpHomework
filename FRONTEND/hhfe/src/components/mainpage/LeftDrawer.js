import React, { useState } from 'react';
import Drawer from '@material-ui/core/Drawer';
import { Face, Done, DateRange, PeopleAlt } from '@material-ui/icons';
import { ListItem, ListItemIcon, ListItemText } from '@material-ui/core';
import clsx from 'clsx';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';

import HomeworkContent from './homework/HomeworkContent'
import useStyles from './LeftDrawerCSS'

import Notification from './notification/Notification'

const LeftDrawer = ({ open, mode, userInfo }) => {
    const classes = useStyles();
    const [tap, setTap] = useState(0);
    const handleTap = (tap) => {
        setTap(tap);
    };
    // 0 : 학생, 1: 선생님
    const listTitle = [
        ['공지', '공지'],
        ['숙제제출', '제출현황'],
        ['마이페이지', '학생목록']
    ]

    const listIcon = [
        [<DateRange />, <DateRange />],
        [<Done />, <Done />],
        [<Face />, <PeopleAlt />]
    ]

    const drawerListItem = listTitle.map((val, idx) => {
        return (
            <ListItem button onClick={() => { handleTap(idx) }} key={idx}>
                <ListItemIcon>{listIcon[idx][mode]}</ListItemIcon>
                <ListItemText>{val[mode]}</ListItemText>
            </ListItem>
        )
    })

    const ContentControl = () => {
        switch (tap) {
            case 0:
                return <Notification mode={mode} />
            case 1:
                return <HomeworkContent mode={mode} />
            case 2:
                return <div />
            default:
                return <Notification />
        }
    }
    return (
        <div className={classes.root}>
            <Drawer
                className={classes.drawer}
                variant="persistent"
                anchor="left"
                open={open}
                classes={{
                    paper: classes.drawerPaper,
                }}
            >
                <div className={classes.drawerHeader}>
            {userInfo.school} {userInfo.grade}학년 {userInfo.classnum}반
                </div>
                <Divider />
                <List>
                    {drawerListItem}
                </List>

            </Drawer>
            <main
                className={clsx(classes.content, {
                    [classes.contentShift]: open,
                })}
            >
                <ContentControl />
            </main>
        </div >
    );
}

LeftDrawer.defaultProps = {
    open: true,
    mode: 1
}

export default LeftDrawer