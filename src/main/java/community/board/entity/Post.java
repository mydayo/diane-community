package community.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import community.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="post")
@NoArgsConstructor
@Data
public class Post {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", 
					  strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "post_id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String contents;
	
	@CreationTimestamp
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdTime;
	
	@UpdateTimestamp
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;
}
