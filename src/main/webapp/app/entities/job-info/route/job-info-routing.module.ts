import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JobInfoComponent } from '../list/job-info.component';
import { JobInfoDetailComponent } from '../detail/job-info-detail.component';
import { JobInfoUpdateComponent } from '../update/job-info-update.component';
import { JobInfoRoutingResolveService } from './job-info-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const jobInfoRoute: Routes = [
  {
    path: '',
    component: JobInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobInfoDetailComponent,
    resolve: {
      jobInfo: JobInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobInfoUpdateComponent,
    resolve: {
      jobInfo: JobInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobInfoUpdateComponent,
    resolve: {
      jobInfo: JobInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(jobInfoRoute)],
  exports: [RouterModule],
})
export class JobInfoRoutingModule {}
