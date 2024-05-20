package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();// 学生Dao

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得

		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		//DBからデータ取得 3

		Subject subject = sDao.get(cd,teacher.getSchool());// 科目IDから科目インスタンスを取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		if (subject != null) {
			// 科目が存在していた場合
			// インスタンスに値をセット

				subject.setName(name);
				subject.setCd(cd);
				// 科目を削除
				sDao.delete(subject, req, res);

		} else {
			errors.put("cd", "科目が存在していません");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("name", name);
			req.setAttribute("cd", cd);
			req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
