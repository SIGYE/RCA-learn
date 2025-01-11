import React from 'react'
import { Link } from 'react-router-dom'
import { FaEdit } from "react-icons/fa";
import { MdDelete } from "react-icons/md";

const Dashboard = () => {
    const employees = [
        {name: 'Linda Teta', email: 'lindateta@gmail.com', phone: +250791847917, role: 'Manager'},
        {name: 'Lisa Tesi', email: 'lisatesi@gmail.com', phone: +250791847917, role: 'Manager'},
        {name: 'Linda Teta', email: 'lindateta@gmail.com', phone: +250791847917, role: 'Manager'},
    ]
  return (
    <div className='min-h-screen bg-gray-100 p-5'> 
        <div className='flex items-center justify-center mb-5'>
            <h1 className='text-2xl font-bold text-center text-gray-600'>Employee Dashboard</h1>
            {/* <Link to="/addForm" className='text-white rounded-lg px-3 py-2 bg-blue-600'>Add Employee</Link> */}
        </div>
        <table className='w-full bg-white rounded-lg shadow-lg'>
            <thead className='bg-blue-500 text-white'>
                <tr>
                <th className='font-semibold text-left px-4 py-2'>Name</th>
                <th className='font-semibold text-left px-4 py-2'>Email</th>
                <th className='font-semibold text-left px-4 py-2'>PhoneNumber</th>
                <th className='font-semibold text-left px-4 py-2'>Role</th>
                </tr>
            </thead>
            <tbody>
                {employees.map((employee) =>(
                    <tr className='border-b'>
                        <td className='px-4 py-2'>{employee.name}</td>
                        <td className='px-4 py-2'>{employee.email}</td>
                        <td className='px-4 py-2'>{employee.phone}</td>
                        <td className='px-4 py-2'>{employee.role}</td>
                        <td><a href="#"><FaEdit/></a></td>
                        <td><a href="#"><MdDelete/></a></td>
                    </tr>
                ))}
            </tbody>

        </table>
    </div>
  )
}

export default Dashboard