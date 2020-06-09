import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import PhotoCamera from '@material-ui/icons/PhotoCamera';
import axios from 'axios'
import TextField from '@material-ui/core/TextField';
import getCookieValue from '../../getCookie'

axios.defaults.xsrfCookieName = 'csrftoken';
axios.defaults.xsrfHeaderName = 'X-CSRFTOKEN';

const FileUpload = ({ mode, homeworkIdx, setRows, setRowsteacher, rows, rowsteacher, seturl, setDel }) => {
    const [img, setImage] = useState(null);
    const [imgname, setImgname] = useState("")

    const onChange = (e) => {
        setImage(e.target.files[0])
        if (e.target.files[0]) {
            setImgname(e.target.files[0].name)
        } else {
            setImgname("사진")
        }
    }
    const uploadData = (data, config) => {
        const fronturl = 'http://k02c1101.p.ssafy.io:8000'
        axios.post("http://k02c1101.p.ssafy.io:9090/api/homework", data, config)
            .then(res => {
                setDel(true)
                if (mode === 1) {
                    const newRow = rowsteacher.concat([res.data.Homework])
                    setRowsteacher(newRow)
                    if (newRow.length === 1) { seturl(fronturl + newRow[0].homework_url) }

                } else {
                    const newRow = rows.concat([res.data.Homework])
                    setRows(newRow)
                    if (newRow.length === 1) { seturl(fronturl + newRow[0].homework_url) }
                }

            })
            .catch(e => { console.log(e) })
    }

    const onClick = () => {
        const memberIdx = getCookieValue("memberIdx")
        const config = {
            headers: { 'Authorization': getCookieValue('token') },
        }
        const formData = new FormData();
        formData.append('file', img);
        formData.append('memberIdx', memberIdx)
        formData.append('homeworkIdx', homeworkIdx)

        return axios.post("http://k02c1101.p.ssafy.io:8000/api/v1/calc/", formData)
            .then(res => {
                return (res.data.point)
            })
            .then(data => {
                return {
                    "homework_idx": "string",
                    "homework_memberIdx": "string",
                    "homework_noticeIdx": homeworkIdx,
                    "homework_score": parseFloat(data),
                    "homework_submitDate": "string",
                    "homework_url": "string"
                }
            })
            .then(data => {
                uploadData(data, config)
            })
            .catch(err => {
                console.log(err)
            })
    }


    return (
        <div>
            <input accept="image/*" style={{ display: 'none' }} onChange={onChange} id="icon-button-file" type="file" />
            <TextField label="사진" value={imgname} disabled />
            <label htmlFor="icon-button-file">
                <IconButton color="primary" aria-label="upload picture" component="span">
                    <PhotoCamera style={{ fontSize: 40 }} />
                </IconButton>
            </label>
            <Button type="button" variant="contained" color="primary" onClick={onClick}> 숙제 내기 </Button>

        </div>
    )
}
export default FileUpload
