package com.examples.jwt;

import java.util.LinkedHashMap;
import java.util.Map;

import com.examples.jwt.SimpleJwtUtil.JwtVerificationResult;

public class JwtConceptsMain {

	private static final String SECRET = "my-super-secret-key";

	public static void main(String[] args) throws InterruptedException {
		System.out.println("================ JWT Concepts Demo ================");
		showJwtStructure();
		showSignatureProtection();
		showExpiration();
	}

	private static void showJwtStructure() {
		printSection("1. JWT structure: header.payload.signature");

		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("sub", "swastik");
		claims.put("role", "admin");
		claims.put("iss", "design-patterns-demo");
		claims.put("iat", 1_725_000_000L);
		claims.put("exp", 1_999_999_999L);

		String token = SimpleJwtUtil.createToken(SECRET, claims);
		System.out.println("JWT token:");
		System.out.println(token);
		System.out.println();
		System.out.println("Decoded header:");
		System.out.println(SimpleJwtUtil.decodeHeader(token));
		System.out.println();
		System.out.println("Decoded payload:");
		System.out.println(SimpleJwtUtil.decodePayload(token));
		System.out.println();
		System.out.println("Signature:");
		System.out.println(SimpleJwtUtil.decodeSignature(token));
	}

	private static void showSignatureProtection() {
		printSection("2. Signature protects the payload from tampering");

		String originalToken = SimpleJwtUtil.createToken(SECRET, "alice", "user", "design-patterns-demo", 300);
		String tamperedToken = tamperSubject(originalToken, "mallory");

		System.out.println("Original token payload:");
		System.out.println(SimpleJwtUtil.decodePayload(originalToken));
		JwtVerificationResult originalResult = SimpleJwtUtil.verifyToken(originalToken, SECRET);
		System.out.println("Original verification: " + originalResult.getMessage());
		System.out.println();

		System.out.println("Tampered token payload:");
		System.out.println(SimpleJwtUtil.decodePayload(tamperedToken));
		JwtVerificationResult tamperedResult = SimpleJwtUtil.verifyToken(tamperedToken, SECRET);
		System.out.println("Tampered verification: " + tamperedResult.getMessage());
		System.out.println("Reason: changing the payload without recomputing the signature makes the token invalid.");
	}

	private static void showExpiration() throws InterruptedException {
		printSection("3. Expiration claim (exp)");

		String shortLivedToken = SimpleJwtUtil.createToken(SECRET, "bob", "reader", "design-patterns-demo", 1);
		System.out.println("Short-lived token payload:");
		System.out.println(SimpleJwtUtil.decodePayload(shortLivedToken));
		System.out.println("Immediate verification: " + SimpleJwtUtil.verifyToken(shortLivedToken, SECRET).getMessage());
		System.out.println("Waiting 2 seconds so the token can expire...");
		Thread.sleep(2000);
		System.out.println("Verification after waiting: " + SimpleJwtUtil.verifyToken(shortLivedToken, SECRET).getMessage());
	}

	private static String tamperSubject(String token, String newSubject) {
		String[] parts = token.split("\\.");
		String payloadJson = SimpleJwtUtil.decodePayload(token);
		String updatedPayloadJson = payloadJson.replace("\"alice\"", "\"" + newSubject + "\"");
		String updatedPayload = SimpleJwtUtil.base64UrlEncode(updatedPayloadJson);
		return parts[0] + "." + updatedPayload + "." + parts[2];
	}

	private static void printSection(String title) {
		System.out.println();
		System.out.println(title);
		System.out.println("-".repeat(title.length()));
	}
}
