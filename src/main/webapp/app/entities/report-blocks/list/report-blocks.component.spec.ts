// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { HttpHeaders, HttpResponse } from '@angular/common/http';
// import { HttpClientTestingModule } from '@angular/common/http/testing';
// import { ActivatedRoute } from '@angular/router';
// import { RouterTestingModule } from '@angular/router/testing';
// import { of } from 'rxjs';
//
// import { ReportBlocksService } from '../service/report-blocks.service';
//
// import { ReportBlocksComponent } from './report-blocks.component';
//
// describe('ReportBlocks Management Component', () => {
//   let comp: ReportBlocksComponent;
//   let fixture: ComponentFixture<ReportBlocksComponent>;
//   let service: ReportBlocksService;
//
//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [RouterTestingModule.withRoutes([{ path: 'report-blocks', component: ReportBlocksComponent }]), HttpClientTestingModule],
//       declarations: [ReportBlocksComponent],
//       providers: [
//         {
//           provide: ActivatedRoute,
//           useValue: {
//             data: of({
//               defaultSort: 'id,asc',
//             }),
//             queryParamMap: of(
//               jest.requireActual('@angular/router').convertToParamMap({
//                 page: '1',
//                 size: '1',
//                 sort: 'id,desc',
//               })
//             ),
//             snapshot: { queryParams: {} },
//           },
//         },
//       ],
//     })
//       .overrideTemplate(ReportBlocksComponent, '')
//       .compileComponents();
//
//     fixture = TestBed.createComponent(ReportBlocksComponent);
//     comp = fixture.componentInstance;
//     service = TestBed.inject(ReportBlocksService);
//
//     const headers = new HttpHeaders();
//     jest.spyOn(service, 'query').mockReturnValue(
//       of(
//         new HttpResponse({
//           body: [{ id: 123 }],
//           headers,
//         })
//       )
//     );
//   });
//
//   it('Should call load all on init', () => {
//     // WHEN
//     comp.ngOnInit();
//
//     // THEN
//     expect(service.query).toHaveBeenCalled();
//     expect(comp.reportBlocks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
//   });
//
//   describe('trackId', () => {
//     it('Should forward to reportBlocksService', () => {
//       const entity = { id: 123 };
//       jest.spyOn(service, 'getReportBlocksIdentifier');
//       const id = comp.trackId(0, entity);
//       expect(service.getReportBlocksIdentifier).toHaveBeenCalledWith(entity);
//       expect(id).toBe(entity.id);
//     });
//   });
// });
