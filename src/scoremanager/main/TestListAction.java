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
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		//セッションからユーザデータを取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subStr = "";//入力された科目ID
		List<Subject> sublist = null;//プルダウンで科目名を表示するためのリスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		int entYear = 0;// 入学年度
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao SubDao = new SubjectDao();
		SubjectDao subDao = new SubjectDao();
		Subject subject = new Subject();
		School school = new School();
		SchoolDao sDao = new SchoolDao();
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータの取得
		subStr = req.getParameter("sub_cd");//例：A01
		entYearStr = req.getParameter("ent_year");//入力された入学年度
		classNum = req.getParameter("class_num");


		if (entYearStr != null){
			entYear = Integer.parseInt(entYearStr);//入学年度
			}
		if (entYear != 0 && classNum != "" ) {
			//SubjectDaoのfilterメソッドを使ってJSPから引っ張ってきたデータをもとにSubject型の変数を取得
			subject = subDao.get(subStr, subject.getSchool());
			//ログイン中の先生の所属校コードからSchool型のインスタンスを取得
			school = sDao.get(teacher.getSchool().getCd());
			//入学年度、クラス番号、科目名をもとに成績一覧を作成（入学年度、クラス、学生番号、氏名、回数）
			TestListSubjectDao TLSDao = new TestListSubjectDao();
			List<TestListSubject> list = TLSDao.filter(entYear,classNum,subject,school);

			// リクエストに成績リストをセット
			req.setAttribute("scores",list);
			//選択された科目を表示するためリクエストに科目リストをセット
			req.setAttribute("sub", subject);
			} else {

			}
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classlist = cNumDao.filter(teacher.getSchool());

		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}

		//ユーザが所属している学校の科目データ一覧を取得
		sublist = SubDao.filter(teacher.getSchool());
		//クラスデータをリクエスト属性にセット
		req.setAttribute("class_num_set", classlist);
		req.setAttribute("ent_year_set", entYearSet);
		//科目データをリクエスト属性にセット
		req.setAttribute("subject_set", sublist);
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
