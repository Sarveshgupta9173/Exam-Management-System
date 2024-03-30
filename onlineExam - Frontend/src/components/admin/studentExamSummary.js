import React, { useEffect, useRef } from "react";
import NavBar from "../navbar";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import CheckIcon from '@mui/icons-material/Check';
import { DataGrid } from "@mui/x-data-grid";
import { Alert, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from "@mui/material";
import { HOST_URL } from "../../utils/constants";

const StudentExamSummary = ()=>{
    // const navigate = useNavigate();
    const location = useLocation();
    const [open, setOpen] = React.useState(false);
    const [danger,setDanger] = React.useState("none");
    const [codeQuestion,setCodeQuestion] = React.useState("");
    const [codeAnswer,setCodeAnswer] = React.useState("");
    const [programMarks,setProgramMarks] = React.useState("");
    const [summaryData,setSummaryData] = React.useState([]);
    const [resultData,setResultData] = React.useState([]);
    const [isReviewed,setIsReviewed] = React.useState(false);
    const [loginData,setLoginData] = React.useState("");
  

    const objToPost = {
        student:location.state.student,
        exam:location.state.exam,
        question:codeQuestion,
        answer:""
    }

    const updateStatusObj = {
      student:location.state.student,
      exam:location.state.exam,
      status:'COMPLETED'
    };

   

    const student_id = location.state.student.user_id;
    const exam_id = location.state.exam.exam_id;
    const columns = [
        { field: 'question.question_type', headerName: 'Question Type',width:140,valueGetter:({row})=>row.question.question_type },
        { field: 'question.question', headerName: 'Question ',width:400,valueGetter:({row})=>row.question.question },
        { field: 'answer', headerName: 'Given Answer',width:200 ,renderCell:(cellValues)=>{
            if(cellValues.row.question.question_type == "PROGRAMMING"){
                return (<Button variant="contained" color="primary" onClick={(event)=>{handleCodeView(event,cellValues)}} >Review Code</Button>)
            }else{
                return cellValues.row.answer;
            }
        }},
        { field: 'question.answer', headerName: 'Correct Answer',width:200,valueGetter:({row})=>row.question.answer  },
        { field: "marks_obtained",headerName:'Marks Obtained',width : 160},
        { field: "is_reviewed",headerName:'Is Reviewed',width : 130},
      ]; 
    // console.log(codeQuestion);

    useEffect(()=>{
        
        const getAllDataUrl = `${HOST_URL}/user-exam-submission/get-by-student-id-exam-id/`.concat(student_id)+`/`.concat(exam_id);

        axios.get(getAllDataUrl)
            .then((response)=>{
                // console.log(response.data);
                setSummaryData(response.data);
                
            })
            .catch((error)=>{
                console.log(error);
            });


        const getResultDataUrl = `${HOST_URL}/user-exam-submission/get-result-section-wise/`.concat(student_id)+`/`.concat(exam_id);
        axios.get(getResultDataUrl)
              .then((response)=>{
                console.log(response.data);
                setResultData(response.data);
              })
              .catch((error)=>{
                console.log(error);
              });

        const getLoginDataUrl = `${HOST_URL}/user-exam-login/get-user-exam-login-details/`.concat(student_id)+`/`.concat(exam_id);
        axios.get(getLoginDataUrl)
              .then((response)=>{
                console.log(response.data);
                setLoginData(response.data);
              })
              .catch((error)=>{
                console.log(error);
              })
    },[]);
  

    const handleCodeView = (event,cellValues)=>{
        // console.log(cellValues.row);
        setCodeQuestion(cellValues.row.question);
        setCodeAnswer(cellValues.row.answer);
        setProgramMarks(cellValues.row.marks_obtained);
        setIsReviewed(cellValues.row.is_reviewed);
        setOpen(true);



    }

  const handleClose = () => {

    const program_marks = Number(document.getElementById("marks").value);
    if(program_marks > 10 || program_marks < 0){
        setDanger("block");
        setTimeout(() => {
            setDanger("none");
        }, 3000);

    }else{
        // console.log(program_marks);
        objToPost.marks_obtained = program_marks;
        objToPost.is_reviewed = true;
        console.log(objToPost);
        const updateProgramMarksUrl = `${HOST_URL}/user-exam-submission/insert-or-update/`;
        axios.post(updateProgramMarksUrl,objToPost,{headers:{
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
         }})
            .then((response)=>{

             // updating the status after exam completion
                
              const updateExamStatusUrl = `${HOST_URL}/student-exam/update-student-exam-details`;
              axios.put(updateExamStatusUrl,updateStatusObj)
                    .then((response)=>{
                      console.log(response.data);
                      console.log("status updated successfully");
                    })
                    .catch((error)=>{
                    });
        
              setOpen(false);
              window.location.reload();
              
            })
            .catch((error)=>{
                console.log(error);
                setOpen(false);
            });
    }
  };
  // console.log(summaryData);

    return (
        <>
        <NavBar></NavBar>
        
        <div style={{backgroundColor:'#f5f3f0',height:260,width:'88%',borderRadius:10,marginTop:30,marginLeft:75}}>
          
          <p style={{fontWeight:600,fontSize:21}}>Student Name : {location.state.student.first_name}</p>
          
          <p style={{fontWeight:600,fontSize:21}}>Total Marks Obtained :
            {(resultData.mcq_marks+resultData.program_marks)?resultData.mcq_marks+resultData.program_marks:0}
            <span style={{fontWeight:600,fontSize:21,padding:'0px 40px'}}>Status : 
            {(location.state.total_marks_scored >= location.state.exam.pass_marks)?' Passed ':' Failed '}
            </span>
          </p>
         
          <p style={{fontWeight:600,fontSize:21}}>Mcq attended:
             { (resultData.mcq_count)?resultData.mcq_count:0}/{location.state.exam.no_of_mcq_questions}

             <span style={{fontWeight:600,fontSize:21,padding:'0px 49px'}}>Mcq marks  : 
            {(resultData.mcq_marks)?resultData.mcq_marks:0}/{location.state.exam.no_of_mcq_questions}
            </span>
          </p>
          
          <p style={{fontWeight:600,fontSize:21}}>Program attended :
             {(resultData.program_count)?resultData.program_count: 0}/{location.state.exam.no_of_programming_questions}
             <span style={{fontWeight:600,fontSize:21,padding:'0px 20px'}}>Program marks : 
            {(resultData.program_marks)?resultData.program_marks:0}/{location.state.exam.no_of_programming_questions*10}
            </span>
          </p>

          <p style={{fontWeight:600,fontSize:21}}>Start Time :
             {(loginData.start_time)?loginData.start_time: ''}
             <span style={{fontWeight:600,fontSize:21,padding:'0px 20px'}}>End Time : 
            {(loginData.end_time)?loginData.end_time:0}
            </span>
          </p>
          
          
        </div>


        <div style={{ width: '95%' }}>
            <DataGrid sx={{ml:10,mt:5,fontSize:18}} rows={summaryData} columns={columns} getRowId={(row) => row.user_exam_submission_id} ></DataGrid>
        </div>

      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <div className="alert" style={{display:danger}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
              Enter marks between 0 - 10
        </Alert>
        </div> 
        <DialogTitle id="alert-dialog-title">
          {codeQuestion.question}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            <TextField fullWidth value={codeAnswer} inputProps={{ readOnly: true, }}
             multiline></TextField>
            <TextField autoFocus required margin="dense" id="marks" defaultValue={programMarks} sx={{mt:10}}
             name="marks"  label="Enter Marks (0-10)" type="number" fullWidth variant="standard"
             disabled={isReviewed}
              />
          </DialogContentText>
        </DialogContent>
        <DialogActions sx={{justifyContent:'center',marginBottom:'15%'}}>
          <Button onClick={()=>{setOpen(false)}} autoFocus variant="contained" color="error">
            Close
          </Button>
          <Button onClick={handleClose}
           disabled={isReviewed}
            autoFocus variant="contained">
            Submit Marks
          </Button>
        </DialogActions>
      </Dialog>

        </>
    );
};

export default StudentExamSummary;