package com.example.vaadin_sso1.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.security.AuthenticationContext;

@Component
public class ServiceSecurity {
	@Autowired AuthenticationContext authenticationContext;

	public UserDetails getAuthenticatedUser() {
		return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
	}

	public static String getAuthenticatedUsername() {
		var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails princ) {
			return princ.getUsername();
		}
		return principal.toString();
	}

	public static boolean hasRole(String role) {
		return SecurityContextHolder.getContext()
				.getAuthentication()
				.getAuthorities()
				.stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
	}

	public void logout() {
		authenticationContext.logout();
	}

	public static boolean isUserLoggedIn() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null
				&& !(auth instanceof AnonymousAuthenticationToken)
				&& auth.isAuthenticated();
	}

	/**
	 * Checks if access is granted for the current user for the given secured view,
	 * defined by the view class.
	 *
	 * @param securedClass View class
	 * @return true if access is granted, false otherwise.
	 */
	public static boolean isAccessGranted(Class<?> securedClass) {
		// Allow if no roles are required.
		var secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
		if (secured == null)
			return true;

		// lookup needed role in user roles
		final var allowedRoles = Arrays.asList(secured.value());
		final var userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		return userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.anyMatch(allowedRoles::contains);
	}
}
