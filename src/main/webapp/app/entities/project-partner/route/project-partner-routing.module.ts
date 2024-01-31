import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectPartnerComponent } from '../list/project-partner.component';
import { ProjectPartnerDetailComponent } from '../detail/project-partner-detail.component';
import { ProjectPartnerUpdateComponent } from '../update/project-partner-update.component';
import { ProjectPartnerRoutingResolveService } from './project-partner-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectPartnerRoute: Routes = [
  {
    path: '',
    component: ProjectPartnerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectPartnerDetailComponent,
    resolve: {
      projectPartner: ProjectPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectPartnerUpdateComponent,
    resolve: {
      projectPartner: ProjectPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectPartnerUpdateComponent,
    resolve: {
      projectPartner: ProjectPartnerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectPartnerRoute)],
  exports: [RouterModule],
})
export class ProjectPartnerRoutingModule {}
