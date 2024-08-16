package com.MovieApi.MovieFlix.Entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="movie_tbl")
@Builder
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer movieId;
	
	@Column(nullable = false,length = 200) 
	@NotNull(message = "Please provide movie name")

	private String movieName;
	
	@Column(nullable = false,length = 200) 
	@NotNull(message = "Please provide Director name")
	private String director;
	
	@Column(nullable = false,length = 200) 
	@NotNull(message = "Please provide movie Studio name")
	private String Studio;
	
	@Column(nullable = false,length = 200) 
	@NotNull(message = "Movie cast's names not added")
	@ElementCollection
	@CollectionTable(name = "movie_cast" )
	private Set<String> MovieCast;
	
	@Column(nullable = false,length = 200) 
	private Integer releaseYear;
	
	@Column(nullable = false,length = 200) 
	@NotNull(message = "Please provide movie Poster")
	private String poster;
	
}
