create table ACTIVITY
(
  id            NUMBER(19) not null,
  name          VARCHAR2(200),
  type          NUMBER(19),
  is_percentage NUMBER(2),
  istop_limit   NUMBER(2),
  send_type     NUMBER(2),
  isfirst_send  NUMBER(2),
  send_money    NUMBER(19,2) default 0,
  start_time    TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  end_time      TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  status        NUMBER(2),
  create_time   TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  description   VARCHAR2(500)
);
comment on column ACTIVITY.id
  is '活动ID';
comment on column ACTIVITY.name
  is '活动名称 ';
comment on column ACTIVITY.type
  is '活动类型1(充值)、2(奖金)、3(注册)';
comment on column ACTIVITY.is_percentage
  is '是否按百分比0(是)1(否)';
comment on column ACTIVITY.istop_limit
  is '是否有上限0(是)1(否)';
comment on column ACTIVITY.send_type
  is '赠送类型1(彩金)、2(红包)、3(积分)';
comment on column ACTIVITY.isfirst_send
  is '是否只在第一次赠送0(是)1(否)';
comment on column ACTIVITY.send_money
  is '赠送金额';
comment on column ACTIVITY.start_time
  is '开始时间';
comment on column ACTIVITY.end_time
  is '结束时间';
comment on column ACTIVITY.status
  is '活动状态';
comment on column ACTIVITY.create_time
  is '创建时间';
comment on column ACTIVITY.description
  is '描述';
alter table ACTIVITY  add constraint ACTIVITY_PK primary key (ID);

create table AWARDLEVEL
(
  lotterytype NUMBER(10) not null,
  prizelevel  VARCHAR2(10 CHAR) not null,
  levelname   VARCHAR2(20 CHAR),
  prize       NUMBER(10),
  extraprize  NUMBER(10)
);
alter table AWARDLEVEL
  add constraint PK_AWARDLEVEL primary key (LOTTERYTYPE, PRIZELEVEL);

create table DC_RACE
(
  id            NUMBER(20) not null,
  phase         VARCHAR2(20),
  match_num     NUMBER(10),
  create_time   TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  update_time   TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  end_sale_time TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  match_date    TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  home_team     VARCHAR2(255),
  away_team     VARCHAR2(255),
  handicap      VARCHAR2(255),
  whole_socre   VARCHAR2(255),
  half_score    VARCHAR2(255),
  match_name    VARCHAR2(255),
  status        NUMBER(10),
  ext           VARCHAR2(4000),
  sp_spf        VARCHAR2(255),
  sp_sxds       VARCHAR2(255),
  sp_jqs        VARCHAR2(255),
  sp_bf         VARCHAR2(255),
  fx_id         NUMBER(10),
  priority      NUMBER(10),
  prize_time    TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  sfp_result    VARCHAR2(10 CHAR),
  sxds_result   VARCHAR2(10 CHAR),
  jqs_result    VARCHAR2(10 CHAR),
  bf_result     VARCHAR2(10 CHAR),
  bcsfp_result  VARCHAR2(10 CHAR)
);
alter table DC_RACE
  add primary key (ID);


create table GIVE_ACTIVITY
(
  id            NUMBER(19) not null,
  name          VARCHAR2(200),
  type          NUMBER(2),
  is_percentage NUMBER(2),
  istop_limit   NUMBER(2),
  send_type     NUMBER(2),
  isfirst_send  NUMBER(2),
  top_limit     NUMBER(19,2) default 0,
  send_money    NUMBER(19,2) default 0,
  start_time    TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  end_time      TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  status        NUMBER(2),
  create_time   TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  description   VARCHAR2(500)
);
comment on column GIVE_ACTIVITY.id
  is '活动ID';
comment on column GIVE_ACTIVITY.name
  is '活动名称 ';
comment on column GIVE_ACTIVITY.type
  is '活动类型1(充值)、2(奖金)、3(注册)';
comment on column GIVE_ACTIVITY.is_percentage
  is '是否按百分比0(是)1(否)';
