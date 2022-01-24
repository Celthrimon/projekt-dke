import { Paper } from '@mui/material';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import {useState, useEffect} from 'react';



function User({ user, profileUser, update }) {
    console.log(user);
    console.log(profileUser);
    
    const[followCheck, setFollowCheck] = useState(false);

    const url = '/mymood/following/validateFollowsUser/'+profileUser+'?user='+user.username;

    var fetchValidateFollowing = async () => {
        const response = await fetch(url);
        if(response.ok) {
            const json = await response.json();
            setFollowCheck(json);
        }
    }

    useEffect(() => {
        fetchValidateFollowing()
    }, []);

    const unfollowUrl = "/mymood/following/followUser/"+profileUser+"/?user="+user.username;
    const followUrl = "/mymood/following/followUser/"+profileUser+"?user="+user.username;

    if(followCheck) {
        return (
            <>
            <Paper style={{padding:"5px", marginTop:"10px"}}>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6} >
                        <p>{user.username}</p>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <Button
                            sx={{mt: 1}}
                            fullWidth
                            color="error"
                            variant="outlined"
                            onClick={ ()=>{
                                fetch(unfollowUrl, {
                                    method: 'DELETE',
                                    headers: {
                                    'Content-Type': 'application/json'
                                    }
                                });
                                setFollowCheck(false);
                                update();
                            }}
                        >
                            unfollow
                        </Button>
                    </Grid>
                </Grid>
            </Paper>

            </>
        );
    } else {
        return (
            <>
            <Paper style={{padding:"5px", marginTop:"10px"}}>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6} >
                        <p>{user.username}</p>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <Button
                            sx={{mt: 1}}
                            fullWidth
                            variant="outlined"
                            onClick={ ()=>{
                                fetch(followUrl, {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json'
                                    }
                                    });
                                setFollowCheck(true);
                                update();
                            }}
                        >
                            follow
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
    
            </>
        );
    }
}

export default User;
