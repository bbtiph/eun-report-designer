import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GeneratedReportComponent } from '../list/generated-report.component';
import { GeneratedReportDetailComponent } from '../detail/generated-report-detail.component';
import { GeneratedReportUpdateComponent } from '../update/generated-report-update.component';
import { GeneratedReportRoutingResolveService } from './generated-report-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const generatedReportRoute: Routes = [
  {
    path: '',
    component: GeneratedReportComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeneratedReportDetailComponent,
    resolve: {
      generatedReport: GeneratedReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeneratedReportUpdateComponent,
    resolve: {
      generatedReport: GeneratedReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeneratedReportUpdateComponent,
    resolve: {
      generatedReport: GeneratedReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(generatedReportRoute)],
  exports: [RouterModule],
})
export class GeneratedReportRoutingModule {}
