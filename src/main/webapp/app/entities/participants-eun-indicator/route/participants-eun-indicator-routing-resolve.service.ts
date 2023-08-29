import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParticipantsEunIndicator } from '../participants-eun-indicator.model';
import { ParticipantsEunIndicatorService } from '../service/participants-eun-indicator.service';

@Injectable({ providedIn: 'root' })
export class ParticipantsEunIndicatorRoutingResolveService implements Resolve<IParticipantsEunIndicator | null> {
  constructor(protected service: ParticipantsEunIndicatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParticipantsEunIndicator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((participantsEunIndicator: HttpResponse<IParticipantsEunIndicator>) => {
          if (participantsEunIndicator.body) {
            return of(participantsEunIndicator.body);
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
