import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMOEParticipationReferences } from '../moe-participation-references.model';
import { MOEParticipationReferencesService } from '../service/moe-participation-references.service';

@Injectable({ providedIn: 'root' })
export class MOEParticipationReferencesRoutingResolveService implements Resolve<IMOEParticipationReferences | null> {
  constructor(protected service: MOEParticipationReferencesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMOEParticipationReferences | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mOEParticipationReferences: HttpResponse<IMOEParticipationReferences>) => {
          if (mOEParticipationReferences.body) {
            return of(mOEParticipationReferences.body);
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
