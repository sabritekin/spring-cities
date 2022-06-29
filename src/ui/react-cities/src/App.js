import React from 'react';
import './App.css';
import Home from './views/Home';
import Login from './views/Login';
import Logout from './views/Logout';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route exact path="/login" element={<Login />} />
        <Route exact path="/logout" element={<Logout />} />
      </Routes>
    </Router>
  )
}

export default App;