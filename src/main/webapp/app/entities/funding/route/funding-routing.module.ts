import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FundingComponent } from '../list/funding.component';
import { FundingDetailComponent } from '../detail/funding-detail.component';
import { FundingUpdateComponent } from '../update/funding-update.component';
import { FundingRoutingResolveService } from './funding-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fundingRoute: Routes = [
  {
    path: '',
    component: FundingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FundingDetailComponent,
    resolve: {
      funding: FundingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FundingUpdateComponent,
    resolve: {
      funding: FundingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FundingUpdateComponent,
    resolve: {
      funding: FundingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fundingRoute)],
  exports: [RouterModule],
})
export class FundingRoutingModule {}
