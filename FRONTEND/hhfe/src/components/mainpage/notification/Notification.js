import React, { useState, useEffect, useRef, useCallback } from 'react';
import { IconButton, Grid, Typography, Paper } from '@material-ui/core';
import { ArrowBackIos, ArrowForwardIos } from '@material-ui/icons';
import axios from 'axios'

import useStyle from './NotificationCSS'
import NoticeInfoTable from './NoticeInfoTable'

import Calendar from '@toast-ui/react-calendar';
import 'tui-calendar/dist/tui-calendar.css';

import FormDialog from './FormDialog'
import getCookieValue from '../../getCookie'

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
    useDetailPopup: false,
    useCreationPopup: false,
    usageStatistics: false,
    calendars: calendarIdx,
    timezone: {tooltip:'Seoul'},
    month: {
        daynames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        workweek: true
    },
    theme: themeConfig
}


const Notification = ({ mode }) => {
    const classes = useStyle()
    const calendarRef = useRef(null);
    const [year, setYear] = useState(null)
    const [viewschedule, setViewschedule] = useState(false)
    const [open, setOpen] = useState(false)
    const [month, setMonth] = useState(null)
    const [calendarInst, setCalendarInst] = useState(null)
    const [isteacher, setIsteacher] = useState(calendarOptions)
    const [schedulelist, setSchedulelist] = useState([])
    const [newschedule, setNewschedule] = useState({
        calendarId: "1",
        id: String(Math.random()),
        title: "",
        start: new Date(),
        end: new Date(),
        category: "allday",
        isAllDay: true,
        description: ""
    })
    
    // 일정 불러오기

    const newschedulelist = (data) => {
        return data.map((temp) => {
            return({
                calendarId: "1",
                id: temp.homeworkNotice_idx,
                title: temp.homeworkNotice_title,
                start: String(temp.homeworkNotice_startDate),
                end: String(temp.homeworkNotice_endDate),
                description: temp.homeworkNotice_detail,
                category: "allday",
                isAllDay: true,
                isVisible: true,
            })
        })
    }
    
    
    useEffect(() => {
        const config = {headers:{'Authorization':getCookieValue('token')}}

        const loadCalendar = (config) => {
            axios.get('http://k02c1101.p.ssafy.io:9090/api/board/homeworks', config)
            .then(res =>{
                setSchedulelist(newschedulelist(res.data.HomeworkNoticeList))
                // setSchedulelist(temp)
                // return temp
                
            })
            .catch(e => {console.log(e)})
        }

        // calendar 요소 불러오기
        const calendarInstance = calendarRef.current.getInstance();
        setCalendarInst(calendarInstance)

        const changeView = () => {
            if (mode === 1) {
                setIsteacher({
                    ...calendarOptions,
                    isReadOnly: false,
                })
            } else {
                setIsteacher({
                    ...calendarOptions,
                    isReadOnly: true,
                })
            }
        }
        const changeDate = () => {

            setMonth(calendarInstance.getDate().toDate().getMonth() + 1)
            setYear(calendarInstance.getDate().toDate().getFullYear())
        }

        changeView()
        changeDate()
        
        loadCalendar(config)
        return () => {}

    }, [mode])

    const onClickSchedule = useCallback(e => {
        console.log(e.schedule)
        setNewschedule({...newschedule,
            ...e.schedule
        })
        setViewschedule(true)
        setOpen(true)
        
    }, [newschedule]);

    const onBeforeCreateSchedule = useCallback(e => {
        setNewschedule({...newschedule,
            ...e
        })
        setOpen(true)
        e.guide.clearGuideElement();
    }, [newschedule])

    const onBeforeDeleteSchedule = useCallback(res => {
        console.log(res);
    }, []);

    const onBeforeUpdateSchedule = useCallback(e => {
        console.log(e);
    }, []);

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
                            {...isteacher}
                            schedules={schedulelist}
                            ref={calendarRef}
                            onClickSchedule={onClickSchedule}
                            onBeforeCreateSchedule={onBeforeCreateSchedule}
                            onBeforeDeleteSchedule={onBeforeDeleteSchedule}
                            onBeforeUpdateSchedule={onBeforeUpdateSchedule}
                        />
                        <FormDialog viewschedule={viewschedule} open={open} setcreateSchedule={calendarInst} setOpen={setOpen} schedule={newschedule} setSchedule={setNewschedule} />
                    </Paper>
                </Grid>
                <Grid item xs={12} sm={12} md={4}>
                    <Paper className={classes.paper} >
                        <Grid container justify='center' alignItems='center'>
                            <NoticeInfoTable mode={mode}/>
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>

        </>

    )
}

Notification.defaultProps = {
    mode: 1
}

export default Notification