import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import Login from './components/Login';
import SignUp from './components/SignUp';
import AddForm from './components/AddForm';
import Dashboard from './components/Dashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path='/dashboard' element={<Dashboard/>}/>
        <Route path='/addForm' element={<AddForm/>}></Route>
      </Routes>
    </Router>
 
  );
}

export default App;
