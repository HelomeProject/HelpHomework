import React, { useState, useEffect, useRef } from 'react';
import { IconButton, Grid, Typography } from '@material-ui/core';
import { ArrowBackIos, ArrowForwardIos } from '@material-ui/icons';
import useStyle from './NotificationCSS'

import Calendar from '@toast-ui/react-calendar';
import 'tui-calendar/dist/tui-calendar.css';
import 'tui-date-picker/dist/tui-date-picker.css';
import 'tui-time-picker/dist/tui-time-picker.css';

const themeConfig = {
    'month.dayname.textAlign': 'center'
}

const calendarOptions = {
    view: 'month',
    disableDblClick: true,
    disableClick: false,
    isReadOnly: false,
    useDetailPopup: true,
    useCreationPopup: true,
    usageStatistics: false,

    month: {
        daynames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        workweek: true
    },
    theme: themeConfig
}


const Notification = () => {
    const classes = useStyle()
    const [month, setMonth] = useState(null)
    const [year, setYear] = useState(null)
    const calendarRef = useRef(null);
    // const calendarRef = createRef();


    useEffect(() => {
        const changeDate = () => {
            const calendarInstance = calendarRef.current.getInstance();
            setMonth(calendarInstance.getDate().toDate().getMonth())
            setYear(calendarInstance.getDate().toDate().getFullYear())
        }
        changeDate()
    })

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
            <Grid item xs={12} sm={12} md={8}>
                <Grid container justify='space-between' alignItems='center'>
                    <IconButton className={classes.calendarTitle} onClick={handleClickPrevButton}><ArrowBackIos /></IconButton>
                    <Typography className={classes.calendarTitle}>{year}년 {month}월</Typography>
                    <IconButton className={classes.calendarTitle} onClick={handleClickNextButton}><ArrowForwardIos /></IconButton>
                </Grid>
                <Calendar
                    {...calendarOptions}
                    ref={calendarRef}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={4}>

            </Grid>
        </>

    )
}
export default Notification