import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'project',
        data: { pageTitle: 'Projects' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'countries',
        data: { pageTitle: 'Countries' },
        loadChildren: () => import('./countries/countries.module').then(m => m.CountriesModule),
      },
      {
        path: 'eun-team',
        data: { pageTitle: 'EunTeams' },
        loadChildren: () => import('./eun-team/eun-team.module').then(m => m.EunTeamModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'Events' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'operational-body',
        data: { pageTitle: 'OperationalBodies' },
        loadChildren: () => import('./operational-body/operational-body.module').then(m => m.OperationalBodyModule),
      },
      {
        path: 'funding',
        data: { pageTitle: 'Fundings' },
        loadChildren: () => import('./funding/funding.module').then(m => m.FundingModule),
      },
      {
        path: 'country',
        data: { pageTitle: 'Countries' },
        loadChildren: () => import('./country/country.module').then(m => m.CountryModule),
      },
      {
        path: 'ministry',
        data: { pageTitle: 'Ministries' },
        loadChildren: () => import('./ministry/ministry.module').then(m => m.MinistryModule),
      },
      {
        path: 'operational-body-member',
        data: { pageTitle: 'OperationalBodyMembers' },
        loadChildren: () => import('./operational-body-member/operational-body-member.module').then(m => m.OperationalBodyMemberModule),
      },
      {
        path: 'organization',
        data: { pageTitle: 'Organizations' },
        loadChildren: () => import('./organization/organization.module').then(m => m.OrganizationModule),
      },
      {
        path: 'person',
        data: { pageTitle: 'People' },
        loadChildren: () => import('./person/person.module').then(m => m.PersonModule),
      },
      {
        path: 'eun-team-member',
        data: { pageTitle: 'EunTeamMembers' },
        loadChildren: () => import('./eun-team-member/eun-team-member.module').then(m => m.EunTeamMemberModule),
      },
      {
        path: 'event-in-organization',
        data: { pageTitle: 'EventInOrganizations' },
        loadChildren: () => import('./event-in-organization/event-in-organization.module').then(m => m.EventInOrganizationModule),
      },
      {
        path: 'event-participant',
        data: { pageTitle: 'EventParticipants' },
        loadChildren: () => import('./event-participant/event-participant.module').then(m => m.EventParticipantModule),
      },
      {
        path: 'organization-in-ministry',
        data: { pageTitle: 'OrganizationInMinistries' },
        loadChildren: () => import('./organization-in-ministry/organization-in-ministry.module').then(m => m.OrganizationInMinistryModule),
      },
      {
        path: 'organization-in-project',
        data: { pageTitle: 'OrganizationInProjects' },
        loadChildren: () => import('./organization-in-project/organization-in-project.module').then(m => m.OrganizationInProjectModule),
      },
      {
        path: 'person-in-organization',
        data: { pageTitle: 'PersonInOrganizations' },
        loadChildren: () => import('./person-in-organization/person-in-organization.module').then(m => m.PersonInOrganizationModule),
      },
      {
        path: 'person-in-project',
        data: { pageTitle: 'PersonInProjects' },
        loadChildren: () => import('./person-in-project/person-in-project.module').then(m => m.PersonInProjectModule),
      },
      {
        path: 'report',
        data: { pageTitle: 'Reports' },
        loadChildren: () => import('./report/report.module').then(m => m.ReportModule),
      },
      {
        path: 'role',
        data: { pageTitle: 'Roles' },
        loadChildren: () => import('./role/role.module').then(m => m.RoleModule),
      },
      {
        path: 'privilege',
        data: { pageTitle: 'Privileges' },
        loadChildren: () => import('./privilege/privilege.module').then(m => m.PrivilegeModule),
      },
      {
        path: 'report-blocks',
        data: { pageTitle: 'ReportBlocks' },
        loadChildren: () => import('./report-blocks/report-blocks.module').then(m => m.ReportBlocksModule),
      },
      {
        path: 'moe-contacts',
        data: { pageTitle: 'MoeContacts' },
        loadChildren: () => import('./moe-contacts/moe-contacts.module').then(m => m.MoeContactsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
