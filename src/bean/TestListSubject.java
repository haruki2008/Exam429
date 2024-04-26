package bean;

import java.util.Map;

public class TestListSubject {
	//変数宣言
	private int entYear;//入学年度
	private String studentNo;//学生番号
	private String studentName;//氏名
	private String classNum;//クラス番号
	private Map<Integer,Integer> points;//点数
	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getstudentNo() {
		return studentNo;
	}
	public void setstudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	//ゲッター、セッター


}
