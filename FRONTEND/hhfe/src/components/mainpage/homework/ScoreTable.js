import React, { useState, useEffect } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';

import useStyles from './ScoreTableCSS'
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import CropOriginalIcon from '@material-ui/icons/CropOriginal';
import IconButton from '@material-ui/core/IconButton';
import axios from 'axios'
import getCookieValue from '../../getCookie'

const Viewtable = ({ rows, seturl, mode, rowsteacher }) => {
    const fronturl = 'http://k02c1101.p.ssafy.io:8000'
    if (mode === 0) {
        return (
            <>
                {rows === [] ? (
                    <TableRow>
                        <TableCell component="th" scope="row">
                            1
                        </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                    </TableRow>
                ) : (
                        <>
                            {rows.map((val, idx) => (
                                <TableRow key={idx}>
                                    <TableCell component="th" scope="row">
                                        {idx + 1}
                                    </TableCell>
                                    <TableCell align="right">{val.homework_submitDate}</TableCell>
                                    <TableCell align="right">{val.homework_score}</TableCell>
                                    <TableCell align="right"  >
                                        <IconButton onClick={() => { seturl(fronturl + val.homework_url) }}> <CropOriginalIcon /> </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </>
                    )
                }
            </>
        )
    } else {
        return (
            <>
                {rowsteacher === [] ? (
                    <TableRow>
                        <TableCell component="th" scope="row">
                            1
                        </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                        <TableCell align="right"> - </TableCell>
                    </TableRow>
                ) : (
                        <>
                            {rowsteacher.map((val, idx) => (
                                <TableRow key={idx}>
                                    <TableCell component="th" scope="row">
                                        {idx + 1}
                                    </TableCell>
                                    <TableCell align="right">{val.homework_submitDate}</TableCell>
                                    <TableCell align="right">{val.homework_score}</TableCell>
                                    <TableCell align="right"  >
                                        <IconButton onClick={() => { seturl(fronturl + val.homework_url) }}> <CropOriginalIcon /> </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </>
                    )
                }
            </>
        )

    }

}

const ScoreTable = ({ mode, homeworkIdx }) => {
    const classes = useStyles();
    const tableheadname = [
        ['No.', '숙제 제출일', '점수', '이미지'],
        ['No.', '이름', '점수', '파일']
    ]
    const [rows, setRows] = useState([])
    const [rowsteacher, setRowsteacher] = useState([])
    const [url, seturl] = useState('')




    useEffect(() => {
        const fronturl = 'http://k02c1101.p.ssafy.io:8000'
        const config = {
            headers: { 'Authorization': getCookieValue('token') },
        }
        if (mode === 0) {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/homeworks", config)
                .then(res => {
                    setRows(res.data.HomeworkList)
                    return (res.data.HomeworkList)
                })
                .then(data => {
                    seturl(fronturl + data[0].homework_url)
                })
                .catch((err) => { console.log(err) })
        } else {
            axios.get("http://k02c1101.p.ssafy.io:9090/api/homeworks/" + String(homeworkIdx), config)
                .then(res => {
                    console.log(res)
                    setRowsteacher(res.data.HomeworkList)
                    return (res.data.HomeworkList)
                })
                .then(data => {
                    seturl(fronturl + data[0].homework_url)
                })
                .catch((err) => { console.log(err) })

        }
        console.log("test")
    }, [mode, homeworkIdx])





    return (
        <Grid container spacing={3}>
            <Grid item xs={12} sm={12} md={6}>
                <Paper className={classes.paper}>
                    <TableContainer >
                        <Table className={classes.table} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell className={classes.tableCellNo} >{tableheadname[mode][0]}</TableCell>
                                    <TableCell className={classes.tableCellDate} align="right">{tableheadname[mode][1]}</TableCell>
                                    <TableCell className={classes.tableCellScore} align="right">{tableheadname[mode][2]}</TableCell>
                                    <TableCell className={classes.tableCellImage} align="right">{tableheadname[mode][3]}</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                <Viewtable rows={rows} seturl={seturl} rowsteacher={rowsteacher} mode={mode} />
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Paper>
            </Grid>
            <Grid item xs={12} sm={12} md={6}>
                <Paper className={classes.paper}>
                    <img className={classes.img} src={url} alt="test" />
                </Paper>
            </Grid>
        </Grid >
    );
}
export default ScoreTable