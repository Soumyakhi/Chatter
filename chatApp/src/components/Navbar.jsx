import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

function Navbar() {

    const navigate=useNavigate();

    const logoutFun=()=>{
        localStorage.removeItem("uname");
        localStorage.removeItem("jwt");
        navigate('/login')
    }

    const uname=localStorage.getItem('uname');

    const [username, setUsername] = useState(uname);
    // setUsername(uname);

    return (
        <div className='d-flex justify-content-md-between align-items-center w-100 p-2 navBar'>
            {/* <div className="appName">Chatter</div> */}
            {/* <div className='d-flex'> */}
            <div className="userName fs-5">{username}</div>
            <div class="logout fs-3" data-bs-toggle="tooltip" data-bs-placement="bottom" title="LOG OUT" onClick={logoutFun}>
                <i class="bi bi-box-arrow-right"></i>
            </div>
            {/* </div> */}
        </div>
    )
}

export default Navbar







