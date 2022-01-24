import * as React from 'react';
import PrimarySearchAppBar from '../navbar';
import Post from '../Post';
import { useState, useEffect } from 'react';
import RefreshIcon from '@mui/icons-material/Refresh';
import { Button } from '@mui/material';
import User from '../User';
import Hashtag from '../Hashtag';

export default function Search({ user }) {

    const usersUrl = "/mymood/user/";
    const hashtagsUrl = "/mymood/posting/hashtags/";
    const [users, setUsers] = useState([]);
    const [hashtags, setHashtagPosts] = useState([]);

    var fetchUsersUrl = async () => {
        const responseUser = await fetch(usersUrl);
        if(responseUser.ok) {
            const jsonUser = await responseUser.json();
            setUsers(jsonUser);
            console.log(jsonUser);
        }
    }
    var fetchHashtagsUrl = async() => {
        const responseHashtag = await fetch(hashtagsUrl);
        if(responseHashtag.ok) {
            const jsonHashtag = await responseHashtag.json();
            setHashtagPosts(jsonHashtag);
            console.log(jsonHashtag);
        }
    }
    useEffect(() => {
        fetchUsersUrl();
        fetchHashtagsUrl();
    }, []);

    if (user == undefined) return (<a href="/">You should not be here</a>);

    return (<>
        <PrimarySearchAppBar user={user}></PrimarySearchAppBar>
        {users.map((u)=>{
            return(<User user={u} profileUser={user} update={()=>{}}/>)
        })}
        {hashtags.map((h)=>{
            return(<Hashtag hashtag={h} profileUser={user} update={()=>{}}/>)
        })}
    </>);

}