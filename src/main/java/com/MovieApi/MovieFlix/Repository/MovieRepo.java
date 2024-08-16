package com.MovieApi.MovieFlix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MovieApi.MovieFlix.Entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {

}
