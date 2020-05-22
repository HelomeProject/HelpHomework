import React, { useState } from 'react';

const FileUpload = () => {
    const [img, setImage] = useState(null);

    const onChange = (e) => {
        setImage(e.target.files[0])
    }

    const onClick = async () => {
        const formData = new FormData();
        formData.append('file', img);
        console.log(formData)
    }

    return (
        <div>
            <input type="file" name="file" onChange={onChange} />
            <button type="button" onClick={onClick}> submit </button>
        </div>
    )
}
export default FileUpload
