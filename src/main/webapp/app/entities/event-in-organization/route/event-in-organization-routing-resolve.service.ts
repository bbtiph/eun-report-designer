import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventInOrganization } from '../event-in-organization.model';
import { EventInOrganizationService } from '../service/event-in-organization.service';

@Injectable({ providedIn: 'root' })
export class EventInOrganizationRoutingResolveService implements Resolve<IEventInOrganization | null> {
  constructor(protected service: EventInOrganizationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventInOrganization | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventInOrganization: HttpResponse<IEventInOrganization>) => {
          if (eventInOrganization.body) {
            return of(eventInOrganization.body);
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
