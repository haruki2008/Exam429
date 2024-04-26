package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{
	//Subject型の単体のインスタンスを取得するメソッド
	public Subject get(String cd, School school) throws Exception{
		//戻り値用
		Subject subject = new Subject();
		//コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement ("select * from subject where cd =? ");
		    //プリペアードステートメントに学生番号をバインド
		    statement. setString(1, cd) ;
		    // プリペアードステートメントを実行
		    ResultSet rSet = statement. executeQuery ();

		 // 学校Daoを初期化
		    SchoolDao schoolDao = new SchoolDao ();
		    if (rSet. next ()) {
		    //リザルトセットが存在する場合
		    subject.setCd(rSet.getString("cd"));
		    subject.setName(rSet.getString("name"));
		    subject.setSchool(schoolDao.get(rSet.getString ("school_cd")));
		    } else {
		        //リザルトセットが存在しない場合
		        //科目インスタンスにnullをセット
		        subject = null;
		        }
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
	    return subject;
	}
	public List<Subject> filter(School school) throws Exception {
		// 戻り値用のリストを作成Subject型
		List<Subject> list = new ArrayList<>();

		//DBと接続
		Connection connection = getConnection();

		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1,school.getCd());

			// 結果はリザルトセット型となる
			ResultSet rSet = statement.executeQuery();

			// リザルトセット（結果）を全件走査
			while (rSet.next()){
				//インスタンスを初期化
				Subject subject = new Subject();
				//科目名をセットする;
				subject.setName(rSet. getString("name"));
				subject.setCd(rSet.getString("cd"));
				subject.setSchool(school);
				//リストに追加
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
	public boolean save(Subject subject) throws Exception {
		//処理
	}
	public boolean delete(Subject subject) throws Exception {
		//処理
	}
}