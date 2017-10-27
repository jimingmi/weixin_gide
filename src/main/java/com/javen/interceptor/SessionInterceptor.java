package com.javen.interceptor;

import javax.servlet.http.HttpSession;
import com.javen.model.Login;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SessionInterceptor implements Interceptor{

	@Override
	public void intercept( Invocation inv ) {
		HttpSession session = inv.getController().getSession();
		if (session == null) {
			inv.getController().redirect("/");
		}
		Login login = (Login)session.getAttribute("session_user");
		if (login != null) {
			inv.invoke();
		} else {
			inv.getController().redirect("/");
		}
	}
}
