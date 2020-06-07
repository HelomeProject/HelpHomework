package com.hl.rest.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.hl.rest.service.IHomeworkService;
import com.hl.rest.service.IMemService;
import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Pagination;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class HomeworkController {
	
	@Autowired
	private IHomeworkService ser;
	
	@Autowired
	private IMemService memser;
	
	@ExceptionHandler(Exception.class)
	public void ExceptionMethod(Exception e) {

	}
	
	/** 숙제 공지생성 */
	@PostMapping("/board/homework")
	@ApiOperation(value = "숙제 공지생성")
	public ResponseEntity<Map<String, Object>> CreateHomeworkNotice(
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
				msg.put("error", "학생은 권한이 없습니다.");
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
			
			if(member.getIsteacher().equals("0")) {
				homework.setHomework_memberIdx(member.getMemberIdx());
				homework.setHomework_url("/home/homeworkImg/"+homework.getHomework_noticeIdx()+"/"+member.getMemberIdx()+".jpg");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //HH:mm:ss
				TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
				df.setTimeZone(time);
				String today = df.format(new Date());
				homework.setHomework_submitDate(today);
				homework.setHomework_score("");
				
				ser.CreateHomework(homework);
				msg.put("Homework", homework);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			} else {
				msg.put("error", "선생님은 제출할 수 없습니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e) {
			Object[] input = {token, homework};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	/** 숙제공지 전체목록 조회 */
	@GetMapping("board/homeworks")
	@ApiOperation(value = "숙제공지 전체목록 조회")
	public ResponseEntity<Map<String, Object>> GetHomeworkNoticeList(
			@RequestHeader(value = "Authorization") String token) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			
			List<HomeworkNotice> homeworknotice = ser.getHomeworkNoticeList();
			msg.put("HomeworkNoticeList", homeworknotice);
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			
		} catch (Exception e) {
			msg.put("Input Data", token);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	/** 숙제제출 전제목록 조회 */
	@GetMapping("/homeworks")
	@ApiOperation(value = "숙제제출 전체목록 조회")
	public ResponseEntity<Map<String, Object>> GetHomeworkList(
			@RequestHeader(value = "Authorization") String token) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			List<Homework> homeworklist = null;
			
			if(member.getIsteacher().equals("1")) { //선생님이 낸 숙제제출 목록 조회
				homeworklist = ser.getHomeworkList_teacher(member.getMemberIdx());
			} else { //자신의 숙제제출 목록 조회
				homeworklist = ser.getHomeworkList_student(member.getMemberIdx());
			}
			
			System.out.println(homeworklist);
			if(homeworklist.size()==0) {
				msg.put("msg", "요청에 성공하였으나 응답할 콘텐츠가 없습니다.");
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.NO_CONTENT);
			} else {
				msg.put("HomeworkList", homeworklist);
				res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
			}
		} catch(Exception e) {
			msg.put("Input Data", token);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	
	/** 숙제제출 현황 조회 */
	@GetMapping("/homework/{homeworkNoticeIdx}")
	@ApiOperation(value = "숙제 제출현황 조회(선생님:homeworkNoticeIdx를 낸 학생 전체 리스트, 학생:자신이 제출한 것)")
	public ResponseEntity<Map<String, Object>> GetHomework(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable("homeworkNoticeIdx") String homeworkNoticeIdx) {
		ResponseEntity<Map<String, Object>> res = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Claims de = AuthController.verification(token);
			String email = (String) de.get("email");
			Member member = memser.getMem(email);
			
			List<Homework> homeworkList=null;
			
			if(member.getIsteacher().equals("1")) {
				String charge = ser.getWhoseHomeworkNotice(homeworkNoticeIdx)+"";
				if(member.getMemberIdx().equals(charge)) {
					homeworkList = ser.getHomeworkList_byIdx(homeworkNoticeIdx);
					msg.put("HomeworkList", homeworkList);
					res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.OK);
				}
				else {
					msg.put("error", "해당 과제 담당자가 아니거나 권한이 없습니다.");
					res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.FORBIDDEN);
				}
			} else {
				
			}
		} catch(Exception e) {
			Object[] input = {token, homeworkNoticeIdx};
			msg.put("Input Data", input);
			msg.put("SAY", "Error msg를 참고하여 Input Data을 다시 한 번 확인해보세요.");
			msg.put("Error msg", e.getMessage());
			res = new ResponseEntity<Map<String, Object>>(msg, HttpStatus.BAD_REQUEST);
			System.out.println(e.getMessage());
		}
		return res;
	}
	
}
