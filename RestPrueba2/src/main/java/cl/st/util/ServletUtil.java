package cl.st.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class ServletUtil {

	/**
	 * @return
	 */
	public static final HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	/**
	 * @return
	 */
	public static final HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	/**
	 * @return
	 */
	public static final String getUserName() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			
			return session.getAttribute("username").toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return
	 */
	public static final String getUserId() {
		HttpSession session = getSession();
		if (session != null){
			return (String) session.getAttribute("userid");
		}else{
			return null;
		}
	}
	
	/**
	 * @return
	 */
	public static final String getUserPerfilId() {
		HttpSession session = getSession();
		if (session != null){
			return (String) session.getAttribute("userperfilid");
		}else{
			return null;
		}
	}
	public static final String getIdProceso() {
		HttpSession session = getSession();
		if (session != null){
			return (String) session.getAttribute("proc");
		}else{
			return null;
		}
	}
}
