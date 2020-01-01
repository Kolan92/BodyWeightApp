const {
  CLIENT_ID,
  ISSUER,
  OKTA_TESTING_DISABLEHTTPSCHECK,
  REDIRECT_URI
} = process.env;

export default {
  oidc: {
    clientId: `${CLIENT_ID}`,
    issuer: `${ISSUER}`,
    redirectUri: "http://localhost:8080/implicit/callback",
    scopes: ["openid", "profile", "email"],
    testing: {
      disableHttpsCheck: `${OKTA_TESTING_DISABLEHTTPSCHECK}`
    }
  },
  resourceServer: {
    messagesUrl: "http://localhost:9000/api/post/1"
  }
};
