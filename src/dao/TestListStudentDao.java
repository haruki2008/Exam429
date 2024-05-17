package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;


public class TestListStudentDao extends Dao{

	//メソッド
	public List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		//戻り値のためのリスト
				List<TestListStudent> list = new ArrayList<>();
				try{
					while(rSet.next()) {
						//インスタンス化
						TestListStudent testliststudent = new TestListStudent();
						//学生別成績参照リストにセット
						testliststudent.setSubjectName(rSet.getString("name"));
						testliststudent.setSubjectCd(rSet.getString("subject_cd"));
						testliststudent.setNum(rSet.getInt("no"));
						testliststudent.setPoint(rSet.getInt("point"));
						//リストに追加
						list.add(testliststudent);
					}
				} catch (SQLException | NullPointerException e) {
					e.printStackTrace();
				}
				return list;
	}

	private String baseSql = "SELECT SUBJECT.NAME,TEST.SUBJECT_CD,TEST.NO,TEST.POINT "
			+ "FROM SUBJECT JOIN TEST "
			+ "ON SUBJECT.CD = TEST.SUBJECT_CD ";
	public List<TestListStudent> filter(Student student) throws Exception {
		//リストを初期化
	    List<TestListStudent> list = new ArrayList<>();
	    if (student != null) {
		    	//コネクションを確立
			    Connection connection = getConnection();
			    //プリペアードステートメント
			    PreparedStatement statement = null;
			    //リザルトセット
			    ResultSet rSet = null;
			  //SQL文の条件
			    String condition = "WHERE SUBJECT.SCHOOL_CD = ? AND TEST.STUDENT_NO = ?";
			  //SQL文のソート
			    String order = " order by no asc";
			    try {
			    	//プリペアードステートメントにSQL文をセット
				    statement = connection. prepareStatement (baseSql + condition + order);
				    //プリペアードステートメントに学校コードをバインド
				    statement. setString(1,student.getSchool().getCd());
				    // プリペアードステートメントに学生番号をバインド
				    statement.setString(2,student.getNo());
				    //プライベートステートメントを実行
				    rSet = statement.executeQuery ();
				    //帰ってきたResultSet型を型に変えて結果をセットする
				    list = postFilter(rSet);
				} catch (Exception e) {
					throw e;
				} finally {

					if(statement != null) {
						try {
							statement.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}

					if(connection != null) {
						try {
							connection.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}
				}
				//listを返す
				return list;
	    	} else {
	    		list = null;
	    		return list;
	    	}

	}
}
