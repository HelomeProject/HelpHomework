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
import com.hl.rest.vo.Member;
import com.hl.rest.vo.MemberLogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private IAuthService ser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 토큰 생성 */
	public static String createToken(String username) {
		String jwt = "";
		try {
			String key = GetKEY.getKey();
			Map<String, Object> headers = new HashMap<>();
			headers.put("typ", "JWT");
			headers.put("alg", "HS256");

			Map<String, Object> payloads = new HashMap<>();
			payloads.put("username", username);
			jwt = Jwts.builder()
					.setHeader(headers)
					.setClaims(payloads)
					.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
		}catch(Exception e) {
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
				String jwt = createToken(login.getEmail());
				msg.put("username", login.getEmail());
				msg.put("token", jwt);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map<String, Object>>(msg, HttpStatus.UNAUTHORIZED);
			}
		} catch(Exception e) {
			msg.put("resmsg", e.getMessage());
			System.out.println(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(msg, HttpStatus.NOT_FOUND);
		}
		return res;
	}
}
