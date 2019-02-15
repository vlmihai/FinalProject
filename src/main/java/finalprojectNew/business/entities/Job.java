package finalprojectNew.business.entities;


import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Job {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	@NotEmpty(message = "Job name can't be empty")
	private String name;

	@NotEmpty(message = "Description can't be empty")
	private String description;

	@NotEmpty(message = "Experience cant be empty")
	private String experience;

	//@NotEmpty(message = "Type can't be empty")
	private String type;

	@NotEmpty(message = "Location can't be empty")
	private String location;

	private String status;

	private boolean isDeleted;

	private Date postedOn;
	private Date updatedOn;

	private long applicationCount;

	@OneToOne
	@JoinColumn(name = "employer_id")
	private Employer employer;

	@Transient
	// which are not saved in the database but will be present in the scope
	private String postedBy;

	@Transient
	private String candidateApplicationStatus;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public Date getPostedOn() {
		return postedOn;
	}
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public long getApplicationCount() {
		return applicationCount;
	}
	public void setApplicationCount(long applicationCount) {
		this.applicationCount = applicationCount;
	}

	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getCandidateApplicationStatus() {
		return candidateApplicationStatus;
	}
	public void setCandidateApplicationStatus(String candidateApplicationStatus) {
		this.candidateApplicationStatus = candidateApplicationStatus;
	}

}