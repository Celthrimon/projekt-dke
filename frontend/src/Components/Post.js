import * as React from "react";
import {
  Card,
  CardActionArea,
  CardHeader,
  CardContent,
  CardActions,
  Avatar,
  IconButton,
  Typography,
} from "@mui/material";
import { red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { useState } from "react";
import { useEffect } from "react";

export default function Post({ post, currentUser }) {
  
  const likeURL = "/mymood/posting/like/"
  const unlikeURL = "/mymood/posting/unlike/"
  const [isLiked, setIsLiked] = useState(post.likedBy.filter((e)=>e.userName==currentUser).length>0);

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

    <Card sx={{ maxWidth: 600 }}>
      <CardHeader
        avatar={
          <CardActionArea onClick={() => console.log("go to author profile")}>
            <Avatar sx={{ bgcolor: red[500] }} aria-label={post.author.userName}>
              {post.author.userName.split("")[0]}
            </Avatar>
          </CardActionArea>
        }
        action={
          <a style={{fontSize:"35px"}}>{post.mood}</a>
        }
        title={post.author.userName}
        subheader={`${months[d.getMonth()]} ${d.getDate()}, ${d.getFullYear()} · ${hours}:${minutes}`}
      />

      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {post.content}
        </Typography>
      </CardContent>
      
      <CardActions disableSpacing>
        <IconButton
          color={isLiked ? "error" : "default"}
          aria-label="like post"
          onClick={() => {
            var url = isLiked?unlikeURL:likeURL;
            fetch(url+post.id+"/?userName="+currentUser, {method: 'POST'});
            setIsLiked(!isLiked);
          }
          }
        >
          <FavoriteIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
}
