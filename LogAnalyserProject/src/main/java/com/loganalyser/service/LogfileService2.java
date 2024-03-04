package com.loganalyser.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.loganalyser.dto.LogFilesReqDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


//local
@Service
public class LogfileService2 {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	private static String srcFolderPath;
	static File outfile;
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 static String todayDate = df.format(new Date());
	 
	
	


	public void LogfileService2(LogFilesReqDto req) throws IOException {
		
	srcFolderPath=req.getSrcFolderPath();
	outfile = new File(req.getDestFolderPath()+"\\"+todayDate+".log");
	BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
		
		List<String> filePaths = null;
		for(String serverName:req.getServerName()) {
			for(String date:req.getDate()) {
				
				filePaths=getfilePaths(serverName,date);
				//filePaths.forEach(System.out::println);
				fileOp(filePaths,req.getSrcFolderPath(),req.getDestFolderPath(),req.getSearchInput(), writer);
			}}
		
		//allFpaths.add(filePaths);
		//allFpaths.forEach(System.out::println);
	
		writer.close();
		this.sendMailWithAttachment(outfile,req.getEmail());
		System.out.println(" ");
		
	}

	
	
public static void fileOp(List<String> filePathslist,String INfolderPath ,String outfolderPath ,String pattern, BufferedWriter writer ) throws IOException {
		for(String filePath:filePathslist) {
			
			System.out.println(filePath);
			Path path=Paths.get(filePath);
			Path fname =path.getFileName();
			String fileName=fname.toString();
			System.out.println("fname ::"+fname);
			
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			writer.write("\n**********"+fname+"**********\n\n");
			
			 String line ;
	           boolean foundPattern = false;
             if(fileName.contains("user")) {System.out.println("user");
	                  while ((line = reader.readLine()) != null) {
	               
	                if (line.contains(pattern)) {
	                	foundPattern = true;
	                    String[] words = line.split(" ");
	                    writer.write( "Time : "+words[0]+"\n"+"UserId : "+words[3]+"\n" +"URL : "+words[4]+"\n" +"Error : "+reader.readLine()+"\n" +reader.readLine()+"\n"+"\n"); }}
             }
             else if (fileName.contains("wallet")) {System.out.println("wallet");
             
                    while ((line = reader.readLine()) != null) {
	               
	                if (line.contains(pattern)) {
	                    foundPattern = true;
	                    String[] words = line.split(" ");
	                    writer.write( "Time : "+words[0]+"\n"+"UserId : "+words[2]+"\n" +"URL : "+words[4]+"\n" +"Error : "+reader.readLine()+"\n" +reader.readLine()+"\n"+"\n"); }}
	                
             } else {
            	 
            	 while ((line = reader.readLine()) != null) {
  	               
 	                if (line.contains(pattern)) {
 	                	foundPattern = true;
 	                    String[] words = line.split(" ");
 	                    writer.write( "Time : "+words[0]+"\n"+"UserId : "+words[3]+"\n" +"URL : "+words[4]+"\n" +"Error : "+reader.readLine()+"\n" +reader.readLine()+"\n"+"\n"); }}
             }
		}}
		
			
		
		
		
		
			
		
			


public static List<String> getfilePaths(String server,String date) throws IOException{
	
	List<String> filePaths;
	if(date.contentEquals(todayDate)) {
		filePaths=Files.list(Paths.get(srcFolderPath)).map(Path::toString).filter((f)->f.contains("titan-"+server+"-server.log")||f.contains("titan-"+server+"-services.log")).collect(Collectors.toList());
		return filePaths;
		
	}else {
	
	filePaths=Files.list(Paths.get(srcFolderPath)).map(Path::toString).filter((f)->f.contains(server)&&f.contains(date)).collect(Collectors.toList());
	return filePaths;
	}

	
	
	
}

public void sendMailWithAttachment(File file, String[] emails) {
	
	try {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		//mimeMessageHelper.setTo("sneha.vk@dikshatech.com");
		mimeMessageHelper.setTo(emails[0]);
	
		String[] cc = {emails[1],emails[2]};
		//String[] cc = {"mail1@gmail.com","mail2@gmail.com"};
		//for(String cc:emails) { mimeMessageHelper.setCc(cc);}
		 mimeMessageHelper.setCc(cc);
		mimeMessageHelper.setSubject("Log File Analyser  "+todayDate);
		mimeMessageHelper.setText("Hi ,\n\n"
				+ "Please find attachment of LogFile Analyser for  "+todayDate
				+ "\n\nThanks & Regards\n sneha.vk");
		FileSystemResource fileSystemResource = new FileSystemResource(file);
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		javaMailSender.send(mimeMessage);
		System.out.println("mail sent");
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new RuntimeException("not sent");
	}
}



}


