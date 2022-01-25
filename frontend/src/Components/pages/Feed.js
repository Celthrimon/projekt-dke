import * as React from 'react';
import {useEffect, useState} from 'react';
import PrimarySearchAppBar from '../navbar';
import Post from '../Post';
import RefreshIcon from '@mui/icons-material/Refresh';
import {Button} from '@mui/material';
import NewPost from '../NewPost';

export default function Feed({user}) {

    const followingUrl = "/mymood/following/followUser/" + user + "/";
    const postsUrl = "/mymood/posting/posts/?userName=";
    const followingHashtagsUrl = "/mymood/following/followHashtag/" + user;
    const postsUrlHashtags = "/mymood/posting/posts/?hashtag=";
    const [posts, setPosts] = useState([]);
    const [hashtagPosts, setHashtagPosts] = useState([]);

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
            console.log(to_append);
            posts_temp = [...posts_temp, ...to_append];
            setPosts(posts_temp);
            console.log(posts_temp);
        });
    }
    var fetchUrlHashtags = async () => {
        if (user == undefined) return;
        const response = await fetch(followingHashtagsUrl);
        var json = await response.json();
        console.log(json);
        var posts_temp = [];
        json.forEach(async (hastag_to_fetch) => {
            console.log(hastag_to_fetch);
            console.log(postsUrlHashtags + hastag_to_fetch.title.substring(1, hastag_to_fetch.title.length));
            var response = await fetch(postsUrlHashtags + hastag_to_fetch.title.substring(1, hastag_to_fetch.title.length));
            var to_append = await response.json();
            console.log(to_append);
            posts_temp = [...posts_temp, ...to_append]
            setHashtagPosts(posts_temp);
            console.log(posts_temp)
        })
    }
    useEffect(() => {
        fetchURL();
        fetchUrlHashtags();
    }, []);

    if (user == undefined) return (<a href="/">You should not be here</a>);

    return (<>
        <PrimarySearchAppBar user={user}></PrimarySearchAppBar>
        <br></br>

        <div style={{width: "60%", maxWidth: "600px", margin: "auto"}}>
            <NewPost currentUser={user}/>
            <br></br>
            {/* {console.log(posts)}
            {posts.sort((a, b) => a.date < b.date ? 1:-1).map((post) => {
                return (<><Post post={post} currentUser={user}/><br/></>);
            })}
            {console.log(hashtagPosts)}
            {hashtagPosts.map((post) => {
                return (<><Post post={post} currentUser={user}/><br/></>)
            })} */}
            {
                posts.concat(hashtagPosts)
                    .filter((i, p) => posts.indexOf(i)===p)
                    .sort((a, b) => a.date < b.date ? 1:-1)
                    .map((post) => {
                        return (<><Post post={post} currentUser={user}/><br/></>);
                    })
            }
        </div>
        <br></br>
        <div style={{width: "35px", margin: "auto"}}>
            <Button onClick={() => {
                fetchURL();
                fetchUrlHashtags();
            }}>
                <RefreshIcon sx={{width: "35px"}}/>
            </Button>
        </div>

    </>);

}