import React from 'react'

function FriendChat(props) {
    return (
        <div className='w-100 mb-5 d-flex '>
            <div className="card-body  p-2 ms-1 h-100 fs-5 fw-bolder d-flex flex-row justify-content-center align-items-center rounded-2 border border-dark-subtle nameChatBox">
                {props.uname.slice(0, 2).toUpperCase()}
            </div>
            <div className='bubble p-2 ps-3 ms-2 rounded'>
                {props.data}
            </div>
        </div>
    )
}

export default FriendChat