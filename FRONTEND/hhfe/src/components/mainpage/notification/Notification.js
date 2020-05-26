import React, { createRef } from 'react';
import Calendar from '@toast-ui/react-calendar';
import 'tui-calendar/dist/tui-calendar.css';
import Grid from '@material-ui/core/Grid'
import 'tui-date-picker/dist/tui-date-picker.css';
import 'tui-time-picker/dist/tui-time-picker.css';

const calendarOptions = {
    view: 'month',
    disableDblClick: true,
    disableClick: false,
    isReadOnly: false,
    useDetailPopup: true,
    useCreationPopup: true,
    usageStatistics: false,
    template: {
        milestone(schedule) {
            return `<span style="color:#fff;background-color: ${schedule.bgColor};">${
                schedule.title
                }</span>`;
        },
        milestoneTitle() {
            return 'Milestone';
        },
        allday(schedule) {
            return `${schedule.title}<i class="fa fa-refresh"></i>`;
        },
        alldayTitle() {
            return 'All Day';
        },


    },
    month: {
        daynames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
        startDayOfWeek: 0,
        narrowWeekend: true

    }
}


const Notification = () => {
    const calendarRef = createRef();
    console.log(calendarRef)
    const handleClickNextButton = () => {
        const calendarInstance = calendarRef.current.getInstance();
        console.log(calendarInstance)
        calendarInstance.next();
    };
    const handleClickButton = () => {
        calendarRef.current.getRootElement().classList.add('calendar-root');
    };

    return (
        <>
            <Grid item xs={12} sm={12} md={9}>
                <button onClick={handleClickButton}>Click!</button>
                <button onClick={handleClickNextButton}>Go next!</button>
                <Calendar
                    {...calendarOptions}
                    ref={calendarRef}
                />
            </Grid>
            <Grid item xs={12} sm={12} md={3}>

            </Grid>
        </>

    )
}
export default Notification