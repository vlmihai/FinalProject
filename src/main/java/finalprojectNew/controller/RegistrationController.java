package finalprojectNew.controller;

import finalprojectNew.business.entities.AppUser;
import finalprojectNew.business.entities.Candidate;
import finalprojectNew.business.entities.Employer;
import finalprojectNew.business.repository.AppUserRepository;
import finalprojectNew.business.repository.CandidateRepository;
import finalprojectNew.business.repository.EmployerRepository;
import finalprojectNew.business.request.UserRegistrationRequest;
import finalprojectNew.util.Role;
import finalprojectNew.util.URLMapper;
import finalprojectNew.util.ViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
@RequestMapping(URLMapper.REGISTRATION)
public class RegistrationController {

	public static final String REGISTRATION_MODEL = "registration";

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	CandidateRepository candidateRepository;

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;


	@RequestMapping(method = RequestMethod.GET)
	public String loadRegistrationPage(Model model) {
		model.addAttribute(REGISTRATION_MODEL, new UserRegistrationRequest());
		model.addAttribute("registration_url", URLMapper.REGISTRATION);
		return ViewMapper.REGISTRATION;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String userRegistration(
			@Valid @ModelAttribute(REGISTRATION_MODEL) UserRegistrationRequest userRegistrationRequest,
			BindingResult result) {

		String name = userRegistrationRequest.getName();
		String email = userRegistrationRequest.getEmail();
		String address = userRegistrationRequest.getAddress();
		String username = userRegistrationRequest.getUsername();

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return ViewMapper.REGISTRATION;
		}

		if (Role.employer.name().equalsIgnoreCase(userRegistrationRequest.getRole())) {

			employerRepository.save(
					new Employer(name, email, address, username));
		}

		if (Role.candidate.name().equalsIgnoreCase(userRegistrationRequest.getRole())) {

			String linkedInUrl = userRegistrationRequest.getLinkedInUrl();
			String phoneNumber = userRegistrationRequest.getPhoneNumber();

			candidateRepository.save(
					new Candidate(name, address, email, linkedInUrl, phoneNumber, username));
		}

		appUserRepository.save(
				new AppUser(username,
						passwordEncoder.encode(userRegistrationRequest.getPassword()),
						userRegistrationRequest.getRole().toLowerCase(),
						true
				));

		return ViewMapper.LOGIN;
	}
}