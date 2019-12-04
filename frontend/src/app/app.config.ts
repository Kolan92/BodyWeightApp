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
    redirectUri: `${REDIRECT_URI}`,
    scopes: ["openid", "profile", "email"],
    testing: {
      disableHttpsCheck: `${OKTA_TESTING_DISABLEHTTPSCHECK}`
    }
  },
  resourceServer: {
    messagesUrl: "http://localhost:8000/api/messages"
  }
};
