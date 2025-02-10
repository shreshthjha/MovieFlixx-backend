package com.movieflix.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> moviesDtos,
								Integer pageNumber,
								Integer pageSize,
								Long totalElements,
								int totalPages,
								boolean isLast) {

}