comment on column GIVE_ACTIVITY.istop_limit
  is '是否有上限0(是)1(否)';
comment on column GIVE_ACTIVITY.send_type
  is '赠送类型1(彩金)、2(红包)、3(积分)';
comment on column GIVE_ACTIVITY.isfirst_send
  is '是否只在第一次赠送0(是)1(否)';
comment on column GIVE_ACTIVITY.top_limit
  is '活动赠送上限 ';
comment on column GIVE_ACTIVITY.send_money
  is '赠送金额';
comment on column GIVE_ACTIVITY.start_time
  is '开始时间';
comment on column GIVE_ACTIVITY.end_time
  is '结束时间';
comment on column GIVE_ACTIVITY.status
  is '活动状态';
comment on column GIVE_ACTIVITY.create_time
  is '创建时间';
comment on column GIVE_ACTIVITY.description
  is '描述';
alter table GIVE_ACTIVITY
  add constraint GIVE_ACTIVITY_PK primary key (ID);


create table ID_GENERATOR
(
  id      NUMBER(19) not null,
  seqid   NUMBER(19),
  seqname VARCHAR2(255)
);
alter table ID_GENERATOR
  add primary key (ID);


create table JCLQ_RACE
(
  match_num                VARCHAR2(255) not null,
  phase                    VARCHAR2(255),
  official_date            TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  official_num             VARCHAR2(255),
  official_weekday         VARCHAR2(10),
  create_time              TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  end_sale_time            TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  match_name               VARCHAR2(255),
  match_date               TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  update_time              TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  home_team                VARCHAR2(255),
  away_team                VARCHAR2(255),
  dynamic_handicap         VARCHAR2(10),
  static_handicap          VARCHAR2(10),
  dynamic_preset_score     VARCHAR2(10),
  static_preset_score      VARCHAR2(10),
  first_quarter            VARCHAR2(10),
  second_quarter           VARCHAR2(10),
  third_quarter            VARCHAR2(10),
  fourth_quarter           VARCHAR2(10),
  final_score              VARCHAR2(10),
  status                   NUMBER(10),
  static_draw_status       NUMBER(10),
  dynamic_draw_status      NUMBER(10),
  static_sale_sf_status    NUMBER(10),
  dynamic_sale_sf_status   NUMBER(10),
  static_sale_rfsf_status  NUMBER(10),
  dynamic_sale_rfsf_status NUMBER(10),
  static_sale_sfc_status   NUMBER(10),
  dynamic_sale_sfc_status  NUMBER(10),
  static_sale_dxf_status   NUMBER(10),
  dynamic_sale_dxf_status  NUMBER(10),
  prize_sf                 VARCHAR2(255),
  prize_rfsf               VARCHAR2(255),
  prize_sfc                VARCHAR2(255),
  prize_dxf                VARCHAR2(255),
  fx_id                    NUMBER(10),
  priority                 NUMBER(10),
  ext                      VARCHAR2(4000),
  prize_status             NUMBER(2)
);
alter table JCLQ_RACE
  add primary key (MATCH_NUM);


create table JCZQ_RACE
(
  match_num                   VARCHAR2(255) not null,
  phase                       VARCHAR2(255),
  official_date               TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  official_num                VARCHAR2(255),
  official_weekday            VARCHAR2(10),
  create_time                 TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  end_sale_time               TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  match_name                  VARCHAR2(255),
  match_date                  TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  update_time                 TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  home_team                   VARCHAR2(255),
  away_team                   VARCHAR2(255),
  handicap                    VARCHAR2(10),
  first_half                  VARCHAR2(10),
  second_half                 VARCHAR2(10),
  final_score                 VARCHAR2(10),
  status                      NUMBER(10),
  static_draw_status          NUMBER(10),
  dynamic_draw_status         NUMBER(10),
  static_sale_spf_status      NUMBER(10),
  dynamic_sale_spf_status     NUMBER(10),
  static_sale_bf_status       NUMBER(10),
  dynamic_sale_bf_status      NUMBER(10),
  static_sale_jqs_status      NUMBER(10),
  dynamic_sale_jqs_status     NUMBER(10),
  static_sale_bqc_status      NUMBER(10),
  dynamic_sale_bqc_status     NUMBER(10),
  static_sale_spf_wrq_status  NUMBER(10),
  dynamic_sale_spf_wrq_status NUMBER(10),
  prize_spf                   VARCHAR2(255),
  prize_bf                    VARCHAR2(255),
  prize_jqs                   VARCHAR2(255),
  prize_bqc                   VARCHAR2(255),
  prize_spf_wrq               VARCHAR2(255),
  fx_id                       NUMBER(10),
  priority                    NUMBER(10),
  ext                         VARCHAR2(4000),
  prize_status                NUMBER(2)
);
alter table JCZQ_RACE
  add primary key (MATCH_NUM);


