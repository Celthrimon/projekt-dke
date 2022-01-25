import * as React from "react";
import {useState} from "react";
import {Button, MenuItem, Select, TextField,} from "@mui/material";

export default function NewPost({currentUser}) {

    const [post, setPost] = useState(
        {
            content: "",
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
                sx={{width: "70%"}}
                id="outlined-basic"
                multiline
                value={post.content}
                label="New Post"
                variant="outlined"
                onChange={(e) => {
                    setPost({...post, content: e.target.value});
                }}
            />
            <Select
                sx={{width: "15%"}}
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={post.mood}
                label="Age"
                onChange={(e) => {
                    setPost({...post, mood: e.target.value});
                }}
            >
                {["😀", "😁", "😅", "🥰", "🤐", "😬", "🤮", "🤬", "💩", "😤", "🤒"].map((emoji) => {
                    return (<MenuItem value={emoji}>{emoji}</MenuItem>)
                })}
            </Select>
            <Button
                sx={{width: "15%", height: "56px", marginTop: "-2px"}}
                variant="outlined"
                onClick={() => {
                    console.log(post.content);
                    if (post.content.includes('#')) {
                        var words = [];
                        var hashtags = '[\n';
                        words = post.content.split(" ");
                        for (var i = 0; i < words.length; i++) {
                            if (words[i].startsWith('#')) {
                                var to_append = words[i];
                                hashtags += '{\"title\": \"' + to_append + '\"},\n';
                            }
                        }
                        var hashtagJson = hashtags.substring(0, hashtags.length-2)
                        hashtagJson += '\n]';
                        console.log(hashtagJson);
                        const hashtagUrl = "mymood/following/createHashtagNodes";
                        fetch(hashtagUrl, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: hashtagJson
                        })
                    }

                    fetch(createURL, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(post)
                    });

                    setPost({
                        content: "",
                        mood: "😀",
                        author: {
                            userName: currentUser
                        },
                        date: new Date().toISOString()
                    });
                }}
            >
                Post
            </Button>
        </div>
    );
}
