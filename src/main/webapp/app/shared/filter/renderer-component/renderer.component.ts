import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { ICellRendererAngularComp } from '@ag-grid-community/angular';

@Component({
  selector: 'app-renderer-component',
  template: ` <span>{{ value }}</span> `,
})
export class RendererComponent implements OnInit, ICellRendererAngularComp {
  value: any;

  constructor(private renderer: Renderer2, private el: ElementRef) {}

  agInit(params: any) {
    this.value = params.value;
  }

  ngOnInit() {
    this.renderer.setStyle(this.el.nativeElement, 'background-color', 'lightblue');
  }

  refresh(params: any): boolean {
    this.value = params.value;
    return true;
  }
}
