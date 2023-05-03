import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonInProjectComponent } from '../list/person-in-project.component';
import { PersonInProjectDetailComponent } from '../detail/person-in-project-detail.component';
import { PersonInProjectUpdateComponent } from '../update/person-in-project-update.component';
import { PersonInProjectRoutingResolveService } from './person-in-project-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personInProjectRoute: Routes = [
  {
    path: '',
    component: PersonInProjectComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonInProjectDetailComponent,
    resolve: {
      personInProject: PersonInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonInProjectUpdateComponent,
    resolve: {
      personInProject: PersonInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonInProjectUpdateComponent,
    resolve: {
      personInProject: PersonInProjectRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personInProjectRoute)],
  exports: [RouterModule],
})
export class PersonInProjectRoutingModule {}
