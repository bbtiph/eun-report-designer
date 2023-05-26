import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MoeContactsService } from '../service/moe-contacts.service';

import { MoeContactsComponent } from './moe-contacts.component';

describe('MoeContacts Management Component', () => {
  let comp: MoeContactsComponent;
  let fixture: ComponentFixture<MoeContactsComponent>;
  let service: MoeContactsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'moe-contacts', component: MoeContactsComponent }]), HttpClientTestingModule],
      declarations: [MoeContactsComponent],
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
      .overrideTemplate(MoeContactsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoeContactsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MoeContactsService);

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
    expect(comp.moeContacts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to moeContactsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMoeContactsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMoeContactsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
