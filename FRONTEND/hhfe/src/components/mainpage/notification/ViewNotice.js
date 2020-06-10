import React from 'react'
import { Dialog, DialogTitle, DialogContent } from '@material-ui/core';
import { Typography, Grid } from '@material-ui/core';

const ViewNotice = ({ title, imgurl, setOpenView, openView }) => {
    const handleClose = (e) => {
        setOpenView(false)
    }
    return (
        <Dialog open={openView} onClose={handleClose} maxWidth={'md'}>
            <DialogTitle id="form-dialog-title">공지사항</DialogTitle>
            <DialogContent>
                <Grid container justify="center">
                    <Typography variant="h3" component="h3">
                        {title}
                    </Typography>

                </Grid>
                <Grid container justify="center">
                    <img src={imgurl} alt="noimg" />
                </Grid>
            </DialogContent>
        </Dialog>
    )
}
export default ViewNotice