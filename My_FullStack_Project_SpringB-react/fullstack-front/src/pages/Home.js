import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useParams } from "react-router-dom";

export default function Home() {
  const [users, setUsers] = useState([]);
  const {id}=useParams();

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const result = await axios.get("http://localhost:8080/users");
      setUsers(result.data);
    } catch (error) {
      console.error("Error loading users:", error);
    }
  };
  const deleteUser=async (id)=>{
    await axios.delete(`http://localhost:8080/user/${id}`)  //id comming from useParams
    loadUsers();
};
const viewUser=async (id)=>{
    await axios.get(`http://localhost:8080/user/${id}`)

}

  return (
    <div className="container">
      <div className="py-4">
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Name</th>
              <th scope="col">Username</th>
              <th scope="col">Email</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user, index) => (
              <tr key={user.id}>
                <th scope="row">{index + 1}</th>
                <td>{user.name}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>
                  <Link className="btn btn-primary mx-2"


                //   onClick={()=>viewUser(user.id)}
                    to={`/viewuser/${user.id}`}
                   >View</Link>

                  <Link
                    className="btn btn-outline-info mx-2"
                    to={`/edituser/${user.id}`}
                  >
                    Edit
                  </Link>

                  <button 
                  className="btn btn-danger mx-2"
                  onClick={()=>deleteUser(user.id)}
                  
                  >
                    Delete
                    </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
