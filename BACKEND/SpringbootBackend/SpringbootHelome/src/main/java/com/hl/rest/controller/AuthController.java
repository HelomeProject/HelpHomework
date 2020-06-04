package com.hl.rest.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hl.rest.key.GetKEY;
import com.hl.rest.service.IAuthService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.MemberLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import java.security.*;

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
	public String createToken(String email, String isteacher) {
		String jwt = "";
		
		//String key = GetKEY.getKey();
		//System.out.println("key 가지러 왔어요 => " + key);
		String key = "ghafjs110!!)";
		
		jwt = Jwts.builder()
				  .setSubject("users/TzMUocMF4p")
				  .setExpiration(new Date(1300819380))
				  .claim("name", "Robert Token Man")
				  .claim("scope", "self groups/admins")
				  .signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();

        System.out.println("토큰 생성 완료-_-");
        return jwt;
	}
	
	public static String createToken(String username) {
        System.out.println("토큰을 생성할게요.");
        String jwt = "";
        try {
			String key = GetKEY.getKey();
			Date exDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(exDate);
			calendar.add(Calendar.MINUTE,30);
			exDate = calendar.getTime();
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");
			TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
			df.setTimeZone(time);
			String expstring = df.format(exDate);
			System.out.println("아래 토큰 만료 시간을 안내해 드립니다 => " + expstring);
			Date calculatedexDate = calendar.getTime();
			
			
			Map<String, Object> headers = new HashMap<>();
			headers.put("typ", "JWT");
			headers.put("alg", "HS256");

			Map<String, Object> payloads = new HashMap<>();
			payloads.put("exp", expstring);
			payloads.put("username", username);

			jwt = Jwts.builder()
					.setHeader(headers)
					.setClaims(payloads)
					.setExpiration(calculatedexDate)
					.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
			
			System.out.println(jwt);
			System.out.println("토큰 생성 완료!");
		}
        catch(Exception e) {
        	System.out.println("에러났어!@!@!@#!@#!@#!@#!@#!@#!@#");
			System.out.println(e.getMessage());
		}
        System.out.println("==============");
		return jwt;
	}
	
	/** SHA-256 generator 
	 * @throws NoSuchAlgorithmException */
	@GetMapping("/auth/sha2/{str}")
	@ApiOperation(value = "SHA-256 generator")
	public ResponseEntity<Map<String, Object>> createSHA256(@PathVariable String str) throws NoSuchAlgorithmException {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(str.getBytes());
	    StringBuilder sb = new StringBuilder();
	    for(byte b : md.digest()) {
	    	sb.append(String.format("%02x", b));
	    }
	    msg.put("sha2", sb.toString());
	    res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
	    return res;
	}
	
	@PostMapping("/auth/login")
	@ApiOperation(value = "로그인 ")
	public ResponseEntity<Map<String, Object>> LoginMember(@RequestBody MemberLogin login) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			if(login.getPassword().equals(ser.getPassword(login.getEmail()))) {
				Member member = memser.getMem(login.getEmail());
				System.out.println(member.toString());
				
				System.out.println(login.getEmail() + " " + member.getIsteacher() );
				String jwt = createToken(login.getEmail(), member.getIsteacher());
				System.out.println(jwt + "대체 왜 안나와");
				
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
			System.out.println("claims error : 아마 키가 없을걸?");
		}
		return c;
	}
}
