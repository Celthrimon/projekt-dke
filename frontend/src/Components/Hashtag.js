import { Paper } from '@mui/material';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';

function Hashtag({ hashtag, profileUser, update }) {
    console.log(hashtag);
    console.log(profileUser);
    
    const unfollowUrl = "/mymood/following/followHashtag/"+profileUser+"/?title="+hashtag.title.substring(1, hashtag.title.lenght);

    return (
        <>
        <Paper style={{padding:"5px", marginTop:"10px"}}>
            <Grid container spacing={2}>
                <Grid item xs={12} sm={6} >
                    <p>{hashtag.title}</p>
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

export default Hashtag;