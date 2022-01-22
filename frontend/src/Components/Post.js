import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';


export default function Post({ userName, date, content }) {
  const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
  const d = new Date(date.split("T")[0]);
  const hours = date.split("T")[1].split(":")[0];
  const minutes = date.split("T")[1].split(":")[1];

  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label={userName}>
            {userName.split("")[0]}
          </Avatar>
        }
        title={userName}
        subheader={
          d.getDate() + " " + months[d.getMonth()] + " " + d.getFullYear() + " · " + hours + ":" + minutes
        }
      />
      <CardContent>
        <Typography variant="body2" color="text.secondary">
            {content}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <IconButton aria-label="like post">
          <FavoriteIcon />
        </IconButton>
      </CardActions>
    </Card>
  );
}