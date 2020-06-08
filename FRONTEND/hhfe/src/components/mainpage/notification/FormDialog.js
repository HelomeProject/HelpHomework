import React from 'react'
import { Button, Grid } from '@material-ui/core';
import DateFnsUtils from '@date-io/date-fns';
import TextField from '@material-ui/core/TextField';
import { MuiPickersUtilsProvider, KeyboardDatePicker } from '@material-ui/pickers';
import { Dialog, DialogActions, DialogContent, DialogTitle } from '@material-ui/core';
import axios from 'axios'
import getCookieValue from '../../getCookie'
import getFormatDate from './getFormatData'
const FormDialog = ({ open, setOpen, schedule, setSchedule, setcreateSchedule, viewschedule }) => {

    const upload = (data, config) => {
        return axios.post('http://k02c1101.p.ssafy.io:9090/api/board/homework', data, config)
            .then(res => {
                setOpen(false)
            })
            .catch(e => { console.log(e) })

    }

    const handleSubmit = () => {
        setcreateSchedule.createSchedules([schedule])

        const uploadschedule = {
            "homeworkNotice_detail": String(schedule.description),
            "homeworkNotice_endDate": String(getFormatDate(schedule.end.toDate())),
            "homeworkNotice_startDate": String(getFormatDate(schedule.start.toDate())),
            "homeworkNotice_title": String(schedule.title),
        }

        const config = {
            headers: { 'Authorization': getCookieValue('token') },
        }

        upload(uploadschedule, config)
    };

    const handleClose = () => {
        setOpen(false)
    }

    const handleDateStartChange = (date) => {
        setSchedule({ ...schedule, start: date })
    }

    const handleDateEndChange = (date) => {
        setSchedule({ ...schedule, end: date })
    }

    const handleTextChange = (e) => {
        const { name, value } = e.target
        setSchedule({ ...schedule, [name]: value })
    }

    const handleDelete = (e) => {
        console.log(e)
    }

    const handleUpdate = (e) => {
        console.log(e)
    }

    return (
        <Dialog open={open} onClose={handleClose} schedule={schedule} aria-labelledby="form-dialog-title" >
            <DialogTitle id="form-dialog-title">일정 적기</DialogTitle>
            <DialogContent>
                <TextField
                    name="title"
                    margin="dense"
                    label="일정 제목"
                    onChange={handleTextChange}
                    fullWidth
                    value={schedule.title}
                />
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <Grid container justify='space-between'>
                        <KeyboardDatePicker
                            disableToolbar
                            variant="inline"
                            format="MM/dd/yyyy"
                            margin="normal"
                            label="시작일"
                            value={schedule.start}
                            onChange={handleDateStartChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            readOnly={viewschedule}
                        />
                        <KeyboardDatePicker
                            disableToolbar
                            variant="inline"
                            format="MM/dd/yyyy"
                            margin="normal"
                            label="종료일"
                            value={schedule.end}
                            onChange={handleDateEndChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            readOnly={viewschedule}
                        />
                    </Grid>


                </MuiPickersUtilsProvider>
                <TextField
                    name="description"
                    margin="dense"
                    label="상세 내용"
                    onChange={handleTextChange}
                    fullWidth
                    value={schedule.description}
                />
            </DialogContent>
            {
                viewschedule ?
                    <DialogActions>
                        <Button onClick={handleClose} color="primary">
                            취소
                    </Button>
                        <Button onClick={handleDelete} color="primary">
                            삭제
                    </Button>
                        <Button onClick={handleUpdate} color="primary">
                            수정
                    </Button>
                    </DialogActions>
                    :
                    <DialogActions>
                        <Button onClick={handleClose} color="primary">
                            취소
                    </Button>
                        <Button onClick={handleSubmit} color="primary">
                            작성
                    </Button>
                    </DialogActions>

            }
        </Dialog >
    );
}

export default FormDialog