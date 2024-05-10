package bean;

import java.io.Serializable;

public class Subject extends Teacher implements Serializable{



	//科目ID
	private String subjectCd;

	//科目名
	private String name;


	private School school;


	// ゲッター、セッター


	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public School getSchool() {
		return school;
	}
}
