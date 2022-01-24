import * as React from "react";
import {
    Card,
    CardContent,
    CardActions,
    Button,
    Select,
    MenuItem,
    TextField,
    Typography,
} from "@mui/material";
import { red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { useState, useEffect } from "react";

export default function NewPost({ currentUser }) {

    const [post, setPost] = useState(
    {
        content:"",
        mood: "😀",
        author: {
            userName: currentUser
        },
        date: new Date().toISOString()
    });

    console.log(post)
    const createURL = "/mymood/posting/post/"

    return (
        <div>
            <TextField
                sx={{ width: "70%" }}
                id="outlined-basic"
                label="New Post"
                variant="outlined"
                onChange={(e) => { setPost({ ...post, content: e.target.value }); }}
            />
            <Select
                sx={{ width: "15%" }}
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={post.mood}
                label="Age"
                onChange={(e) => { setPost({ ...post, mood: e.target.value }); }}
            >
                <MenuItem value="😀">😀</MenuItem>
                <MenuItem value="😅">😅</MenuItem>
            </Select>
            <Button 
                sx={{ width: "15%", height: "56px", marginTop:"-2px" }} 
                variant="outlined"
                onClick={ ()=>{
                    fetch(createURL, {
                        method: 'POST',
                        headers: {
                          'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(post)
                      });
                }}
            >
                Post
            </Button>
        </div>
    );
}
