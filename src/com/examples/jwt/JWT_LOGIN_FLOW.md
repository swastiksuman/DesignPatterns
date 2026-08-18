# JWT Login Flow for Websites

## What this guide explains

This file explains how website login **usually works** when JWT is used.

The goal is to understand:
- how the browser logs in
- how the server creates tokens
- how later requests stay authenticated
- how refresh tokens and logout usually work
- what security practices are commonly recommended

---

## The big picture

When a user logs into a website using JWT, the system usually works like this:

1. The user submits username/email and password
2. The server validates the credentials
3. The server creates a short-lived **access token**
4. The server often also creates a longer-lived **refresh token**
5. The browser sends the access token on later API requests
6. The server verifies the token before allowing access
7. When the access token expires, the browser uses the refresh token to get a new one

So JWT is usually part of an **authentication flow**, not the entire login system by itself.

---

## Typical login flow step by step

### 1. User opens the login page

The browser shows a login form:
- email/username
- password

Example:

```text
POST /login
username=swastik
password=secret123
```

---

### 2. Browser sends credentials to the server

The browser sends the login request over **HTTPS**.

Example JSON request:

```http
POST /api/login
Content-Type: application/json

{
  "username": "swastik",
  "password": "secret123"
}
```

Important:
- passwords should only be sent over HTTPS
- the server should compare against a **hashed password**, not plain text

---

### 3. Server validates the user

The server checks:
- does the user exist?
- does the password match?
- is the account active / locked / verified?

If login fails:
- return `401 Unauthorized` or similar

If login succeeds:
- create tokens

---

### 4. Server creates the JWT access token

The server creates a signed JWT that usually contains small identity claims like:

```json
{
  "sub": "user-123",
  "role": "student",
  "iss": "my-website",
  "iat": 1720000000,
  "exp": 1720000900
}
```

Common claims:
- `sub` → user id / subject
- `role` → authorization info
- `iss` → issuer
- `iat` → issued at
- `exp` → expiration time

The server signs it with a secret or private key.

The important idea is:
- the client can hold the token
- the server can later verify it
- the client must **not** be trusted without signature verification

---

### 5. Server often creates a refresh token too

Usually there are **two tokens**:

#### Access token
- short lifetime
- used on normal API requests
- for example: 5 minutes, 15 minutes, or 30 minutes

#### Refresh token
- longer lifetime
- used only to get a new access token
- for example: 7 days, 30 days, or more depending on the app

Why use two tokens?
- short access tokens reduce risk if stolen
- refresh tokens avoid forcing the user to log in again too often

---

## Where tokens are usually stored

This is one of the most important practical topics.

### Common recommendation

A common secure pattern is:
- store the **access token** in memory or in a secure cookie strategy
- store the **refresh token** in an **HttpOnly, Secure cookie**

Why HttpOnly cookie?
- JavaScript cannot read it directly
- this reduces risk from XSS stealing the refresh token

### Be careful with localStorage

Many tutorials store JWT in `localStorage`, but that has risk:
- if your site has an XSS vulnerability, malicious JavaScript can read it

So in real applications:
- `localStorage` is simple for demos
- HttpOnly cookies are often safer for refresh tokens

---

## 6. Browser sends the access token with later requests

After login, when the frontend calls protected APIs, it usually sends the access token in the `Authorization` header.

Example:

```http
GET /api/profile
Authorization: Bearer <access-token>
```

This is the usual pattern for SPAs and APIs.

---

## 7. Server verifies the token on every protected request

For each protected request, the server usually checks:
- is the JWT signature valid?
- is it expired?
- is the issuer correct?
- is the audience correct? (if used)
- does the user/role still have permission?

If valid:
- allow the request

If invalid:
- reject with `401 Unauthorized`

Important:
- the server should not trust the JWT just because it looks well-formed
- it must verify the signature first

---

## 8. What happens when the access token expires?

This is where the refresh token is used.

Typical flow:

1. access token expires
2. API request fails with `401`
3. frontend calls a refresh endpoint
4. browser automatically includes refresh token cookie
5. server validates the refresh token
6. server issues a new access token
7. frontend retries the original request

Example:

```http
POST /api/refresh
Cookie: refreshToken=abc123...
```

