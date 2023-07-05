import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkingGroupReferences } from '../working-group-references.model';
import { WorkingGroupReferencesService } from '../service/working-group-references.service';

@Injectable({ providedIn: 'root' })
export class WorkingGroupReferencesRoutingResolveService implements Resolve<IWorkingGroupReferences | null> {
  constructor(protected service: WorkingGroupReferencesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkingGroupReferences | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((workingGroupReferences: HttpResponse<IWorkingGroupReferences>) => {
          if (workingGroupReferences.body) {
            return of(workingGroupReferences.body);
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
