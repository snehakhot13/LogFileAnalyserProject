package com.loganalyser.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.loganalyser.dto.LogFilesReqDto;

@Service
public class LogFileService {

	
	private String search;
	 static File outfile; 
	 static BufferedWriter writer ;//= new BufferedWriter(new FileWriter(outfile));
	
	public void logAnalyser(LogFilesReqDto req) throws IOException {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 String todayDate = df.format(new Date());
		

		String INfolderPath= req.getSrcFolderPath();
		String outfolderPath= req.getDestFolderPath();
		
		outfile = new File(outfolderPath+"\\"+todayDate+".log");
		writer = new BufferedWriter(new FileWriter(outfile));
		
		System.out.println("service ");
		for(String serveri:req.getServerName()) {
		String server =serveri;
		
		for(String datei:req.getDate()) {
		String		date= datei;
	
		if(date !=null && server!=null)this.search=server;
		else if(date ==null)this.search=server;
		else if(server==null)this.search=date;
		
	
		    
		System.out.println("search :"+search +" , date :"+date +", server : "+server+" , todayDate : "+ todayDate);
		String pattern = "ERROR";
	
		
		
	    File directory = new File(INfolderPath); 
		
		String[] list = directory.list(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		    	
		        return name.contains(search);
		    }
		});
		
		
		for(String fileName:list) {
			
	
			if(todayDate.equals(date)) {
				System.out.println("today");
			
				String fileName2 = "titan-"+server+"-server.log";
			//if(fileName.startsWith("titan-") && fileName.contains(server) && fileName.endsWith(".log") ) {
				if(fileName.contains(fileName2)  ) {
				fileOp(fileName, INfolderPath , outfolderPath , pattern );
			}
		
		}else if(date !=null){
		
			/*if(date !=null) {*/if(fileName.contains(date)) {fileOp(fileName, INfolderPath , outfolderPath , pattern );
			
			
			}}
	     
			else if(date==null) {fileOp(fileName, INfolderPath , outfolderPath , pattern );}
			}
		//System.out.println("writer closed");writer.close();
		
		}
		}
		}
	//}
		
		
public static void fileOp(String fileName,String INfolderPath ,String outfolderPath ,String pattern ) throws IOException {
		
		System.out.println(fileName);
		String sourceFilePath = INfolderPath+"\\"+fileName;
		System.out.println(sourceFilePath);
	
		BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
		// File outfile = new File(outfolderPath+"\\"+fileName);
		//	BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			writer.write("\n"+fileName+"\n\n");
		
		 String line;
            boolean foundPattern = false;

            while ((line = reader.readLine()) != null) {
               
                if (line.contains(pattern)) {
                    foundPattern = true;
                   
                    String[] words = line.split(" ");
                    writer.write(
                    "Time : "+words[0]+"\n"
                    +"UserId : "+words[3]+"\n"
                    +"URL : "+words[4]+"\n"
                    +"Error : "+reader.readLine()+"\n"
                    +reader.readLine()+"\n"+"\n");
                }
                
                
            }
            reader.close();
			writer.close();
            
          //  if(outfile.length()==0) {outfile.delete();}
       
}

}
