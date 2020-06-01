import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({

    paper: {
        minHeight: '100%',
    },
    title: {
        textAlign: 'center',
    },
    button: {
        visibility: 'hidden',
    }
}));

export default useStyles