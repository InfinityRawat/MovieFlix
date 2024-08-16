package com.MovieApi.MovieFlix.Security.Entity;

import java.time.Instant;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshToken {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer tokenId;
		
		@Column(nullable = false)
		@Length(max = 500)
		private String refreshToken;
		
		@Column(nullable = false)
		private Instant expirationTime;
		
		@OneToOne()
		@JoinColumn(name = "user_id")
		private User user;
		
}
