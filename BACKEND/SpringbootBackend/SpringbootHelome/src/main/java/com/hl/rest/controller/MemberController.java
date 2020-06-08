package com.hl.rest.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Pagination;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private IMemService ser;
	
	/** 회원가입 */
	@PostMapping("/user")
	@ApiOperation(value = "회원가입 ")
	public ResponseEntity<Map<String, Object>> CreateMember(@RequestBody @Valid Member mem) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
			
		try {
			ser.registerMem(mem);
			msg.put("member", mem);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		}catch(Exception e) {
			if(e.getMessage().contains("Duplicate")) {
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.CONFLICT);
				System.out.println(e.getMessage());
			} else {
				msg.put("Input Data", mem);
				msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
				msg.put("Error msg", e.getMessage());
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
				System.out.println(e.getMessage());
			}
		}
		return res;
	}
	
	/** 회원조회(list) */
	@GetMapping("/users")
	@ApiOperation(value = "멤버 정보 전체 조회", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> listMem(
			@RequestHeader(value = "Authorization") @Valid String token,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			String isteacher = (String) de.get("isteacher");
			
			if(isteacher.equals("1")) {
				List<Member> list = new LinkedList<>();
				int memlistsize = ser.getMemListSize();
				Pagination pagination = new Pagination();
				pagination.pageInfo(page, range, memlistsize);
				
				//선생님의 학생들만
				Member teacher = ser.getMem(email);
				list = ser.getMemStudentList(pagination.getStartList(), pagination.getListSize(), teacher.getGrade(), teacher.getClassnum());
				msg.put("MemberList", list);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
				
			} else {
				msg.put("Error msg", "권한이 없습니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
			
		} catch (Exception e) {
			String[] input = {token, page+"", range+""};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
	/** 회원조회(self) */
	@GetMapping("/users/{memberIdx}")
	@ApiOperation(value = "멤버 정보 조회", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> listMem(
			@RequestHeader(value = "Authorization") @Valid String token,
			@PathVariable("memberIdx") String memberIdx) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			String isteacher = (String) de.get("isteacher");
			Member user = ser.getMem(email);
			if(isteacher.equals("1") || user.getMemberIdx().equals(memberIdx)) {
				Member member = ser.getMem(Integer.parseInt(memberIdx));
				msg.put("Member", member);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("err", "자신의 개인정보 혹은 선생님만 접근 가능합니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
			}
		}catch(Exception e) {
			String[] input = {token, memberIdx};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	/** 회원수정 */
	@PutMapping("/users/{memberIdx}")
	@ApiOperation(value = "멤버정보 수정", response = List.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> UpdateMember(
			@RequestHeader(value = "Authorization") @Valid String token,
			@PathVariable("memberIdx") String memberIdx,
			@RequestBody Member member) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			member.setMemberIdx(memberIdx);
			ser.updateMember(member);
			msg.put("Member", member);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
		} catch(Exception e) {
			Object[] input = {token, member, memberIdx};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	
	/** 회원탈퇴 */
	
}
