import React, { useState } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Button from '@material-ui/core/Button';
import useStyles from './MyAppBarCSS'
import LeftDrawer from './LeftDrawer'
import clsx from 'clsx';

const MyAppBar = () => {
    const classes = useStyles();
    const [open, setOpen] = useState(true);

    const handleDrawerToggle = () => {
        setOpen(!open);
    };

    return (
        <div className={classes.root}>
            <AppBar position="static"
                className={clsx(classes.appBar, {
                    [classes.appBarShift]: open,
                })}>
                <Toolbar>
                    <IconButton
                        edge="start"
                        className={classes.menuButton}
                        color="inherit" aria-label="menu"
                        onClick={handleDrawerToggle}>
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" className={classes.title}>
                        News
            </Typography>
                    <Button color="inherit">Login</Button>
                </Toolbar>
            </AppBar>
            <LeftDrawer open={open} />
        </div>
    );
}
export default MyAppBar