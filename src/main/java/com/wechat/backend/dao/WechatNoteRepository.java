package com.wechat.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wechat.backend.domain.WechatNote;

public interface WechatNoteRepository extends CrudRepository<WechatNote, String>{

	List<WechatNote> findByPersonId(String personId);
	
	WechatNote findByNoteId(Long noteId);
	
}
