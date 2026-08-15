package com.examples.jwt;

import com.examples.jwt.SimpleJwtUtil.JwtVerificationResult;

public class JwtAuthFlowMain {

	private static final String SECRET = "auth-flow-secret";

	public static void main(String[] args) {
		System.out.println("================ JWT Auth Flow Demo ================");

		String token = login("swastik", "password123");
		System.out.println("Token issued after login:");
		System.out.println(token);
		System.out.println();

		accessProtectedResource(token);
		accessProtectedResource(token + "broken");
	}

	private static String login(String username, String password) {
		System.out.println("Login request for user: " + username);
		if (!"swastik".equals(username) || !"password123".equals(password)) {
			throw new IllegalArgumentException("Invalid username/password for demo");
		}
		System.out.println("Credentials accepted. Server creates a signed token.");
		return SimpleJwtUtil.createToken(SECRET, username, "student", "auth-service", 600);
	}

	private static void accessProtectedResource(String token) {
		System.out.println("Trying to access protected resource...");
		JwtVerificationResult result = SimpleJwtUtil.verifyToken(token, SECRET);
		if (!result.isValid()) {
			System.out.println("Access denied: " + result.getMessage());
			System.out.println();
			return;
		}

		String payloadJson = result.getPayloadJson();
		String subject = SimpleJwtUtil.extractSubject(payloadJson);
		String role = SimpleJwtUtil.extractRole(payloadJson);
		System.out.println("Access granted for subject = " + subject + ", role = " + role);
		System.out.println("The server trusts the claims only after verifying the signature.");
		System.out.println();
	}
}

