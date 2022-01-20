import Userpanel from './Userpanel'
import PrimarySearchAppBar from './navbar';
import Login from './pages/Login';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Register from './pages/Register';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact component={Login} />
          <Route path="/register" exact component={Register} />
          <Route path="/view">
            <PrimarySearchAppBar></PrimarySearchAppBar>
            <br></br>
            <Userpanel user={'Mario'} />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
