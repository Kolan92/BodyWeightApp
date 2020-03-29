from functools import wraps
from flask import request, g, abort
import jwt
from jwt.algorithms import RSAAlgorithm
import json
import requests

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
       print(request.headers)
       authorization = request.headers.get("authorization", None)
       if not authorization:
           return json.dumps({'error': 'no authorization token provied'}), 403, {'Content-type': 'application/json'}
      
       try:
            token = authorization.split(' ')[1]
            public_key = get_public_key()
            claims = jwt.decode(token, public_key, audience='bwa_api', algorithms='RS256')
            print(claims)
       except Exception as e:
           print(e)
           return json.dumps({'error': 'invalid authorization token'}), 403, {'Content-type': 'application/json'}
      
       return f(*args, **kwargs)

    def get_public_key():
        r = requests.get('https://dev-302380.okta.com/oauth2/aus2e219i7zn6kdQV357/v1/keys')
        key_json = json.dumps(r.json()["keys"][0])
        print(key_json)
        public_key = RSAAlgorithm.from_jwk(key_json)
        return public_key
 
    return wrap
