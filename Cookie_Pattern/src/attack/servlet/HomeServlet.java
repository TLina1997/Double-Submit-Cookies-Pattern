package attack.servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet{
	
	  private static final long serialVersionUID = 1L;

	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
	    response.getWriter().append("Served at: ").append(request.getContextPath());
	  }

	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {

	    String hiddenToken = request.getParameter("token");

	    Optional<String> cookieValue = Stream.of(request.getCookies()).filter(c -> c.getName().equalsIgnoreCase("CSRF_TOKEN")).map(Cookie::getValue).findFirst();

	    String csrfToken = cookieValue.get();

	    if (csrfToken.equals(hiddenToken))
	    {
	      response.getWriter().append("User token verified..! (Hiddden token from JS: "+hiddenToken+" vs CSRF Token: "+csrfToken+" and the Cookie value is: "+cookieValue.toString()+")");
	    }
	    else
	    {
	      response.getWriter().append("Invalid token. Verification failed (Hidden token from JS: "+hiddenToken+" vs CSRF Token: "+csrfToken+" and the Cookie value is: "+cookieValue.toString()+")");
	    }
	  }

}
