import React, { useEffect }  from "react";
import NavBar from "../navbar";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { DataGrid } from "@mui/x-data-grid";
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Typography } from "@mui/material";
import dayjs from "dayjs";
import CheckIcon from '@mui/icons-material/Check';
import { HOST_URL } from "../../utils/constants";

const StudentPersonalExamInfo = (props)=>{
    const navigate = useNavigate();
    const location = useLocation();
    const student_id = location.state.user_id;
    const [tableData,setStudentData] = React.useState([]);
    const [open, setOpen] = React.useState(false);
    const [curr_exam,setExam] = React.useState("");
    const [datatoForward,setDataToforward] = React.useState([]);
    const [alert,setAlertVal] = React.useState("none");
    const [errorMsg,setErrorMsg] = React.useState("");
    const [danger,setDanger] = React.useState("none");

    const objToPost = { student: location.state, }

    const columns = [
      { field: 'exam.exam_id', headerName: 'Exam ID',width:200,valueGetter:({row})=>row.exam.exam_id },
      { field: "status",headerName:'Status',width : 240, renderCell: (cellValues)=>{
          if(cellValues.row.status === "COMPLETED"){
              return (
                  <Button variant="contained" color="error" >
                    COMPLETED
                  </Button>
                );
          }else if (cellValues.row.status === "REMAINING"){
              return (
                  <Button variant="contained" color="primary"  onClick={(event) => {handleExamStart(event, cellValues);}} >
                    Start Exam
                  </Button>
                );
          }else{
              return (
                  <Button variant="contained" color="secondary" onClick={handleExamResume} >
                    ABORTED
                  </Button>
                ); 
          }
      }
  },
  { headerName:"View Result",renderCell:(cellValues)=>{
    if(cellValues.row.status === "COMPLETED" && cellValues.row.result_issued === true){
      return (
        <Button variant="contained" color="info" onClick={(event) => handleViewResult(event,cellValues)}>View Result</Button>
      );
    }else if(cellValues.row.status === "COMPLETED" && cellValues.row.result_issued === false){
      return "Result Not Issued"
    }else{
      return " ";
    }
  },width:200}
      
    ];


    useEffect(()=>{

      if(sessionStorage.getItem("role") !== "STUDENT"){
        navigate("/authorization-alert");
      }
       const get_url = `${HOST_URL}/student-exam/get-by-student-id/`.concat(student_id);

        axios.get(get_url)
        .then((response)=>{
            // console.log(response.data);
            setStudentData(response.data);
            if(response.data.length == 0){
              setAlertVal("block");
            }
        })
        .catch((error)=>{
            console.log(error);

        });

        

    },[]);

    

      const handleViewResult = (event,cellValues)=>{
        console.log(cellValues.row);
        navigate("/studentPersonalResult",{state:cellValues.row})
      }

      const handleExamStart = (event,cellValues)=>{
        setExam(cellValues.row.exam);
        // console.log(cellValues.row.exam);
        setDataToforward(cellValues.row);


          // getting all the questions data 
          // console.log(new Date(parseInt(sessionStorage.getItem("started_time"))*1000).getMinutes());
          const exam_id = cellValues.row.exam.exam_id;
          const getQuestionsUrl = `${HOST_URL}/question-exam/get-all-questions-by-exam-id/`.concat(exam_id);
          axios.get(getQuestionsUrl)
                  .then(async (response)=>{
                      // setAllQuestions(response.data);
                      // console.log(response.data);
                      localStorage.setItem("allQuestions",JSON.stringify(response.data));
                  })
                  .catch((error)=>{
                      console.log(error);
                     
                  });

      
          sessionStorage.setItem("started_time",Date.now());

          objToPost.start_time = dayjs().format("YYYY-MM-DD HH:mm");
          objToPost.exam = cellValues.row.exam;
          // console.log(objToPost);
          const postUrl = `${HOST_URL}/user-exam-login/insert-user-exam-login-details`;
          axios.post(postUrl,objToPost)
                .then((response)=>{
                  console.log(response.data);
                  setOpen(true);
                })
                .catch((error)=>{

                  console.log(error);
                  

                  const updateExamStatusObj = {
                    student:location.state,
                    exam:cellValues.row.exam,
                    status:"COMPLETED",
                    // result:"FAIL"
                  };

                  console.log(updateExamStatusObj);
                  const updateExamStatusUrl = `${HOST_URL}/student-exam/update-student-exam-details`;
                  axios.put(updateExamStatusUrl, updateExamStatusObj)
                    .then((response) => {
                      // console.log(response.data);

                      setDanger("block");
                      setTimeout(() => {
                        setDanger("none");
                      window.location.reload();

                    }, 3000);
                    })
                    .catch((error) => {
                      console.log(error);
                    });

                });

       
        

      }

      const handleClose = () => {
        setOpen(false);

        

        navigate('/start-exam',{state:datatoForward})
      };
    
    
        const handleExamResume = (event,cellValues)=>{
            
        }
   


    return (
        <>
        <NavBar></NavBar>
        <div className="alert" style={{display:alert}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="info">
             No Exams Assigned for you.....
        </Alert>
        </div>
        <div className="alert" style={{display:danger}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
             Exam Already submitted due to Malpractice.
        </Alert>
        </div>
        <div style={{ width: '60%' }}>
            <DataGrid sx={{ml:'30%',mt:5}} rows={tableData} columns={columns} getRowId={(row) => row.id} ></DataGrid>
        </div>

        <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Please Read the instructions carefully before starting the exam."}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            
            <Typography>There will be 2 Sections in the Exam .</Typography>
            <Typography> Section - 1. Mcq - There will be {curr_exam.no_of_mcq_questions } Mcq's (Logical,Technical,Pseudocode).</Typography>
           <Typography>Section - 2. Programming - There will be {curr_exam.no_of_programming_questions} Programs.</Typography>
           <Typography>
            -- Programs : <Button color="secondary" variant="outlined" sx={{ml:4,mb:2}}>Programs</Button> 
            </Typography>
            <Typography>
            -- Mcq's : <Button color="success" variant="outlined" sx={{ml:4}}>Mcq</Button> 
            </Typography>
            <Typography> Candidate can  take the test from the safe and secure environment of his/her home, with a 
            desktop/laptop/smartphone with a webcam and an internet connection un-interrupted 
            internet speed is desirable.</Typography>
            <Typography> Candidates are requested to take the test honestly, ethically, and should follow all the 
            instructions.</Typography>
           
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} autoFocus>
            Agree
          </Button>
        </DialogActions>
      </Dialog>

        </>
    );
}

export default StudentPersonalExamInfo;