create table LOTTERY_CASELOT
(
  id               VARCHAR2(50) not null,
  starter          VARCHAR2(255) not null,
  minamt           NUMBER(19),
  totalamt         NUMBER(19),
  safeamt          NUMBER(19),
  buyamtbystarter  NUMBER(19),
  commisionratio   NUMBER(3),
  starttime        TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  endtime          TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  visibility       NUMBER(1),
  description      VARCHAR2(255),
  title            VARCHAR2(255),
  state            NUMBER(2),
  displaystate     NUMBER(2),
  content          VARCHAR2(50),
  orderid          VARCHAR2(255),
  lottype          NUMBER(10),
  phaseno          VARCHAR2(50),
  winbigamt        NUMBER(19),
  winpreamt        NUMBER(19),
  winstarttime     TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  windetail        VARCHAR2(50),
  caselotinfo      VARCHAR2(100),
  buyamtbyfollower NUMBER(10,2),
  sortstate        NUMBER(1),
  participantcount NUMBER(10),
  isexchanged      NUMBER(5),
  displaystatememo VARCHAR2(50)
);
comment on column LOTTERY_CASELOT.starter
  is '发起人用户编号';
comment on column LOTTERY_CASELOT.minamt
  is '合买最小购买金额';
comment on column LOTTERY_CASELOT.totalamt
  is '方案总金额';
comment on column LOTTERY_CASELOT.safeamt
  is '方案保底金额';
comment on column LOTTERY_CASELOT.buyamtbystarter
  is '发起人购买金额';
comment on column LOTTERY_CASELOT.commisionratio
  is '佣金比例';
comment on column LOTTERY_CASELOT.starttime
  is '方案开始时间';
comment on column LOTTERY_CASELOT.endtime
  is '方案结束时间';
comment on column LOTTERY_CASELOT.visibility
  is '方案内容保密状态';
comment on column LOTTERY_CASELOT.description
  is '方案描述 ';
comment on column LOTTERY_CASELOT.title
  is '方案标题';
comment on column LOTTERY_CASELOT.state
  is '方案状态';
comment on column LOTTERY_CASELOT.displaystate
  is '用户看到的状态';
comment on column LOTTERY_CASELOT.content
  is '方案内容描述';
comment on column LOTTERY_CASELOT.orderid
  is '订单编号';
comment on column LOTTERY_CASELOT.lottype
  is '彩种';
comment on column LOTTERY_CASELOT.phaseno
  is '期号';
comment on column LOTTERY_CASELOT.winbigamt
  is '税后奖金';
comment on column LOTTERY_CASELOT.winpreamt
  is '税前奖金';
comment on column LOTTERY_CASELOT.winstarttime
  is '中奖时间';
comment on column LOTTERY_CASELOT.windetail
  is '中奖信息';
comment on column LOTTERY_CASELOT.caselotinfo
  is '方案信息';
comment on column LOTTERY_CASELOT.buyamtbyfollower
  is '参与人购买金额';
comment on column LOTTERY_CASELOT.sortstate
  is '0：普通合买，3：申请置顶合买大厅，4:申请置顶合买中心，8：置顶合买大厅，9：置顶合买中心';
comment on column LOTTERY_CASELOT.participantcount
  is '参与人数';
