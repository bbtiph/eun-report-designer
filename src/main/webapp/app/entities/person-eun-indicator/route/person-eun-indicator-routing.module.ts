import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonEunIndicatorComponent } from '../list/person-eun-indicator.component';
import { PersonEunIndicatorDetailComponent } from '../detail/person-eun-indicator-detail.component';
import { PersonEunIndicatorUpdateComponent } from '../update/person-eun-indicator-update.component';
import { PersonEunIndicatorRoutingResolveService } from './person-eun-indicator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personEunIndicatorRoute: Routes = [
  {
    path: '',
    component: PersonEunIndicatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonEunIndicatorDetailComponent,
    resolve: {
      personEunIndicator: PersonEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonEunIndicatorUpdateComponent,
    resolve: {
      personEunIndicator: PersonEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonEunIndicatorUpdateComponent,
    resolve: {
      personEunIndicator: PersonEunIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personEunIndicatorRoute)],
  exports: [RouterModule],
})
export class PersonEunIndicatorRoutingModule {}
