import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJobInfo } from '../job-info.model';
import { JobInfoService } from '../service/job-info.service';

@Injectable({ providedIn: 'root' })
export class JobInfoRoutingResolveService implements Resolve<IJobInfo | null> {
  constructor(protected service: JobInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobInfo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((jobInfo: HttpResponse<IJobInfo>) => {
          if (jobInfo.body) {
            return of(jobInfo.body);
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
