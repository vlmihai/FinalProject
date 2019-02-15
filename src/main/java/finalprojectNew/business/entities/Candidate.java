package finalprojectNew.business.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Candidate {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;

	private String name;
	private String address;
	private String email;
	private String linkedInUrl;
	private String phoneNumber;
	private String username;

	@OneToMany
	@Transient
	private List<Job> appliedJobs;

	@Transient
	private String applicationStatus;


	public Candidate() {
	}

	public Candidate(String name, String address, String email,
					 String linkedInUrl, String phoneNumber, String username) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.linkedInUrl = linkedInUrl;
		this.phoneNumber = phoneNumber;
		this.username = username;
	}

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

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}
	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public List<Job> getAppliedJobs() {
		return appliedJobs;
	}
	public void setAppliedJobs(List<Job> appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}