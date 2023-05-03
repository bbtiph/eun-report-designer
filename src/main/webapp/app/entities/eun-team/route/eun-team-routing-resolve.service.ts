import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEunTeam } from '../eun-team.model';
import { EunTeamService } from '../service/eun-team.service';

@Injectable({ providedIn: 'root' })
export class EunTeamRoutingResolveService implements Resolve<IEunTeam | null> {
  constructor(protected service: EunTeamService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEunTeam | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eunTeam: HttpResponse<IEunTeam>) => {
          if (eunTeam.body) {
            return of(eunTeam.body);
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
