package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao extends Dao {
	public Teacher get(String id) throws Exception {
		//教員インスタンスを初期化
		Teacher teacher = new Teacher();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try{
		//プリペアードステートメントSQL文にセット
		statement = connection.prepareStatement("select * from teacher where id=?");
		//プリペアードステートメントに教員IDをバインド
		statement.setString(1, id);
		//プリペアードステートメントを実行
		ResultSet rSet=statement.executeQuery();

		//学校Daoを初期化
		SchoolDao SchoolDao = new SchoolDao();

		if (rSet.next()){
			//リザルトセットが存在する場合
			//教員インスタンスに検索結果をセット
			teacher.setName(rSet.getString("name"));
			// 学校フィールドには学校コードで検索した学校インスタンスをセット
			teacher.setSchool(SchoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
		        //学生インスタンスにnullをセット
				teacher = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(statement != null){
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
			return teacher;


	}
	public Teacher login(String id, String password) throws Exception {
		Teacher teacher=null;

		Connection con=getConnection();

		PreparedStatement ps;
		ps=con.prepareStatement("select * from teacher where id=? and password=?");
		ps.setString(1,id);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();

		//学校Daoを初期化
		SchoolDao SchoolDao = new SchoolDao();

		while(rs.next()){
			teacher= new Teacher();
			teacher.setId(rs.getString("id"));
			teacher.setPassword(rs.getString("password"));
			teacher.setName(rs.getString("name"));
			teacher.setSchool(SchoolDao.get(rs.getString("school_cd")));
		}

		ps.close();
		rs.close();
		return teacher;


	}
}