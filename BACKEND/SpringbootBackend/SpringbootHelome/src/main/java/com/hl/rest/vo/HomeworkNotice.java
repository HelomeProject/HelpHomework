package com.hl.rest.vo;

import javax.validation.constraints.NotNull;

/** 숙제 공지생성 */
public class HomeworkNotice {
	@NotNull private String HomeworkNotice_idx;
	private String HomeworkNotice_memberIdx;
	@NotNull private String HomeworkNotice_startDate;
	@NotNull private String HomeworkNotice_endDate;
	@NotNull private String HomeworkNotice_detail;
	
	public HomeworkNotice() {}
	public HomeworkNotice(@NotNull String homeworkNotice_idx, String homeworkNotice_memberIdx,
			@NotNull String homeworkNotice_startDate, @NotNull String homeworkNotice_endDate,
			@NotNull String homeworkNotice_detail) {
		HomeworkNotice_idx = homeworkNotice_idx;
		HomeworkNotice_memberIdx = homeworkNotice_memberIdx;
		HomeworkNotice_startDate = homeworkNotice_startDate;
		HomeworkNotice_endDate = homeworkNotice_endDate;
		HomeworkNotice_detail = homeworkNotice_detail;
	}

	public String getHomeworkNotice_memberIdx() {
		return HomeworkNotice_memberIdx;
	}
	public void setHomeworkNotice_memberIdx(String homeworkNotice_memberIdx) {
		HomeworkNotice_memberIdx = homeworkNotice_memberIdx;
	}
	public String getHomeworkNotice_idx() {
		return HomeworkNotice_idx;
	}
	public void setHomeworkNotice_idx(String homeworkNotice_idx) {
		HomeworkNotice_idx = homeworkNotice_idx;
	}
	public String getHomeworkNotice_startDate() {
		return HomeworkNotice_startDate;
	}
	public void setHomeworkNotice_startDate(String homeworkNotice_startDate) {
		HomeworkNotice_startDate = homeworkNotice_startDate;
	}
	public String getHomeworkNotice_endDate() {
		return HomeworkNotice_endDate;
	}
	public void setHomeworkNotice_endDate(String homeworkNotice_endDate) {
		HomeworkNotice_endDate = homeworkNotice_endDate;
	}
	public String getHomeworkNotice_detail() {
		return HomeworkNotice_detail;
	}
	public void setHomeworkNotice_detail(String homeworkNotice_detail) {
		HomeworkNotice_detail = homeworkNotice_detail;
	}
	@Override
	public String toString() {
		return "HomeworkNotice [HomeworkNotice_idx=" + HomeworkNotice_idx + ", HomeworkNotice_memberIdx="
				+ HomeworkNotice_memberIdx + ", HomeworkNotice_startDate=" + HomeworkNotice_startDate
				+ ", HomeworkNotice_endDate=" + HomeworkNotice_endDate + ", HomeworkNotice_detail="
				+ HomeworkNotice_detail + "]";
	}
}
