import React from 'react'
import { Link  } from 'react-router-dom'

const SignUp = () => {
  return (
    <div className='min-h-screen flex items-center justify-center bg-white'>
        <div className='w-full max-w-md bg-white rounded-lg shadow-lg items-center justify-center px-5 py-10'>
            <h2 className='text-center font-semibold text-blue-600'>Create Your Account</h2>
            <div className='mb-4'>
                <form className='space-y-6 items-center'>
                    <input type="text" placeholder='Full Names' className='w-full border border-gray-400 rounded-lg text-gray-600 px-5 py-3' />
                    <input type="email" id="email" placeholder='Enter Email' className='w-full border border-gray-400 rounded-lg text-gray-600 px-5 py-3'/>
                    <input type="password" id="password" placeholder='Enter Password' className='w-full border border-gray-400 rounded-lg text-gray-600 px-5 py-3'/>
                    <input type="password" id="password" placeholder='Confirm Password' className='w-full border border-gray-400 rounded-lg text-gray-600 px-5 py-3'/>
                    <div className='flex justify-center'>
                    <button className='px-3 py-2 bg-blue-600 font-semibold text-white rounded-lg top-5'>Sign up</button>
                    </div>
                </form>
            </div>
            <p className='text-sm text-center justify-center'>
          Already have an account?{' '}
          <Link to="/" className='text-sm text-blue-600 hover:underline'>
            Login
          </Link>
        </p>
        </div>
    </div>
  )
}

export default SignUp