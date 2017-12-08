package com.wechat.backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WechatNote {

	@Id@GeneratedValue 
	private Long noteId;
	private String title;
	private String personId;
	private String content;
	private Date date;
	
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Long getNoteId() {
		return noteId;
	}
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
