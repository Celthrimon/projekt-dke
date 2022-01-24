

import Login from './pages/Login';
import Post from './Post'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Register from './pages/Register';
import Feed from './pages/Feed';
import * as React from 'react';

function App() {


  const [username, setUserName] = React.useState('Mario');

  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact>
            <Login changeUserName={username => setUserName(username)}></Login>
          </Route>
          <Route path="/register" exact>
            <Register changeUserName={username => setUserName(username)}></Register>
          </Route>
          <Route path="/view">
            <Feed user={username}/>
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
