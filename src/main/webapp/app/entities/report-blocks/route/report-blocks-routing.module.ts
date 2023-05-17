import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportBlocksComponent } from '../list/report-blocks.component';
import { ReportBlocksDetailComponent } from '../detail/report-blocks-detail.component';
import { ReportBlocksUpdateComponent } from '../update/report-blocks-update.component';
import { ReportBlocksRoutingResolveService } from './report-blocks-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reportBlocksRoute: Routes = [
  {
    path: '',
    component: ReportBlocksComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportBlocksDetailComponent,
    resolve: {
      reportBlocks: ReportBlocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportBlocksUpdateComponent,
    resolve: {
      reportBlocks: ReportBlocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportBlocksUpdateComponent,
    resolve: {
      reportBlocks: ReportBlocksRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportBlocksRoute)],
  exports: [RouterModule],
})
export class ReportBlocksRoutingModule {}
