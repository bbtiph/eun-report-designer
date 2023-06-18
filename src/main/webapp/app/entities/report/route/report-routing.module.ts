import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportComponent } from '../list/report.component';
import { ReportDetailComponent } from '../detail/report-detail.component';
import { ReportUpdateComponent } from '../update/report-update.component';
import { ReportRoutingResolveService } from './report-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { ReportBlocksManageComponent } from '../report-blocks-manage/report-blocks-manage.component';
import { ReportBlockEdit } from '../report-blocks-manage/report-block-edit/report-block-edit.component';
import { ReportCountryContentComponent } from '../report-country-content/report-country-content.component';

const reportRoute: Routes = [
  {
    path: '',
    component: ReportComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportDetailComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/:country/blocks',
    component: ReportBlocksManageComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/report-country',
    component: ReportCountryContentComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'blocks/edit',
    component: ReportBlockEdit,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportRoute)],
  exports: [RouterModule],
})
export class ReportRoutingModule {}
