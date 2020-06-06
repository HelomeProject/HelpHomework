package com.hl.rest.vo;

import javax.validation.constraints.FutureOrPresent;

public class Homework_old {
	String homeworkIdx, homeworkTitle, homeworkDetail, memberIdx;
	
	@FutureOrPresent String startDate;
	@FutureOrPresent String endDate;
	
	public Homework_old() {}
	public Homework_old(String homeworkIdx, String homeworkTitle, String startDate, String endDate, String homeworkDetail,
			String memberIdx) {
		this.homeworkIdx = homeworkIdx;
		this.homeworkTitle = homeworkTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.homeworkDetail = homeworkDetail;
		this.memberIdx = memberIdx;
	}

	public String getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(String memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getHomeworkIdx() {
		return homeworkIdx;
	}
	public void setHomeworkIdx(String homeworkIdx) {
		this.homeworkIdx = homeworkIdx;
	}
	public String getHomeworkTitle() {
		return homeworkTitle;
	}
	public void setHomeworkTitle(String homeworkTitle) {
		this.homeworkTitle = homeworkTitle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getHomeworkDetail() {
		return homeworkDetail;
	}
	public void setHomeworkDetail(String homeworkDetail) {
		this.homeworkDetail = homeworkDetail;
	}
	@Override
	public String toString() {
		return "Homework [homeworkIdx=" + homeworkIdx + ", homeworkTitle=" + homeworkTitle + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", homeworkDetail=" + homeworkDetail + ", memberIdx=" + memberIdx + "]";
	}
	
}
