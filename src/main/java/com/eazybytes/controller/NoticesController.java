package com.eazybytes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.Notice;
import com.eazybytes.repository.NoticeRepository;

@RestController
public class NoticesController {

	public static final String URL = "/notices";

	private final NoticeRepository noticeRepository;

	public NoticesController(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@GetMapping(URL)
	public List<Notice> getNotices() {
		return noticeRepository.findAllActiveNotices();
	}

}
