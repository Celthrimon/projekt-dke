import User from './User'
import {useState, useEffect} from 'react';
import PersonIcon from '@mui/icons-material/Person';
import TextField from '@mui/material/TextField';
import EmojiEmotionsIcon from '@mui/icons-material/EmojiEmotions';
import SendIcon from '@mui/icons-material/Send';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import { red } from '@mui/material/colors';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import FavoriteIcon from '@mui/icons-material/Favorite';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { Paper, Stack, Typography } from '@mui/material';
import UserBox from './UserBox';
import { Box } from '@mui/system';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Post from './Post'


const theme = createTheme();

function Userpanel({ user }) {
    const urlFollower = "/mymood/following/followUser/"+user+"/";
    var urlPostings = "/mymood/posting/posts?userName=";

    const [users, setUsers] = useState([]);
    const [posts, setPosts] = useState([]);

    var fetchURL = async () => {
        const responseFollower = await fetch(urlFollower);
        if(responseFollower.ok) {
            const jsonFollower = await responseFollower.json();
            setUsers(jsonFollower);
            for(var i = 0; i < users.length; i++) {
                urlPostings += users[i].username;
                console.log(urlPostings);
                const responsePostings = await fetch(urlPostings);
                if(responsePostings.ok) {
                    const jsonPostings = await responsePostings.json();
                    console.log(jsonPostings);
                    setPosts(posts => posts.concat(jsonPostings));
                }
                urlPostings = "/mymood/posting/posts?userName=";
            }
        }
    }
    useEffect(() => {
        fetchURL();
    }, []);

    return (
        <>
            <div>
                <Paper style={{ padding: "10px", width: "200px", textAlign: "center" }}>
                    <PersonIcon />
                    <br />
                    {user} follows:
                    <br />
                    <Stack spacing={0}>
                        {console.log(users)}
                        {users.map((user) => {
                            return (<User key={user.username} user={user} />);
                        })}
                    </Stack>
                </Paper>
            </div>
            <ThemeProvider theme={theme}>
                <Container component="main" maxWidth="sm">
                    <CssBaseline />
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            border: '1px solid grey',
                            borderRadius: 2
                        }}
                    >
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={1}>
                                <IconButton sx={{mt: 2, ml: 1}}>
                                    <EmojiEmotionsIcon/>
                                </IconButton>
                            </Grid>
                            <Grid item xs={12} sm={8.5}>
                                <TextField
                                    sx={{ m : 1}}
                                    fullWidth
                                    id="outlined-textarea"
                                    label="What is your mood today?"
                                    placeholder="Share your mood"
                                    multiline
                                />
                            </Grid>
                            <Grid item xs={12} sm={2}>
                                <Button
                                    sx={{ mt: 2, ml: 1 }}
                                    fullWidth
                                    variant="outlined"
                                    startIcon={<SendIcon />}
                                >
                                    Post
                                </Button>   
                            </Grid>
                            {console.log(posts)}
                            {posts.map((post) => {
                                return (
                                    <>
                                        <Grid item xs={12}>
                                            <Post key={post.id} userName={post.author.userName} date={post.date} content={post.content}></Post>
                                        </Grid>
                                    </>
                                );
                            })}
                            {/* <Grid item xs={12}>
                                <Post userName={'Mario'} date={'2022-01-23T14:04:47.503451'} content={'This is an example content, to see if line breaks work and to get a general idea of how the content looks inside the card'}></Post>
                            </Grid>
                            <Grid item xs={12}>
                                <Post userName={'Beni'} date={'2022-01-23T14:04:47.503451'} content={'This is an example content, to see if line breaks work and to get a general idea of how the content looks inside the card'}></Post>
                            </Grid>
                            <Grid item xs={12}>
                                <Post userName={'Felix'} date={'2022-01-23T14:04:47.503451'} content={'This is an example content, to see if line breaks work and to get a general idea of how the content looks inside the card'}></Post>
                            </Grid>
                            <Grid item xs={12}>
                                <Post userName={'Max'} date={'2022-01-23T14:04:47.503451'} content={'This is an example content, to see if line breaks work and to get a general idea of how the content looks inside the card'}></Post>
                            </Grid> */}
                        </Grid>
                    </Box>
                </Container>
            </ThemeProvider>
        </>
    );
}

export default Userpanel;