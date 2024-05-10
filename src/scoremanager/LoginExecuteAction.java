package scoremanager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String url = "";

		Teacher teacher = new Teacher();
		TeacherDao tDao = new TeacherDao();

		//リクエストパラメータ―の取得 2
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		//DBからデータ取得 3
		teacher = tDao.login(id,password);

		//ビジネスロジック 4



		if (teacher != null) {
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			//Sessionを有効にする
			HttpSession session = req.getSession(true);
			//セッションに"user"という変数名で値はTeacher変数の中身
			session.setAttribute("user", teacher);
			//リダイレクト
			url = "main/Menu.action";
			res.sendRedirect(url);
		} else {
			Map<String, String> errors = new HashMap<>();// エラーメッセージ
			errors.put("1", "ログインに失敗しました。IDまたはPasswardが違います。");
			req.setAttribute("errors", errors);
			url = "login.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}


		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		//なし
		//JSPへフォワード 7
		//req.getRequestDispatcher("main/Menu.action").forward(req, res);


	}

}
