package finalprojectNew.controller;

import finalprojectNew.business.entities.Candidate;
import finalprojectNew.business.entities.CandidateApplication;
import finalprojectNew.business.entities.Employer;
import finalprojectNew.business.entities.Job;
import finalprojectNew.business.repository.CandidateApplicationRepository;
import finalprojectNew.business.repository.CandidateRepository;
import finalprojectNew.business.repository.EmployerRepository;
import finalprojectNew.business.repository.JobRepository;
import finalprojectNew.util.URLMapper;
import finalprojectNew.util.ViewMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class EmployerController {

	static final String POST_JOB_MODEL = "postJob";
	static final String UPDATE_JOB_MODEL = "updateJob";

	@Autowired
	CandidateApplicationRepository candidateApplicationRepository;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	CandidateRepository candidateRepository;

	@RequestMapping(value = URLMapper.EMPLOYER_JOB_LISTING_URL, method = RequestMethod.GET)
	public String loadEmployerJobs(Model model, Authentication auth) {
		Employer employer = employerRepository.findByUsername(auth.getName());
//		System.out.println(auth.getName()+"iii");
		model.addAttribute("jobs", jobRepository.findByEmployerId(employer.getId()));
		model.addAttribute("employer",employer);
		return ViewMapper.EMPLOYER_JOB_LISTING;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_POST_JOB_URL, method = RequestMethod.GET)
	public String loadPostJobPage(Model model, Authentication auth) {
		Employer employer = employerRepository.findByUsername(auth.getName());
		model.addAttribute(POST_JOB_MODEL, new Job());
		model.addAttribute("employer_post_job_url", URLMapper.EMPLOYER_POST_JOB_URL);
		model.addAttribute("employer",employer);
		return ViewMapper.EMPLOYER_POST_JOB;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_POST_JOB_URL, method = RequestMethod.POST)
	public String postJob(@Valid @ModelAttribute(POST_JOB_MODEL) Job job, BindingResult result, Model model,
						  HttpServletRequest request, Authentication auth) {

		// validate the job entry
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			// the errors will automatically get mapped to form:errors path = ""
			model.addAttribute("employer_post_job_url", URLMapper.EMPLOYER_POST_JOB_URL);
			return ViewMapper.EMPLOYER_POST_JOB;
		}

		Employer employer = employerRepository.findByUsername(auth.getName());
//		System.out.println(auth.getName()+"iii");
		model.addAttribute("jobs", jobRepository.findByEmployerId(employer.getId()));
		model.addAttribute("employer",employer);
		String name = job.getName();
		String description = job.getDescription();
		String experience = job.getExperience();
		String location = job.getLocation();

		// Hardcoded right now. Change Later. based on login
		job.setEmployer(employerRepository.getOne(employer.getId()));
		job.setPostedOn(new Date());
		job.setDeleted(false);
		job.setName(name);
		job.setDescription(description);
		job.setExperience(experience);
		job.setStatus("active");
		job.setLocation(location);
		String type = request.getParameter("jobType");
		job.setType(type);
		System.out.println(job);

		// saving job
		jobRepository.save(job);

		return "redirect:" + URLMapper.EMPLOYER_JOB_LISTING_URL;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_UPDATE_APPLICATION_STATUS)
	public String updateStatus(
			@RequestParam("jobId") Long jobId,
			@RequestParam("candidateId") Long candidateId,
			@RequestParam("status") String status,
			Authentication auth,
			Model model
	) {
		CandidateApplication candidateApplication = candidateApplicationRepository.
				findByJobIdAndCandidateId(jobId, candidateId);

		Employer employer = employerRepository.findByUsername(auth.getName());
		candidateApplication.setApplicationStatus(status);
		candidateApplication.setEmployerActionOn(new Date());

		candidateApplicationRepository.save(candidateApplication);
		model.addAttribute("employer",employer);


		return "redirect:" + URLMapper.EMPLOYER_VIEW_JOB_RESPONSES + "?jobId=" + jobId;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_UPDATE_JOB_URL, method = RequestMethod.GET)
	public String loadUpdateJobPage(Model model, @RequestParam(value = "jId") String jobId, Authentication auth) {
		Job job = jobRepository.getOne(Long.parseLong(jobId));
		Employer employer = employerRepository.findByUsername(auth.getName());


		model.addAttribute(UPDATE_JOB_MODEL, job);
		model.addAttribute("employer_update_job_url", URLMapper.EMPLOYER_UPDATE_JOB_URL);
		model.addAttribute("employer",employer);

		return ViewMapper.EMPLOYER_UPDATE_JOB;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_UPDATE_JOB_URL, method = RequestMethod.POST)
	public String postJob(@Valid @ModelAttribute(UPDATE_JOB_MODEL) Job job, BindingResult result, Model model, HttpServletRequest request) {

		// validate the job entry
		if (result.hasErrors()) {
			// the errors will automatically get mapped to form:errors path = ""
			model.addAttribute("employer_update_job_url", URLMapper.EMPLOYER_UPDATE_JOB_URL);
			return ViewMapper.EMPLOYER_UPDATE_JOB;
		}
		// Hardcoded right now. Change Later. based on login

		try {
			Job newJob = jobRepository.getOne(job.getId());
			if (null == newJob) {
				return ViewMapper.EMPLOYER_UPDATE_JOB;
			}
			String name = job.getName();
			String description = job.getDescription();
			String experience = job.getExperience();
			String location = job.getLocation();


			newJob.setName(name);
			newJob.setDescription(description);
			newJob.setExperience(experience);
			newJob.setLocation(location);
			String type = request.getParameter("jobType");
			// System.out.println(type);
			newJob.setType(type);

			newJob.setUpdatedOn(new Date());

			// updating job
			jobRepository.save(newJob);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:" + URLMapper.EMPLOYER_JOB_LISTING_URL;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_DELETE_JOB_URL, method = RequestMethod.GET)
	public String deleteJobPage(Model model, @RequestParam(value = "jId") String jobId, Authentication auth) {
		Job job = jobRepository.getOne(Long.parseLong(jobId));
		Employer employer = employerRepository.findByUsername(auth.getName());

		job.setUpdatedOn(new Date());
		job.setStatus("deactive");
		job.setDeleted(true);

		jobRepository.save(job);
		model.addAttribute("employer",employer);

		return "redirect:" + URLMapper.EMPLOYER_JOB_LISTING_URL;
	}

	@RequestMapping(value = URLMapper.EMPLOYER_VIEW_JOB_RESPONSES)
	public String loadResponses(@RequestParam(value = "jobId", required = true) Long jobId, Model model,
								Authentication auth) {
		List<Candidate> appliedCandidates = null;
		Employer employer = employerRepository.findByUsername(auth.getName());

		List<CandidateApplication> appliedCandidateIds = candidateApplicationRepository.findByJobId(jobId);
		if (CollectionUtils.isNotEmpty(appliedCandidateIds)) {
			appliedCandidates = new ArrayList<>();

			for (CandidateApplication candidateApp : appliedCandidateIds) {
				Candidate candidate = candidateRepository.getOne(candidateApp.getCandidate().getId());
				candidate.setApplicationStatus(candidateApp.getApplicationStatus());

				appliedCandidates.add(candidate);
			}
		}
		model.addAttribute("appliedCandidates", appliedCandidates);
		model.addAttribute("jobId", jobId);
		model.addAttribute("employer",employer);

		return ViewMapper.EMPLOYER_VIEW_JOB_RESPONSES;
	}
}
