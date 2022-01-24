import * as React from 'react';
import Userpanel from '../Userpanel';
import PrimarySearchAppBar from '../navbar';
import Post from '../Post';
import { useState, useEffect } from 'react';
import RefreshIcon from '@mui/icons-material/Refresh';
import { Button } from '@mui/material';
import NewPost from '../NewPost';

export default function Feed({ user }) {

    const followingUrl = "/mymood/following/followUser/" + user + "/";
    const postsUrl = "/mymood/posting/posts/?userName=";
    const [posts, setPosts] = useState([]);

    var fetchURL = async () => {
        if (user == undefined) return;
        const response = await fetch(followingUrl);
        var json = await response.json();
        json = [...json, {username: user}];
        console.log(json)
        var posts_temp = [];
        json.forEach(async (user_to_fetch) => {
            console.log(user_to_fetch)
            console.log(postsUrl + user_to_fetch.username)
            var postresponse = await fetch(postsUrl + user_to_fetch.username);
            var to_append = await postresponse.json();
            posts_temp = [...posts_temp, ...to_append];
            setPosts(posts_temp);
            console.log(posts_temp);
        });
    }
    useEffect(() => {
        fetchURL();
    }, []);

    if (user == undefined) return (<a href="/">You should not be here</a>);

    return (<>
        <PrimarySearchAppBar user={user}></PrimarySearchAppBar>
        <br></br>

        <div style={{ width: "60%", maxWidth: "600px", margin: "auto" }}>
            <NewPost currentUser={user}/>
            <br></br>
            {posts.map((post) => {
                return (<><Post post={post} currentUser={user} /><br/></>);
            })}
        </div>
        <br></br>
        <div style={{ width: "35px", margin: "auto" }}>
            <Button onClick={() => {
                fetchURL();
            }} >
                <RefreshIcon sx={{ width: "35px" }} />
            </Button>
        </div>

    </>);

}