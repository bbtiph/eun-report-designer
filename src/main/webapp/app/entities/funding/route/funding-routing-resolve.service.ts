import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFunding } from '../funding.model';
import { FundingService } from '../service/funding.service';

@Injectable({ providedIn: 'root' })
export class FundingRoutingResolveService implements Resolve<IFunding | null> {
  constructor(protected service: FundingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFunding | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((funding: HttpResponse<IFunding>) => {
          if (funding.body) {
            return of(funding.body);
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
