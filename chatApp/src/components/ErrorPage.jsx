import React from 'react'
// import Home from './Home'
import { Link } from 'react-router-dom'

function ErrorPage() {
    return (
        <>
            {/* <div className='errorBox'> */}
                <div id="clouds">
                    <div class="cloud x1"></div>
                    <div class="cloud x1_5"></div>
                    <div class="cloud x2"></div>
                    <div class="cloud x3"></div>
                    <div class="cloud x4"></div>
                    <div class="cloud x5"></div>
                </div>
                <div class='c'>
                    <div class='_404'>404</div>

                    <div class='_1'>THE PAGE</div>
                    <div class='_2'>WAS NOT FOUND</div>
                    <Link to="/home">
                        <p class='errorbtn' href='#web'>BACK TO HOME</p>
                    </Link>
                </div>
            {/* </div> */}
        </>

    )
}

export default ErrorPage