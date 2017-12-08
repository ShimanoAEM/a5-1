package com.wechat.backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.backend.dao.WechatNoteRepository;
import com.wechat.backend.domain.WechatNote;

@RestController
@RequestMapping(value = "/note")
public class WechatNoteController {

	@Autowired
	WechatNoteRepository wechatNoteRepository;
	
	@RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
	public String wechatNoteSave(String title, String personId,String content){
		WechatNote note = new WechatNote();
		note.setTitle(title);
		note.setPersonId(personId);
		note.setContent(content);
		note.setDate(new Date());
		wechatNoteRepository.save(note);
		return "success";
	}
	
	@RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
	public String findByPersonId(Long noteId , String title , String content){
		WechatNote wechatNote = wechatNoteRepository.findByNoteId(noteId);
		wechatNote.setTitle(title);
		wechatNote.setContent(content);
		wechatNote.setDate(new Date());
		wechatNoteRepository.save(wechatNote);
		return "success";
	}
	
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteByNoteId(Long noteId){
		WechatNote wechatNote = wechatNoteRepository.findByNoteId(noteId);
		wechatNoteRepository.delete(wechatNote);
		return "success";
	}
	
	@RequestMapping(value = "/findByPersonId", method = {RequestMethod.GET, RequestMethod.POST})
	public List<WechatNote> findByPersonId(String personId){
		return wechatNoteRepository.findByPersonId(personId);
	}
	
	@RequestMapping(value = "/findByNoteId", method = {RequestMethod.GET, RequestMethod.POST})
	public WechatNote findByNoteId(Long noteId){
		return wechatNoteRepository.findByNoteId(noteId);
	}
	
	@RequestMapping(value = "/findAll", method = {RequestMethod.GET, RequestMethod.POST})
	public Iterable<WechatNote> findAll(){
		return wechatNoteRepository.findAll();
	}
	
}
