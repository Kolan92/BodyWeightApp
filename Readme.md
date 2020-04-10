# Body Weight APP

Application which allows to calculate your daily calories needs based on your current weight and goals.

[![Build Status](https://dev.azure.com/pawelkrzysztofkolanowski/pawelkrzysztofkolanowski/_apis/build/status/Kolan92.BodyWeightApp?branchName=master)](https://dev.azure.com/pawelkrzysztofkolanowski/pawelkrzysztofkolanowski/_build/latest?definitionId=1&branchName=master)

To backend application add file backend/src/main/resources/application.properties. Example content of the file:

```
okta.oauth2.issuer=https://{domain}.com/oauth2/auth_server
okta.oauth2.audience={scope}
server.port=5000

```