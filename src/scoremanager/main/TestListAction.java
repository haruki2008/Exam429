package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}
		//セッションからユーザデータを取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classlist = cNumDao.filter(teacher.getSchool());

		//ユーザが所属している学校の科目データ一覧を取得
		SubjectDao SubDao = new SubjectDao();
		List<Subject> sublist = SubDao.filter(teacher.getSchool());

		//Subject型の中の科目名をString型のリストに格納

		//科目データをリクエスト属性にセット
		req.setAttribute("subject_set", sublist);
		//クラスデータをリクエスト属性にセット
		req.setAttribute("class_num_set", classlist);
		//
		req.setAttribute("ent_year_set", entYearSet);

		//フォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);

	}
	//private void setTestListSubject(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//処理
	//}
	//private void setTestListStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//処理
	//}

}
