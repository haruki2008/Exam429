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

public class SubjectUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();// 学生Dao

		Subject Name = null;

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Name = sDao.get2(name);


		//DBからデータ取得 3
		Subject subject = sDao.get(cd);// 科目IDから科目インスタンスを取得
		List<String> list = sDao.filter2(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		if (subject != null) {
			// 科目が存在していた場合
			// インスタンスに値をセット
			if(Name == null){
				errors.put("name","その科目名は登録済みです");
			} else {
				subject.setName(name);
				subject.setSubjectCd(cd);
				// 学生を保存
				sDao.save(subject);
			}
		} else {
			errors.put("cd", "科目が存在していません");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("class_num_set", list);

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("name", name);
			req.setAttribute("cd", cd);
			req.getRequestDispatcher("student_update.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
