jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ReportBlocksContentService } from '../service/report-blocks-content.service';

import { ReportBlocksContentDeleteDialogComponent } from './report-blocks-content-delete-dialog.component';

describe('ReportBlocksContent Management Delete Component', () => {
  let comp: ReportBlocksContentDeleteDialogComponent;
  let fixture: ComponentFixture<ReportBlocksContentDeleteDialogComponent>;
  let service: ReportBlocksContentService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReportBlocksContentDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ReportBlocksContentDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReportBlocksContentDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReportBlocksContentService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