If refresh is valid:
- return a new access token

If refresh is invalid/expired:
- force the user to log in again

---

## 9. How logout usually works

Logout is a little different with JWT compared to server sessions.

### On the client side
The browser can:
- remove the access token from memory/storage
- clear cookies if the server instructs it to

### On the server side
The server may:
- invalidate/delete the refresh token in its database
- clear the refresh cookie
- optionally maintain a blocklist for revoked tokens in some systems

A very common approach is:
- access token expires quickly on its own
- refresh token is stored server-side or tracked so it can be revoked

---

## Typical real-world architecture

A common modern website setup looks like this:

### Login
- frontend sends username/password to backend
- backend validates user
- backend returns access token
- backend sets refresh token cookie

### Authenticated API calls
- frontend includes access token in `Authorization: Bearer ...`
- backend verifies token
- backend returns protected data

### Token renewal
- frontend calls refresh endpoint when access token expires
- backend validates refresh token cookie
- backend issues a new access token

---

## Visual flow diagrams

### 1. Login Flow (Step by step)

```
┌─────────┐
│  User   │
└────┬────┘
     │
     │ 1. Opens login page
     ▼
┌──────────────────────────┐
│ Browser: Login Form      │
│ [username]               │
│ [password]               │
│ [Login Button]           │
└────┬─────────────────────┘
     │
     │ 2. Submits credentials (HTTPS)
     ▼
┌──────────────────┐         ┌──────────────┐
│     Server       │────────▶│   Database   │
│  POST /login     │         │ Validate     │
│                  │◀────────│ user/pass    │
└────┬─────────────┘         └──────────────┘
     │
     │ 3. Create tokens
     │ - access token (15 min)
     │ - refresh token (7 days)
     ▼
┌──────────────────────────┐
│ Browser Storage:         │
│ Memory: accessToken      │
│ Cookie: refreshToken     │
└────┬─────────────────────┘
     │
     │ 4. User is logged in
     │
```

### 2. Authenticated API Request Flow

```
┌────────────────────────────┐
│ Browser (User wants data)  │
└────┬───────────────────────┘
     │
     │ Has accessToken in memory
     ▼
┌────────────────────────────────────┐
│ GET /api/profile                   │
│ Authorization: Bearer <token>      │
└────┬───────────────────────────────┘
     │
     ▼
┌──────────────────────────────────┐
│ Server:                          │
│ 1. Extract token from header     │
│ 2. Verify signature              │
│ 3. Check expiration              │
│ 4. Check permissions             │
└────┬─────────────────────────────┘
     │
     │ Token valid?
     ├─ YES ──▶ Return protected data
     │
     └─ NO ──▶ Return 401 Unauthorized
```

### 3. Token Refresh Flow (When Access Token Expires)

```
┌──────────────────────────────────┐
│ Browser tries API request        │
│ accessToken is EXPIRED           │
└────┬─────────────────────────────┘
     │
     │ API returns 401 (Unauthorized)
     ▼
┌──────────────────────────────────┐
│ Browser Logic:                   │
│ "Access token expired, refresh"  │
└────┬─────────────────────────────┘
     │
     │ Has refreshToken in cookie
     ▼
┌────────────────────────────────────┐
│ POST /api/refresh                  │
│ (refreshToken sent in cookie)      │
└────┬───────────────────────────────┘
     │
     ▼
┌──────────────────────────────────┐
│ Server:                          │
│ 1. Check refresh token           │
│ 2. Is it still valid?            │
│ 3. Is user still active?         │
└────┬─────────────────────────────┘
     │
     │ Refresh token valid?
     │
     ├─ YES ──▶ Issue new access token
     │          Browser retries request
     │
     └─ NO ──▶ Return 401
                (Force user to log in again)
```

### 4. Complete Login → API Call → Refresh Cycle

