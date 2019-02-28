package finalprojectNew.controller;

import finalprojectNew.business.entities.Candidate;
import finalprojectNew.business.entities.CandidateApplication;
import finalprojectNew.business.entities.Job;
import finalprojectNew.business.repository.CandidateApplicationRepository;
import finalprojectNew.business.repository.CandidateRepository;
import finalprojectNew.business.repository.JobRepository;
import finalprojectNew.util.Status;
import finalprojectNew.util.URLMapper;
import finalprojectNew.util.ViewMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CandidateController {

	@Autowired
	JobRepository jobRepository;

	@Autowired
	CandidateApplicationRepository candidateApplicationRepository;

	@Autowired
	CandidateRepository candidateRepository;


	@RequestMapping(value = URLMapper.CANDIDATE_JOBS)
	public String candidateViewJobs(Model model, Authentication auth) {
		Candidate candidate = candidateRepository.findByUsername(auth.getName());

		/** Manipulation for Applied Jobs. Cant apply to Already Applied Jobs */
		List<Job> allActiveJobs = jobRepository.findByisDeleted(false);
		List<CandidateApplication> appliedJobs = candidateApplicationRepository.findByCandidateId(candidate.getId());

		if(CollectionUtils.isNotEmpty(appliedJobs)){
			Map<Long, String> jobIdStatusMap = new HashMap<>();
			for (CandidateApplication candidateApplication : appliedJobs) {
				jobIdStatusMap.put(candidateApplication.getJob().getId(), candidateApplication.getApplicationStatus());
			}

			for (Job candidateApplication : allActiveJobs) {
				candidateApplication.setCandidateApplicationStatus(jobIdStatusMap.get(candidateApplication.getId()));
			}
		}

		model.addAttribute("jobs", allActiveJobs);
		model.addAttribute("candidate", candidate);
		return ViewMapper.CANDIDATE_JOBS;
	}


	@RequestMapping(value = URLMapper.CANDIDATE_PROFILE)
	public String candidateProfile(Model model, Authentication auth) {
		Candidate candidate = candidateRepository.findByUsername(auth.getName());


		model.addAttribute("candidate", candidate);
		return ViewMapper.CANDIDATE_PROFILE;
	}



	@RequestMapping(value = URLMapper.CANDIDATE_APPLY_JOB)
	public String candidateApplyJobs(@RequestParam(value = "jobId", required = true) Long jobId, Authentication auth,
									 Model model) {
		Candidate candidate = candidateRepository.findByUsername(auth.getName());
		model.addAttribute("candidate", candidate);
		Job job = jobRepository.getOne(jobId);

		/** Updating the finalproject.Application Count */
		long previousApplicationCount = 0;

		if(job.getApplicationCount() != 0){
			previousApplicationCount = job.getApplicationCount();
		}

		long incrementedApplicationCount = previousApplicationCount + 1;
		job.setApplicationCount(incrementedApplicationCount);
		jobRepository.save(job);

		candidateApplicationRepository.save(new CandidateApplication(candidate, job, Status.applied.name(), new Date()));

		return "redirect:"+ URLMapper.CANDIDATE_JOBS;
	}
}