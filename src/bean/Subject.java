package bean;

<<<<<<< HEAD
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

=======
public class Subject {
	//変数宣言
	private String cd;
	private String name;
	private School school;

	//ゲッター、セッター
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
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
>>>>>>> branch 'master' of https://github.com/haruki2008/Exam429.git
	public void setSchool(School school) {
		this.school = school;
	}



}
