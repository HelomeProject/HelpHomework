import React, { useState, useEffect } from 'react';
import FileUpload from './FileUpload'
import ScoreTable from './ScoreTable'
import getCookieValue from '../../getCookie'
import { Paper, Grid, Select, MenuItem } from '@material-ui/core';
import useStyles from './HomeworkContentCSS'
import axios from 'axios'

const HomeworkContent = ({ mode }) => {
    const classes = useStyles();
    const [homeworkIdx, setHomeworkIdx] = useState('')
    const [homeworklist, setHomeworklist] = useState([])
    const [rows, setRows] = useState([])
    const [rowsteacher, setRowsteacher] = useState([])
    const [url, seturl] = useState('')

    useEffect(() => {
        const config = { headers: { 'Authorization': getCookieValue('token') } }
        axios.get('http://k02c1101.p.ssafy.io:9090/api/board/homeworks', config)
            .then(res => {
                setHomeworklist(res.data.HomeworkNoticeList)
                setHomeworkIdx(res.data.HomeworkNoticeList[0].homeworkNotice_idx)
            })
            .catch(e => { console.log(e) })

    }, [])

    useEffect(() => {
        const fronturl = 'http://k02c1101.p.ssafy.io:8000'
        const config = {
            headers: { 'Authorization': getCookieValue('token') },
        }
        if (mode === 0) {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/homeworks", config)
                .then(res => {
                    if (res.data !== "") {
                        setRows(res.data.HomeworkList)
                        return (res.data.HomeworkList)
                    }
                    else
                        return null

                })
                .then(data => {
                    if (data !== null) { seturl(fronturl + data[0].homework_url) }

                })
                .catch((err) => { console.log(err) })
        } else {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/homeworks/" + String(homeworkIdx), config)
                .then(res => {
                    if (res.data !== "") {
                        console.log(res.data)
                        setRowsteacher(res.data.HomeworkList)
                        return (res.data.HomeworkList)
                    }
                    else
                        return null

                })
                .then(data => {
                    if (data !== null) { seturl(fronturl + data[0].homework_url) }

                })
                .catch((err) => { console.log(err) })

        }
    }, [mode, homeworkIdx])

    const handleChange = (event) => {
        setHomeworkIdx(event.target.value);
    };

    return (
        <>
            <Paper className={classes.paperFileUpload}>
                <Grid container alignItems="center">
                    <Grid item xs={6}>
                        <Select
                            fullWidth
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={homeworkIdx}
                            onChange={handleChange}
                        >
                            {homeworklist.map(val => <MenuItem
                                key={val.homeworkNotice_idx} value={val.homeworkNotice_idx}
                            >{val.homeworkNotice_title}</MenuItem>)}
                        </Select>
                    </Grid>
                    <Grid item xs={6}>
                        <FileUpload mode={mode} homeworkIdx={homeworkIdx} rows={rows} setRows={setRows} rowsteacher={rowsteacher} setRowsteacher={setRowsteacher} />
                    </Grid>
                </Grid>
            </Paper>
            <ScoreTable mode={mode} homeworkIdx={homeworkIdx} rows={rows} rowsteacher={rowsteacher} url={url} seturl={seturl} />
        </>
    )
}
HomeworkContent.defaultPorps = {
    mode: 1
}

export default HomeworkContent