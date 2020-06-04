import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import axios from 'axios'
import TextField from '@material-ui/core/TextField';

axios.defaults.xsrfCookieName = 'csrftoken';
axios.defaults.xsrfHeaderName = 'X-CSRFTOKEN';

const FileUpload = () => {
    const [img, setImage] = useState(null);
    const [imgname, setImgname] = useState("")

    const onChange = (e) => {
        setImage(e.target.files[0])
        setImgname(e.target.files[0].name)
    }

    const onClick = () => {
        const formData = new FormData();
        formData.append('file', img);

        return axios.post("http://127.0.0.1:8000/api/v1/calc/", formData)
            .then(res => {
            })
            .catch(err => {
            })
    }


    return (
        <div>
            <input accept="image/*" style={{display:'none'}} onChange={onChange} id="icon-button-file" type="file" />
            <TextField label="사진" value={imgname} disabled/>
            <label htmlFor="icon-button-file">
                <IconButton color="primary" aria-label="upload picture" component="span">
                    <PhotoCamera style={{ fontSize: 40 }}/>
                </IconButton>
            </label>
            <Button type="button" variant="contained" color="primary" onClick={onClick}> 숙제 내기 </Button>

        </div>
    )
}
export default FileUpload
