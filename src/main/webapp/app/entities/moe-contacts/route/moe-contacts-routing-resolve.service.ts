import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoeContacts } from '../moe-contacts.model';
import { MoeContactsService } from '../service/moe-contacts.service';

@Injectable({ providedIn: 'root' })
export class MoeContactsRoutingResolveService implements Resolve<IMoeContacts | null> {
  constructor(protected service: MoeContactsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoeContacts | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moeContacts: HttpResponse<IMoeContacts>) => {
          if (moeContacts.body) {
            return of(moeContacts.body);
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
