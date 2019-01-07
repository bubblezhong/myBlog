require('styles/common.css');

import React from 'react';
import {
  HashRouter as Router
} from 'react-router-dom'
// import Archive from './Archive.js';
// import Index from './Index.js';
// import Header from './common/Header';
import SideBar from './common/SideBar';
import Content from './common/Content';
// import Home from './Home.js';
// {/**/}
class AppComponent extends React.Component {
  componentWillUnmount() {
    // localStorage.clear();
  }
  render() {
    return (
    	<Router>
        <div className="index">
          <SideBar />
          <Content />
        </div>
      </Router>
    );
  }
}

AppComponent.defaultProps = {
};

export default AppComponent;