comment on column LOTTERY_CASELOT.displaystatememo
  is '用户看到的状态描述';
alter table LOTTERY_CASELOT
  add constraint CASELOT_PK primary key (ID);


create table LOTTERY_CASELOT_BUY
(
  id                NUMBER(19) not null,
  caselotid         VARCHAR2(100),
  userno            VARCHAR2(255),
  num               NUMBER(19),
  buytime           TIMESTAMP(6),
  flag              NUMBER(1),
  safeamt           NUMBER(19),
  freezesafeamt     NUMBER(19),
  buytype           NUMBER(1),
  prizeamt          NUMBER(19),
  commisionprizeamt NUMBER(19),
  isexchanged       NUMBER(1),
  lottype           NUMBER(5),
  phaseno           VARCHAR2(100),
  freezdrawamt      NUMBER(19),
  buydrawamt        NUMBER(19)
);
comment on column LOTTERY_CASELOT_BUY.caselotid
  is '合买ID';
comment on column LOTTERY_CASELOT_BUY.userno
  is '用户编号 ';
comment on column LOTTERY_CASELOT_BUY.num
  is '购买金额';
comment on column LOTTERY_CASELOT_BUY.buytime
  is '购买时间 ';
comment on column LOTTERY_CASELOT_BUY.flag
  is '1:正常，0:撤资 ';
comment on column LOTTERY_CASELOT_BUY.safeamt
  is '购买时的保底金额';
comment on column LOTTERY_CASELOT_BUY.freezesafeamt
  is '冻结的保底金额，也就是实际可保底的金额';
comment on column LOTTERY_CASELOT_BUY.buytype
  is '购买类型。1:发起者，0:参与者';
comment on column LOTTERY_CASELOT_BUY.prizeamt
  is '中奖金额 ';
comment on column LOTTERY_CASELOT_BUY.commisionprizeamt
  is '佣金金额 ';
comment on column LOTTERY_CASELOT_BUY.isexchanged
  is '派奖状态';
comment on column LOTTERY_CASELOT_BUY.lottype
  is '彩种';
comment on column LOTTERY_CASELOT_BUY.phaseno
  is '期号';
comment on column LOTTERY_CASELOT_BUY.freezdrawamt
  is '冻结可提现余额变动金额';
comment on column LOTTERY_CASELOT_BUY.buydrawamt
  is '购买可提现变动';
alter table LOTTERY_CASELOT_BUY
  add constraint CASELOTBUY_PK primary key (ID);

create table LOTTERY_CHASE
(
  id            NUMBER(19) not null,
  userno        VARCHAR2(255) not null,
  lottery_type  NUMBER(10) not null,
  batch_num     NUMBER(3) not null,
  remain_num    NUMBER(3) not null,
  begin_phase   VARCHAR2(255) not null,
  last_phase    VARCHAR2(255) not null,
  bet_num       NUMBER(10) not null,
  bet_code      VARCHAR2(4000) not null,
  amount        NUMBER(10,2) default 0,
  total_amount  NUMBER(10,2) default 0,
  remain_amount NUMBER(10,2) default 0,
  state         NUMBER(1) not null,
  end_time      TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  change_time   TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  create_time   TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  cancel_by     NUMBER(1),
  chase_detail  VARCHAR2(4000),
  chase_type    NUMBER(10) default 0,
  prize_total   NUMBER(19,2) default 0
);
alter table LOTTERY_CHASE
  add constraint LOTTERY_CHASE_PK primary key (ID);


create table LOTTERY_DRAW_AMOUNT
(
  id           VARCHAR2(255) not null,
  userno       VARCHAR2(255) not null,
  user_name    VARCHAR2(255),
  draw_type    NUMBER(10),
  bank_type    VARCHAR2(20),
  bank_id      VARCHAR2(255) not null,
  bank_address VARCHAR2(4000),
  draw_amount  NUMBER(19,2) default 0,
  fee          NUMBER(19,2) default 0,
  real_amount  NUMBER(19,2) default 0,
  create_time  TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  status       NUMBER(10) default 0,
  finish_time  TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  description  VARCHAR2(4000) default '',
  batch_id     VARCHAR2(50) default '',
  province     VARCHAR2(35) default '',
  city         VARCHAR2(35) default ''
);
alter table LOTTERY_DRAW_AMOUNT
  add primary key (ID);


