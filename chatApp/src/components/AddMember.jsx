import React, { useState, useEffect, useCallback } from "react";
import Member from "./Member";
import axios from "axios";



function AddMember(props) {
    const [searchMember, setSearchMember] = useState("");
    const [searchResult, setSearchResult] = useState([]);
    const [groupMembers, setGroupMembers] = useState([]);
    const jwt = localStorage.getItem("jwt");

    const fetchMembers = useCallback(async () => {
        try {
            const response = await axios.get(
                `http://localhost:8080/index/fetchMembers/${props.groupId}`,
                { headers: { Authorization: `Bearer ${jwt}`, "Content-Type": "application/json" } }
            );
            setGroupMembers(response.data);
        } catch (error) {
            console.error("Error fetching group members:", error);
        }
    }, [props.groupId, jwt]);

    const fetchNonMembers = useCallback(async (query) => {
        if (!query.trim()) {
            setSearchResult([]);
            return;
        }

        try {
            const response = await axios.get(
                `http://localhost:8080/index/searchNonMembers/${query}/${props.groupId}`,
                { headers: { Authorization: `Bearer ${jwt}`, "Content-Type": "application/json" } }
            );
            setSearchResult(response.data);
        } catch (error) {
            console.error("Error fetching non-members:", error);
        }
    }, [props.groupId, jwt]);

    const addNonMember = async (UNAME, UID) => {
        try {
            await axios.post(`http://localhost:8080/index/addMember`, 
                { uid: UID, uname: UNAME, groupid: props.groupId }, 
                { headers: { Authorization: `Bearer ${jwt}`, "Content-Type": "application/json" } }
            );
    
            // Correct filtering logic
            setSearchResult((prevItems) => prevItems.filter((user) => user.uid !== UID));
    
            fetchMembers(); // Refresh group members list
        } catch (error) {
            console.error("Error adding non-member:", error);
        }
    };
    

    useEffect(() => {
        fetchMembers();
    }, [fetchMembers]);

    useEffect(() => {
        const delaySearch = setTimeout(() => {
            fetchNonMembers(searchMember);
        }, 300);
        return () => clearTimeout(delaySearch);
    }, [searchMember, fetchNonMembers]);

    return (
        <div className="d-flex flex-column p-3 addMember">
            <div className="mb-3 rounded-4 w-100 d-flex align-items-center justify-content-between">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Search Here..."
                    value={searchMember}
                    onChange={(e) => setSearchMember(e.target.value)}
                />
            </div>

            {searchMember.length === 0 ? (
                <div className="w-100 fs-6 h-25 d-flex justify-content-center member">
                    <p>Go for a Search</p>
                </div>
            ) : searchResult.length > 0 ? (
                searchResult.map((user, index) => (
                    <div key={index} className="nonMember">
                        <Member data={user.userName} />
                        <div className="addBtn">
                            <i className="bi bi-person-plus-fill" onClick={() => addNonMember(user.userName, user.uid)}></i>
                        </div>
                    </div>
                ))
            ) : (
                <div className="w-100 fs-6 h-25 d-flex justify-content-center member">
                    <p>No Users Available</p>
                </div>
            )}

            <div>
                <h6 className="text-center mb-3 mt-3 border-top pt-2">Group Members</h6>
            </div>

            {groupMembers.map((member, index) => (
                <Member key={index} data={member.uname} />
            ))}
        </div>
    );
}

export default AddMember;
