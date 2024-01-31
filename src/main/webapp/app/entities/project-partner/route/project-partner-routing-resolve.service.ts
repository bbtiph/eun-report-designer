import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectPartner } from '../project-partner.model';
import { ProjectPartnerService } from '../service/project-partner.service';

@Injectable({ providedIn: 'root' })
export class ProjectPartnerRoutingResolveService implements Resolve<IProjectPartner | null> {
  constructor(protected service: ProjectPartnerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectPartner | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectPartner: HttpResponse<IProjectPartner>) => {
          if (projectPartner.body) {
            return of(projectPartner.body);
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
