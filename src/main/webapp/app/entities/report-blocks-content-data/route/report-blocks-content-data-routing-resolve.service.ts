import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReportBlocksContentData } from '../report-blocks-content-data.model';
import { ReportBlocksContentDataService } from '../service/report-blocks-content-data.service';

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentDataRoutingResolveService implements Resolve<IReportBlocksContentData | null> {
  constructor(protected service: ReportBlocksContentDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportBlocksContentData | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reportBlocksContentData: HttpResponse<IReportBlocksContentData>) => {
          if (reportBlocksContentData.body) {
            return of(reportBlocksContentData.body);
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
