package com.stl.rupam.SchoolWebApp.user.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stl.rupam.SchoolWebApp.user.entity.User;
import com.stl.rupam.SchoolWebApp.user.jwt.JwtRequest;
import com.stl.rupam.SchoolWebApp.user.jwt.JwtResponse;
import com.stl.rupam.SchoolWebApp.user.jwt.JwtUtil;
import com.stl.rupam.SchoolWebApp.user.repo.UserRepo;


@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		
		User user = userRepo.findById(userName).get();
		
		return new JwtResponse(user, newGeneratedToken);
			
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findById(username).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(), 
					user.getUserPassword(),
					(Collection<? extends GrantedAuthority>) getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("UserName is not valid" + username);
		}
	}

	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});

		return authorities;
	}

	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("User is disabled", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Bad credentials from user", e);
		}
	}

}
