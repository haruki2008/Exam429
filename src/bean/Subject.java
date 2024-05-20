package bean;

import java.io.Serializable;

public class Subject extends Teacher implements Serializable{



	//科目ID
	private String cd;

	//科目名
	private String name;


	//学校
	private School school;


	// ゲッター、セッター


	public String getCd() {
		return cd;
	}

	public void setCd(String subjectCd) {
		this.cd = subjectCd;
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
