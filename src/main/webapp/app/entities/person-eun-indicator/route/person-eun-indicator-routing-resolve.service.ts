import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonEunIndicator } from '../person-eun-indicator.model';
import { PersonEunIndicatorService } from '../service/person-eun-indicator.service';

@Injectable({ providedIn: 'root' })
export class PersonEunIndicatorRoutingResolveService implements Resolve<IPersonEunIndicator | null> {
  constructor(protected service: PersonEunIndicatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonEunIndicator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personEunIndicator: HttpResponse<IPersonEunIndicator>) => {
          if (personEunIndicator.body) {
            return of(personEunIndicator.body);
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
