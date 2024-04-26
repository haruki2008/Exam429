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
	public Subject get(String cd, School school) throws Exception{
		//処理
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
				subject.setCd(rSet.getString("name"));
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