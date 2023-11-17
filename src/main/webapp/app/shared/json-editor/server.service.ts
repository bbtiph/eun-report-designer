import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FetchServer {
  static getFakerData() {
    throw new Error('Method not implemented.');
  }

  private proxyurl = 'https://cors-anywhere.herokuapp.com/';
  private url = 'https://fakedata.avinashsingh7.repl.co/about/';
  private URL_ = 'https://fakedata.avinashsingh7.repl.co/analyze';
  private Faker = 'https://fakedata.avinashsingh7.repl.co/getdata';

  private httpOptions: any;
  // @ts-ignore
  public token: string;

  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    };
  }

  postApi(user: any) {
    return this.http.post<JSON[]>(this.proxyurl + this.url, JSON.stringify({ user }));
  }

  getApi(): Observable<JSON[]> {
    return this.http.get<JSON[]>(this.proxyurl + this.url);
  }

  getFakerData(data: any) {
    return this.http.post<any[]>(this.proxyurl + this.Faker, JSON.stringify({ data }));
  }
}
