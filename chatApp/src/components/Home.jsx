import React, { useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import ChatProfile from './ChatProfile';
import Navbar from './Navbar';
import FriendChat from './FriendChat';
import MyChat from './MyChat';
import AddMember from './AddMember';
import PopUp from './PopUp';
import axios from 'axios';
import bgPic from '../assets/bg-pic.jpg'
import NoChat from './NoChat';

const Home = () => {
    const navigate = useNavigate();
    const [isOpen, setIsOpen] = useState(false);
    const [popupOpen, setPopupOpen] = useState(false);
    const [searchInput, setSearchInput] = useState("");
    const [chatProfileData, setChatProfileData] = useState([]);

    const [groupName, setGroupName] = useState("");
    const [groupId, setGroupId] = useState(null);

    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");
    const [ws, setWs] = useState(null);
    const [UserId, setUserId] = useState(null);
    const [ISclicked, setISclicked] = useState(false);

    const uid = localStorage.getItem('uId');
    const jwt = localStorage.getItem('jwt');

    const chatEndRef = useRef(null);

    useEffect(() => {
        chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]); // Runs whenever messages update

    // Redirect user to login if JWT is not found
    useEffect(() => {
        if (!jwt) {
            navigate('/login');
        }
    }, [jwt, navigate]);

    // Connect WebSocket only when groupId is set
    useEffect(() => {
        if (!groupId || !jwt) return; // Prevent WebSocket from connecting when no group is selected

        const socket = new WebSocket(`ws://localhost:8080/ws/groups/${groupId}/${jwt}`);

        socket.onopen = () => {
            console.log("Connected to WebSocket");
            setWs(socket);
        };

        socket.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                console.log("Received message:", data);
        
                setMessages((prevMessages) => [
                    ...prevMessages,
                    { 
                        text: data.text, 
                        senderId: data.uId, 
                        uName: data.uName || "Unknown"  // Fallback if uName is missing
                    }
                ]);
            } catch (error) {
                console.error("Error parsing WebSocket message:", error);
            }
        };
        

        socket.onclose = () => {
            console.log("Disconnected from WebSocket");
        };

        return () => socket.close();
    }, [groupId, jwt]);



    const sendMessage = async () => {
        if (!message.trim()) return;

        // Send message to backend
        try {
            await axios.post(`http://localhost:8080/index/addText`,
                { text: message, groupid: groupId },
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        'Content-Type': 'application/json',
                    },
                }
            );

            // Manually add message to state to reflect instantly
            // setMessages(prev => [...prev, { text: message, senderId: Number(uid) }]);
            setMessage("");

        } catch (error) {
            console.error("Error sending message:", error);
            navigate('/error');

        }
    };

    const fetchData = async (path) => {
        try {
            if (!jwt) {
                console.warn("No JWT found. Please log in.");
                return [];
            }

            const baseUrl = 'http://localhost:8080/index/fetchGroups';
            const response = await axios.get(`${baseUrl}/${encodeURIComponent(path.trim() || " ")}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });

            console.log(response.data);
            //    console.log( response.data.sort((a,b)=>b.textId - a.textId));
            return response.data;

        } catch (error) {
            navigate('/error');
            return [];
        }
    };

    // Fetch chat groups when searchInput changes
    useEffect(() => {
        const delaySearch = setInterval(async () => {
            const data = await fetchData(searchInput);
            setChatProfileData(data);
            console.log("calling");
        }, 1000); // Added a debounce delay of 300ms
        // delaySearch();
        return () => clearTimeout(delaySearch);
    }, [searchInput, messages, popupOpen]);

    // useEffect(() => {
    //     if (!groupId) return; // Ensure we have a valid group
    
    //     fetchData(); // Fetch immediately when group changes
    
    //     const interval = setInterval(() => {
    //         fetchData(); // Fetch every second
    //     }, 1000); // 1000ms = 1 second
    
    //     return () => clearInterval(interval); // Cleanup interval on unmount or when groupId changes
    // }, [groupId]); // Runs when groupId changes
    


    const changeName = (name, id) => {
        setGroupName(name);
        setGroupId(id);
        setMessages([]); // Reset messages when switching groups
        setISclicked(true);
    };


    const FetchAllText = async () => {
        try {
            if (!jwt) {
                console.warn("No JWT found. Please log in.");
                return [];
            }

            const response = await axios.get(`http://localhost:8080/index/fetchAllTexts/${groupId}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            console.log(response.data);
            setMessages(response.data)

        } catch (error) {
            navigate('/error');
            return [];
        }
    }

    useEffect(() => {
        console.log("groupId:", groupId);  // Debugging
        if (groupId) {
            FetchAllText();
        }
    }, [groupId]);



    return (
        <div className="outBox d-flex">
            {popupOpen && <PopUp onClose={() => setPopupOpen(false)} />}

            <div className="leftBox p-2 d-flex flex-column">
                <Navbar />

                <div className="mb-2 w-100 d-flex align-items-center justify-content-between">
                    <div className="addButton rounded" onClick={() => setPopupOpen(true)}>
                        <i className="bi bi-plus-square-fill"></i>
                    </div>
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search Here..."
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                    />
                </div>

                {chatProfileData.length > 0 ? (
                    chatProfileData.map((data, index) => <ChatProfile key={index} data={data} name={changeName} />)
                ) : (
                    <div className="w-100 fs-6 h-25 d-flex justify-content-center member">
                        <p>No Chats Available</p>
                    </div>
                )}
            </div>



            <div className="rightBox">

                {!ISclicked ? <NoChat /> :
                    <div>
                        <div className="groupName">
                            <div>{groupName}</div>
                            <button type="button" id="hamBurger" onClick={() => setIsOpen(!isOpen)}>
                                <i className="bi bi-list"></i>
                            </button>
                        </div>

                        <div className="groupChat">
                            <div>
                                {messages.map((msg, index) =>
                                    (msg.senderId === Number(uid) || msg.uId === Number(uid))
                                        ? <MyChat key={index} data={msg.text || ""} uname={msg.uName || "Unknown"} />
                                        : <FriendChat key={index} data={msg.text || ""} uname={msg.uName || "Unknown"} />
                                )}

                                <div ref={chatEndRef} /> {/* Invisible div for auto-scroll */}
                            </div>
                        </div>

                        <div className="input-group">
                            <input
                                type="text"
                                className="form-control rounded"
                                placeholder="Type a message..."
                                value={message}
                                onChange={(e) => setMessage(e.target.value)}
                            />
                            <button className="sendBtn rounded ms-2" type="button" onClick={sendMessage}>
                                <i className="bi bi-send-fill"></i>
                            </button>
                        </div>
                    </div>}

            </div>

            {isOpen && <AddMember groupName={groupName} groupId={groupId} />}
        </div>
    );
};

export default Home;
