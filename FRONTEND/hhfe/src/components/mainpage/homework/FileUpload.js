import React, { useState } from 'react';
import useStyles from './FileUploadCSS'
import Button from '@material-ui/core/Button';
import axios from 'axios'

axios.defaults.xsrfCookieName = 'csrftoken';
axios.defaults.xsrfHeaderName = 'X-CSRFTOKEN';

const FileUpload = () => {
    const [img, setImage] = useState(null);
    const classes = useStyles();

    const onChange = (e) => {
        setImage(e.target.files[0])

        console.log(typeof e.target.files[0])
        console.log(e.target.files[0])
    }

    const onClick = () => {
        console.log(img)
        const formData = new FormData();
        formData.append('file', img);
        console.log(formData)
        console.log(img)

        return axios.post("http://127.0.0.1:8000/api/v1/calc/", formData)
            .then(res => {
                console.log(res)
                alert('성공')
            })
            .catch(err => {
                alert('실패')
            })
    }


    return (
        <div>
            <label htmlFor="fileupload" >업로드</label>
            <input type="file" id="fileupload" name="file" onChange={onChange} readOnly style={{ display: 'none' }} />
            <input className={classes.uploadbox} readOnly value="파일선택" />

            <Button type="button" variant="contained" color="primary" onClick={onClick}> submit </Button>

        </div>
    )
}
export default FileUpload
