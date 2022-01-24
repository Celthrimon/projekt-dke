import User from './User'
import {useState, useEffect} from 'react';
import PersonIcon from '@mui/icons-material/Person';
import { Paper, Stack } from '@mui/material';

function Userpanel({ user, isMyself=false }) {
    const url = "/mymood/following/followUser/"+user+"/";
    const [users, setUsers] = useState([]);

    var fetchURL = async () => {
        const response = await fetch(url);
        const json = await response.json();
        console.log(json);
        setUsers(json);
    }
    useEffect(() => {
        fetchURL();
    }, []);

    return (
        <Paper style={{padding:"10px", width:"200px", textAlign:"center"}}>
            <PersonIcon />
            <br />
            {isMyself?"You":user} follow{isMyself?"":"s"}:
            <br />
            <Stack spacing={0}>
                
                {users.map((user)=>{
                    return(<User key={user.username} user={user}/>)
                })}
            </Stack>
        </Paper>
    );
}

export default Userpanel;