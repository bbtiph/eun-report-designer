import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrivilegeService } from '../service/privilege.service';

import { PrivilegeComponent } from './privilege.component';

describe('Privilege Management Component', () => {
  let comp: PrivilegeComponent;
  let fixture: ComponentFixture<PrivilegeComponent>;
  let service: PrivilegeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'privilege', component: PrivilegeComponent }]), HttpClientTestingModule],
      declarations: [PrivilegeComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrivilegeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrivilegeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrivilegeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.privileges?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to privilegeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPrivilegeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPrivilegeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
