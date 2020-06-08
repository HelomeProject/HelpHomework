package com.hl.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IBoardDao;
import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Notice;

@Service
public class BoardServiceImpl implements IBoardService {
	
	@Autowired
	IBoardDao repo;

	@Override
	public Notice getNotice(String noticeIdx) {
		return repo.getNotice(noticeIdx);
	}

	@Override
	public void createNotice(Notice notice) {
		repo.createNotice(notice);
	}

	@Override
	public List<Notice> getNoticeList() {
		return repo.getNoticeList();
	}

	@Override
	public int getNoticeListSize() {
		return repo.getNoticeListSize();
	}

}
