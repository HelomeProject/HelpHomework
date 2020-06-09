package com.hl.rest.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import com.hl.rest.key.GetKEY;
import com.hl.rest.service.IAuthService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.MemberLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private IAuthService ser;
	@Autowired
	private IMemService memser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 토큰 생성 */
	public static String createToken(String email, String isteacher) {
		String jwt = "";
		try {
			String key = GetKEY.getKey();
			Map<String, Object> headers = new HashMap<>();
			headers.put("typ", "JWT");
			headers.put("alg", "HS256");

			Map<String, Object> payloads = new HashMap<>();
			payloads.put("email", email);
			payloads.put("isteacher", isteacher);
			jwt = Jwts.builder()
					.setHeader(headers)
					.setClaims(payloads)
					.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
		}catch(Exception e) {
			String[] input = {email, isteacher};
			System.out.println("input => "+input);
			System.out.println(e.getMessage());
		}
		return jwt;
	}
	
	/** SHA-256 generator 
	 * @throws NoSuchAlgorithmException */
	public static String createSHA256(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(str.getBytes());
	    StringBuilder sb = new StringBuilder();
	    for(byte b : md.digest()) {
	    	sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
	
	@PostMapping("/auth/login")
	@ApiOperation(value = "로그인 ")
	public ResponseEntity<Map<String, Object>> LoginMember(@RequestBody MemberLogin login) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		if(login.getEmail()=="" || login.getPassword()=="") {
			return new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
		}
		try {
			if(login.getPassword().equals(ser.getPassword(login.getEmail()))) {
				Member member = memser.getMem(login.getEmail());
				String jwt = createToken(login.getEmail(), member.getIsteacher());
				
				msg.put("memberIdx", member.getMemberIdx());
				msg.put("email", login.getEmail());
				msg.put("username", member.getUsername());
				msg.put("school", member.getSchool());
				msg.put("isteacher", member.getIsteacher());
				msg.put("grade", member.getGrade());
				msg.put("classnum", member.getClassnum());
				msg.put("token", jwt);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map<String, Object>>(msg, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			msg.put("Input Data", login);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	/** Claims 객체 */
	public static Claims verification(String token) {
		Claims c = null;
		try {
			c = Jwts.parser()
				.setSigningKey(GetKEY.getKey().getBytes())
				.parseClaimsJws(token)
				.getBody();
		} catch(Exception e) {
			System.out.println("claims error");
		}
		return c;
	}
}
