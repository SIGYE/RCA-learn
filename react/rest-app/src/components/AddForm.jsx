import React from 'react'

const AddForm = () => {
  return (
    <div className='min-h-screen bg-white flex items-center justify-center'>
        <div className='w-full max-w-md bg-white rounded-lg shadow-lg px-5 py-10 items-center'>
            <h2 className='text-blue-600 font-semibold text-center'>Add Employee</h2>
            <div className='mb-4'>
                <form className='space-y-6 items-center'>
                    <input type="text" placeholder='Enter Employee Name' className='w-full border border-gray-400 text-gray-600 rounded-lg px-5 py-3' />
                    <input type="email" id='email' placeholder='Enter Email' className='w-full border border-gray-400 text-gray-600 rounded-lg px-5 py-3' />
                    <input type="number" placeholder='Enter Phonenumber' className='w-full border border-gray-400 text-gray-600 rounded-lg px-5 py-3' />
                    <select name="role" className='w-full border border-gray-400 text-gray-600 rounded-lg px-5 py-3'>
                        <option value="" disabled>Select Employee Role</option>
                        <option value="CEO">Manager</option>
                        <option value="Designer">Designer</option>
                        <option value="Developer">Developer</option>
                        <option value="Marketing Department Manager">Marketing Department Manager</option>
                        <option value="CTO">CTO</option>
                    </select>
                    <div className='flex justify-center'></div>
                    <button className='px-3 py-2 text-white rounded-lg bg-blue-600'>Add Employee</button>
                </form>
            </div>
        </div>
    </div>
  )
}

export default AddForm