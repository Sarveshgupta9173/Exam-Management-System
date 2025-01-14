import React, { useEffect } from "react";
import NavBar from "../navbar";
import {  useNavigate } from "react-router-dom";
import axios from "axios";
import { DataGrid } from "@mui/x-data-grid";
import { Button } from "@mui/material";
import  { HOST_URL } from "../../utils/constants";


const StudentExamDetails = ()=>{

    const [tableData, setTableData] = React.useState([]);
    const navigate = useNavigate();
    
    const columns = [
        { field: 'id', headerName: 'Entity ID',width:100 },
        { field: 'student.first_name', headerName: 'Student Name',width:130,valueGetter:({row})=>row.student.first_name },
        { field: 'exam.exam_id', headerName: 'Exam ID',width:130,valueGetter:({row})=>row.exam.exam_id },
        { field: "status",headerName:'Status',width : 170},
        { field: "Detail result",headerName:'Detailed Result ',width : 230 , renderCell:(cellValues)=>{
            if(cellValues.row.status === "COMPLETED"){
                return (
                    <Button variant="contained" color="primary" onClick={(event)=>{handleViewSummary(event,cellValues)}}>View Summary</Button>
                );
            }
        }
          }, 
          { field: "result",headerName:'Result ',width : 130
          },
          { field: "result_issued",headerName:'Issued Result ',width : 130
          },

      ];

    useEffect(()=>{
        if(sessionStorage.getItem("role") !== "ADMIN"){
            navigate("/authorization-alert");
          }

        const getUrl = `${HOST_URL}/student-exam/get-all`;
        axios.get(getUrl)
                .then((response)=>{
                    // console.log(response.data);
                    setTableData(response.data);

                })
                .catch((error)=>{
                    console.log(error);
                });

    },[]);


    const handleViewSummary = (event,cellValues)=>{
        // console.log(cellValues.row);
        const studentExamData = cellValues.row;
        
        navigate("/student-exam-summary",{state:studentExamData})
    }


    return(
    <>
    <NavBar></NavBar>
        <div style={{ width: '75%' }}>
            <DataGrid sx={{ml:20,mt:5}} rows={tableData} columns={columns} getRowId={(row) => row.id} ></DataGrid>
        </div>
    </>);
}

export default StudentExamDetails;