import { Injectable } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BodyInfoServiceService {

  constructor(public oktaAuth: OktaAuthService, private http: HttpClient) { }

  public getBodyInfo(accessToken: string) {

    return this.http.get<Array<any>>("http://localhost:5000/api/bodyinfo", {
      headers: {
        'authorization': 'Bearer ' + accessToken,
      }
    });
  }
}
