package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {

		HttpSession session=req.getSession();

		//customerの中に何かが格納されている場合(ログインされている場合)
			//customerの中身を空にする
			session.removeAttribute("user");
			req.getRequestDispatcher("logout.jsp").forward(req, res);

	}
}