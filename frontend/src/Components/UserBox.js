import {useState, useEffect} from 'react';
import User from './User'
import PersonIcon from '@mui/icons-material/Person';
import { Paper, Stack, Typography } from '@mui/material';
import PrimarySearchAppBar from './navbar';
import Post from './Post';
import Grid from '@mui/material/Grid';
import { Box } from '@mui/system';

function UserBox({user}) {
    const urlFollowed = "/mymood/following/followUser/"+user+"/";
    const urlMyPosts = "/mymood/posting/posts?userName="+user;
    const urlFollower = "/mymood/following/follower/"+user;

    const [followedUsers, setFollowedUsers] = useState([]);
    const [myPosts, setPosts] = useState([]);
    const [follower, setFollower] = useState([]);

    var fetchFollowed = async () => {
        const responseFollowed = await fetch(urlFollowed);
        if(responseFollowed.ok) {
            const jsonFollowed = await responseFollowed.json();
            setFollowedUsers(jsonFollowed);
        }
    }

    var fetchMyPosts = async () => {
        const responseMyPosts = await fetch(urlMyPosts);
        if(responseMyPosts.ok) {
            const jsonMyPosts = await responseMyPosts.json();
            setPosts(jsonMyPosts);
        }
    }

    var fetchFollower = async () => {
        const responseFollower = await fetch(urlFollower);
        if(responseFollower.ok) {
            const jsonFollower = await responseFollower.json();
            setFollower(jsonFollower);
        }
    }


    useEffect(() => {
        fetchFollowed();
        fetchMyPosts();
        fetchFollower();
    }, []);

    return (
        <>
            <PrimarySearchAppBar user={user}></PrimarySearchAppBar>
            <br></br>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center'
                }}
            >
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={3}>
                        <br/>
                        <br/>
                        <Paper style={{ padding: "10px", width: "200px", textAlign: "center" }}>
                            <PersonIcon />
                            <br />
                            {user} follows:
                            <br />
                            <Stack spacing={0}>
                                {followedUsers.map((followed) => {
                                    return (<User key={followed.username} user={followed} profileUser={user}/>);
                                })}
                            </Stack>
                        </Paper>
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <br/>
                        <br/>
                        <Paper style={{ padding: "10px", width: "200px", textAlign: "center" }}>
                            <PersonIcon />
                            <br />
                            {user} is followed by:
                            <br />
                            <Stack spacing={0}>
                                {follower.map((follower) => {
                                    return (<User key={follower.username} user={follower} profileUser={user}/>);
                                })}
                            </Stack>
                        </Paper>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        {user+"'s"} posts:
                        <br/>
                        <br/>
                        {myPosts.map((post) => {
                            return (<><Post post={post} currentUser={user} /><br/></>);
                        })}
                    </Grid>
                </Grid>
            </Box>
        </>
    )
}

export default UserBox;