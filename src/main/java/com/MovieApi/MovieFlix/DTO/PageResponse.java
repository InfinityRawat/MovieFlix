package com.MovieApi.MovieFlix.DTO;

import java.util.List;

import lombok.Builder;

@Builder
public record PageResponse(
		
		List<MovieDto> entities,
		Integer pageNumber,
		Integer pageSize,
		Integer totalPage,
		boolean isLast
		
		) {}
