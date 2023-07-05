import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WorkingGroupReferencesComponent } from '../list/working-group-references.component';
import { WorkingGroupReferencesDetailComponent } from '../detail/working-group-references-detail.component';
import { WorkingGroupReferencesUpdateComponent } from '../update/working-group-references-update.component';
import { WorkingGroupReferencesRoutingResolveService } from './working-group-references-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const workingGroupReferencesRoute: Routes = [
  {
    path: '',
    component: WorkingGroupReferencesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkingGroupReferencesDetailComponent,
    resolve: {
      workingGroupReferences: WorkingGroupReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkingGroupReferencesUpdateComponent,
    resolve: {
      workingGroupReferences: WorkingGroupReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkingGroupReferencesUpdateComponent,
    resolve: {
      workingGroupReferences: WorkingGroupReferencesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(workingGroupReferencesRoute)],
  exports: [RouterModule],
})
export class WorkingGroupReferencesRoutingModule {}
