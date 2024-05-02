package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション

		String cd = "";//科目ID
		String name = "";//科目名

		Subject subject = null;// 科目ID
		Subject Name = null;// 科目名
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得


		//リクエストパラメータ―の取得 2

		cd = req.getParameter("cd");//科目ID
		name = req.getParameter("name");//科目名

		//DBからデータ取得 3
		subject = sDao.get(cd);// 科目IDから科目インスタンスを取得
		Name = sDao.get2(name);


		// ログインユーザーの学校コードをもとに科目IDの一覧を取得
		List<String> list = sDao.filter2(teacher.getSchool());



		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐

		if (subject == null) {// 新規の科目ID

			if(Name != null){
				errors.put("name", "科目名が重複しています");
			} else {

			// 科目インスタンスを初期化
			subject = new Subject();
			// インスタンスに値をセット
			subject.setSubjectCd(cd);
			subject.setName(name);

			subject.setSchool(((Teacher)session.getAttribute("user")).getSchool());
			// 科目を保存
			sDao.save(subject);
			}
		} else if(subject != null) {//入力された科目IDがDBに保存されていた場合
			errors.put("cd", "科目IDが重複しています");
		}


		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("subject_id_set", list);//科目IDのlistをセット


		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);

			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
