import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import useStyles from './ScoreTableCSS'
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

const createData = (name, calories, fat, carbs) => {
    return { name, calories, fat, carbs };
}

const rows = [
    createData(1, 159, 6.0, 24),
    createData(2, 237, 9.0, 37),
    createData(3, 262, 16.0, 24),
    createData(4, 305, 3.7, 67),
    createData(5, 356, 16.0, 49),
];

const ScoreTable = () => {
    const classes = useStyles();

    return (
        <Grid container spacing={3}>
            <Grid item xs={12} sm={12} md={6}>
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
                                <TableRow key={row.name}>
                                    <TableCell component="th" scope="row" >
                                        {row.name}
                                    </TableCell>
                                    <TableCell align="right">{row.calories}</TableCell>
                                    <TableCell align="right">{row.fat}</TableCell>
                                    <TableCell align="right">{row.carbs}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Grid>
            <Grid item xs={12} sm={12} md={6}>

            </Grid>

        </Grid>
    );
}
export default ScoreTable