import React, { useState } from 'react'

function ChatProfile(response) {
   
    // console.log(response.data);
    

    return (
        <div className="chatProfile card d-flex flex-row justify-content-center align-items-center p-2 rounded-3 overflow-hidden mb-1" onClick={()=>response.name(response.data.groupName,response.data.groupId)}>
            <div className="nameBox card-body p-2 ms-1 h-100 fs-5 fw-bolder d-flex flex-row justify-content-center align-items-center rounded-2 border border-dark-subtle">
                {response.data.groupName.slice(0,2).toUpperCase()}
            </div>

            <div className="card-body w-75 ms-3 fs-6 h-100 p-0 pe-1">
                <div className="fw-bold text-nowrap fs-6 overflow-hidden w-100 member">
                    {response.data.groupName}
                </div>
                <div className="text-nowrap overflow-hidden w-100">
                    {response.data.text}
                </div>
            </div>
        </div>
    )
}

export default ChatProfile