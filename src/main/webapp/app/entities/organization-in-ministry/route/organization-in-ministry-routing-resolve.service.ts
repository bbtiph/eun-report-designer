import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationInMinistry } from '../organization-in-ministry.model';
import { OrganizationInMinistryService } from '../service/organization-in-ministry.service';

@Injectable({ providedIn: 'root' })
export class OrganizationInMinistryRoutingResolveService implements Resolve<IOrganizationInMinistry | null> {
  constructor(protected service: OrganizationInMinistryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationInMinistry | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organizationInMinistry: HttpResponse<IOrganizationInMinistry>) => {
          if (organizationInMinistry.body) {
            return of(organizationInMinistry.body);
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
