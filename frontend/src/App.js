import React, { Component } from 'react';
import './App.css';
import MainRouter from './components/Main';

import './source/css/bootstrap.css';
import './source/css/bootstrap-grid.css';
import './source/css/bootstrap-reboot.min.css';
import './source/css/main.css';
import './source/css/animate.css';

class App extends Component {
  render() {
    return (
        <MainRouter/>
    );
  }
}

export default App;
