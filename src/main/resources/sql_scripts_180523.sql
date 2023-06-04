create table databasechangeloglock
(
    id          integer not null
        primary key,
    locked      boolean not null,
    lockgranted timestamp,
    lockedby    varchar(255)
);

alter table databasechangeloglock
    owner to "eunReportDesigner";

create table databasechangelog
(
    id            varchar(255) not null,
    author        varchar(255) not null,
    filename      varchar(255) not null,
    dateexecuted  timestamp    not null,
    orderexecuted integer      not null,
    exectype      varchar(10)  not null,
    md5sum        varchar(35),
    description   varchar(255),
    comments      varchar(255),
    tag           varchar(255),
    liquibase     varchar(20),
    contexts      varchar(255),
    labels        varchar(255),
    deployment_id varchar(10)
);

alter table databasechangelog
    owner to "eunReportDesigner";

create table jhi_user
(
    id                 bigint      not null
        primary key,
    login              varchar(50) not null
        constraint ux_user_login
            unique,
    password_hash      varchar(60) not null,
    first_name         varchar(50),
    last_name          varchar(50),
    email              varchar(191)
        constraint ux_user_email
            unique,
    image_url          varchar(256),
    activated          boolean     not null,
    lang_key           varchar(10),
    activation_key     varchar(20),
    reset_key          varchar(20),
    created_by         varchar(50) not null,
    created_date       timestamp,
    reset_date         timestamp,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

alter table jhi_user
    owner to "eunReportDesigner";

create table jhi_authority
(
    name varchar(50) not null
        primary key
);

alter table jhi_authority
    owner to "eunReportDesigner";

create table jhi_user_authority
(
    user_id        bigint      not null
        constraint fk_user_id
            references jhi_user,
    authority_name varchar(50) not null
        constraint fk_authority_name
            references jhi_authority,
    primary key (user_id, authority_name)
);

alter table jhi_user_authority
    owner to "eunReportDesigner";

create table countries
(
    id           bigint not null
        primary key,
    country_name varchar(255)
);

alter table countries
    owner to "eunReportDesigner";

create table eun_team
(
    id          bigint not null
        primary key,
    name        varchar(255),
    description varchar(255)
);

alter table eun_team
    owner to "eunReportDesigner";

create table event
(
    id              bigint not null
        primary key,
    type            varchar(255),
    location        varchar(255),
    title           varchar(255),
    description     varchar(255),
    start_date      date,
    end_date        date,
    url             varchar(255),
    eun_contact     varchar(255),
    course_modules  varchar(255),
    course_duration integer,
    course_type     varchar(255),
    modules         integer,
    status          varchar(255),
    engagement_rate integer,
    completion_rate integer,
    name            varchar(255)
);

alter table event
    owner to "eunReportDesigner";

create table operational_body
(
    id          bigint       not null
        primary key,
    name        varchar(255) not null,
    acronym     varchar(255),
    description varchar(255),
    type        varchar(255),
    status      varchar(255)
);

alter table operational_body
    owner to "eunReportDesigner";

create table funding
(
    id          bigint not null
        primary key,
    name        varchar(255),
    type        varchar(255),
    parent_id   bigint,
    description varchar(255)
);

alter table funding
    owner to "eunReportDesigner";

create table project
(
    id                                   bigint       not null
        primary key,
    status                               varchar(255) not null,
    supported_country_ids                varchar(255),
    supported_language_ids               varchar(255),
    name                                 varchar(255) not null,
    acronym                              varchar(255),
    period                               varchar(255),
    description                          varchar(255) not null,
    contact_email                        varchar(255),
    contact_name                         varchar(255),
    total_budget                         bigint,
    total_funding                        bigint,
    total_budget_for_eun                 bigint,
    total_funding_for_eun                bigint,
    funding_value                        bigint,
    percentage_of_funding                bigint,
    percentage_of_indirect_costs         bigint,
    percentage_of_funding_for_eun        bigint,
    percentage_of_indirect_costs_for_eun bigint,
    funding_extra_comment                varchar(255),
    programme                            varchar(255),
    eu_dg                                varchar(255),
    role_of_eun                          varchar(255),
    summary                              varchar(255),
    main_tasks                           varchar(255),
    expected_outcomes                    varchar(255),
    eu_call_reference                    varchar(255),
    eu_project_reference                 varchar(255),
    eu_call_deadline                     varchar(255),
    project_manager                      varchar(255),
    reference_number_of_project          integer,
    eun_team                             varchar(255),
    sys_creat_timestamp                  timestamp,
    sys_creat_ip_address                 varchar(255),
    sys_modif_timestamp                  timestamp,
    sys_modif_ip_address                 varchar(255),
    funding_id                           bigint
        constraint fk_project__funding_id
            references funding
);

alter table project
    owner to "eunReportDesigner";

create table country
(
    id                         bigint not null
        primary key,
    country_name               varchar(255),
    ministry_id                bigint,
    operational_body_member_id bigint,
    organization_id            bigint,
    person_id                  bigint
);

alter table country
    owner to "eunReportDesigner";

create table ministry
(
    id                            bigint not null
        primary key,
    languages                     varchar(255),
    formal_name                   varchar(255),
    english_name                  varchar(255),
    acronym                       varchar(255),
    description                   varchar(255),
    website                       varchar(255),
    steercom_member_name          varchar(255),
    steercom_member_email         varchar(255),
    postal_address_region         varchar(255),
    postal_address_postal_code    varchar(255),
    postal_address_city           varchar(255),
    postal_address_street         varchar(255),
    status                        varchar(255),
    eun_contact_firstname         varchar(255),
    eun_contact_lastname          varchar(255),
    eun_contact_email             varchar(255),
    invoicing_address             varchar(255),
    financial_corresponding_email varchar(255),
    country_id                    bigint
);

alter table ministry
    owner to "eunReportDesigner";

create table operational_body_member
(
    id                    bigint not null
        primary key,
    person_id             bigint,
    position              varchar(255),
    start_date            date,
    end_date              date,
    department            varchar(255),
    eun_contact_firstname varchar(255),
    eun_contact_lastname  varchar(255),
    cooperation_field     varchar(255),
    status                varchar(255),
    country_id            bigint
);

alter table operational_body_member
    owner to "eunReportDesigner";

create table organization
(
    id                  bigint       not null
        primary key,
    eun_db_id           bigint,
    status              varchar(255) not null,
    official_name       varchar(255) not null,
    description         varchar(255),
    type                varchar(255),
    address             varchar(255),
    latitude            integer,
    longitude           integer,
    max_age             integer,
    min_age             integer,
    area                integer,
    specialization      varchar(255),
    number_of_students  varchar(255),
    hardware_used       boolean,
    ict_used            boolean,
    website             varchar(255),
    image               bytea,
    image_content_type  varchar(255),
    telephone           varchar(255),
    fax                 varchar(255),
    email               varchar(255),
    organisation_number varchar(255),
    country_id          bigint
);

alter table organization
    owner to "eunReportDesigner";

create table person
(
    id                      bigint not null
        primary key,
    eun_db_id               bigint,
    firstname               varchar(255),
    lastname                varchar(255),
    salutation              integer,
    main_contract_email     varchar(255),
    extra_contract_email    varchar(255),
    language_mother_tongue  varchar(255),
    language_other          varchar(255),
    description             varchar(255),
    website                 varchar(255),
    mobile                  varchar(255),
    phone                   varchar(255),
    social_network_contacts varchar(255),
    status                  varchar(255),
    gdpr_status             varchar(255),
    last_login_date         date,
    country_id              bigint
);

alter table person
    owner to "eunReportDesigner";

create table eun_team_member
(
    id        bigint not null
        primary key,
    role      varchar(255),
    status    varchar(255),
    team_id   bigint,
    person_id bigint
);

alter table eun_team_member
    owner to "eunReportDesigner";

create table event_in_organization
(
    id              bigint not null
        primary key,
    event_id        bigint,
    organization_id bigint
);

alter table event_in_organization
    owner to "eunReportDesigner";

create table event_participant
(
    id        bigint not null
        primary key,
    type      varchar(255),
    event_id  bigint,
    person_id bigint
);

alter table event_participant
    owner to "eunReportDesigner";

create table organization_in_ministry
(
    id              bigint not null
        primary key,
    status          varchar(255),
    ministry_id     bigint,
    organization_id bigint
);

alter table organization_in_ministry
    owner to "eunReportDesigner";

create table organization_in_project
(
    id                                                     bigint not null
        primary key,
    status                                                 varchar(255),
    join_date                                              date,
    funding_for_organization                               integer,
    participation_to_matching_funding                      integer,
    school_registration_possible                           boolean,
    teacher_participation_possible                         boolean,
    ambassadors_pilot_teachers_leading_teachers_identified boolean,
    users_can_register_to_portal                           boolean,
    project_id                                             bigint,
    organization_id                                        bigint
);

alter table organization_in_project
    owner to "eunReportDesigner";

create table person_in_organization
(
    id                   bigint not null
        primary key,
    role_in_organization varchar(255),
    person_id            bigint,
    organization_id      bigint
);

alter table person_in_organization
    owner to "eunReportDesigner";

create table person_in_project
(
    id              bigint not null
        primary key,
    role_in_project varchar(255),
    person_id       bigint,
    project_id      bigint
);

alter table person_in_project
    owner to "eunReportDesigner";

create table report
(
    id                bigint       not null
        primary key,
    report_name       varchar(255) not null,
    acronym           varchar(255),
    description       varchar(255),
    type              varchar(255),
    is_active         varchar(255),
    file              bytea,
    file_content_type varchar(255)
);

alter table report
    owner to "eunReportDesigner";

create table role
(
    id   bigint      not null
        primary key,
    name varchar(50) not null
);

alter table role
    owner to "eunReportDesigner";

create table rel_role_privilege
(
    privilege_id bigint not null,
    role_id      bigint not null,
    constraint rel_role__privilege_pkey
        primary key (role_id, privilege_id)
);

alter table rel_role_privilege
    owner to "eunReportDesigner";

create table privilege
(
    id   bigint not null
        primary key,
    name varchar(255)
);

alter table privilege
    owner to "eunReportDesigner";

create table rel_user_role
(
    user_id bigint not null
        constraint rel_user_role_jhi_user_id_fk
            references jhi_user,
    role_id bigint not null
        constraint rel_user_role_role_id_fk
            references role
);

alter table rel_user_role
    owner to "eunReportDesigner";

create table report_blocks
(
    id              bigint not null
        primary key,
    country_name    varchar(255),
    priority_number bigint,
    content         varchar,
    is_active       boolean,
    type            varchar(255),
    sql_script      varchar(255)
);

alter table report_blocks
    owner to "eunReportDesigner";

create table rel_report_blocks_country
(
    country_id       bigint not null,
    report_blocks_id bigint not null,
    constraint rel_report_blocks__country_id_pkey
        primary key (report_blocks_id, country_id)
);

alter table rel_report_blocks_country
    owner to "eunReportDesigner";

create table rel_report_blocks_report_id
(
    report_id        bigint not null,
    report_blocks_id bigint not null,
    constraint rel_report_blocks__report_id_pkey
        primary key (report_blocks_id, report_id)
);

alter table rel_report_blocks_report_id
    owner to "eunReportDesigner";

INSERT INTO public.project (id, status, supported_country_ids, supported_language_ids, name, acronym, period, description, contact_email, contact_name, total_budget, total_funding, total_budget_for_eun, total_funding_for_eun, funding_value, percentage_of_funding, percentage_of_indirect_costs, percentage_of_funding_for_eun, percentage_of_indirect_costs_for_eun, funding_extra_comment, programme, eu_dg, role_of_eun, summary, main_tasks, expected_outcomes, eu_call_reference, eu_project_reference, eu_call_deadline, project_manager, reference_number_of_project, eun_team, sys_creat_timestamp, sys_creat_ip_address, sys_modif_timestamp, sys_modif_ip_address, funding_id) VALUES (2, 'CLOSED', '{}', '{}', 'KeyCoNet', 'KeyCoNet', '', 'KeyCoNet', 'key_co_net@gmail.com', 'Prueba11', 3752, 10615, 54610, 100000, 30395, 5, 17, 90, 76, 'South', 'teal e-services', 'International networks', 'Architect Metal', 'KeyCoNet - a project dedicated to fostering collaboration among various educational organizations and creating a network of key competencies for students in the European Union.', 'KeyCoNet - to identify and promote a set of key competences for lifelong learning', 'the development of a set of key competences for lifelong learning', '127.0.0.1', '127.0.0.1', '', 'Debry', 10, 'Väike-Maarja Gümnaasium', '2023-04-27 06:26:18.000000', '', '2023-04-27 02:17:06.000000', '', null);
INSERT INTO public.project (id, status, supported_country_ids, supported_language_ids, name, acronym, period, description, contact_email, contact_name, total_budget, total_funding, total_budget_for_eun, total_funding_for_eun, funding_value, percentage_of_funding, percentage_of_indirect_costs, percentage_of_funding_for_eun, percentage_of_indirect_costs_for_eun, funding_extra_comment, programme, eu_dg, role_of_eun, summary, main_tasks, expected_outcomes, eu_call_reference, eu_project_reference, eu_call_deadline, project_manager, reference_number_of_project, eun_team, sys_creat_timestamp, sys_creat_ip_address, sys_modif_timestamp, sys_modif_ip_address, funding_id) VALUES (9, 'DRAFT', '{}', '{}', 'TELLNET', 'TELLNET', '', 'TELLNET', 'telnet@gmail.com', 'Isidora', 47958, 43558, 19159, 100000, 46610, 5, 17, 90, 76, 'program', 'sensor', 'Handmade matrix 24', 'Assurance Table', 'TELLNET - a project aimed at creating a network of European experts in language education to bridge the digital divide in language learning and implement technology in education.', 'to develop a network of experts in language education', 'a better understanding of young people''s experiences and perspectives on citizenship', '127.0.0.1', '127.0.0.1', '', 'Salim', 10, 'Kehra Gümnaasium', '2023-04-27 02:04:05.000000', '', '2023-04-27 05:39:41.000000', '', null);
INSERT INTO public.project (id, status, supported_country_ids, supported_language_ids, name, acronym, period, description, contact_email, contact_name, total_budget, total_funding, total_budget_for_eun, total_funding_for_eun, funding_value, percentage_of_funding, percentage_of_indirect_costs, percentage_of_funding_for_eun, percentage_of_indirect_costs_for_eun, funding_extra_comment, programme, eu_dg, role_of_eun, summary, main_tasks, expected_outcomes, eu_call_reference, eu_project_reference, eu_call_deadline, project_manager, reference_number_of_project, eun_team, sys_creat_timestamp, sys_creat_ip_address, sys_modif_timestamp, sys_modif_ip_address, funding_id) VALUES (8, 'ACTIVE', '{}', '{}', 'Pan_EU_Youth', 'Pan_EU_Youth', '', 'Pan_EU_Youth', 'pan_eu@gmail.com', 'Maite', 79934, 18792, 22655, 100000, 75051, 5, 17, 90, 76, 'payment gold Liaison', 'best-of-breed overriding', 'Loan', 'value-added transmit context-sensitive', 'Pan_EU_Youth - a research project that explores the opinions and experiences of young people in the European Union on issues of citizenship', 'Pan_EU_Youth - to provide an evidence base to support policy making in the field of youth', 'the creation of a network of experts in language education', '127.0.0.1', '127.0.0.1', '', 'Benito', 10, 'Sillamäe Vanalinna Kool', '2023-04-26 21:29:20.000000', '', '2023-04-26 18:14:21.000000', '', null);
INSERT INTO public.project (id, status, supported_country_ids, supported_language_ids, name, acronym, period, description, contact_email, contact_name, total_budget, total_funding, total_budget_for_eun, total_funding_for_eun, funding_value, percentage_of_funding, percentage_of_indirect_costs, percentage_of_funding_for_eun, percentage_of_indirect_costs_for_eun, funding_extra_comment, programme, eu_dg, role_of_eun, summary, main_tasks, expected_outcomes, eu_call_reference, eu_project_reference, eu_call_deadline, project_manager, reference_number_of_project, eun_team, sys_creat_timestamp, sys_creat_ip_address, sys_modif_timestamp, sys_modif_ip_address, funding_id) VALUES (7, 'REQUESTED', '{}', '{}', 'Global_Excursion', 'Global_Excursion', '', 'Global_Excursion', 'excursion@gmail.com', 'Quentin', 82582, 76941, 9921, 100000, 2653, 5, 17, 90, 76, 'User-centric Division Music', 'Books generating Soft', 'Gorgeous', 'gold Rubber attitude-oriented', 'Global_Excursion - an educational project that allows students to travel around the world and learn about different cultures', 'Global_Excursion - to provide students with an immersive and engaging educational experience using virtual reality technology', 'improved mathematics teaching in Europe', '127.0.0.1', '127.0.0.1', '', 'Scientix2', 10, 'Antsla Gümnaasium', '2023-04-26 10:49:29.000000', '', '2023-04-27 07:30:48.000000', '', null);
INSERT INTO public.project (id, status, supported_country_ids, supported_language_ids, name, acronym, period, description, contact_email, contact_name, total_budget, total_funding, total_budget_for_eun, total_funding_for_eun, funding_value, percentage_of_funding, percentage_of_indirect_costs, percentage_of_funding_for_eun, percentage_of_indirect_costs_for_eun, funding_extra_comment, programme, eu_dg, role_of_eun, summary, main_tasks, expected_outcomes, eu_call_reference, eu_project_reference, eu_call_deadline, project_manager, reference_number_of_project, eun_team, sys_creat_timestamp, sys_creat_ip_address, sys_modif_timestamp, sys_modif_ip_address, funding_id) VALUES (4, 'REVIEWED', '{}', '{}', 'EUN-Fourier', 'EUN-Fourier', '', 'EUN-Fourier', 'eun-fourier@gmail.com', 'Rocio', 65412, 59350, 37655, 78915, 59770, 5, 17, 90, 76, 'parse Savings Bypass', 'Unbranded', 'Computer', 'Cliff sticky', 'EUN-Fourier - a project that helps teachers across Europe improve their knowledge of mathematics and learn to use new teaching methods.', 'EUN-Fourier - to improve the quality of mathematics teaching in Europe', 'an immersive and engaging educational experience for students using virtual reality technology', '127.0.0.1', '127.0.0.1', '', 'Guimezanes', 10, 'Loksa Gümnaasium', '2023-04-27 09:35:48.000000', '', '2023-04-27 04:23:20.000000', '', null);

INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427111606-1-data', 'jhipster', 'config/liquibase/changelog/20230427111606_added_entity_Countries.xml', '2023-05-03 18:24:55.643221', 4, 'EXECUTED', '8:0dea03b1e1a99152857f88517b96fe1d', 'loadData tableName=countries', '', null, '4.15.0', 'faker', null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427114022-1', 'jhipster', 'config/liquibase/changelog/20230427114022_added_entity_EunTeam.xml', '2023-05-03 18:24:55.655089', 5, 'EXECUTED', '8:58dabca8c792aeb8718d3169aa26fa69', 'createTable tableName=eun_team', '', null, '4.15.0', null, null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427114022-1-data', 'jhipster', 'config/liquibase/changelog/20230427114022_added_entity_EunTeam.xml', '2023-05-03 18:24:55.670265', 6, 'EXECUTED', '8:de09a6fe7d78f062b0a9dcbc97434354', 'loadData tableName=eun_team', '', null, '4.15.0', 'faker', null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230428120830-1', 'jhipster', 'config/liquibase/changelog/20230428120830_added_entity_Event.xml', '2023-05-03 18:24:55.682020', 7, 'EXECUTED', '8:5b77d890ecee69fb18de6fe4f20d72b1', 'createTable tableName=event', '', null, '4.15.0', null, null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230428120830-1-data', 'jhipster', 'config/liquibase/changelog/20230428120830_added_entity_Event.xml', '2023-05-03 18:24:55.701071', 8, 'EXECUTED', '8:de933aed977f4e43cd9f318dd17a19af', 'loadData tableName=event', '', null, '4.15.0', 'faker', null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230428120831-1', 'jhipster', 'config/liquibase/changelog/20230428120831_added_entity_OperationalBody.xml', '2023-05-03 18:24:55.712382', 9, 'EXECUTED', '8:d1ca302ce1f232f5d890d41cdbfd3ea7', 'createTable tableName=operational_body', '', null, '4.15.0', null, null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427100940-1', 'jhipster', 'config/liquibase/changelog/20230427100940_added_entity_Project.xml', '2023-05-03 18:24:55.564924', 1, 'EXECUTED', '8:8566f4c3b798961685eb42f289ae220c', 'createTable tableName=project; dropDefaultValue columnName=sys_creat_timestamp, tableName=project; dropDefaultValue columnName=sys_modif_timestamp, tableName=project', '', null, '4.15.0', null, null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427100940-1-data', 'jhipster', 'config/liquibase/changelog/20230427100940_added_entity_Project.xml', '2023-05-03 18:24:55.617560', 2, 'EXECUTED', '8:d25aebec16638fe787bcf5c11eedd90d', 'loadData tableName=project', '', null, '4.15.0', 'faker', null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230427111606-1', 'jhipster', 'config/liquibase/changelog/20230427111606_added_entity_Countries.xml', '2023-05-03 18:24:55.626565', 3, 'EXECUTED', '8:2919982661ff1ae723685af820311371', 'createTable tableName=countries', '', null, '4.15.0', null, null, '3116695424');
INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) VALUES ('20230428120831-1-data', 'jhipster', 'config/liquibase/changelog/20230428120831_added_entity_OperationalBody.xml', '2023-05-03 18:24:55.730190', 10, 'EXECUTED', '8:7e6f266016dc03256d116708fcb72b07', 'loadData tableName=operational_body', '', null, '4.15.0', 'faker', null, '3116695424');
