package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SchoolDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{
	//private void setTestListSubject(HttpServletRequest req, HttpServletResponse res) throws Exception {
	//処理
//}
//private void setTestListStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
	//処理
//}
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		int entYear;//入力された入学年度
		entYear = Integer.parseInt(req.getParameter("ent_year"));//入学年度

		String classNum = "";//入力されたクラス番号
		classNum = req.getParameter("class_num");


		String subStr = "";//入力された科目
		subStr = req.getParameter("sub_name");//例：A01
		//sub_nameと一致するデータをDBから引っ張る
		SubjectDao subDao = new SubjectDao();
		Subject subject = new Subject();
		//SubjectDaoのfilterメソッドを使ってJSPから引っ張ってきたデータをもとにSubject型の変数を取得
		subject = subDao.get(subStr, subject.getSchool());

		School school = new School();
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		SchoolDao sDao = new SchoolDao();
		//ログイン中の先生の所属校コードからSchool型のインスタンスを取得
		school = sDao.get(teacher.getSchool().getCd());

		//入学年度、クラス番号、科目名をもとに成績一覧を作成（入学年度、クラス、学生番号、氏名、回数）
		TestListSubjectDao TLSDao = new TestListSubjectDao();
		List<TestListSubject> list = TLSDao.filter(entYear,classNum,subject,school);

	}
}
