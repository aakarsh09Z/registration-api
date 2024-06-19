package com.aakarsh09z.registrationapi.Payload.Response;

public record JwtAccessTokenResponse(String myAccessToken, String firstname, String lastname, String newUsername, Boolean success) {
}
