package com.hl.rest.vo;

import javax.validation.constraints.NotNull;

public class Homework {
	private String Homework_idx, Homework_url, Homework_score, Homework_memberIdx, Homework_submitDate;
	@NotNull private String Homework_noticeIdx;
	
	public Homework() {}
	public Homework(String homework_idx, String homework_url, String homework_score, String homework_memberIdx,
			String homework_submitDate, @NotNull String homework_noticeIdx) {
		Homework_idx = homework_idx;
		Homework_url = homework_url;
		Homework_score = homework_score;
		Homework_memberIdx = homework_memberIdx;
		Homework_submitDate = homework_submitDate;
		Homework_noticeIdx = homework_noticeIdx;
	}
	
	public String getHomework_idx() {
		return Homework_idx;
	}
	public void setHomework_idx(String homework_idx) {
		Homework_idx = homework_idx;
	}
	public String getHomework_url() {
		return Homework_url;
	}
	public void setHomework_url(String homework_url) {
		Homework_url = homework_url;
	}
	public String getHomework_score() {
		return Homework_score;
	}
	public void setHomework_score(String homework_score) {
		Homework_score = homework_score;
	}
	public String getHomework_memberIdx() {
		return Homework_memberIdx;
	}
	public void setHomework_memberIdx(String homework_memberIdx) {
		Homework_memberIdx = homework_memberIdx;
	}
	public String getHomework_submitDate() {
		return Homework_submitDate;
	}
	public void setHomework_submitDate(String homework_submitDate) {
		Homework_submitDate = homework_submitDate;
	}
	public String getHomework_noticeIdx() {
		return Homework_noticeIdx;
	}
	public void setHomework_noticeIdx(String homework_noticeIdx) {
		Homework_noticeIdx = homework_noticeIdx;
	}
	@Override
	public String toString() {
		return "Homework [Homework_idx=" + Homework_idx + ", Homework_url=" + Homework_url + ", Homework_score="
				+ Homework_score + ", Homework_memberIdx=" + Homework_memberIdx + ", Homework_submitDate="
				+ Homework_submitDate + ", Homework_noticeIdx=" + Homework_noticeIdx + "]";
	}
}
