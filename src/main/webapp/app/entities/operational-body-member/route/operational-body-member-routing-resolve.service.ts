import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperationalBodyMember } from '../operational-body-member.model';
import { OperationalBodyMemberService } from '../service/operational-body-member.service';

@Injectable({ providedIn: 'root' })
export class OperationalBodyMemberRoutingResolveService implements Resolve<IOperationalBodyMember | null> {
  constructor(protected service: OperationalBodyMemberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperationalBodyMember | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((operationalBodyMember: HttpResponse<IOperationalBodyMember>) => {
          if (operationalBodyMember.body) {
            return of(operationalBodyMember.body);
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
