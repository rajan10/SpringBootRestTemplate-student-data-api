package com.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/student")
public class StudentController {
	@GetMapping("/{studentId}")
	public ResponseEntity<ResponseData> getStudentData(@PathVariable("studentId") Long studentId){
		//Create a ResponseData object to encapsulate the response
		ResponseData response= new ResponseData();
		
		//create a dummy student object with sample data
		Student s1= new Student();
		s1.setStudentId(1l);
		s1.setStudentName("Rajan");
		s1.setAddress("Kathmandu");
		s1.setCollegeId(1l);
		
		
		//set the student data in the response
		response.setStudent(s1);
		
		//Fetch college data using RestTemplate
		Long collegeId=s1.getCollegeId();
		RestTemplate restTemplate = new RestTemplate();
		String endPoint="http://localhost:8081/college/{collegeId}";
		
		//Make a GET request to the college endpoint with the collegeId
		ResponseEntity<College> data = restTemplate.getForEntity(endPoint,College.class, collegeId);
		if(data.getStatusCodeValue()==200)
		{
			//if successful, set the college data in the response
			College c1=data.getBody();
			response.setCollege(c1);
		}
	//return the ResponseData object as the response with HTTP status OK
		return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
		
	}

}
