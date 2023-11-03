import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReferenceTableSettingsComponent } from '../list/reference-table-settings.component';
import { ReferenceTableSettingsDetailComponent } from '../detail/reference-table-settings-detail.component';
import { ReferenceTableSettingsUpdateComponent } from '../update/reference-table-settings-update.component';
import { ReferenceTableSettingsRoutingResolveService } from './reference-table-settings-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { ReferenceTableSettingsManageComponent } from '../manage/reference-table-settings-manage.component';

const referenceTableSettingsRoute: Routes = [
  {
    path: '',
    component: ReferenceTableSettingsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'manage',
    component: ReferenceTableSettingsManageComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReferenceTableSettingsDetailComponent,
    resolve: {
      referenceTableSettings: ReferenceTableSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReferenceTableSettingsUpdateComponent,
    resolve: {
      referenceTableSettings: ReferenceTableSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReferenceTableSettingsUpdateComponent,
    resolve: {
      referenceTableSettings: ReferenceTableSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(referenceTableSettingsRoute)],
  exports: [RouterModule],
})
export class ReferenceTableSettingsRoutingModule {}
