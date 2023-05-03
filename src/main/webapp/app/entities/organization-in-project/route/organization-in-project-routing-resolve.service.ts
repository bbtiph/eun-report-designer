import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationInProject } from '../organization-in-project.model';
import { OrganizationInProjectService } from '../service/organization-in-project.service';

@Injectable({ providedIn: 'root' })
export class OrganizationInProjectRoutingResolveService implements Resolve<IOrganizationInProject | null> {
  constructor(protected service: OrganizationInProjectService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationInProject | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organizationInProject: HttpResponse<IOrganizationInProject>) => {
          if (organizationInProject.body) {
            return of(organizationInProject.body);
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
