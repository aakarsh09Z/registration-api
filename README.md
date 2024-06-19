# Registration APIs
## Deployed Link
````
https://registration-api-1.onrender.com
````
## Swagger Documentation
````
https://registration-api-1.onrender.com/swagger-ui/index.html
````
## Endpoints
- ### Register
~~~
/api/user/register
~~~
***POST***
~~~
{
    "firstname":"Aakarsh",
    "lastname":"Singh",
    "username":"aakarsh09",
    "email":"singhaakarsh54321@gmail.com",
    "password":"Pass@123"
}
~~~
**Success Response**
~~~
{
    "message": "Check your email for OTP",
    "success": true
}
~~~
**Invalid Email Format**
~~~
{
    "email": "Invalid email format"
}
~~~
***Invalid Username Format***
~~~
{
    "username": "Username must be alphanumeric and between 6 to 20 characters"
}
~~~
- ### Verification for email
~~~
/api/user/register/verify
~~~
***POST***
~~~
{
    "email":"singhaakarsh54321@gmail.com",
    "otp":"124326"
}
~~~
~~~
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWthcnNoMDkiLCJpYXQiOjE3MTg4MTEzMDksImV4cCI6MTcxODg5NzcwOX0.crFo14rJDYgNxKhdWlUKUoZ3ad9sRFwJib9-76XabqJVy5SGd0B1fUeera5EDnkfU8CJIjpPm_nN5TmYTCOQKA",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoX2Fha2Fyc2gwOSIsImlhdCI6MTcxODgxMTMwOSwiZXhwIjoxNzI3NDUxMzA5fQ.ePU8hGKJ7XuiKG53kvRxcUN4PFV130yHOorTEkiNV1LznA2_ykMSnwxoTM4iNOMdEtu-8z9kQ2f4BGgwf-bTXA",
    "username": "aakarsh09",
    "email": "singhaakarsh54321@gmail.com",
    "success": true
}
~~~
- ### Login
~~~
/api/user/login
~~~
***POST***
~~~
{
"username":"aakarsh09",
"password":"Pass@123"
}
~~~
**Success Response**
~~~
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWthcnNoMDkiLCJpYXQiOjE3MTg4MTEzNDMsImV4cCI6MTcxODg5Nzc0M30.bTNhMHtkZVhKYOzz7WGXtT95EoGBRC_wTlnKVRPXeZMGLb4nL_wB8exVgllHnVivf9ePRkX4n_0WR9YDoZRyOg",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoX2Fha2Fyc2gwOSIsImlhdCI6MTcxODgxMTM0MywiZXhwIjoxNzI3NDUxMzQzfQ.x6UUKnHIb4ipKMzIy4XiE9xOsCvs_tp8mpEhIMBrf0jmnbvAxmywj8gKBLtXZBwghwaPtVbcl1XMo-y82cckXQ",
    "username": "aakarsh09",
    "email": "singhaakarsh54321@gmail.com",
    "success": true
}
~~~
**Wrong Password Response**
~~~
{
    "message": "Invalid Credentials",
    "success": false
}
~~~
- ### Forgot Password
~~~
/api/user/forgot-password
~~~
***POST***
~~~
/api/user/forgot-password?username=aakarsh09
~~~
~~~
{
    "message": "Check your registered email for OTP",
    "success": true
}
~~~
- ### Verify OTP to reset password
~~~
/api/user/reset-password
~~~
***POST***
~~~
{
    "username":"aakarsh09",
    "password":"Pass@123"
}
~~~
~~~
{
    "message": "Reset password successful",
    "success": true
}
~~~
- ### Resend OTP
~~~
/api/user/resend-otp
~~~
***POST***
~~~
/api/user/resend-otp?username=aakarsh09
~~~
~~~
{
    "message": "OTP sent successfully",
    "success": true
}
~~~
- ### Regenerate Token
~~~
/api/user/regenerateToken
~~~
***GET***
~~~
/api/user/regenerateToken?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoX2Fha2Fyc2gwOVoiLCJpYXQiOjE3MTg2MzkwNzEsImV4cCI6MTcyNzI3OTA3MX0.XdxdbggYYaF5ceQfiXO4S5Y_fbiBaxYZDgtNaeL-CmFWk2-4Ktfpvd6H8dALmtEFU3OezcmpR--B_cAHzIAfXg
~~~
**Success Response**
~~~
{
  "myAccessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWthcnNoMDkiLCJpYXQiOjE3MTg4MTI0OTQsImV4cCI6MTcxODg5ODg5NH0.9UDVMgWcVxc_iiDEijwuRm5agUUwqWiI3ID_slm5r8nrW5wao2p5LfHNf-C-SJXFOtbVI-jf3hsce2jYBo6fEQ",
  "firstname": "Aakarsh",
  "lastname": "Singh",
  "newUsername": "aakarsh09",
  "success": true
}
~~~
**Failure response**
~~~
{
    "message": "Not a Refresh Token",
    "success": false
}
~~~
- ### Fetch all users
~~~
/api/user/fetch
~~~
***GET***
~~~
{
    "content": [
        {
            "id": 1,
            "username": "aakarsh09",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": true
        },
        {
            "id": 2,
            "username": "aakarsh",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": false
        },
        {
            "id": 3,
            "username": "askjdn12",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": false
        },
        {
            "id": 4,
            "username": "asa1233s2",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": false
        },
        {
            "id": 5,
            "username": "asa132847asjhash",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": false
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 5,
        "totalPages": 1
    }
}
~~~
- ### Fetch user containing letters
~~~
/api/user/fetch/aakarsh
~~~
***GET***
~~~
{
    "content": [
        {
            "id": 1,
            "username": "aakarsh09",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": true
        },
        {
            "id": 2,
            "username": "aakarsh",
            "firstname": "Aakarsh",
            "lastname": "Singh",
            "email": "singhaakarsh54321@gmail.com",
            "isVerified": false
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 2,
        "totalPages": 1
    }
}
~~~