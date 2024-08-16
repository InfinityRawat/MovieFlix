	package com.MovieApi.MovieFlix.Security.Entity;
	
	import java.util.Date;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToOne;
	import jakarta.persistence.Table;
	import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
	
	@Entity
	@NoArgsConstructor
	@AllArgsConstructor
	@Table(name="forgetPass")
	@Builder
	@Data
	public class ForgetPassword {
			
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer fid;
			
			@Column(nullable = false)
			private Integer otp;
			
			@Column(nullable = false)
			private Date ExpirationTime;
			
			@OneToOne
			private User user;
	}
