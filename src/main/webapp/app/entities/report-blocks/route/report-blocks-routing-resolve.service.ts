import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReportBlocks } from '../report-blocks.model';
import { ReportBlocksService } from '../service/report-blocks.service';

@Injectable({ providedIn: 'root' })
export class ReportBlocksRoutingResolveService implements Resolve<IReportBlocks | null> {
  constructor(protected service: ReportBlocksService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportBlocks | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reportBlocks: HttpResponse<IReportBlocks>) => {
          if (reportBlocks.body) {
            return of(reportBlocks.body);
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
