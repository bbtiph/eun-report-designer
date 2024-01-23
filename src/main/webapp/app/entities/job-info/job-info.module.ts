import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JobInfoComponent } from './list/job-info.component';
import { JobInfoDetailComponent } from './detail/job-info-detail.component';
import { JobInfoUpdateComponent } from './update/job-info-update.component';
import { JobInfoDeleteDialogComponent } from './delete/job-info-delete-dialog.component';
import { JobInfoRoutingModule } from './route/job-info-routing.module';

@NgModule({
  imports: [SharedModule, JobInfoRoutingModule],
  declarations: [JobInfoComponent, JobInfoDetailComponent, JobInfoUpdateComponent, JobInfoDeleteDialogComponent],
})
export class JobInfoModule {}
