import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import LoginSignUp from "./components/LoginSignUp";
import Home from "./components/Home";
import AddMember from "./components/AddMember";
import Navbar from "./components/Navbar";
import MyChat from "./components/MyChat";
import FriendChat from "./components/FriendChat";
import PopUp from "./components/PopUp";
// import WebSocketChat from "./components/WebSocketComponent";
import ErrorPage from "./components/ErrorPage";
import NoChat from "./components/NoChat";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginSignUp />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<LoginSignUp />} />
        {/* <Route path="/addMember" element={<AddMember />} /> */}
        {/* <Route path="/navbar" element={<Navbar />} /> */}
        {/* <Route path="/chat" element={<MyChat/>}/> */}
        {/* <Route path="/no" element={<NoChat/>}/> */}
        <Route path="/error" element={<ErrorPage/>}/>

      </Routes>
    </BrowserRouter>
  );
}

export default App;
