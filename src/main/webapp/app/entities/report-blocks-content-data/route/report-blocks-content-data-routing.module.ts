import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportBlocksContentDataComponent } from '../list/report-blocks-content-data.component';
import { ReportBlocksContentDataDetailComponent } from '../detail/report-blocks-content-data-detail.component';
import { ReportBlocksContentDataUpdateComponent } from '../update/report-blocks-content-data-update.component';
import { ReportBlocksContentDataRoutingResolveService } from './report-blocks-content-data-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reportBlocksContentDataRoute: Routes = [
  {
    path: '',
    component: ReportBlocksContentDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportBlocksContentDataDetailComponent,
    resolve: {
      reportBlocksContentData: ReportBlocksContentDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportBlocksContentDataUpdateComponent,
    resolve: {
      reportBlocksContentData: ReportBlocksContentDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportBlocksContentDataUpdateComponent,
    resolve: {
      reportBlocksContentData: ReportBlocksContentDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportBlocksContentDataRoute)],
  exports: [RouterModule],
})
export class ReportBlocksContentDataRoutingModule {}
