import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';

import useStyles from './ScoreTableCSS'
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import CropOriginalIcon from '@material-ui/icons/CropOriginal';
import IconButton from '@material-ui/core/IconButton';


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
                                    <TableCell align="right">{val.homework_memberIdx}</TableCell>
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

const ScoreTable = ({ mode, rows, rowsteacher, seturl, url }) => {
    const classes = useStyles();
    const tableheadname = [
        ['No.', '숙제 제출일', '점수', '이미지'],
        ['No.', '학생 번호', '점수', '파일']
    ]

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
                    <img className={classes.img} src={url} alt="사진이 없습니다." />
                </Paper>
            </Grid>
        </Grid >
    );
}
export default ScoreTable