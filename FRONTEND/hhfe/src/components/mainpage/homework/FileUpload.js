import React, { useState } from 'react';
import useStyles from './FileUploadCSS'
import Button from '@material-ui/core/Button';



const FileUpload = () => {
    const [img, setImage] = useState(null);
    const classes = useStyles();

    const onChange = (e) => {
        setImage(e.target.files[0])
        console.log(typeof e.target.files[0])
        console.log(e.target.files[0])
    }

    const onClick = async () => {
        const formData = new FormData();
        formData.append('file', img);
        console.log(formData)
    }

    return (
        <div>
            <label htmlFor="fileupload" >업로드</label>
            <input type="file" id="fileupload" name="file" onChange={onChange} style={{ display: 'none' }} />
            <input className={classes.uploadbox} value="파일선택" />

            <Button type="button" variant="contained" color="primary" onClick={onClick}> submit </Button>

        </div>
    )
}
export default FileUpload