create table LOTTERY_ORDER
(
  id                 VARCHAR2(255) not null,
  adddition          NUMBER(10),
  agencyno           NUMBER(19),
  amount             NUMBER(19,2) not null,
  big_posttaxprize   NUMBER(19,2),
  content            VARCHAR2(4000) not null,
  dead_line          TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  draw_time          TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  firest_matchnum    NUMBER(19),
  hemaiid            VARCHAR2(255),
  last_matchnum      NUMBER(19),
  lottery_type       NUMBER(10) not null,
  multiple           NUMBER(10) not null,
  orderresult_status NUMBER(10),
  order_status       NUMBER(10) not null,
  pay_status         NUMBER(10) not null,
  phase              VARCHAR2(255),
  pretax_prize       NUMBER(19,2),
  print_time         TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  prize_detail       VARCHAR2(255),
  process_time       TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  receive_time       TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  reward_time        TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  small_posttaxprize NUMBER(19,2),
  userno             VARCHAR2(255) not null,
  terminalid         NUMBER(10),
  amt                NUMBER(10),
  isexchanged        NUMBER(2),
  byuserno           VARCHAR2(255) default '',
  wincode            VARCHAR2(255),
  account_type       NUMBER(10) default 0,
  terminal_type_id   NUMBER(10),
  bet_type           NUMBER(10) default 0,
  chase_id           VARCHAR2(255)
);
create index IDX_LOTTERY_ORDER_DEAD_LINE on LOTTERY_ORDER (DEAD_LINE DESC);
create index IDX_LOTTERY_ORDER_RECIVETIME on LOTTERY_ORDER (RECEIVE_TIME DESC);
create index IDX_LOTTERY_ORDER_PHASE on LOTTERY_ORDER (LOTTERY_TYPE, PHASE);
create index IDX_LOTTERY_ORDER_userno on LOTTERY_ORDER (userno);
alter table LOTTERY_ORDER  add primary key (ID);


create table LOTTERY_ORDER_CHASE
(
  id           VARCHAR2(100) not null,
  userno       VARCHAR2(200),
  chase_id     VARCHAR2(200),
  order_id     VARCHAR2(200),
  lottery_type NUMBER(10),
  phase        VARCHAR2(100),
  bet_type     NUMBER(10) default 1,
  status       NUMBER(10) default 0,
  prize        NUMBER(10) default 0,
  create_time  TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  update_time  TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  content      VARCHAR2(4000),
  chase_type   NUMBER(10) default 0
);
create index IDX_ORDER_CASEID on LOTTERY_ORDER_CHASE (CHASE_ID);
create index IDX_O_CT_CP on LOTTERY_ORDER_CHASE (LOTTERY_TYPE, PHASE);
alter table LOTTERY_ORDER_CHASE  add primary key (ID);

create table LOTTERY_PHASE
(
  id                  NUMBER(19) not null,
  create_time         TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  draw_time           TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  end_sale_time       TIMESTAMP(6) not null,
  end_ticket_time     TIMESTAMP(6) not null,
  hemai_end_time      TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  lottery_type        NUMBER(10) not null,
  phase               VARCHAR2(255) not null,
  phase_status        NUMBER(10) not null,
  phase_time_status   NUMBER(10),
  start_sale_time     TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') not null,
  terminal_status     NUMBER(10) default 1 not null,
  wincode             VARCHAR2(4000),
  prize_detail        VARCHAR2(4000),
  phase_encash_status NUMBER(2),
  switch_time         TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  for_sale            NUMBER(10) default 0,
  for_current         NUMBER(10) default 0
);
create unique index IDX_UN_LOTTERY_TYPE_PHASE on LOTTERY_PHASE (LOTTERY_TYPE, PHASE);
alter table LOTTERY_PHASE  add primary key (ID);

