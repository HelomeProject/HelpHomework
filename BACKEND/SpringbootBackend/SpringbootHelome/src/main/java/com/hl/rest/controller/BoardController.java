package com.hl.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IBoardService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Notice;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/board/")
public class BoardController {
	
	@Autowired
	private IBoardService ser;
	
	@Autowired
	private IMemService memser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

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
				//int curIdx = ser.getNoticeListSize()+1;
				notice.setNoticeImgUrl("/home/noticeImg/"+member.getGrade()+"_"+member.getClassnum()+"_"+notice.getNoticeTitle()+".jpg");
				notice.setMemberIdx(member.getMemberIdx());
				notice.setMemberGrade(member.getGrade());
				notice.setMemberClassNum(member.getClassnum());
				
				ser.createNotice(notice);
				msg.put("Notice", notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "선생님만 접근 가능합니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			Object[] input = {token, notice};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
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
			
			Notice notice = ser.getNotice(noticeIdx);
			if(member.getGrade().equals(notice.getMemberGrade()) && member.getClassnum().equals(notice.getMemberClassNum())) {
				msg.put("Notice", notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "권한 없음");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
			
		} catch(Exception e) {
			String[] input = {token, noticeIdx};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
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
			msg.put("Input Data", token);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
	/** 공지 삭제 */
	@DeleteMapping("/notice/{noticeIdx}")
	@ApiOperation(value = "공지사항 삭제", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> DeleteNotice(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable("noticeIdx") int noticeIdx) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			
			Notice notice = ser.getNotice(noticeIdx+"");
			if(member.getGrade().equals(notice.getMemberGrade()) && member.getClassnum().equals(notice.getMemberClassNum())) {
				ser.deleteNotice(noticeIdx);
				msg.put("Notice", notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "해당 공지 글에 대한 삭제 권한이 없습니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			msg.put("Input Data", token);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		return res;
	}
	
}
