package com.loganalyser.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loganalyser.dto.LogFilesReqDto;

import com.loganalyser.service.LogFileService;
import com.loganalyser.service.LogfileService2;

@RestController
public class LogFilesController {
	
	@Autowired
	LogfileService2 LogfileService;
	
	@RequestMapping(
		      value = {"/LogAnalyser"},
		      method = {RequestMethod.POST},
		    consumes = {"application/json", "application/xml"}
		     // produces = {"application/json"}
		   )
		   public ResponseEntity<?> logAnalyser(@RequestBody LogFilesReqDto req ) throws IOException {
		     
                System.out.println(req);
		      try {
		    	 
		    	 
		    	 // logFileService.logAnalyser(req);
		    	  LogfileService.LogfileService2(req);
		          return ResponseEntity
		  				.ok()
		  				.contentType(MediaType.APPLICATION_JSON)
		  				.body("");
		      } catch (Exception e) {
		    	 e.printStackTrace();
		         return new ResponseEntity(HttpStatus.NOT_FOUND);
		      }
			
		   }
	 

}
