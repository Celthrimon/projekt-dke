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

export default function Post({ userName, date, content, liked = false, currentUser }) {
  const [isLiked, setIsLiked] = useState(liked);

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
  const d = new Date(date.split("T")[0]);
  const hours = date.split("T")[1].split(":")[0];
  const minutes = date.split("T")[1].split(":")[1];

  return (
    <Card sx={{ m : 2}}>
      <CardHeader
        avatar={
          <CardActionArea onClick={() => console.log("go to author profile")}>
            <Avatar sx={{ bgcolor: red[500] }} aria-label={userName}>
              {userName.split("")[0]}
            </Avatar>
          </CardActionArea>
        }
        title={userName}
        subheader={`${months[d.getMonth()]} ${d.getDate()}, ${d.getFullYear()} · ${hours}:${minutes}`}
      />

      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {content}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton
          color={isLiked ? "error" : "default"}
          aria-label="like post"
          onClick={() => setIsLiked(!isLiked)}
        >
          <FavoriteIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
}
