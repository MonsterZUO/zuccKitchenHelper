/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/8/23 21:09:34                           */
/*==============================================================*/


drop table if exists adminInfo;

drop table if exists foodBuy;

drop table if exists foodInfo;

drop table if exists foodOrder;

drop table if exists foodTypeInfo;

drop table if exists orderDetail;

drop table if exists recipeComment;

drop table if exists recipeInfo;

drop table if exists recipeStep;

drop table if exists recipeUse;

drop table if exists userInfo;

/*==============================================================*/
/* Table: adminInfo                                             */
/*==============================================================*/
create table adminInfo
(
   adminNo              varchar(200) not null,
   adminName            varchar(200) not null,
   adminPassword        varchar(200) not null,
   primary key (adminNo)
);

/*==============================================================*/
/* Table: foodBuy                                               */
/*==============================================================*/
create table foodBuy
(
   buyNo                varchar(200) not null,
   foodNo               varchar(200) not null,
   amount               double not null,
   buyStatus            varchar(200) not null,
   primary key (buyNo)
);

/*==============================================================*/
/* Table: foodInfo                                              */
/*==============================================================*/
create table foodInfo
(
   foodNo               varchar(200) not null,
   foodTypeNo           varchar(200) not null,
   foodName             varchar(200) not null,
   foodPrice            double not null,
   foodAmount           double not null,
   foodDetail           varchar(200) not null,
   foodUnit             varchar(200) not null,
   primary key (foodNo)
);

/*==============================================================*/
/* Table: foodOrder                                             */
/*==============================================================*/
create table foodOrder
(
   orderNo              varchar(200) not null,
   userNo               varchar(200) not null,
   deliverTime          datetime not null,
   address              varchar(200) not null,
   phone                varchar(11) not null,
   orderStatus          varchar(200) not null,
   primary key (orderNo)
);

/*==============================================================*/
/* Table: foodTypeInfo                                          */
/*==============================================================*/
create table foodTypeInfo
(
   foodTypeNo           varchar(200) not null,
   foodTypeName         varchar(200) not null,
   foodTypeDetail       varchar(200),
   primary key (foodTypeNo)
);

/*==============================================================*/
/* Table: orderDetail                                           */
/*==============================================================*/
create table orderDetail
(
   orderNo              varchar(200) not null,
   foodNo               varchar(200) not null,
   amount               double not null,
   price                double not null,
   discount             double not null,
   primary key (orderNo)
);

/*==============================================================*/
/* Table: recipeComment                                         */
/*==============================================================*/
create table recipeComment
(
   recipeNo             varchar(200) not null,
   userNo               varchar(200) not null,
   comment              varchar(255) not null,
   look                 bool not null,
   collect              bool not null,
   score                double not null,
   primary key (recipeNo, userNo)
);

/*==============================================================*/
/* Table: recipeInfo                                            */
/*==============================================================*/
create table recipeInfo
(
   recipeNo             varchar(200) not null,
   recipeName           varchar(200) not null,
   userNo               varchar(200) not null,
   recipeDetail         varchar(200) not null,
   scoreTotal           double not null,
   collectCount         int not null,
   lookCount            int not null,
   primary key (recipeNo)
);

/*==============================================================*/
/* Table: recipeStep                                            */
/*==============================================================*/
create table recipeStep
(
   recipeNo             varchar(200) not null,
   stepNo               int not null,
   stepDetail           varchar(200) not null,
   primary key (recipeNo, stepNo)
);

/*==============================================================*/
/* Table: recipeUse                                             */
/*==============================================================*/
create table recipeUse
(
   recipeNo             varchar(200) not null,
   foodNo               varchar(200) not null,
   amount               double not null,
   unit                 varchar(200) not null,
   primary key (recipeNo, foodNo)
);

/*==============================================================*/
/* Table: userInfo                                              */
/*==============================================================*/
create table userInfo
(
   userNo               varchar(200) not null,
   userName             varchar(200) not null,
   userSex              varchar(200) not null,
   userPassword         varchar(200) not null,
   userPhone            varchar(11),
   userMail             varchar(200),
   userCity             varchar(200),
   registerTime         datetime not null,
   primary key (userNo)
);

alter table foodBuy add constraint FK_Reference_8 foreign key (foodNo)
      references foodInfo (foodNo) on delete restrict on update restrict;

alter table foodInfo add constraint FK_Reference_1 foreign key (foodTypeNo)
      references foodTypeInfo (foodTypeNo) on delete restrict on update restrict;

alter table foodOrder add constraint FK_Reference_5 foreign key (userNo)
      references userInfo (userNo) on delete restrict on update restrict;

alter table orderDetail add constraint FK_Reference_6 foreign key (orderNo)
      references foodOrder (orderNo) on delete restrict on update restrict;

alter table orderDetail add constraint FK_Reference_7 foreign key (foodNo)
      references foodInfo (foodNo) on delete restrict on update restrict;

alter table recipeComment add constraint FK_Reference_3 foreign key (recipeNo)
      references recipeInfo (recipeNo) on delete restrict on update restrict;

alter table recipeComment add constraint FK_Reference_4 foreign key (userNo)
      references userInfo (userNo) on delete restrict on update restrict;

alter table recipeInfo add constraint FK_Reference_11 foreign key (userNo)
      references userInfo (userNo) on delete restrict on update restrict;

alter table recipeStep add constraint FK_Reference_2 foreign key (recipeNo)
      references recipeInfo (recipeNo) on delete restrict on update restrict;

alter table recipeUse add constraint FK_Reference_10 foreign key (foodNo)
      references foodInfo (foodNo) on delete restrict on update restrict;

alter table recipeUse add constraint FK_Reference_9 foreign key (recipeNo)
      references recipeInfo (recipeNo) on delete restrict on update restrict;

