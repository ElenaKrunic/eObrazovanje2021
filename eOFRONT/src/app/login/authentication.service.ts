import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class AuthenticationService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});

	constructor(
		private http: HttpClient
	) { }

	login(auth: any): Observable<any> {
		return this.http.post('api/login', {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'json'});
	}

	logout(): Observable<any> {
		return this.http.get('api/logOut', {headers: this.headers, responseType: 'text'});
	}

	isLoggedIn(): boolean {
		if (!localStorage.getItem("user")) {
				return false;
		}
		return true;
	}


}
