import React from 'react';
import FileUpload from './FileUpload'
import ScoreTable from './ScoreTable'
import { Paper, Grid, Select, MenuItem } from '@material-ui/core';
import useStyles from './HomeworkContentCSS'

const HomeworkContent = ({ mode }) => {
    const classes = useStyles();
    const [homework, setHomework] = React.useState('');

    const handleChange = (event) => {
        setHomework(event.target.value);
    };

    const homeworkapp = [
        {
            homeworkidx: 1,
            homeworktitle: "숙제 : 구몬 1회 풀기"
        },
        {
            homeworkidx: 2,
            homeworktitle: "숙제 : 수학익힘책 다 풀기"
        },
        {
            homeworkidx: 3,
            homeworktitle: "숙제 : 그냥 풀어라"
        },
    ]

    const homeworklist = homeworkapp.map((val) => {
        return (
            <MenuItem key={val.homeworkidx} value={val.homeworkidx}>{val.homeworktitle}</MenuItem>
        )
    })

    return (
        <>
            <Paper className={classes.paperFileUpload}>
                <Grid container>
                    <Grid item xs={6}>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={homework}
                            onChange={handleChange}
                            autoWidth
                        >
                            {homeworklist}
                        </Select>
                    </Grid>
                    <Grid item xs={6}>
                        <FileUpload />

                    </Grid>
                </Grid>
            </Paper>
            <ScoreTable mode={mode} />
        </>
    )
}
HomeworkContent.defaultPorps = {
    mode: 1
}

export default HomeworkContent