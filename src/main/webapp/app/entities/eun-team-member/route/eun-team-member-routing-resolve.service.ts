import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEunTeamMember } from '../eun-team-member.model';
import { EunTeamMemberService } from '../service/eun-team-member.service';

@Injectable({ providedIn: 'root' })
export class EunTeamMemberRoutingResolveService implements Resolve<IEunTeamMember | null> {
  constructor(protected service: EunTeamMemberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEunTeamMember | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eunTeamMember: HttpResponse<IEunTeamMember>) => {
          if (eunTeamMember.body) {
            return of(eunTeamMember.body);
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
