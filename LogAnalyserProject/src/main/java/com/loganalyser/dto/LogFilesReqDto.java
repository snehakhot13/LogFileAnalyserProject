package com.loganalyser.dto;

import java.util.List;

public class LogFilesReqDto {
	
	
	private String srcFolderPath ;
	private String destFolderPath;
	//private List<String> serverName;
	//private List<String> date;
	private String searchInput;
	private String[] serverName;
	private String[] date;
	private String[] email;
	
	public String getSrcFolderPath() {
		return srcFolderPath;
	}
	public void setSrcFolderPath(String srcFolderPath) {
		this.srcFolderPath = srcFolderPath;
	}
	public String getDestFolderPath() {
		return destFolderPath;
	}
	public void setDestFolderPath(String destFolderPath) {
		this.destFolderPath = destFolderPath;
	}
	public String[] getServerName() {
		return serverName;
	}
	public void setServerName(String[] serverName) {
		this.serverName = serverName;
	}
	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
	public String[] getEmail() {
		return email;
	}
	public void setEmail(String[] email) {
		this.email = email;
	}
	public String getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}
	
	
	
	
}