```
PHASE 1: LOGIN
═════════════════════════════════════════════════════════════════
User enters credentials
         │
         ▼
    [Server validates]
         │
         ▼
    [Create tokens]
    
    accessToken (expires in 15 min)
    refreshToken (expires in 7 days)
         │
         ▼
 Browser stores both tokens


PHASE 2: AUTHENTICATED REQUESTS
═════════════════════════════════════════════════════════════════
Browser includes accessToken:
GET /api/data
Authorization: Bearer <accessToken>
         │
         ▼
    [Server verifies token]
         │
    ─────┴─────
    │         │
   ✓         ✗ (expired)
   │         │
   ▼         ▼
 Return  Return 401
 data    (Unauthorized)


PHASE 3: TOKEN REFRESH (when access expires)
═════════════════════════════════════════════════════════════════
Browser detects 401
         │
         ▼
   POST /api/refresh
   (with refreshToken cookie)
         │
         ▼
    [Server validates refresh token]
         │
    ─────┴─────
    │         │
   ✓         ✗ (expired)
   │         │
   ▼         ▼
 Issue   Redirect to
 new     login page
 access
 token
   │
   ▼
 Browser retries
 original request
 with new access token


PHASE 4: LOGOUT
═════════════════════════════════════════════════════════════════
User clicks logout
         │
         ▼
 Browser:
 - Clear access token from memory
 - Tell server to revoke refresh token
         │
         ▼
 Server:
 - Delete/blacklist refresh token
 - Clear refresh cookie
         │
         ▼
 User redirected to login page
```

---

## Simple sequence diagram

```text
User -> Browser: enter username/password
Browser -> Server: POST /login
Server -> Database: validate user/password
Database -> Server: valid user
Server -> Browser: access token + refresh token
Browser -> Server: GET /profile with Bearer access token
Server -> Browser: protected data

Later...
Browser -> Server: POST /refresh with refresh cookie
Server -> Browser: new access token
```

---

## Example response after login

One common response style is:

```json
{
  "accessToken": "eyJhbGciOi...",
  "user": {
    "id": "user-123",
    "name": "Swastik",
    "role": "student"
  }
}
```

And the refresh token is set as a cookie by the server.

---

## Why websites often prefer short-lived access tokens

If an access token is stolen, the attacker can use it until it expires.

So it is common to keep access tokens short-lived, such as:
- 5 minutes
- 15 minutes
- 30 minutes

Then the refresh token handles the “stay logged in” experience.

---

## JWT does not replace all security

JWT helps with stateless authentication, but you still need:
- HTTPS
- password hashing
- rate limiting on login
- CSRF protection where cookies are involved
- XSS protection
- proper authorization checks on the server

JWT answers the question:
- “How can the server verify a signed identity claim?”

It does **not** automatically solve:
- secure frontend code
- user permissions
- token theft
- logout/revocation strategy

---

## Common mistakes

### ❌ Putting sensitive data in the payload
JWT payloads are readable.
Do not put:
- passwords
- OTP secrets
- API keys
- private personal data unless absolutely necessary

### ❌ Making access tokens live too long
Long-lived access tokens increase risk.

### ❌ Trusting the token without verification
The server must verify signature and expiration.

### ❌ Storing everything in localStorage without thinking
This is common in demos, but risky if your app has XSS issues.

### ❌ Forgetting refresh token revocation
If refresh tokens cannot be revoked, logout and account compromise become harder to manage.

---

## Practical recommendation for a usual website

A common pattern that many production apps use is:

- Access token:
  - short-lived
  - sent in `Authorization` header
- Refresh token:
  - longer-lived
  - stored in `HttpOnly`, `Secure` cookie
- Backend:
  - verifies every access token
  - rotates or revokes refresh tokens when needed
- Frontend:
  - refreshes access token silently
  - redirects to login when refresh fails

This gives a good balance between:
- user experience
- security
- scalability

---

## How this relates to files in this package

- `JwtConceptsMain.java` helps you understand token structure, tampering, and expiration
- `JwtPitfallsMain.java` shows why JWT payloads are readable and why signatures matter
- `JwtAuthFlowMain.java` shows a simplified login and protected-resource flow
- `JWT_GUIDE.md` explains the core JWT concepts

This new file focuses specifically on the **usual website login flow** around JWT.

---

## Final takeaway

A website using JWT usually works like this:
- user logs in with username/password
- server validates the user
- server issues a short-lived access token
- server often issues a refresh token too
- browser sends the access token on API calls
- server verifies it on every protected request
- refresh token is used to keep the user logged in without asking for the password again

The best mental model is:
**JWT is usually one part of a broader authentication system, not the whole system by itself.**
