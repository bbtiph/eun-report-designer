import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonInOrganizationComponent } from '../list/person-in-organization.component';
import { PersonInOrganizationDetailComponent } from '../detail/person-in-organization-detail.component';
import { PersonInOrganizationUpdateComponent } from '../update/person-in-organization-update.component';
import { PersonInOrganizationRoutingResolveService } from './person-in-organization-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personInOrganizationRoute: Routes = [
  {
    path: '',
    component: PersonInOrganizationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonInOrganizationDetailComponent,
    resolve: {
      personInOrganization: PersonInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonInOrganizationUpdateComponent,
    resolve: {
      personInOrganization: PersonInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonInOrganizationUpdateComponent,
    resolve: {
      personInOrganization: PersonInOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personInOrganizationRoute)],
  exports: [RouterModule],
})
export class PersonInOrganizationRoutingModule {}
