package com.hl.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IBoardService;
import com.hl.rest.vo.Homework;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BoardController {
	
	@Autowired
	private IBoardService ser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 숙제 제출 */
	@PostMapping("/board/homework")
	@ApiOperation(value = "숙제 제출")
	public ResponseEntity<Map<String, Object>> CreateHomework(@RequestBody Homework homework) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			ser.insertHomework(homework);
			msg.put("Homework", homework);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		} catch(Exception e) {
			msg.put("error", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		}
		
		return res;
	}
}
