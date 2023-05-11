import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrivilege } from '../privilege.model';
import { PrivilegeService } from '../service/privilege.service';

@Injectable({ providedIn: 'root' })
export class PrivilegeRoutingResolveService implements Resolve<IPrivilege | null> {
  constructor(protected service: PrivilegeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrivilege | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((privilege: HttpResponse<IPrivilege>) => {
          if (privilege.body) {
            return of(privilege.body);
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
