import { JsonEditorComponent, JsonEditorOptions } from 'ang-jsoneditor';

import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';

import { FetchServer } from './server.service';
@Component({
  selector: 'json-editor-toolbar',
  templateUrl: './json-editor-toolbar.component.html',
  styleUrls: ['./json-editor-toolbar.component.css'],
})
export class JsonEditorToolbarComponent implements OnInit {
  // @ts-ignore
  @ViewChild(JsonEditorComponent) editor: JsonEditorComponent;
  @Input() public schema: any;
  @Input() public action: any;
  @Output() columnsUpdated = new EventEmitter<any>();

  options = new JsonEditorOptions();
  disableBtn = true;
  data: any;

  constructor(private _service: FetchServer) {
    this.options.statusBar = false;
    this.options.onChange = () => this.fetchdata();
  }

  ngOnInit(): void {
    this.options.mode = this.action == 'edit' ? 'code' : 'view';
    this.options.modes = this.action == 'edit' ? ['code', 'text', 'tree', 'view'] : ['view'];
    this.data = JSON.parse(this.schema);
  }

  fetchdata() {
    try {
      this.disableBtn = true;
      const updatedColumns = this.editor.get();
      this.columnsUpdated.emit(JSON.stringify(updatedColumns));
    } catch (e) {
      this.disableBtn = false;
    }
  }
}
