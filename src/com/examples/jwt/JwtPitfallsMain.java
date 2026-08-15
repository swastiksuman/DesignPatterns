package com.examples.jwt;

public class JwtPitfallsMain {

	public static void main(String[] args) {
		System.out.println("================ JWT Pitfalls Demo ================");
		showJwtIsReadable();
		showWrongSecretFailsVerification();
	}

	private static void showJwtIsReadable() {
		String token = SimpleJwtUtil.createToken(
			"another-secret",
			"charlie",
			"developer",
			"design-patterns-demo",
			300
		);

		System.out.println();
		System.out.println("1. JWTs are signed, not encrypted by default");
		System.out.println("-----------------------------------------------");
		System.out.println("Token:");
		System.out.println(token);
		System.out.println();
		System.out.println("Anyone holding the token can decode the payload without the secret:");
		System.out.println(SimpleJwtUtil.decodePayload(token));
		System.out.println("Lesson: never put passwords, API keys, or private data into JWT claims.");
	}

	private static void showWrongSecretFailsVerification() {
		String token = SimpleJwtUtil.createToken(
			"correct-secret",
			"diana",
			"manager",
			"design-patterns-demo",
			300
		);

		System.out.println();
		System.out.println("2. Verification depends on the same signing secret");
		System.out.println("-----------------------------------------------");
		System.out.println("Verification with correct secret: "
			+ SimpleJwtUtil.verifyToken(token, "correct-secret").getMessage());
		System.out.println("Verification with wrong secret: "
			+ SimpleJwtUtil.verifyToken(token, "wrong-secret").getMessage());
	}
}

