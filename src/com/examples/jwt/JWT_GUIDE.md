# JWT Concepts Guide

## What is a JWT?

A **JWT (JSON Web Token)** is a compact string used to transfer claims between systems.
It is commonly used for **authentication** and **authorization**.

A JWT usually looks like this:

```text
header.payload.signature
```

Each section is Base64URL-encoded.

---

## The 3 Parts of a JWT

### 1. Header
The header describes the token metadata.

Example:

```json
{"alg":"HS256","typ":"JWT"}
```

- `alg` = signing algorithm
- `typ` = token type

### 2. Payload
The payload contains the **claims**.

Example:

```json
{"sub":"swastik","role":"admin","iss":"design-patterns-demo","exp":1999999999}
```

Common claims:
- `sub` → subject (usually the user id / username)
- `iss` → issuer
- `iat` → issued at
- `exp` → expiration time

### 3. Signature
The signature proves the token was created by someone with the secret key.

For `HS256`, the idea is:

```text
HMACSHA256(base64Url(header) + "." + base64Url(payload), secret)
```

---

## Important JWT Ideas

### JWT is signed, not encrypted
A normal signed JWT can be **decoded by anyone** who has the token.
That means the payload is **readable**.

So:
- ✅ Put identifiers and simple claims in the payload
- ❌ Do not put passwords, secrets, or sensitive private data in the payload

### JWT helps detect tampering
If someone changes the payload but does not know the secret key, the signature becomes invalid.

### JWT can expire
The `exp` claim limits how long a token is valid.

---

## Files in this package

### `SimpleJwtUtil.java`
A small dependency-free utility that shows how JWT creation and verification work internally:
- Base64URL encoding
- HS256 signature generation
- Payload decoding
- Expiration checking

### `JwtConceptsMain.java`
Runnable example showing:
1. JWT structure
2. Payload decoding
3. Tampering detection
4. Expiration checking

### `JwtPitfallsMain.java`
Runnable example showing:
1. Why JWT payloads are readable
2. Why the correct signing secret is required

### `JwtAuthFlowMain.java`
Runnable example showing a simplified auth flow:
1. User logs in
2. Server creates a token
3. Client sends token back
4. Server verifies token before allowing access

---

## How the examples teach the concepts

### Example 1: JWT structure
`JwtConceptsMain` creates a token and prints:
- the full token
- decoded header
- decoded payload
- signature

This helps you see that a JWT is just three parts joined by dots.

### Example 2: Tampering
The demo changes the payload (`sub = alice` → `sub = mallory`) **without** re-signing the token.
Verification then fails.

This demonstrates the core promise of JWT signatures:
**the token can be read, but it cannot be safely changed without the secret.**

### Example 3: Expiration
The demo creates a token that expires in 1 second, verifies it immediately, waits 2 seconds, and verifies again.

This makes the `exp` claim easy to understand.

### Example 4: Readability and mistakes
`JwtPitfallsMain` shows that anyone can decode the payload.
This is one of the most important real-world lessons about JWTs.

### Example 5: Auth flow
`JwtAuthFlowMain` simulates the common flow where:
- a user logs in,
- the server issues a signed token,
- the client sends the token with later requests,
- the server verifies the token before trusting the claims.

---

## When JWT is useful

Use JWT when:
- you want a stateless token for authentication
- services need to verify tokens without session storage
- you need to pass identity/role claims between systems

JWT may not be the best choice when:
- you need immediate server-side revocation of every token
- your system is simple and server sessions are enough
- you are tempted to store sensitive information in the token

---

## Practical rules

- Keep payload claims small
- Set expiration (`exp`)
- Use HTTPS
- Never trust the client blindly just because a token exists
- Verify the signature on the server
- Do not store secrets in the payload

---

## Quick run ideas

From the project root:

```bash
javac -d out src/com/examples/jwt/*.java
java -cp out com.examples.jwt.JwtConceptsMain
java -cp out com.examples.jwt.JwtPitfallsMain
java -cp out com.examples.jwt.JwtAuthFlowMain
```

---

## Final takeaway

A JWT is best understood like this:
- **Header** says what algorithm is used
- **Payload** carries claims
- **Signature** protects against tampering

It is a **compact signed message**, not a hidden/encrypted one.

