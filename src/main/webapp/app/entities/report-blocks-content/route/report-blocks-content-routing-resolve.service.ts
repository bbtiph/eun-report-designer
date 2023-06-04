import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReportBlocksContent } from '../report-blocks-content.model';
import { ReportBlocksContentService } from '../service/report-blocks-content.service';

@Injectable({ providedIn: 'root' })
export class ReportBlocksContentRoutingResolveService implements Resolve<IReportBlocksContent | null> {
  constructor(protected service: ReportBlocksContentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportBlocksContent | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reportBlocksContent: HttpResponse<IReportBlocksContent>) => {
          if (reportBlocksContent.body) {
            return of(reportBlocksContent.body);
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
