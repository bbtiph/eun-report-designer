import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperationalBody } from '../operational-body.model';
import { OperationalBodyService } from '../service/operational-body.service';

@Injectable({ providedIn: 'root' })
export class OperationalBodyRoutingResolveService implements Resolve<IOperationalBody | null> {
  constructor(protected service: OperationalBodyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperationalBody | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((operationalBody: HttpResponse<IOperationalBody>) => {
          if (operationalBody.body) {
            return of(operationalBody.body);
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
