import React, { useEffect } from "react";
import EmailIcon from '@mui/icons-material/Email';
import { Grid, TextField } from "@mui/material";
import PersonIcon from '@mui/icons-material/Person';
import KeyIcon from '@mui/icons-material/Key';
import Button from '@mui/material/Button';
import Paper from "@mui/material/Paper";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import axios from "axios";
import Alert from '@mui/material/Alert';
import CheckIcon from '@mui/icons-material/Check';
import { useNavigate } from "react-router-dom";
import NavBar from "../navbar"
import { HOST_URL } from "../../utils/constants";


const CreateStudent = ()=>{

    const paperStyle = {height:'55vh',margin:"30px auto", padding:30,width:'35%'};
    const initialFormData = Object.freeze({
        first_name:"",
        last_name:"",
        email: "",
        password: "",
        role:""
      });

    const [formData, updateFormData] = React.useState(initialFormData);
    const [alert,setAlertVal] = React.useState("none");
    const [danger,setDanger] = React.useState("none");
    const [errorMsg,setErrorMsg] = React.useState("none");
    const navigate = useNavigate();

    useEffect(()=>{
        if(sessionStorage.getItem("role") != "ADMIN"){
            navigate("/authorization-alert");
        }
    },[]);


    const handleChange = (event) => {
        updateFormData({ ...formData,  [event.target.name]: event.target.value });
      };
   
    const handleSubmit = (e) => {

            if(formData.first_name){
                if(formData.email && formData.email.includes(".com")){
                    if(formData.password){
                        const mydata = {
                            first_name: formData.first_name,
                            last_name: formData.last_name,
                            email: formData.email,
                            password:formData.password,
                            role :formData.role
                        };

                        const url = `${HOST_URL}/user/create-user`;
                         
                        axios.post(url, mydata)
                        .then((response)=>
                        {
                            if(response.status !== 200){
                                setErrorMsg("Error ....This email already exists.")
                            setDanger("block");
                            setTimeout(() => {
                                setDanger("none");
                            }, 3000);
                            }else{
                                setAlertVal("block");
                                setTimeout(() => {
                                    setAlertVal("none");
                                }, 3000);
                            }
                        })
                        .catch((error)=>
                        {
                            console.log(error);
                            setErrorMsg("Error ....This email already exists.")
                            setDanger("block");
                            setTimeout(() => {
                                setDanger("none");
                            }, 3000);
                        });

                       
                                                
                        
                    }else{
                        setErrorMsg("Please Enter Password");
                        setDanger("block"); 
                        
                        setTimeout(() => {
                            setDanger("none");
                        },3000);
                    }
                }else{
                    console.log("Enter email");
                    setErrorMsg("Please enter valid email");
                    setDanger("block"); 
                    setTimeout(() => {
                        setDanger("none");
                    },3000);
                }
            }else{
                console.log("enter username");
                setErrorMsg("Please enter username");
                setDanger("block");
                setTimeout(() => {
                    setDanger("none");
                },3000); 

            }
        }

        return (
        <>
        <NavBar ></NavBar>
        <div className="alert" style={{display:alert,position:'fixed'}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
             Student created successfully
        </Alert>
        </div> 
        <div className="alert" style={{display:danger,position:"fixed"}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
             {errorMsg}
        </Alert>
        </div> 
        
        
        <h2>Create New Student</h2>
        <Grid>
        <Paper elevation={8} style={paperStyle}>
        <FormControl>

        <div className="container">
            <div className="inputs"  >
                <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <TextField id="standard-basic" required={true} label="First Name" name="first_name" variant="outlined" type="text"
                onChange={handleChange} sx={{width:400}}  ></TextField>
            </div>
            <div className="inputs"  >
                <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <TextField id="standard-basic" label="Last Name" name="last_name" variant="outlined" type="text" 
                onChange={handleChange} required sx={{width:400}}  ></TextField>
            </div>
            <div className="inputs"  >
                <EmailIcon fontSize="large"></EmailIcon>
                <TextField id="standard-basic" label="Email"   name="email" variant="outlined" type="email"
                onChange={handleChange} required sx={{width:400}}  ></TextField>
            </div>
            <div className="inputs" >
                <KeyIcon fontSize="large" ></KeyIcon>
                <TextField id="standard-basic" label="Password" name="password" variant="outlined" type="password"
                onChange={handleChange} required sx={{width:400}} ></TextField>
            </div>
            <div className="inputs">
                <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <FormControl >
                    <InputLabel id="demo-simple-select-label">Role</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Role"
                    value="STUDENT"
                    onChange={handleChange}
                    name="role"
                    placeholder="Select Role"
                    required
                    sx={{mt:2,width:400 }}
                    >
                    <MenuItem value={"STUDENT"}>Student</MenuItem>
                    </Select>
                </FormControl>
            </div>
        </div> 

        <div className="btn">
            <div>
            <Button variant="contained"  onClick={handleSubmit}>Create Student</Button>
            </div>
        </div>
        </FormControl>
        
        </Paper>
        </Grid>

        </>
    )
}

export default  CreateStudent;