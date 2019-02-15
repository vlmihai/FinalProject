package finalprojectNew.business.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employer {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;

	private String name;
	private String email;
	private String address;
	private String username;

	@OneToMany (mappedBy = "employer")
	@Transient
	private List<Job> postedJobs;

	public Employer (){}

	public Employer(String username) {
		this.username = username;
	}

	public Employer(String name, String address, String email, String username) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.username = username;
	}

	@Override
	public String toString() {
		return String.format("Employer[id=%d, name='%s', address='%s', email = '%s', username='%s',postedJobs='%s']",
				id,name,address,email,username,postedJobs);
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

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public List<Job> getPostedJobs() {
		return postedJobs;
	}
	public void setPostedJobs(List<Job> postedJobs) {
		this.postedJobs = postedJobs;
	}
}