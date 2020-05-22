import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    paper: {
        minHeight: '150%'
    },
    table: {
        margin: theme.spacing(2),
        width: '95%'
    },
    tableCellNo: {
        width: '10%',
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    tableCellDate: {
        width: '25%',
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    tableCellScore: {
        width: '25%',
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    tableCellImage: {
        width: '25%',
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    }
}));

export default useStyles