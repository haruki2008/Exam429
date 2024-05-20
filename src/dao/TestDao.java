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
			statement.setString(2, subject.getSubjectCd());
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
				test.setSubject(subjectDao.get3(rSet.getString("subject_cd"), school));
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
	    String condition = "WHERE ent_year = ? AND student.class_num = ? AND student.school_cd = ? ";
	    //SQL文のソートー
	    String order = "order by student_no asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement ("SELECT ent_year, student.class_num, student.no AS student_no, COALESCE(subject_cd, ?) AS subject_cd, COALESCE(test.no, ?) AS no, COALESCE(point, -1) AS point "
		    										+ "FROM student LEFT OUTER JOIN test ON test.student_no = student.no AND test.school_cd = student.school_cd AND test.subject_cd = ? AND test.no = ? "
		    											+ condition + order);

		    //プリペアードステートメントに入学年度をバインド
		    statement. setString(1, subject.getSubjectCd()) ;
		  //プリペアードステートメントにクラス番号をバインド
		    statement. setInt(2, num);
		    //プリペアードステートメントに科目コードをバインド
		    statement. setString(3, subject. getSubjectCd ());
		    statement. setInt(4, num);
		    statement. setInt(5, entYear);
		    statement. setString(6, classNum);
		    statement. setString(7, school. getCd ());


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

	public boolean save(List<Test> list) throws Exception{



		int count = 0;

			for (Test test:list){
				//データベースへのコネクションを確立
				Connection connection = getConnection();
				//値がカウントされたら失敗する
				try{
				boolean bool = save(test, connection);
				if(bool != true){
					count++;
					}
				}
				catch (Exception e) {
					throw e;
				} finally {

					if(connection != null) {
						try {
							connection.close();
						} catch (SQLException sqle) {
							throw sqle;
					}
				}
			}
		}
		if (count > 0){
			return true;
		}
		else{
			return false;
		}

	}

	private boolean save(Test test, Connection connection) throws Exception{
	    //プリペアードステートメント
	    PreparedStatement statement = null;

	    //実行件数
	    int count = 0;

	    try{
				//データベースから学生を取得
				Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
				if (old == null) {
					//学生が存在しなかった場合
					//プリペアードステートメンにINSERT文をセット
					statement = connection.prepareStatement(
							"insert into test (student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?) ");
					//プリペアードステートメントに値をバインド
					statement.setString(1, test.getStudent().getNo());
					statement.setString(2, test.getSubject().getSubjectCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());
					statement.setInt(5, test.getPoint());
					statement.setString(6, test.getClassNum());
				} else {
					//学生が存在した場合
					//プリペアードステートメントにUPDATE文をセット
					statement = connection
							.prepareStatement("update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=?");
					//プリペアードステートメントに値をバインド
					statement.setInt(1, test.getPoint());
					statement.setString(2, test.getStudent().getNo());
					statement.setString(3, test.getSubject().getSubjectCd());
					statement.setString(4, test.getSchool().getCd());
					statement.setInt(5, test.getNo());
				}
	        //プリペアードステートメントを実行
	        count = statement. executeUpdate ();

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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	        } else {
	        //実行件数が0件の場合
	        return false;
	        }

	}

	public boolean delete(List<Test> list) throws Exception{

		int count = 0;

		for (Test test:list){
			//データベースへのコネクションを確立
			Connection connection = getConnection();
			//値がカウントされたら失敗する
			try{
			boolean bool = delete(test, connection);
			if(bool != true){
				count++;
				}
			}
			catch (Exception e) {
				throw e;
			} finally {

				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
			}
		}
		if (count > 0){
			return true;
		}
		else{
			return false;
		}
	}

	private boolean delete(Test test, Connection connection) throws Exception{
	    //プリペアードステートメント
	    PreparedStatement statement = null;

	    //実行件数
	    int count = 0;

	    try{
				//データベースから学生を取得
				Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
				if (old != null) {
					//テストが存在しなかった場合
					//プリペアードステートメンにINSERT文をセット
					statement = connection.prepareStatement(
							"delete from test where student_no=? and subject_cd=? and school_cd=? and no=?");
					//プリペアードステートメントに値をバインド
					statement.setString(1, test.getStudent().getNo());
					statement.setString(2, test.getSubject().getSubjectCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());
				}
	        //プリペアードステートメントを実行
	        count = statement. executeUpdate ();

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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	        } else {
	        //実行件数が0件の場合
	        return false;
	        }

	}
}
