import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    title: {
        justifyContent: 'center',
        margin: '0.75rem'
    },
    listitem: {
        justifyContent: 'flex-start',
        width: '100%',
    }
}));

export default useStyles