create table LOTTERY_TICKET_CONFIG
(
  id                      NUMBER(19) not null,
  batchcode               NUMBER(10),
  batch_time              NUMBER(19),
  beginsaleallot_backward NUMBER(19),
  beginsale_forward       NUMBER(19),
  drawbackward            NUMBER(19),
  endsaleallot_forward    NUMBER(19),
  endsale_forward         NUMBER(19),
  endticket_timeout       NUMBER(19),
  lottery_type            NUMBER(10)
);
create unique index IDX_TICKET_CONFIG_TYPE on LOTTERY_TICKET_CONFIG (LOTTERY_TYPE);
alter table LOTTERY_TICKET_CONFIG  add primary key (ID);

create table LOTTYPE_CONFIG
(
  lottery_type     NUMBER(10) not null,
  state            NUMBER(1) not null,
  end_sale_time    VARCHAR2(10) not null,
  hemai_endt_ime   VARCHAR2(10) not null,
  draw_time        VARCHAR2(10) not null,
  onprize          NUMBER(1),
  autoencash       NUMBER(1),
  lotcenterisvalid NUMBER(1),
  pre_phase_num    NUMBER(5) not null,
  start_sale_time  VARCHAR2(10) not null,
  pre_phase_amt    NUMBER(5)
);
alter table LOTTYPE_CONFIG  add constraint PK_LOTTYPE_CONFIG primary key (LOTTERY_TYPE);


create table PAY_PROPERTY
(
  id           NUMBER(20) not null,
  config_name  VARCHAR2(100) not null,
  config_value VARCHAR2(1000) not null,
  pay_channel  VARCHAR2(50) not null
);
comment on column PAY_PROPERTY.config_name
  is '配置名称';
comment on column PAY_PROPERTY.config_value
  is '配置值';
comment on column PAY_PROPERTY.pay_channel
  is '配置类别';
alter table PAY_PROPERTY  add primary key (ID);
create index PAY_PROPERTY_channel on PAY_PROPERTY(pay_channel);

create table PRIZE_ERROR_LOG
(
  tranaction_id      VARCHAR2(255) not null,
  userno             VARCHAR2(200) not null,
  order_id           VARCHAR2(200) not null,
  lottery_type       NUMBER(10) default 0,
  phase              VARCHAR2(20) default '',
  amt                NUMBER(19,2),
  balance            NUMBER(19,2),
  draw_balance       NUMBER(19,2),
  after_balance      NUMBER(19,2),
  after_draw_balance NUMBER(19,2),
  create_time        TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')
);
alter table PRIZE_ERROR_LOG  add primary key (TRANACTION_ID);

create table TERMINAL
(
  id                NUMBER(19) not null,
  allotforbidperiod VARCHAR2(255),
  is_enabled        NUMBER(10) not null,
  is_paused         NUMBER(10) not null,
  name              VARCHAR2(255) not null,
  sendforbidperiod  VARCHAR2(255),
  terminal_type     NUMBER(10) not null,
  checkforbidperiod VARCHAR2(255)
);
create index IDX_TERMINAL_TERMINAL_TYPE on TERMINAL (TERMINAL_TYPE);
alter table TERMINAL  add primary key (ID);
 
create table TERMINAL_CONFIG
(
  id                NUMBER(19) not null,
  allotforbidperiod VARCHAR2(255),
  is_enabled        NUMBER(10) not null,
  is_paused         NUMBER(10) not null,
  lottery_type      NUMBER(10) not null,
  play_type         NUMBER(10),
  sendforbidperiod  VARCHAR2(255),
  teriminal_id      NUMBER(19) not null,
  terminal_type     NUMBER(10),
  terminateforward  NUMBER(19),
  weight            NUMBER(10) not null,
  checkforbidperiod VARCHAR2(255)
);
create index IDX_TERMINAL_LOTTERY on TERMINAL_CONFIG (LOTTERY_TYPE, TERIMINAL_ID);
alter table TERMINAL_CONFIG add primary key (ID);


