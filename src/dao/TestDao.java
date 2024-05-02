package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao{

	private String baseSql = "select * from test where ";

	//get(学生番号、科目、学校、何回目のテストか）
	//get(2374475,数学,knz,1) --> この条件に当てはまるtestのデータを持ってくる
	public Test get(Student student, Subject subject, School school, int no) throws Exception{

		//学生インスタンスを初期化
		Test test = new Test();
		//データエースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + "student_no=? and subject_cd=? and school_cd=? and no=? ");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//返す用のデータを格納している
			if (rSet.next()) {
				//リザルトセットが存在する場合
				//テストインスタンスに検索結果をセット
				test.setStudent(student);
				test.setSchool(school);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));
				test.setSubject(subject);

			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				test = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//
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
		//testを返す
		return test;

	}

	//ResultSet型をtest型に変換するメソッド
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception{
		//戻り値用のリスト
		List<Test> list = new ArrayList<>();
		try{
			while (rSet.next()) {
				Test test = new Test();
				StudentDao  studentDao= new StudentDao();
				SubjectDao  subjectDao= new SubjectDao();

				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setPoint(rSet.getInt("point"));
				test.setNo(rSet.getInt("no"));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	//Cでの絞り込み処理のメソッド
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception{
		//リストを初期化
	    List<Test> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;
	    //SQL文の条件
	    String condition = " ent_year =? and test.class_num =? and subject_cd =? and test.no =? and test.School_cd =?";
	    //SQL文のソートー
	    String order = " order by no asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement ("select test.* from test inner join student on student.no = test.student_no where"
		    											+ condition + order);
		    //プリペアードステートメントに入学年度をバインド
		    statement. setInt (1, entYear) ;
		  //プリペアードステートメントにクラス番号をバインド
		    statement. setString(2, classNum) ;
		    //プリペアードステートメントに科目コードをバインド
		    statement. setString(3, subject. getCd ());
		    statement. setInt(4, num);
		    statement. setString(5, school. getCd ());


		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    //帰ってきたResultSet型をtest型に変えて結果をセットする
		    list = postFilter(rSet,school);
		} catch (Exception e) {
			throw e;
		} finally {
			//
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
	}

	public boolean save(List<Test> list){
		return false;

	}

	public boolean save(Test test, Connection connection){
		return false;

	}

	public boolean delete(List<Test> list){
		return false;

	}

	public boolean delete(Test test, Connection connection){
		return false;

	}
}
