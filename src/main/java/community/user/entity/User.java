package community.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Table(name="user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Data
public class User {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", 
					  strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "user_id")
	private String id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;

	@CreationTimestamp
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(columnDefinition = "boolean default false")
	private boolean admin;

	public User() {
		
	}

	public User(String id, String email, String password, boolean admin) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
}

