package com.movieflix.auth.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User  implements  UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userID;
	
	@NotBlank(message="The name field can't be blank")
	private String name;
	
	@NotBlank(message="The username field can't be blank")
	@Column(unique=true)
	private String username;
	
	@NotBlank(message="The email field can't be blank")
	@Column(unique=true)
	@Email(message ="please enter email in proper format!")
	private String email;
	
	@NotBlank(message="The password field can't be blank")
	@Size(min = 5, message = "The password must have at least 5 Characters")
	private String password;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private RefreshToken refreshToken;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ForgotPassword forgotPassword;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
		

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true; 
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true; // Modify based on your logic
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true; // Modify based on your logic
	}

	@Override
	public boolean isEnabled() {
	    return true; // Modify based on your logic
	}
	
}
