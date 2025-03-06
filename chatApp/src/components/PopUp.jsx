import axios from 'axios';
import React, { useState } from 'react';

function PopUp({ onClose }) {
    const jwt = localStorage.getItem('jwt');
    const uId = localStorage.getItem('uId'); // Ensure it's retrieved correctly

    const [groupname, setGroupname] = useState("");
    // const [creator, setCreator] = useState({ uId }); // Ensuring `uId` is stored correctly

    const CreateGroup = async () => {
        if (!jwt) {
            console.error("JWT token is missing!");
            return;
        }
    
        try {
            const url = 'http://localhost:8080/index/createGroup'; 
    
            const data = {
                groupname,
                creator: { uId: parseInt(uId, 10) } // Convert to integer
            };
    
            console.log("Sending request:", JSON.stringify(data));
    
            const config = {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwt}`,
                },
            };
    
            const response = await axios.post(url, data, config);
    
            console.log('Group Created:', response.data);

            setGroupname("");
        } 
        
        catch (error) {
            if (error.response) {
                console.error('Server responded with:', error.response.status, error.response.data);
            } else if (error.request) {
                console.error('No response received:', error.request);
            } else {
                console.error('Error setting up request:', error.message);
            }
        }
    };
    

    return (
        <div className="position-fixed top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center bg-dark bg-opacity-50" style={{ zIndex: 1050 }}>
            <div className="popBox bg-white p-4 rounded shadow d-flex flex-column justify-content-center">
                <div className='d-flex justify-content-end align-items-center'>
                    <button onClick={onClose} className='btn-close fs-6'></button>
                </div>
                <div>
                    <input
                        type="text"
                        placeholder='Enter Group Name'
                        className="form-control mt-3"
                        value={groupname}
                        onChange={(e) => setGroupname(e.target.value)}
                    />
                    <button
                        className="btn btn-primary mt-4 crBtn"
                        onClick={CreateGroup}
                    >
                        Create Group
                    </button>
                </div>
            </div>
        </div>
    );
}

export default PopUp;
