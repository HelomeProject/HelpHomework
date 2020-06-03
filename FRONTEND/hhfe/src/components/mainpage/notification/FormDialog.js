import React from 'react'
import { Button, Grid } from '@material-ui/core';
import DateFnsUtils from '@date-io/date-fns';
import TextField from '@material-ui/core/TextField';
import { MuiPickersUtilsProvider, KeyboardDatePicker } from '@material-ui/pickers';
import { Dialog, DialogActions, DialogContent, DialogTitle } from '@material-ui/core';

const FormDialog = ({ open, setOpen, schedule, setSchedule, setcreateSchedule }) => {
    const handleClose = () => {
        setcreateSchedule.createSchedules([schedule])
        setOpen(false)
    };
    const handleDateStartChange = (date) => {
        setSchedule({ ...schedule, start: date })
    }
    const handleDateEndChange = (date) => {
        setSchedule({ ...schedule, end: date })
    }
    const handleTextChange = (e) => {
        const { name, value } = e.target
        console.log(name, value)
        setSchedule({ ...schedule, [name]: value })
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
                        />
                    </Grid>


                </MuiPickersUtilsProvider>
                <TextField
                    name="description"
                    margin="dense"
                    label="상세 내용"
                    onChange={handleTextChange}
                    fullWidth
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                    </Button>
                <Button onClick={handleClose} color="primary">
                    Subscribe
                    </Button>
            </DialogActions>
        </Dialog >
    );
}

export default FormDialog