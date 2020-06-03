package com.hl.rest.vo;

public class Notice {
	private String idx, noticeTitle, noticeImgUrl, memberIdx, memberGrade, memberClassNum;
	public Notice(){}
	public Notice(String idx, String noticeTitle, String noticeImgUrl, String memberIdx, String memberGrade,
			String memberClassNum) {
		this.idx = idx;
		this.noticeTitle = noticeTitle;
		this.noticeImgUrl = noticeImgUrl;
		this.memberIdx = memberIdx;
		this.memberGrade = memberGrade;
		this.memberClassNum = memberClassNum;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeImgUrl() {
		return noticeImgUrl;
	}
	public void setNoticeImgUrl(String noticeImgUrl) {
		this.noticeImgUrl = noticeImgUrl;
	}
	public String getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(String memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	public String getMemberClassNum() {
		return memberClassNum;
	}
	public void setMemberClassNum(String memberClassNum) {
		this.memberClassNum = memberClassNum;
	}
	@Override
	public String toString() {
		return "Notice [idx=" + idx + ", noticeTitle=" + noticeTitle + ", noticeImgUrl=" + noticeImgUrl + ", memberIdx="
				+ memberIdx + ", memberGrade=" + memberGrade + ", memberClassNum=" + memberClassNum + "]";
	}
}
