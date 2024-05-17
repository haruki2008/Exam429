package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject {
	//変数宣言
	private int entYear;//入学年度
	private String studentNo;//学生番号
	private String studentName;//氏名
	private String classNum;//クラス番号
	private Map<Integer,Integer> points = new HashMap<>();
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
	public int getPoint(int key) {
		return points.get(key);
	}
	public void putPoints(int key,int value) {
		points.put(key, value);
	}

	//ゲッター、セッター
	public void removePoints(int key) {
		points.remove(key);
	}


}
