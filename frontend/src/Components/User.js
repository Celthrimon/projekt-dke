import { Paper } from '@mui/material';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';



function User({ user, profileUser, update }) {
    console.log(user);
    console.log(profileUser);
    
    const unfollowUrl = "/mymood/following/followUser/"+profileUser+"/?user="+user.username;

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
                            update();
                        }}
                    >
                        remove
                    </Button>
                </Grid>
            </Grid>
        </Paper>

        </>
    );
}

export default User;
