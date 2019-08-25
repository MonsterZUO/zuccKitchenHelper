/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/8/22 14:51:55                           */
/*==============================================================*/


drop table if exists learningExamScore;

drop table if exists majorInfo;

drop table if exists plan;

drop table if exists schoolInfo;

drop table if exists selectExamScore;

drop table if exists signInfo;

drop table if exists skill;

drop table if exists stuInfo;

drop table if exists subInfo;

/*==============================================================*/
/* Table: learningExamScore                                     */
/*==============================================================*/
create table learningExamScore
(
   stuId                varchar(18) not null,
   subNo                int,
   LEScore              int not null,
   LERank               tinyint,
   primary key (stuId)
);

/*==============================================================*/
/* Table: majorInfo                                             */
/*==============================================================*/
create table majorInfo
(
   majorNo              varchar(20) not null,
   schoolNo             int,
   majorName            varchar(255) not null,
   majorType            varchar(255) not null,
   primary key (majorNo)
);

/*==============================================================*/
/* Table: plan                                                  */
/*==============================================================*/
create table plan
(
   planYear             numeric(4,0) not null,
   majorNo              varchar(20) not null,
   planSubLimit         bool not null,
   planSub1             varchar(255),
   planSub2             varchar(255),
   planSub3             varchar(255),
   planCount            int not null,
   fee                  int not null,
   other                varchar(255),
   primary key (planYear, majorNo)
);

/*==============================================================*/
/* Table: schoolInfo                                            */
/*==============================================================*/
create table schoolInfo
(
   schoolNo             int not null,
   schoolName           varchar(255) not null,
   schoolProvince       varchar(255) not null,
   schoolCity           varchar(255) not null,
   schoolDetail         varchar(255),
   primary key (schoolNo)
);

/*==============================================================*/
/* Table: selectExamScore                                       */
/*==============================================================*/
create table selectExamScore
(
   stuId                varchar(18) not null,
   subNo                int not null,
   SEScore              int not null,
   SERank               tinyint not null,
   primary key (stuId)
);

/*==============================================================*/
/* Table: signInfo                                              */
/*==============================================================*/
create table signInfo
(
   schoolNo             int not null,
   signCondition        varchar(255) not null,
   signStart            datetime not null,
   signEnd              datetime not null,
   signWay              varchar(255) not null,
   primary key (schoolNo)
);

/*==============================================================*/
/* Table: skill                                                 */
/*==============================================================*/
create table skill
(
   stuId                varchar(18) not null,
   skillType            varchar(50) not null,
   skillRank            tinyint not null,
   skillDetail          varchar(255) not null,
   primary key (stuId)
);

/*==============================================================*/
/* Table: stuInfo                                               */
/*==============================================================*/
create table stuInfo
(
   stuId                varchar(18) not null,
   stuExamNo            varchar(50) not null,
   stuName              varchar(255) not null,
   stuSex               varchar(20) not null,
   stuPhone             varchar(11) not null,
   highSchoolNo         varchar(50) not null,
   stuProvince          int not null,
   primary key (stuId)
);

/*==============================================================*/
/* Table: subInfo                                               */
/*==============================================================*/
create table subInfo
(
   subNo                int not null,
   subName              varchar(20) not null,
   primary key (subNo)
);

alter table learningExamScore add constraint FK_Reference_5 foreign key (stuId)
      references stuInfo (stuId) on delete restrict on update restrict;

alter table learningExamScore add constraint FK_Reference_6 foreign key (subNo)
      references subInfo (subNo) on delete restrict on update restrict;

alter table majorInfo add constraint FK_Reference_1 foreign key (schoolNo)
      references schoolInfo (schoolNo) on delete restrict on update restrict;

alter table plan add constraint FK_Reference_2 foreign key (majorNo)
      references majorInfo (majorNo) on delete restrict on update restrict;

alter table selectExamScore add constraint FK_Reference_7 foreign key (stuId)
      references stuInfo (stuId) on delete restrict on update restrict;

alter table signInfo add constraint FK_Reference_3 foreign key (schoolNo)
      references schoolInfo (schoolNo) on delete restrict on update restrict;

alter table skill add constraint FK_Reference_8 foreign key (stuId)
      references stuInfo (stuId) on delete restrict on update restrict;

