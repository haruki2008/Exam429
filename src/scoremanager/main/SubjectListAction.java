package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String entCd = "";//入力された科目ID

		String Cd = "";




		List<Subject> subjects = null;// 科目リスト

		SubjectDao sDao = new SubjectDao();//科目Dao

		//リクエストパラメータ―の取得 2
		entCd = req.getParameter("f1");


		//DBからデータ取得３

		if(entCd != null){
			Cd = entCd;
		}

		if(Cd.equals("0") || Cd.length() == 0) {
			// 全科目情報を取得
			subjects = sDao.filter(teacher.getSchool());
		}

		else if (!Cd.equals("0")) {
			// 科目IDを指定
			subjects = sDao.filter(teacher.getSchool(), entCd);
		}

		// ログインユーザーの学校コードをもとに科目IDの一覧を取得
		List<String> list = sDao.filter2(teacher.getSchool());

		List<String> nameList = sDao.filter3(teacher.getSchool());


		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6

		// リクエストに科目IDをセット
		req.setAttribute("f1", entCd);


		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);
		// リクエストにデータをセット
		req.setAttribute("set_sub_list", list);
		req.setAttribute("set_name_list", nameList);

		//JSPへフォワード 7

		req.getRequestDispatcher("subject_list.jsp").forward(req, res);

	}
}
