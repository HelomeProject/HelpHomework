package com.hl.rest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.vo.Member;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MemberController {
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 회원가입 */
	@PostMapping("/member/register")
	@ApiOperation(value = "회원가입 ")
	public ResponseEntity<Map<String, Object>> memRegister(@RequestBody Member mem) {
		ResponseEntity<Map<String, Object>> res = null;
		
		
		return res;
	}
}
