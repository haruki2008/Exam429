package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{


	//型をTestListSubject型に変換するメソッド
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//戻り値のためのリスト
		List<TestListSubject> list = new ArrayList<>();
		try{
			while(rSet.next()) {
				//インスタンス化
				TestListSubject testlistsubject = new TestListSubject();
				//学生インスタンスに検索結果をセット
				testlistsubject.setClassNum(rSet.getString("class_num"));
				testlistsubject.setEntYear(rSet. getInt("ent_year"));
				testlistsubject.setstudentNo(rSet.getString("student_no"));
				testlistsubject.setStudentName(rSet. getString("name"));
				testlistsubject.putPoints(rSet.getInt("no"), rSet.getInt("point"));
				//pointをセット
				//リストに追加
				list.add(testlistsubject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
	//共通部分のSQL文
	private String baseSql = "SELECT ENT_YEAR,TEST.CLASS_NUM,STUDENT_NO,NAME,TEST.NO,POINT "
			+ "FROM TEST INNER JOIN STUDENT "
			+ "ON TEST.STUDENT_NO = STUDENT.NO ";

	public List<TestListSubject> filter(int entYear,String classNum,Subject subject,School school) throws Exception {
		//リストを初期化
	    List<TestListSubject> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;
	    //SQL文の条件
	    String condition = "WHERE STUDENT.ENT_YEAR = ? AND TEST.CLASS_NUM = ? AND TEST.SUBJECT_CD = ? AND TEST.SCHOOL_CD = ?";
	    //SQL文のソート
	    String order = " order by no asc";

	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql + condition + order);
		    //プリペアードステートメントに入学年度をバインド
		    statement. setInt(1, entYear);		    // プリペアードステートメントにクラス番号をバインド
		    statement. setString (2, classNum) ;
		    //プリペアードステートメントに科目名をバインド
		    statement. setString(3, subject.getCd()) ;
		    //プリペアードステートメントに回数をバインド
		    statement. setString(4, school.getCd());
		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    //帰ってきたResultSet型を型に変えて結果をセットする
		    list = postFilter(rSet);
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
}
