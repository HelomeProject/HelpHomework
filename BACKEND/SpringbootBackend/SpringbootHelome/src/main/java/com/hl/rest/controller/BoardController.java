package com.hl.rest.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IBoardService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.Member;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BoardController {
	
	@Autowired
	private IBoardService ser;
	@Autowired
	private IMemService memser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 숙제 제출 */
	@PostMapping("/board/homework")
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
			
			homework.setMemberIdx(member.getMemberIdx());
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
	@GetMapping("/board/homework")
	@ApiOperation(value = "숙제 정보 전체 조회", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> GetHomeworkList(
			@RequestHeader(value = "Authorization") String token){
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			String isteacher = (String) de.get("isteacher");
			
			//선생님 -> 학생들 것
			
			
			//학생 -> 자기것
			
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		}
		
		return res;
		
	}
	
}
