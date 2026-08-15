package com.examples.jwt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class SimpleJwtUtil {

	private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
	private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();
	private static final Pattern EXP_PATTERN = Pattern.compile("\"exp\"\\s*:\\s*(\\d+)");
	private static final Pattern SUB_PATTERN = Pattern.compile("\"sub\"\\s*:\\s*\"([^\"]*)\"");
	private static final Pattern ROLE_PATTERN = Pattern.compile("\"role\"\\s*:\\s*\"([^\"]*)\"");

	private SimpleJwtUtil() {
	}

	public static String createToken(String secret, String subject, String role, String issuer, long expiresInSeconds) {
		long now = Instant.now().getEpochSecond();

		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("sub", subject);
		claims.put("role", role);
		claims.put("iss", issuer);
		claims.put("iat", now);
		claims.put("exp", now + expiresInSeconds);

		return createToken(secret, claims);
	}

	public static String createToken(String secret, Map<String, Object> claims) {
		String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
		String payloadJson = toJson(claims);
		String encodedHeader = base64UrlEncode(headerJson);
		String encodedPayload = base64UrlEncode(payloadJson);
		String signingInput = encodedHeader + "." + encodedPayload;
		String signature = sign(signingInput, secret);
		return signingInput + "." + signature;
	}

	public static JwtVerificationResult verifyToken(String token, String secret) {
		String[] parts = token.split("\\.");
		if (parts.length != 3) {
			return JwtVerificationResult.invalid("JWT must contain exactly 3 parts");
		}

		String signingInput = parts[0] + "." + parts[1];
		String expectedSignature = sign(signingInput, secret);
		if (!MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8), parts[2].getBytes(StandardCharsets.UTF_8))) {
			return JwtVerificationResult.invalid("Signature verification failed");
		}

		String headerJson = base64UrlDecode(parts[0]);
		String payloadJson = base64UrlDecode(parts[1]);
		Long expiration = extractLongClaim(payloadJson, EXP_PATTERN);
		if (expiration != null && Instant.now().getEpochSecond() >= expiration) {
			return JwtVerificationResult.invalid("Token has expired", headerJson, payloadJson);
		}

		return JwtVerificationResult.valid(headerJson, payloadJson);
	}

	public static String decodeHeader(String token) {
		return decodePart(token, 0);
	}

	public static String decodePayload(String token) {
		return decodePart(token, 1);
	}

	public static String decodeSignature(String token) {
		String[] parts = token.split("\\.");
		if (parts.length != 3) {
			throw new IllegalArgumentException("JWT must contain exactly 3 parts");
		}
		return parts[2];
	}

	public static String base64UrlEncode(String value) {
		return URL_ENCODER.encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}

	public static String base64UrlDecode(String value) {
		return new String(URL_DECODER.decode(value), StandardCharsets.UTF_8);
	}

	public static String extractSubject(String payloadJson) {
		return extractStringClaim(payloadJson, SUB_PATTERN);
	}

	public static String extractRole(String payloadJson) {
		return extractStringClaim(payloadJson, ROLE_PATTERN);
	}

	private static String decodePart(String token, int index) {
		String[] parts = token.split("\\.");
		if (parts.length != 3) {
			throw new IllegalArgumentException("JWT must contain exactly 3 parts");
		}
		return base64UrlDecode(parts[index]);
	}

	private static String sign(String input, String secret) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
			byte[] signatureBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
			return URL_ENCODER.encodeToString(signatureBytes);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sign JWT", e);
		}
	}

	private static String toJson(Map<String, Object> values) {
		StringBuilder builder = new StringBuilder("{");
		boolean first = true;
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (!first) {
				builder.append(',');
			}
			first = false;
			builder.append('"').append(escape(entry.getKey())).append('"').append(':');
			appendJsonValue(builder, entry.getValue());
		}
		builder.append('}');
		return builder.toString();
	}

	private static void appendJsonValue(StringBuilder builder, Object value) {
		if (value == null) {
			builder.append("null");
			return;
		}
		if (value instanceof Number || value instanceof Boolean) {
			builder.append(value);
			return;
		}
		builder.append('"').append(escape(String.valueOf(value))).append('"');
	}

	private static String escape(String value) {
		return value.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	private static Long extractLongClaim(String payloadJson, Pattern pattern) {
		Matcher matcher = pattern.matcher(payloadJson);
		if (matcher.find()) {
			return Long.parseLong(matcher.group(1));
		}
		return null;
	}

	private static String extractStringClaim(String payloadJson, Pattern pattern) {
		Matcher matcher = pattern.matcher(payloadJson);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static final class JwtVerificationResult {

		private final boolean valid;
		private final String message;
		private final String headerJson;
		private final String payloadJson;

		private JwtVerificationResult(boolean valid, String message, String headerJson, String payloadJson) {
			this.valid = valid;
			this.message = message;
			this.headerJson = headerJson;
			this.payloadJson = payloadJson;
		}

		public static JwtVerificationResult valid(String headerJson, String payloadJson) {
			return new JwtVerificationResult(true, "Token is valid", headerJson, payloadJson);
		}

		public static JwtVerificationResult invalid(String message) {
			return new JwtVerificationResult(false, message, null, null);
		}

		public static JwtVerificationResult invalid(String message, String headerJson, String payloadJson) {
			return new JwtVerificationResult(false, message, headerJson, payloadJson);
		}

		public boolean isValid() {
			return valid;
		}

		public String getMessage() {
			return message;
		}

		public String getHeaderJson() {
			return headerJson;
		}

		public String getPayloadJson() {
			return payloadJson;
		}
	}
}