create table TERMINAL_PROPERTY
(
  id             NUMBER(19) not null,
  terminalid     NUMBER(19) not null,
  terminale_key  VARCHAR2(255) not null,
  terminal_value VARCHAR2(255) not null
);
alter table TERMINAL_PROPERTY  add primary key (ID);
create index TERMINAL_PROPERTY_tid on TERMINAL_PROPERTY(terminalid);


create table TICKET
(
  id                   VARCHAR2(255) not null,
  addition             NUMBER(10),
  amount               NUMBER(19,2) not null,
  batch_id             VARCHAR2(255),
  batch_index          NUMBER(19),
  content              VARCHAR2(4000) not null,
  create_time          TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  draw_time            TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  exchange_time        TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  external_id          VARCHAR2(255),
  failure_message      VARCHAR2(255),
  failure_type         NUMBER(10),
  is_exchanged         NUMBER(10),
  lottery_type         NUMBER(10) not null,
  multiple             NUMBER(10) default 1 not null,
  orderid              VARCHAR2(255) not null,
  passwd               VARCHAR2(255),
  peilv                VARCHAR2(255),
  phase                VARCHAR2(255),
  play_type            NUMBER(10) not null,
  posttax_prize        NUMBER(19,2),
  pretax_prize         NUMBER(19,2),
  print_time           TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  prize_detail         VARCHAR2(255),
  send_time            TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  status               NUMBER(10) not null,
  terminal_id          NUMBER(19),
  ticket_result_status NUMBER(10),
  terminate_time       TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  userno               VARCHAR2(255),
  dead_line            TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  terminal_type_id     NUMBER(10) default 0
);
create index IDX_TICKET_BATCHID on TICKET (BATCH_ID);
create index IDX_TICKET_CREATE_TIME on TICKET (CREATE_TIME DESC);
create index IDX_TICKET_DEAD_LINE on TICKET (DEAD_LINE);
create index IDX_TICKET_ORDERID on TICKET (ORDERID);
create index IDX_TICKET_SEND_TIME on TICKET (SEND_TIME);
create index IDX_TICKET_STATUS on TICKET (STATUS);
create index IDX_TICKET_TERMINATE_TIME on TICKET (TERMINATE_TIME);
create index IDX_TICKET_TYPE_PHASE on TICKET (LOTTERY_TYPE, PHASE);
create index IND_TICKET_TERMINAL_TYPE_ID on TICKET (TERMINAL_TYPE_ID);
alter table TICKET  add primary key (ID);
 
create table TICKET_BATCH
(
  id                  VARCHAR2(255) not null,
  create_time         TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  lottery_type        NUMBER(10) default 0 not null,
  phase               VARCHAR2(255) default '',
  play_type           NUMBER(10) default 0,
  send_time           TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  ticket_batch_status NUMBER(10),
  terminal_id         NUMBER(19) not null,
  terminal_time       TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  terminal_type_id    NUMBER(10) default 0
);
create index IDX_TICKET_BATCH_TIME on TICKET_BATCH (CREATE_TIME DESC);
create index IDX_TYPE_PHASE_BATCH on TICKET_BATCH (LOTTERY_TYPE, PHASE);
alter table TICKET_BATCH  add primary key (ID);

create table USER_ACCOUNT
(
  userno         VARCHAR2(16) not null,
  balance        NUMBER(19,2) default 0,
  draw_balance   NUMBER(19,2) default 0,
  freeze         NUMBER(19,2) default 0,
  lasttrade_time TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  lasttradeamt   NUMBER(19,2) default 0,
  lastfreeze     NUMBER(19,2) default 0,
  mac            VARCHAR2(255) default '',
  totalbetamt    NUMBER(19,2) default 0,
  totalgiveamt   NUMBER(19,2) default 0,
  totalprizeamt  NUMBER(19,2) default 0,
  total_balance  NUMBER(19,2) default 0
);

alter table USER_ACCOUNT  add primary key (USERNO);


