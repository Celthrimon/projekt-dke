import Userpanel from './Userpanel'
import PrimarySearchAppBar from './navbar';
import Login from './pages/Login';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Register from './pages/Register';
import * as React from 'react';

function App() {
  
  const [username, getUserName] = React.useState('Mario');

  const sendDataToApp = (username) => {
    getUserName(username);
  }

  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact>
            <Login sendDataToApp={sendDataToApp}></Login>
          </Route>
          <Route path="/register" exact component={Register} />
          <Route path="/view">
            {console.log(username)}
            <PrimarySearchAppBar></PrimarySearchAppBar>
            <br></br>
            <Userpanel user={username} />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
