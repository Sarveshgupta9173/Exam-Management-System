import React, { useEffect } from "react";
import NavBar from "../navbar";
import { HOST_URL } from "../../utils/constants";
import axios from "axios";
import { useLocation } from "react-router-dom";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import CheckIcon from "@mui/icons-material/Check";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Alert,
  Button,
  Typography,
} from "@mui/material";

const StudentPersonalResult = () => {
  const location = useLocation();
  const [resultData, setResultData] = React.useState("");
  const [detailResult, setDetailResult] = React.useState([]);
  const [viewResult, setViewResult] = React.useState(false);
  const [alert, setAlertVal] = React.useState("none");

  useEffect(() => {
    const student_id = location.state.student.user_id;
    const exam_id = location.state.exam.exam_id;

    const getResultDataUrl =
      `${HOST_URL}/user-exam-submission/get-result-section-wise/`.concat(
        student_id
      ) + `/`.concat(exam_id);
    axios
      .get(getResultDataUrl)
      .then((response) => {
        // console.log(response.data);
        setResultData(response.data);
      })
      .catch((error) => {
        console.log(error);
      });

    const getAllDataUrl =
      `${HOST_URL}/user-exam-submission/get-by-student-id-exam-id/`.concat(
        student_id
      ) + `/`.concat(exam_id);

    axios
      .get(getAllDataUrl)
      .then((response) => {
        // console.log(response.data);
        setDetailResult(response.data);
      })
      .catch((error) => {
        //   console.log(error);
        setAlertVal("block");
      });
  }, []);
  console.log(detailResult);

  return (
    <>
      <NavBar></NavBar>
      <div
        className="alert"
        style={{ display: alert, position: "fixed", width: "100%" }}
      >
        <Alert icon={<CheckIcon fontSize="inherit" />} severity="info">
          <span style={{ fontWeight: 600, fontSize: 20 }}>
            Oops!....No questions attended.
          </span>
        </Alert>
      </div>

      <div
        style={{
          backgroundColor: "#f5f3f0",
          height: 260,
          width: "83%",
          borderRadius: 10,
          marginTop: 70,
          marginLeft: 75,
          textAlign: "start",
          paddingLeft: 20,
        }}
      >
        <p style={{ fontWeight: 600, fontSize: 21 }}>
          Exam Name : {location.state.exam.exam_name}
        </p>

        <p style={{ fontWeight: 600, fontSize: 21 }}>
          Total Marks Obtained :
          {resultData.mcq_marks + resultData.program_marks
            ? resultData.mcq_marks + resultData.program_marks
            : 0}
          <span style={{ fontWeight: 600, fontSize: 21, padding: "0px 40px" }}>
            Status :
            {location.state.total_marks_scored >= location.state.exam.pass_marks
              ? " Passed "
              : " Failed "}
          </span>
        </p>

        <p style={{ fontWeight: 600, fontSize: 21 }}>
          Mcq attended:
          {resultData.mcq_count ? resultData.mcq_count : 0}/
          {location.state.exam.no_of_mcq_questions}
          <span style={{ fontWeight: 600, fontSize: 21, padding: "0px 49px" }}>
            Mcq marks :{resultData.mcq_marks ? resultData.mcq_marks : 0}/
            {location.state.exam.no_of_mcq_questions}
          </span>
        </p>

        <p style={{ fontWeight: 600, fontSize: 21 }}>
          Program attended :
          {resultData.program_count ? resultData.program_count : 0}/
          {location.state.exam.no_of_programming_questions}
          <span style={{ fontWeight: 600, fontSize: 21, padding: "0px 20px" }}>
            Program marks :
            {resultData.program_marks ? resultData.program_marks : 0}/
            {location.state.exam.no_of_programming_questions * 10}
          </span>
        </p>

        <Button
          variant="contained"
          color="info"
          onClick={() => setViewResult(!viewResult)}
        >
          {viewResult ? "Hide Detailed Result" : "View Detailed Result"}
        </Button>
      </div>

      {detailResult.map((element, index) => {
        return (
          <div style={{ width: "80%",margin: '40px 0 20px 7%',display:(viewResult)?'block':'none'
          }}>
            <Accordion>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1-content"
                id="panel1-header"
                sx={{fontWeight:500,fontSize:20}}
              >
                Question {index + 1}
              </AccordionSummary>
              <AccordionDetails sx={{ textAlign: "start" }}>
                <Typography>
                  Question Type : {element.question.question_type}
                </Typography>
                <Typography sx={{ mt: 2 }}>
                  Question : {element.question.question}
                </Typography>

                {element.question.question_type === "MCQ" && (
                  <>
                    <Typography>
                      Option A : {element.question.option_A}
                    </Typography>
                    <Typography>
                      Option B : {element.question.option_B}
                    </Typography>
                    <Typography>
                      Option C : {element.question.option_C}
                    </Typography>
                    <Typography>
                      Option D : {element.question.option_D}
                    </Typography>
                    <Typography sx={{ mt: 2 }}>
                      Correct Answer : {element.question.answer}
                    </Typography>
                    
                  </>
                )}

                <Typography sx={{mt:2}}>Your Answer : {element.answer}</Typography>
                <Typography>
                        Marks Obtained : {element.marks_obtained}
                </Typography>
              </AccordionDetails>
            </Accordion>
          </div>
        );
      })}
    </>
  );
};

export default StudentPersonalResult;
