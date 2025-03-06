import React from 'react'

function MyChat(props) {
  return (
    <div className='w-100 mb-5 d-flex justify-content-end'>
     
      <div className='Mybubble p-2 ps-3 ms-2 rounded'>
        {props.data}
        {/* Lorem, ipsum dolor sit amet consectetur adipisicing elit. Debitis atque alias rem necessitatibus officiis, rerum magni. Delectus eos et molestias? */}
      </div>
      <div className="card-body p-2 ms-1 text-primary h-100 fs-5 fw-bolder d-flex flex-row justify-content-center align-items-center rounded-2 border border-dark-subtle  nameChatBox">
        {props.uname.slice(0, 2).toUpperCase()}
      </div>
    </div>
  )
}

export default MyChat