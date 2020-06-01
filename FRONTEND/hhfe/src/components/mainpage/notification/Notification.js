import React, { useState, useEffect, useRef } from 'react';
import { IconButton, Grid, Typography, Paper } from '@material-ui/core';
import { ArrowBackIos, ArrowForwardIos } from '@material-ui/icons';

import useStyle from './NotificationCSS'
import NoticeInfoTable from './NoticeInfoTable'

import Calendar from '@toast-ui/react-calendar';
import 'tui-calendar/dist/tui-calendar.css';
import 'tui-date-picker/dist/tui-date-picker.css';
import 'tui-time-picker/dist/tui-time-picker.css';
import { ISchedule, ICalendarInfo } from "tui-calendar";

const themeConfig = {
    'month.dayname.textAlign': 'center'
}

const calendarIdx = [
    {
        id: '0',
        name: '숙제',
        bgColor: '#9e5fff',
        borderColor: '#9e5fff'
    },
]

const calendarOptions = {
    view: 'month',
    disableDblClick: true,
    disableClick: false,
    isReadOnly: false,
    useDetailPopup: true,
    useCreationPopup: true,
    usageStatistics: false,
    calendars: calendarIdx,
    month: {
        daynames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        workweek: true
    },
    theme: themeConfig
}




const Notification = ({ mode }) => {
    const classes = useStyle()
    const [month, setMonth] = useState(null)
    const [year, setYear] = useState(null)
    const calendarRef = useRef(null);

    useEffect(() => {
        const changeDate = () => {
            const calendarInstance = calendarRef.current.getInstance();
            setMonth(calendarInstance.getDate().toDate().getMonth())
            setYear(calendarInstance.getDate().toDate().getFullYear())
        }

        console.log()
        changeDate()
    }, [year, month])

    const handleClickNextButton = () => {
        const calendarInstance = calendarRef.current.getInstance();
        calendarInstance.next();
        setMonth(calendarInstance.getDate().toDate().getMonth())
        setYear(calendarInstance.getDate().toDate().getFullYear())
    };
    const handleClickPrevButton = () => {
        const calendarInstance = calendarRef.current.getInstance();
        calendarInstance.prev();
        setMonth(calendarInstance.getDate().toDate().getMonth())
        setYear(calendarInstance.getDate().toDate().getFullYear())
    };

    return (
        <>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={12} md={8}>
                    <Paper className={classes.paper}>
                        <Grid container justify='space-between' alignItems='center'>
                            <IconButton className={classes.title} onClick={handleClickPrevButton}><ArrowBackIos /></IconButton>
                            <Typography className={classes.title}>{year}년 {month}월</Typography>
                            <IconButton className={classes.title} onClick={handleClickNextButton}><ArrowForwardIos /></IconButton>
                        </Grid>
                        <Calendar
                            {...calendarOptions}
                            ref={calendarRef}
                        />
                    </Paper>
                </Grid>
                <Grid item xs={12} sm={12} md={4}>
                    <Paper className={classes.paper} >
                        <Grid container justify='center' alignItems='center'>
                            <NoticeInfoTable />
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>

        </>

    )
}

Notification.defaultProps = {
    mode: "1"
}

export default Notification