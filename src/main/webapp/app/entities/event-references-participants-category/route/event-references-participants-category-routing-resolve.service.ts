import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventReferencesParticipantsCategory } from '../event-references-participants-category.model';
import { EventReferencesParticipantsCategoryService } from '../service/event-references-participants-category.service';

@Injectable({ providedIn: 'root' })
export class EventReferencesParticipantsCategoryRoutingResolveService implements Resolve<IEventReferencesParticipantsCategory | null> {
  constructor(protected service: EventReferencesParticipantsCategoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventReferencesParticipantsCategory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventReferencesParticipantsCategory: HttpResponse<IEventReferencesParticipantsCategory>) => {
          if (eventReferencesParticipantsCategory.body) {
            return of(eventReferencesParticipantsCategory.body);
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
