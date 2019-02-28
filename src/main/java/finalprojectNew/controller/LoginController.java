package finalprojectNew.controller;

import finalprojectNew.business.entities.AppUser;
import finalprojectNew.business.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	ModelAndView mv;

	@Autowired
	AppUserRepository appUserRepository;


	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		AppUser user = appUserRepository.findByUsernameIgnoreCase(username);

		session.setAttribute("user", user);

		if (user != null && user.getPassword().equals(password)) {

			if (user.getRole().equalsIgnoreCase("employer")) {
		 		//employer login
				mv = new ModelAndView("employerDashboard", "employer", user);
		 	} else if (user.getRole().equalsIgnoreCase("candidate")) {
		 		//candidate login
				mv = new ModelAndView("candidateDashboard", "candidate", user);
		 	} else {
		 		//admin login
				mv = new ModelAndView("adminDashboard", "admin", user);
		 	}

		 } else { mv = new ModelAndView("loginpage", "status", true); }

		 return mv;
	}

	 @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	 public String initializeForm(@ModelAttribute("user") User user, BindingResult result) {
	 	return "loginpage";
	 }

	/*@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	 protected ModelAndView handleRequestInternalLogout(HttpServletRequest request, HttpServletResponse response)
			 throws Exception { HttpSession session = request.getSession(); session.invalidate();

			 mv = new ModelAndView("loginpage");

			 return mv;
	 }*/
}
