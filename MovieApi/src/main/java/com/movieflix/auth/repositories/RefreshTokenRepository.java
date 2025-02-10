package com.movieflix.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.User;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
	
		Optional<RefreshToken> findByRefreshToken(String refreshToken);
		
		// Add this method to find a refresh token by User
	    Optional<RefreshToken> findByUser(User user);
		
}
