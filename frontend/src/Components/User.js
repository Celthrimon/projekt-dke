import { Paper } from '@mui/material';

function User({ user }) {
    console.log(user);
    return (
        <Paper style={{padding:"5px", marginTop:"10px"}}>
            {user.username}
        </Paper>
    );
}

export default User;
