import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventReferences } from '../event-references.model';
import { EventReferencesService } from '../service/event-references.service';

@Injectable({ providedIn: 'root' })
export class EventReferencesRoutingResolveService implements Resolve<IEventReferences | null> {
  constructor(protected service: EventReferencesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventReferences | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventReferences: HttpResponse<IEventReferences>) => {
          if (eventReferences.body) {
            return of(eventReferences.body);
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
