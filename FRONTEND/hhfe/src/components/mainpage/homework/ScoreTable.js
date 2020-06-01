import React, { useState } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import useStyles from './ScoreTableCSS'
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import CropOriginalIcon from '@material-ui/icons/CropOriginal';
import IconButton from '@material-ui/core/IconButton';

const createData = (num, date, score, url) => {
    return { num, date, score, url };
}

const rows = [
    createData(1, 159, 6.0, "https://placeimg.com/64/64/3"),
    createData(2, 237, 9.0, 37),
    createData(3, 262, 16.0, 24),
    createData(4, 305, 3.7, 67),
    createData(5, 356, 16.0, 49),
];

const ScoreTable = () => {
    const classes = useStyles();
    const tableheadname = [
        ['No.', '숙제 제출일', '점수', '이미지'],
        ['No.', '이름', '점수', '파일']
    ]
    const [url, seturl] = useState(rows[0].url)

    const viewImg = (url) => {
        seturl(url)
    }

    return (
        <Grid container spacing={3}>
            <Grid item xs={12} sm={12} md={6}>
                <Paper className={classes.paper}>
                    <TableContainer >
                        <Table className={classes.table} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell className={classes.tableCellNo} >NO.</TableCell>
                                    <TableCell className={classes.tableCellDate} align="right">과제 제출일</TableCell>
                                    <TableCell className={classes.tableCellScore} align="right">점수</TableCell>
                                    <TableCell className={classes.tableCellImage} align="right">이미지</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows.map((row) => (
                                    <TableRow key={row.num}>
                                        <TableCell component="th" scope="row" >
                                            {row.num}
                                        </TableCell>
                                        <TableCell align="right">{row.date}</TableCell>
                                        <TableCell align="right">{row.score}</TableCell>
                                        <TableCell align="right" onClick={() => { viewImg(row.url) }} >
                                            <IconButton> <CropOriginalIcon /> </IconButton>

                                        </TableCell>
                                    </TableRow>
                                ))}
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