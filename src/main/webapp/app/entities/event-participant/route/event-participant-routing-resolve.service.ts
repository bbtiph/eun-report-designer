import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventParticipant } from '../event-participant.model';
import { EventParticipantService } from '../service/event-participant.service';

@Injectable({ providedIn: 'root' })
export class EventParticipantRoutingResolveService implements Resolve<IEventParticipant | null> {
  constructor(protected service: EventParticipantService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventParticipant | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventParticipant: HttpResponse<IEventParticipant>) => {
          if (eventParticipant.body) {
            return of(eventParticipant.body);
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
