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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IBoardService;
import com.hl.rest.service.IHomeworkService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Pagination;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/board/")
public class HomeworkController {
	
	@Autowired
	private IHomeworkService ser;
	
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
			@RequestBody HomeworkNotice homework_notice) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			homework_notice.setHomeworkNotice_memberIdx(member.getMemberIdx()+"");
			
			if(member.getIsteacher().equals("1")) {
				ser.CreateHomeworkNotice(homework_notice);
				msg.put("homework_notice", homework_notice);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "권한이 없습니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			Object[] input = {token, homework_notice};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
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
			String[] input = {token, page+"", range+""};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;	
	}
}
