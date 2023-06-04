import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportBlocksContentComponent } from '../list/report-blocks-content.component';
import { ReportBlocksContentDetailComponent } from '../detail/report-blocks-content-detail.component';
import { ReportBlocksContentUpdateComponent } from '../update/report-blocks-content-update.component';
import { ReportBlocksContentRoutingResolveService } from './report-blocks-content-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reportBlocksContentRoute: Routes = [
  {
    path: '',
    component: ReportBlocksContentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportBlocksContentDetailComponent,
    resolve: {
      reportBlocksContent: ReportBlocksContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportBlocksContentUpdateComponent,
    resolve: {
      reportBlocksContent: ReportBlocksContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportBlocksContentUpdateComponent,
    resolve: {
      reportBlocksContent: ReportBlocksContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportBlocksContentRoute)],
  exports: [RouterModule],
})
export class ReportBlocksContentRoutingModule {}
