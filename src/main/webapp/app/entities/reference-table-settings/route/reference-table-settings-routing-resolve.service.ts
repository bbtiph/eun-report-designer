import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReferenceTableSettings } from '../reference-table-settings.model';
import { ReferenceTableSettingsService } from '../service/reference-table-settings.service';

@Injectable({ providedIn: 'root' })
export class ReferenceTableSettingsRoutingResolveService implements Resolve<IReferenceTableSettings | null> {
  constructor(protected service: ReferenceTableSettingsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReferenceTableSettings | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((referenceTableSettings: HttpResponse<IReferenceTableSettings>) => {
          if (referenceTableSettings.body) {
            return of(referenceTableSettings.body);
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
