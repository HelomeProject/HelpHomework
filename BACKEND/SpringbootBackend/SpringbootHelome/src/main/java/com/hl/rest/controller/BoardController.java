package com.hl.rest.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IBoardService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Notice;
import com.hl.rest.vo.Pagination;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	private IBoardService ser;
	@Autowired
	private IMemService memser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 숙제 제출 */
	@PostMapping("/homework")
	@ApiOperation(value = "숙제 제출")
	public ResponseEntity<Map<String, Object>> CreateHomework(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody Homework homework) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			homework.setMemberIdx(member.getMemberIdx()+"");
						
			ser.insertHomework(homework);
			msg.put("Homework", homework);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
	/** 숙제조회(list) */
	@GetMapping("/homeworks")
	@ApiOperation(value = "숙제 정보 전체 조회", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> GetHomeworkList(
			@RequestHeader(value = "Authorization") String token,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "1") int range){
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			String isteacher = (String) de.get("isteacher");
			Member member = memser.getMem(email);
			
			if(isteacher.equals("1")) {
				List<Homework> list = new LinkedList<>();
				int homeworklistsize = ser.getHomeListSize(member.getGrade(), member.getClassnum());
				Pagination pagination = new Pagination();
				pagination.pageInfo(page, range, homeworklistsize);
				
				list = ser.getHomeworkList(pagination.getStartList(), pagination.getListSize(), member.getGrade(), member.getClassnum());
				msg.put("HomeworkList", list);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
				
			} else {
				List<Homework> list = new LinkedList<>();
				int homeworklistsize = ser.getHomeListSize(member.getMemberIdx());
				Pagination pagination = new Pagination();
				pagination.pageInfo(page, range, homeworklistsize);
				
				list = ser.getHomeworkList(pagination.getStartList(), pagination.getListSize(), member.getMemberIdx());
				msg.put("HomeworkList", list);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			}
			
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;	
	}
	
	
	/** 공지 생성 */
	@PostMapping("/notice")
	@ApiOperation(value = "공지 생성")
	public ResponseEntity<Map<String, Object>> CreateNotice(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody Notice notice) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			String isteacher = (String) de.get("isteacher");
			
			if(isteacher.equals("1")) {
				Member member = memser.getMem(email);
				notice.setNoticeImgUrl("fakepath/"+member.getMemberIdx()+"/"+notice.getNoticeTitle());
				notice.setMemberIdx(member.getMemberIdx());
				notice.setMemberGrade(member.getGrade());
				notice.setMemberClassNum(member.getClassnum());
				
				ser.createNotice(notice);
				msg.put("Notice", notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "권한 없음");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}

		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
	/** 공지사항 조회(one) */
	@GetMapping("/notice/{noticeIdx}")
	@ApiOperation(value = "공지사항 조회(one)", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> GetNotice(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable("noticeIdx") String noticeIdx) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			
			//권한이 있는지?
			Notice notice = ser.getNotice(noticeIdx);
			if(member.getGrade().equals(notice.getMemberGrade()) && member.getClassnum().equals(notice.getMemberClassNum())) {
				msg.put("Notice", notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "권한 없음");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
			
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
	
	@GetMapping("/notices")
	@ApiOperation(value = "공지사항 목록 조회(list)", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> GetNoticeList(
			@RequestHeader(value = "Authorization") String token) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			List<Notice> list = ser.getNoticeList();
			msg.put("NoticeList", list);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
}
