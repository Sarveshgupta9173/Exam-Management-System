import { Alert, Box, Button, Container, FormControlLabel, FormLabel, Radio, RadioGroup,  TextField } from "@mui/material";
import React, { useEffect } from "react";
import {  useLocation, useNavigate } from "react-router-dom";
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import axios from "axios";
import CheckIcon from '@mui/icons-material/Check';
import NavBar from "../navbar";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { DateRangePicker } from '@mui/x-date-pickers-pro/DateRangePicker';
import { Dayjs } from "dayjs";
import { HOST_URL } from "../../utils/constants";
import { Label } from "@mui/icons-material";



const CreateExams = ()=>{

    const location = useLocation();
    const navigate  = useNavigate();
    const mcq_count  = location.state.mcq_count;
    const program_count = location.state.program_count;
    const [alert,setAlertVal] = React.useState("none");
    const [danger,setDanger] = React.useState("none");
    const [countError,setCountError] = React.useState("none");
    const [errorMessage,setErrorMessage] = React.useState("");
    const [errorMsg,setErrorMsg] = React.useState("");
    const [start_date,setStartDate] = React.useState(Dayjs);
    const [end_date,setEndDate] = React.useState(Dayjs);
    let allAssignedQuestions = [];
    const initialFormData = {
      exam_name:"",
      no_of_mcq_questions:"",
      no_of_programming_questions:"",
      pass_marks: "",
      difficulty_level:"",
      duration:"",
      start_time: "",
      end_time:"",
      exam_instruction:""
    };
    const [formData, updateFormData] = React.useState(initialFormData);

    const handleChange = (event) => {
      if(formData.no_of_mcq_questions < mcq_count || formData.no_of_mcq_questions < 0){
          if(formData.no_of_programming_questions < program_count || formData.no_of_programming_questions<0){
              
          }else{
            event.preventDefault();
            setCountError("block");
            setErrorMessage(`Error!You have only ${program_count/3} Programming Questions  . `);
            setTimeout(() => {
              setCountError("none");
            }, 3000);
          }
      }else{
        event.preventDefault();
        setCountError("block");
        setErrorMessage(`Error! You have only ${mcq_count} Mcq Questions. `);
        setTimeout(() => {
          setCountError("none");
        }, 3000);
      }

      updateFormData({
        ...formData,
        [event.target.name]: event.target.value
      });
    };

    const handleRadio = (event)=>{
      updateFormData({
        ...formData,
        [event.target.name]: event.target.value
      });
    }

    const handleStartDate = (event) =>{
      setStartDate(event);
      formData.start_time = event.format("YYYY-MM-DD HH:mm");
      
    }

    const handleEndDate = (event) =>{
      setEndDate(event);
      formData.end_time = event.format("YYYY-MM-DD HH:mm");

    }

    useEffect(()=>{
      if(sessionStorage.getItem("role") !== "ADMIN"){
        navigate("/authorization-alert");
      }

    },[]);



    const handleSubmit = (event)=>{
          
        console.log(formData);

        if(formData.no_of_mcq_questions !== "" && formData.no_of_mcq_questions > 0 && formData.no_of_mcq_questions < mcq_count){

            if(formData.no_of_programming_questions !== "" && formData.no_of_programming_questions >= 0 && formData.no_of_programming_questions < program_count/3){

                if(formData.duration !== "" && formData.duration > 0 && formData.duration < 5){

                    if(formData.start_time !== "" && formData.end_time !== ""){

                      const url = `${HOST_URL}/exam/create-exam`;

                      axios.post(url,formData) 
                      .then((response)=>{

                        const exam = response.data;
                          
                        // assigning the questions automatically and inserting data intpo questionexams table
                        const mcqlimit = formData.no_of_mcq_questions;
                        const programLimit = formData.no_of_programming_questions;
                        const exam_level = formData.difficulty_level;
    
                         // getting the mcq questions assigned automatically
                         const getMcqUrl = `${HOST_URL}/question/get-mcq-questions-on-type-and-level/`.concat(mcqlimit+`/`+exam_level);
                         axios.get(getMcqUrl)
                               .then((response)=>{
    
                                 const mcq_data = response.data.map((element,index) => {
                                 return {exam:exam,question:element};
                                });
    
                                allAssignedQuestions = [...allAssignedQuestions , ...mcq_data];
                                console.log(allAssignedQuestions);
                               })
                               .catch((error)=>{
                                 console.log(error);
                               });
    
                        // getting the programming questions assigned automatically
    
                      
                        const getProgramUrl = `${HOST_URL}/question/get-program-questions-on-type-and-level/`.concat(programLimit+`/`+exam_level);
                        axios.get(getProgramUrl)
                              .then((response)=>{
                                
                                const program_data = response.data.map((element,index)=>{
                                  return {exam:exam,question:element};
                                })
                                allAssignedQuestions = [...allAssignedQuestions,...program_data];
                                console.log(allAssignedQuestions);
                              })
                              .catch((error)=>{
                                console.log(error);
                              });    
    
                        
                                    setTimeout(() => {
                                       // posting all the question exam into exam-questions table
                                    const postUrl = `${HOST_URL}/question-exam/save-all`;
                                    axios.post(postUrl,allAssignedQuestions)
                                          .then((response)=>{
                                            console.log(response.data);
                                            console.log("inserted successfuullyyyy");
                                                  
                                              setAlertVal("block");
                                              setTimeout(()=>{ navigate("/exams") },1000)
                                                  
                                          })
                                          .catch((error)=>{
                                            console.log(error);
                                            setErrorMsg("Error creating exam.")
                                            setDanger("block");
                                            setTimeout(() => {
                                              setDanger("none");
                                            }, 3000);
                                          });
                                    }, 1000);
                                    
    
                        
                        })
                        .catch((error)=>{
                          console.log(error);
                        });                 
                      

                    }else{
                      setErrorMsg("Enter Start Date or End Date");
                      setDanger("block");
                      setTimeout(() => {setDanger("none"); }, 3000);
                    }
                }else{
                  setErrorMsg("Duration between 1 - 5");
                    setDanger("block");
                    setTimeout(() => {setDanger("none"); }, 3000);
                }

            }else{
              setErrorMsg("Please Enter Programming Question correctly");
              setDanger("block");
              setTimeout(() => {setDanger("none"); }, 3000);
            }

        }else{
          setErrorMsg("Please Enter Mcq Questions correctly ");
          setDanger("block");
          setTimeout(() => {setDanger("none"); }, 3000);
        }
       
    }
  

    return (
        <>
        <NavBar ></NavBar>

        <div className="alert" style={{display:alert,position:'fixed'}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
             Exam created successfully.
        </Alert>
        </div> 
        <div className="alert" style={{display:danger,position:'fixed'}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
              {errorMsg}
        </Alert>
        </div> 
        <div className="alert" style={{display:countError,position:'fixed'}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
              {errorMessage}
        </Alert>
        </div> 
        
        <h2>Create New Exam</h2>

        <Container maxWidth="md">
            <Box sx={{ bgcolor: '#f2f4f7', height: '780px',padding:5,borderRadius:5 }} >
              <div>
                <QuestionAnswerIcon fontSize="large" ></QuestionAnswerIcon>
                <TextField id="standard-basic" required label="Exam Name " sx={{width:'90%'}}
                name="exam_name"variant="outlined" type="text"onChange={handleChange}  ></TextField>
              </div>
            <div style={{margin:'15px 0px'}}>
              <RadioGroup
                row
                aria-labelledby="demo-row-radio-buttons-group-label"
                name="difficulty_level"
                defaultValue={"MEDIUM"}
                // sx={{ml:15}}
                onChange={handleRadio} 
              >
                <p style={{fontSize:20,margin:'5px 20px',}}>Exam Level : </p>
                <FormControlLabel value="EASY" control={<Radio />} label="Easy" />
                <FormControlLabel value="MEDIUM" control={<Radio />} label="Medium" />
                <FormControlLabel value="HARD"  control={<Radio />} label="Hard" />
              </RadioGroup>
            </div>
            <div className="inputs"  style={{margin:'15px 0px'}}  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Mcq" 
                name="no_of_mcq_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Mcq Questions" sx={{width:'40%'}} ></TextField>
                 
                 <QuestionAnswerIcon fontSize="large" sx={{ml:5}}></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Programming" 
                name="no_of_programming_questions"variant="outlined" type="number" onChange={handleChange}
                 placeholder="No. of Programming Questions" sx={{width:'40%'}}  ></TextField>
            </div>
            
            <div className="inputs" style={{margin:'20px 0px'}}  >
                <QuestionAnswerIcon fontSize="large" ></QuestionAnswerIcon>
                <TextField id="standard-basic" className="disabled" required label="Passing Marks" 
                name="pass_marks"variant="outlined" type="number"onChange={handleChange} sx={{width:'40%'}}  ></TextField>

                <QuestionAnswerIcon fontSize="large" sx={{ml:5}}></QuestionAnswerIcon>
                <TextField id="standard-basic"  className="disabled" required label="Duration in Hours" 
                name="duration" variant="outlined" type="number"  onChange={handleChange} sx={{width:'40%'}} ></TextField>
            </div>
           
            <div style={{margin:'20px 0px'}}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DateTimePicker']}>
                  <DateTimePicker label="Start Date Time" disablePast onChange={handleStartDate} name="start_time"/>
                </DemoContainer>
              </LocalizationProvider>

              <LocalizationProvider dateAdapter={AdapterDayjs} >
                <DemoContainer components={['DateTimePicker']} sx={{mt:3}}>
                  <DateTimePicker  label="End Date Time" disablePast minDate={start_date} onChange={handleEndDate} name="end_time"/>
                </DemoContainer>
              </LocalizationProvider>
            </div>

            <div style={{marginTop:20}}>
              <Label></Label>
            <TextField id="standard-basic" required label="Exam Instructions " sx={{width:'96%'}}
                name="exam_instruction"variant="outlined" type="text"onChange={handleChange} multiline rows={10} ></TextField>
            </div>

            <div className="btn">
                <div>
                <Button variant="contained" fullWidth sx={{mt:2}} onClick={handleSubmit}>Create Exam</Button>
                </div>
            </div>
            </Box>
        </Container>
    
        </>
    );
}

export default CreateExams;