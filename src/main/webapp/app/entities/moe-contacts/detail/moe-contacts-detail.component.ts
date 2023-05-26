import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoeContacts } from '../moe-contacts.model';

@Component({
  selector: 'jhi-moe-contacts-detail',
  templateUrl: './moe-contacts-detail.component.html',
})
export class MoeContactsDetailComponent implements OnInit {
  moeContacts: IMoeContacts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moeContacts }) => {
      this.moeContacts = moeContacts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