create table USER_ACCOUNT_DETAIL
(
  id                  VARCHAR2(255) not null,
  amt                 NUMBER(19,2) default 0,
  balance             NUMBER(19,2) default 0,
  status              NUMBER(10) default 0,
  create_time         TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  draw_balance        NUMBER(19,2) default 0,
  freeze              NUMBER(19,2) default 0,
  payid               VARCHAR2(255) default 0,
  pay_type            NUMBER(10) default 0,
  userno              VARCHAR2(16) default 0,
  account_detail_type NUMBER(2),
  memo                VARCHAR2(200),
  otherid             VARCHAR2(255)
);
create index IDX_USER_ACCOUNT_DETAIL_TIME on USER_ACCOUNT_DETAIL (CREATE_TIME DESC);
create index IDX_USER_ACCOUNT_DETAIL_USERNO on USER_ACCOUNT_DETAIL (USERNO);
create unique index UN_USER_ACCOUNT_DETAIL_TYPE on USER_ACCOUNT_DETAIL (PAYID, PAY_TYPE, ACCOUNT_DETAIL_TYPE);
alter table USER_ACCOUNT_DETAIL  add primary key (ID);

 
create table USER_DRAW_BANK
(
  id          VARCHAR2(200) not null,
  userno      VARCHAR2(200) not null,
  real_name   VARCHAR2(50) not null,
  bank_type   VARCHAR2(255),
  bank_name   VARCHAR2(200),
  bank_card   VARCHAR2(255) not null,
  province    VARCHAR2(255) not null,
  city        VARCHAR2(255) not null,
  branch      VARCHAR2(255) not null,
  create_time TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  update_time TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')
);
create index IDX_DRAW_BANK_USERNO on USER_DRAW_BANK (USERNO);
alter table USER_DRAW_BANK
  add primary key (ID);


create table USER_INFO
(
  userno           VARCHAR2(255) not null,
  username         VARCHAR2(255) not null,
  real_name        VARCHAR2(255) default '',
  passwd           VARCHAR2(255) not null,
  last_login_time  TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  phoneno          VARCHAR2(25),
  idcard           VARCHAR2(255),
  status           NUMBER(2) default 0 not null,
  agency_no        VARCHAR2(50),
  ticket_type_id   NUMBER(10),
  photo_url        VARCHAR2(255),
  qq               VARCHAR2(255),
  register_time    TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  terminal_type_id NUMBER(10),
  email            VARCHAR2(2000)
);
create index IDX_USER_INFO on USER_INFO (REGISTER_TIME DESC);
create unique index INDEX_UN_USER_INFO on USER_INFO (USERNAME);
alter table USER_INFO  add primary key (USERNO);


create table USER_TRANSACTION
(
  id          VARCHAR2(255) not null,
  userno      VARCHAR2(255) not null,
  trade_no    VARCHAR2(255) default '',
  status      NUMBER(10) default 0,
  create_time TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  finish_time TIMESTAMP(6) default to_timestamp('2013-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  fee         NUMBER(19,2) default 0,
  channel     VARCHAR2(100) default '',
  pay_type    VARCHAR2(50) default '',
  description VARCHAR2(255),
  amount      NUMBER(19,2),
  real_amount NUMBER(10,2) default 0,
  give_amount NUMBER(10) default 0,
  give_id     VARCHAR2(255) default 0
);
alter table USER_TRANSACTION add primary key (ID);
create index IDX_TRANSACTION_userno on USER_TRANSACTION (userno);

create table ZC_RACE
(
  id               NUMBER(20) not null,
  lottery_type     NUMBER(10) not null,
  phase            VARCHAR2(20) not null,
  match_num        NUMBER(10),
  create_time      TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  match_id         NUMBER(10) default 0,
  match_date       TIMESTAMP(6) default to_timestamp('2014-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
  home_team        VARCHAR2(255),
  away_team        VARCHAR2(255),
  match_name       VARCHAR2(255),
  half_score       VARCHAR2(255),
  final_score      VARCHAR2(255),
  average_index    VARCHAR2(4000),
  ext              VARCHAR2(4000),
  is_possibledelay VARCHAR2(20)
);
alter table ZC_RACE add primary key (ID);


