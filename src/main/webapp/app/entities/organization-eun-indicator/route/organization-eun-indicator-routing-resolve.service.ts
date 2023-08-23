import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationEunIndicator } from '../organization-eun-indicator.model';
import { OrganizationEunIndicatorService } from '../service/organization-eun-indicator.service';

@Injectable({ providedIn: 'root' })
export class OrganizationEunIndicatorRoutingResolveService implements Resolve<IOrganizationEunIndicator | null> {
  constructor(protected service: OrganizationEunIndicatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationEunIndicator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organizationEunIndicator: HttpResponse<IOrganizationEunIndicator>) => {
          if (organizationEunIndicator.body) {
            return of(organizationEunIndicator.body);
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
