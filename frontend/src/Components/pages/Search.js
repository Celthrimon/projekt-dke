import * as React from 'react';
import {useEffect, useState} from 'react';
import PrimarySearchAppBar from '../navbar';
import {TextField} from '@mui/material';
import User from '../User';
import Hashtag from '../Hashtag';

export default function Search({user}) {

    const usersUrl = "/mymood/user/";
    const hashtagsUrl = "/mymood/posting/hashtags/";
    const [users, setUsers] = useState([]);
    const [hashtags, setHashtagPosts] = useState([]);
    const [query, setQuery] = useState("");

    var fetchUsersUrl = async () => {
        const responseUser = await fetch(usersUrl);
        if (responseUser.ok) {
            const jsonUser = await responseUser.json();
            setUsers(jsonUser);
        }
    }

    console.log()
    var fetchHashtagsUrl = async () => {
        const responseHashtag = await fetch(hashtagsUrl);
        if (responseHashtag.ok) {
            const jsonHashtag = await responseHashtag.json();
            setHashtagPosts(jsonHashtag);
        }
    }
    useEffect(() => {
        fetchUsersUrl();
        fetchHashtagsUrl();
    }, []);

    if (user == undefined) return (<a href="/">You should not be here</a>);

    return (<>
        <PrimarySearchAppBar user={user}></PrimarySearchAppBar>
        <div style={{width: "60%", maxWidth: "600px", margin: "auto"}}>
            <TextField
                sx={{width: "100%", marginTop: "10px"}}
                id="outlined-basic"
                value={query}
                label="Search"
                variant="outlined"
                onChange={(e) => {
                    setQuery(e.target.value)
                }}
            />
            <div style={{width: "48%", float: "left"}}>
                {users.filter((u) => {
                    return query != "" && u.username.toLowerCase().includes(query.toLowerCase());
                }).map((u) => {
                    return (<User user={u} profileUser={user} update={() => {
                    }}/>)
                })}
            </div>
            <div style={{width: "48%", float: "right"}}>
                {hashtags.filter((h) => {
                    return query != "" && h.title.toLowerCase().includes(query.toLowerCase());
                }).map((h) => {
                    return (<Hashtag hashtag={h} profileUser={user} update={() => {
                    }}/>)
                })}
            </div>
        </div>
    </>);

}