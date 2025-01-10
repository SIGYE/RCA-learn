import React from 'react'
import { Link } from 'react-router-dom';

const Login = () => {
  return (
    <div className='min-h-screen bg-white flex items-center justify-center'>
        <div className='w-full max-w-md bg-white rounded-lg shadow-lg items-center justify-center px-5 py-10'>
            <h2 className='px-2 py-3 text-center font-semibold text-lg text-blue-600'>Login</h2>
            <div className='mb-4'>
              <form className='space-y-6 '>
                <input type="email" placeholder='Enter Email or Username' className='w-full border border-gray-400 rounded-lg px-5 py-3 text-gray-600' />
                <input type="password" id='password' placeholder='Enter Password' className='w-full border border-gray-600 rounded-lg px-5 py-3 text-gray-600' />
                <button className='px-5 py-3 rounded-lg bg-blue-600 top-5 hover:bg-blue-500 text-white items-center justify-center'>Login</button>
              </form>
            </div>
            <p className='text-sm text-center justify-center'>
          Don't have an account?{' '}
          <Link to="/signup" className='text-sm text-blue-600 hover:underline'>
            Sign Up
          </Link>
        </p>
        </div>
    </div>
  )
}

export default Login