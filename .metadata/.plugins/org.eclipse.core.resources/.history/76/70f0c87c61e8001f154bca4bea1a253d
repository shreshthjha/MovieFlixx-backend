package com.movieflix.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDto;
import com.movieflix.dto.MoviePageResponse;
import com.movieflix.exceptions.EmptyFileException;
import com.movieflix.services.MovieService;
import com.movieflix.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/movie")
@CrossOrigin(origins = "*")
public class MovieController {
	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add-movie")
	public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file , @RequestPart String movieDto) throws IOException, EmptyFileException {
		
		if(file.isEmpty()) {
			throw new EmptyFileException("File is empty ! please send another file");
		}
		MovieDto dto = convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
	}
	
	//getting single movie
	@GetMapping("/{movieId}")
	public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
		return ResponseEntity.ok(movieService.getMovie(movieId));
	}
	
	//get all movies
	@GetMapping("/all")
	public ResponseEntity<List<MovieDto>> getAllMoviesHandler(){
		return ResponseEntity.ok(movieService.getAllMovie());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{movieId}")
	public ResponseEntity<MovieDto> UpdateMovieHandler(@PathVariable Integer movieId,
													   @RequestPart MultipartFile file,
													   @RequestPart String movieDtoObj) throws IOException {
		if(file.isEmpty()) file = null;
		MovieDto movieDto = convertToMovieDto(movieDtoObj);
		return ResponseEntity.ok(movieService.updateMovie(movieId,movieDto , file));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{movieId}")
	public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException{
		return ResponseEntity.ok(movieService.deleteMovie(movieId));
	}
	
	@GetMapping("/allMoviesPage")
	public ResponseEntity<MoviePageResponse> getMoviesWithPagination(
			
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
			){
		return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
	}
	
	@GetMapping("/allMoviesPageSort")
	public ResponseEntity<MoviePageResponse> getMoviesWithPaginationAndSorting(
			
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
			){
		return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
	}
	
	//convert string to json(Dto)
	private MovieDto convertToMovieDto(String movieDtoObj) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		MovieDto movieDto = objectMapper.readValue(movieDtoObj, MovieDto.class);
		return movieDto;
	}
}
