import React from "react";
import NavBar from "../navbar";
import { useLocation } from "react-router-dom";
import EmailIcon from '@mui/icons-material/Email';
import { Grid, TextField} from "@mui/material";
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
import { HOST_URL } from "../../utils/constants";

const EditStudent = (props) => {
    const location = useLocation();

    const paperStyle = {height:'55vh',margin:"30px auto", padding:30,width:'35%'};
    const initialFormData = Object.freeze({
        user_id:location.state.user_id,
        first_name:location.state.first_name,
        last_name:location.state.last_name,
        email: location.state.email,
        password: location.state.password,
        role:location.state.role
      });
    const [formData, updateFormData] = React.useState(initialFormData);
    const [alert,setAlertVal] = React.useState("none");
    const [danger,setDanger] = React.useState("none");
    const [errorMsg,setErrorMsg] = React.useState("none");
    const [role, setRole] = React.useState('');
    const navigate = useNavigate();

    const handleChange = (event) => {
        setRole(event.target.value);
        updateFormData({
          ...formData,
          [event.target.name]: event.target.value
        });

      };
   
    const handleSubmit = (e) => {
        
             
            if(formData.first_name){
                if(formData.email && formData.email.includes(".com")){
                    if(formData.password){
                        const mydata = {
                            user_id:location.state.user_id,
                            first_name: formData.first_name,
                            last_name: formData.last_name,
                            email: formData.email,
                            password:formData.password,
                            role :formData.role
                        };

                        const user_id = location.state.user_id;
                        const url = `${HOST_URL}/user/update/`.concat(user_id);
            
                         axios.put(url, mydata,{headers:{
                                'Content-Type': 'application/json',
                                'Access-Control-Allow-Origin': '*'
                                }})
                            .then((response)=>{

                                if(response.status === 200){
                                    setAlertVal("block");
                                    setTimeout(() => {
                                        navigate("/student");
                                    },2000);
                                    }
                                    
                            })
                            .catch((error)=>{
                                console.log(error);
                                setErrorMsg("User already exists");
                                setDanger("block");
                                setTimeout(() => {
                                    setDanger("none");
                                }, 3000);
                            });
                        
                    }else{
                        console.log("This Data Alredy Exists")
                        setDanger("block"); 
                        setTimeout(() => {
                            setDanger("none");
                        }, 3000); 
                    }
                }else{
                    console.log("Enter email");
                    setErrorMsg("Please Enter Valid Email");
                    setDanger("block"); 
                    setTimeout(() => {
                        setDanger("none");
                    }, 3000); 
                }
            }else{
                console.log("enter username");
                setErrorMsg("Please Enter Username");
                setDanger("block");
                setTimeout(() => {
                    setDanger("none");
                }, 3000); 

            }
        }

    return (
        <>
         
        <NavBar></NavBar>
        <div className="alert" style={{display:alert,position:"fixed"}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
             Student details updated successfully
        </Alert>
        </div> 
        <div className="alert" style={{display:danger,position:"fixed"}}>
         <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
             {errorMsg}
        </Alert>
        </div>
        <h2>Edit Student</h2>
        <Grid>
        <Paper elevation={5} style={paperStyle}>
        <div className="container">
            <div className="inputs"  >
                <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <TextField id="standard-basic" required label="First Name" name="first_name" variant="outlined" type="text"
                onChange={handleChange} defaultValue={location.state.first_name} sx={{width:400}}></TextField>
            </div>
            <div className="inputs"  >
                <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <TextField id="standard-basic" label="Last Name" name="last_name" variant="outlined" type="text" 
                onChange={handleChange} required defaultValue={location.state.last_name}sx={{width:400}} ></TextField>
            </div>
            <div className="inputs"  >
                <EmailIcon fontSize="large"></EmailIcon>
                <TextField id="standard-basic"   name="email" variant="outlined" type="email"
                onChange={handleChange} required defaultValue={location.state.email} sx={{width:400}}></TextField>
            </div>
            <div className="inputs" >
                <KeyIcon fontSize="large" ></KeyIcon>
                <TextField id="standard-basic" name="password" variant="outlined" type="password"
                onChange={handleChange} required defaultValue={location.state.password} sx={{width:400}}></TextField>
            </div>
            <div className="inputs">
                 <PersonIcon fontSize="large" margin="normal"></PersonIcon>
                <FormControl  >
                    <InputLabel id="demo-simple-select-label">Role</InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="Role"
                    value={location.state.role}
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
            <Button variant="contained"  onClick={handleSubmit}>Update Student</Button>
            </div>
        </div>
        
        </Paper>
        </Grid>
        </>
    );
}
export default EditStudent;