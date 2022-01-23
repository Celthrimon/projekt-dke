import Userpanel from './Userpanel'
import PrimarySearchAppBar from './navbar';
import Login from './pages/Login';
import Post from './Post'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Register from './pages/Register';
import * as React from 'react';

function App() {

  const post ={
    id: 3,
    content: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Modi enim distinctio, dolore eius voluptatibus excepturi neque? Ipsa quam excepturi vel!",
    emoji: "",
    mood: "",
    author: { userName: "Mario" },
    hashtags: [],
    likedBy: [],
    date: "2022-01-18T16:13:57.331928",
  }

  const [username, setUserName] = React.useState('Felix');

  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact>
            <Login changeUserName={username => setUserName(username)}></Login>
          </Route>
          <Route path="/register" exact>
            <Register></Register>
          </Route>
          <Route path="/view">
            <PrimarySearchAppBar></PrimarySearchAppBar>
            <br></br>
            <Userpanel user={username} />

            <Post
              userName={post.author.userName}
              content={post.content}
              date={post.date}
              liked={true}
              currentUser={username}
            />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
