// import {Component, Input, OnInit} from '@angular/core';
// import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
//
// @Component({
//   selector: 'app-abstract-export-modal',
//   template: `
//       <div class="modal-header">
//         <h4 class="modal-title text-center" id="modal-title">Выгрузка</h4>
//         <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss()">
//           <span aria-hidden="true">&times;</span>
//         </button>
//       </div>
//       <div class="modal-body">
//         <div class="row">
//           <div class="col-md-12">
//             <div class="form-group">
//               <label class="col-form-label">Наименование:</label>
//               <input type="text" class="form-control"
//                      [disabled]="true" [value]="reportName">
//             </div>
//             <div class="form-group">
//               <label class="col-form-label">Формат:</label>
//               <ng-select name="reportFormat"
//                          bindLabel="fullname"
//                          [items]="reportFormats"
//                          [(ngModel)]="format"
//               >
//                 <ng-template ng-option-tmp let-item="item">
//                   <div [title]="item.name">{{item.fullname}} ({{item.name}})</div>
//                 </ng-template>
//               </ng-select>
//             </div>
//           </div>
//         </div>
//       </div>
//       <div class="modal-footer">
//         <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss()">Закрыть</button>
//         <button type="button" class="btn btn-success" (click)="modal.close({type: reportTypes[0], format: format, lang: lang})"
//                 >Выгрузить
//         </button>
//       </div>`
// })
// export class AbstractExportModal implements OnInit {
//   isDisabled = true;
//   @Input() public type: string;
//   @Input() public format = {id: 1, name: 'PDF', fullname: 'Acrobat Reader'};
//   @Input() public reports;
//   @Input() public reportName;
//   public lang = 'ru';
//
//   constructor(public modal: NgbActiveModal) {
//   }
//
//   reportTypes = [];
//
//   reportFormats = [
//     {id: 1, name: 'PDF', fullname: 'Acrobat Reader'},
//     {id: 2, name: 'DOCX', fullname: 'MS Word'},
//     {id: 3, name: 'XLSX', fullname: 'MS Excel'},
//     {id: 4, name: 'DOC', fullname: 'MS Word'},
//   ];
//
//   ngOnInit(): void {
//     if (Array.isArray(this.reports)) {
//       let i = 0;
//       this.reports.forEach(obj => {
//         const key = Object.keys(obj)[0];
//         const keyReportName = Object.keys(obj)[1];
//         this.reportName = obj[keyReportName];
//         // @ts-ignore
//         this.reportTypes.push({id: ++i, name: obj[key], reportName: obj[keyReportName]});
//       });
//     } else {
//       // @ts-ignore
//       this.reportTypes.push({id: 1, name: this.reports, reportName: this.reportName});
//     }
//   }
//
//   onChange(item: any) {
//     if (item.id === 1001) {
//       this.format = {id: 1001, name: 'DOCX', fullname: 'MS Word'};
//     }
//     this.isDisabled = item.id === 1001;
//   }
// }
