package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession(true);// セッションを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		SubjectDao sDao = new SubjectDao();
		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subjectStr = "";//入力された科目
		String num = "";//入力された回数
		int entYear = 0;// 入学年度
		int no = 0;// 回数
		List<Test> test=null;
		School school = new School();
		Subject subject = null;//科目
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		Subject sub = new Subject();
		TestDao tDao = new TestDao();
		SchoolDao scDao = new SchoolDao();
		Map<String, String> errors = new HashMap<>();// エラーメッセージ



		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		num = req.getParameter("f4");

		System.out.println(entYearStr + classNum + subjectStr + num);

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとに科目番号の一覧を取得
		List<Subject> subject_list = sDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		subject = sDao.get(subjectStr,teacher.getSchool());


		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		//回数が送信されていた場合
		if (num != null){
			//数値に変換
			no = Integer.parseInt(num);
		}

		if (entYear != 0 && classNum != "0" && subjectStr != "0" && no != 0) {

			school = scDao.get(teacher.getSchool().getCd());

			test = tDao.filter(entYear, classNum, subject, no, school);

			req.setAttribute("tests",test);

		}


		//回数が送信されていた場合
		if (num != null){
			//数値に変換
			no = Integer.parseInt(num);
		}
		// リストを初期化
		List<Integer> no_list = new ArrayList<>();
		// 1～２までの回数をリストに追加
		for (int i = 1; i < 3; i++) {
			no_list.add(i);
		}

		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//DBへデータ保存 5

		if (entYear == 0 || classNum == "0" || subjectStr == "0" || no == 0 ){
			errors.put("f1", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}



		//レスポンス値をセット 6

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectStr);
		req.setAttribute("f4", no);

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", list);
		req.setAttribute("subject_set", subject_list);
		req.setAttribute("no_set", no_list);

		req.setAttribute("subject", subject);

		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

//	private void setRequestData(HttpServletRequest req, HttpServletResponse res) throws Exception {
//
//	}

}
