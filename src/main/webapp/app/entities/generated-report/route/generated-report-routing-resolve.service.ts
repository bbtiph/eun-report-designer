import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGeneratedReport } from '../generated-report.model';
import { GeneratedReportService } from '../service/generated-report.service';

@Injectable({ providedIn: 'root' })
export class GeneratedReportRoutingResolveService implements Resolve<IGeneratedReport | null> {
  constructor(protected service: GeneratedReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeneratedReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((generatedReport: HttpResponse<IGeneratedReport>) => {
          if (generatedReport.body) {
            return of(generatedReport.body);
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
