import React, { useEffect } from 'react';
import './App.css';
import Home from './views/Home';
import City from './views/City';
import Login from './views/Login';
import Logout from './views/Logout';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

const App = () => {
  useEffect(() => {
    document.title = "Cities";
  }, []);

  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/city/:id" element={<City />} />
        <Route exact path="/login" element={<Login />} />
        <Route exact path="/logout" element={<Logout />} />
        <Route path="*" element={<Home />} />
      </Routes>
    </Router>
  );
};

export default App;