import * as React from "react";
import {useEffect, useState} from "react";
import {
    Avatar,
    Card,
    CardActionArea,
    CardActions,
    CardContent,
    CardHeader,
    IconButton,
    Typography,
} from "@mui/material";
import {red} from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import Link from '@mui/material/Link';


export default function Post({post, currentUser}) {

    const [anchorEl, setAnchorEl] = React.useState(null);
    const [currHashtag, setHashtag] = React.useState('');
    const handleHashtagMenuOpen = (event) => {
        setHashtag(event.currentTarget.innerText);
        setAnchorEl(event.currentTarget);
    };
    const isMenuOpen = Boolean(anchorEl);

    const handleMenuClose = () => {
        setAnchorEl(null);
        const followUrl = "/mymood/following/followHashtag/" + currentUser + "?title=" + currHashtag.substring(1, currHashtag.length);
        fetch(followUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
    };

    const renderMenu = (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'right',
            }}
            id='hashtag-menu'
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            open={isMenuOpen}
            onClose={handleMenuClose}
        >
            <MenuItem onClick={handleMenuClose}>Follow</MenuItem>
        </Menu>
    );

    const likeURL = "/mymood/posting/like/"
    const unlikeURL = "/mymood/posting/unlike/"
    const [isLiked, setIsLiked] = useState(post.likedBy.filter((e) => e.userName == currentUser).length > 0);

    useEffect(() => {

    }, [isLiked])

    const months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
    ];
    const d = new Date(post.date.split("T")[0]);
    const hours = post.date.split("T")[1].split(":")[0];
    const minutes = post.date.split("T")[1].split(":")[1];

    return (

        <Card sx={{maxWidth: 600}}>
            <CardHeader
                avatar={
                    <CardActionArea onClick={() => console.log("go to author profile")}>
                        <Avatar sx={{bgcolor: red[500]}} aria-label={post.author.userName}>
                            {post.author.userName.split("")[0]}
                        </Avatar>
                    </CardActionArea>
                }
                action={
                    <a style={{fontSize: "35px"}}>{post.mood}</a>
                }
                title={post.author.userName}
                subheader={`${months[d.getMonth()]} ${d.getDate()}, ${d.getFullYear()} · ${hours}:${minutes}`}
            />

            <CardContent>
                <Typography variant="body2" color="text.secondary" style={{whiteSpace: "pre-wrap"}}>
                    {post.content.split(" ").map((word) => {
                        if (word.charAt(0) == "#")
                            return (<Link variant="body2" style={{color: 'blue'}}
                                          onClick={handleHashtagMenuOpen}>{word} </Link>)
                        else
                            return (<>{word} </>)
                    })}
                </Typography>
            </CardContent>

            <CardActions disableSpacing>
                <IconButton
                    color={isLiked ? "error" : "default"}
                    aria-label="like post"
                    onClick={() => {
                        var url = isLiked ? unlikeURL : likeURL;
                        fetch(url + post.id + "/?userName=" + currentUser, {method: 'POST'});
                        setIsLiked(!isLiked);
                    }
                    }
                >
                    <FavoriteIcon/>
                </IconButton>
            </CardActions>
            {renderMenu}
        </Card>
    );
}
