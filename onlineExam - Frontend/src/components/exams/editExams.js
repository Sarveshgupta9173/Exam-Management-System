import { Alert, Box, Button, Container, FormControlLabel, Radio, RadioGroup, TextField } from "@mui/material";
import { DateTimePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import React, { useEffect } from "react";
import {  useLocation, useNavigate } from "react-router-dom";
import NavBar from "../navbar";
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import axios from "axios";
import CheckIcon from '@mui/icons-material/Check';
import dayjs from "dayjs";
import { HOST_URL } from "../../utils/constants";
import { Label } from "@mui/icons-material";

const EditExam = ()=>{

    const location = useLocation();
    const navigate  = useNavigate();
    const [alert,setAlertVal] = React.useState("none");
    const [danger,setDanger] = React.useState("none");
    const [start_date,setStartDate] = React.useState(dayjs(location.state.start_time));
    const [end_date,setEndDate] = React.useState(dayjs(location.state.end_time));

    const initialFormData = {
      no_of_mcq_questions:location.state.no_of_mcq_questions,
      no_of_programming_questions:location.state.no_of_programming_questions,
      pass_marks: location.state.pass_marks,
      duration:location.state.duration,
      start_time: location.state.start_time,
      end_time:location.state.end_time
    };
    const [formData, updateFormData] = React.useState(initialFormData);

    useEffect(()=>{
      if(sessionStorage.getItem("role") !== "ADMIN"){
        navigate("/authorization-alert");
      }
    },[]);

    const handleChange = (event) => {
      updateFormData({
        ...formData,
        [event.target.name]: event.target.value
      });
    };

    const handleStartDate = (event) =>{
      setStartDate(event);
      formData.start_time = event.format("YYYY-MM-DD HH:mm");
      

      
    }

    const handleEndDate = (event) =>{
      setEndDate(event);
      formData.end_time = event.format("YYYY-MM-DD HH:mm");
      
    }

    const handleSubmit = (event)=>{
      

      if(formData.no_of_mcq_questions && formData.no_of_programming_questions 
        && formData.start_time  && formData.end_time && formData.duration){

          const exam_id = location.state.exam_id;
          const url = `${HOST_URL}/exam/update/`.concat(exam_id);
          axios.put(url,formData,{headers:{
                          'Content-Type': 'application/json',
                          'Access-Control-Allow-Origin': '*'
                  }})
                      .then((response)=>{
                      // console.log("Exam Updated Successfully..",response.status);

                        if(response.status === 200){
                          setAlertVal("block");
                          setTimeout(() => {
                            navigate("/exams");
                          },2000);
                        }else{
                          setDanger("block");
                          console.log("Error status code");
                          setTimeout(() => {
                            setDanger("none");
                          }, 3000);
                        }

                      })
                      .catch((error)=>{
                      console.log(error);
                      setDanger("block");
                          setTimeout(() => {
                            setDanger("none");
                          }, 3000);
                      });

                          
      }else{
        setDanger("block");
        console.log("Please Enter all the values");
        setTimeout(() => {
          setDanger("none");
        }, 3000);
      }

    }

    return(
    <>
        <NavBar ></NavBar>
        <div className="alert" style={{display:alert}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
             Exam updated successfully
        </Alert>
        </div> 
        <div className="alert" style={{display:danger}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
             Please Fill out all the Fields correctly
        </Alert>
        </div> 
        <h2>Edit Exam</h2>

        <Container maxWidth="md">
            <Box sx={{ bgcolor: '#f2f4f7', height: '780px',padding:5,borderRadius:5 }} >
              <div>
                <QuestionAnswerIcon fontSize="large" ></QuestionAnswerIcon>
                <TextField id="standard-basic" required label="Exam Name " sx={{width:'90%'}}
                name="exam_name"variant="outlined" type="text"onChange={handleChange}  defaultValue={location.state.exam_name} ></TextField>
              </div>
            
            <div className="inputs"  style={{margin:'15px 0px'}}  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Mcq" 
                name="no_of_mcq_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Mcq Questions" sx={{width:'40%'}}
                  disabled defaultValue={location.state.no_of_mcq_questions} ></TextField>
                 
                 <QuestionAnswerIcon fontSize="large" sx={{ml:5}}></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Programming" 
                name="no_of_programming_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Programming Questions" sx={{width:'40%'}} disabled 
                 defaultValue={location.state.no_of_programming_questions}></TextField>
            </div>
            
            <div className="inputs" style={{margin:'20px 0px'}}  >
                <QuestionAnswerIcon fontSize="large" ></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Passing Marks" 
                name="pass_marks"variant="outlined" type="number"onChange={handleChange} sx={{width:'40%'}}
                defaultValue={location.state.pass_marks}  ></TextField>

                <QuestionAnswerIcon fontSize="large" sx={{ml:5}}></QuestionAnswerIcon>
                <TextField id="standard-basic"  className="disabled" required label="Duration in Hours" 
                name="duration" variant="outlined" type="number"  onChange={handleChange} sx={{width:'40%'}}
                defaultValue={location.state.duration} ></TextField>
            </div>
           
            <div style={{margin:'20px 0px'}}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DateTimePicker']}>
                  <DateTimePicker label="Start Date Time" value={dayjs(location.state.start_time)}
                   disablePast onChange={handleStartDate} name="start_time"/>
                </DemoContainer>
              </LocalizationProvider>

              <LocalizationProvider dateAdapter={AdapterDayjs} >
                <DemoContainer components={['DateTimePicker']} sx={{mt:3}}>
                  <DateTimePicker  label="End Date Time" value={dayjs(location.state.end_time)}
                   disablePast minDate={start_date} onChange={handleEndDate} name="end_time"/>
                </DemoContainer>
              </LocalizationProvider>
            </div>

            <div style={{marginTop:20}}>
              <Label></Label>
            <TextField id="standard-basic" required label="Exam Instructions " sx={{width:'96%'}} defaultValue={location.state.exam_instruction}
                name="exam_instruction"variant="outlined" type="text"onChange={handleChange} multiline rows={10} ></TextField>
            </div>

            <div className="btn">
                <div>
                <Button variant="contained" fullWidth sx={{mt:2}} onClick={handleSubmit}>Update Exam</Button>
                </div>
            </div>
            </Box>
        </Container>
{/* 
        <Container maxWidth="sm">
            <Box sx={{ bgcolor: '#E0E0E0', height: '458px',padding:5,borderRadius:5 }} >
            
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Mcq" 
                name="no_of_mcq_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Mcq Questions" disabled defaultValue={location.state.no_of_mcq_questions} ></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Programming" 
                name="no_of_programming_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Programming Questions" disabled defaultValue={location.state.no_of_programming_questions}></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Passing Marks" 
                name="pass_marks"variant="outlined" type="number"onChange={handleChange} defaultValue={location.state.pass_marks} ></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic"  className="disabled" required label="Duration in Hours" 
                name="duration" variant="outlined" type="number" onChange={handleChange} defaultValue={location.state.duration} ></TextField>
            </div>
            <div>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DateTimePicker']}>
                  <DateTimePicker label="Start Date Time" disablePast onChange={handleStartDate} name="start_date"  value={dayjs(location.state.start_time)}/>
                </DemoContainer>
              </LocalizationProvider>
            </div>
            <div>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DateTimePicker']}>
                  <DateTimePicker label="End Date Time" disablePast minDate={start_date} onChange={handleEndDate} name="end_date" value={dayjs(location.state.end_time)} />
                </DemoContainer>
              </LocalizationProvider>
            </div> */}


            {/* <div className="btn">
                <div>
                <Button variant="contained" fullWidth sx={{mt:2}} onClick={handleSubmit}>Update Exam</Button>
                </div>
            </div>
            </Box>
        </Container> */}
    

    </>

    );
}

export default EditExam;