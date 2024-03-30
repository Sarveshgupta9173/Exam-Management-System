import { Alert, Button, FormControl, InputLabel, MenuItem, Select, TextField, TextareaAutosize } from "@mui/material";
import axios from "axios";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CheckIcon from '@mui/icons-material/Check';
import NavBar from "../navbar";
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import { HOST_URL } from "../../utils/constants";



const CreateQuestion = () =>{



    const navigate = useNavigate();
    const initialFormData = Object.freeze({
        question_type:"",
        categories:"",
        difficulty_level: "",
        question: "",
        option_A:"",
        option_B:"",
        option_C:"",
        option_D:"",
        answer:null,
      });

    const [formData, updateFormData] = React.useState(initialFormData);
    const [alert,setAlertVal] = React.useState("none");
    const [danger,setDanger] = React.useState("none");
    const [question_type,setQuestionType] = React.useState('');
    const [categories,setCategory] = React.useState('');
    const [difficulty_level,setDifficultyLevel] = React.useState('');
    const [answer,setAnswer] = React.useState('');
    const [disable,setDisable] = React.useState(false);

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

        const handleAnswer = (event)=>{
            setAnswer(event.target.value);

            updateFormData({
                ...formData,
                [event.target.name]: event.target.value
                });
        }

        const handleDifficultyLevel = (event)=>{
            setDifficultyLevel(event.target.value);

            updateFormData({
                ...formData,
                [event.target.name]: event.target.value
                });
        }

        const handleCategory = (event)=>{
            setCategory(event.target.value);

            updateFormData({
                ...formData,
                [event.target.name]: event.target.value
                });
        }

        const handleQuestionType = (event)=>{
            setQuestionType(event.target.value);
            if(event.target.value === "PROGRAMMING"){
                setDisable(true);
            }else{
                setDisable(false);
            }

            updateFormData({
                ...formData,
                [event.target.name]: event.target.value
            });
        }

   
        const handleSubmit = (e) => {
        
            console.log(formData);

            if(formData.question_type){
                if(formData.categories){
                    if(formData.difficulty_level){
                        if(formData.question){
                            if(formData.answer){
                                const url = `${HOST_URL}/question/create-question`;

                                    axios.post(url,formData,{headers:{
                                                    'Content-Type': 'application/json',
                                                    'Access-Control-Allow-Origin': '*'
                                                }})
                                            .then((response)=>{
                                                console.log("Form Submitted Successfully.."+ response.data);
                                            })
                                            .catch((error)=>{
                                                console.log("Error Submitting Form"+ error);
                                            });
                                    
                                    setAlertVal("block");
                                    setTimeout(()=>{
                                        navigate('/questions');
                                    },2000)
                            }else{
                                setDanger("block");
                                console.log("Error....Please enter Answers");
                                setTimeout(() => {
                                    setDanger("none");
                                }, 3000); 
                            }
                        }else{
                            setDanger("block");
                            console.log("Error....Please enter Question");
                            setTimeout(() => {
                                setDanger("none");
                            }, 3000); 
                        }
                    }else{
                        setDanger("block");
                        console.log("Error....Please enter Difficulty Level");
                        setTimeout(() => {
                            setDanger("none");
                        }, 3000); 
                    }
                }else{
                    setDanger("block");
                    console.log("Error....Enter Category");
                    setTimeout(() => {
                        setDanger("none");
                    }, 3000); 

                }
            }else{
                setDanger("block");
                console.log("Error....Enter Question Type");
                setTimeout(() => {
                    setDanger("none");
                }, 3000); 
            }
        }


    return(
        <>
        
        
        <NavBar ></NavBar>
        <div className="alert" style={{display:alert,position:"fixed"}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
             Question created successfully
        </Alert>
        </div> 
        <div className="alert" style={{display:danger,position:"fixed"}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
             Please Fill out all the Fields 
        </Alert>
        </div> 
        <h2>Create New Question</h2>

        <Container maxWidth="md">
            <Box sx={{ bgcolor: '#f2f4f7', height: '828px',padding:5,borderRadius:5 }} >
            <div className="container">
                
                <div className="inputs">
                <FormControl fullWidth >
                    <InputLabel id="demo-simple-select-label">Select Question Type</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Role"
                    value={question_type}
                    onChange={handleQuestionType}
                    name="question_type"
                    required
                    >
                    <MenuItem value={"MCQ"}>Mcq</MenuItem>
                    <MenuItem value={"PROGRAMMING"}>Programming</MenuItem>
                    </Select>
                </FormControl>
            </div>
            <div className="inputs">
                <FormControl fullWidth >
                    <InputLabel id="demo-simple-select-label"> Select Category</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Category"
                    value={categories}
                    onChange={handleCategory}
                    name="categories"
                    required
                    >
                    <MenuItem value={"LOGICAL"}>Logical</MenuItem>
                    <MenuItem value={"TECHNICAL"}>Technical</MenuItem>
                    <MenuItem value={"PROGRAMMING"}>Porgramming</MenuItem>
                    </Select>
                </FormControl>
            </div>
            <div className="inputs">
                <FormControl fullWidth >
                    <InputLabel id="demo-simple-select-label">Select Difficulty Level</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Difficulty Level"
                    value={difficulty_level}
                    onChange={handleDifficultyLevel}
                    name="difficulty_level"
                    required
                    >
                    <MenuItem value={"EASY"}>Easy</MenuItem>
                    <MenuItem value={"MEDIUM"}>Medium</MenuItem>
                    <MenuItem value={"HARD"}>Hard</MenuItem>
                    </Select>
                </FormControl>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" multiline rows={8}  sx={{width: '95%'}} className="disabled" required label="Enter Question" name="question" variant="outlined" type="text"
                onChange={handleChange}  ></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" label="Enter Option A" name="option_A" variant="outlined" type="text" 
                onChange={handleChange} required disabled={disable} sx={{width:'95%'}}></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" label="Enter Option B" name="option_B" variant="outlined" type="text" 
                onChange={handleChange} required disabled={disable} sx={{width:'95%'}}></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" label="Enter Option C" name="option_C" variant="outlined" type="text" 
                onChange={handleChange}  disabled={disable} sx={{width:'95%'}}></TextField>
            </div>
            <div className="inputs"  >
                <QuestionAnswerIcon fontSize="large"></QuestionAnswerIcon>
                <TextField id="standard-basic" label="Enter Option D" name="option_D" variant="outlined" type="text" 
                onChange={handleChange}  disabled={disable} sx={{width:'95%'}}></TextField>
            </div>
            <div className="inputs">
                <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">Select Correct Answer</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Answer"
                    value={answer}
                    onChange={handleAnswer}
                    name="answer"
                    disabled={disable}
                    required
                    >
                    <MenuItem value={"OPTION_A"}>Option A</MenuItem>
                    <MenuItem value={"OPTION_B"}>Option B</MenuItem>
                    <MenuItem value={"OPTION_C"}>Option C</MenuItem>
                    <MenuItem value={"OPTION_D"}>Option D</MenuItem>
                    </Select>
                </FormControl>
            </div>
        </div> 
        <div className="btn">
            <div>
            <Button variant="contained" fullWidth sx={{mt:2}} onClick={handleSubmit}>Create Question</Button>
            </div>
        </div>
            </Box>
        </Container>
    

        </>

    );
}

export default CreateQuestion;