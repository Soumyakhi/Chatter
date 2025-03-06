import React from 'react';

function Member({ data}) {  // Default empty function
    return (
        <div 
            className="card d-flex flex-row justify-content-center align-items-center p-2 rounded-3 overflow-hidden mb-2 w-100" 
            
        >
            <div className="card-body p-2 ms-1 h-100 fs-5 fw-bolder d-flex flex-row justify-content-center align-items-center rounded-2 border border-dark-subtle nameBox">
                {data.slice(0, 2).toUpperCase()}
            </div>
            <div className="fw-bold text-nowrap fs-6 overflow-hidden w-100 ms-3 member">
                {data}
            </div>
        </div>
    );
}

export default Member;
