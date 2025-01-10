import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import Login from './components/Login';
import SignUp from './components/SignUp';
import AddForm from './components/AddForm';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </Router>
    // <>
    // <AddForm/>
    // </>
  );
}

export default App;
