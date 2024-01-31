import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectPartnerComponent } from './list/project-partner.component';
import { ProjectPartnerDetailComponent } from './detail/project-partner-detail.component';
import { ProjectPartnerUpdateComponent } from './update/project-partner-update.component';
import { ProjectPartnerDeleteDialogComponent } from './delete/project-partner-delete-dialog.component';
import { ProjectPartnerRoutingModule } from './route/project-partner-routing.module';

@NgModule({
  imports: [SharedModule, ProjectPartnerRoutingModule],
  declarations: [
    ProjectPartnerComponent,
    ProjectPartnerDetailComponent,
    ProjectPartnerUpdateComponent,
    ProjectPartnerDeleteDialogComponent,
  ],
})
export class ProjectPartnerModule {}
