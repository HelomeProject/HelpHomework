import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    filelabel: {
        display: 'inline-block',
        padding: '10px 20px',
        color: '#999',
        verticalalign: 'middle',
        backgroundcolor: '#fdfdfd',
        cursor: 'pointer',
        border: '1px solid #ebebeb',
        borderradius: '5px',
    },
    uploadbox: {
        display: 'inline-block',
        height: '35px',
        fontsize: '18px',
        padding: '0 10px',
        verticalalign: 'middle',
        backgroundcolor: '#f5f5f5',
        border: '1px solid #ebebeb',
        borderradius: '5px'
    }
}));

export default useStyles