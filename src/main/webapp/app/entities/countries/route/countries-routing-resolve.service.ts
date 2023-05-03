import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICountries } from '../countries.model';
import { CountriesService } from '../service/countries.service';

@Injectable({ providedIn: 'root' })
export class CountriesRoutingResolveService implements Resolve<ICountries | null> {
  constructor(protected service: CountriesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICountries | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((countries: HttpResponse<ICountries>) => {
          if (countries.body) {
            return of(countries.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
