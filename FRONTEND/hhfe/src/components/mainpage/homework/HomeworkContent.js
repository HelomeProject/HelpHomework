import React from 'react';
import FileUpload from './FileUpload'
import ScoreTable from './ScoreTable'
import Paper from '@material-ui/core/Paper';
import useStyles from './HomeworkContentCSS'


const HomeworkContent = () => {
    const classes = useStyles();
    return (
        <div>
            <Paper className={classes.paperFileUpload}>
                <FileUpload />
            </Paper>
            <ScoreTable />
        </div >
    )
}

export default HomeworkContent