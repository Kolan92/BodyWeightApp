from flask import Flask, request
from flask_restplus import Api, Resource, fields
from middlewares import login_required
from flask_cors import CORS

import os
import sys

domain_env = os.environ['DOMAIN']
if not domain_env:
    print("fatal error", file=sys.stderr)
    sys.exit(1)


flask_app = Flask(__name__)
cors = CORS(flask_app, resources={r"/api/*": {"origins": "*"}})
app = Api(app = flask_app, 
          version = "1.0", 
          title = "Name Recorder", 
          description = "Manage names of various users of the application")

name_space = app.namespace('api/messages', description='Manage names')

model = app.model('Name Model', 
                  {'name': fields.String(required = True, 
                                           description="Name of the person", 
                                           help="Name cannot be blank.")})

list_of_names = {}

@name_space.route("/<int:id>")
class MainClass(Resource):
    
    @app.doc(responses={ 200: 'OK'})
    @login_required    
    def get(self, id):
        return [{"date": "2020-01-01", "text": "dupa1"}, {"date": "2020-01-01", "text": "dupa1"}]

    @app.doc(responses={ 200: 'OK', 400: 'Invalid Argument', 500: 'Mapping Key Error' }, 
             params={ 'id': 'Specify the Id associated with the person' })
    @app.expect(model)    
    @login_required    
    def post(self, id):
        try:
            list_of_names[id] = request.json['name']
            return {
                "status": "New person added",
                "name": list_of_names[id]
            }
        except KeyError as e:
            name_space.abort(500, e.__doc__, status = "Could not save information", statusCode = "500")
        except Exception as e:
            name_space.abort(400, e.__doc__, status = "Could not save information", statusCode = "400")