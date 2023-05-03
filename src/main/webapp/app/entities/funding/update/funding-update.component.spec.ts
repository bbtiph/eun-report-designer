import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FundingFormService } from './funding-form.service';
import { FundingService } from '../service/funding.service';
import { IFunding } from '../funding.model';

import { FundingUpdateComponent } from './funding-update.component';

describe('Funding Management Update Component', () => {
  let comp: FundingUpdateComponent;
  let fixture: ComponentFixture<FundingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fundingFormService: FundingFormService;
  let fundingService: FundingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FundingUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FundingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FundingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fundingFormService = TestBed.inject(FundingFormService);
    fundingService = TestBed.inject(FundingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const funding: IFunding = { id: 456 };

      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      expect(comp.funding).toEqual(funding);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingFormService, 'getFunding').mockReturnValue(funding);
      jest.spyOn(fundingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funding }));
      saveSubject.complete();

      // THEN
      expect(fundingFormService.getFunding).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fundingService.update).toHaveBeenCalledWith(expect.objectContaining(funding));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingFormService, 'getFunding').mockReturnValue({ id: null });
      jest.spyOn(fundingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funding }));
      saveSubject.complete();

      // THEN
      expect(fundingFormService.getFunding).toHaveBeenCalled();
      expect(fundingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFunding>>();
      const funding = { id: 123 };
      jest.spyOn(fundingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fundingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
