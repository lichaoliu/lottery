

CREATE TABLE `auto_join` (
  `id` bigint(20) NOT NULL,
  `userno` varchar(255) NOT NULL,
  `lottery_type` int(6) NOT NULL,
  `starter` varchar(255) DEFAULT NULL,
  `times` int(3) DEFAULT '0',
  `join_amt` bigint(10) DEFAULT NULL,
  `join_times` int(3) DEFAULT NULL,
  `fail_num` int(3) DEFAULT NULL,
  `create_time` datetime  DEFAULT NULL,
  `update_time` datetime  DEFAULT NULL,
  `status` int(1) NOT NULL,
  `force_join` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `auto_join_detail`;
CREATE TABLE `auto_join_detail` (
  `id` bigint(20) NOT NULL,
  `auto_join_id` bigint(20) NOT NULL,
  `caselot_buy_id` varchar(60) DEFAULT NULL,
  `userno` varchar(60) NOT NULL,
  `caselot_id` varchar(255) NOT NULL,
  `join_amt` bigint(10) DEFAULT '0',
  `status` int(1) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `create_time` datetime  DEFAULT NULL,
  `lottery_type` int(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `beidan_analyze_data`;
CREATE TABLE `beidan_analyze_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `arankings` text,
  `asia_index` text,
  `endtime` datetime DEFAULT NULL,
  `eour_index` text,
  `guest_after_matches` text,
  `guest_pre_matches` text,
  `home_after_matches` text,
  `home_pre_matches` text,
  `hrankings` text,
  `matchinfo` text,
  `match_num` varchar(20) DEFAULT NULL,
  `phase` varchar(10) DEFAULT NULL,
  `pre_clash_matches` text,
  `rankings` text,
  `recommend` text,
  `score` text,
  `score_detail` text,
  `state` int(11) DEFAULT NULL,
  `thirdpartid` varchar(15) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `betting_limit_number`;
CREATE TABLE `betting_limit_number` (
  `id` bigint(20) NOT NULL,
  `limit_type` int(2) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `play_type` int(10) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `content` varchar(4000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bettinglimit_lp` (`lottery_type`,`play_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `coupon_type`;
CREATE TABLE `coupon_type` (
  `id` bigint(10) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime  DEFAULT NULL,
  `lottery_types` varchar(300) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `order_amount` decimal(19) NOT NULL,
  `discount_amount` decimal(19) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date NOT NULL,
  `status` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS  `dc_race`;
CREATE TABLE `dc_race` (
  `id` bigint(20) NOT NULL,
  `away_team` varchar(255) DEFAULT NULL,
  `bcsfp_result` varchar(255) DEFAULT NULL,
  `bf_result` varchar(255) DEFAULT NULL,
  `catch_id` int(11) DEFAULT NULL,
  `create_time` datetime  NULL DEFAULT '0000-00-00 00:00:00',
  `dc_type` int(5) NOT NULL,
  `end_sale_time` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `ext` varchar(255) DEFAULT NULL,
  `fx_id` int(11) DEFAULT '0',
  `half_score` varchar(255) DEFAULT NULL,
  `handicap` varchar(255) DEFAULT NULL,
  `home_team` varchar(255) DEFAULT NULL,
  `jqs_result` varchar(255) DEFAULT NULL,
  `match_date` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `match_name` varchar(255) DEFAULT NULL,
  `match_num` int(11) DEFAULT NULL,
  `phase` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `prize_status` int(11) DEFAULT NULL,
  `prize_time` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `sf_result` varchar(255) DEFAULT NULL,
  `sfp_result` varchar(255) DEFAULT NULL,
  `sp_bcsfp` varchar(255) DEFAULT NULL,
  `sp_bf` varchar(255) DEFAULT NULL,
  `sp_jqs` varchar(255) DEFAULT NULL,
  `sp_sf` varchar(255) DEFAULT NULL,
  `sp_sfp` varchar(255) DEFAULT NULL,
  `sp_sxds` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sxds_result` varchar(255) DEFAULT NULL,
  `update_time` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `whole_score` varchar(255) DEFAULT NULL,
  `result_from` varchar(255) DEFAULT NULL,
  `game_type` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_un_dcrace_p_m` (`phase`,`match_num`,`dc_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `give_activity`;
CREATE TABLE `give_activity` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(255) DEFAULT NULL,
  `end_time` datetime  DEFAULT '0000-00-00 00:00:00',
  `isfirst_send` int(11) DEFAULT NULL,
  `is_percentage` int(11) DEFAULT NULL,
  `istop_limit` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `send_money` decimal(19) DEFAULT '0',
  `send_type` int(11) DEFAULT NULL,
  `start_time` datetime NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) DEFAULT NULL,
  `top_limit` decimal(19) DEFAULT '0',
  `TYPE` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `id_generator`;
CREATE TABLE `id_generator` (
  `id` bigint(20) NOT NULL,
  `seqid` bigint(20) NOT NULL,
  `seqname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `jc_guanyajun_race`;
CREATE TABLE `jc_guanyajun_race` (
  `lottery_type` int(11) NOT NULL,
  `phase` varchar(10) NOT NULL,
  `matchnum` varchar(5) NOT NULL,
  `content` varchar(20) NOT NULL,
  `status` int(2) NOT NULL,
  `prize_status` int(2) NOT NULL,
  `odd` varchar(10) DEFAULT ' ',
  `iswin` int(2) NOT NULL,
  `probability` varchar(10) DEFAULT ' ',
  `supportrate` varchar(10) DEFAULT ' ',
  PRIMARY KEY (`lottery_type`,`phase`,`matchnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `jclq_race`;
CREATE TABLE `jclq_race` (
  `match_num` varchar(255) NOT NULL,
  `away_team` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dynamic_draw_status` int(11) DEFAULT NULL,
  `dynamic_handicap` varchar(255) DEFAULT NULL,
  `dynamic_preset_score` varchar(255) DEFAULT NULL,
  `dynamic_sale_dxf_status` int(11) DEFAULT NULL,
  `dynamic_sale_rfsf_status` int(11) DEFAULT NULL,
  `dynamic_sale_sf_status` int(11) DEFAULT NULL,
  `dynamic_sale_sfc_status` int(11) DEFAULT NULL,
  `end_sale_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ext` varchar(255) DEFAULT NULL,
  `final_score` varchar(255) DEFAULT NULL,
  `first_quarter` varchar(255) DEFAULT NULL,
  `fourth_quarter` varchar(255) DEFAULT NULL,
  `fx_id` int(11) DEFAULT NULL,
  `home_team` varchar(255) DEFAULT NULL,
  `match_date` datetime NULL DEFAULT NULL,
  `match_name` varchar(255) DEFAULT NULL,
  `official_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `official_num` varchar(255) DEFAULT NULL,
  `official_weekday` varchar(255) DEFAULT NULL,
  `phase` varchar(255) NOT NULL,
  `priority` int(11) DEFAULT '0',
  `prize_dxf` varchar(255) DEFAULT NULL,
  `prize_rfsf` varchar(255) DEFAULT NULL,
  `prize_sf` varchar(255) DEFAULT NULL,
  `prize_sfc` varchar(255) DEFAULT NULL,
  `prize_status` int(11) DEFAULT NULL,
  `second_quarter` varchar(255) DEFAULT NULL,
  `static_draw_status` int(11) DEFAULT NULL,
  `static_handicap` varchar(255) DEFAULT NULL,
  `static_preset_score` varchar(255) DEFAULT NULL,
  `static_sale_dxf_status` int(11) DEFAULT NULL,
  `static_sale_rfsf_status` int(11) DEFAULT NULL,
  `static_sale_sf_status` int(11) DEFAULT NULL,
  `static_sale_sfc_status` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `third_quarter` varchar(255) DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `dg_static_sale_sf_status` int(10) DEFAULT '0',
  `dg_static_sale_rfsf_status` int(10) DEFAULT '0',
  `dg_static_sale_dxf_status` int(10) DEFAULT '0',
  `dg_static_sale_sfc_status` int(10) DEFAULT '0',
  `result_from` varchar(255) DEFAULT NULL,
  `match_short_name` varchar(30) DEFAULT '',
  `home_team_short` varchar(30) DEFAULT '',
  `away_team_short` varchar(30) DEFAULT '',
  PRIMARY KEY (`match_num`),
  KEY `jclq_race_phase` (`phase`),
  KEY `idx_jclq_status_time` (`status`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `jczq_race`;
CREATE TABLE `jczq_race` (
  `match_num` varchar(255) NOT NULL,
  `away_team` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dynamic_draw_status` int(11) DEFAULT NULL,
  `dynamic_sale_bf_status` int(11) DEFAULT NULL,
  `dynamic_sale_bqc_status` int(11) DEFAULT NULL,
  `dynamic_sale_jqs_status` int(11) DEFAULT NULL,
  `dynamic_sale_spf_status` int(11) DEFAULT NULL,
  `dynamic_sale_spf_wrq_status` int(11) DEFAULT NULL,
  `end_sale_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ext` varchar(255) DEFAULT NULL,
  `final_score` varchar(255) DEFAULT NULL,
  `first_half` varchar(255) DEFAULT NULL,
  `handicap` varchar(255) DEFAULT NULL,
  `home_team` varchar(255) DEFAULT NULL,
  `match_date` datetime(6) NULL DEFAULT NULL,
  `match_name` varchar(255) DEFAULT NULL,
  `official_date` datetime NULL DEFAULT NULL,
  `official_num` varchar(255) DEFAULT NULL,
  `official_weekday` varchar(255) DEFAULT NULL,
  `phase` varchar(255) NOT NULL,
  `priority` int(11) DEFAULT NULL,
  `prize_bf` varchar(255) DEFAULT NULL,
  `prize_bqc` varchar(255) DEFAULT NULL,
  `prize_jqs` varchar(255) DEFAULT NULL,
  `prize_spf` varchar(255) DEFAULT NULL,
  `prize_spf_wrq` varchar(255) DEFAULT NULL,
  `prize_status` int(11) DEFAULT NULL,
  `second_half` varchar(255) DEFAULT NULL,
  `static_draw_status` int(11) DEFAULT NULL,
  `static_sale_bf_status` int(11) DEFAULT NULL,
  `static_sale_bqc_status` int(11) DEFAULT NULL,
  `static_sale_jqs_status` int(11) DEFAULT NULL,
  `static_sale_spf_status` int(11) DEFAULT NULL,
  `static_sale_spf_wrq_status` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `dg_static_sale_spf_status` int(10) DEFAULT '0',
  `dg_static_sale_jqs_status` int(10) DEFAULT '0',
  `dg_static_sale_bqc_status` int(10) DEFAULT '0',
  `dg_static_sale_spf_wrq_status` int(10) DEFAULT '0',
  `dg_static_sale_bf_status` int(10) DEFAULT '0',
  `result_from` varchar(255) DEFAULT NULL,
  `match_short_name` varchar(30) DEFAULT '',
  `home_team_short` varchar(30) DEFAULT '',
  `away_team_short` varchar(30) DEFAULT '',
  PRIMARY KEY (`match_num`),
  KEY `jczq_race_phase` (`phase`),
  KEY `idx_jczq_status_time` (`status`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `jingcai_analyze_data`;
CREATE TABLE `jingcai_analyze_data` (
  `match_num` varchar(15) NOT NULL,
  `asia_index` text,
  `dx_index` text,
  `endtime` datetime DEFAULT NULL,
  `eour_index` text,
  `guest_after_matches` text,
  `guest_pre_matches` text,
  `home_after_matches` text,
  `home_pre_matches` text,
  `letball_index` text,
  `matchinfo` text,
  `pre_clash_matches` text,
  `rankings` text,
  `recommend` text,
  `state` int(11) DEFAULT NULL,
  `thirdpartid` varchar(15) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `arankings` text,
  `erankings` text,
  `hrankings` text,
  `wrankings` text,
  `score_detail` text,
  `score` text,
  `phase` varchar(10) DEFAULT NULL,
  `awayteam` varchar(25) DEFAULT NULL,
  `hometeam` varchar(25) DEFAULT NULL,
  `matchname` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`match_num`),
  KEY `idx_state` (`state`),
  KEY `idx_type` (`type`),
  KEY `idx_phase` (`phase`),
  KEY `idx_thirdpartid` (`thirdpartid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_addprize_strategy`;
CREATE TABLE `lottery_addprize_strategy` (
  `lottery_type` int(10) NOT NULL,
  `prize_level` varchar(200) NOT NULL,
  `start_phase` varchar(200) NOT NULL,
  `end_phase` varchar(200) NOT NULL,
  `status` int(10) NOT NULL DEFAULT '0',
  `add_amt` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`lottery_type`,`prize_level`,`start_phase`,`end_phase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_agency`;
CREATE TABLE `lottery_agency` (
  `agency_no` varchar(200) NOT NULL,
  `parent_agency` varchar(200) NOT NULL DEFAULT '0',
  `agency_type` int(5) NOT NULL DEFAULT '0',
  `status` int(5) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `level` int(2) NOT NULL,
  `leaf` int(2) NOT NULL,
  `agency_name` varchar(200) DEFAULT '0',
  `agency_key` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`agency_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_agency_point_location`;
CREATE TABLE `lottery_agency_point_location` (
  `id` bigint(20) NOT NULL,
  `agency_no` varchar(200) NOT NULL DEFAULT '0',
  `parent_agency` varchar(200) NOT NULL DEFAULT '0',
  `lottery_type` int(10) NOT NULL DEFAULT '0',
  `point_location` decimal(19,4) NOT NULL DEFAULT '0.0000',
  `agency_point_location` decimal(19,4) NOT NULL DEFAULT '0.0000',
  `parent_point_location` decimal(19,4) NOT NULL DEFAULT '0.0000',
  `status` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_caselot`;
CREATE TABLE `lottery_caselot` (
  `id` varchar(255) NOT NULL,
  `buyamt_by_follower` bigint(20) NOT NULL,
  `buyamt_by_starter` bigint(20) DEFAULT NULL,
  `caselotinfo` varchar(255) DEFAULT NULL,
  `commision_ratio` int(6) NOT NULL,
  `content`  text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `display_state` int(2) DEFAULT NULL,
  `display_state_memo` varchar(100)   DEFAULT NULL,
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_exchanged` int(1) DEFAULT '0',
  `lots_type` int(5) DEFAULT NULL,
  `lottery_type` int(11) NOT NULL,
  `min_amt` bigint(20) DEFAULT NULL,
  `orderid` varchar(255) DEFAULT NULL,
  `participant_count` bigint(20) DEFAULT '0',
  `phase` varchar(50)   NOT NULL,
  `safe_amt` bigint(20) DEFAULT '0',
  `sort_state` int(5) DEFAULT '0',
  `start_time` datetime DEFAULT NULL,
  `starter` varchar(60)   NOT NULL,
  `state` int(5) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_amt` bigint(20) DEFAULT NULL,
  `visibility` int(11) DEFAULT NULL,
  `win_big_amt` bigint(20) DEFAULT NULL,
  `win_detail` varchar(255)   DEFAULT NULL,
  `win_pre_amt` bigint(20) DEFAULT NULL,
  `win_start_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `start_name` varchar(100) DEFAULT NULL,
  `order_result_status` int(5) DEFAULT '0',
  `is_commission` int(5) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_case_lot_time` (`start_time`),
  KEY `idx_lottery_caselot_l_s_t` (`lottery_type`,`state`,`start_time`),
  KEY `idx_lottery_caselot_l_ss_t` (`sort_state`,`start_time`),
  KEY `caselot_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_caselot_buy`;
CREATE TABLE `lottery_caselot_buy` (
  `id` varchar(255) NOT NULL,
  `buy_draw_amt` decimal(19) DEFAULT NULL,
  `buy_time` datetime DEFAULT NULL,
  `buy_type` int(6) DEFAULT NULL,
  `caselot_id` varchar(60)   NOT NULL,
  `commision_prize_amt` decimal(19) DEFAULT '0',
  `flag` int(2) DEFAULT NULL,
  `freez_draw_amt` decimal(19) DEFAULT NULL,
  `freeze_safe_amt` decimal(19) DEFAULT NULL,
  `is_exchanged` int(1) DEFAULT NULL,
  `lottery_type` int(10) NOT NULL,
  `num` decimal(19) NOT NULL,
  `phase` varchar(50)   NOT NULL,
  `prize_amt` decimal(19) DEFAULT NULL,
  `safe_amt` decimal(19) DEFAULT NULL,
  `userno` varchar(60)   NOT NULL,
  `real_name` varchar(200) DEFAULT NULL,
  `user_name` varchar(200) DEFAULT NULL,
  `total_amount` decimal(19) DEFAULT '0.',
  `lots_type` int(5) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_caselot_case_buy_id` (`caselot_id`),
  KEY `idx_caselot_buy_userno` (`userno`),
  KEY `idx_caselot_buy_time` (`buy_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_channel_partner`;
CREATE TABLE `lottery_channel_partner` (
  `id` int(10) NOT NULL,
  `agencyno` varchar(200) NOT NULL,
  `agencyuser` varchar(200) NOT NULL,
  `userno` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_channe_u_a` (`agencyno`,`agencyuser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_chase`;
CREATE TABLE `lottery_chase` (
  `id` varchar(255) NOT NULL,
  `amount` decimal(19) DEFAULT NULL,
  `batch_num` int(11) DEFAULT NULL,
  `begin_phase` varchar(60) DEFAULT NULL,
  `bet_code` longtext NOT NULL,
  `bet_num` int(11) DEFAULT NULL,
  `cancel_by` int(11) DEFAULT NULL,
  `change_time` datetime NULL DEFAULT NULL,
  `chase_detail` varchar(255) DEFAULT NULL,
  `chase_type` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_phase` varchar(60) DEFAULT NULL,
  `lottery_type` int(11) NOT NULL,
  `prize_total` decimal(19) DEFAULT NULL,
  `remain_amount` decimal(19) DEFAULT NULL,
  `remain_num` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `total_amount` decimal(19) DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `already_prize` decimal(19) DEFAULT '0.00',
  `multiple` int(10) DEFAULT '1',
  `addition` int(2) DEFAULT NULL,
  `end_phase` varchar(200) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `memo` varchar(4000) DEFAULT NULL,
  `buy_agencyno` varchar(200) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_chase_userno` (`userno`),
  KEY `idx_chase_creatime` (`create_time`),
  KEY `idx_chase_lottype_chagetiem` (`lottery_type`,`change_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_chase_buy`;
CREATE TABLE `lottery_chase_buy` (
  `id` varchar(255) NOT NULL,
  `chaseid` varchar(255) NOT NULL,
  `orderid` varchar(255) DEFAULT NULL,
  `lottery_type` int(10) NOT NULL,
  `phase` varchar(200) NOT NULL,
  `status` int(10) DEFAULT NULL,
  `chase_index` int(10) DEFAULT '0',
  `phase_start_time` datetime DEFAULT '0000-00-00 00:00:00',
  `phase_end_time` datetime DEFAULT '0000-00-00 00:00:00',
  `prize` decimal(19) DEFAULT '0',
  `multiple` int(10) DEFAULT '1',
  `remain_num` int(10) DEFAULT NULL,
  `amount` decimal(19) DEFAULT '0',
  `remain_amount` decimal(19) DEFAULT '0',
  `memo` varchar(4000) DEFAULT NULL,
  `addition` int(2) DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `userno` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `chase_type` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `bet_code` longtext NOT NULL,
  `order_result_status` int(2) DEFAULT NULL,
  `order_status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `chase_buy_orderid` (`orderid`),
  KEY `chase_buy_lottery_phase` (`lottery_type`,`phase`),
  KEY `chase_buy_chaseid` (`chaseid`),
  KEY `lottery_chase_buy_createtime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_draw_amount`;
CREATE TABLE `lottery_draw_amount` (
  `id` varchar(255) NOT NULL,
  `bank_address` varchar(255) DEFAULT NULL,
  `bank_id` varchar(255) NOT NULL,
  `bank_type` varchar(255) DEFAULT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `draw_amount` decimal(19) NOT NULL,
  `draw_type` int(11) DEFAULT NULL,
  `fee` decimal(19) DEFAULT '0',
  `finish_time` datetime NULL DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `real_amount` decimal(19) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `operate_type` int(11) DEFAULT '1',
  `submit_time` datetime NULL DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT '0',
  `draw_bank_id` varchar(200) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_draw_mount_userno` (`userno`),
  KEY `idx_draw_mount_ctime` (`create_time`),
  KEY `idx_draw_mount_bid` (`batch_id`),
  KEY `idx_draw_amount_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_logic_machine`;
CREATE TABLE `lottery_logic_machine` (
  `id` bigint(20) NOT NULL,
  `terminal_type` int(10) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `terminal_id` int(10) NOT NULL,
  `start_id` bigint(20) NOT NULL,
  `end_id` bigint(20) NOT NULL,
  `current_id` bigint(20) DEFAULT '0',
  `status` int(3) DEFAULT '0',
  `switch_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `currentdate` varchar(200) DEFAULT NULL,
  `phase` varchar(200) DEFAULT NULL,
  `weight` int(5) NOT NULL DEFAULT '0',
  `ip` varchar(4000) DEFAULT NULL,
  `port` int(10) DEFAULT '0',
  `describe_str` varchar(4000) DEFAULT NULL,
  `city_code` varchar(200) DEFAULT '0',
  PRIMARY KEY (`id`,`terminal_type`,`lottery_type`),
  KEY `logic_machine_tid` (`terminal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_order`;
CREATE TABLE `lottery_order` (
  `id` varchar(255) NOT NULL,
  `account_type` int(11) NOT NULL,
  `adddition` int(11) DEFAULT NULL,
  `agencyno` varchar(255) DEFAULT NULL,
  `amount` decimal(19) NOT NULL,
  `amt` decimal(19) NOT NULL,
  `bet_type` int(11) NOT NULL,
  `big_posttaxprize` decimal(19) DEFAULT '0',
  `byuserno` varchar(255) NOT NULL,
  `chase_id` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL,
  `dead_line` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `draw_time` datetime NULL DEFAULT NULL,
  `first_matchnum` bigint(20) DEFAULT '0',
  `hemaiid` varchar(255) DEFAULT NULL,
  `is_exchanged` int(3) DEFAULT NULL,
  `last_matchnum` bigint(20) DEFAULT '0',
  `lottery_type` int(11) NOT NULL,
  `match_nums` text DEFAULT NULL,
  `multiple` int(11) NOT NULL,
  `order_result_status` int(3) NOT NULL,
  `order_status` int(11) NOT NULL,
  `pay_status` int(11) NOT NULL,
  `phase` varchar(255) NOT NULL,
  `pretax_prize` decimal(19) DEFAULT '0',
  `print_time` datetime NULL DEFAULT NULL,
  `prize_detail` varchar(255) DEFAULT NULL,
  `process_time` datetime NULL DEFAULT NULL,
  `receive_time` datetime(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `reward_time` datetime NULL DEFAULT NULL,
  `small_posttaxprize` decimal(19) DEFAULT '0',
  `terminalid` varchar(255) DEFAULT NULL,
  `terminal_type_id` int(11) DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `wincode` varchar(255) DEFAULT NULL,
  `play_type_str` varchar(4000) DEFAULT NULL,
  `total_prize` decimal(19) DEFAULT '0',
  `add_prize` decimal(19) DEFAULT '0',
  `user_name` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `buy_agencyno` varchar(200) DEFAULT '0',
  `agent_id` varchar(255) NOT NULL DEFAULT '0',
  `ticket_count` bigint(15) DEFAULT '0',
  `fail_count` bigint(15) DEFAULT '0',
  `prize_optimize` int(5) DEFAULT '0',
  `code_filter` int(2) NOT NULL DEFAULT '0',
  `preferential_amount` decimal(19) NOT NULL DEFAULT '0',
  `memo` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `IDX_LOTTERY_ORDER_DEAD_LINE` (`dead_line`),
  KEY `IDX_LOTTERY_ORDER_RECIVETIME` (`receive_time`),
  KEY `IDX_LOTTERY_ORDER_PHASE` (`lottery_type`,`phase`),
  KEY `IDX_LOTTERY_ORDER_userno` (`userno`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_order_result_status` (`order_result_status`),
  KEY `idx_order_printtime` (`print_time`),
  KEY `IDX_USERNO_ORDER_RESULT_STATUS` (`userno`,`order_result_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_phase`;
CREATE TABLE `lottery_phase` (
  `id` bigint(20) NOT NULL,
  `add_pool_amount` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT  NULL,
  `draw_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_sale_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_ticket_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `for_current` int(11) NOT NULL,
  `for_sale` int(11) NOT NULL,
  `hemai_end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lottery_type` int(11) NOT NULL,
  `phase` varchar(255) NOT NULL,
  `phase_encash_status` int(11) DEFAULT NULL,
  `phase_status` int(11) NOT NULL,
  `phase_time_status` int(11) NOT NULL,
  `pool_amount` varchar(255) DEFAULT NULL,
  `prize_detail` varchar(255) DEFAULT NULL,
  `sale_amount` varchar(255) DEFAULT NULL,
  `start_sale_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `switch_time` datetime NULL DEFAULT NULL,
  `terminal_status` int(11) NOT NULL,
  `wincode` varchar(255) DEFAULT NULL,
  `draw_data_from` varchar(200) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `real_draw_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_UN_LOTTERY_TYPE_PHASE` (`lottery_type`,`phase`),
  KEY `idx_lottery_phase_ctime` (`for_current`,`end_sale_time`),
  KEY `idx_lottery_phase_ttime` (`terminal_status`,`start_sale_time`),
  KEY `idx_lottery_phase_ptime` (`phase_status`,`end_ticket_time`),
  KEY `idx_lotteryphase_endsale` (`end_sale_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_phase_draw_config`;
CREATE TABLE `lottery_phase_draw_config` (
  `id` bigint(20) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `terminal_id` int(5) NOT NULL,
  `is_enabled` int(5) NOT NULL,
  `sync_time` int(5) NOT NULL,
  `terminal_name` varchar(200) DEFAULT NULL,
  `is_draw` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_phase_draw_lt` (`lottery_type`,`terminal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_terminal_config`;
CREATE TABLE `lottery_terminal_config` (
  `id` bigint(20) NOT NULL,
  `lottery_type` int(10) DEFAULT NULL,
  `is_enabled` int(5) NOT NULL,
  `is_check_enabled` int(5) NOT NULL,
  `is_paused` int(5) NOT NULL,
  `allot_forbid_period` varchar(4000) DEFAULT NULL,
  `send_forbid_period` varchar(4000) DEFAULT NULL,
  `check_forbid_period` varchar(4000) DEFAULT NULL,
  `comment` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lottery_terminal_config_lotteru` (`lottery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_ticket_config`;
CREATE TABLE `lottery_ticket_config` (
  `id` bigint(20) NOT NULL,
  `batch_count` int(11) DEFAULT NULL,
  `batch_time` bigint(20) DEFAULT NULL,
  `beginsaleallot_backward` bigint(20) DEFAULT NULL,
  `beginsale_forward` bigint(20) DEFAULT NULL,
  `drawbackward` bigint(20) DEFAULT NULL,
  `endsaleallot_forward` bigint(20) DEFAULT NULL,
  `endsale_forward` bigint(20) DEFAULT NULL,
  `endticket_timeout` bigint(20) DEFAULT NULL,
  `lottery_type` int(11) NOT NULL,
  `send_count` int(10) DEFAULT '0',
  `chase_endsale_forward` bigint(20) DEFAULT '0',
  `send_thread_count` int(5) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lottery_type` (`lottery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottery_upload_file`;
CREATE TABLE `lottery_upload_file` (
  `id` varchar(255) NOT NULL,
  `userno` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `phase` varchar(255) NOT NULL,
  `multiple` int(10) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `convert_content` longtext,
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `file_name` varchar(4000) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `lottype_config`;
CREATE TABLE `lottype_config` (
  `lottery_type` int(11) NOT NULL,
  `autoencash` int(11) DEFAULT '0',
  `lotcenterisvalid` int(11) DEFAULT NULL,
  `onprize` int(11) DEFAULT NULL,
  `pre_phase_num` int(11) DEFAULT '0',
  `state` int(11) DEFAULT NULL,
  `heimai_Forward` int(10) DEFAULT '0',
  `upload_Forward` int(5) DEFAULT '0',
  `web_end_sale` int(5) DEFAULT '0',
  `ios_end_sale` int(5) DEFAULT '0',
  `android_end_sale` int(5) DEFAULT '0',
  `is_add_prize` int(5) DEFAULT '0',
  `single_hemai_forward` int(5) DEFAULT '0',
  `sale_enabled` int(5) NOT NULL DEFAULT '1',
  `b2b_forward` int(10) DEFAULT '0',
  `b2b_end_sale` int(5) DEFAULT '0',
  `hemai_end_sale` int(5) DEFAULT '0',
  `chase_end_sale` int(5) DEFAULT '0',
  `end_forward` int(10) DEFAULT '0',
  PRIMARY KEY (`lottery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `merchant`;
CREATE TABLE `merchant` (
  `merchant_code` varchar(255) NOT NULL,
  `secret_key` varchar(4000) DEFAULT NULL,
  `ip` varchar(4000) DEFAULT NULL,
  `credit_balance` decimal(19) DEFAULT '0',
  `merchant_name` varchar(255) DEFAULT NULL,
  `status` int(3) DEFAULT NULL,
  `notice_url` varchar(4000) DEFAULT NULL,
  `real_name` varchar(200) DEFAULT NULL,
  `phoneno` varchar(200) DEFAULT NULL,
  `idcard` varchar(200) DEFAULT NULL,
  `is_notice` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`merchant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `merchant_notice`;
CREATE TABLE `merchant_notice` (
  `orderid` varchar(200) NOT NULL,
  `merchant_code` varchar(10) NOT NULL,
  `merchant_no` varchar(200) NOT NULL,
  `lottery_type` int(5) NOT NULL,
  `phase` varchar(20) NOT NULL,
  `order_status` int(2) NOT NULL,
  `order_result_status` int(2) NOT NULL,
  `total_prize` decimal(19) DEFAULT '0',
  `order_status_notice` int(5) DEFAULT '0',
  `prize_status_notice` int(5) DEFAULT '0',
  `print_time` datetime NULL DEFAULT NULL,
  `batch_id` varchar(200) DEFAULT '0',
  PRIMARY KEY (`orderid`),
  UNIQUE KEY `un_idx_mnotice` (`merchant_code`,`merchant_no`),
  KEY `idx_mnotice_lp` (`lottery_type`,`phase`),
  KEY `idx_mnotice_orderstatus` (`order_status_notice`),
  KEY `idx_mnotice_prizestatus` (`prize_status_notice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `merchant_order`;
CREATE TABLE `merchant_order` (
  `orderid` varchar(255) NOT NULL,
  `merchant_code` varchar(255) NOT NULL,
  `merchant_no` varchar(255) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `phase` varchar(25) NOT NULL,
  `multiple` int(8) DEFAULT NULL,
  `play_type` int(10) DEFAULT NULL,
  `bet_code` longtext,
  `order_status` int(5) NOT NULL,
  `order_result_status` int(5) NOT NULL,
  `is_exchanged` int(3) NOT NULL,
  `wincode` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT '0000-00-00 00:00:00.000000',
  `print_time` datetime DEFAULT '0000-00-00 00:00:00',
  `addition` int(4) DEFAULT NULL,
  `prize_detail` varchar(255) DEFAULT NULL,
  `total_prize` decimal(19) DEFAULT '0',
  `amount` decimal(19) NOT NULL DEFAULT '0',
  `order_status_notice` int(5) DEFAULT '0',
  `prize_status_notice` int(5) DEFAULT '0',
  `end_time` datetime NULL DEFAULT NULL,
  `batch_id` varchar(200) DEFAULT '0',
  PRIMARY KEY (`orderid`),
  UNIQUE KEY `merchant_order_unique` (`merchant_code`,`merchant_no`),
  KEY `idx_merchant_orderlp` (`lottery_type`,`phase`),
  KEY `idx_merchantorder_status` (`order_status_notice`),
  KEY `idx_merchantprize_status` (`prize_status_notice`),
  KEY `idx_morder_creat` (`create_time`),
  KEY `idx_morder_endtime` (`end_time`),
  KEY `idx_notice_create` (`order_status_notice`,`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `pay_property`;
CREATE TABLE `pay_property` (
  `id` bigint(20) NOT NULL,
  `config_name` varchar(255) NOT NULL,
  `config_value` varchar(4000) NOT NULL,
  `pay_channel` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `pay_property_channel` (`pay_channel`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS  `prize_error_log`;
CREATE TABLE `prize_error_log` (
  `tranaction_id` varchar(255) NOT NULL,
  `after_balance` decimal(19) DEFAULT NULL,
  `after_draw_balance` decimal(19) DEFAULT NULL,
  `amt` decimal(19) DEFAULT NULL,
  `balance` decimal(19) DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `draw_balance` decimal(19) DEFAULT NULL,
  `lottery_type` int(11) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `phase` varchar(255) DEFAULT NULL,
  `userno` varchar(255) DEFAULT NULL,
  `bet_type` int(3) DEFAULT NULL,
  PRIMARY KEY (`tranaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `system_exception_message`;
CREATE TABLE `system_exception_message` (
  `id` int(10) NOT NULL,
  `is_read` int(2) DEFAULT '0',
  `message` longblob,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `s_exception_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `t_award_level`;
CREATE TABLE `t_award_level` (
  `lottery_type` int(11) NOT NULL,
  `prize_level` varchar(255) NOT NULL,
  `extra_prize` bigint(20) DEFAULT NULL,
  `level_name` varchar(255) NOT NULL,
  `prize` bigint(20) NOT NULL,
  PRIMARY KEY (`lottery_type`,`prize_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `t_member_account`;
CREATE TABLE `t_member_account` (
  `terminal_type` int(10) NOT NULL,
  `agent_code` varchar(200) NOT NULL,
  `terminal_name` varchar(200) DEFAULT NULL,
  `balance` decimal(19) DEFAULT '0',
  `credit_balance` decimal(19) DEFAULT '0',
  `toatal_prize` decimal(19) DEFAULT '0',
  `samll_prize` decimal(19) DEFAULT '0',
  `big_prize` decimal(19) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `warn_amount` decimal(19) DEFAULT '0',
  `sms_flag` int(2) NOT NULL DEFAULT '0',
  `is_sync` int(5) DEFAULT '0',
  PRIMARY KEY (`terminal_type`,`agent_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `terminal`;
CREATE TABLE `terminal` (
  `id` bigint(20) NOT NULL,
  `allotforbidperiod` varchar(255) DEFAULT NULL,
  `checkForbidPeriod` varchar(255) DEFAULT NULL,
  `is_enabled` int(11) NOT NULL,
  `is_paused` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sendforbidperiod` varchar(255) DEFAULT NULL,
  `terminal_type` int(11) NOT NULL,
  `is_check_enabled` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_terminal_terminal_type` (`terminal_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `terminal_config`;
CREATE TABLE `terminal_config` (
  `id` bigint(20) NOT NULL,
  `allotforbidperiod` varchar(255) DEFAULT NULL,
  `checkForbidPeriod` varchar(255) DEFAULT NULL,
  `is_enabled` int(11) NOT NULL,
  `is_paused` int(11) NOT NULL,
  `lottery_type` int(11) NOT NULL,
  `play_type` int(11) DEFAULT NULL,
  `sendforbidperiod` varchar(255) DEFAULT NULL,
  `terminal_id` bigint(20) NOT NULL,
  `terminal_type` int(11) DEFAULT NULL,
  `terminateForward` bigint(20) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `terminal_name` varchar(4000) DEFAULT NULL,
  `is_check_enabled` int(2) NOT NULL DEFAULT '0',
  `terminate_allot_forward` bigint(20) DEFAULT '0',
  `prefer_amount_region` varchar(4000) DEFAULT NULL,
  `playtype_not_contain_enabled` int(2) NOT NULL DEFAULT '0',
  `playtype_not_contain` longblob,
  `amount_enabled` int(2) NOT NULL DEFAULT '0',
  `mix_contain` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_un_terminal_config_unique` (`lottery_type`,`terminal_id`,`play_type`),
  KEY `idex_tremina_confipl` (`play_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `terminal_property`;
CREATE TABLE `terminal_property` (
  `id` bigint(20) NOT NULL,
  `terminalid` bigint(20) NOT NULL,
  `terminale_key` varchar(255) NOT NULL,
  `terminal_value` varchar(4000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `terminal_property_tid` (`terminalid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `ticket`;
CREATE TABLE `ticket` (
  `id` varchar(255) NOT NULL,
  `addition` int(11) DEFAULT NULL,
  `amount` decimal(19) NOT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  `batch_index` bigint(20) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime(6) NOT NULL ,
  `dead_line` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `draw_time` datetime NULL DEFAULT NULL,
  `exchange_time` datetime NULL DEFAULT NULL,
  `external_id` varchar(255) DEFAULT NULL,
  `failure_message` varchar(255) DEFAULT NULL,
  `failure_type` int(11) DEFAULT NULL,
  `is_exchanged` int(11) DEFAULT NULL,
  `lottery_type` int(11) NOT NULL,
  `multiple` int(11) NOT NULL,
  `orderid` varchar(255) NOT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  `peilv` text,
  `phase` varchar(255) NOT NULL,
  `play_type` int(11) NOT NULL,
  `posttax_prize` decimal(19) DEFAULT '0',
  `pretax_prize` decimal(19) DEFAULT '0',
  `print_time` datetime NULL DEFAULT NULL,
  `prize_detail` varchar(255) DEFAULT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `terminal_id` bigint(20) DEFAULT NULL,
  `terminal_type` int(10) DEFAULT '0',
  `terminate_time` datetime NULL DEFAULT NULL,
  `ticket_result_status` int(11) DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `total_prize` decimal(19) DEFAULT '0',
  `add_prize` decimal(19) DEFAULT '0',
  `user_name` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `send_time_long` bigint(32) DEFAULT '0',
  `dead_time_long` bigint(32) DEFAULT '0',
  `match_nums` text,
  `first_matchnum` bigint(20) DEFAULT '0',
  `last_matchnum` bigint(20) DEFAULT '0',
  `agency_prize` decimal(19) DEFAULT '0',
  `agency_exchanged` int(10) DEFAULT '0',
  `machine_code` bigint(15) DEFAULT '0',
  `sell_run_code` bigint(15) DEFAULT '0',
  `md5_code` varchar(200) DEFAULT '0',
  `agent_id` varchar(255) NOT NULL DEFAULT '0',
  `order_amount` decimal(19) NOT NULL DEFAULT '0',
  `ticket_end_time` datetime NOT NULL ,
  `serial_id` varchar(4000) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_ticket_batchid` (`batch_id`),
  KEY `idx_ticket_create_time` (`create_time`),
  KEY `idx_ticket_dead_line` (`dead_line`),
  KEY `idx_ticket_orderid` (`orderid`),
  KEY `idx_ticket_send_time` (`send_time`),
  KEY `idx_ticket_terminate_time` (`terminate_time`),
  KEY `idx_ticket_type_phase` (`lottery_type`,`phase`),
  KEY `ind_ticket_terminal_type_id` (`terminal_type`),
  KEY `ticke_send_long` (`send_time_long`),
  KEY `ticke_dead_long` (`dead_time_long`),
  KEY `idx_ticket_t_s_send` (`terminal_id`,`status`,`send_time`),
  KEY `idx_ticket_l_match` (`last_matchnum`),
  KEY `idex_ticket_externalId` (`external_id`),
  KEY `idx_ticket_tid_createtime` (`terminal_id`,`create_time`),
  KEY `idx_ticket_stat_dead_line` (`status`,`dead_line`),
  KEY `idx_ticket_result_time` (`ticket_result_status`,`draw_time`),
  KEY `idx_ticket_agentid` (`agent_id`),
  KEY `idx_st_lo_te_de` (`status`,`lottery_type`,`terminal_id`,`dead_line`),
  KEY `idx_status_create_time` (`status`,`create_time`),
  KEY `idx_terminal_id_send_time` (`terminal_id`,`send_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `ticket_allot_log`;
CREATE TABLE `ticket_allot_log` (
  `id` varchar(255) NOT NULL ,
  `ticket_id` varchar(255) DEFAULT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `terminal_id` int(10) DEFAULT NULL,
  `terminal_type_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ticket_allot_log_time` (`create_time`),
  KEY `idx_ticket_allot_log_tikcet` (`ticket_id`),
  KEY `idx_ticket_allot_log_batch` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `ticket_batch`;
CREATE TABLE `ticket_batch` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `lottery_type` int(11) NOT NULL,
  `phase` varchar(255) NOT NULL,
  `play_type` int(11) NOT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `ticket_batch_status` int(11) NOT NULL,
  `terminal_id` bigint(20) NOT NULL,
  `terminal_type_id` bigint(20) DEFAULT NULL,
  `terminal_time` datetime NOT NULL ,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_TICKET_BATCH_TIME` (`create_time`),
  KEY `IDX_TYPE_PHASE_BATCH` (`lottery_type`,`phase`),
  KEY `idx_ticket_batch_type_statsu_time` (`lottery_type`,`ticket_batch_status`,`terminal_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `ticket_batch_send_log`;
CREATE TABLE `ticket_batch_send_log` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `batch_id` varchar(255) DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `terminal_id` int(10) DEFAULT NULL,
  `terminal_type_id` int(10) DEFAULT NULL,
  `error_message` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_batch_send_log_time` (`create_time`),
  KEY `idx_batch_send_log_batchid` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_account`;
CREATE TABLE `user_account` (
  `userno` varchar(255) NOT NULL,
  `balance` decimal(19) DEFAULT '0',
  `draw_balance` decimal(19) DEFAULT '0',
  `freeze` decimal(19) DEFAULT '0',
  `lasttrade_time` datetime NULL DEFAULT NULL,
  `last_trade_amt` decimal(19) DEFAULT '0',
  `last_freeze` decimal(19) DEFAULT '0',
  `mac` varchar(255) DEFAULT NULL,
  `total_balance` decimal(19) DEFAULT '0',
  `total_bet_amt` decimal(19) DEFAULT '0',
  `totalgiveamt` decimal(19) DEFAULT '0',
  `total_prize_amt` decimal(19) DEFAULT '0',
  `username` varchar(255) DEFAULT NULL,
  `total_recharge` decimal(19) DEFAULT '0',
  `give_balance` decimal(19) DEFAULT '0',
  `phoneno` varchar(200) DEFAULT '0',
  PRIMARY KEY (`userno`),
  UNIQUE KEY `user_account_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_account_add`;
CREATE TABLE `user_account_add` (
  `id` varchar(255) NOT NULL,
  `userno` varchar(255) NOT NULL,
  `amount` decimal(19) DEFAULT '0',
  `for_draw` int(3) DEFAULT NULL,
  `autdit_status` int(3) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `memo` varchar(4000) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `aduiter` varchar(255) DEFAULT NULL,
  `error_message` varchar(4000) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `is_add` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_account_add_username` (`username`),
  KEY `user_account_add_userno` (`userno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_account_detail`;
CREATE TABLE `user_account_detail` (
  `id` varchar(255) NOT NULL,
  `amt` decimal(19) NOT NULL,
  `balance` decimal(19) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime(6) NOT NULL,
  `account_detail_type` int(11) NOT NULL,
  `draw_balance` decimal(19) DEFAULT NULL,
  `freeze` decimal(19) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `otherid` varchar(255) DEFAULT NULL,
  `payid` varchar(255) NOT NULL,
  `pay_type` int(11) NOT NULL,
  `userno` varchar(255) NOT NULL,
  `sign_status` int(4) DEFAULT '0',
  `finish_time` datetime DEFAULT NULL,
  `lottery_type` int(10) DEFAULT '0',
  `phase` varchar(200) DEFAULT '0',
  `draw_amount` decimal(19) DEFAULT '0',
  `not_draw_amount` decimal(19) DEFAULT '0',
  `give_amount` decimal(19,2) DEFAULT '0',
  `agency_no` varchar(200) DEFAULT '0',
  `user_name` varchar(255) NOT NULL DEFAULT '0',
  `order_prize_amount` decimal(19) DEFAULT '0',
  `order_prize_type` int(5) DEFAULT '0',
  `orderid` varchar(255) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_user_account_detail_type` (`payid`,`pay_type`,`account_detail_type`),
  KEY `idx_user_account_detail_time` (`create_time`),
  KEY `idx_user_account_detail_userno` (`userno`),
  KEY `idx_account_detail_l` (`lottery_type`),
  KEY `idx_uaccountdetail_orderid` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_account_handsel`;
CREATE TABLE `user_account_handsel` (
  `userno` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `balance` decimal(19) NOT NULL DEFAULT '0',
  `total_give` decimal(19) NOT NULL DEFAULT '0',
  `last_transaction` decimal(19) DEFAULT '0',
  `payid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userno`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_achievement`;
CREATE TABLE `user_achievement` (
  `id` bigint(20) NOT NULL,
  `userno` varchar(255) DEFAULT NULL,
  `lottery_type` int(11) NOT NULL,
  `effective_achievement` bigint(20) DEFAULT NULL,
  `ineffective_achievement` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_achievement_detail`;
CREATE TABLE `user_achievement_detail` (
  `id` bigint(20) NOT NULL,
  `userno` varchar(60) NOT NULL,
  `lottery_type` int(11) NOT NULL,
  `achievement` int(10) DEFAULT NULL,
  `achievement_type` int(1) DEFAULT NULL,
  `caselot_id` varchar(60) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_coupon`;
CREATE TABLE `user_coupon` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime NULL DEFAULT NULL,
  `order_time` datetime NULL DEFAULT NULL,
  `end_date` date NOT NULL,
  `coupon_type_id` bigint(10) DEFAULT NULL,
  `coupon_type_desc` varchar(500) NOT NULL,
  `order_id` varchar(50) DEFAULT NULL,
  `status` int(2) NOT NULL,
  `userno` varchar(50) NOT NULL,
  `preferential_amount` decimal(19,0) DEFAULT '0',
  `lottery_types` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_draw_bank`;
CREATE TABLE `user_draw_bank` (
  `id` varchar(255) NOT NULL,
  `bank_card` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_type` varchar(255) DEFAULT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `draw_type` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_draw_bank_userno` (`userno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_info`;
CREATE TABLE `user_info` (
  `userno` varchar(255) NOT NULL,
  `agency_no` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `last_login_time` datetime NULL DEFAULT NULL,
  `passwd` varchar(255) NOT NULL,
  `phoneno` varchar(255) DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `register_time` datetime NULL DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `terminal_type` int(10) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`userno`),
  UNIQUE KEY `index_un_user_info` (`username`),
  UNIQUE KEY `idx_info_phoneno` (`phoneno`),
  KEY `idx_user_info` (`register_time`),
  KEY `idx_un_phoneno` (`phoneno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_rebate`;
CREATE TABLE `user_rebate` (
  `id` varchar(255) NOT NULL,
  `userno` varchar(200) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `rebate_type` int(10) NOT NULL,
  `bet_amount` bigint(20) DEFAULT '0',
  `point_location` decimal(19) NOT NULL,
  `is_agent` int(10) NOT NULL DEFAULT '0',
  `agency_no` varchar(200) DEFAULT '0',
  `create_time` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `is_paused` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_user_rebat_u` (`userno`),
  KEY `idx_user_rebat_t` (`rebate_type`,`create_time`),
  KEY `idex_rebate_lottery` (`lottery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_rebate_instant`;
CREATE TABLE `user_rebate_instant` (
  `orderid` varchar(255) NOT NULL,
  `userno` varchar(255) DEFAULT NULL,
  `amount` decimal(19) NOT NULL,
  `point_location` decimal(10,2) NOT NULL,
  `rebate_amount` decimal(19) NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `bet_type` int(10) DEFAULT '0',
  `buy_amount` decimal(19) DEFAULT '0',
  `safe_amount` decimal(19) DEFAULT '0',
  PRIMARY KEY (`orderid`),
  KEY `idx_user_rebate_iu` (`userno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_rebate_statistic`;
CREATE TABLE `user_rebate_statistic` (
  `id` bigint(20) NOT NULL,
  `agency_no` varchar(200) NOT NULL,
  `lottery_type` int(10) NOT NULL,
  `amount` decimal(19) NOT NULL,
  `rebate_amount` decimal(19) NOT NULL,
  `point_location` decimal(19) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `belong_to` varchar(200) NOT NULL,
  `is_agent` int(5) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `statistic_lottery` int(10) NOT NULL,
  `statistic_type` int(5) NOT NULL DEFAULT '0',
  `month_num` bigint(20) DEFAULT '0',
  KEY `idx_pk_rebate_mstatistic` (`id`,`agency_no`,`lottery_type`),
  KEY `idx_rebate_mstatisticb` (`belong_to`),
  KEY `idx_rebate_mstatisticl` (`statistic_lottery`),
  KEY `idx_rebate_static_mn` (`month_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_recharge_give`;
CREATE TABLE `user_recharge_give` (
  `id` varchar(200) NOT NULL,
  `status` int(10) NOT NULL,
  `recharge_give_type` int(2) NOT NULL,
  `recharge_amount` decimal(19) NOT NULL,
  `give_amount` decimal(19) NOT NULL,
  `start_time` datetime NOT NULL,
  `finish_time` datetime NOT NULL,
  `for_scope` int(2) DEFAULT '1',
  `for_limit` int(2) DEFAULT '1',
  `not_draw_perset` decimal(5,2) NOT NULL DEFAULT '0.00',
  `agencyno` varchar(200) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_recharge_give_detail`;
CREATE TABLE `user_recharge_give_detail` (
  `give_id` varchar(200) NOT NULL DEFAULT '',
  `userno` varchar(200) NOT NULL DEFAULT '',
  `recharge_amount` decimal(19) NOT NULL DEFAULT '0',
  `give_amount` decimal(19) NOT NULL DEFAULT '0',
  `create_time` datetime(6) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '3',
  `update_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `transation_id` varchar(200) NOT NULL DEFAULT '0',
  `memo` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`give_id`,`userno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_transaction`;
CREATE TABLE `user_transaction` (
  `id` varchar(255) NOT NULL,
  `amount` decimal(19) NOT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fee` decimal(19) DEFAULT NULL,
  `finish_time` datetime(6) NULL DEFAULT NULL,
  `give_amount` decimal(19) DEFAULT '0',
  `give_id` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT NULL,
  `real_amount` decimal(19) DEFAULT '0',
  `status` int(11) NOT NULL,
  `trade_no` varchar(255) DEFAULT NULL,
  `userno` varchar(255) NOT NULL,
  `card_id` varchar(200) DEFAULT NULL,
  `passwd` varchar(200) DEFAULT NULL,
  `not_draw_perset` decimal(5,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `IDX_TRANSACTION_userno` (`userno`),
  KEY `IDX_TRANSACTION_create` (`create_time`),
  KEY `IDX_TRANSACTION_tur` (`trade_no`),
  KEY `idx_transaction_status` (`status`),
  KEY `idx_transaction_chanel` (`channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `zc_race`;
CREATE TABLE `zc_race` (
  `id` bigint(20) NOT NULL,
  `average_index` varchar(255) DEFAULT NULL,
  `away_team` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `ext` varchar(255) DEFAULT NULL,
  `final_score` varchar(255) DEFAULT NULL,
  `half_score` varchar(255) DEFAULT NULL,
  `home_team` varchar(255) DEFAULT NULL,
  `is_possibledelay` varchar(255) DEFAULT NULL,
  `lottery_type` int(11) DEFAULT NULL,
  `match_date` datetime(6) NULL DEFAULT NULL,
  `match_id` int(11) DEFAULT NULL,
  `match_name` varchar(255) DEFAULT NULL,
  `match_num` int(11) DEFAULT NULL,
  `phase` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `zc_type_phase_match` (`lottery_type`,`phase`,`match_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `zucai_analyze_data`;
CREATE TABLE `zucai_analyze_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `asia_index` text,
  `endtime` datetime DEFAULT NULL,
  `eour_index` text,
  `guest_after_matches` text,
  `guest_pre_matches` text,
  `home_after_matches` text,
  `home_pre_matches` text,
  `lottery_type` int(11) DEFAULT NULL,
  `matchinfo` text,
  `matchno` int(11) DEFAULT NULL,
  `phase` varchar(15) DEFAULT NULL,
  `pre_clash_matches` text,
  `rankings` text,
  `recommend` text,
  `thirdpartid` varchar(15) DEFAULT NULL,
  `arankings` text,
  `erankings` text,
  `hrankings` text,
  `wrankings` text,
  `score_detail` text,
  `score` text,
  `state` int(11) DEFAULT NULL,
  `away_team` varchar(25) DEFAULT NULL,
  `home_team` varchar(25) DEFAULT NULL,
  `league` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_lotterytype` (`lottery_type`),
  KEY `idx_thirdpartId` (`thirdpartid`),
  KEY `idx_phase` (`phase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


