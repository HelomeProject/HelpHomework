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

    useEffect(() => {
        const config = { headers: { 'Authorization': getCookieValue('token') } }
        axios.get('http://k02c1101.p.ssafy.io:9090/api/board/homeworks', config)
            .then(res => {
                setHomeworklist(res.data.HomeworkNoticeList)
                setHomeworkIdx(res.data.HomeworkNoticeList[0].homeworkNotice_idx)
            })
            .catch(e => { console.log(e) })

    }, [])

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
                        <FileUpload homeworkIdx={homeworkIdx} />
                    </Grid>
                </Grid>
            </Paper>
            <ScoreTable mode={mode} homeworkIdx={homeworkIdx} />
        </>
    )
}
HomeworkContent.defaultPorps = {
    mode: 1
}

export default HomeworkContent