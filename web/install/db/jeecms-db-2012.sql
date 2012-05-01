# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 192.168.0.121    Database: 
# ------------------------------------------------------
# Server version 5.0.77-community-nt

#
# Source for table jc_acquisition
#

DROP TABLE IF EXISTS `jc_acquisition`;
CREATE TABLE `jc_acquisition` (
  `acquisition_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `acq_name` varchar(50) NOT NULL COMMENT '采集名称',
  `start_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '停止时间',
  `status` int(11) NOT NULL default '0' COMMENT '当前状态(0:静止;1:采集;2:暂停)',
  `curr_num` int(11) NOT NULL default '0' COMMENT '当前号码',
  `curr_item` int(11) NOT NULL default '0' COMMENT '当前条数',
  `total_item` int(11) NOT NULL default '0' COMMENT '每页总条数',
  `pause_time` int(11) NOT NULL default '0' COMMENT '暂停时间(毫秒)',
  `page_encoding` varchar(20) NOT NULL default 'GBK' COMMENT '页面编码',
  `plan_list` longtext COMMENT '采集列表',
  `dynamic_addr` varchar(255) default NULL COMMENT '动态地址',
  `dynamic_start` int(11) default NULL COMMENT '页码开始',
  `dynamic_end` int(11) default NULL COMMENT '页码结束',
  `linkset_start` varchar(255) default NULL COMMENT '内容链接区开始',
  `linkset_end` varchar(255) default NULL COMMENT '内容链接区结束',
  `link_start` varchar(255) default NULL COMMENT '内容链接开始',
  `link_end` varchar(255) default NULL COMMENT '内容链接结束',
  `title_start` varchar(255) default NULL COMMENT '标题开始',
  `title_end` varchar(255) default NULL COMMENT '标题结束',
  `keywords_start` varchar(255) default NULL COMMENT '关键字开始',
  `keywords_end` varchar(255) default NULL COMMENT '关键字结束',
  `description_start` varchar(255) default NULL COMMENT '描述开始',
  `description_end` varchar(255) default NULL COMMENT '描述结束',
  `content_start` varchar(255) default NULL COMMENT '内容开始',
  `content_end` varchar(255) default NULL COMMENT '内容结束',
  `pagination_start` varchar(255) default NULL COMMENT '内容分页开始',
  `pagination_end` varchar(255) default NULL COMMENT '内容分页结束',
  `queue` int(11) NOT NULL default '0' COMMENT '队列',
  `repeat_check_type` varchar(20) NOT NULL default 'NONE' COMMENT '重复类型',
  PRIMARY KEY  (`acquisition_id`),
  KEY `fk_jc_acquisition_channel` (`channel_id`),
  KEY `fk_jc_acquisition_contenttype` (`type_id`),
  KEY `fk_jc_acquisition_site` (`site_id`),
  KEY `fk_jc_acquisition_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS采集表';

#
# Dumping data for table jc_acquisition
#

LOCK TABLES `jc_acquisition` WRITE;
/*!40000 ALTER TABLE `jc_acquisition` DISABLE KEYS */;
INSERT INTO `jc_acquisition` VALUES (1,1,37,1,1,'新浪国内新闻','2011-12-17 17:19:52','2011-12-17 17:20:09',0,0,0,0,500,'GBK','http://roll.news.sina.com.cn/news/gnxw/gdxw1/index.shtml','http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_[page].shtml',2,5,'<ul class=\"list_009\">','</ul>','<li><a href=\"','\" target=\"_blank\"','<title>','_新闻中心_新浪网</title>',NULL,NULL,NULL,NULL,' f_id=\'3\' -->','<!-- publish_helper_end -->',NULL,NULL,0,'NONE');
/*!40000 ALTER TABLE `jc_acquisition` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_acquisition_history
#

DROP TABLE IF EXISTS `jc_acquisition_history`;
CREATE TABLE `jc_acquisition_history` (
  `history_id` int(11) NOT NULL auto_increment,
  `channel_url` varchar(255) NOT NULL default '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL default '' COMMENT '内容地址',
  `title` varchar(255) default NULL COMMENT '标题',
  `description` varchar(20) NOT NULL default '' COMMENT '描述',
  `acquisition_id` int(11) default NULL COMMENT '采集源',
  `content_id` int(11) default NULL COMMENT '内容',
  PRIMARY KEY  (`history_id`),
  KEY `fk_acquisition_history_acquisition` (`acquisition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集历史记录表';

#
# Dumping data for table jc_acquisition_history
#

LOCK TABLES `jc_acquisition_history` WRITE;
/*!40000 ALTER TABLE `jc_acquisition_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_acquisition_history` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_acquisition_temp
#

DROP TABLE IF EXISTS `jc_acquisition_temp`;
CREATE TABLE `jc_acquisition_temp` (
  `temp_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `channel_url` varchar(255) NOT NULL default '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL default '' COMMENT '内容地址',
  `title` varchar(255) default NULL COMMENT '标题',
  `percent` int(3) NOT NULL default '0' COMMENT '百分比',
  `description` varchar(20) NOT NULL default '' COMMENT '描述',
  `seq` int(3) NOT NULL default '0' COMMENT '顺序',
  PRIMARY KEY  (`temp_id`),
  KEY `fk_jc_temp_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集进度临时表';

#
# Dumping data for table jc_acquisition_temp
#

LOCK TABLES `jc_acquisition_temp` WRITE;
/*!40000 ALTER TABLE `jc_acquisition_temp` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_acquisition_temp` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_advertising
#

DROP TABLE IF EXISTS `jc_advertising`;
CREATE TABLE `jc_advertising` (
  `advertising_id` int(11) NOT NULL auto_increment,
  `adspace_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '广告名称',
  `category` varchar(50) NOT NULL COMMENT '广告类型',
  `ad_code` longtext COMMENT '广告代码',
  `ad_weight` int(11) NOT NULL default '1' COMMENT '广告权重',
  `display_count` bigint(20) NOT NULL default '0' COMMENT '展现次数',
  `click_count` bigint(20) NOT NULL default '0' COMMENT '点击次数',
  `start_time` date default NULL COMMENT '开始时间',
  `end_time` date default NULL COMMENT '结束时间',
  `is_enabled` char(1) NOT NULL default '1' COMMENT '是否启用',
  PRIMARY KEY  (`advertising_id`),
  KEY `fk_jc_advertising_site` (`site_id`),
  KEY `fk_jc_space_advertising` (`adspace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS广告表';

#
# Dumping data for table jc_advertising
#

LOCK TABLES `jc_advertising` WRITE;
/*!40000 ALTER TABLE `jc_advertising` DISABLE KEYS */;
INSERT INTO `jc_advertising` VALUES (1,1,1,'banner','image',NULL,1,127,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (2,2,1,'通栏广告1','image',NULL,1,123,2,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (3,3,1,'视频广告上','image',NULL,1,0,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (4,4,1,'视频广告下','image',NULL,1,0,0,NULL,NULL,'1');
INSERT INTO `jc_advertising` VALUES (5,5,1,'留言板本周热点广告','image',NULL,1,0,0,NULL,NULL,'1');
/*!40000 ALTER TABLE `jc_advertising` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_advertising_attr
#

DROP TABLE IF EXISTS `jc_advertising_attr`;
CREATE TABLE `jc_advertising_attr` (
  `advertising_id` int(11) NOT NULL,
  `attr_name` varchar(50) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_params_advertising` (`advertising_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS广告属性表';

#
# Dumping data for table jc_advertising_attr
#

LOCK TABLES `jc_advertising_attr` WRITE;
/*!40000 ALTER TABLE `jc_advertising_attr` DISABLE KEYS */;
INSERT INTO `jc_advertising_attr` VALUES (1,'image_title','查看JEECMS官方网站');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_url','/r/cms/www/red/img/banner.gif');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_link','http://www.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_width','735');
INSERT INTO `jc_advertising_attr` VALUES (1,'image_height','70');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_title','JEECMS官方网站');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_url','/r/cms/www/red/img/banner1.jpg');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_link','http://www.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_width','960');
INSERT INTO `jc_advertising_attr` VALUES (2,'image_height','60');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_height','89');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_link','http://');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_url','/u/cms/www/201112/17144805im1p.jpg');
INSERT INTO `jc_advertising_attr` VALUES (3,'image_width','980');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_height','90');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_link','http://');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_url','/u/cms/www/201112/17145028j3bj.jpg');
INSERT INTO `jc_advertising_attr` VALUES (4,'image_width','980');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_height','109');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_link','http://3x.jeecms.com');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_target','_blank');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_url','/u/cms/www/201112/18155751wi1k.gif');
INSERT INTO `jc_advertising_attr` VALUES (5,'image_width','215');
/*!40000 ALTER TABLE `jc_advertising_attr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_advertising_space
#

DROP TABLE IF EXISTS `jc_advertising_space`;
CREATE TABLE `jc_advertising_space` (
  `adspace_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '名称',
  `description` varchar(255) default NULL COMMENT '描述',
  `is_enabled` char(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY  (`adspace_id`),
  KEY `fk_jc_adspace_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS广告版位表';

#
# Dumping data for table jc_advertising_space
#

LOCK TABLES `jc_advertising_space` WRITE;
/*!40000 ALTER TABLE `jc_advertising_space` DISABLE KEYS */;
INSERT INTO `jc_advertising_space` VALUES (1,1,'页头banner','全站页头banner','1');
INSERT INTO `jc_advertising_space` VALUES (2,1,'通栏广告','页面中间通栏广告','1');
INSERT INTO `jc_advertising_space` VALUES (3,1,'视频广告上','','1');
INSERT INTO `jc_advertising_space` VALUES (4,1,'视频广告下','','1');
INSERT INTO `jc_advertising_space` VALUES (5,1,'留言板本周热点广告','','1');
/*!40000 ALTER TABLE `jc_advertising_space` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_channel
#

DROP TABLE IF EXISTS `jc_channel`;
CREATE TABLE `jc_channel` (
  `channel_id` int(11) NOT NULL auto_increment,
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `parent_id` int(11) default NULL COMMENT '父栏目ID',
  `channel_path` varchar(30) default NULL COMMENT '访问路径',
  `lft` int(11) NOT NULL default '1' COMMENT '树左边',
  `rgt` int(11) NOT NULL default '2' COMMENT '树右边',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL default '1' COMMENT '是否有内容',
  `is_display` tinyint(1) NOT NULL default '1' COMMENT '是否显示',
  PRIMARY KEY  (`channel_id`),
  KEY `fk_jc_channel_model` (`model_id`),
  KEY `fk_jc_channel_parent` (`parent_id`),
  KEY `fk_jc_channel_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='CMS栏目表';

#
# Dumping data for table jc_channel
#

LOCK TABLES `jc_channel` WRITE;
/*!40000 ALTER TABLE `jc_channel` DISABLE KEYS */;
INSERT INTO `jc_channel` VALUES (1,1,1,NULL,'news',1,16,1,1,1);
INSERT INTO `jc_channel` VALUES (9,4,1,NULL,'download',17,26,4,1,1);
INSERT INTO `jc_channel` VALUES (10,2,1,NULL,'about',27,28,10,0,0);
INSERT INTO `jc_channel` VALUES (11,1,1,1,'gnxw',2,3,10,1,1);
INSERT INTO `jc_channel` VALUES (12,1,1,1,'world',4,5,10,1,1);
INSERT INTO `jc_channel` VALUES (13,1,1,1,'shehui',6,7,10,1,1);
INSERT INTO `jc_channel` VALUES (14,1,1,1,'review',8,9,10,1,1);
INSERT INTO `jc_channel` VALUES (15,3,1,1,'photo',10,11,10,1,1);
INSERT INTO `jc_channel` VALUES (37,4,1,9,'system',18,19,10,1,1);
INSERT INTO `jc_channel` VALUES (38,4,1,9,'network',20,21,10,1,1);
INSERT INTO `jc_channel` VALUES (39,4,1,9,'media',22,23,10,1,1);
INSERT INTO `jc_channel` VALUES (40,1,1,1,'jjsd',12,13,10,1,1);
INSERT INTO `jc_channel` VALUES (41,1,1,1,'cjbd',14,15,10,1,1);
INSERT INTO `jc_channel` VALUES (42,5,1,NULL,'picture',29,36,2,1,1);
INSERT INTO `jc_channel` VALUES (43,5,1,42,'wyty',30,31,10,1,1);
INSERT INTO `jc_channel` VALUES (44,5,1,42,'mrzx',32,33,10,1,1);
INSERT INTO `jc_channel` VALUES (45,5,1,42,'whxy',34,35,10,1,1);
INSERT INTO `jc_channel` VALUES (46,6,1,NULL,'veido',37,46,3,1,1);
INSERT INTO `jc_channel` VALUES (47,3,1,NULL,'works',47,54,5,1,1);
INSERT INTO `jc_channel` VALUES (48,7,1,NULL,'product',55,58,6,1,1);
INSERT INTO `jc_channel` VALUES (49,6,1,46,'star',38,39,10,1,1);
INSERT INTO `jc_channel` VALUES (50,6,1,46,'yceg',40,41,10,1,1);
INSERT INTO `jc_channel` VALUES (51,6,1,46,'jdgx',42,43,10,1,1);
INSERT INTO `jc_channel` VALUES (52,6,1,46,'hhqw',44,45,10,1,1);
INSERT INTO `jc_channel` VALUES (53,3,1,47,'fjfg',48,53,10,1,1);
INSERT INTO `jc_channel` VALUES (54,3,1,53,'tiankong',49,50,10,1,1);
INSERT INTO `jc_channel` VALUES (55,3,1,53,'dong',51,52,10,1,1);
INSERT INTO `jc_channel` VALUES (56,7,1,48,'phone',56,57,10,1,1);
INSERT INTO `jc_channel` VALUES (57,4,1,9,'syzs',24,25,10,1,1);
/*!40000 ALTER TABLE `jc_channel` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_channel_attr
#

DROP TABLE IF EXISTS `jc_channel_attr`;
CREATE TABLE `jc_channel_attr` (
  `channel_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目扩展属性表';

#
# Dumping data for table jc_channel_attr
#

LOCK TABLES `jc_channel_attr` WRITE;
/*!40000 ALTER TABLE `jc_channel_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_channel_attr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_channel_ext
#

DROP TABLE IF EXISTS `jc_channel_ext`;
CREATE TABLE `jc_channel_ext` (
  `channel_id` int(11) NOT NULL,
  `channel_name` varchar(100) NOT NULL COMMENT '名称',
  `final_step` tinyint(4) default '2' COMMENT '终审级别',
  `after_check` tinyint(4) default NULL COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_static_channel` char(1) NOT NULL default '0' COMMENT '是否栏目静态化',
  `is_static_content` char(1) NOT NULL default '0' COMMENT '是否内容静态化',
  `is_access_by_dir` char(1) NOT NULL default '1' COMMENT '是否使用目录访问',
  `is_list_child` char(1) NOT NULL default '0' COMMENT '是否使用子栏目列表',
  `page_size` int(11) NOT NULL default '20' COMMENT '每页多少条记录',
  `channel_rule` varchar(150) default NULL COMMENT '栏目页生成规则',
  `content_rule` varchar(150) default NULL COMMENT '内容页生成规则',
  `link` varchar(255) default NULL COMMENT '外部链接',
  `tpl_channel` varchar(100) default NULL COMMENT '栏目页模板',
  `tpl_content` varchar(100) default NULL COMMENT '内容页模板',
  `title_img` varchar(100) default NULL COMMENT '缩略图',
  `content_img` varchar(100) default NULL COMMENT '内容图',
  `has_title_img` tinyint(1) NOT NULL default '0' COMMENT '内容是否有缩略图',
  `has_content_img` tinyint(1) NOT NULL default '0' COMMENT '内容是否有内容图',
  `title_img_width` int(11) NOT NULL default '139' COMMENT '内容标题图宽度',
  `title_img_height` int(11) NOT NULL default '139' COMMENT '内容标题图高度',
  `content_img_width` int(11) NOT NULL default '310' COMMENT '内容内容图宽度',
  `content_img_height` int(11) NOT NULL default '310' COMMENT '内容内容图高度',
  `comment_control` int(11) NOT NULL default '0' COMMENT '评论(0:匿名;1:会员;2:关闭)',
  `allow_updown` tinyint(1) NOT NULL default '1' COMMENT '顶踩(true:开放;false:关闭)',
  `is_blank` tinyint(1) NOT NULL default '1' COMMENT '是否新窗口打开',
  `title` varchar(255) default NULL COMMENT 'TITLE',
  `keywords` varchar(255) default NULL COMMENT 'KEYWORDS',
  `description` varchar(255) default NULL COMMENT 'DESCRIPTION',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目内容表';

#
# Dumping data for table jc_channel_ext
#

LOCK TABLES `jc_channel_ext` WRITE;
/*!40000 ALTER TABLE `jc_channel_ext` DISABLE KEYS */;
INSERT INTO `jc_channel_ext` VALUES (1,'新闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'新闻资讯','新闻资讯','新闻资讯');
INSERT INTO `jc_channel_ext` VALUES (9,'下载',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'下载中心','下载中心','下载中心');
INSERT INTO `jc_channel_ext` VALUES (10,'关于我们',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'关于我们','关于我们','关于我们');
INSERT INTO `jc_channel_ext` VALUES (11,'国内新闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html','/WEB-INF/template/www/red/content/新闻内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,'国内新闻','国内新闻','国内新闻');
INSERT INTO `jc_channel_ext` VALUES (12,'国际新闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'国际新闻','国际新闻','国际新闻');
INSERT INTO `jc_channel_ext` VALUES (13,'社会热点',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'社会热点','社会热点','社会热点');
INSERT INTO `jc_channel_ext` VALUES (14,'时事评论',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html',NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,'时事评论','时事评论','时事评论');
INSERT INTO `jc_channel_ext` VALUES (15,'图片新闻',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/作品栏目_子栏目.html','/WEB-INF/template/www/red/content/作品内容.html',NULL,NULL,1,0,139,139,310,310,0,1,0,'图片新闻','图片新闻','图片新闻');
INSERT INTO `jc_channel_ext` VALUES (37,'系统软件',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/下载栏目_二级.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'系统软件','系统软件','系统软件');
INSERT INTO `jc_channel_ext` VALUES (38,'网络游戏',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/下载栏目_二级.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'网络游戏','网络工具','网络游戏');
INSERT INTO `jc_channel_ext` VALUES (39,'媒体工具',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/下载栏目_二级.html',NULL,NULL,NULL,1,1,48,48,139,98,0,1,0,'媒体工具','媒体工具','媒体工具');
INSERT INTO `jc_channel_ext` VALUES (40,'基金视点',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html','/WEB-INF/template/www/red/content/新闻内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (41,'财经报道',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/新闻栏目_子栏目.html','/WEB-INF/template/www/red/content/新闻内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (42,'图库',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/图库栏目.html','/WEB-INF/template/www/red/content/图库内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (43,'文娱体育',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/图库栏目_子栏目.html','/WEB-INF/template/www/red/content/图库内容.html',NULL,NULL,1,0,67,50,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (44,'美容资讯',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/图库栏目_子栏目.html','/WEB-INF/template/www/red/content/图库内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (45,'文化 校园',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/图库栏目_子栏目.html','/WEB-INF/template/www/red/content/图库内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (46,'视频',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (47,'作品',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/作品栏目.html','/WEB-INF/template/www/red/content/作品内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (48,'产品',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (49,'明星糗镜头',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/视频栏目_子栏目.html','/WEB-INF/template/www/red/content/视频内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (50,'有才恶搞',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/视频栏目_子栏目.html','/WEB-INF/template/www/red/content/视频内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (51,'经典搞笑专辑',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/视频栏目_子栏目.html','/WEB-INF/template/www/red/content/视频内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (52,'哈哈趣闻',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/视频栏目_子栏目.html','/WEB-INF/template/www/red/content/视频内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (53,'风景风光类',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/作品栏目_子栏目.html','/WEB-INF/template/www/red/content/作品内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (54,'蓝天白云绿地',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/作品栏目_子栏目.html','/WEB-INF/template/www/red/content/作品内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (55,'冬雪系列',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/作品栏目_子栏目.html','/WEB-INF/template/www/red/content/作品内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (56,'手机',NULL,NULL,'0','0','0','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/产品栏目.html','/WEB-INF/template/www/red/content/产品内容.html',NULL,NULL,0,0,139,139,310,310,0,1,0,NULL,NULL,NULL);
INSERT INTO `jc_channel_ext` VALUES (57,'实用助手',NULL,NULL,'0','0','1','0',20,NULL,NULL,NULL,'/WEB-INF/template/www/red/channel/下载栏目.html','/WEB-INF/template/www/red/content/下载内容.html',NULL,NULL,1,1,48,48,180,120,0,1,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `jc_channel_ext` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_channel_txt
#

DROP TABLE IF EXISTS `jc_channel_txt`;
CREATE TABLE `jc_channel_txt` (
  `channel_id` int(11) NOT NULL,
  `txt` longtext COMMENT '栏目内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目文本表';

#
# Dumping data for table jc_channel_txt
#

LOCK TABLES `jc_channel_txt` WRITE;
/*!40000 ALTER TABLE `jc_channel_txt` DISABLE KEYS */;
INSERT INTO `jc_channel_txt` VALUES (10,'<p><font size=\"2\">&nbsp;&nbsp;&nbsp; JEECMS是JavaEE版网站管理系统（Java Enterprise Edition Content Manage System）的简称。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;Java凭借其强大、稳定、安全、高效等多方面的优势，一直是企业级应用的首选。在国外基于JavaEE技术的CMS已经发展的相当成熟，但授权费昂贵，一般需几十万一套；而国内在这方面一直比较薄弱，至今没有一款基于JavaEE技术的开源免费CMS产品。这次我们本着&quot;大气开源，诚信图强&quot;的原则将我们开发的这套JEECMS系统源码完全公布，希望能为国内JavaEE技术的发展尽自己的一份力量。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;JEECMS使用目前java主流技术架构：hibernate3+spring3+freemarker。AJAX使用jquery和json实现。视图层并没有使用传统的 JSP技术，而是使用更为专业、灵活、高效freemarker。 数据库使用MYSQL，并可支持orcale、DB2、SQLServer等主流数据库。应用服务器使用tomcat，并支持其他weblogic、 websphere等应用服务器。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;JEECMS并不是一个只追求技术之先进，而不考虑用户实际使用的象牙塔CMS。系统的设计宗旨就是从用户的需求出发，提供最便利、合理的使用方式，懂html就能建站，从设计上满足搜索引擎优化，最小性能消耗满足小网站要求、可扩展群集满足大网站需要。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;很多人觉得java、jsp难掌握，技术门槛高。jeecms具有强大的模板机制。所有前台页面均由模板生成，通过在线编辑模板轻松调整页面显示。模板内容不涉及任何java和jsp技术，只需掌握html语法和jeecms标签即可完成动态网页制作。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;强大、灵活的标签。提供两种风格的标签，一种风格的标签封装了大量互联网上常见的显示样式，通过调整参数就可实现文章列表、图文混排、图文滚动、跑马灯、焦点图等效果。这种标签的优势在于页面制作简单、效率高，对js、css、html不够精通和希望快速建站的用户非常适用。并且各种效果的内容不使用js生成，对搜索引擎非常友好。另一种风格的标签只负责读取数据，由用户自己控制显示内容和显示方式，想到什么就能做到什么，对于技术能力高和追求个性化的用户，可谓如鱼得水。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;采用完全生成静态页面技术，加快页面访问速度，提升搜索引擎友好性；采用扁平的、可自定义的路径结构。对于有特别需求者，可自定义页面后缀，如.php,.asp,.aspx等。</font></p>\r\n<p><font size=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;站群设计，对于大型的网站，往往需要通过次级域名建立子站群，各个子站后台管理权限可以分离，程序和附件分离，前台用户实现单点登录，大规模网站轻松建设。</font></p>',NULL,NULL,NULL);
/*!40000 ALTER TABLE `jc_channel_txt` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_channel_user
#

DROP TABLE IF EXISTS `jc_channel_user`;
CREATE TABLE `jc_channel_user` (
  `channel_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`user_id`),
  KEY `fk_jc_channel_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目用户关联表';

#
# Dumping data for table jc_channel_user
#

LOCK TABLES `jc_channel_user` WRITE;
/*!40000 ALTER TABLE `jc_channel_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_channel_user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_chnl_group_contri
#

DROP TABLE IF EXISTS `jc_chnl_group_contri`;
CREATE TABLE `jc_chnl_group_contri` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_c` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目投稿会员组关联表';

#
# Dumping data for table jc_chnl_group_contri
#

LOCK TABLES `jc_chnl_group_contri` WRITE;
/*!40000 ALTER TABLE `jc_chnl_group_contri` DISABLE KEYS */;
INSERT INTO `jc_chnl_group_contri` VALUES (1,1);
INSERT INTO `jc_chnl_group_contri` VALUES (11,1);
INSERT INTO `jc_chnl_group_contri` VALUES (12,1);
INSERT INTO `jc_chnl_group_contri` VALUES (13,1);
INSERT INTO `jc_chnl_group_contri` VALUES (14,1);
INSERT INTO `jc_chnl_group_contri` VALUES (15,1);
INSERT INTO `jc_chnl_group_contri` VALUES (40,1);
INSERT INTO `jc_chnl_group_contri` VALUES (41,1);
INSERT INTO `jc_chnl_group_contri` VALUES (42,1);
INSERT INTO `jc_chnl_group_contri` VALUES (42,2);
INSERT INTO `jc_chnl_group_contri` VALUES (43,1);
INSERT INTO `jc_chnl_group_contri` VALUES (43,2);
INSERT INTO `jc_chnl_group_contri` VALUES (44,1);
INSERT INTO `jc_chnl_group_contri` VALUES (44,2);
INSERT INTO `jc_chnl_group_contri` VALUES (45,1);
INSERT INTO `jc_chnl_group_contri` VALUES (45,2);
INSERT INTO `jc_chnl_group_contri` VALUES (46,1);
INSERT INTO `jc_chnl_group_contri` VALUES (46,2);
INSERT INTO `jc_chnl_group_contri` VALUES (47,1);
INSERT INTO `jc_chnl_group_contri` VALUES (47,2);
INSERT INTO `jc_chnl_group_contri` VALUES (48,1);
INSERT INTO `jc_chnl_group_contri` VALUES (48,2);
INSERT INTO `jc_chnl_group_contri` VALUES (49,1);
INSERT INTO `jc_chnl_group_contri` VALUES (49,2);
INSERT INTO `jc_chnl_group_contri` VALUES (50,1);
INSERT INTO `jc_chnl_group_contri` VALUES (50,2);
INSERT INTO `jc_chnl_group_contri` VALUES (51,1);
INSERT INTO `jc_chnl_group_contri` VALUES (51,2);
INSERT INTO `jc_chnl_group_contri` VALUES (52,1);
INSERT INTO `jc_chnl_group_contri` VALUES (52,2);
INSERT INTO `jc_chnl_group_contri` VALUES (53,1);
INSERT INTO `jc_chnl_group_contri` VALUES (53,2);
INSERT INTO `jc_chnl_group_contri` VALUES (54,1);
INSERT INTO `jc_chnl_group_contri` VALUES (54,2);
INSERT INTO `jc_chnl_group_contri` VALUES (55,1);
INSERT INTO `jc_chnl_group_contri` VALUES (55,2);
INSERT INTO `jc_chnl_group_contri` VALUES (56,1);
INSERT INTO `jc_chnl_group_contri` VALUES (56,2);
/*!40000 ALTER TABLE `jc_chnl_group_contri` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_chnl_group_view
#

DROP TABLE IF EXISTS `jc_chnl_group_view`;
CREATE TABLE `jc_chnl_group_view` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_v` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目浏览会员组关联表';

#
# Dumping data for table jc_chnl_group_view
#

LOCK TABLES `jc_chnl_group_view` WRITE;
/*!40000 ALTER TABLE `jc_chnl_group_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_chnl_group_view` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_comment
#

DROP TABLE IF EXISTS `jc_comment`;
CREATE TABLE `jc_comment` (
  `comment_id` int(11) NOT NULL auto_increment,
  `comment_user_id` int(11) default NULL COMMENT '评论用户ID',
  `reply_user_id` int(11) default NULL COMMENT '回复用户ID',
  `content_id` int(11) NOT NULL COMMENT '内容ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `reply_time` datetime default NULL COMMENT '回复时间',
  `ups` smallint(6) NOT NULL default '0' COMMENT '支持数',
  `downs` smallint(6) NOT NULL default '0' COMMENT '反对数',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  `is_checked` tinyint(1) NOT NULL default '0' COMMENT '是否审核',
  PRIMARY KEY  (`comment_id`),
  KEY `fk_jc_comment_content` (`content_id`),
  KEY `fk_jc_comment_reply` (`reply_user_id`),
  KEY `fk_jc_comment_site` (`site_id`),
  KEY `fk_jc_comment_user` (`comment_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='CMS评论表';

#
# Dumping data for table jc_comment
#

LOCK TABLES `jc_comment` WRITE;
/*!40000 ALTER TABLE `jc_comment` DISABLE KEYS */;
INSERT INTO `jc_comment` VALUES (1,1,NULL,240,1,'2011-12-19 11:13:06',NULL,0,0,1,1);
INSERT INTO `jc_comment` VALUES (2,1,NULL,49,1,'2011-12-19 11:13:45',NULL,0,0,1,1);
INSERT INTO `jc_comment` VALUES (3,1,NULL,250,1,'2011-12-19 14:19:38',NULL,0,0,1,1);
INSERT INTO `jc_comment` VALUES (4,1,NULL,226,1,'2011-12-19 14:20:54',NULL,0,0,1,1);
INSERT INTO `jc_comment` VALUES (5,NULL,NULL,311,1,'2011-12-19 16:35:27',NULL,0,0,0,0);
INSERT INTO `jc_comment` VALUES (6,1,NULL,295,1,'2011-12-19 17:19:23',NULL,0,0,0,0);
/*!40000 ALTER TABLE `jc_comment` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_comment_ext
#

DROP TABLE IF EXISTS `jc_comment_ext`;
CREATE TABLE `jc_comment_ext` (
  `comment_id` int(11) NOT NULL,
  `ip` varchar(50) default NULL COMMENT 'IP地址',
  `text` longtext COMMENT '评论内容',
  `reply` longtext COMMENT '回复内容',
  KEY `fk_jc_ext_comment` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论扩展表';

#
# Dumping data for table jc_comment_ext
#

LOCK TABLES `jc_comment_ext` WRITE;
/*!40000 ALTER TABLE `jc_comment_ext` DISABLE KEYS */;
INSERT INTO `jc_comment_ext` VALUES (1,'192.168.0.1','以当局共释放1027名在押巴勒斯坦人员，以换取被哈马斯俘获的以军士兵沙利特。','');
INSERT INTO `jc_comment_ext` VALUES (2,'192.168.0.1','观察了一会，见没什么动静才回家，但直到零点以后才睡着，“总是担心床会摇晃！”','');
INSERT INTO `jc_comment_ext` VALUES (3,'192.168.0.200','哇，好可怜。。。','');
INSERT INTO `jc_comment_ext` VALUES (4,'192.168.0.200','真的吗？\r\n','');
INSERT INTO `jc_comment_ext` VALUES (5,'192.168.0.115','求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧',NULL);
INSERT INTO `jc_comment_ext` VALUES (6,'192.168.0.115','直板触控造型设计',NULL);
/*!40000 ALTER TABLE `jc_comment_ext` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_config
#

DROP TABLE IF EXISTS `jc_config`;
CREATE TABLE `jc_config` (
  `config_id` int(11) NOT NULL,
  `context_path` varchar(20) default '/JeeCms' COMMENT '部署路径',
  `servlet_point` varchar(20) default NULL COMMENT 'Servlet挂载点',
  `port` int(11) default NULL COMMENT '端口',
  `db_file_uri` varchar(50) NOT NULL default '/dbfile.svl?n=' COMMENT '数据库附件访问地址',
  `is_upload_to_db` tinyint(1) NOT NULL default '0' COMMENT '上传附件至数据库',
  `def_img` varchar(255) NOT NULL default '/JeeCms/r/cms/www/default/no_picture.gif' COMMENT '图片不存在时默认图片',
  `login_url` varchar(255) NOT NULL default '/login.jspx' COMMENT '登录地址',
  `process_url` varchar(255) default NULL COMMENT '登录后处理地址',
  `mark_on` tinyint(1) NOT NULL default '1' COMMENT '开启图片水印',
  `mark_width` int(11) NOT NULL default '120' COMMENT '图片最小宽度',
  `mark_height` int(11) NOT NULL default '120' COMMENT '图片最小高度',
  `mark_image` varchar(100) default '/r/cms/www/watermark.png' COMMENT '图片水印',
  `mark_content` varchar(100) NOT NULL default 'www.jeecms.com' COMMENT '文字水印内容',
  `mark_size` int(11) NOT NULL default '20' COMMENT '文字水印大小',
  `mark_color` varchar(10) NOT NULL default '#FF0000' COMMENT '文字水印颜色',
  `mark_alpha` int(11) NOT NULL default '50' COMMENT '水印透明度（0-100）',
  `mark_position` int(11) NOT NULL default '1' COMMENT '水印位置(0-5)',
  `mark_offset_x` int(11) NOT NULL default '0' COMMENT 'x坐标偏移量',
  `mark_offset_y` int(11) NOT NULL default '0' COMMENT 'y坐标偏移量',
  `count_clear_time` date NOT NULL COMMENT '计数器清除时间',
  `count_copy_time` datetime NOT NULL COMMENT '计数器拷贝时间',
  `download_code` varchar(32) NOT NULL default 'jeecms' COMMENT '下载防盗链md5混淆码',
  `download_time` int(11) NOT NULL default '12' COMMENT '下载有效时间（小时）',
  `email_host` varchar(50) default NULL COMMENT '邮件发送服务器',
  `email_encoding` varchar(20) default NULL COMMENT '邮件发送编码',
  `email_username` varchar(100) default NULL COMMENT '邮箱用户名',
  `email_password` varchar(100) default NULL COMMENT '邮箱密码',
  `email_personal` varchar(100) default NULL COMMENT '邮箱发件人',
  PRIMARY KEY  (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置表';

#
# Dumping data for table jc_config
#

LOCK TABLES `jc_config` WRITE;
/*!40000 ALTER TABLE `jc_config` DISABLE KEYS */;
INSERT INTO `jc_config` VALUES (1,NULL,NULL,80,'/dbfile.svl?n=',0,'/r/cms/www/no_picture.gif','/login.jspx',NULL,1,120,120,'/r/cms/www/watermark.png','www.jeecms.com',20,'#FF0000',50,1,0,0,'2011-12-19','2011-12-19 17:30:44','jeecms',12,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `jc_config` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_config_attr
#

DROP TABLE IF EXISTS `jc_config_attr`;
CREATE TABLE `jc_config_attr` (
  `config_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_config` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置属性表';

#
# Dumping data for table jc_config_attr
#

LOCK TABLES `jc_config_attr` WRITE;
/*!40000 ALTER TABLE `jc_config_attr` DISABLE KEYS */;
INSERT INTO `jc_config_attr` VALUES (1,'password_min_len','3');
INSERT INTO `jc_config_attr` VALUES (1,'username_reserved','');
INSERT INTO `jc_config_attr` VALUES (1,'register_on','true');
INSERT INTO `jc_config_attr` VALUES (1,'member_on','true');
INSERT INTO `jc_config_attr` VALUES (1,'username_min_len','3');
INSERT INTO `jc_config_attr` VALUES (1,'version','jeecms-3.1.1-final');
/*!40000 ALTER TABLE `jc_config_attr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content
#

DROP TABLE IF EXISTS `jc_content`;
CREATE TABLE `jc_content` (
  `content_id` int(11) NOT NULL auto_increment,
  `channel_id` int(11) NOT NULL COMMENT '栏目ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `type_id` int(11) NOT NULL COMMENT '属性ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `sort_date` datetime NOT NULL COMMENT '排序日期',
  `top_level` tinyint(4) NOT NULL default '0' COMMENT '固顶级别',
  `has_title_img` tinyint(1) NOT NULL default '0' COMMENT '是否有标题图',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  `status` tinyint(4) NOT NULL default '2' COMMENT '状态(0:草稿;1:审核中;2:审核通过;3:回收站)',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问数',
  `comments_day` smallint(6) NOT NULL default '0' COMMENT '日评论数',
  `downloads_day` smallint(6) NOT NULL default '0' COMMENT '日下载数',
  `ups_day` smallint(6) NOT NULL default '0' COMMENT '日顶数',
  PRIMARY KEY  (`content_id`),
  KEY `fk_jc_content_site` (`site_id`),
  KEY `fk_jc_content_type` (`type_id`),
  KEY `fk_jc_content_user` (`user_id`),
  KEY `fk_jc_contentchannel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8 COMMENT='CMS内容表';

#
# Dumping data for table jc_content
#

LOCK TABLES `jc_content` WRITE;
/*!40000 ALTER TABLE `jc_content` DISABLE KEYS */;
INSERT INTO `jc_content` VALUES (34,11,1,4,1,'2011-01-03 21:38:30',0,0,1,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (35,11,1,1,1,'2011-01-03 21:38:31',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (36,11,1,1,1,'2011-01-03 21:38:32',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (37,11,1,1,1,'2011-01-03 21:38:33',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (38,11,1,1,1,'2011-01-03 21:38:35',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (39,11,1,4,1,'2011-01-03 21:38:36',0,0,0,2,8,0,0,0);
INSERT INTO `jc_content` VALUES (40,11,1,1,1,'2011-01-03 21:38:38',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (41,11,1,1,1,'2011-01-03 21:38:39',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (42,11,1,1,1,'2011-01-03 21:38:40',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (43,11,1,1,1,'2011-01-03 21:38:42',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (44,11,1,1,1,'2011-01-03 21:38:45',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (45,11,1,1,1,'2011-01-03 21:38:48',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (46,11,1,1,1,'2011-01-03 21:38:49',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (47,11,1,1,1,'2011-01-03 21:38:51',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (48,11,1,1,1,'2011-01-03 21:38:53',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (49,11,1,1,1,'2011-01-03 21:38:54',0,0,1,2,2,1,0,0);
INSERT INTO `jc_content` VALUES (54,12,1,1,1,'2011-01-04 11:02:52',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (55,12,1,1,1,'2011-01-04 11:04:16',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (56,12,1,1,1,'2011-01-04 11:06:23',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (57,12,1,2,1,'2011-01-04 11:07:34',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (58,13,1,1,1,'2011-01-04 11:10:12',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (59,13,1,1,1,'2011-01-04 11:12:15',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (60,13,1,1,1,'2011-01-04 11:13:25',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (61,13,1,1,1,'2011-01-04 11:14:40',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (62,13,1,1,1,'2011-01-04 11:16:01',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (63,14,1,1,1,'2011-01-04 11:17:04',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (64,14,1,1,1,'2011-01-04 11:18:39',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (65,14,1,1,1,'2011-01-04 11:19:33',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (66,14,1,1,1,'2011-01-04 11:20:15',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (112,41,1,1,1,'2011-01-04 13:43:07',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (115,41,1,1,1,'2011-01-04 13:44:49',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (116,41,1,1,1,'2011-01-04 13:46:10',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (117,41,1,1,1,'2011-01-04 13:48:04',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (172,39,1,1,1,'2011-01-04 14:34:23',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (173,39,1,1,1,'2011-01-04 14:44:02',0,1,0,2,1,0,1,0);
INSERT INTO `jc_content` VALUES (174,37,1,1,1,'2011-01-04 14:44:59',0,1,0,2,2,0,1,0);
INSERT INTO `jc_content` VALUES (176,15,1,2,1,'2011-01-04 14:55:11',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (177,15,1,2,1,'2011-01-04 14:56:38',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (178,15,1,2,1,'2011-01-04 14:57:29',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (179,15,1,2,1,'2011-01-04 14:58:20',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (180,15,1,2,1,'2011-01-04 14:59:06',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (181,15,1,2,1,'2011-01-04 14:59:48',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (182,15,1,2,1,'2011-01-04 15:00:32',0,1,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (183,15,1,2,1,'2011-01-04 15:01:43',0,1,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (184,15,1,2,1,'2011-01-04 15:03:31',0,1,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (185,15,1,2,1,'2011-01-04 15:04:31',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (186,15,1,2,1,'2011-01-04 15:05:34',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (187,15,1,2,1,'2011-01-04 15:06:52',0,1,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (188,13,1,3,1,'2011-01-04 15:13:44',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (189,43,1,3,1,'2011-12-17 14:06:55',0,1,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (190,43,1,3,1,'2011-12-17 14:12:57',0,1,1,2,6,0,0,0);
INSERT INTO `jc_content` VALUES (191,43,1,2,1,'2011-12-17 14:16:07',0,1,1,2,16,0,0,0);
INSERT INTO `jc_content` VALUES (192,51,1,2,1,'2011-12-17 15:45:32',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (193,51,1,2,1,'2011-12-17 16:11:27',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (194,51,1,2,1,'2011-12-17 16:11:41',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (195,51,1,2,1,'2011-12-17 16:12:30',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (196,51,1,2,1,'2011-12-17 16:12:44',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (197,51,1,2,1,'2011-12-17 16:12:55',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (198,51,1,2,1,'2011-12-17 16:13:26',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (209,49,1,2,1,'2011-12-17 16:56:26',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (210,52,1,2,1,'2011-12-17 16:59:57',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (211,52,1,2,1,'2011-12-17 17:00:14',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (212,50,1,2,1,'2011-12-17 17:01:18',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (213,50,1,2,1,'2011-12-17 17:01:50',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (214,50,1,2,1,'2011-12-17 17:02:03',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (215,50,1,2,1,'2011-12-17 17:02:20',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (216,52,1,2,1,'2011-12-17 17:05:34',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (217,52,1,2,1,'2011-12-17 17:07:05',0,0,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (218,51,1,2,1,'2011-12-17 17:08:21',0,0,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (219,52,1,2,1,'2011-12-17 17:09:29',0,0,1,2,17,0,0,0);
INSERT INTO `jc_content` VALUES (220,49,1,2,1,'2011-12-17 17:10:57',0,0,1,2,23,0,0,0);
INSERT INTO `jc_content` VALUES (223,44,1,2,1,'2011-12-17 17:21:16',0,0,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (224,44,1,2,1,'2011-12-17 17:25:18',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (225,43,1,3,1,'2011-12-17 17:27:30',0,1,1,2,8,0,0,0);
INSERT INTO `jc_content` VALUES (226,40,1,1,1,'2011-12-19 08:30:48',0,0,1,2,4,1,0,0);
INSERT INTO `jc_content` VALUES (227,40,1,1,1,'2011-12-19 08:32:58',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (228,40,1,2,1,'2011-12-19 08:33:33',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (229,40,1,1,1,'2011-12-19 08:34:05',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (230,40,1,1,1,'2011-12-19 08:34:55',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (231,40,1,1,1,'2011-12-19 08:35:21',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (232,40,1,1,1,'2011-12-19 08:35:45',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (233,40,1,1,1,'2011-12-19 08:36:16',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (234,12,1,4,1,'2011-12-19 08:40:56',0,0,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (235,12,1,4,1,'2011-12-19 08:42:45',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (236,12,1,4,1,'2011-12-19 08:44:27',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (237,12,1,4,1,'2011-12-19 08:45:02',0,0,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (238,12,1,1,1,'2011-12-19 08:45:39',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (239,12,1,1,1,'2011-12-19 08:46:10',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (240,12,1,1,1,'2011-12-19 08:46:32',0,0,0,2,2,1,0,0);
INSERT INTO `jc_content` VALUES (241,14,1,1,1,'2011-12-19 08:51:40',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (242,14,1,1,1,'2011-12-19 08:52:05',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (243,14,1,2,1,'2011-12-19 08:53:01',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (244,14,1,1,1,'2011-12-19 08:53:27',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (245,14,1,1,1,'2011-12-19 08:53:48',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (246,13,1,1,1,'2011-12-19 08:54:35',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (247,13,1,1,1,'2011-12-19 08:55:04',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (248,13,1,1,1,'2011-12-19 08:55:51',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (249,11,1,2,1,'2011-12-19 09:02:32',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (250,13,1,2,1,'2011-12-19 09:10:17',0,0,0,2,22,1,0,0);
INSERT INTO `jc_content` VALUES (251,41,1,1,1,'2011-12-19 09:19:32',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (252,41,1,1,1,'2011-12-19 09:20:23',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (253,41,1,1,1,'2011-12-19 09:20:58',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (254,41,1,2,1,'2011-12-19 09:22:08',0,0,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (255,41,1,1,1,'2011-12-19 09:23:07',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (256,41,1,1,1,'2011-12-19 09:23:31',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (257,41,1,1,1,'2011-12-19 09:23:58',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (258,41,1,1,1,'2011-12-19 09:25:32',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (259,41,1,1,1,'2011-12-19 09:26:22',0,0,0,2,11,0,0,0);
INSERT INTO `jc_content` VALUES (260,56,1,2,1,'2011-12-19 09:28:30',0,0,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (261,56,1,2,1,'2011-12-19 09:33:03',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (262,56,1,2,1,'2011-12-19 09:34:36',0,0,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (263,43,1,2,1,'2011-12-19 09:36:34',0,0,0,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (264,56,1,2,1,'2011-12-19 09:37:46',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (265,56,1,2,1,'2011-12-19 09:38:53',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (266,56,1,2,1,'2011-12-19 09:41:26',0,0,1,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (267,43,1,2,1,'2011-12-19 09:41:53',0,0,0,2,6,0,0,0);
INSERT INTO `jc_content` VALUES (268,56,1,2,1,'2011-12-19 09:42:42',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (269,56,1,2,1,'2011-12-19 09:43:49',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (270,56,1,2,1,'2011-12-19 09:45:10',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (271,43,1,2,1,'2011-12-19 09:45:20',0,0,0,2,6,0,0,0);
INSERT INTO `jc_content` VALUES (272,56,1,2,1,'2011-12-19 09:46:28',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (273,56,1,2,1,'2011-12-19 09:47:13',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (274,56,1,2,1,'2011-12-19 09:48:33',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (275,43,1,2,1,'2011-12-19 09:49:54',0,1,0,2,7,0,0,0);
INSERT INTO `jc_content` VALUES (276,56,1,2,1,'2011-12-19 09:49:55',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (277,56,1,2,1,'2011-12-19 09:51:04',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (278,56,1,2,1,'2011-12-19 09:52:07',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (279,56,1,2,1,'2011-12-19 09:53:35',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (280,44,1,2,1,'2011-12-19 09:54:30',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (281,56,1,2,1,'2011-12-19 09:54:45',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (282,56,1,2,1,'2011-12-19 09:55:35',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (283,56,1,2,1,'2011-12-19 09:56:14',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (284,56,1,2,1,'2011-12-19 09:57:13',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (285,56,1,2,1,'2011-12-19 09:58:26',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (286,43,1,2,1,'2011-12-19 09:58:29',0,0,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (287,56,1,2,1,'2011-12-19 09:59:00',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (288,56,1,2,1,'2011-12-19 09:59:42',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (289,56,1,2,1,'2011-12-19 10:00:48',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (290,56,1,2,1,'2011-12-19 10:01:35',0,0,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (291,56,1,2,1,'2011-12-19 10:02:44',0,0,0,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (292,56,1,2,1,'2011-12-19 10:04:30',0,0,0,2,6,0,0,0);
INSERT INTO `jc_content` VALUES (293,44,1,2,1,'2011-12-19 10:08:21',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (294,44,1,2,1,'2011-12-19 10:12:01',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (295,56,1,2,1,'2011-12-19 10:13:08',0,0,0,2,16,1,0,0);
INSERT INTO `jc_content` VALUES (296,44,1,2,1,'2011-12-19 10:14:08',0,0,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (297,45,1,2,1,'2011-12-19 10:19:53',0,0,0,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (298,55,1,2,1,'2011-12-19 10:22:29',0,0,0,2,10,0,0,0);
INSERT INTO `jc_content` VALUES (299,45,1,2,1,'2011-12-19 10:23:16',0,0,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (300,45,1,2,1,'2011-12-19 10:27:20',0,0,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (301,45,1,2,1,'2011-12-19 10:30:37',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (302,45,1,2,1,'2011-12-19 10:34:05',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (303,45,1,2,1,'2011-12-19 10:36:59',0,0,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (304,52,1,3,1,'2011-12-19 10:48:59',0,0,1,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (305,52,1,2,1,'2011-12-19 10:58:55',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (306,52,1,2,1,'2011-12-19 10:59:44',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (307,52,1,2,1,'2011-12-19 11:00:37',0,0,1,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (308,52,1,2,1,'2011-12-19 11:01:22',0,0,1,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (309,52,1,2,1,'2011-12-19 11:01:51',0,0,1,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (310,52,1,2,1,'2011-12-19 11:03:01',0,0,1,2,7,0,0,0);
INSERT INTO `jc_content` VALUES (311,52,1,2,1,'2011-12-19 11:03:41',0,0,1,2,9,1,0,0);
INSERT INTO `jc_content` VALUES (312,49,1,2,1,'2011-12-19 11:06:15',0,0,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (313,49,1,2,1,'2011-12-19 11:07:01',0,0,0,2,0,0,0,0);
INSERT INTO `jc_content` VALUES (314,49,1,2,1,'2011-12-19 11:07:39',0,0,0,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (315,37,1,1,1,'2011-12-19 11:08:38',0,1,0,2,50,0,5,0);
INSERT INTO `jc_content` VALUES (316,54,1,2,1,'2011-12-19 11:18:32',0,0,0,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (317,54,1,2,1,'2011-12-19 11:22:25',0,0,1,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (318,54,1,2,1,'2011-12-19 11:25:12',0,0,1,2,4,0,0,0);
INSERT INTO `jc_content` VALUES (319,54,1,2,1,'2011-12-19 11:27:14',0,0,1,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (320,37,1,1,1,'2011-12-19 11:27:15',0,1,0,2,21,0,2,0);
INSERT INTO `jc_content` VALUES (321,54,1,2,1,'2011-12-19 11:31:57',0,0,1,2,2,0,0,0);
INSERT INTO `jc_content` VALUES (322,54,1,2,1,'2011-12-19 11:33:51',0,0,1,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (323,54,1,2,1,'2011-12-19 11:35:47',0,0,1,2,15,0,0,0);
INSERT INTO `jc_content` VALUES (324,54,1,2,1,'2011-12-19 11:39:35',0,0,1,2,10,0,0,0);
INSERT INTO `jc_content` VALUES (325,54,1,2,1,'2011-12-19 11:41:35',0,0,1,2,10,0,0,0);
INSERT INTO `jc_content` VALUES (326,38,1,1,1,'2011-12-19 11:42:28',0,1,0,2,13,0,2,0);
INSERT INTO `jc_content` VALUES (327,55,1,2,1,'2011-12-19 11:43:44',0,0,0,2,8,0,0,0);
INSERT INTO `jc_content` VALUES (328,38,1,1,1,'2011-12-19 12:02:07',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (329,38,1,1,1,'2011-12-19 13:17:53',0,1,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (330,38,1,1,1,'2011-12-19 13:45:17',0,1,0,2,7,0,1,0);
INSERT INTO `jc_content` VALUES (331,39,1,1,1,'2011-12-19 13:53:49',0,1,0,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (332,39,1,1,1,'2011-12-19 13:57:03',0,1,0,2,10,0,0,0);
INSERT INTO `jc_content` VALUES (333,37,1,1,1,'2011-12-19 14:10:29',0,1,0,2,2,0,1,0);
INSERT INTO `jc_content` VALUES (334,57,1,1,1,'2011-12-19 14:18:12',0,1,0,2,1,0,1,0);
INSERT INTO `jc_content` VALUES (335,57,1,1,1,'2011-12-19 14:21:32',0,1,0,2,3,0,0,0);
INSERT INTO `jc_content` VALUES (336,57,1,1,1,'2011-12-19 14:25:26',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (337,57,1,1,1,'2011-12-19 14:30:04',0,1,0,2,1,0,0,0);
INSERT INTO `jc_content` VALUES (338,43,1,3,1,'2011-12-19 15:43:16',0,1,1,2,5,0,0,0);
INSERT INTO `jc_content` VALUES (339,43,1,3,1,'2011-12-19 15:45:54',0,1,1,2,6,0,0,0);
INSERT INTO `jc_content` VALUES (340,11,1,1,1,'2011-12-19 18:00:00',0,0,0,1,0,0,0,0);
/*!40000 ALTER TABLE `jc_content` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_attachment
#

DROP TABLE IF EXISTS `jc_content_attachment`;
CREATE TABLE `jc_content_attachment` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `attachment_path` varchar(255) NOT NULL COMMENT '附件路径',
  `attachment_name` varchar(100) NOT NULL COMMENT '附件名称',
  `filename` varchar(100) default NULL COMMENT '文件名',
  `download_count` int(11) NOT NULL default '0' COMMENT '下载次数',
  KEY `fk_jc_attachment_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容附件表';

#
# Dumping data for table jc_content_attachment
#

LOCK TABLES `jc_content_attachment` WRITE;
/*!40000 ALTER TABLE `jc_content_attachment` DISABLE KEYS */;
INSERT INTO `jc_content_attachment` VALUES (172,0,'/u/cms/www/201112/19115513v9k3.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (173,0,'/u/cms/www/201112/19115459jnds.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (174,0,'/u/cms/www/201112/19115540rghk.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (315,0,'/u/cms/www/201112/19111015xsud.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (320,0,'/u/cms/www/201112/19112925s625.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (326,0,'/u/cms/www/201112/19114320y7x2.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (328,0,'/u/cms/www/201112/19120206ddre.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (329,0,'/u/cms/www/201112/19132320u46d.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (330,0,'/u/cms/www/201112/19134542a8qu.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (331,0,'/u/cms/www/201112/19135345g1s7.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (332,0,'/u/cms/www/201112/19140010z9z1.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (333,0,'/u/cms/www/201112/19141042cfu8.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (334,0,'/u/cms/www/201112/191418286eoi.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (335,0,'/u/cms/www/201112/19142201umby.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (336,0,'/u/cms/www/201112/191425405rka.zip','111.zip','111.zip',0);
INSERT INTO `jc_content_attachment` VALUES (337,0,'/u/cms/www/201112/19143017qxs3.zip','111.zip','111.zip',0);
/*!40000 ALTER TABLE `jc_content_attachment` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_attr
#

DROP TABLE IF EXISTS `jc_content_attr`;
CREATE TABLE `jc_content_attr` (
  `content_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展属性表';

#
# Dumping data for table jc_content_attr
#

LOCK TABLES `jc_content_attr` WRITE;
/*!40000 ALTER TABLE `jc_content_attr` DISABLE KEYS */;
INSERT INTO `jc_content_attr` VALUES (172,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (172,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (172,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (172,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (173,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (173,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (173,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (173,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (174,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (174,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (174,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (174,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (260,'price','5350');
INSERT INTO `jc_content_attr` VALUES (260,'marketprice','5450');
INSERT INTO `jc_content_attr` VALUES (261,'price','5100');
INSERT INTO `jc_content_attr` VALUES (261,'marketprice','5300');
INSERT INTO `jc_content_attr` VALUES (262,'price','4980');
INSERT INTO `jc_content_attr` VALUES (262,'marketprice','5150');
INSERT INTO `jc_content_attr` VALUES (264,'price','4600');
INSERT INTO `jc_content_attr` VALUES (264,'marketprice','4700');
INSERT INTO `jc_content_attr` VALUES (265,'price','7450');
INSERT INTO `jc_content_attr` VALUES (265,'marketprice','7850');
INSERT INTO `jc_content_attr` VALUES (266,'price','219200');
INSERT INTO `jc_content_attr` VALUES (266,'marketprice','232200');
INSERT INTO `jc_content_attr` VALUES (268,'price','7398');
INSERT INTO `jc_content_attr` VALUES (268,'marketprice','7602');
INSERT INTO `jc_content_attr` VALUES (269,'price','7000');
INSERT INTO `jc_content_attr` VALUES (269,'marketprice','7200');
INSERT INTO `jc_content_attr` VALUES (270,'price','5000');
INSERT INTO `jc_content_attr` VALUES (270,'marketprice','5300');
INSERT INTO `jc_content_attr` VALUES (272,'price','2780');
INSERT INTO `jc_content_attr` VALUES (272,'marketprice','2880');
INSERT INTO `jc_content_attr` VALUES (273,'price','3080');
INSERT INTO `jc_content_attr` VALUES (273,'marketprice','3230');
INSERT INTO `jc_content_attr` VALUES (274,'price','2099');
INSERT INTO `jc_content_attr` VALUES (274,'marketprice','2399');
INSERT INTO `jc_content_attr` VALUES (276,'price','4680');
INSERT INTO `jc_content_attr` VALUES (276,'marketprice','4880');
INSERT INTO `jc_content_attr` VALUES (277,'price','2278');
INSERT INTO `jc_content_attr` VALUES (277,'marketprice','2500');
INSERT INTO `jc_content_attr` VALUES (278,'price','2588');
INSERT INTO `jc_content_attr` VALUES (278,'marketprice','2788');
INSERT INTO `jc_content_attr` VALUES (279,'price','1199');
INSERT INTO `jc_content_attr` VALUES (279,'marketprice','1399');
INSERT INTO `jc_content_attr` VALUES (281,'price','2999');
INSERT INTO `jc_content_attr` VALUES (281,'marketprice','3199');
INSERT INTO `jc_content_attr` VALUES (282,'price','1499');
INSERT INTO `jc_content_attr` VALUES (282,'marketprice','1699');
INSERT INTO `jc_content_attr` VALUES (283,'price','2699');
INSERT INTO `jc_content_attr` VALUES (283,'marketprice','2800');
INSERT INTO `jc_content_attr` VALUES (284,'price','1898');
INSERT INTO `jc_content_attr` VALUES (284,'marketprice','2000');
INSERT INTO `jc_content_attr` VALUES (285,'price','3600');
INSERT INTO `jc_content_attr` VALUES (285,'marketprice','3800');
INSERT INTO `jc_content_attr` VALUES (287,'price','2700');
INSERT INTO `jc_content_attr` VALUES (287,'marketprice','2750');
INSERT INTO `jc_content_attr` VALUES (288,'price','1260');
INSERT INTO `jc_content_attr` VALUES (288,'marketprice','1360');
INSERT INTO `jc_content_attr` VALUES (289,'price','2680');
INSERT INTO `jc_content_attr` VALUES (289,'marketprice','2770');
INSERT INTO `jc_content_attr` VALUES (290,'price','2039');
INSERT INTO `jc_content_attr` VALUES (290,'marketprice','2200');
INSERT INTO `jc_content_attr` VALUES (291,'price','2999');
INSERT INTO `jc_content_attr` VALUES (291,'marketprice','3199');
INSERT INTO `jc_content_attr` VALUES (292,'price','1999');
INSERT INTO `jc_content_attr` VALUES (292,'marketprice','2100');
INSERT INTO `jc_content_attr` VALUES (295,'price','2940');
INSERT INTO `jc_content_attr` VALUES (295,'marketprice','3100');
INSERT INTO `jc_content_attr` VALUES (315,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (315,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (315,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (315,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (320,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (320,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (320,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (320,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (326,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (326,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (326,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (326,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (328,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (328,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (328,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (328,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (329,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (329,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (329,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (329,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (330,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (330,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (330,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (330,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (331,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (331,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (331,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (331,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (332,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (332,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (332,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (332,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (333,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (333,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (333,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (333,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (334,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (334,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (334,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (334,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (335,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (335,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (335,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (335,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (336,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (336,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (336,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (336,'softType','国产软件');
INSERT INTO `jc_content_attr` VALUES (337,'demoUrl','http://');
INSERT INTO `jc_content_attr` VALUES (337,'warrant','免费版');
INSERT INTO `jc_content_attr` VALUES (337,'relatedLink','http://');
INSERT INTO `jc_content_attr` VALUES (337,'softType','国产软件');
/*!40000 ALTER TABLE `jc_content_attr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_channel
#

DROP TABLE IF EXISTS `jc_content_channel`;
CREATE TABLE `jc_content_channel` (
  `channel_id` int(11) NOT NULL,
  `content_id` int(11) NOT NULL,
  PRIMARY KEY  (`channel_id`,`content_id`),
  KEY `fk_jc_channel_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容栏目关联表';

#
# Dumping data for table jc_content_channel
#

LOCK TABLES `jc_content_channel` WRITE;
/*!40000 ALTER TABLE `jc_content_channel` DISABLE KEYS */;
INSERT INTO `jc_content_channel` VALUES (11,34);
INSERT INTO `jc_content_channel` VALUES (11,35);
INSERT INTO `jc_content_channel` VALUES (11,36);
INSERT INTO `jc_content_channel` VALUES (11,37);
INSERT INTO `jc_content_channel` VALUES (11,38);
INSERT INTO `jc_content_channel` VALUES (11,39);
INSERT INTO `jc_content_channel` VALUES (11,40);
INSERT INTO `jc_content_channel` VALUES (11,41);
INSERT INTO `jc_content_channel` VALUES (11,42);
INSERT INTO `jc_content_channel` VALUES (11,43);
INSERT INTO `jc_content_channel` VALUES (11,44);
INSERT INTO `jc_content_channel` VALUES (11,45);
INSERT INTO `jc_content_channel` VALUES (11,46);
INSERT INTO `jc_content_channel` VALUES (11,47);
INSERT INTO `jc_content_channel` VALUES (11,48);
INSERT INTO `jc_content_channel` VALUES (11,49);
INSERT INTO `jc_content_channel` VALUES (11,249);
INSERT INTO `jc_content_channel` VALUES (11,340);
INSERT INTO `jc_content_channel` VALUES (12,54);
INSERT INTO `jc_content_channel` VALUES (12,55);
INSERT INTO `jc_content_channel` VALUES (12,56);
INSERT INTO `jc_content_channel` VALUES (12,57);
INSERT INTO `jc_content_channel` VALUES (12,234);
INSERT INTO `jc_content_channel` VALUES (12,235);
INSERT INTO `jc_content_channel` VALUES (12,236);
INSERT INTO `jc_content_channel` VALUES (12,237);
INSERT INTO `jc_content_channel` VALUES (12,238);
INSERT INTO `jc_content_channel` VALUES (12,239);
INSERT INTO `jc_content_channel` VALUES (12,240);
INSERT INTO `jc_content_channel` VALUES (13,58);
INSERT INTO `jc_content_channel` VALUES (13,59);
INSERT INTO `jc_content_channel` VALUES (13,60);
INSERT INTO `jc_content_channel` VALUES (13,61);
INSERT INTO `jc_content_channel` VALUES (13,62);
INSERT INTO `jc_content_channel` VALUES (13,188);
INSERT INTO `jc_content_channel` VALUES (13,246);
INSERT INTO `jc_content_channel` VALUES (13,247);
INSERT INTO `jc_content_channel` VALUES (13,248);
INSERT INTO `jc_content_channel` VALUES (13,250);
INSERT INTO `jc_content_channel` VALUES (14,63);
INSERT INTO `jc_content_channel` VALUES (14,64);
INSERT INTO `jc_content_channel` VALUES (14,65);
INSERT INTO `jc_content_channel` VALUES (14,66);
INSERT INTO `jc_content_channel` VALUES (14,241);
INSERT INTO `jc_content_channel` VALUES (14,242);
INSERT INTO `jc_content_channel` VALUES (14,243);
INSERT INTO `jc_content_channel` VALUES (14,244);
INSERT INTO `jc_content_channel` VALUES (14,245);
INSERT INTO `jc_content_channel` VALUES (15,176);
INSERT INTO `jc_content_channel` VALUES (15,177);
INSERT INTO `jc_content_channel` VALUES (15,178);
INSERT INTO `jc_content_channel` VALUES (15,179);
INSERT INTO `jc_content_channel` VALUES (15,180);
INSERT INTO `jc_content_channel` VALUES (15,181);
INSERT INTO `jc_content_channel` VALUES (15,182);
INSERT INTO `jc_content_channel` VALUES (15,183);
INSERT INTO `jc_content_channel` VALUES (15,184);
INSERT INTO `jc_content_channel` VALUES (15,185);
INSERT INTO `jc_content_channel` VALUES (15,186);
INSERT INTO `jc_content_channel` VALUES (15,187);
INSERT INTO `jc_content_channel` VALUES (37,174);
INSERT INTO `jc_content_channel` VALUES (37,315);
INSERT INTO `jc_content_channel` VALUES (37,320);
INSERT INTO `jc_content_channel` VALUES (37,333);
INSERT INTO `jc_content_channel` VALUES (38,326);
INSERT INTO `jc_content_channel` VALUES (38,328);
INSERT INTO `jc_content_channel` VALUES (38,329);
INSERT INTO `jc_content_channel` VALUES (38,330);
INSERT INTO `jc_content_channel` VALUES (39,172);
INSERT INTO `jc_content_channel` VALUES (39,173);
INSERT INTO `jc_content_channel` VALUES (39,331);
INSERT INTO `jc_content_channel` VALUES (39,332);
INSERT INTO `jc_content_channel` VALUES (40,226);
INSERT INTO `jc_content_channel` VALUES (40,227);
INSERT INTO `jc_content_channel` VALUES (40,228);
INSERT INTO `jc_content_channel` VALUES (40,229);
INSERT INTO `jc_content_channel` VALUES (40,230);
INSERT INTO `jc_content_channel` VALUES (40,231);
INSERT INTO `jc_content_channel` VALUES (40,232);
INSERT INTO `jc_content_channel` VALUES (40,233);
INSERT INTO `jc_content_channel` VALUES (41,112);
INSERT INTO `jc_content_channel` VALUES (41,115);
INSERT INTO `jc_content_channel` VALUES (41,116);
INSERT INTO `jc_content_channel` VALUES (41,117);
INSERT INTO `jc_content_channel` VALUES (41,251);
INSERT INTO `jc_content_channel` VALUES (41,252);
INSERT INTO `jc_content_channel` VALUES (41,253);
INSERT INTO `jc_content_channel` VALUES (41,254);
INSERT INTO `jc_content_channel` VALUES (41,255);
INSERT INTO `jc_content_channel` VALUES (41,256);
INSERT INTO `jc_content_channel` VALUES (41,257);
INSERT INTO `jc_content_channel` VALUES (41,258);
INSERT INTO `jc_content_channel` VALUES (41,259);
INSERT INTO `jc_content_channel` VALUES (43,189);
INSERT INTO `jc_content_channel` VALUES (43,190);
INSERT INTO `jc_content_channel` VALUES (43,191);
INSERT INTO `jc_content_channel` VALUES (43,225);
INSERT INTO `jc_content_channel` VALUES (43,263);
INSERT INTO `jc_content_channel` VALUES (43,267);
INSERT INTO `jc_content_channel` VALUES (43,271);
INSERT INTO `jc_content_channel` VALUES (43,275);
INSERT INTO `jc_content_channel` VALUES (43,286);
INSERT INTO `jc_content_channel` VALUES (43,338);
INSERT INTO `jc_content_channel` VALUES (43,339);
INSERT INTO `jc_content_channel` VALUES (44,223);
INSERT INTO `jc_content_channel` VALUES (44,224);
INSERT INTO `jc_content_channel` VALUES (44,280);
INSERT INTO `jc_content_channel` VALUES (44,293);
INSERT INTO `jc_content_channel` VALUES (44,294);
INSERT INTO `jc_content_channel` VALUES (44,296);
INSERT INTO `jc_content_channel` VALUES (45,297);
INSERT INTO `jc_content_channel` VALUES (45,299);
INSERT INTO `jc_content_channel` VALUES (45,300);
INSERT INTO `jc_content_channel` VALUES (45,301);
INSERT INTO `jc_content_channel` VALUES (45,302);
INSERT INTO `jc_content_channel` VALUES (45,303);
INSERT INTO `jc_content_channel` VALUES (49,209);
INSERT INTO `jc_content_channel` VALUES (49,220);
INSERT INTO `jc_content_channel` VALUES (49,312);
INSERT INTO `jc_content_channel` VALUES (49,313);
INSERT INTO `jc_content_channel` VALUES (49,314);
INSERT INTO `jc_content_channel` VALUES (50,212);
INSERT INTO `jc_content_channel` VALUES (50,213);
INSERT INTO `jc_content_channel` VALUES (50,214);
INSERT INTO `jc_content_channel` VALUES (50,215);
INSERT INTO `jc_content_channel` VALUES (51,192);
INSERT INTO `jc_content_channel` VALUES (51,193);
INSERT INTO `jc_content_channel` VALUES (51,194);
INSERT INTO `jc_content_channel` VALUES (51,195);
INSERT INTO `jc_content_channel` VALUES (51,196);
INSERT INTO `jc_content_channel` VALUES (51,197);
INSERT INTO `jc_content_channel` VALUES (51,198);
INSERT INTO `jc_content_channel` VALUES (51,218);
INSERT INTO `jc_content_channel` VALUES (52,210);
INSERT INTO `jc_content_channel` VALUES (52,211);
INSERT INTO `jc_content_channel` VALUES (52,216);
INSERT INTO `jc_content_channel` VALUES (52,217);
INSERT INTO `jc_content_channel` VALUES (52,219);
INSERT INTO `jc_content_channel` VALUES (52,304);
INSERT INTO `jc_content_channel` VALUES (52,305);
INSERT INTO `jc_content_channel` VALUES (52,306);
INSERT INTO `jc_content_channel` VALUES (52,307);
INSERT INTO `jc_content_channel` VALUES (52,308);
INSERT INTO `jc_content_channel` VALUES (52,309);
INSERT INTO `jc_content_channel` VALUES (52,310);
INSERT INTO `jc_content_channel` VALUES (52,311);
INSERT INTO `jc_content_channel` VALUES (54,316);
INSERT INTO `jc_content_channel` VALUES (54,317);
INSERT INTO `jc_content_channel` VALUES (54,318);
INSERT INTO `jc_content_channel` VALUES (54,319);
INSERT INTO `jc_content_channel` VALUES (54,321);
INSERT INTO `jc_content_channel` VALUES (54,322);
INSERT INTO `jc_content_channel` VALUES (54,323);
INSERT INTO `jc_content_channel` VALUES (54,324);
INSERT INTO `jc_content_channel` VALUES (54,325);
INSERT INTO `jc_content_channel` VALUES (55,298);
INSERT INTO `jc_content_channel` VALUES (55,327);
INSERT INTO `jc_content_channel` VALUES (56,260);
INSERT INTO `jc_content_channel` VALUES (56,261);
INSERT INTO `jc_content_channel` VALUES (56,262);
INSERT INTO `jc_content_channel` VALUES (56,264);
INSERT INTO `jc_content_channel` VALUES (56,265);
INSERT INTO `jc_content_channel` VALUES (56,266);
INSERT INTO `jc_content_channel` VALUES (56,268);
INSERT INTO `jc_content_channel` VALUES (56,269);
INSERT INTO `jc_content_channel` VALUES (56,270);
INSERT INTO `jc_content_channel` VALUES (56,272);
INSERT INTO `jc_content_channel` VALUES (56,273);
INSERT INTO `jc_content_channel` VALUES (56,274);
INSERT INTO `jc_content_channel` VALUES (56,276);
INSERT INTO `jc_content_channel` VALUES (56,277);
INSERT INTO `jc_content_channel` VALUES (56,278);
INSERT INTO `jc_content_channel` VALUES (56,279);
INSERT INTO `jc_content_channel` VALUES (56,281);
INSERT INTO `jc_content_channel` VALUES (56,282);
INSERT INTO `jc_content_channel` VALUES (56,283);
INSERT INTO `jc_content_channel` VALUES (56,284);
INSERT INTO `jc_content_channel` VALUES (56,285);
INSERT INTO `jc_content_channel` VALUES (56,287);
INSERT INTO `jc_content_channel` VALUES (56,288);
INSERT INTO `jc_content_channel` VALUES (56,289);
INSERT INTO `jc_content_channel` VALUES (56,290);
INSERT INTO `jc_content_channel` VALUES (56,291);
INSERT INTO `jc_content_channel` VALUES (56,292);
INSERT INTO `jc_content_channel` VALUES (56,295);
INSERT INTO `jc_content_channel` VALUES (57,334);
INSERT INTO `jc_content_channel` VALUES (57,335);
INSERT INTO `jc_content_channel` VALUES (57,336);
INSERT INTO `jc_content_channel` VALUES (57,337);
/*!40000 ALTER TABLE `jc_content_channel` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_check
#

DROP TABLE IF EXISTS `jc_content_check`;
CREATE TABLE `jc_content_check` (
  `content_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL default '0' COMMENT '审核步数',
  `check_opinion` varchar(255) default NULL COMMENT '审核意见',
  `is_rejected` tinyint(1) NOT NULL default '0' COMMENT '是否退回',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容审核信息表';

#
# Dumping data for table jc_content_check
#

LOCK TABLES `jc_content_check` WRITE;
/*!40000 ALTER TABLE `jc_content_check` DISABLE KEYS */;
INSERT INTO `jc_content_check` VALUES (34,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (35,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (36,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (37,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (38,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (39,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (40,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (41,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (42,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (43,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (44,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (45,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (46,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (47,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (48,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (49,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (54,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (55,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (56,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (57,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (58,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (59,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (60,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (61,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (62,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (63,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (64,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (65,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (66,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (112,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (115,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (116,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (117,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (172,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (173,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (174,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (176,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (177,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (178,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (179,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (180,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (181,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (182,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (183,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (184,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (185,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (186,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (187,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (188,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (189,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (190,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (191,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (192,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (193,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (194,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (195,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (196,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (197,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (198,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (209,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (210,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (211,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (212,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (213,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (214,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (215,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (216,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (217,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (218,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (219,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (220,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (223,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (224,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (225,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (226,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (227,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (228,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (229,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (230,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (231,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (232,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (233,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (234,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (235,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (236,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (237,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (238,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (239,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (240,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (241,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (242,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (243,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (244,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (245,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (246,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (247,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (248,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (249,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (250,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (251,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (252,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (253,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (254,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (255,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (256,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (257,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (258,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (259,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (260,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (261,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (262,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (263,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (264,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (265,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (266,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (267,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (268,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (269,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (270,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (271,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (272,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (273,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (274,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (275,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (276,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (277,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (278,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (279,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (280,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (281,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (282,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (283,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (284,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (285,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (286,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (287,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (288,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (289,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (290,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (291,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (292,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (293,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (294,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (295,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (296,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (297,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (298,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (299,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (300,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (301,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (302,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (303,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (304,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (305,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (306,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (307,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (308,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (309,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (310,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (311,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (312,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (313,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (314,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (315,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (316,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (317,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (318,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (319,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (320,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (321,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (322,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (323,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (324,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (325,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (326,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (327,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (328,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (329,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (330,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (331,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (332,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (333,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (334,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (335,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (336,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (337,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (338,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (339,2,NULL,0);
INSERT INTO `jc_content_check` VALUES (340,0,NULL,0);
/*!40000 ALTER TABLE `jc_content_check` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_count
#

DROP TABLE IF EXISTS `jc_content_count`;
CREATE TABLE `jc_content_count` (
  `content_id` int(11) NOT NULL,
  `views` int(11) NOT NULL default '0' COMMENT '总访问数',
  `views_month` int(11) NOT NULL default '0' COMMENT '月访问数',
  `views_week` int(11) NOT NULL default '0' COMMENT '周访问数',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问数',
  `comments` int(11) NOT NULL default '0' COMMENT '总评论数',
  `comments_month` int(11) NOT NULL default '0' COMMENT '月评论数',
  `comments_week` smallint(6) NOT NULL default '0' COMMENT '周评论数',
  `comments_day` smallint(6) NOT NULL default '0' COMMENT '日评论数',
  `downloads` int(11) NOT NULL default '0' COMMENT '总下载数',
  `downloads_month` int(11) NOT NULL default '0' COMMENT '月下载数',
  `downloads_week` smallint(6) NOT NULL default '0' COMMENT '周下载数',
  `downloads_day` smallint(6) NOT NULL default '0' COMMENT '日下载数',
  `ups` int(11) NOT NULL default '0' COMMENT '总顶数',
  `ups_month` int(11) NOT NULL default '0' COMMENT '月顶数',
  `ups_week` smallint(6) NOT NULL default '0' COMMENT '周顶数',
  `ups_day` smallint(6) NOT NULL default '0' COMMENT '日顶数',
  `downs` int(11) NOT NULL default '0' COMMENT '总踩数',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容计数表';

#
# Dumping data for table jc_content_count
#

LOCK TABLES `jc_content_count` WRITE;
/*!40000 ALTER TABLE `jc_content_count` DISABLE KEYS */;
INSERT INTO `jc_content_count` VALUES (34,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (35,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (36,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (38,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (39,12,12,12,8,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (40,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (41,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (42,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (43,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (45,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (46,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (47,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (49,3,3,3,2,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (54,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (55,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (56,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (57,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (58,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (59,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (60,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (61,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (62,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (63,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (64,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (65,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (66,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (112,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (115,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (116,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (117,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (172,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (173,4,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (174,2,2,2,2,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (176,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (177,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (178,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (179,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (180,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (181,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (182,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (183,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (184,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (185,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (186,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (187,8,8,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (188,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (189,3,3,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (190,11,11,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (191,26,26,19,16,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (192,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (193,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (194,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (195,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (196,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (197,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (198,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (209,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (210,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (211,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (212,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (213,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (214,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (215,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (216,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (217,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (218,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (219,17,17,17,17,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (220,31,31,31,23,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (223,7,7,7,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (224,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (225,14,14,14,10,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (226,4,4,4,4,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (227,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (228,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (229,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (230,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (232,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (233,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (234,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (235,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (236,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (237,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (238,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (239,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (240,2,2,2,2,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (241,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (242,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (243,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (244,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (245,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (246,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (247,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (248,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (249,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (250,22,22,22,22,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (251,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (252,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (253,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (254,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (255,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (256,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (257,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (258,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0);
INSERT INTO `jc_content_count` VALUES (259,11,11,11,11,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (260,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (261,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (262,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (263,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (264,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (265,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (266,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (267,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (268,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (269,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (270,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (271,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (272,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (273,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (274,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (275,7,7,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (276,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (277,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (279,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (280,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (281,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (282,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (283,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (284,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (285,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (286,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (287,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (288,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (289,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (290,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (291,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (292,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (293,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (294,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (295,16,16,16,16,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (296,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (297,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (298,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (299,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (300,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (301,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (302,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (303,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (304,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (305,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (306,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (307,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (308,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (309,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (310,7,7,7,7,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (311,9,9,9,9,1,1,1,1,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (312,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (313,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (314,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (315,50,50,50,50,0,0,0,0,5,5,1,5,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (316,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (317,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (318,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (319,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (320,21,21,21,21,0,0,0,0,2,2,1,2,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (321,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (322,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (323,15,15,15,15,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (324,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (325,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (326,13,13,13,13,0,0,0,0,2,2,1,2,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (327,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (328,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (329,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (330,7,7,7,7,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (331,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (332,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (333,2,2,2,2,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (334,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (335,3,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (336,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (337,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (338,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (339,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0);
INSERT INTO `jc_content_count` VALUES (340,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `jc_content_count` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_ext
#

DROP TABLE IF EXISTS `jc_content_ext`;
CREATE TABLE `jc_content_ext` (
  `content_id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL COMMENT '标题',
  `short_title` varchar(150) default NULL COMMENT '简短标题',
  `author` varchar(100) default NULL COMMENT '作者',
  `origin` varchar(100) default NULL COMMENT '来源',
  `origin_url` varchar(255) default NULL COMMENT '来源链接',
  `description` varchar(255) default NULL COMMENT '描述',
  `release_date` datetime NOT NULL COMMENT '发布日期',
  `media_path` varchar(255) default NULL COMMENT '媒体路径',
  `media_type` varchar(20) default NULL COMMENT '媒体类型',
  `title_color` varchar(10) default NULL COMMENT '标题颜色',
  `is_bold` tinyint(1) NOT NULL default '0' COMMENT '是否加粗',
  `title_img` varchar(100) default NULL COMMENT '标题图片',
  `content_img` varchar(100) default NULL COMMENT '内容图片',
  `type_img` varchar(100) default NULL COMMENT '类型图片',
  `link` varchar(255) default NULL COMMENT '外部链接',
  `tpl_content` varchar(100) default NULL COMMENT '指定模板',
  `need_regenerate` tinyint(1) NOT NULL default '1' COMMENT '需要重新生成静态页',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展表';

#
# Dumping data for table jc_content_ext
#

LOCK TABLES `jc_content_ext` WRITE;
/*!40000 ALTER TABLE `jc_content_ext` DISABLE KEYS */;
INSERT INTO `jc_content_ext` VALUES (34,'我国有望后年发射火星探测器',NULL,NULL,NULL,NULL,'据中国空间技术研究院有关专家介绍，2013年，我国有望利用长征三号乙运载火箭发射自主火星探测器。我国航天工业部门已先期启动了基于探月一二期技术的自主火星探测器研究和方案设计工作，目前正在积极开展技术攻关。据悉，我国自主火星探测器的科学载荷重量达100公斤以上，科学探测能力将大大提高。','2011-01-03 21:38:30',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (35,'首都机场30个赴欧美航班因欧美暴雪延误',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:31',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (36,'江苏建湖县3天发生31起小地震',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:32',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (37,'贵州湖南公路路面大面积结冰',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:33',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (38,'北京长安街西延长线今年开建',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:35',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (39,'婚姻登记信息5年内全国联网 北京等3地今年试点',NULL,NULL,NULL,NULL,'民政部日前召开婚姻登记规范化视频会议。会上透露，截至目前，全国多个省市婚登机关已实现婚姻登记网上预约、在线查询、婚姻档案信息检索等功能。婚姻登记信息实现省级联网的省份已由2005年的2个增加到现在的23个，省级联网率达到了74%。“十二五”期间，婚姻登记信息将实现全国联网。','2011-01-03 21:38:36',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (40,'贵州全省仅一条高速部分开放',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:38',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (41,'福州厦门等多个城市决定继续执行楼市限购令',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:39',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (42,'重庆40余人自驾游因道路结冰受困山间 ',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:40',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (43,'重庆2010年挖出涉黑保护伞47人',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:42',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (44,'北京城管在官网接受市民评价',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:45',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (45,'北京低保人员将须审核财产情况',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:48',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (46,'重庆荣昌将在押期间表现纳入量刑情节',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:49',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (47,'1月份将上演日偏食等四大天象',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:51',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (48,'湖南未来一周仍维持低温雨雪天气',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:53',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (49,'江苏建湖连续发生31起地震 部分居民夜里跑出门',NULL,NULL,NULL,NULL,NULL,'2011-01-03 21:38:54',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (54,'外媒称欧盟明年或取消对华武器禁运','外媒称欧盟明年或取消对华武器禁运',NULL,NULL,NULL,'法国主流媒体援引接近欧盟外交高层的话说，欧盟对华武器禁运有望于2011年取消。据悉，欧洲国家对此问题的分歧正在缩小，但是一些东欧国家对此不认同，还有国家要求为解禁提出附加条件。','2011-01-04 11:02:52',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (55,'日本否认欲建立“日韩同盟”令韩媒体更正(图)','日本否认欲建立“日韩同盟”',NULL,NULL,NULL,'3日，东北亚地区因为韩朝交火和美韩日密集军事演习引发的紧张气氛尚未消散之际，韩国媒体又爆出“大新闻”，称日本提议建立日韩军事同盟。消息一出来，日本政府赶紧辟谣，称日方并无此意，仅是希望日韩加强在安全保障领域的合作。','2011-01-04 11:04:16',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (56,'俄罗斯开始大规模裁减公务员 3年将削减两成','俄罗斯开始大规模裁减公务员',NULL,NULL,NULL,'国际在线报道（驻俄罗斯记者盛晶晶）：3号，俄罗斯总统梅德韦杰夫正式签署一项关于优化联邦国家机关工作人员数量的命令，称联邦政府将在今后3年内削减20%的公务员，并严格控制政府机构的人员增长。这是自苏联解体以来，俄罗斯联邦政府第一次大规模裁减公务员。\r\n','2011-01-04 11:06:23',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (57,'美国朝鲜问题特使今将抵达韩国开始东亚三国之行','美国朝鲜问题特使今将抵达韩国开始东亚三国之行',NULL,NULL,NULL,'新年伊始，朝韩半岛局势继续牵动国际社会的目光。今天（4号）下午，美国朝鲜问题特使斯蒂芬·博斯沃思将抵达韩国，就朝鲜半岛局势下一步如何行动展开磋商，这也是他此次中日韩三国访问的第一站。据报道，博斯沃思此次访问的议题可能会涉及六方会谈。那么，对于博思沃斯的访问，韩国国内是怎样一个态度？相关的举动能否在新的一年打开朝鲜半岛局势的新局面？ \r\n','2011-01-04 11:07:34',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/17095324wp2z.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (58,'著名作家史铁生昨日凌晨突发脑溢血逝世','著名作家史铁生昨日凌晨突发脑溢血逝世','新华网','网易','http://www.163.com/','12月31日凌晨3时，59岁的作家史铁生因脑溢血在北京去世。根据史铁生生前遗愿，他的脊椎、大脑将捐给医学研究；他的肝脏将捐给有需要的患者。史铁生年轻时双腿瘫痪，后来患肾病并发展到尿毒症，一直靠透析维持生命。其著名散文《我与地坛》影响最大，感动无数读者。','2011-01-04 11:10:12',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (59,'政府网站政风满意率调查公示被曝藏猫腻','政府网站政风满意率调查公示被曝藏猫腻',NULL,NULL,NULL,'一个政府网站政风行风投票评议公示被网友指出暗藏玄机，依此公示计算即使全票不满意，群众满意率调查也会不低于50%。对此，该地纪委回应称是网络公司搞错了，并迅速将网上公式做了更改。工作人员称，该公式不会影响评选结果，因只统计评分，不会关注满意率。\r\n','2011-01-04 11:12:15',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (60,'黄山徽州区\"因人设岗\"招聘续:招聘方案全部终止','黄山徽州区\"因人设岗\"招聘续:招聘方案全部终止',NULL,NULL,NULL,'12月24日，一名网友发帖称黄山市徽州区事业单位招聘为领导子女专设，引起网民热议。1月3日，徽州招聘工作负责人表示，除原定于1月3日进行的面试已经在2日公告取消外，此次事业单位招聘方案目前也已全部终止。当地将完善招聘工作，重新组织实施。','2011-01-04 11:13:25',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (61,'屠宰场视频曝生猪注水 被拍方称遭诬陷','屠宰场视频曝生猪注水',NULL,NULL,NULL,'日前，记者收到淄博一市民送来的屠宰场“注水肉”视频。而被拍摄厂家说是被人“诬陷”了，当地主管部门称拍摄者是“自导自演”，警方表示视频拍摄者属“团伙犯罪”，目前已有两人被刑拘。','2011-01-04 11:14:40',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (62,'深圳保障房跨年丑闻，谁该脸红','深圳保障房跨年丑闻',NULL,NULL,NULL,'深圳市第二次保障性住房从名单初审到终审，形式上遮遮掩掩，内容中问题成堆，有关部门未能真正按照制度和规则办事，未能反映出“公生明、廉生威”式的磊落坦荡。\r\n','2011-01-04 11:16:01',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (63,'“被走过场”的民调如何硬气起来','“被走过场”的民调如何硬气起来',NULL,NULL,NULL,'要让“被走过场”的民调硬气起来，关键还得在程序的公正与透明上下功夫。','2011-01-04 11:17:04',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (64,'校车，如何让家长放心？','校车，如何让家长放心？',NULL,NULL,NULL,'尊重孩子的生命，呵护孩子的安全，起码得建造坚固的校舍，起码让孩子坐上安全的校车，如果没有这些基本的硬件，一切皆是虚妄之谈。','2011-01-04 11:18:39',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (65,'郭松民 ：勤奋工作赶不上物价涨幅 ','郭松民 ：勤奋工作赶不上物价涨幅 ',NULL,NULL,NULL,'通胀问题根本上是一个宏观经济政策问题，因此也应该通过政府的宏观调控来解决，各国皆然，从来没有哪个国家是靠号召民众通过“勤奋工作”来应对通胀的。','2011-01-04 11:19:33',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (66,'叶檀：警惕以城市化名义剥夺农民土地红利 ','叶檀：警惕以城市化名义剥夺农民土地红利 ',NULL,NULL,NULL,'中国目前紧锣密鼓的城市化进程，事实上是1949年之后的另一次土改。当年的土改是将土地分到农民手中，公社时期前后则是将土地集中国有化，现在的土改主要是将集体土地重新流入市场进行财富再分配。','2011-01-04 11:20:15',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (112,'宇通客车总裁汤玉祥：不会拓展轿车业务','宇通客车总裁汤玉祥',NULL,NULL,NULL,'2010年12月27日，几位来自东北的宇通客户因为及时提到了车，在宇通大厦楼上的餐厅推杯换盏，喜形于色，还有一些来自天南海北的客户不得不选择在专门针对客户营业的宇通大厦客房里入住，等待提车的消息。','2011-01-04 13:43:07',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (115,'传Facebook获高盛5亿美元投资 估值达500亿美元','传Facebook获高盛5亿美元投资 估值达500亿美元',NULL,NULL,NULL,'据知情人士周一表示，美国著名社交网站Facebook已经从高盛集团和德国的一家投资公司Digital Sky Technologies手中获得了5亿美元的投资。','2011-01-04 13:44:49',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (116,'大佬们的新年：李开复等回老家 李瑜三亚奇遇','大佬们的新年',NULL,NULL,NULL,'热闹的2010年过去了，伴随着各式各样的盘点，给力的，V5的，淡定……','2011-01-04 13:46:10',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (117,'百姓网王建硕：分类网站关键在于规模化服务','百姓网王建硕',NULL,NULL,NULL,'“分类广告是人类的基本需求，只是现在把原本放在平面媒体上的分类信息搬到互联网上而已，‘在线分类＋社区互动’给了分类广告新的生存平台。”','2011-01-04 13:48:04',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (172,'酷乐影音','酷乐影音2010 1.0.3.6',NULL,NULL,NULL,'随点随看','2011-01-04 14:34:23',NULL,NULL,NULL,0,'/u/cms/www/201112/19141318apz1.jpg','http://img.duote.com/softImg/soft/25902_s.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (173,'千千静听','千千静听(TTPlayer) V5.7 BETA1',NULL,NULL,NULL,'千千静听是一款完全免费的音乐播放软件，拥有自主研发的全新音频引擎，集播放、音效、转换、歌词等众多功能于一身。其小巧精致、操作简捷、功能强大的特点，深得用户喜爱，被网友评为中国十大优秀软件之一，并且成为目前国内最受欢迎的音乐播放软件。','2011-01-04 14:44:02',NULL,NULL,NULL,0,'/u/cms/www/201112/19141200ip5c.jpg','http://ttplayer.qianqian.com/upload/100902/100902162707s.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (174,'谷歌浏览器','谷歌浏览器(Google Chrome) V6.0.447.0 Beta',NULL,NULL,NULL,'谷歌浏览器','2011-01-04 14:44:59',NULL,NULL,NULL,0,'/u/cms/www/201112/19140340fri2.jpg','http://img.duote.com/softImg/soft/5897_s.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (176,'美国国家地理评2010年最佳太空图片','国家地理最佳太空图片',NULL,NULL,NULL,'包括曼谷上空日环食、哈勃太空望远镜拍摄的神秘山等壮观景象。','2011-01-04 14:55:11',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/11/11/20101111142555e4f00.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/12/6/201012061800268136c.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (177,'外太空鸟瞰地球河流','外太空鸟瞰地球河流',NULL,NULL,NULL,'这些旖旎的风景，从一个侧面反映出了气候对地球环境的影响。','2011-01-04 14:56:38',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/11/11/20101111142555e4f00.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/11/11/20101111142555e4f00.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (178,'抓拍彗星划过洛城夜空','抓拍彗星划过洛城夜空',NULL,NULL,NULL,'11月9日，美国洛杉矶，池谷—村上彗星划过夜空。','2011-01-04 14:57:29',NULL,NULL,NULL,0,'http://img2.cache.netease.com/cnews/2010/11/19/20101119150339508b6.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/11/19/20101119150339508b6.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (179,'微观摄影赛获奖作品选','微观摄影赛获奖作品选',NULL,NULL,NULL,'微观世界摄影大赛公布2010年获奖者名单,展示了一场视觉盛宴。','2011-01-04 14:58:20',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/10/23/2010102315250086ae8.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/10/23/2010102315250086ae8.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (180,'镜头下的梦幻冰川洞穴','镜头下的梦幻冰川洞穴',NULL,NULL,NULL,'这些怪异的冰川洞穴中环境温度有时会低至-20度以下','2011-01-04 14:59:06',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/10/20/2010102010384818a9e.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/10/20/2010102010384818a9e.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (181,'微距下的昆虫之美','微距下的昆虫之美',NULL,NULL,NULL,'在微距摄影师约翰·霍尔门看来，昆虫隐藏着数不清的魅力。','2011-01-04 14:59:48',NULL,NULL,NULL,0,'http://img2.cache.netease.com/cnews/2010/10/20/20101020101550c3f85.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/10/20/20101020101550c3f85.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (182,'美国迎来秋天狩猎季','美国迎来秋天狩猎季',NULL,NULL,NULL,'对于数百万美国人来说，秋天是狩猎季节的开始。','2011-01-04 15:00:32',NULL,NULL,NULL,0,'http://img2.cache.netease.com/cnews/2010/9/16/201009161706524bdbc.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/9/28/201009281459528f697.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (183,'艺术家创石头平衡术','艺术家创石头平衡术',NULL,NULL,NULL,'据英国《每日邮报》报道，英国艺术家阿德里安·格雷在海滩上利用石头创造出令人惊奇的艺术作品。','2011-01-04 15:01:43',NULL,NULL,NULL,0,'http://img2.cache.netease.com/cnews/2010/9/16/201009161706524bdbc.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/9/16/201009161706524bdbc.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (184,'印尼海底发现新物种','印尼海底发现新物种',NULL,NULL,NULL,'当地时间8月26日，由印度尼西亚和美国科学家组成的探险小组宣布，他们利用无人遥控潜水器在对印尼苏拉威西岛附近的卡维奥巴拉特海底火山勘测时，意外发现一批新的海洋生物。','2011-01-04 15:03:31',NULL,NULL,NULL,0,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg',NULL,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (185,'首位机器宇航员将服役','首位机器宇航员将服役',NULL,NULL,NULL,'2010年9月，“机器宇航员2号”将有可能前往国际空间站。该机器人成员的主要工作任务在于执行国际空间站中危险及重复的太空作业，以节省人手和时间使得空间站的其他宇航员可以从事其他太空研究工作.','2011-01-04 15:04:31',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/8/13/2010081315365844b74.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/8/13/2010081315365844b74.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (186,'谎言：日本科研捕鲸','谎言：日本科研捕鲸',NULL,NULL,NULL,'2010年6月25日世界捕鲸委员会（IWC）年度大会在摩洛哥阿迪加尔闭幕，这次原本旨在达成新的捕鲸管理法规的国际努力最终无果而终。这意味着，日本依然可以“科研捕鲸”的借口每年捕杀1500头鲸，其中包括在南大洋鲸类禁猎区捕鲸。','2011-01-04 15:05:34',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/6/30/201006301150090b875.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/6/30/201006301150090b875.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (187,'农场上的狂欢：欧洲最大的音乐节','欧洲最大的音乐节',NULL,NULL,NULL,'6月23日第四十届格拉斯顿伯里音乐节在英国格拉斯顿伯里的沃西农场开幕。由奶农迈克尔·伊维斯于1970年创办的音乐节，如今已成为欧洲最大的音乐节。','2011-01-04 15:06:52',NULL,NULL,NULL,0,'http://img1.cache.netease.com/cnews/2010/7/1/20100701105023f6a14.jpg',NULL,'http://img1.cache.netease.com/cnews/2010/7/1/20100701105023f6a14.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (188,'世界各地迎接2011新年','世界各地迎接2011新年',NULL,NULL,NULL,'2011年在全球各地逐渐揭开面纱，庆贺新年伊始是世界各国各地区的普遍习俗，各地民众纷纷以各种庆祝活动迎接新年的到来。','2011-01-04 15:13:44',NULL,NULL,NULL,0,NULL,NULL,'http://img1.cache.netease.com/cnews/2011/1/1/201101011535122ec4b.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (189,'一段用生命写出来的故事 讲述了一个简单的环岛梦',NULL,NULL,NULL,NULL,'改编自台湾真人实事的故事《百万步的爱》,纪录著一个男人在妻子罹患小脑萎缩症多年後,仍不离不弃、并实践年轻时的承诺,推著轮椅上的妻子环岛...','2011-12-17 14:06:55',NULL,NULL,NULL,0,'/u/cms/www/201112/17141056zbjl.jpg',NULL,'/u/cms/www/201112/171410455sqq.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (190,'2011岁末催泪大戏《倾城之泪》',NULL,NULL,NULL,NULL,'黄真真执导的催泪爱情巨制《倾城之泪》,以“三滴泪”的形式诠释三段动人的爱情故事,三对白色恋人轮番凄美恋情,打造“史上最感人的爱情催泪大戏”...','2011-12-17 14:12:57',NULL,NULL,NULL,0,'/u/cms/www/201112/17141341je98.jpg',NULL,'/u/cms/www/201112/17141320s4xy.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (191,'演史上最长贺岁大片 文章携汤唯黄渤欢乐声援',NULL,NULL,NULL,NULL,'故事以两个好朋友在寻找多年不见的好兄弟兰彻的过程中展开的回忆.讲述十年前兰彻顶替他人来到皇家工程学院读书...','2011-12-17 14:16:07',NULL,NULL,NULL,0,'/u/cms/www/201112/17141604lrhh.jpg',NULL,'/u/cms/www/201112/171415447op5.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (192,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 15:45:32','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171545290lr9.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (193,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 16:11:27','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (194,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 16:11:41','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171611397af9.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (195,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 16:12:30','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (196,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 16:12:44','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161242nbqp.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (197,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,'婚礼上最倒霉十大新郎','2011-12-17 16:12:55','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612535rhr.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (198,'婚礼上最倒霉十大新郎',NULL,NULL,NULL,NULL,NULL,'2011-12-17 16:13:26','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (209,'好搞笑的视频',NULL,NULL,NULL,NULL,NULL,'2011-12-17 16:56:26','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (210,'jeecms视频教程',NULL,NULL,NULL,NULL,'jeecms视频教程','2011-12-17 16:59:57','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (211,'jeecms要发视频教程了',NULL,NULL,NULL,NULL,'jeecms要发视频教程了','2011-12-17 17:00:14','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (212,'超级火爆的恶搞视频',NULL,NULL,NULL,NULL,'超级火爆的恶搞视频','2011-12-17 17:01:18','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (213,'视频功能很强大啊',NULL,NULL,NULL,NULL,'视频功能很强大啊','2011-12-17 17:01:50','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (214,'测试一下视频功能',NULL,NULL,NULL,NULL,'测试一下视频功能','2011-12-17 17:02:03','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (215,'果然很有才',NULL,NULL,NULL,NULL,'果然很有才','2011-12-17 17:02:20','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (216,'我爱jeecms',NULL,NULL,NULL,NULL,NULL,'2011-12-17 17:05:34','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (217,'hold不住了',NULL,NULL,NULL,NULL,'hold不住了','2011-12-17 17:07:05','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161324z8jn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (218,'视频功能多好啊',NULL,NULL,NULL,NULL,'视频功能多好啊','2011-12-17 17:08:21','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (219,'玩的很开心啊',NULL,NULL,NULL,NULL,'玩的很开心啊','2011-12-17 17:09:29','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (220,'jeecms2012来啦',NULL,NULL,NULL,NULL,'jeecms2012来啦','2011-12-17 17:10:57','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (223,'冬日的温暖','冬日的温暖',NULL,NULL,NULL,'冬日的温暖','2011-12-17 17:21:16',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/171415447op5.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (224,'冬日写真',NULL,NULL,NULL,NULL,NULL,'2011-12-17 17:25:18',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/171415447op5.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (225,'演史上最长贺岁大片',NULL,NULL,NULL,NULL,'故事以两个好朋友在寻找多年不见的好兄弟兰彻的过程中展开的回忆.讲述十年前兰彻顶替他人来到皇家工程学院读书...','2011-12-17 17:27:30',NULL,NULL,NULL,0,'/u/cms/www/201112/17141604lrhh.jpg',NULL,'/u/cms/www/201112/171415447op5.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (226,'市场短期反弹需求强烈 可稳健加仓六只基金',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:30:48',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (227,'基金集中持股或同系抱团个股 不确定隐忧频现',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:32:58',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (228,'中小基金公司首批专户放行 实际运作比预期快',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:33:33',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190837201odl.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (229,'RQFII启动 200亿背后存6000亿庞大“主力军”',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:34:05',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (230,'指数现“十年轮回” 基民对待“抄底”仍谨慎',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:34:55',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (231,'上市指基交投两极分化 入围两融标的成分水岭',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:35:21',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (232,'年末基金“意外”收获 新基金销售井喷',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:35:45',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (233,'2011躁动的人才流动 基金法修订能否带来曙光',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:36:16',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (234,'捷克前总统瓦茨拉夫-哈维尔去世 享年76岁',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:40:56',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (235,'菲律宾风暴遇难者升至652人 800人失踪',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:42:45',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (236,'印尼爪哇岛海域一艘偷渡船倾覆 200余人失踪',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:44:27',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (237,'数万名巴基斯坦人举行大规模反北约美国集会',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:45:02',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (238,'美军全部撤离伊拉克 近9年伊战代价是天文数字',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:45:39',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (239,'俄钻井平台倾覆沉没致4人死 梅德韦杰夫令彻查',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:46:10',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (240,'以色列释放550名巴在押人员 落实换人协议',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:46:32',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (241,'白蚁围攻广州四座大桥 海珠桥防震枕木几被掏空',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:51:40',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (242,'媒体称珠三角部分外向型企业因成本上升关停',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:52:05',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (243,'中国语文教育已经到了最危险的时刻',NULL,NULL,NULL,NULL,'廖增湖还有个身份——作家。在圈子里，他笔名叶开，素来以阅读面宽阔文章不落俗套著称，几部长篇小说，口碑很好，被评论界称为“上海的王朔，中国的拉伯雷”。如今是知名文学杂志《收获》的编审。\r\n\r\n','2011-12-19 08:53:01',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19090536qbxp.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (244,'电信联通申请中止垄断调查 “余额不退”遭疑',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:53:27',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (245,'足坛反赌案铁岭正式开审 张建强首个出庭受审',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:53:48',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (246,'兰州金河煤矿发生冒顶事故 4人获救2人死亡',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:54:35',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (247,'新生代农民工：文化市场与公共服务的夹心层',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:55:04',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (248,'海南万宁大桥坍塌事件3责任人涉嫌渎职被公诉',NULL,NULL,NULL,NULL,NULL,'2011-12-19 08:55:51',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (249,'南京排水管理处原副处长受贿130万获刑逾十年',NULL,NULL,NULL,NULL,'2010年2月底，媒体报道了南京市板仓街雨污分流改造工程出现多处塌陷的问题，事故原因调查中，多处疑点直指主管这段工程的南京市市政排水管理处副处长谭沭兵。 一年后，曾任南京市排水管理处规划建设科科长、南京市给排水工程公司经理的原南京市排水管理处副处长谭沭兵，因受贿罪被法院一审判处有期徒刑10年6个月，没收个人财产60万元','2011-12-19 09:02:32',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19090143y6m0.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (250,'河南一红会医院疑篡改病历掩盖患者死亡真相',NULL,NULL,NULL,NULL,'新郑市新村镇村民邓永杰2009年3月20日患“溃疡性结肠炎”入住新郑红十字会肛肠医院治疗，后病情发展，转院治疗，不久病死。邓永杰妻子樊连花认为该医院在给邓永杰治疗时有过错，将其起诉到法院。“没想到该医院向卫生局和法院出具了两份不同的病历！”樊连花说。记者核实发现，两份病历医师签名和用药都有不同之处。\r\n\r\n','2011-12-19 09:10:17',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19090946s78x.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (251,'星河湾降价6亿补偿老业主 业内称破坏市场规律',NULL,NULL,NULL,NULL,'尽管遭遇严厉调控，但上海星河湾和浦东星河湾并非陷入成交泥塘。据悉，浦东星河湾二期今年8月开盘，均价约7.8万元/平方米，目前销售约80套；上海星河湾今年也出货100多套，均价在5.2万元/平方米左右。由于户型最小面积都在200平方米以上，两个项目均是千万元起步，一年内录得180多套的销售，在上海豪宅市场中尚处于佼佼者。\r\n','2011-12-19 09:19:32',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (252,'北京：2012限购不会松绑 保障房将“以租为主”',NULL,NULL,NULL,NULL,'北京市住建委委员王荣武表示，经过一年的房地产市场调控，北京楼市价格终于实现“稳中有降”的目标，各项指标变化符合调控预期','2011-12-19 09:20:23',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (253,'星河湾上海两项目降价 称拿6亿为老业主补差',NULL,NULL,NULL,NULL,'据中国之声《央广新闻》报道，在楼市调控严厉的大背景下，星河湾集团近日宣布旗下上海两个项目下调售价——8-8.5折或更大的优惠，同时表示将拿出超6亿元对前期老业主进行价差补偿。\r\n\r\n','2011-12-19 09:20:58',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (254,'中华首款SUV中华V5到店实拍',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:22:08',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190922058498.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (255,'圣诞节新品笔记本导购 联想Y470p领衔',NULL,NULL,NULL,NULL,'本周日就是圣诞节，在北京、上海等一线城市的许多外资公司里都有着圣诞节交换礼物的习惯，笔者就在上周五准备了一份礼物与同事交换。一般来说，同事、朋友之间的圣诞礼物多半是一些价值不高的小礼品，但是对于情人、亲人或者有业务往来的伙伴来说，圣诞节送的礼物档次可以提高一些','2011-12-19 09:23:07',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (256,'年底最保值手机搜罗 1.5GHz双核2000元',NULL,NULL,NULL,NULL,'2011年即将结束，每年的这个时候都是购机的旺季。对于用户而言，购买手机最关心的就是价格问题，降到心理价位就要入手？NO！NO！NO！从手机市场来看，价格便宜但仍需稳定，如果今天手机报价3500元，明天就跌至3000元，这样的情况谁也不愿碰到。相反对于官方定价的手机，比如小米手机','2011-12-19 09:23:31',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (257,'康菲石油食言 称无证据显示渤海溢油致环境污染',NULL,NULL,NULL,NULL,'马军对记者表示，渤海是半封闭的内海，平均水深仅为20多米，溢油对渤海环境的影响是比较严重的危害。而且，溢油造成的油基泥浆至今还未完全清理干净，对环境的影响还将长期存在。他说，康菲公司对公众隐瞒不报在先，屡屡误导陈述在后，同时一再试图回避承担其造成的生态损害和养殖等经济利益损失，其公信力已经丧失','2011-12-19 09:23:58',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (258,'跨国车企巨头揽本土高管 撤回“外脑”',NULL,NULL,NULL,NULL,'随着在华项目越来越多，包括大众汽车在内的不少跨国汽车巨头陆续遇到高管人才储备断档的瓶颈。这在竞争日益激烈的中国车市，能否顺利解决这一难题，将成为影响企业在华业绩的重要因素','2011-12-19 09:25:32',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (259,'小米手机今日开放购买：3小时10万库存售罄',NULL,NULL,NULL,NULL,'12月18日消息，小米手机今日正式开放销售，不过，在宣布开放销售仅仅3个小时后，小米官方腾讯微博发布公告，称今天凌晨开放购买3小时，12月在线销售10万库存已售罄。小米还表示，下一轮开放购买，请关注小米论坛和官方微博的通知','2011-12-19 09:26:22',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (260,'苹果iPhone 4S（16GB）',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:28:30',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19092939oy7r.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (261,'苹果iPhone 4（16GB） ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:33:03',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19093226mevs.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (262,'三星GALAXY Note I9220',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:34:36',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19093508z0je.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (263,'世界上最强悍的体育运动',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:36:34',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190935502lvi.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (264,'三星I9250（Galaxy Nexus）',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:37:46',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19093742dmvl.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (265,'三星W899',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:38:53',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19093849begu.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (266,'诺基亚Vertu Constellation Quest',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:41:26',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094122mc5z.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (267,'鬼魅写真',NULL,NULL,NULL,NULL,'女人长大之后慢慢的蜕变成一个别人不曾见过的自己\r\n\r\n悲伤的，阳光的，快乐的，邪恶的，又或者，，，，，\r\n\r\n你的心里住着几个不同的自己呢？？？','2011-12-19 09:41:53',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190947311x4l.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (268,'诺基亚Oro',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:42:42',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094240v28v.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (269,'诺基亚8800ca',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:43:49',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190943477255.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (270,'HTC Flyer',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:45:10',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094508jfde.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (271,'思路摄影【闺蜜糖心派】',NULL,NULL,NULL,NULL,'我深信这世上有些孩子一直都是天使，所以，请你要一直这样地纯真着。对于这世间所有的烈日风霜，一定要相信，你不只是一个人手心里的宝。\r\n   愿天下所有的闺蜜快乐健康！~~','2011-12-19 09:45:20',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094631wo80.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (272,'HTC sensation（G14） ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:46:28',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190946273j5i.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (273,'HTC Sensation XE（G18)',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:47:13',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190947003jp4.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (274,'摩托罗拉ME525 Defy ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:48:33',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094831t9hq.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (275,'如果我们有一颗感恩的心',NULL,NULL,NULL,NULL,'如果我们有一颗感恩的心，我们就会明白是谁给予了我们的生命，是谁把我们养大成人，是谁在真正关心帮助我们；我们就会明白有这份尽管可能是不太理想，却是来之不易的工作是如何的重要；我们更会明白有一个生存的环境和喘息的空间的弥足珍贵。','2011-12-19 09:49:54',NULL,NULL,NULL,0,'/u/cms/www/201112/190949521eei.jpg',NULL,'/u/cms/www/201112/19095026h3ca.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (276,'摩托罗拉XT910（DROID RAZR） ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:49:55',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19094951qk1a.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (277,'索尼爱立信MT15i',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:51:04',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095103kcpr.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (278,'索尼爱立信Xperia ray ST18i ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:52:07',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095231lcq8.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (279,'联想乐Phone P70',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:53:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190953537bvn.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (280,'最私密的12星座内衣全分析',NULL,NULL,NULL,NULL,'内衣是女人的自我宠爱，也是女人的另一种爱情。时令进冬天，空气中飘浮着成熟的味道，阳光普照下，心也云淡风轻。优雅如你，置身于逃不脱的十二星座。内衣设计需要特别注重各个细节的个性，考虑从星相角度对人体心理气场及整体运势的微妙影响。','2011-12-19 09:54:30',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095401xwwr.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (281,'联想乐Phone S2',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:54:45',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/190954435nww.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (282,'步步高i531',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:55:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095533hv75.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (283,'步步高vivo V1 ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:56:14',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095559cx9e.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (284,'步步高i606 ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:57:13',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095711di9x.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (285,'黑莓9900 ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:58:26',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095824t0mi.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (286,'“奶茶MM”加入清华啦啦队 激情热舞展现青春活力',NULL,NULL,NULL,NULL,'近日，国家体育总局体操运动管理中心主办、北京市大学生体操协会“两操”分会和全国啦啦操竞赛委员会承办的2011年全国啦啦操联赛北京站比赛在京举行。来自清华大学的“奶茶MM”章泽天激情热舞格外抢眼，成本次比赛话题人物。','2011-12-19 09:58:29',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095752235i.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (287,'黑莓9700',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:59:00',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095858mdri.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (288,'天语E800',NULL,NULL,NULL,NULL,NULL,'2011-12-19 09:59:42',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19095940zkzo.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (289,'天语阿里云',NULL,NULL,NULL,NULL,NULL,'2011-12-19 10:00:48',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19100045xs61.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (290,'魅族M9（8GB)',NULL,NULL,NULL,NULL,NULL,'2011-12-19 10:01:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19100130dqrl.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (291,'魅族MX（16GB)',NULL,NULL,NULL,NULL,NULL,'2011-12-19 10:02:44',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191002428q2p.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (292,'小米M1（MIUI） ',NULL,NULL,NULL,NULL,NULL,'2011-12-19 10:04:30',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19100427q41v.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (293,'星座女星的制服诱惑',NULL,NULL,NULL,NULL,'说到制服，总能引起男人们无限的遐想，而性感的身材在制服的衬托下则更具诱惑力！无论是女仆、学生妹、护士还是警察，每一种Style都独有风韵。可以是天使面庞魔鬼身材，也可以是清纯无敌娇艳欲滴……现在，就让我们悉数星座女星制服造型，看看谁最让人无法抗拒','2011-12-19 10:08:21',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19100819q0nh.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (294,'当金发妞恋上中国风',NULL,NULL,NULL,NULL,'超模Kate Upton全裸青花瓷彩绘演古典风情','2011-12-19 10:12:01',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19101159t0ld.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (295,'LG P970（Optimus Black）',NULL,NULL,NULL,NULL,' LG P970也拥有着直板触控造型设计，简洁的外形方面有着不错的时尚感表现。在其正面为一块4.0英寸大小的NOVA电容触摸屏，在WVGA级别分辨率的支持下','2011-12-19 10:13:08',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19101258cu07.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (296,'比基尼？看腻了',NULL,NULL,NULL,NULL,'体育画报御用嫩模教你玩转沙滩复古风体育画报御用嫩模教你玩转沙滩复古风体育画报御用嫩模教你玩转沙滩复古风体育画报御用嫩模教你玩转沙滩复古风体育画报御用嫩模教你玩转沙滩复古风','2011-12-19 10:14:08',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19101405vhee.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (297,'销魂睡姿16式',NULL,NULL,NULL,NULL,'销魂睡姿16式——萌翻你的睡意','2011-12-19 10:19:53',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191019506xu1.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (298,'红白相间 如此纯粹',NULL,NULL,NULL,NULL,NULL,'2011-12-19 10:22:29',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19102057h28j.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (299,'揭秘日本艺妓鲜为人知的生活',NULL,NULL,NULL,NULL,'畅销小说《艺伎回忆录》描写了一位日本艺伎在二战期间的生活。女主人公被卖到艺伎馆做奴隶，后来成为了当时最当红的艺伎。她既是一位舞者，也是一位音乐家，更是一个没有太多选择的女人。她的初夜卖出了一个史无前例的天价，这也为她在艺伎的历史中赢得了一席之地','2011-12-19 10:23:16',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19102314gfph.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (300,'世界最疯狂的紧身胸衣',NULL,NULL,NULL,NULL,'紧身衣是爱美女性的最爱，因为它们不仅能使女性保持良好的身材，还可以让她们的曼妙曲线看上去更加诱人，因此深受女性朋友们喜爱。下面这些紧身衣虽然性感十足，但相信并不是每个美女都敢穿。如果你想看上去与众不同，也许可以试一下其中几款，绝对引爆眼球','2011-12-19 10:27:20',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19102719hx2v.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (301,'阜阳“天上人间”的有偿陪侍小姐',NULL,NULL,NULL,NULL,'安徽阜阳网友反映，该市胜利北路“黄金水岸”小区大型住宅区楼下新开的“皇家公馆”，存在非法经营：消防不达标，工商、税务、文化、公安等特种行业均未发证【图片由网友提供】','2011-12-19 10:30:37',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19103035b3ll.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (302,'不一样的美国生活方式',NULL,NULL,NULL,NULL,'他们生活简朴，不使用电力和汽车，拒绝一切现代技术。他们不从军，不接受社会福利，更不购买保险。他们重视宗教的自由，讲究家庭团结，创建和谐社会...” 这...这到底是在说谁？只有贫困国家才有这种族人存在吧？不过话说回来，再穷的话，电也总会有的，可...这些人究竟指哪些人呢？他们就是今天文章中的主人公--Amish阿米什族人','2011-12-19 10:34:05',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19103403xlfg.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (303,'黄金之国加纳的酋长们',NULL,NULL,NULL,NULL,'加纳号称黄金之国，今天看到了很多来自这个国家的酋长，他们全身佩戴的饰品果真是金灿灿的，真不愧是黄金之国的酋长呀','2011-12-19 10:36:59',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191036587tik.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (304,'刘子璇写真',NULL,NULL,NULL,NULL,'如果我们学会了感恩，就会尊敬我们的长者，努力去替她们做点什么；如果我们学会了感恩，就会体贴、关心自己的亲人和朋友，知道他们曾经的关爱，理解他们今天的喜悦、担忧、甚至痛苦，明白我应该给予的支持和关怀；我们学会了感恩，就会珍惜眼前的工作，晓得努力工作不是为了别人，正是为了自己；如果我们学会了感恩','2011-12-19 10:48:59','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/191047224mmx.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (305,'男子网吧内因邻座脚臭将其砍死',NULL,NULL,NULL,NULL,'男子网吧内因邻座脚臭将其砍死男子网吧内因邻座脚臭将其砍死男子网吧内因邻座脚臭将其砍死男子网吧内因邻座脚臭将其砍死男子网吧内因邻座脚臭将其砍死','2011-12-19 10:58:55','/u/cms/www/201112/17165616uvy4.','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (306,'公交投币拒载 农民工赤身怒骂',NULL,NULL,NULL,NULL,'公交投币拒载 农民工赤身怒骂公交投币拒载 农民工赤身怒骂公交投币拒载 农民工赤身怒骂公交投币拒载 农民工赤身怒骂公交投币拒载 农民工赤身怒骂公交投币拒载 农民工赤身怒骂','2011-12-19 10:59:44','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (307,'十大给力最浪漫求婚把两人故事拍成短片',NULL,NULL,NULL,NULL,'十大给力最浪漫求婚把两人故事拍成短片十大给力最浪漫求婚把两人故事拍成短片十大给力最浪漫求婚把两人故事拍成短片十大给力最浪漫求婚把两人故事拍成短片十大给力最浪漫求婚把两人故事拍成短片','2011-12-19 11:00:37','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (308,'十二星座2012年爱情运势大解读',NULL,NULL,NULL,NULL,'十二星座2012年爱情运势大解读十二星座2012年爱情运势大解读十二星座2012年爱情运势大解读十二星座2012年爱情运势大解读十二星座2012年爱情运势大解读','2011-12-19 11:01:22','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (309,'《梦宅诡影》终极预告片 007转型玩惊悚',NULL,NULL,NULL,NULL,'《梦宅诡影》终极预告片 007转型玩惊悚《梦宅诡影》终极预告片 007转型玩惊悚《梦宅诡影》终极预告片 007转型玩惊悚《梦宅诡影》终极预告片 007转型玩惊悚《梦宅诡影》终极预告片 007转型玩惊悚','2011-12-19 11:01:51','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/171612286b6w.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (310,'大胸妹勾男术：只有想不到没有做不到！',NULL,NULL,NULL,NULL,'大胸妹勾男术：只有想不到没有做不到！大胸妹勾男术：只有想不到没有做不到！大胸妹勾男术：只有想不到没有做不到！大胸妹勾男术：只有想不到没有做不到！大胸妹勾男术：只有想不到没有做不到！','2011-12-19 11:03:01','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (311,'求职者告白聚美陈欧 直言刘惠璞尖酸刻薄',NULL,NULL,NULL,NULL,'求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧 直言刘惠璞尖酸刻薄求职者告白聚美陈欧 直言刘惠璞尖酸刻薄','2011-12-19 11:03:41','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (312,'“梅花奶奶”舍身救男童',NULL,NULL,NULL,NULL,'“梅花奶奶”舍身救男童“梅花奶奶”舍身救男童“梅花奶奶”舍身救男童“梅花奶奶”舍身救男童“梅花奶奶”舍身救男童','2011-12-19 11:06:15','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (313,'贵州学生食堂使用地沟油 学生愤怒砸食堂',NULL,NULL,NULL,NULL,'贵州学生食堂使用地沟油 学生愤怒砸食堂贵州学生食堂使用地沟油 学生愤怒砸食堂贵州学生食堂使用地沟油 学生愤怒砸食堂贵州学生食堂使用地沟油 学生愤怒砸食堂','2011-12-19 11:07:01','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (314,'实拍一男子自断手指 满地鲜血',NULL,NULL,NULL,NULL,'实拍一男子自断手指 满地鲜血实拍一男子自断手指 满地鲜血实拍一男子自断手指 满地鲜血实拍一男子自断手指 满地鲜血实拍一男子自断手指 满地鲜血','2011-12-19 11:07:39','/u/cms/www/201112/17165616uvy4.swf','FLASH',NULL,0,NULL,NULL,'/u/cms/www/201112/17161125k5kt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (315,'硬盘数据恢复',NULL,NULL,NULL,NULL,'顶尖硬盘数据恢复软件 免费试用版','2011-12-19 11:08:38',NULL,NULL,NULL,0,'/u/cms/www/201112/19140601kgid.jpg','/u/cms/www/201112/19110822fin2.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (316,'阿德雷企鹅',NULL,NULL,NULL,NULL,'昨天晚饭后和张晖骑着雪地摩托车到冰山深处去了一趟，碰到了一只帝企鹅和一群十九只阿德雷企鹅。本来是想去弄点绿色冰回来的，结果也没找到具体地方。正好昨天的风还很大，有8到9级大风。','2011-12-19 11:18:32',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19111756gll3.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (317,'母其弥雅:梦境瑜伽',NULL,NULL,NULL,NULL,'这次近距离的拍摄，让我荣幸地结识了美丽的母其弥雅及她的团队，她的敬业与谦逊让人印象深刻！专业的瑜伽动作与仙境般的雨林相融成的画面让人久久挥之不去！ 母其弥雅个人介绍：母其弥雅，被称为“亚洲最美瑜伽教练”，“瑜伽第一美女”。','2011-12-19 11:22:25',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19112223q81f.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (318,'性感房车宝贝秀色可餐 各个体态丰腴很抢眼',NULL,NULL,NULL,NULL,'WTCC（World Touring Car Championship）即世界房车锦标赛，是FIA国际汽联于2005年新推出的一项全球性汽车赛事。赛场上的宝贝也是一道靓丽的风景线，各个体态婀娜，秀色可餐。','2011-12-19 11:25:12',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19112511nfma.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (319,'励志静态电影《放飞梦想》',NULL,NULL,NULL,NULL,'世上并不缺乏外貌美的人，更不缺乏刻苦努力的人，先天的优势或者盲目地努力并不足以打开成功之门，要在各自领域里有所成就，我们最需要彻底的自我管理和毅力，让自己成为坚定、睿智、内心强大的人','2011-12-19 11:27:14',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191127127do2.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (320,'360杀毒',NULL,NULL,NULL,NULL,'360杀毒','2011-12-19 11:27:15',NULL,NULL,NULL,0,'/u/cms/www/201112/19112623820c.jpg','/u/cms/www/201112/19112700bypf.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (321,'童星今昔对比照大曝光',NULL,NULL,NULL,NULL,'小龙人、霹雳贝贝、红孩儿......一个个耳熟能详的名字，一个个陪伴我们度过美好童年的小明星，时过境迁，如今又是什么模样？本文将带你一起领略昔日同学现在的风采。','2011-12-19 11:31:57',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19113155zjrt.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (322,'美国旧金山圣诞老人大聚会',NULL,NULL,NULL,NULL,'美国旧金山圣诞老人大聚会美国旧金山圣诞老人大聚会美国旧金山圣诞老人大聚会美国旧金山圣诞老人大聚会','2011-12-19 11:33:51',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19113349wi0o.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (323,'柳岩红黑诱惑大片写真',NULL,NULL,NULL,NULL,'日前，柳岩一组红黑两款长裙礼服的性感优雅写真曝光，写真中柳岩马尾低垂眼神温柔，纯红纯黑低胸长礼服上身既凸显傲人美胸，更衬托出柳岩的浓浓优雅女人味。','2011-12-19 11:35:47',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19113545y1n3.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (324,'爨底下村私家写真',NULL,NULL,NULL,NULL,'【现代主义族】 之一 爨底下村私家写真【现代主义族】 之一 爨底下村私家写真【现代主义族】 之一 爨底下村私家写真【现代主义族】 之一 爨底下村私家写真【现代主义族】 之一 爨底下村私家写真','2011-12-19 11:39:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191139257lag.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (325,'AC米兰球星出席圣诞晚会，帅哥美女云集',NULL,NULL,NULL,NULL,'当地时间2011年12月14日，AC米兰全队圣诞晚宴，卡萨诺携妻儿出席，而安布罗西尼、阿奎拉尼和安东尼尼等人都带着妻子女友出席，帅哥云集，太太团斗艳，抢镜十足','2011-12-19 11:41:35',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/191141333snd.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (326,'魔兽世界',NULL,NULL,NULL,NULL,'大地的裂变','2011-12-19 11:42:28',NULL,NULL,NULL,0,'/u/cms/www/201112/19114043tp85.jpg','/u/cms/www/201112/19114201tdir.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (327,'足球宝贝徐冬冬海边写真 湿身展无限魅力',NULL,NULL,NULL,NULL,'足球宝贝徐冬冬海边写真 湿身展无限魅力足球宝贝徐冬冬海边写真 湿身展无限魅力足球宝贝徐冬冬海边写真 湿身展无限魅力足球宝贝徐冬冬海边写真 湿身展无限魅力','2011-12-19 11:43:44',NULL,NULL,NULL,0,NULL,NULL,'/u/cms/www/201112/19114342fmb2.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (328,'天下3',NULL,NULL,NULL,NULL,'6大革新，精彩无穷无尽','2011-12-19 12:02:07',NULL,NULL,NULL,0,'/u/cms/www/201112/191201449moh.jpg','/u/cms/www/201112/191203538tdp.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (329,'神魔大陆',NULL,NULL,NULL,NULL,'暮光之城','2011-12-19 13:17:53',NULL,NULL,NULL,0,'/u/cms/www/201112/19131809acqm.jpg','/u/cms/www/201112/19131949r2ge.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (330,'永恒之塔',NULL,NULL,NULL,NULL,'神之竞技场','2011-12-19 13:45:17',NULL,NULL,NULL,0,'/u/cms/www/201112/191342393mlg.jpg','/u/cms/www/201112/19134448qvza.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (331,'腾讯QQ ',NULL,NULL,NULL,NULL,'2011 正式版','2011-12-19 13:53:49',NULL,NULL,NULL,0,'/u/cms/www/201112/191351590e53.jpg','/u/cms/www/201112/19135821ij0m.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (332,'风行网络电影',NULL,NULL,NULL,NULL,'风行网络电影','2011-12-19 13:57:03',NULL,NULL,NULL,0,'/u/cms/www/201112/19135642zjak.jpg','/u/cms/www/201112/19135645ge7r.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (333,'酷狗音乐',NULL,NULL,NULL,NULL,'酷狗音乐7.1','2011-12-19 14:10:29',NULL,NULL,NULL,0,'/u/cms/www/201112/19140803w9fg.jpg','/u/cms/www/201112/191408471iyj.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (334,'搜狗拼音输入',NULL,NULL,NULL,NULL,'准确快速','2011-12-19 14:18:12',NULL,NULL,NULL,0,'/u/cms/www/201112/19141700opui.jpg','/u/cms/www/201112/19141756u9sa.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (335,'有道词典',NULL,NULL,NULL,NULL,'翻译多语种网络词典','2011-12-19 14:21:32',NULL,NULL,NULL,0,'/u/cms/www/201112/19142041eu8x.jpg','/u/cms/www/201112/19142206y73m.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (336,'迅雷7',NULL,NULL,NULL,NULL,'迅雷7.0','2011-12-19 14:25:26',NULL,NULL,NULL,0,'/u/cms/www/201112/19142430589t.jpg','/u/cms/www/201112/19142451945q.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (337,'多玩YY',NULL,NULL,NULL,NULL,'YY4.0是多玩YY语音的全新版本，活动中心盛大起航，汇集YY上最好最优质的频道和活动，提供YY上最有价值的内容，不需再为找好 频道而费尽心机。','2011-12-19 14:30:04',NULL,NULL,NULL,0,'/u/cms/www/201112/19142818yvty.jpg','/u/cms/www/201112/19142840ycm6.jpg',NULL,NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (338,'海豚的故事-先行版预告片',NULL,NULL,NULL,NULL,'讲述的是一条名叫维特的海豚失去了尾巴,在人类救护它的过程中,它和一个善良的小男孩之间建立了温馨的友谊...','2011-12-19 15:43:16',NULL,NULL,NULL,0,'/u/cms/www/201112/19154430myw1.jpg',NULL,'/u/cms/www/201112/19154302hxkg.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (339,'周渝民小小彬温情携手《新天生一对》',NULL,NULL,NULL,NULL,'仔仔首演超级奶爸,杨幂初为人母,ELLA为爱变菲佣,甘愿为仔仔烧菜、洗衣、打扫,只期待有一天心爱的人可以 振作不再堕落...','2011-12-19 15:45:54',NULL,NULL,NULL,0,'/u/cms/www/201112/19154650hare.jpg',NULL,'/u/cms/www/201112/191546459xk2.jpg',NULL,NULL,1);
INSERT INTO `jc_content_ext` VALUES (340,'1212',NULL,'21212',NULL,NULL,'121','2011-12-19 18:00:00',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `jc_content_ext` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_group_view
#

DROP TABLE IF EXISTS `jc_content_group_view`;
CREATE TABLE `jc_content_group_view` (
  `content_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY  (`content_id`,`group_id`),
  KEY `fk_jc_content_group_v` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容浏览会员组关联表';

#
# Dumping data for table jc_content_group_view
#

LOCK TABLES `jc_content_group_view` WRITE;
/*!40000 ALTER TABLE `jc_content_group_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_content_group_view` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_picture
#

DROP TABLE IF EXISTS `jc_content_picture`;
CREATE TABLE `jc_content_picture` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `img_path` varchar(100) NOT NULL COMMENT '图片地址',
  `description` varchar(255) default NULL COMMENT '描述',
  PRIMARY KEY  (`content_id`,`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容图片表';

#
# Dumping data for table jc_content_picture
#

LOCK TABLES `jc_content_picture` WRITE;
/*!40000 ALTER TABLE `jc_content_picture` DISABLE KEYS */;
INSERT INTO `jc_content_picture` VALUES (184,0,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg','');
INSERT INTO `jc_content_picture` VALUES (184,1,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg','');
INSERT INTO `jc_content_picture` VALUES (184,2,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg','');
INSERT INTO `jc_content_picture` VALUES (184,3,'http://img2.cache.netease.com/cnews/2010/8/30/2010083015190967339.jpg','');
INSERT INTO `jc_content_picture` VALUES (185,0,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (185,1,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (185,2,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (185,3,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (186,0,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (186,1,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (186,2,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (186,3,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (187,0,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (187,1,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (187,2,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (187,3,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (189,0,'/u/cms/www/201112/17171653nfp8.jpg','动态');
INSERT INTO `jc_content_picture` VALUES (189,1,'/u/cms/www/201112/171717039zq7.jpg','动态');
INSERT INTO `jc_content_picture` VALUES (189,2,'/u/cms/www/201112/17171709jdjw.jpg','动态');
INSERT INTO `jc_content_picture` VALUES (189,3,'/u/cms/www/201112/17171716ivqv.jpg','动态');
INSERT INTO `jc_content_picture` VALUES (190,0,'/u/cms/www/201112/17171653nfp8.jpg','很好看');
INSERT INTO `jc_content_picture` VALUES (190,1,'/u/cms/www/201112/171717039zq7.jpg','很好看');
INSERT INTO `jc_content_picture` VALUES (190,2,'/u/cms/www/201112/17171709jdjw.jpg','很好看');
INSERT INTO `jc_content_picture` VALUES (190,3,'/u/cms/www/201112/17171716ivqv.jpg','很好看');
INSERT INTO `jc_content_picture` VALUES (191,0,'/u/cms/www/201112/17171653nfp8.jpg','好美');
INSERT INTO `jc_content_picture` VALUES (191,1,'/u/cms/www/201112/171717039zq7.jpg','好美');
INSERT INTO `jc_content_picture` VALUES (191,2,'/u/cms/www/201112/17171709jdjw.jpg','好美');
INSERT INTO `jc_content_picture` VALUES (191,3,'/u/cms/www/201112/17171716ivqv.jpg','好美');
INSERT INTO `jc_content_picture` VALUES (223,0,'/u/cms/www/201112/17171653nfp8.jpg','');
INSERT INTO `jc_content_picture` VALUES (223,1,'/u/cms/www/201112/171717039zq7.jpg','');
INSERT INTO `jc_content_picture` VALUES (223,2,'/u/cms/www/201112/17171709jdjw.jpg','');
INSERT INTO `jc_content_picture` VALUES (223,3,'/u/cms/www/201112/17171716ivqv.jpg','');
INSERT INTO `jc_content_picture` VALUES (224,0,'/u/cms/www/201112/17171653nfp8.jpg','');
INSERT INTO `jc_content_picture` VALUES (224,1,'/u/cms/www/201112/171717039zq7.jpg','');
INSERT INTO `jc_content_picture` VALUES (224,2,'/u/cms/www/201112/17171709jdjw.jpg','');
INSERT INTO `jc_content_picture` VALUES (224,3,'/u/cms/www/201112/17171716ivqv.jpg','');
INSERT INTO `jc_content_picture` VALUES (225,0,'/u/cms/www/201112/17171653nfp8.jpg','');
INSERT INTO `jc_content_picture` VALUES (225,1,'/u/cms/www/201112/171717039zq7.jpg','');
INSERT INTO `jc_content_picture` VALUES (225,2,'/u/cms/www/201112/17171709jdjw.jpg','');
INSERT INTO `jc_content_picture` VALUES (225,3,'/u/cms/www/201112/17171716ivqv.jpg','');
INSERT INTO `jc_content_picture` VALUES (263,0,'/u/cms/www/201112/19093530anjq.jpg','');
INSERT INTO `jc_content_picture` VALUES (263,1,'/u/cms/www/201112/19093534pbhx.jpg','');
INSERT INTO `jc_content_picture` VALUES (263,2,'/u/cms/www/201112/19093538klmo.jpg','');
INSERT INTO `jc_content_picture` VALUES (263,3,'/u/cms/www/201112/19093544rdou.jpg','');
INSERT INTO `jc_content_picture` VALUES (267,0,'/u/cms/www/201112/190938494dpy.jpg','');
INSERT INTO `jc_content_picture` VALUES (267,1,'/u/cms/www/201112/19093854n45u.jpg','');
INSERT INTO `jc_content_picture` VALUES (267,2,'/u/cms/www/201112/19093857hrzl.jpg','');
INSERT INTO `jc_content_picture` VALUES (267,3,'/u/cms/www/201112/19093900wjkf.jpg','');
INSERT INTO `jc_content_picture` VALUES (271,0,'/u/cms/www/201112/19094329a5xa.jpg','');
INSERT INTO `jc_content_picture` VALUES (271,1,'/u/cms/www/201112/19094332tbdx.jpg','');
INSERT INTO `jc_content_picture` VALUES (271,2,'/u/cms/www/201112/190943347cma.jpg','');
INSERT INTO `jc_content_picture` VALUES (271,3,'/u/cms/www/201112/19094339inmu.jpg','');
INSERT INTO `jc_content_picture` VALUES (275,0,'/u/cms/www/201112/1909485814nz.jpg','');
INSERT INTO `jc_content_picture` VALUES (275,1,'/u/cms/www/201112/190949019o6w.jpg','');
INSERT INTO `jc_content_picture` VALUES (275,2,'/u/cms/www/201112/19094903k37i.jpg','');
INSERT INTO `jc_content_picture` VALUES (275,3,'/u/cms/www/201112/19094907y45o.jpg','');
INSERT INTO `jc_content_picture` VALUES (275,4,'/u/cms/www/201112/190949132edb.jpg','');
INSERT INTO `jc_content_picture` VALUES (280,0,'/u/cms/www/201112/190952289wxr.jpg','');
INSERT INTO `jc_content_picture` VALUES (280,1,'/u/cms/www/201112/19095231picz.jpg','');
INSERT INTO `jc_content_picture` VALUES (280,2,'/u/cms/www/201112/19095234gz2e.jpg','');
INSERT INTO `jc_content_picture` VALUES (280,3,'/u/cms/www/201112/19095237hsr4.jpg','');
INSERT INTO `jc_content_picture` VALUES (280,4,'/u/cms/www/201112/19095244lvkp.jpg','');
INSERT INTO `jc_content_picture` VALUES (286,0,'/u/cms/www/201112/190956509gt3.jpg','');
INSERT INTO `jc_content_picture` VALUES (286,1,'/u/cms/www/201112/190956531lp5.jpg','');
INSERT INTO `jc_content_picture` VALUES (286,2,'/u/cms/www/201112/190956567pd6.jpg','');
INSERT INTO `jc_content_picture` VALUES (286,3,'/u/cms/www/201112/19095659npus.jpg','');
INSERT INTO `jc_content_picture` VALUES (293,0,'/u/cms/www/201112/19100730yqkm.jpg','');
INSERT INTO `jc_content_picture` VALUES (293,1,'/u/cms/www/201112/19100732rbr3.jpg','');
INSERT INTO `jc_content_picture` VALUES (293,2,'/u/cms/www/201112/19100735jhtm.jpg','');
INSERT INTO `jc_content_picture` VALUES (293,3,'/u/cms/www/201112/19100738xu07.jpg','');
INSERT INTO `jc_content_picture` VALUES (294,0,'/u/cms/www/201112/19101104l8gx.jpg','');
INSERT INTO `jc_content_picture` VALUES (294,1,'/u/cms/www/201112/19101107fyhz.jpg','');
INSERT INTO `jc_content_picture` VALUES (294,2,'/u/cms/www/201112/19101110t0zj.jpg','');
INSERT INTO `jc_content_picture` VALUES (294,3,'/u/cms/www/201112/191011141hrz.jpg','');
INSERT INTO `jc_content_picture` VALUES (296,0,'/u/cms/www/201112/19101300vgo6.jpg','');
INSERT INTO `jc_content_picture` VALUES (296,1,'/u/cms/www/201112/191013028ees.jpg','');
INSERT INTO `jc_content_picture` VALUES (296,2,'/u/cms/www/201112/19101306te5g.jpg','');
INSERT INTO `jc_content_picture` VALUES (296,3,'/u/cms/www/201112/19101310nlax.jpg','');
INSERT INTO `jc_content_picture` VALUES (297,0,'/u/cms/www/201112/19101815my5b.jpg','');
INSERT INTO `jc_content_picture` VALUES (297,1,'/u/cms/www/201112/191018173g5w.jpg','');
INSERT INTO `jc_content_picture` VALUES (297,2,'/u/cms/www/201112/19101820duyo.jpg','');
INSERT INTO `jc_content_picture` VALUES (297,3,'/u/cms/www/201112/1910182468l0.jpg','');
INSERT INTO `jc_content_picture` VALUES (298,0,'/u/cms/www/201112/19111932a9xv.jpg','');
INSERT INTO `jc_content_picture` VALUES (298,1,'/u/cms/www/201112/19111935140r.jpg','');
INSERT INTO `jc_content_picture` VALUES (298,2,'/u/cms/www/201112/19111938y8xu.jpg','');
INSERT INTO `jc_content_picture` VALUES (299,0,'/u/cms/www/201112/19102230f277.jpg','');
INSERT INTO `jc_content_picture` VALUES (299,1,'/u/cms/www/201112/19102233g7ot.jpg','');
INSERT INTO `jc_content_picture` VALUES (299,2,'/u/cms/www/201112/191022359mqb.jpg','');
INSERT INTO `jc_content_picture` VALUES (299,3,'/u/cms/www/201112/191022388uvv.jpg','');
INSERT INTO `jc_content_picture` VALUES (300,0,'/u/cms/www/201112/191026320f2y.jpg','');
INSERT INTO `jc_content_picture` VALUES (300,1,'/u/cms/www/201112/19102634814r.jpg','');
INSERT INTO `jc_content_picture` VALUES (300,2,'/u/cms/www/201112/19102637g8wu.jpg','');
INSERT INTO `jc_content_picture` VALUES (300,3,'/u/cms/www/201112/191026405ljl.jpg','');
INSERT INTO `jc_content_picture` VALUES (301,0,'/u/cms/www/201112/1910294695ky.jpg','');
INSERT INTO `jc_content_picture` VALUES (301,1,'/u/cms/www/201112/19102949nry1.jpg','');
INSERT INTO `jc_content_picture` VALUES (301,2,'/u/cms/www/201112/191029525wi9.jpg','');
INSERT INTO `jc_content_picture` VALUES (301,3,'/u/cms/www/201112/191029566nps.jpg','');
INSERT INTO `jc_content_picture` VALUES (302,0,'/u/cms/www/201112/19103318bgac.jpg','');
INSERT INTO `jc_content_picture` VALUES (302,1,'/u/cms/www/201112/191033227v2n.jpg','');
INSERT INTO `jc_content_picture` VALUES (302,2,'/u/cms/www/201112/19103325i7eh.jpg','');
INSERT INTO `jc_content_picture` VALUES (302,3,'/u/cms/www/201112/19103329ra1y.jpg','');
INSERT INTO `jc_content_picture` VALUES (303,0,'/u/cms/www/201112/191035531l49.jpg','');
INSERT INTO `jc_content_picture` VALUES (303,1,'/u/cms/www/201112/19103555yzl9.jpg','');
INSERT INTO `jc_content_picture` VALUES (303,2,'/u/cms/www/201112/19103559ef8r.jpg','');
INSERT INTO `jc_content_picture` VALUES (303,3,'/u/cms/www/201112/19103603lu7z.jpg','');
INSERT INTO `jc_content_picture` VALUES (316,0,'/u/cms/www/201112/191116447msi.jpg','');
INSERT INTO `jc_content_picture` VALUES (316,1,'/u/cms/www/201112/19111647quq9.jpg','');
INSERT INTO `jc_content_picture` VALUES (316,2,'/u/cms/www/201112/191116504sdn.jpg','');
INSERT INTO `jc_content_picture` VALUES (316,3,'/u/cms/www/201112/19111654zbf8.jpg','');
INSERT INTO `jc_content_picture` VALUES (317,0,'/u/cms/www/201112/19112136dax2.jpg','');
INSERT INTO `jc_content_picture` VALUES (317,1,'/u/cms/www/201112/19112139m5p5.jpg','');
INSERT INTO `jc_content_picture` VALUES (317,2,'/u/cms/www/201112/19112142aniu.jpg','');
INSERT INTO `jc_content_picture` VALUES (317,3,'/u/cms/www/201112/191121451voq.jpg','');
INSERT INTO `jc_content_picture` VALUES (318,0,'/u/cms/www/201112/191124260dyw.jpg','');
INSERT INTO `jc_content_picture` VALUES (318,1,'/u/cms/www/201112/19112428i5di.jpg','');
INSERT INTO `jc_content_picture` VALUES (318,2,'/u/cms/www/201112/19112431i6yl.jpg','');
INSERT INTO `jc_content_picture` VALUES (318,3,'/u/cms/www/201112/191124347he2.jpg','');
INSERT INTO `jc_content_picture` VALUES (319,0,'/u/cms/www/201112/19112627baql.jpg','');
INSERT INTO `jc_content_picture` VALUES (319,1,'/u/cms/www/201112/19112630rnkd.jpg','');
INSERT INTO `jc_content_picture` VALUES (319,2,'/u/cms/www/201112/19112633k9hl.jpg','');
INSERT INTO `jc_content_picture` VALUES (319,3,'/u/cms/www/201112/1911263840lw.jpg','');
INSERT INTO `jc_content_picture` VALUES (321,0,'/u/cms/www/201112/19113108a4tf.jpg','');
INSERT INTO `jc_content_picture` VALUES (321,1,'/u/cms/www/201112/19113111l3z3.jpg','');
INSERT INTO `jc_content_picture` VALUES (321,2,'/u/cms/www/201112/19113114xqvt.jpg','');
INSERT INTO `jc_content_picture` VALUES (321,3,'/u/cms/www/201112/19113119mokz.jpg','');
INSERT INTO `jc_content_picture` VALUES (322,0,'/u/cms/www/201112/191133047hli.jpg','');
INSERT INTO `jc_content_picture` VALUES (322,1,'/u/cms/www/201112/19113306vsom.jpg','');
INSERT INTO `jc_content_picture` VALUES (322,2,'/u/cms/www/201112/1911330905lu.jpg','');
INSERT INTO `jc_content_picture` VALUES (322,3,'/u/cms/www/201112/191133124ekg.jpg','');
INSERT INTO `jc_content_picture` VALUES (323,0,'/u/cms/www/201112/19113455w6da.jpg','');
INSERT INTO `jc_content_picture` VALUES (323,1,'/u/cms/www/201112/19113458qtv5.jpg','');
INSERT INTO `jc_content_picture` VALUES (323,2,'/u/cms/www/201112/19113500lodt.jpg','');
INSERT INTO `jc_content_picture` VALUES (323,3,'/u/cms/www/201112/19113504ak3b.jpg','');
INSERT INTO `jc_content_picture` VALUES (324,0,'/u/cms/www/201112/1911384005xa.jpg','');
INSERT INTO `jc_content_picture` VALUES (324,1,'/u/cms/www/201112/19113842vbep.jpg','');
INSERT INTO `jc_content_picture` VALUES (324,2,'/u/cms/www/201112/191138453a05.jpg','');
INSERT INTO `jc_content_picture` VALUES (324,3,'/u/cms/www/201112/19113849s58m.jpg','');
INSERT INTO `jc_content_picture` VALUES (325,0,'/u/cms/www/201112/19114055fyqx.jpg','');
INSERT INTO `jc_content_picture` VALUES (325,1,'/u/cms/www/201112/191140578xaj.jpg','');
INSERT INTO `jc_content_picture` VALUES (325,2,'/u/cms/www/201112/19114100zis1.jpg','');
INSERT INTO `jc_content_picture` VALUES (325,3,'/u/cms/www/201112/19114104hajf.jpg','');
INSERT INTO `jc_content_picture` VALUES (327,0,'/u/cms/www/201112/191143031z9j.jpg','');
INSERT INTO `jc_content_picture` VALUES (327,1,'/u/cms/www/201112/19114305b0sj.jpg','');
INSERT INTO `jc_content_picture` VALUES (327,2,'/u/cms/www/201112/19114309f164.jpg','');
INSERT INTO `jc_content_picture` VALUES (327,3,'/u/cms/www/201112/19114312te0u.jpg','');
INSERT INTO `jc_content_picture` VALUES (338,0,'/u/cms/www/201112/19155032l0ql.jpg','');
INSERT INTO `jc_content_picture` VALUES (338,1,'/u/cms/www/201112/19155034wvyj.jpg','');
INSERT INTO `jc_content_picture` VALUES (338,2,'/u/cms/www/201112/19155038k2do.jpg','');
INSERT INTO `jc_content_picture` VALUES (338,3,'/u/cms/www/201112/191550415nco.jpg','');
INSERT INTO `jc_content_picture` VALUES (339,0,'/u/cms/www/201112/191553100g34.jpg','');
INSERT INTO `jc_content_picture` VALUES (339,1,'/u/cms/www/201112/19155314sxs8.jpg','');
INSERT INTO `jc_content_picture` VALUES (339,2,'/u/cms/www/201112/19155317zg9k.jpg','');
INSERT INTO `jc_content_picture` VALUES (339,3,'/u/cms/www/201112/191553207vdb.jpg','');
/*!40000 ALTER TABLE `jc_content_picture` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_tag
#

DROP TABLE IF EXISTS `jc_content_tag`;
CREATE TABLE `jc_content_tag` (
  `tag_id` int(11) NOT NULL auto_increment,
  `tag_name` varchar(50) NOT NULL COMMENT 'tag名称',
  `ref_counter` int(11) NOT NULL default '1' COMMENT '被引用的次数',
  PRIMARY KEY  (`tag_id`),
  UNIQUE KEY `ak_tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='CMS内容TAG表';

#
# Dumping data for table jc_content_tag
#

LOCK TABLES `jc_content_tag` WRITE;
/*!40000 ALTER TABLE `jc_content_tag` DISABLE KEYS */;
INSERT INTO `jc_content_tag` VALUES (1,'2011',1);
INSERT INTO `jc_content_tag` VALUES (2,'中国',1);
INSERT INTO `jc_content_tag` VALUES (9,'机构',0);
INSERT INTO `jc_content_tag` VALUES (16,'基金',0);
INSERT INTO `jc_content_tag` VALUES (40,'汤玉祥',1);
INSERT INTO `jc_content_tag` VALUES (41,'轿车',1);
INSERT INTO `jc_content_tag` VALUES (42,'Facebook',1);
INSERT INTO `jc_content_tag` VALUES (43,'投资',1);
INSERT INTO `jc_content_tag` VALUES (44,'新年',1);
INSERT INTO `jc_content_tag` VALUES (45,'李开复',1);
INSERT INTO `jc_content_tag` VALUES (46,'李瑜',1);
INSERT INTO `jc_content_tag` VALUES (47,'王建硕',1);
INSERT INTO `jc_content_tag` VALUES (48,'网站',1);
INSERT INTO `jc_content_tag` VALUES (49,'规模化',1);
INSERT INTO `jc_content_tag` VALUES (52,'调控',0);
INSERT INTO `jc_content_tag` VALUES (53,'121',1);
/*!40000 ALTER TABLE `jc_content_tag` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_topic
#

DROP TABLE IF EXISTS `jc_content_topic`;
CREATE TABLE `jc_content_topic` (
  `content_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  PRIMARY KEY  (`content_id`,`topic_id`),
  KEY `fk_jc_content_topic` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS专题内容关联表';

#
# Dumping data for table jc_content_topic
#

LOCK TABLES `jc_content_topic` WRITE;
/*!40000 ALTER TABLE `jc_content_topic` DISABLE KEYS */;
INSERT INTO `jc_content_topic` VALUES (47,3);
INSERT INTO `jc_content_topic` VALUES (48,3);
INSERT INTO `jc_content_topic` VALUES (49,3);
INSERT INTO `jc_content_topic` VALUES (57,1);
INSERT INTO `jc_content_topic` VALUES (57,2);
INSERT INTO `jc_content_topic` VALUES (57,3);
INSERT INTO `jc_content_topic` VALUES (190,1);
INSERT INTO `jc_content_topic` VALUES (190,2);
INSERT INTO `jc_content_topic` VALUES (190,3);
INSERT INTO `jc_content_topic` VALUES (225,1);
INSERT INTO `jc_content_topic` VALUES (225,2);
INSERT INTO `jc_content_topic` VALUES (225,3);
INSERT INTO `jc_content_topic` VALUES (243,1);
INSERT INTO `jc_content_topic` VALUES (243,2);
INSERT INTO `jc_content_topic` VALUES (243,3);
INSERT INTO `jc_content_topic` VALUES (249,1);
INSERT INTO `jc_content_topic` VALUES (249,2);
INSERT INTO `jc_content_topic` VALUES (249,3);
INSERT INTO `jc_content_topic` VALUES (250,1);
INSERT INTO `jc_content_topic` VALUES (250,2);
INSERT INTO `jc_content_topic` VALUES (250,3);
/*!40000 ALTER TABLE `jc_content_topic` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_txt
#

DROP TABLE IF EXISTS `jc_content_txt`;
CREATE TABLE `jc_content_txt` (
  `content_id` int(11) NOT NULL,
  `txt` longtext COMMENT '文章内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容文本表';

#
# Dumping data for table jc_content_txt
#

LOCK TABLES `jc_content_txt` WRITE;
/*!40000 ALTER TABLE `jc_content_txt` DISABLE KEYS */;
INSERT INTO `jc_content_txt` VALUES (34,'<p>　　据新华社电 据中国空间技术研究院有关专家介绍，2013年，我国有望利用长征三号乙运载火箭发射自主火星探测器。我国航天工业部门已先期启动了基于探月一二期技术的自主火星探测器研究和方案设计工作，目前正在积极开展技术攻关。据悉，我国自主火星探测器的科学载荷重量达100公斤以上，科学探测能力将大大提高。</p>\r\n<p>　　火星作为太阳系中最近似地球的天体之一，对人类有一种天然的吸引力。火星探测是21世纪人类深空探测的重点之一，我国正在积极开展火星自主探测的相关研究。</p>\r\n<p>　　据介绍，2011年，我国将与俄罗斯合作共同探测火星。我国首个火星探测器&ldquo;萤火一号&rdquo;原计划于2009年10月和俄罗斯的&ldquo;福布斯－土壤&rdquo;卫星一起，搭乘&ldquo;天顶&rdquo;号运载火箭从拜科努尔航天中心发射升空，后因故推迟到2011年10月。</p>\r\n<p>　　目前，世界各主要航天国家都将深空探测视为重要战略性领域，各国未来深空探测规划涵盖整个太阳系。在世界各国未来探测规划中，较为关注的探测目标是月球、火星、金星和小行星。我国的深空探测活动起步于月球，到目前为止，我国共实施了两次月球探测。</p>\r\n<div style=\"height: 0px; clear: both; overflow: hidden\">&nbsp;</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (35,'\n<p>　　本报讯 (记者易靖)昨天，首都机场运送旅客14万人次。受暴雪天气影响，约30个飞赴欧美的航班延误。</p>\n\n<p>　　据了解，新年首日，首都机场运送旅客17万人次，与平时相比变化不大。多数旅客是从北京出发赴厦门、海南等地旅游。北京至海南航班机票早在一周前就已经全部售完。昨天，首都机场运送旅客14万人次。今天，预计运送旅客数量较多，为19万人次左右。</p>\n\n<p>　　虽然国内多个地区有雨雪天气，但首都机场国内航班未受影响。但受欧美部分地区暴雪影响，首都机场飞赴欧美部分航班受到影响。前天，25个飞往欧美航班被取消。昨天，飞往莫斯科、巴黎等地约30个航班延误。</p>\n\n<p>　　首都机场提醒旅客，去往欧美的旅客及时与航空公司联系，随时咨询最新消息，以免影响行程。</p> <div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\"></div>\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (36,'\n<p>　　据中央人民广播电台《金陵晚报》报道 从2010年12月30日到2011年1月2日凌晨，江苏省盐城市建湖县3天发生31起小地震，部分居民产生恐慌情绪。江苏省地震局2日就此表示，该地区近期没有发生大地震迹象，但不排除继续发生有感地震。</p>\n\n<p><strong>　　&gt;&gt;居民</strong></p>\n\n<p><strong>　　小腿随地面抖动</strong></p>\n\n<p>　　1月1日17时08分，建湖县高作镇的王金厚全家人正在吃晚饭，他突然感到地面在晃动，小腿也跟着抖起来。同时，他听到二楼邻居厨房间碗盆的撞击声，时间持续大约5秒钟。紧接着，楼上很多人往楼下冲，大家都有些慌张。发生地震后，很多居民不敢回家睡觉，一直在外面徘徊。</p>\n\n<p>　　据盐城地震台报告，这次地震为3.5级，造成当地部分群众有震感，震中在建湖县钟庄，盐城地震台电话接连不断。</p>\n\n<p><strong>　　&gt;&gt;地震局</strong></p>\n\n<p><strong>　　三天内地震31次</strong></p>\n\n<p>　　据江苏省地震局介绍，2010年12月30日23时57分，建湖钟庄发生ML2.1级地震。该局随即展开会商讨论，专家们认为，这次地震是正常的能量释放。到2011年1月1日，地震开始高频率出现，有感地震就发生了3次。截至2日下午，江苏省地震局没接到地震造成破坏的报告。</p>\n\n<p>　　江苏建湖位于江淮里下河平原地区。据盐城市地震局有关人士介绍，从2010年12月30日6时56分45秒至2011年1月2日6时32分24.2秒，建湖县境内钟庄连续发生31次地震，最大一次震级为里氏3.5级的有感地震，周边的草堰口、冈西、建湖县城有震感，这次连续小震属于钟庄小地震群，震级较小，没有造成人员伤亡及财产损失。尽管震级不大，但是当地居民还是产生了一些恐慌情绪。</p>\n\n<p><strong>　　系正常能量释放</strong></p>\n\n<p>　　地震为何震感如此明显？小地震为何高频率发生？江苏近期还会不会发生地震？江苏省地震局办公室主任龚寿荣2日就此做了解答。据龚寿荣介绍，“这次地震属于构造性地震，震中有可能位于洪泽沟敦断裂带上，这条断裂带在上世纪90年代多次发生过地震，其中最大的是4.8级。”小地震是正常能量释放，不具备破坏性，破坏性地震要达到5级以上。</p>\n\n<p>　　这次地震发生后，江苏省地震局经综合会商认为：这一地区虽有小地震发生，但地震活动强度弱，目前全省地震观测数据资料分析正常，近期该地区及全省不会发生破坏性地震。但专家也认为，该地区不排除会有有感地震的发生，他们会密切留意这次地震后的后续情况，“地震部门已经增加了流动地震监测点，也会在第一时间将有关监测情况向公众发布。”</p> \n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (37,'<p>　　近两日，贵州、湖南出现凝冻天气，公路路面大面积结冰，并对电网运行造成影响，贵州省绝大多数高速公路被迫关闭。万余人滞留贵州各客运站场。湖南境内往贵州方向车辆滞留约10公里，数千名驾乘人员被困。滞留广西南丹的车辆已达1500辆，受困群众增至近8000人。</p>\r\n<!-- 播放器 begin --><!-- flash player begin -->\r\n<p><style type=\"text/css\">\r\n\t\t\t\tp .contentPlayer{margin-top:10px;}\r\n\t\t\t\t.contentPlayer{float:left;width:336px;height:322px;background:url(http://i0.sinaimg.cn/cj/video_bg.png) no-repeat 0 0;margin:0 10px 10px -10px;*margin-right:7px;padding:1px 10px;_display:inline}\r\n\t\t\t\t.contentPlayer a{text-decoration:underline;font-size:12px!important;}\r\n\t\t\t\t.cp_player{padding:14px 0 0;text-align:center;height:249px;display:block;}\r\n\t\t\t\t.cp_tit{padding:10px 0 0 18px;font-size:12px!important;line-height:20px!important;display:block;}\r\n\t\t\t\t.cp_from{padding:0 0 0 18px;font-size:12px!important;line-height:20px!important;display:block;}\r\n\t\t\t\t</style><span class=\"contentPlayer\"><span class=\"cp_tit\"><a target=\"_blank\" style=\"font-weight: normal\" href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/185561225743.html\">视频：贵州瓮安路面结冰致交通陷入瘫痪</a></span> <span class=\"cp_from\" style=\"font-weight: normal\">来源：CCTV新闻频道</span> </span><script type=\"text/javascript\">\r\nvar sinaBokePlayerConfig_o = {\r\n\tcontainer: \"p_player\",  //Div容器的id\r\n\tplayerWidth:298,     //宽\r\n\tplayerHeight:250,    //高\r\n\tautoLoad: 0,        //自动加载\r\n\tautoPlay: 0,        //自动播放\r\n\tas: 0,              //广告\r\n\ttj:0             //推荐\r\n};\r\n</script><script src=\"http://v.sina.com.cn/js/pg/play/playflash.js\" type=\"text/javascript\"></script><!-- flash player end --><script language=\"javascript\" type=\"text/javascript\">\r\n\r\nsinaBokePlayerConfig_o.autoLoad = 0;\r\nsinaBokePlayerConfig_o.autoPlay = 0;\r\nSinaBokePlayer_o.addVars(\"vid\", 44490466);\r\nSinaBokePlayer_o.addVars(\"as\", 0);\r\nSinaBokePlayer_o.addVars(\"logo\", 0);\r\nSinaBokePlayer_o.addVars(\"pid\", 1);\r\nSinaBokePlayer_o.addVars(\"head\", 0);\r\nSinaBokePlayer_o.addVars(\"tjAD\", 0);\r\nSinaBokePlayer_o.addVars(\"tj\", 0);\r\n\r\n\tSinaBokePlayer_o.addVars(\"vblog\", 2);\r\n\tSinaBokePlayer_o.addVars(\"singleRss\", \"http://video.sina.com.cn/iframe/fourlists/p/news/c/v/2011-01-02/185561225743.xml\");\r\nSinaBokePlayer_o.showFlashPlayer();\r\n</script><!-- 播放器 end --></p>\r\n<p>　　启动凝冻灾害Ⅲ级响应</p>\r\n<p>　　2010年12月31日夜间至2011年1月2日8时，贵州中北部地区累计有62个县(市、区)出现冻雨，出现道路结冰和电线积冰。全省气温普遍在0℃以下。同时，湖南也有35县市出现冰冻，目前，灾情仍在持续加重。</p>\r\n<p>　　2日，贵州电网有45条110KV及以上线路覆冰厚度已达13毫米。目前，贵州500KV福青线和220KV习鸭线融冰装置已启动。</p>\r\n<p>　　贵州省2日启动凝冻灾害Ⅲ级应急响应。贵州省民政部门紧急向黔南州调拨救灾棉被1000床、棉衣1000件。</p>\r\n<p>　　贵州近2万人困在路途</p>\r\n<p>　　从1日开始，贵州省内的多条公路相继关闭。目前除镇胜高速仅部分路段封闭外，其他高速公路均已全部封闭。经交通部门初步排查，高速公路滞留车辆数千台、人员6200余人，此外国省干线还滞留至少600人，全省各客运站场滞留旅客万余人。</p>\r\n<p>　　贵州多个部门相继启动应急预案，已将数百名被困人员护送到附近的乡镇政府和服务区休息，并对凝冻路段和桥面进行防滑防冻处理。民政部门已在车辆滞留较多的贵新高速贵定段、马场坪段等设立了多个救助点，发放棉被、棉衣、矿泉水及食物等物资。</p>\r\n<p><strong>　　广西南丹车辆绵延15公里</strong></p>\r\n<p>　　截至2日17时，因贵新高速公路封路而滞留南丹的车辆已达1500辆，受困群众增至近8000人，被困车辆绵延15公里。</p>\r\n<p>　　目前南丹境内气温只有0℃-3℃，保障受困群众基本生活成为头等大事。南丹县委宣传部表示，对生活必需品，当地政府部门将&ldquo;能供应多少供应多少&rdquo;，然而，由于堵车路段较长，受困群众较多，&ldquo;供应难度非常大&rdquo;。</p>\r\n<p>　<strong>　沪昆高速湖南段严重堵车</strong></p>\r\n<p>　　因贵州境内高速公路封闭，导致贯穿中国东西部的公路交通主动脉沪昆(上海至昆明)高速湖南怀新(怀化至新晃)路段、新晃至贵州大龙段严重堵车，湖南境内往贵州方向车辆滞留约10公里，新晃至大龙段滞留车辆700余台。目前虽经两省路政部门积极疏导，但由于大雪和冻雨持续，交通状况依然严峻。</p>\r\n<p>　　截至2日20时，滞留的133台客运车辆、6000多名旅客已疏导至新晃县城。除部分车主因不愿意下行国道而仍然滞留高速公路以外，近600台货车已疏导疏散200多台。</p>\r\n<p>　　据新华社电</p>\r\n<!-- 2009-08-04 zhaokai begin -->\r\n<p><style type=\"text/css\">\r\n.blk-video{float:left;background:url(http://i0.sinaimg.cn/ent/deco/2009/0804/cv_m_02.png) no-repeat 0 0;height:89px;clear:left;}\r\n.blk-video-l{float:left;width:125px;overflow:hidden; text-align:center;padding:5px 2px 0 0;}\r\n.blk-video-l img{border:1px solid #d0ddee;padding:3px;background:#f7f7f9;}\r\n.blk-video-l a:hover img{border-color:#e55c5c;}\r\n.blk-video-r{float:left;padding:10px 15px 0 13px;background:url(http://i0.sinaimg.cn/ent/deco/2009/0804/cv_m_02.png) no-repeat 100% 0;height:79px;}\r\n.blk-v-tit{padding:5px 0 3px 2px ;line-height:20px;font-size:12px!important;float:left;clear:both;}\r\n.blk-v-tit a{text-decoration:underline;font-size:12px!important;}\r\n.blk-v-from{color:#000;line-height:18px;height:20px;font-size:12px!important;float:left;clear:both;}\r\na.blk-v-play,a.blk-v-play:visited{display:block; width:56px;height:18px;line-height:20px;padding:0 0 0 20px; background:url(http://i0.sinaimg.cn/dy/deco/2009/0629/picpic/vblue02.png) 0 0;color:#003a7f;text-decoration:none; white-space:nowrap;font-size:12px!important;float:left;clear:both;}\r\na.blk-v-play:hover{background-position:0 -100px; }\r\n.clearcl{clear:both;height:0;visibility:hiddden;overflow:hidden;}</style><br />\r\n&nbsp;</p>\r\n<div class=\"blk-video\">\r\n<div class=\"blk-video-l\"><a href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/185561225743.html\"><img alt=\"贵州湖南公路路面大面积结冰\" width=\"94\" height=\"71\" src=\"http://p.v.iask.com/526/4/44490466_0.jpg\" /></a></div>\r\n<div class=\"blk-video-r\">\r\n<div class=\"blk-v-tit\"><a href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/185561225743.html\">贵州瓮安路面结冰致交通陷入瘫痪 </a></div>\r\n<div class=\"blk-v-from\">来源：CCTV新闻频道</div>\r\n<a class=\"blk-v-play\" href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/185561225743.html\">播放视频</a></div>\r\n</div>\r\n<div class=\"clearcl\">&nbsp;</div>\r\n<!-- 2009-08-04 zhaokai end -->',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (38,'\n<p>　　晨报讯 (记者 王歧丰)随着首钢完成最后的搬迁工作，投资近20亿元的长安街西延长线建设将在今年正式启动，道路全长约6.4公里，沿线拟设9座公交站。</p>\n\n<p>　　根据规划，长安街西延项目东起古城南路，向西经现在的首钢东大门、首钢厂区后，跨永定河经西六环路继续向西，终点至门头沟的三石路。石景山区区长周茂非近日在接受央视采访时，将这一路段称为“新十里长街”，要按照现在东、西长安街的标准，建成五上五下的城市快速路。</p>\n\n<p>　　周茂非说，跨越永定河的一段道路，将会成为长安街西延长线上最美丽的景观之一，届时在长安街与永定河相交的莲石湖段，将修建一座大桥，目前初步选定“龙凤呈祥”的方案，分为上下两层，上面是机动车通行，下面是人行通行。周茂非同时表示，未来的长安街西延线，不仅会成为北京旅游的新亮点，而且会成为一条财富大道，“新十里长街”两侧的土地资源，将释放出巨大的发展空间。</p>\n\n<p>　　记者还了解到，按照规划，长安街西延线拟定9座公交站。横跨永定河大桥的两个站点之间距离最长，达到1880米；而两站间距离最小的只有420米。在门头沟滨河路南，还将设置一座公交车站，接驳S1线。此外，根据道路宽度和车道数量，有望划定公交专线。</p> <div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\"></div>\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (39,'<p>　　晨报讯(记者 孙韬) 北京小伙娶上海或陕西姑娘，双方婚姻状况信息将上网可查。记者昨天从民政部获悉，&ldquo;十二五&rdquo;期间，<a target=\"_blank\" href=\"http://news.sina.com.cn/c/2011-01-03/011021749667.shtml\">婚姻登记信息将实现全国联网</a>，目前相关软硬件设施等正在逐步完备。北京、上海、陕西三地将从今年起先行试点。</p>\r\n<p>　　民政部日前召开婚姻登记规范化视频会议。会上透露，截至目前，全国多个省市婚登机关已实现婚姻登记网上预约、在线查询、婚姻档案信息检索等功能。婚姻登记信息实现省级联网的省份已由2005年的2个增加到现在的23个，省级联网率达到了74%。&ldquo;十二五&rdquo;期间，婚姻登记信息将实现全国联网。</p>\r\n<p>　　从今年起，京沪陕三地的婚姻登记信息将率先实现共享。也就是说，如果北京人与上海人或陕西人两地联姻，可以首先在网上查到对方婚姻的真实状况。同时，未来5年，本市户籍人口的婚姻状况信息都将被输入北京市婚姻登记信息数据库内。</p>\r\n<p>　　据民政部相关负责人介绍，目前已开发完成了&ldquo;全国婚姻登记管理信息系统&rdquo;和&ldquo;部、省两级婚姻登记数据交换系统&rdquo;，初步建成民政部婚姻登记数据中心，启动了部省两级婚姻信息实时交换与共享的试运行工作，为&ldquo;十二五&rdquo;实现全国联网打下基础。</p>\r\n<p>　　另据媒体报道，我国很多省市婚姻登记机关目前仍沿用旧的统计模式，部分省市的婚姻登记电脑软件仅局限于一般的办公自动化，各级婚姻登记机关独立操作、自成体系，未形成省级婚姻登记网络系统。</p>\r\n<div style=\"height: 0px; clear: both; overflow: hidden\">&nbsp;</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (40,'\n<p>　　近两日来，贵州出现凝冻天气，公路路面大面积结冰，并对电网运行造成影响，贵州省绝大多数高速公路被迫关闭，导致高速公路及全省各客运站场滞留旅客4万余人。为此，贵州省2日启动凝冻灾害Ⅲ级应急响应。</p><!-- 播放器 begin -->\n<!-- flash player begin -->\n\n\t\t\t\t<style type=\"text/css\">\n\t\t\t\tp .contentPlayer{margin-top:10px;}\n\t\t\t\t.contentPlayer{float:left;width:336px;height:322px;background:url(http://i0.sinaimg.cn/cj/video_bg.png) no-repeat 0 0;margin:0 10px 10px -10px;*margin-right:7px;padding:1px 10px;_display:inline}\n\t\t\t\t.contentPlayer a{text-decoration:underline;font-size:12px!important;}\n\t\t\t\t.cp_player{padding:14px 0 0;text-align:center;height:249px;display:block;}\n\t\t\t\t.cp_tit{padding:10px 0 0 18px;font-size:12px!important;line-height:20px!important;display:block;}\n\t\t\t\t.cp_from{padding:0 0 0 18px;font-size:12px!important;line-height:20px!important;display:block;}\n\t\t\t\t</style>\n\t\t\t\t<span class=\"contentPlayer\">\n\t\t\t\t\t<span class=\"cp_player\" id=\"p_player\"></span>\n\t\t\t\t\t\t\t\t\t\t<span class=\"cp_tit\"><a target=\"_blank\" href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/175661225713.html\" style=\"font-weight:normal;\">视频：贵州出现冻雨全省高速公路封闭</a></span>\n\t\t\t\t\t<span class=\"cp_from\" style=\"font-weight:normal;\">来源：CCTV新闻频道</span>\n\n\t\t\t\t</span>\n\n<script type=\"text/javascript\">\nvar sinaBokePlayerConfig_o = {\n\tcontainer: \"p_player\",  //Div容器的id\n\tplayerWidth:298,     //宽\n\tplayerHeight:250,    //高\n\tautoLoad: 0,        //自动加载\n\tautoPlay: 0,        //自动播放\n\tas: 0,              //广告\n\ttj:0             //推荐\n};\n</script>\n\n<script src=\"http://v.sina.com.cn/js/pg/play/playflash.js\" type=\"text/javascript\"></script>\n\n<!-- flash player end -->\n\n<script language=\"javascript\" type=\"text/javascript\">\n\nsinaBokePlayerConfig_o.autoLoad = 0;\nsinaBokePlayerConfig_o.autoPlay = 0;\nSinaBokePlayer_o.addVars(\"vid\", 44415891);\nSinaBokePlayer_o.addVars(\"as\", 0);\nSinaBokePlayer_o.addVars(\"logo\", 0);\nSinaBokePlayer_o.addVars(\"pid\", 1);\nSinaBokePlayer_o.addVars(\"head\", 0);\nSinaBokePlayer_o.addVars(\"tjAD\", 0);\nSinaBokePlayer_o.addVars(\"tj\", 0);\n\n\tSinaBokePlayer_o.addVars(\"vblog\", 2);\n\tSinaBokePlayer_o.addVars(\"singleRss\", \"http://video.sina.com.cn/iframe/fourlists/p/news/c/v/2011-01-02/175661225713.xml\");\nSinaBokePlayer_o.showFlashPlayer();\n</script>\n\n<!-- 播放器 end -->\n\n<p>　　此外，贵州高速关闭还导致沪昆高速湖南怀新路段严重堵车，湖南境内往贵州方向车辆滞留约6公里，目前当地政府部门正全力救援被困高速公路的数千名驾乘人员。因贵新高速公路封路而滞留广西南丹的车辆已达1500辆，受困民众增至七八千人，被困车辆绵延15公里。</p>\n\n<p>　　预计1月2日白天到3日夜间，贵州省中北部地区阴天有冻雨或雨夹雪，其余地区阴天有小雨，凝冻仍将维持。</p>\n\n<p><strong>　　【贵州】</strong></p>\n\n<p><strong>　　全省仅一条高速部分开放</strong></p>\n\n<p><strong>　　部分地区有冻雨，大部分地区普遍道路结冰</strong></p>\n\n<p>　　受北方强冷空气持续影响，1月1日8时至2日8时，贵州省中北部地区有62个(市、区)出现冻雨，全省大部分地区气温在零摄氏度以下，普遍出现道路结冰和导线积冰现象。</p>\n\n<p>　　从1日傍晚开始，贵州境内高速公路开始陆续关闭，目前除镇胜高速仅部分路段封闭外，其他高速公路均已全部封闭。经交通部门初步排查，高速公路滞留车辆数千台、人员6200余人，此外国省干线还滞留至少600人，全省各客运站场滞留旅客万余人。</p>\n\n<p>　　近两日来，贵州多个部门相继启动应急预案，同时采取紧急措施，全力疏散和救援高速公路被困人员。贵州各级路政部门利用路政巡逻车，已将数百名被困人员护送到附近的乡镇政府和服务区休息，并对凝冻路段和桥面进行防滑防冻处理。民政部门已在车辆滞留较多的贵新高速贵定段、马场坪段等设立了多个救助点，发放棉被、棉衣、矿泉水及食物等物资。</p>\n\n<p>　　记者从南方电网贵州电网公司了解到，全省电网运行及电力供应基本稳定。据新华社电</p>\n\n<p><strong>　　【湖南】</strong></p>\n\n<p><strong>　　700多辆车半路被困高速</strong></p>\n\n<p><strong>　　6000多名旅客被疏导，数百货车仍停高速</strong></p>\n\n<p>　　1日夜间至2日凌晨，因贵州境内高速公路结冰严重，临时交通管制，封闭了与湖南交界的贵州一侧收费站，导致贯穿中国东西部的公路交通主动脉沪昆(上海至昆明)高速湖南怀新(怀化至新晃)路段严重堵车，湖南境内往贵州方向车辆滞留约6公里，目前虽经两省路政部门积极疏导，但由于大雪和冻雨持续，交通状况依然严峻。</p>\n\n<p>　　截至2日20时，滞留的133台客运车辆6000多名旅客已疏导至新晃县城。除部分车辆车主因不愿意下行国道而仍然滞留高速公路以外，近600台货车已疏导疏散200多台，预计至3日凌晨可疏导300多台。对滞留的司乘人员，该县组织了慰问组为他们送去矿泉水2000瓶、鸡蛋3000个、面包3000份、方便面1500盒等食品。</p>\n\n<p>　　1月2日8时，新晃县迅速启动应对冰冻雨雪应急预案。针对沪昆高速公路上滞留车辆问题，新晃专门成立应急处置小组，从2日早晨开始，该县协调沪昆高速公路新晃交警大队和县内交警、交通、公路等相关部门人员40余人，出动疏导车辆8台，对滞留新晃境内的司乘人员和旅客开展动员，疏导车辆从高速公路下行至320国道或城郊。据新华社电</p>\n\n<p><strong>　　【广西】</strong></p>\n\n<p><strong>　　南丹被困车辆绵延15公里</strong></p>\n\n<p><strong>　　受困者约八千人，当地免费发放食品</strong></p>\n\n<p>　　记者从广西壮族自治区河池市南丹县委宣传部获悉，截至2日17时，因贵新高速公路封路而滞留南丹的车辆已达1500辆，受困群众增至七八千人，被困车辆绵延15公里。当地政府正采取多项措施保障受困群众基本生活需要。</p>\n\n<p>　　受北方强冷空气影响，目前南丹境内气温只有0—3摄氏度，保障受困群众基本生活成为头等大事。为此，南丹县交通、公安、民政、医卫等各有关部门紧急联动，组织党员赶赴现场，维护车辆秩序，提供医疗服务，免费为受困群众发放食品和御寒衣物。</p>\n\n<p>　　南丹县委宣传部表示，对生活必需品，当地政府部门将“能供应多少供应多少”，然而，由于堵车路段较长，受困群众较多，“供应难度非常大”。据新华社电</p> <!-- 2009-08-04 zhaokai begin -->\n\n<style type=\"text/css\">\n.blk-video{float:left;background:url(http://i0.sinaimg.cn/ent/deco/2009/0804/cv_m_02.png) no-repeat 0 0;height:89px;clear:left;}\n.blk-video-l{float:left;width:125px;overflow:hidden; text-align:center;padding:5px 2px 0 0;}\n.blk-video-l img{border:1px solid #d0ddee;padding:3px;background:#f7f7f9;}\n.blk-video-l a:hover img{border-color:#e55c5c;}\n.blk-video-r{float:left;padding:10px 15px 0 13px;background:url(http://i0.sinaimg.cn/ent/deco/2009/0804/cv_m_02.png) no-repeat 100% 0;height:79px;}\n.blk-v-tit{padding:5px 0 3px 2px ;line-height:20px;font-size:12px!important;float:left;clear:both;}\n.blk-v-tit a{text-decoration:underline;font-size:12px!important;}\n.blk-v-from{color:#000;line-height:18px;height:20px;font-size:12px!important;float:left;clear:both;}\na.blk-v-play,a.blk-v-play:visited{display:block; width:56px;height:18px;line-height:20px;padding:0 0 0 20px; background:url(http://i0.sinaimg.cn/dy/deco/2009/0629/picpic/vblue02.png) 0 0;color:#003a7f;text-decoration:none; white-space:nowrap;font-size:12px!important;float:left;clear:both;}\na.blk-v-play:hover{background-position:0 -100px; }\n.clearcl{clear:both;height:0;visibility:hiddden;overflow:hidden;}\n</style>\n<br>\n<div class=\"blk-video\">\n\t<div class=\"blk-video-l\">\n\t\t<a href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/175661225713.html\"><img alt=贵州全省仅一条高速部分开放 src=\"http://p.v.iask.com/775/854/44415891_0.jpg\" width=\"94\" height=\"71\" alt=\"贵州出现冻雨全省高速公路封闭\n\" /></a>\n\t</div>\n\t<div class=\"blk-video-r\">\n\t\t<div class=\"blk-v-tit\"><a href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/175661225713.html\">贵州出现冻雨全省高速公路封闭\n</a></div>\n\n\t\t<div class=\"blk-v-from\">来源：CCTV新闻频道</div>\n\t\t<a href=\"http://video.sina.com.cn/p/news/c/v/2011-01-02/175661225713.html\" class=\"blk-v-play\">播放视频</a>\n\t</div>\n</div>\n<div class=\"clearcl\"></div>\n\n<!-- 2009-08-04 zhaokai end -->\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (41,'\n<p>　　据新华社电 曾注明商品房限购政策“有效期限”至2010年12月31日的福州、厦门等部分城市日前决定，将顺延执行此项政策。业内人士及专家表示，政府继续“给力”限购在意料之中，而要切实缓解楼市反弹压力，则亟待扩大楼市供应。</p>\n\n<p><strong>　　多地“限购令”已到期</strong></p>\n\n<p>　　“限购令”首次登上房地产调控舞台是在2010年4月底北京出台的“国十条实施细则”中，随后有多个城市跟进出台了限购政策。其中，福州、厦门、海口、温州等部分城市在这项政策中都注明了“有效期限”至2010年12月31日。</p>\n\n<p>　　在过去一年里，“限购”加上“限贷”等调控组合拳很大程度上抑制了部分城市房地产的成交量。“但应引起注意的是，福州的房价并没有稳住，不少楼盘售价一涨再涨。部分原因是，大家都在等着政策到期后成交量的反弹。”福州大学房地产研究所所长王阿忠说。</p>\n\n<p>　　2010年10月10日，福州楼市正式出台了限制政策，对一手商品房执行“一户限购一套”等措施，受此影响，福州市一手房的实际签约量整体急速下滑，从10月份的3000多套，到11月大跌至1300多套，12月再跌至1200多套，而房价却一直稳居于每平方米1.2万元到1.4万元的高位。</p>\n\n<p><strong>　　房价存上涨冲动</strong></p>\n\n<p>　　“限购令”的顺延执行给开发商泼了一盆冷水。去年底，市场形势似乎逐渐“暖”了起来，福州、厦门等地不少开发商借着限购即将解禁之机，纷纷推出大批新房源，同时打出岁末优惠牌，打算提前“捂热”春节楼市。</p>\n\n<p>　　业内人士指出，如果“限购令”真的被解禁了，楼市大幅反弹的可能性就会很大。目前，限购城市不仅面临着量的反弹压力，房价也存在上涨的冲动。为此，地方政府除继续“给力”限购政策外，还须切实扩大楼市供应以及调整供应结构，继续加大保障房的建设和供应。</p> <div style=\"margin-right: 0px; margin-left: 0pt; padding-right: 0px;\" class=\"blkComment otherContent_01\"></div><div style=\"overflow:hidden;zoom:1;\" class=\"otherContent_01\">\n<p>　> <strong>相关报道：</strong></p><p>　　<a href=http://news.sina.com.cn/c/2010-12-31/112321741325.shtml target=_blank>住建部：热点城市房屋限购令将加强</a></p>\n<p>　　<a href=http://news.sina.com.cn/c/2010-12-31/073221739676.shtml target=_blank>多城市房地产限购令将到期 调控效果遭质疑</a></p>\n<p>　　<a href=http://news.sina.com.cn/c/2010-12-28/101821719712.shtml target=_blank>深圳规划国土委否认将取消住房限购令</a></p></div>\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (42,'<p>　　&ldquo;我们现在被困在秀山县雅江镇至贵州省松桃县路途当中，快来救救我们吧。&rdquo;1日晚11时50分许，秀山消防接到了这样的报警。8名消防官兵接报后紧急赶往现场，在昨日凌晨1时许与报警者会合。</p>\r\n<p>　　据现场李女士介绍，他们是从重庆到湖南省凤凰古镇的自驾旅行团，车队有15辆小车，团员有40余名，都是重庆市民。本来出行倒还顺利，可谁都没有想到晚上天气寒冷，道路竟然开始结冰了。1日晚上8点多，车队行驶到秀山县雅江镇至贵州松桃县芽驾路段时，行驶在最前面的几辆车突然打滑，车辆掉到路边的排水沟内，无法动弹。</p>\r\n<p>　　被困游客告诉消防官兵，当时夜间气温突降至零下3℃左右，车队停在公路上进退两难，无奈之下，他们只好拨打了求救电话。</p>\r\n<p>　　目前，受困的40余市民已全部安全获救。</p>\r\n<p>　　昨日，记者从秀山当地交通部门获悉，重庆秀山至湖南凤凰路段已经结冰，提醒自驾游市民前往时务必谨慎。</p>\r\n<p>　　另外，1月1日，第一场冰雪突袭武陵山区，黔江区319线国道黔江至彭水路段的香山隧道路段、沙坝路段、黔洲桥平台路段积雪特别严重，路面全面霜冻。黔江交巡警支队紧急安排疏导，并用沙子、干稻草、工业盐、铲车铲除阻路的冰雪，确保了道路畅通。</p>\r\n<p>　　记者 夏祥洲 通讯员 任燕 摄影报道</p>\r\n<p>　　道路结冰第二次黄色预警</p>\r\n<p>　　气象局每三小时有滚动气象消息</p>\r\n<p>　　本报讯 记者从相关部门了解到，昨天有多条高速公路因为冰雪受到了管制。G65(包茂渝湘段)主线站&mdash;&mdash;&mdash;南川、彭水东&mdash;&mdash;&mdash;洪安已双向管制。G42(沪蓉)云阳&mdash;&mdash;&mdash;巫山管制。G75(兰海渝黔段)因协助贵州管制，出城车辆在东溪下道，出城货车在G75主线站分流，东溪、安稳出城方向入口管制。此外，G42(沪蓉)万州&mdash;&mdash;&mdash;开县也因冰雪双向管制。</p>\r\n<p>　　昨日，气象部门继续发布今年第二个道路结冰黄色预警，预计从昨天20时到3日14时，我市黔江、酉阳、秀山、石柱、武隆、彭水、南川、城口、巫溪、巫山、奉节、云阳、开县、梁平、垫江等地以及其余区县海拔600米以上的山区将出现道路结冰。</p>\r\n<p>　　市气象局从昨天上午11点起启动了3小时一次滚动预报，时间为11点、14点、17点、20点。市民出行前，可以拨打相关服务电话(12121和96121声讯)咨询路况和天气。记者 朱隽</p>\r\n<p>　　贵州湖南继续大范围冻雨</p>\r\n<p>　　贵州境内所有高速均封闭，由湘入黔车辆滞留约10公里，由桂入黔数千人滞留南丹</p>\r\n<p>　　1月1日至2日早晨，贵州、湖南等地持续出现雨雪天气，贵州大部、湖南西部等地出现大范围冻雨。截止到1月2日8时，贵州省中北部地区电线积冰直径普遍有25～40毫米，湘西冻雨厚度在3～12毫米。</p>\r\n<p>　　贵州所有高速公路因道路结冰实行临时交通管制，目前滞留在贵州高速公路上的车辆有数千台，乘客有6200余人。此外，由于贵州方面封闭210国道线毗邻广西路段，近千车辆滞留南丹境内，四五千人被困。湖南省湘西、湘北部分地区公路结冰严重，主要集中在怀化、湘西自治州、张家界和岳阳的部分路段，由湘入黔车辆滞留约10公里。</p>\r\n<p>　　预计未来两天贵州、湖南等地仍将出现冻雨天气。其中，2日，贵州大部、湖南西部和南部地区将有冻雨；3日，贵州中部、湖南西部偏南地区和南部仍然有冻雨；4日，本次冻雨天气基本结束。中国天气网</p>\r\n<p>　　长江中下游将现大范围雨雪</p>\r\n<p>　　随着冷空气东移南下，长江中下游地区将迎来大范围雨雪天气，降温加剧。</p>\r\n<p>　　在新一轮冷空气的影响下，新年伊始，全国大部地区的气温呈直线下降。截至1日下午2时，新疆北部、西藏西部、内蒙古东北部、黑龙江西部、吉林西部、辽宁中西部、四川盆地、贵州、重庆、湖北及江南大部普遍降温3摄氏度至8摄氏度。</p>\r\n<p>　　中央气象台预计，未来两天，青藏高原东部、西南地区东部等地有小到中雪(雨)或雨夹雪，其中，贵州东北部、湖南中部偏西的局部地区有大雪。新华社</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (43,'<p>　　本报讯(记者沈义)记者日前从重庆市检察院获悉，2010年，该市检察机关积极投入平安重庆建设，始终保持对黑恶犯罪的高压态势，在严厉打击黑恶势力犯罪的同时，深挖彻查黑恶势力背后的职务犯罪。该市三级检察机关依法批捕涉黑涉恶犯罪嫌疑人920人、起诉1219人，打掉43个涉黑团伙，深挖彻查黑恶势力背后的职务犯罪143件172人，其中&ldquo;保护伞&rdquo;47人。</p>\r\n<div style=\"height: 0px; clear: both; overflow: hidden\">&nbsp;</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (44,'\n<p>　　本报讯&nbsp;(记者杜丁)北京市城管执法局官方网站(www.bjcg.gov.cn)改版升级，“市民评城管”栏目被放在了网站的显著位置。市民不仅可以提意见，而且可以随时看到其他网友参与评说的结果。</p>\n\n<p>　　在“您最希望城管队员解决哪些问题”中，栏目根据城管的职责和人们关心的话题给出了10个选项，违法建设、无照经营、夜间烧烤、乱发小广告、加强执法风纪方面的监督……市民可以根据自己的意见进行选择；“您对城管工作的评价”分成了满意、基本满意、不满意3档；栏目还设置了给城管工作提建议和意见的对话框，市民可以直接通过网络提出自己对城管的看法和改进建议。</p>\n\n<p>　　市民不仅可以参与对城管的评价，还能查看别人参与的情况。记者注意到，随着小广告治理力度的加大，乱贴小广告已降到了城管最该解决问题的第6位，无照经营、违法建设和夜间烧烤位列前3，黑车经营、施工扰民的名次都“跑”到了小广告的前面，它们都是市民眼中最需要解决的问题。</p> <div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\"></div>\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (45,'\n<p>　　晨报讯 (记者 孙韬)今后，本市低保政策将引入财产审核制度。记者日前从市民政局获悉，民政部门将考虑通过与金融、公安、建设等部门建立联动机制，对低保人员的房产、汽车、存款等财产情况进行调查，并纳入低保审核指标。</p>\n\n<p>　　据市民政局有关负责人介绍，目前本市低保资格的审查主要通过收入来衡量，低保人员拥有的汽车、房屋等财产情况，由于缺乏相应的政策和法律依据，民政部门尚无途径掌握。</p>\n\n<p>　　此外，针对一些低保人员存在弹性就业、反复就业等情况，虽然民政部门对低保家庭的收入会定期进行动态审核，但对隐性收入无从核查。</p>\n\n<p>　　据悉，民政部门已开始对低保户家庭存在拥有房、车和隐性收入等情况进行关注。</p> <div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\"></div>\n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (46,'\n<p>　　■《在押未决人员羁押表现评鉴制度实施办法》提出，法院依据鉴定表中反映的被告人在未判决前在押期间的表现，判定其是否具有从轻或从重情节，而增、减刑的幅度控制在三个月内。</p>\n\n<p>　　■在押未决人员的考评程序非常严格，首先是看守所开考评会议，驻所检察人员依法监督全过程；具体填写向法院递交的《在押未决人员月考评情况汇总表》时，必须有五个方面的意见，即管教民警、县看守所、县公安局、县检察院驻所检察室、县检察院，缺一不可。</p>\n\n<p>　　因涉嫌重大责任事故罪而被刑事拘留的黄某，日前领到了重庆市荣昌县法院的判决书。由于他在判决前的羁押期内表现良好，黄某在原判决的基础上，获得了三个月的减刑。&nbsp;</p>\n\n<p>　　对于在押未决人员的奖惩，我国目前没有明确的法律规定，而这恰恰成为在押未决人员难管的重要原因之一。&nbsp;</p>\n\n<p>　　为解决这一难题，荣昌县检察院在当地政法委主持下，与该县法院、公安局、司法局联合制定了在押未决人员羁押表现评鉴制度。黄某正是受益于此项制度的人员之一。&nbsp;</p>\n\n<p>　　截至目前，荣昌县看守所已有包括黄某在内的5名在押人员，由于在羁押期间表现良好，获得依法轻判。</p>\n\n<p>　　法律“留白”致在押未决人员“难管”</p>\n\n<p>　　在押未决人员羁押表现评鉴制度的出台，并非一时心血来潮的草率决定，而是荣昌县检察院驻所检察室主任张长久在日常工作中的细心观察与分析的结果。&nbsp;</p>\n\n<p>　　在驻所检察室工作期间，张长久发现，从被关进看守所到判决确定的这段时间，是在押人员等待判决结果的“过渡期”，也是情绪最不稳定期。冲动、易怒、抗拒……在这些负面情绪的驱使下，在押人员在看守所内不服监管、欺压打骂同监人员，充当“牢头狱霸”、打架斗殴的现象时有发生。&nbsp;</p>\n\n<p>　　张长久说，对于违反监规的在押人员，看守所依法能给予的处罚措施就是戴上戒具，最严厉的也不过是关短期禁闭。而这些处罚措施，对于关乎在押人员切身利益的判决来说，并无实质性影响。因此，部分在押人员对于自己在看守所是否受过处罚感到“无所谓”，而且即便处罚完毕后，他们中的多数人仍不吸取教训，继续打斗闹事，为监管场所的安全埋下了隐患。</p>\n\n<p>　　一支牙膏难以激发在押人员改过兴趣</p>\n\n<p>　　采访时，张长久对记者坦言，表现好的在押人员，看守所给予的奖励就是一支牙膏。“这怎么能激起这些‘问题人员’认真改过的兴趣？”张长久说。&nbsp;</p>\n\n<p>　　“我们看在眼里急在心上，总想着如何充分发挥检察职能，从根本上解决这一问题，进一步敦促在押未决人员遵守监规、积极改造。”荣昌县检察院检察长梁经顺向记者透露了出台羁押表现评鉴制度的初衷。&nbsp;</p>\n\n<p>　　他说，我国刑法中破坏监管秩序罪的主体限于已决犯，而对看守所中破坏监管秩序的在押人员，因其属于未决犯，难以追究其刑事责任，这样导致的后果是，在看守所中聚众闹事的在押人员，被关进监狱之后就变得“老实”多了。&nbsp;</p>\n\n<p>　　“同样一个人前后表现变化为何如此之大？原因就在于他们被判刑后，害怕在监狱中表现不好而失去减刑、假释的机会，甚至因触犯法律而被加刑。”梁经顺说，“于是，我们就考虑，只有将在押未决人员在羁押期间的表现纳入量刑情节，使他们在看守所里的一举一动都与日后的量刑息息相关，才能真正促使在押未决人员规范自己的言行，遵守监管秩序。”</p>\n\n<p>　　羁押表现纳入量刑情节符合立法精神</p>\n\n<p>　　通过反复分析论证和广泛征求意见，荣昌县检察院认为，将在押未决人员羁押表现纳入量刑情节，不仅在法理上可以找到依据，而且在实践中具有较强的操作性。&nbsp;</p>\n\n<p>　　梁经顺告诉记者，根据我国刑事立法精神，反映行为人人身危险性程度的事实，一般也可作为量刑情节予以考虑。&nbsp;</p>\n\n<p>　　梁经顺分析道，在押未决人员在看守所内的羁押表现，能有效反映在押未决人员在审判前的人身危险性，将其纳入酌定量刑情节，是根据在押未决人员人身危险性的消长变化而作出相适应刑罚的有效举措，符合刑罚的价值取向。&nbsp;</p>\n\n<p>　　“而且，在我国，犯罪嫌疑人审前羁押的期限一般较长，在这段期间内如不及时对在押未决人员进行教育改造，就有可能‘交叉感染’，相互传染恶习，增大其再次犯罪的可能性。如羁押表现可纳入量刑情节，可有效激励在押未决人员自觉遵守监规，认真改造。”梁经顺说。</p>\n\n<p>　　增、减刑幅度控制在三个月内</p>\n\n<p>　　2009年初，荣昌县检察院在当地政法委的主持下，与该县法院、公安局、司法局召开会议，提出了对在押未决人员羁押表现进行评鉴的构想，并对评鉴结果的使用时机和使用规定等做了大致的谋划。&nbsp;</p>\n\n<p>　　经过进一步的调研、论证，当年8月15日，荣昌县法院、检察院、公安局、司法局联合制定了《在押未决人员羁押表现评鉴制度实施办法》(下称《办法》)。该《办法》规定，羁押在荣昌县看守所的在押未决人员均要接受羁押表现评鉴，该县公安局负责具体的评鉴工作，并出具评鉴表，该县检察院驻所检察室则对评鉴过程进行同步监督。&nbsp;</p>\n\n<p>　　该《办法》提出，法院依据鉴定表中反映的被告人在未判决前在押期间的表现，判定其是否具有从轻或从重情节。若法院酌定被告人存在从轻情节，其将获得最多三个月的轻判奖励；反之，若被酌定具有从重情节，则被增加最多三个月的处罚。</p>\n\n<p>　　实施规则详细，考评程序严格</p>\n\n<p>　　荣昌县检察院副检察长魏东告诉记者，在押未决人员羁押表现评鉴制度规定得非常细致，细化和明确了加、减分标准，在实际操作中，监管人员只需依照规定填写报表即可。例如，在押未决人员个人考评基础分为100分，实行加分和扣分制，100分以上的为表现好，80分以上为一般，80分以下的为差。其中，加分标准有主动制止同监室在押人员打架斗殴、主动制止同监室在押人员自伤、自残、自杀、越狱、冲监、暴狱等8条；扣分标准则包括称王称霸、拉帮结伙、私立规矩，勒索、抢夺、强吃、强占他人财物，阻碍他人检举、举报，诬陷、报复检举人等24条。&nbsp;</p>\n\n<p>　　魏东说，尽管已规定了很详细的实施细则，对在押未决人员的考评程序依然非常严格。首先是看守所开考评会议，驻所检察人员依法监督全过程；具体填写向法院递交的《在押未决人员月考评情况汇总表》时，必须有五个方面的意见，即管教民警、县看守所、县公安局、县检察院驻所检察室、县检察院，缺一不可。&nbsp;</p>\n\n<p>　　为了更好地施行《办法》，当年8月试行时，荣昌县检察院还制定了《在押未决人员羁押表现考评细则》，明确了相关部门的评鉴职责和方法步骤及评鉴标准。</p>\n\n<p>　　违反监规的现象明显减少</p>\n\n<p>　　采访中，记者发现，黄某的刑事判决书上有这样几行字：“荣昌县公安局、荣昌县检察院评鉴，被告人黄某在羁押期间被评定为表现好……鉴于其在押期间表现好，有一定悔罪表现，依法酌定从轻处罚。”&nbsp;</p>\n\n<p>　　2009年9月，黄某因涉嫌重大责任事故罪被刑事拘留。据管教民警介绍，进入看守所后，他们就向黄某告知了羁押表现评鉴制度。在随后的羁押中，黄某感到非常后悔和自责，觉得对不起家庭，尤其是年迈的父母；主动与管教民警谈心，决心彻底痛改前非，重新做人。羁押期间，黄某严格遵守各项监规。荣昌县看守所对其进行考核时，确定其为“表现好”。&nbsp;</p>\n\n<p>　　荣昌县检察院将证明材料移送该县法院后，黄某因此获得酌定从轻的判决。黄某拿到判决书时，激动不已。&nbsp;</p>\n\n<p>　　“现在，几乎所有未决在押人员都明白，他们在押期间的表现不再无关紧要，尤其是对涉嫌轻微刑事犯罪的在押未决人员特别有吸引力，他们渴望能获得从轻量刑的机会。”张长久告诉记者，如今，看守所内监管秩序井然，在押未决人员对自己的日常表现很上心，连被子都叠得整整齐齐。&nbsp;</p>\n\n<p>　　记者了解到，自在押未决人员羁押表现评鉴制度推行以来，荣昌县看守所在押未决人员违反监规的现象明显减少。</p>\n\n<p>　　经验成熟后将适时推广</p>\n\n<p>　　记者了解到，由检察院、法院、公安局、司法局联合出台统一文件，规范在押未决人员羁押表现的制度在重庆市尚属首例，在全国也鲜见。&nbsp;</p>\n\n<p>　　全程参与该制度制定的重庆大学法学院副教授肖洪博士称，此制度对于感化教育犯罪嫌疑人、敦促其认罪服法，加强监管秩序，都有非常积极的意义。&nbsp;</p>\n\n<p>　　刑法学家、山东科技大学教授牛忠志认为，监所监督是检察权的有机组成部分，然而由于法制的不建全，监管工作出现了诸如“躲猫猫”、“喝水死”等事件，在全国造成恶劣的影响。该制度正是有效“治疗”这些“疾病”的良方，是对监所监管工作的积极探索。在重庆市范围内乃至在全国范围内都具有创新性，待经验成熟后，建议全国推广。&nbsp;</p>\n\n<p>　　据悉，重庆市检察院监所检察处近日专门到荣昌县检察院对在押未决人员羁押表现评鉴制度进行调研后表示，将在完善《办法》的基础上，适时将该制度在全市推广。</p> \n',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (47,'<p>　　据新华社电 新年首月天宇精彩纷呈。中科院紫金山天文台研究员王思潮向记者通报，2011年1月天宇将上演象限仪流星雨、日偏食、金星和水星西大距等四大天象。</p>\r\n<p>　　王思潮介绍，今年象限仪流星雨的极大很可能出现在北京时间1月4日9时，届时每小时的天顶流量(ZHR)将达到120，从中亚到整个欧洲，观测条件都十分理想。</p>\r\n<p>　　1月4日发生的日偏食于北京时间14时40分从非洲和欧洲西部开始，19时01分结束于我国西部的新疆、西藏、青海和甘肃的部分地区。我国只有极西部地区可以看到从初亏到复圆的全过程，而其他西部地区就只能看到带食日落了。</p>\r\n<p>　　今年1月8日，金星将到达西大距的位置。对于北纬40度附近地区来说，日出时金星的地平高度可达30度左右，观测条件非常好。</p>\r\n<p>　　今年水星将有7次大距，1月9日将要发生的西大距，日出时的地平高度约15度，观测条件不错。</p>\r\n<div style=\"height: 0px; clear: both; overflow: hidden\">&nbsp;</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (48,'<p>　　本报长沙讯 受北方冷空气影响，1月1日，雨雪主要出现在湘西北湘西这一带，自治州、张家界等17县市首先降下了新年的第一场雪。昨日上午8点，全省35县市出现雨雪冰冻天气，省气象局再次发布道路结冰橙色预警。鉴于防冰防冻形势趋于严峻，湘西州气象局已于昨日中午12点紧急启动了突发性气象灾害三级应急预案。</p>\r\n<p>　　受地面强冷空气的影响，湘西自治州从1月1日起就出现明显的低温雨雪冰冻天气，部分县市出现冻雨，极端最低气温达-2.6℃(保靖、花垣、凤凰)，最大积冰厚度12mm(凤凰县)，最大积雪深度3cm(保靖县)。</p>\r\n<p>　　据省气象台专家姚蓉分析：受分裂的南支低槽和分股南下的地面冷空气共同影响，未来几天我省将出现持续的雨雪天气，昨天和今天两天降雪较明显。今明两日省内气温明显偏低，日平均气温都在2℃以下，湘中以南地区有冰冻，这三天省内大部分路段将有道路结冰现象出现。</p>\r\n<p>　　另据最新资料分析，中低纬高空环流平直，多小波动，地面不断有冷空气渗透南下。预计：短中期冷空气继续渗透南下，省内阴雨雪天气维持，4日中低层转南风温度略微升高，湘中以南会出现雪转雨或雨夹雪，5日开始又一股冷空气渗透南下，5日晚开始又转纯雪或雨夹雪天气，6日-8日阴雨雪天气维持。</p>\r\n<p>　　具体天气预报：</p>\r\n<p>　　4日：湘中以南小到中雪转雨夹雪，局部地区有冰冻，其他地区阴天有小雪，最高气温0-2℃，最低气温-2-0℃；</p>\r\n<p>　　5日：湘北小到中雨夹雪转中雪，局部地区有大雪，其他地区阴天有小雨或小雨夹雪，湘中局部地区有冰冻；</p>\r\n<p>　　6-8日：受强冷空气影响，省内仍维持低温雨雪天气，冰冻又有发展。</p>\r\n<p>　　记者储文静 实习生张园　通讯员谭萍 李好</p>\r\n<p>　　[帮你问]</p>\r\n<p>　　这次雨雪会发展成类似2008年的冰灾？</p>\r\n<p>　　省气象局专家解释，目前冰冻主要在怀化、邵阳等湖南西部地区，湘东基本无冰冻，像长沙等地就直接下着雪或雨夹雪，形成冰冻所需的逆温层不够稳定，且逆温层不够厚，范围不够广，气温不能长时间维持在0℃以下，就无法达到冰冻形成的条件。而且虽然这次雨雪过程持续时间较长，但降水不强，属于过程性雨雪天气，中间有短暂升温，预计4日前后省内气温有所回升，届时冰冻将减弱或部分融掉，所以不会出现类似2008年的冰灾。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (49,'<p>　　对于盐城建湖地区的一些居民来说，这个新年过得并不平静。从2010年12月30日6时56分开始，该地区地震不断，最近的一次是昨天清晨6时32分，前前后后一共发生了31次地震。据省地震台网发布的消息，其中6次为有感地震，最高为里氏2.9级。昨天傍晚记者赶到震中&mdash;&mdash;建湖经济开发区钟庄社区办事处，发现总体上居民情绪比较平稳，只有几户居民已在昨晚搭起了防震棚，不过，这次属于小的地震群，即发生频率比较高，但该地区历史上最高地震级别仅为里氏5级。</p>\r\n<p>　　据了解，截至记者发稿，地震部门尚未接到地震造成的破坏损失报告。</p>\r\n<p>　　<strong>现场</strong></p>\r\n<p>　　<strong>县城居民夜里吓得跑出门</strong></p>\r\n<p>　　观察了一会，见没什么动静才回家，但直到零点以后才睡着，&ldquo;总是担心床会摇晃！&rdquo;</p>\r\n<p>　　昨天在网上出现这样一条消息，盐城市建湖县钟庄从12月30日晨到1月2日晨，先后发生了31次小地震。消息的来源据称是盐城市地震局。</p>\r\n<p>　　昨天下午，记者联系了盐城市地震局值班室，工作人员确认从12月30日6点56分45秒开始，钟庄境内发生第一次地震，一直到1月2日早晨6时32分24秒，一共发生31次地震。从震级来看，最高为ML3.5级(即江苏省地震局发布的M2.9级)，其中，ML2.7级以上地震为6次。</p>\r\n<p>　　昨天下午，记者电话采访了几位震中钟庄的居民，陈女士家住钟庄社区办事处马南组。她说，1月1日下午5点左右，她正在家里面，突然感觉到地面抖了一下，屋顶发出吱吱的声响，水泥地面也在晃动，她赶紧跑出去，发现邻居都已经冲出家门。</p>\r\n<p>　　&ldquo;怎么回事？&rdquo;大家互问，最后一致认定是遭遇地震。陈女士平时一般住在县城，此前并未感觉过震动，但是大家都反映，房子摇晃，而且在地震前听到一阵轰隆隆的巨响。</p>\r\n<p>　　在钟庄南边一处加油站，记者询问这几天的情况，工作人员称，震感最明显的还是1月1日下午5点左右那一次，而据地震台网消息，准确时间是下午5点07分，ML3.5级。&ldquo;当时震感挺强烈的，地面明显晃了一下。&rdquo;</p>\r\n<p>　　&ldquo;这些天到底震了多少次，我也说不清楚。&rdquo;一位女工作人员说，反正在夜里多次感觉到了，都不明显，一般在凌晨四五点，就好像是大货车经过时的震动一样，包括1月1日那次最强的地震，感觉就是震动了一秒左右就停止了。</p>\r\n<p>　　县城距离钟庄大约9公里，住在县城某小区的朱先生是个有心人，他说，从1月1日下午5点左右那一次地震开始，建湖县连续震了多次，晚上9点32分震了一次，1月2日清晨6点32分又震了一次。他说，在晚上9点多那次震过后，他吓得从四楼跑了下去，结果发现不少住户都出门了。</p>\r\n<p>　　当时夜间很冷，朱先生说，很多人都带了两件羽绒服，穿在身上御寒。观察了一会，没有再发生什么动静，他们才回家，但他直到零点以后才睡着，&ldquo;总是担心床会摇晃！&rdquo;</p>\r\n<p>　　昨晚6点多，记者途经县城时，发现城里秩序井然，已看不出任何地震的明显影响。路上的行人很少，绝大多数人已经因为寒冷的天气早早回到了家里。</p>\r\n<p>　　<strong>居民用木棍稻草搭起地震棚</strong></p>\r\n<p>　　在县电视台的有线节目上看到字幕，说是发生地震，大家一商量就搞了地震棚</p>\r\n<p>　　不过就在加油站附近的钟南生产队，记者看到有人家已经搭起了地震棚。居民孙景树家住在钟庄最南头，是两层的漂亮楼房，不过现在他和老伴带着四岁的孙女一起住进了昨天下午刚刚搭好的地震棚。</p>\r\n<p>　　记者看到，地震棚在屋子南头的麦地里，是用木棍、油毛毡等做架子，北面的风口堆放了大量的稻草，东西也是如此，南面是敞开的，孙景树说还来不及在南面挂个帘子。</p>\r\n<p>　　和他们一家一起准备凑合过一夜的还有两户邻居，一户是李大妈，带着小孙女，一户是住户郑琴。为了尽量取暖，地上铺了厚厚的稻草，记者坐上去感觉虽然毛糙一点，但是还比较暖和，盖上厚被子，不至于受冻。</p>\r\n<p>　　为什么要搭地震棚？郑琴说，其实此前他们还不知道有地震呢，直到昨天中午，在县电视台的有线节目上看到了字幕，说是发生地震，大家一商量就搞了地震棚。</p>\r\n<p>　　&ldquo;当然，也不是一点感觉没有。&rdquo;郑琴说，前天下午震了一次强烈的，孙景树的大孙女在服装厂上班，当时员工都跑出来了。到了夜里9点多，她正在看安徽卫视的跨年晚会，突然又震了，电视机都晃了一下。早晨出来和大家一说，就决定搭个棚子。</p>\r\n<p>　　李大妈的儿子在无锡打工，她说，儿子前天就从网上看到了地震的消息，当即打电话回来问，此后电话不断，他们住进棚子，也是让家人放心，&ldquo;大人倒无所谓，关键是孩子！&rdquo;</p>\r\n<p>　　孙景树说，这个棚子要住八九个人呢，大孙女自从1月1日下午那次强震之后，夜里就睡不着了。此外，郑琴有个13岁的儿子，夜里也要回来住，&ldquo;大家就这样挤着睡吧。&rdquo;</p>\r\n<p>　　<strong>村干部通知&ldquo;尽量不住楼房&rdquo;</strong></p>\r\n<p>　　&ldquo;村干部昨天上门通知，要求大家做一定的预防，搬到平房里去，这样更安全一些。&rdquo;</p>\r\n<p>　　孙景树说，这个地震棚确实好多年没搭过了，还是1991年左右搭过。住棚子无所谓，关键是不要大震，&ldquo;好好的家可不能震坏了。&rdquo;他说，好像听说这次要防震时间长一些，他们准备住半个月。</p>\r\n<p>　　附近的红旗西南生产队，据孙景树称，村干部昨天已经上门通知了，要求大家做一定的预防，主要是尽量不住楼房，搬到平房里去，这样更安全一些。</p>\r\n<p>　　记者随后来到孙景树家对面一家金惠源超市，开超市的姜大亮说，他们家也搭起了地震棚，暂时能住四五个人，&ldquo;不过如果真是有什么强震，可以避难啊，大家白天可以坐在里面，夜里也一样，至少可以遮风避雨。&rdquo;</p>\r\n<p>　　姜大亮说，自己特别关注这次地震，目前他也从网上得知发生31次小震，不过他说，昨天白天11点左右，又发生一次地震，大家都感觉到了，所以现在已经不止31次了。</p>\r\n<p>　　&ldquo;小地震不可怕，可是这么频繁，就有点让人担心了。&rdquo;姜大亮说。而其他居民也提到，都有点纳闷，为什么总是震，&ldquo;不是说地震前有地震云吗，我们没看到啊！&rdquo;</p>\r\n<p>　　<strong>发布</strong></p>\r\n<p>　　地震发生后，江苏省地震局的专家们紧急进行了地震会商，得出的结果是：近期该地区没有发生破坏性地震的前兆异常，但不排除该地区在近期发生有感地震。</p>\r\n<p>　　据江苏省地震局副巡视员龚寿荣介绍，地震一般在5级或5级以上才叫破坏性地震，而一般3级左右，称为有感地震。有感地震在全国大中城市行政区域内发生并不鲜见，市民大可不必多虑。&ldquo;这次是正常的能量释放，不具备破坏性。&rdquo;</p>\r\n<p>　　<strong>建湖县政府：</strong></p>\r\n<p>　　<strong>没造成任何伤害和损失</strong></p>\r\n<p>　　2011年1月1日10时57分，在江苏省盐城市建湖县(33.56&deg;，119.88&deg;)发生M2.7级地震。</p>\r\n<p>　　2011年1月1日17时07分，在江苏省盐城市建湖县(33.56&deg;，119.84&deg;)发生M2.9级地震。</p>\r\n<p>　　2011年1月2日1时11分，在江苏省盐城市建湖县(33.55&deg;，119.85&deg;)发生M2.5级地震。</p>\r\n<p>　　&hellip;&hellip;</p>\r\n<p>　　很多人都沉浸在新年的美好中，但不期而遇的地震，却让盐城建湖一带的居民不安。尽管都是小地震，但大家还是难免恐慌。据了解，从2010年12月30日清晨一直到昨日清晨，建湖地区已经发生31次地震，其中6次为有感地震。而6次有感地震中，里氏(M)2.3级地震2次，里氏(M)2.5级地震2次，里氏(M)2.7级地震一次，里氏(M)2.9级地震一次。1月1日，是有感地震次数最多的一天，一共发生了3次，最大一次就是前天17：07发生的里氏(M)2.9级地震，震中都在建湖县境内的钟庄(原钟庄镇)，周边的草堰口、冈西、建湖县城有震感。</p>\r\n<p>　　昨天记者从建湖县政府宣传部了解到，目前县里面高度重视此事，相关的措施都已采取。</p>\r\n<p>　　&ldquo;地震级别都不大，没有造成任何伤害和损失。&rdquo;宣传部的陈主任说，他自己也感觉到了几次，但都是很快就过去了。县城里也没有出现抢购食品等现象。</p>\r\n<p>　　&ldquo;目前政府也在关注下一步会怎么样，有没有更强的地震。&rdquo;陈主任说，昨晚县科委等部门从上级部门得到消息，目前来看，不排除继续发生有感地震，但是综合判断，目前没有发生破坏性地震的可能性。</p>\r\n<p>　　记者了解到，昨天省地震局和盐城市地震局的相关负责人已经带着监测设备赶到钟庄。昨晚记者在钟庄社区办事处内看到，一台监测仪器已经被布置在了办事处的围墙上，连着的数据线通到室内的一套设备上面，监测仪器看起来就像一个水杯，引得不少人进来观看。</p>\r\n<p>　　&ldquo;应该问题不大吧。&rdquo;不少住户表示，不相信建湖会发生大地震，所以基本上都没有搭地震棚的打算。</p>\r\n<p>　　<strong>江苏省地震局：</strong></p>\r\n<p>　　<strong>近期不会有破坏性地震</strong></p>\r\n<p>　　建湖发生地震后，江苏省地震局的专家立刻进行了紧急地震会商，虽然都是小地震，但专家们都特别关注，会议从1日下午一直持续到2日凌晨两点多。昨天下午，江苏省地震局又进行了第三次地震会商，结果是：目前没有监测到发生破坏性地震的前兆异常，&ldquo;近期不会发生破坏性地震，但不排除发生有感地震的可能。&rdquo;</p>\r\n<p>　　江苏省地震局副巡视员龚寿荣告诉记者，地震发生后，当地已经启动了地震预防预案。江苏省地震局的专家带着观测仪器赶到现场，在现场增加了流动观测仪器，同时，盐城地震局加强了24小时值班。今天一大早，江苏省地震局的监测、预报、通讯专家也将赶到一线，为当地带来更精确细密的监测。</p>\r\n<p>　　同时，地震局的专家还配合当地政府，向受到震慌的居民进行解释工作，也会在第一时间将有关监测情况向公众发布。</p>\r\n<p>　　<strong>史上曾发生&ldquo;几天几十次地震&rdquo;</strong></p>\r\n<p>　　盐城市位于华北地台和下扬子准地台的过渡地带，除周边的郯城－庐江深大断裂、海安－拼茶断裂和南黄海大断裂外，影响盐城市的主要断裂有盱眙－响水断裂、盐城－南洋岸断裂、洪泽－沟墩断裂、陈家堡－小海断裂、盐城－阜宁断裂、苏北滨岸断裂等。</p>\r\n<p>　　造成这次连发地震的是洪泽&mdash;沟墩断裂带，这条断裂带周边曾经多次发生地震，最密的时候，曾经在几天内连续发生几十次地震。</p>\r\n<p>　　龚寿荣介绍说，洪泽&mdash;沟墩断裂带上最大的一次地震是里氏5级左右，1991年和1992年曾经发生过里氏4.7级、4.8级地震。</p>\r\n<p>　　&ldquo;这次是正常的能量释放，不具备破坏性。&rdquo;龚寿荣告诉记者，目前建湖还没有发生房子遭破坏等任何由于地震带来的损失。&ldquo;虽然这一地区有小地震发生，但地震活动强度弱，目前全省地震观测数据资料分析正常，近期该地区及全省不会发生破坏性地震。&rdquo;</p>\r\n<p>　　<strong>■谣言顿起</strong></p>\r\n<p>　　<strong>&ldquo;地震&rdquo;</strong></p>\r\n<p>　　<strong>是钻探引发的？</strong></p>\r\n<p>　　在政府发布消息之前，网上早就有消息了。建湖居民姜大亮说，他一直在关注贴吧的建湖吧，从12月31日左右就有网友发布地震消息，到1月1日，一天之内震了10来次，网上开始沸腾。</p>\r\n<p>　　&ldquo;上面全是网友的留言，都是讲述地震感受的。&rdquo;姜大亮说，不少人都在交流感受，他也不断地用手机上网，并且参与讨论。</p>\r\n<p>　　1日晚上，姜大亮发现，贴吧网速已经很慢，说明上网的人非常多，他夜里睡不着，半夜里也挂在网上关注消息。</p>\r\n<p>　　在网友的交流中，有人还认为政府发布消息不够快，门户网站都转载网友的消息了，政府的地震台网还是没有任何消息，直到前天晚上才发布。</p>\r\n<p>　　据了解，在当地也出现了一些不靠谱的谣言，有人就称，昨天中午县有线电视台发布了预警消息，称下午5点05分会发生一次3级地震。不过对此，姜大亮表示根本不可能，而且当时电视上根本没有提到。&ldquo;怎么可能如此精确呢，地震预报还做不到啊！&rdquo;</p>\r\n<p>　　此前，在建湖曾流传，这不是地震，而是相邻的射阳等县最近勘探油田，是钻机钻探引发的，不过现在已经排除了这个说法。</p>\r\n<p>　　<strong>■网言网语</strong></p>\r\n<p>　　<strong>家中的老母</strong></p>\r\n<p>　　<strong>还好吗？</strong></p>\r\n<p>　　建湖贴吧这两天因一次小地震人气上升不少。</p>\r\n<p>　　人在遥远的南方，心在建湖。刚刚听说晚上可能有地震，很是担心家中的老母，希望知情人说说。</p>\r\n<p>　　地震了，绝对地震了，就在刚才二十秒之前。</p>\r\n<p>　　我们建湖是个风水宝地，无旱无涝又没得龙卷风，只是小地震而已。</p>\r\n<p>　　这几天吓死了！房子晃来晃去的！</p>\r\n<p>　　电脑晃了一下，整个楼都晃动了一下，吓死了，我在想不会是地震了吧，后来到网上一看，天啊，都震好几次了。</p>\r\n<p>　　建湖又地震了！!!好担心~~~ 希望家里人没事。</p>\r\n<p>　　我元旦没回去，对此一无所知，家里也没电话告诉我。</p>\r\n<p>　　是有人在钟庄那采石油？在外地的建湖人，心系家乡，求真相。摘自贴吧</p>\r\n<p>　　<strong>释疑</strong></p>\r\n<p>　　<strong>为啥震感较强？</strong></p>\r\n<p>　　<strong>盐城市地震局：震源较浅！</strong></p>\r\n<p>　　昨天下午，盐城市地震局值班人员表示，这次小的地震群发生，和地质构造有关系，因为建湖一带位于洪泽-沟墩断层上，历史上建湖发生的地震也与此有关，近期断层活动引起了地面的震动。而且这一次地震震源较浅，所以即使是ML2.7级以下的地震，也可能会有感受。</p>\r\n<p>　　另据了解，其实在江苏省地震台网发布消息的同时，相邻的山东省地震台网也在前天中午11点多发布过1日一次地震的测定数据。</p>\r\n<p>　　据了解，建湖县历史上地震并不多，这次地震的级别在当地地震局看来并不算高，此前1991年到1992年之间，曾发生过ML4.7级地震，当时也是地震群，即连续发生几十次小震，而历史上最高为5级。</p>\r\n<p>　　而2010年7月9日和7月19日，在江苏省南通市如东县附近海域(北纬32.5&deg;，东经121.6&deg;)10天内也连续发生两次3.8级地震。</p>\r\n<p>　　<strong>为啥晚上地震多？</strong></p>\r\n<p>　　<strong>省地震局：安静，容易感觉到！</strong></p>\r\n<p>　　江苏省地震局副局长张振亚介绍说，一般来说5级或5级以上的地震才称之为破坏性地震；而有感地震，与白天还是晚上，以及震中距离和震源深浅都有关系。&ldquo;白天大家都在工作，相对喧闹，不会那么敏感，但到了晚上，大家都睡觉了，很安静，对周边环境也特敏感；另外距离震中越近，越会有感觉，而震源越深，震级会越大，震级越浅，感觉也会更强烈一些。一般情况下3级左右的地震，称为有感地震；但如果距震中近，又是晚上，2级左右就会有感。&rdquo;</p>\r\n<p>　　<strong>最高是3.5级还是2.9级？</strong></p>\r\n<p>　　<strong>省地震局：同一震级的两种标度</strong></p>\r\n<p>　　据盐城地震局官网显示，2010年12月30日23：57，钟庄发生ML2.1级地震，12月31日5：02，钟庄发生ML2.4级地震，12月31日10：34，钟庄发生ML2.6级地震；2011年1月1日17：07，钟庄发生ML3.5级地震；1月1日18：26，冈西发生ML2.2级地震；1月1日21：33草堰口发生ML3.0级地震&hellip;&hellip;</p>\r\n<p>　　记者发现，同一个时间点内，也就是1月1日17：07，江苏省地震局台网对外公布的是里氏(M)2.9级地震，而盐城地震局对外公布的却是ML3.5级地震。&ldquo;当地居民每个人都知道是3.5级地震，怎么会是2.9级？究竟哪个更准确？&rdquo;对此，专家说，其实是一回事，是同一震级的两种标度。江苏省地震局用的是现在最常用的里氏测算发，也就是MS，是&ldquo;面波震级&rdquo;，而ML则是&ldquo;近震震级&rdquo;测算方法。</p>\r\n<p>　　据了解，这两种标准的差别在于，里氏是按照面源测定，即测定综合的地区比较广，而ML则是近源测定，即以靠近震中的烈度和强度等来测定震级，所以里氏震级一般要低于ML标准的，不过对于超过6级以上的地震，ML标准就不再能准确测定，有其局限性。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (54,'<p>接近欧盟外交高层的消息源12月30日对法国主流媒体透露，欧盟对华持续20多年的武器禁运，有望在2011年初取消。</p>\r\n<p>法国主流媒体《费加罗报》30日引述与欧盟外交政策负责人阿什顿女士关系密切的外交人士的话说，欧盟对华武器禁运令可能会&ldquo;很快到来&rdquo;。</p>\r\n<p>《费加罗报》分析认为，这意味着最早在2011年初武器禁运令可能就会取消。</p>\r\n<p>1989年美国和欧洲等西方国家开始对中国实施严格的武器禁运。</p>\r\n<p>该报援引这个消息源的话说，由于中国和美国以及欧洲国家很少有武器往来，并且中国在建设自身国防体系方面取得了很大成就，因此这项实施了20多年的禁运令实际上对中国并没有产生太大影响。</p>\r\n<p>媒体援引一份12月17日欧盟峰会后曝光的报告称，武器禁运已经成为欧洲和中国发展安全与外交关系中的一个&ldquo;主要障碍&rdquo;。</p>\r\n<p>报告还称，在此情况下，&ldquo;欧盟应该做出现实的判断，并且采取行动。&rdquo;</p>\r\n<p>今年9月，一度传出欧盟将解除对华武器禁运，但针对此问题，欧盟各成员国产生分歧，西班牙、法国表示支持，但是一些东欧国家对此不认同，还有国家要求为解禁提出附加条件。</p>\r\n<p>但欧洲媒体指出，20多年来，欧洲国家对此问题的分歧正在缩小。此前支持对华禁运的英国、荷兰以及德国等国，目前都降低了反对的调门。</p>\r\n<p>分析</p>\r\n<p>&ldquo;中国对欧武器需求不大&rdquo;</p>\r\n<p>中国军事问题专家张博称，欧盟至今维持的对华武器禁运是冷战思维的体现。即使未来解除禁运，中国对欧盟军事采购也不会明显增加。中国呼吁取消禁运的主要目的是希望欧盟消除对华歧视政策。</p>\r\n<p><br />\r\n张博表示，欧盟对华武器禁运是当时整个西方对华进行制裁的一部分，但是20多年来，中国、欧洲和全世界的形势都发生了巨大变化，固守对华武器禁运明显带有冷战思维，对中国来说不公平，是歧视。这是中国呼吁欧盟解除武器禁运的主要原因。</p>\r\n<p>如果欧盟能在短期内解除对华武器禁运，当然是好事;但中国对欧盟的军事采购不会有明显增加。因为总体上看，我们现在的军事装备、军事技术体系可以做到自给自足，对于欧洲的军事技术的需求并不迫切，但对于我们引进先进技术、装备、理念和人才都是有利的。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (55,'<p class=\"f_center\" style=\"text-align: center\"><img alt=\"日否认欲建&ldquo;日韩同盟&rdquo;(组图)\" src=\"http://img1.cache.netease.com/catchpic/8/81/8138BD0C61A6BA1B05FB93C878E94BAC.jpg\" /></p>\r\n<p>2010年10月，东京，日本首相菅直人检阅自卫队。日本政府3日否认欲与韩国军事结盟。</p>\r\n<p class=\"f_center\" style=\"text-align: center\"><img alt=\"日否认欲建&ldquo;日韩同盟&rdquo;(组图)\" src=\"http://img1.cache.netease.com/catchpic/D/D0/D02114CE13A2A691AA68D12D576A48BD.jpg\" /></p>\r\n<p>去年12月，韩国总统李明博视察位于江原道的韩军部队。</p>\r\n<p>3日，东北亚地区因为韩朝交火和美韩日密集军事演习引发的紧张气氛尚未消散之际，韩国媒体又爆出&ldquo;大新闻&rdquo;，称日本提议建立日韩军事同盟。消息一出来，日本政府赶紧辟谣，称日方并无此意，仅是希望日韩加强在安全保障领域的合作。</p>\r\n<p>针对朝鲜提议结盟？</p>\r\n<p>目前，东北亚地区已经有两大军事同盟，日美同盟和日韩同盟，如果日韩再结为盟友，整个东北亚无异于彻底回到了冷战时代。爆出这一消息的是韩国《每日经济新闻》，该媒体3日刊出了对日本外相前原诚司的一次新年书面采访。这篇报道援引前原的&ldquo;话&rdquo;称，&ldquo;朝鲜（去年）的武力挑衅行为，对朝鲜半岛乃至整个东亚的安定与和平均构成了威胁，因此，（日本）希望与韩国在安全保障领域结成同盟关系。&rdquo;</p>\r\n<p>报道称，前原在接受采访时说，进入新的一年，日本外交最大的课题之一就是与近邻各国构筑牢固的安全保障机制。而为了与韩国就安保合作问题展开讨论，他希望在不久后访问韩国，与韩国外交通商部长官会晤。</p>\r\n<p>日要求韩媒体更正</p>\r\n<p>就前原&ldquo;提议&rdquo;的建立日韩同盟问题，《每日经济新闻》分析称，一旦两国结盟，韩国军队不仅将与日本自卫队展开联合军事演习，在朝鲜半岛爆发冲突之际，日本自卫队还可能向韩国派兵进行支援，以及开展其他涉及安保和军事领域的全方位合作。</p>\r\n<p>而在谈到韩国国内可能因为日本政府对其侵略史的态度，反对日韩结盟时，报道援引前原的&ldquo;话&rdquo;说，日本政府会彻底反省应该反省的历史，而日韩两国不仅应该在政治、经济和文化领域加强合作，还应为扩大安保领域的同盟关系，不断进行努力和展开对话。</p>\r\n<p>《每日经济新闻》的上述专访内容3日刊出后，日本外务省立即表态，否认前原说过&ldquo;希望建立日韩同盟&rdquo;。外务省方面称，&ldquo;前原外相表达的意思是&lsquo;日韩强化在安保领域的合作很重要&rsquo;，并主张建立有利于日韩在安保领域展开良好对话的环境，所谓&lsquo;希望建立日韩同盟&rsquo;并不属实。&rdquo;外务省发言人还要求《每日经济新闻》立即对3日的报道进行更正。</p>\r\n<p>截至3日晚，作为被采访者的前原并没有对韩国媒体的报道作出任何表态。前原诚司在日本执政的民主党内是著名的鹰派人物、少壮派中坚。（百千）</p>\r\n<p>动态</p>\r\n<p>日防相外相接踵访韩</p>\r\n<p>日方可能提议日韩签署重要军事协议</p>\r\n<p>在日本外务省否认提议日韩结盟的当天，日本媒体传出消息。外相前原诚司正准备本月14日访问韩国，推动日韩在安保领域的合作。</p>\r\n<p>据称，目前这一访问日程已进入最后协商阶段。如果成行，将是前原去年9月就任外相以来首次访问韩国。前原准备会见韩国外长金星焕，就目前紧张的朝鲜半岛局势交换意见。</p>\r\n<p>除了前原，日防卫大臣北泽俊美也将于10日开始访问韩国，日本媒体称，北泽在访问期间可能会提议日韩签署一项重要的军事合作协定两国军队互相提供物资和服务。目前日本已与盟友美国及&ldquo;准盟友&rdquo;澳大利亚达成上述协定，根据该协定，日本自卫队与美军或澳军可互相提供食品、水、燃料等物资以及运输和修理等服务。日媒体称，日本防相与外相相继访韩，旨在强化日韩在安保领域的合作。</p>\r\n<p>另有日本媒体报道称，前原除了准备访韩外，还计划3月访问中国，以求改善自去年9月以来停滞的日中关系。（百千）</p>\r\n<p>周边</p>\r\n<p>朝韩互放&ldquo;对话&rdquo;信号</p>\r\n<p>朝媒体多篇文章呼吁对话，李明博称和平道路并未被阻断</p>\r\n<p>综合新华社电 朝鲜《劳动新闻》3日发表多篇文章，呼吁进行朝韩对话，改善朝韩关系。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (56,'<p>国际在线报道（驻俄罗斯记者盛晶晶）：3号，俄罗斯总统梅德韦杰夫正式签署一项关于优化联邦国家机关工作人员数量的命令，称联邦政府将在今后3年内削减20%的公务员，并严格控制政府机构的人员增长。这是自苏联解体以来，俄罗斯联邦政府第一次大规模裁减公务员。</p>\r\n<p>3号俄罗斯总统梅德韦杰夫正式签署了关于优化联邦国家机关工作人员数量的命令。俄罗斯媒体援引克里姆林宫新闻局当天的消息说，该总统令规定未来3年内将有不少于20%的俄国家公务人员被裁掉。总统令同时责成联邦政府提交相应的具体裁员建议。此外，这份总统令还将转交俄联邦会议（议会）、联邦审计署和中央选举委员会审议。这些部门在审议之后将就裁员问题向联邦政府提出合理化的建议。根据总统令的规定，即将开始的裁员行动将分阶段进行：即2011年和2012年每年减少5%的公务员数量，而2013年将削减10%，裁员总数将不少于12万人。另外，50%因裁减公务员而节省出的预算将被转而用于提高在岗公务人员的物质福利。</p>\r\n<p>去年6月初俄罗斯财政部长库德林以帮助国家主动减少财政赤字为由向俄总统提出拟裁减公务员的计划。库德林表示，裁员20%意味着每年将节省近430亿卢布（约十几亿美元）的财政开支，而这笔数目可观的开支可以帮助解决许多其他经济问题。对于财政部拟定的政府&ldquo;瘦身&rdquo;计划，俄总统梅德韦杰夫一直表示支持。去年9月的时候梅德韦杰夫就责成联邦政府和克里姆林宫办公厅研究和制定必要的削减规模。他同时也指出，这个计划不能机械地进行，应该按照实际情况循序渐进，财政支持的医生、教师等行业人员不应在裁员行列。</p>\r\n<p>俄罗斯自苏联时期以来形成了官僚腐败、低效的风气；政府机关公务人员数量庞大，且人际关联极其复杂。从苏联时期的勃列日涅夫到现代俄罗斯的普京都曾一度尝试裁员，但公务员却越裁越多，政府机关仍旧&ldquo;臃肿&rdquo;。根据俄罗斯国家统计局的数据，截至2010年中期包括立法、执行和司法机关在内的俄国家公务员数量超过了60万人。</p>\r\n<p><br />\r\n面对仍旧庞大的公务员队伍，梅德韦杰夫在去年6月向国家杜马提交了一份法律草案，规定了国家公务人员的退休年龄由原来的65岁改为60岁。这个草案的实施将直接为裁减公务员除去很多障碍，使得一些&ldquo;超龄服役&rdquo;的国家公务员自觉的下岗。梅德韦杰夫曾强调：&ldquo;削减官员数量，对于国家的发展非常有益。为实现这一目标，必须采取刚性措施。指望官员们自己走人是不可能的。这无疑是一项很残酷的举措，但有助于解决一系列问题。&rdquo;分析人士指出，除了金融危机背景下的财政问题，这次裁员计划实际上是俄罗斯政府又一次尝试改变现有的机构臃肿状态，打击官僚之风，从而进一步提高联邦政府各机构工作的效率。事实上，去年的这个大规模政府裁员计划被宣布之后，虽然俄罗斯社会各界仍有怀疑的声音出现，但是大部分人对政府此举表示了欢迎和信心。</p>\r\n<p>&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (57,'<p><strong>美国朝鲜问题特使博斯沃思今天将抵达韩国开始东亚三国之行</strong></p>\r\n<p>国际在线报道（编辑 游佳）：新年伊始，朝韩半岛局势继续牵动国际社会的目光。今天（4号）下午，美国朝鲜问题特使斯蒂芬&middot;博斯沃思将抵达韩国，就朝鲜半岛局势下一步如何行动展开磋商，这也是他此次中日韩三国访问的第一站。据报道，博斯沃思此次访问的议题可能会涉及六方会谈。那么，对于博思沃斯的访问，韩国国内是怎样一个态度？相关的举动能否在新的一年打开朝鲜半岛局势的新局面？</p>\r\n<p>根据韩国媒体的报道，博斯沃思将从3号至7号先后访问韩国、中国、日本，他3号从华盛顿出发，4号抵达韩国，并于5号访问中国，6号访问日本，与各国政府官员举行会谈，就朝核问题等交换意见。今天下午他将抵达首尔。从目前披露的情况来看，韩国外交通商部长官金星焕，还有六方会谈韩国代表团的团长魏圣洛将分别与博思沃斯进行会晤，议题包括重启六方会谈的先决条件，议题的先后顺序等等。</p>\r\n<p>在博斯沃思访问前夕，韩国外交通商部发言人金英善3号下午在例行新闻发布会上就重启六方会谈的问题表明了韩国方面的态度。他说，解决问题的钥匙握在朝鲜的手中。他指出，当天李明博总统在发表新年国政演说时并没有直接提及六方会谈，只是从大局出发，概括了韩朝关系的重要性。金英善说，根据至今六方会谈相关的讨论来判断，朝鲜是否会对无核化表现出诚意至关重要。在朝鲜对无核化表现出诚意的条件下，六方会谈才能得以重启。他还表示，六方会谈是能够商讨朝鲜无核化问题的唯一机制，有关国家一致认为六方会谈还继续有效。</p>\r\n<p>由于目前是各国为重启六方会谈进行频繁外交活动的敏感时期，韩国舆论普遍认为，博思沃斯此次东亚之行将会对重启六方会谈有重要影响，韩国政府对博思沃斯的访问比较重视，也进行了精心的安排。</p>\r\n<p>韩国总统李明博在青瓦台通过广播、电视、因特网等媒体，发表了&ldquo;新年特别演说&rdquo;。这个演说主要是表明韩国在新一年的政策方向。在演说中，李明博强调，2011年韩国各项政策的核心集中在安全领域和经济领域。因此，在新一年，韩国将加大国防改革力度，继续增强遏制力，并争取实现5%以上的经济增长速度。</p>\r\n<p>当然，李明博的这次新年特别演说最受关注的还是对于朝鲜政策的阐述。在演说中，李明博首次公开指出延坪岛炮击事件对韩国而言就相当于美国的9&middot;11事件，而美国在9&middot;11事件后重新制定了安全战略和国家战略，暗示韩国将调整国防安全政策和加强军事部署。李明博同时指出，新的一年韩国政府将更加严厉应对朝鲜可能的挑衅，韩国政府不会容忍朝鲜武力威胁韩国国民的人身和财产安全。</p>\r\n<p>另外，李明博还在演讲中表示，朝鲜应该清醒地认识到，军事冒险主义不会得到任何好处。朝鲜开发核武器是对朝鲜半岛和世界和平的严重威胁。国际社会应为促使朝鲜弃核并走向共同繁荣之路而共同努力。</p>\r\n<p>尽管如此，李明博仍然在演讲中表示，韩朝对话的大门并未关闭，只要朝鲜表现出诚意，并兑现自己之前在解除核设施方面的承诺，韩国愿意与国际社会一起扩大与朝鲜的经济合作。李明博在谈话中还督促国际社会共同为朝鲜弃核和繁荣共同努力。</p>\r\n<p>朝鲜的《劳动新闻》、《朝鲜人民军》和《青年前卫》三家报纸报纸，近年来每年都会联合发表元旦社论，外界一般将这一社论看作是朝鲜全年工作的指导方针。而今年这篇社论可以说是释放了不少积极信号，比如社论提到朝鲜和韩国的&ldquo;对抗应尽早解除&rdquo;，应把民族共同利益放在首位，为营造朝韩对话、合作氛围积极努力。同时，社论也强调，朝鲜致力于实现东北亚和平以及朝鲜半岛无核化的&ldquo;决心和立场没有改变&rdquo;等等。 因此，韩联社发表评论称，这篇社论内容与韩国总统李明博强调韩朝对话重要性的发言相呼应，可以视为朝方愿意推进韩朝政府间会谈的一个表态。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (58,'<p class=\"f_center\" style=\"text-align: center\"><object id=\"flash_97671\" codebase=\"http://fpdownload2.macromedia.com/get/shockwave/cabs/flash/swflash.cab\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"490\" height=\"394\">\r\n<param name=\"_cx\" value=\"12964\" />\r\n<param name=\"_cy\" value=\"10424\" />\r\n<param name=\"FlashVars\" value=\"\" />\r\n<param name=\"Movie\" value=\"http://img1.cache.netease.com/flvplayer081128/~false~0001_V6OB90R30~vimg2.ws.126.net/image/snapshot/2010/12/3/1/V6OB90R31~.swf\" />\r\n<param name=\"Src\" value=\"http://img1.cache.netease.com/flvplayer081128/~false~0001_V6OB90R30~vimg2.ws.126.net/image/snapshot/2010/12/3/1/V6OB90R31~.swf\" />\r\n<param name=\"WMode\" value=\"Window\" />\r\n<param name=\"Play\" value=\"0\" />\r\n<param name=\"Loop\" value=\"-1\" />\r\n<param name=\"Quality\" value=\"High\" />\r\n<param name=\"SAlign\" value=\"LT\" />\r\n<param name=\"Menu\" value=\"-1\" />\r\n<param name=\"Base\" value=\"\" />\r\n<param name=\"AllowScriptAccess\" value=\"always\" />\r\n<param name=\"Scale\" value=\"NoScale\" />\r\n<param name=\"DeviceFont\" value=\"0\" />\r\n<param name=\"EmbedMovie\" value=\"0\" />\r\n<param name=\"BGColor\" value=\"FFFFFF\" />\r\n<param name=\"SWRemote\" value=\"\" />\r\n<param name=\"MovieData\" value=\"\" />\r\n<param name=\"SeamlessTabbing\" value=\"1\" />\r\n<param name=\"Profile\" value=\"0\" />\r\n<param name=\"ProfileAddress\" value=\"\" />\r\n<param name=\"ProfilePort\" value=\"0\" />\r\n<param name=\"AllowNetworking\" value=\"all\" />\r\n<param name=\"AllowFullScreen\" value=\"true\" /></object></p>\r\n<p class=\"f_center\" style=\"text-align: center\"><img alt=\"著名作家史铁生今日凌晨突发脑溢血逝世\" src=\"http://img1.cache.netease.com/cnews/2010/12/31/20101231084643005ab.jpg\" /><br />\r\n史铁生 资料图</p>\r\n<p>新华网北京12月31日电（记者廖翊）著名作家史铁生未能走过2010年的最后一天。12月31日凌晨3时，59岁的史铁生因脑溢血在北京宣武医院去世。</p>\r\n<p>&ldquo;铁生昨天下午6点多从医院做完透析回家后，感到头疼、恶心，并呕吐，后一直昏迷，被急救车送往医院。他再也没有醒过来。&rdquo;北京市作家协会秘书长王升山向新华社记者叙述。</p>\r\n<p>王升山介绍，根据史铁生生前遗愿，他的脊椎、大脑将捐给医学研究；他的肝脏将捐给有需要的患者。</p>\r\n<p>史铁生1951年生于北京，年轻时双腿瘫痪，后来患肾病并发展到尿毒症，一直靠透析维持生命，自称&ldquo;职业是生病，业余在写作&rdquo;。&ldquo;文革&rdquo;期间，史铁生下放陕北，1979年发表第一篇小说《法学教授及其夫人》。成名作是《我的遥远的清平湾》，获1983年全国优秀短篇小说奖。小说《老屋小记》获首届鲁迅文学奖。2002年，他获得华语文学传媒大奖年度杰出成就奖。其著名散文《我与地坛》影响最大，感动了无数读者。史铁生在电影创作上成绩丰硕，所创作的电影剧本《多梦时节》《死神与少女》等充满诗意，为电影类型的发展作出了贡献，并在国内外获奖。</p>\r\n<p>&ldquo;先生的影响太深了，从来没想过先生会走。地坛里玩耍的那个孩子，回去了！&rdquo;&ldquo;史先生走了，但他对生命意义的思考已经成为一笔非常重要的社会财富永远留了下来。&rdquo; 读者第一时间在网上留言表达纪念。</p>\r\n<p><strong>史铁生简介</strong></p>\r\n<p>史铁生，男，汉族，1951年生于北京。1969年赴延安插队，1972年双腿瘫痪回到北京。1974年始在某街道工厂做工，七年后因病情加重回家疗养。</p>\r\n<p>1979年开始发表文学作品。著有中短篇小说集《我的遥远的清平湾》、《礼拜日》、《命若琴弦》、《往事等；散文随笔集《自言自语》、《我与地坛》、《病隙碎笔》等；长篇小说《务虚笔记》以及《史铁生作品集》。曾先后获全国优秀短篇小说奖、鲁迅文学奖，以及多种全国文学刊物奖。一些作品被译成英、法、日等文字，单篇或结集在海外出版。</p>\r\n<p><!--[4,175,19] published at 2010-12-27 08:48:43 from #202 by 261--></p>\r\n<p>2002年，史铁生荣获华语文学传播大奖年度杰出成就奖，同年，《病隙碎笔》(之六)获首届&ldquo;老舍散文奖&rdquo;一等奖。</p>\r\n<p><strong>名家评价</strong></p>\r\n<p>我们从史铁生的文字里看得到一个人内心无一日止息的起伏， 时也在这个人内心的起伏中解读了宁静。――蒋子丹</p>\r\n<p>在红卫兵一代中，史铁生也许是极少数能够超越自身，具有现代意识的作家。――许纪霖</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (59,'<p class=\"f_center\" style=\"text-align: center\"><img alt=\"政府网站政风满意率调查公示被曝藏猫腻\" src=\"http://img1.cache.netease.com/catchpic/0/06/06A6306C43003CD50A01C4F1F8F2F04D.jpg\" /></p>\r\n<p class=\"f_center\">这个公式（圈内），受到网友质疑。 网页截图</p>\r\n<p class=\"f_center\" style=\"text-align: center\"><img alt=\"政府网站政风满意率调查公示被曝藏猫腻\" src=\"http://img1.cache.netease.com/catchpic/E/EF/EFD1D25C0FDA8B54774300D3E991E529.jpg\" /></p>\r\n<p class=\"f_center\">垫江政府网被指更新太慢 网页截图</p>\r\n<p><founder></founder></p>\r\n<p>一个挂在开县政风行风投票评议网的公式：满意率=(非常满意&times;100%+满意&times;90%+基本满意&times;70%+不满意&times;50%)&divide;得票总数&times;100%。</p>\r\n<p>网友认为这个公式暗藏玄机：如100名群众给受评的部门、公用企业单位投票时，如非常满意、满意、基本满意都是0票，不满意得票100票，那按公式计算为：满意率=(0&times;100%+0&times;90%+0&times;70%+100&times;50%)&divide;100&times;100%，即群众满意率为50%。</p>\r\n<p>这个公式在网上已挂了14天。昨天，本报记者就此采访开县纪委，得到的回应是：网络公司搞错了。随后，该县对该公式作了更改。</p>\r\n<p><strong>网曝年终考核有猫腻</strong></p>\r\n<p>1月1日，网友&ldquo;虎猫&rdquo;在大渝网上发帖称，开县纪委对该县各部门的年终考核有猫腻。</p>\r\n<p>&ldquo;虎猫&rdquo;所说的&ldquo;猫腻&rdquo;，藏于一个计算公式中。他称，开县纪委请广大群众对各部门、公用企业单位的服务投票，选项有非常满意、满意、基本满意、不满意四项。计算群众满意率的公式为：满意率=(非常满意&times;100%+满意&times;90%+基本满意&times;70%+不满意&times;50%)&divide;得票总数&times;100%。</p>\r\n<p>&ldquo;虎猫&rdquo;说，如果一个部门有100票不满意，非常满意、满意、基本满意都是0票，按该公式计算，该单位群众的满意率为50%，不满意率为50%。</p>\r\n<p>明明是100%的不满意，咋变成50%的不满意？&ldquo;虎猫&rdquo;质疑：这样的计算，如何能反映社情民意？</p>\r\n<p><strong>调查</strong></p>\r\n<p><strong>满意率被公式提升</strong></p>\r\n<p>昨天中午11点过，记者登录开县政风行风投票评议网，发现该网确有&ldquo;虎猫&rdquo;所说的公式。</p>\r\n<p>该网请广大群众对197个部门、17个公用企业单位投票，评它们的服务质量。投票时间从2010年12月20日起，到2011年1月15日止。</p>\r\n<p>记者随机查看网上的投票情况，发现受评部门和企业的满意率经公式计算后，纷纷提升。该县环保局环境监测站的&ldquo;工作作风&rdquo;，非常满意、满意、基本满意均为0票，不满意9票，按公式计算，其满意率竟为50%；县发改委&ldquo;信息公开&rdquo;一项，非常满意76票，满意2票，基本满意2票，这三个选项相加共80票，而不满意85票。但按该公式计算，群众满意率超过70%。</p>\r\n<p><strong>回应</strong></p>\r\n<p><strong>网络公司搞错了</strong></p>\r\n<p>昨天中午约12点，记者联系上负责这次网上政风、行风投票评比的开县纪委常委、监察局副局长刘丰。刘丰称，该行评页面，是委托一家网络公司制作的，可能是网络公司出了错。</p>\r\n<p>刘丰随后让具体负责此事的县纪委监察局纠风室主任杨人萍与记者联系。杨人萍称，网络公司在制作时，将公式搞错了；制作出的页面，网络公司未交给他们审核，便挂在网上。</p>\r\n<p>广大群众已投了14天的票，这个公式也被悬挂于网上14天。有网友质疑，难道相关部门在14天里，都没能看出这个公式有错？杨人萍表示：&ldquo;确实存在工作上的疏忽。&rdquo;</p>\r\n<p><strong>出错不影响评比</strong></p>\r\n<p>有网友担心，这个公式恐让民意遭到&ldquo;暗算&rdquo;。对此，杨人萍表示，该公式不会影响评比结果。&ldquo;我县规定，我们是评分，而不是评满意率。&rdquo;杨人萍说：&ldquo;受评的部门和公用企业，如果评分达不到85分，将会受到处理。&rdquo;</p>\r\n<p>他还称，群众网评并不是此次行评的全部内容。行评总分值为100分，分为四项：群众网评得分，占总分值的20%；党代表、人大代表、政协委员评分，占总分值的20%；基层代表评分，占总分值的20%；部门和公用企业单位直接的服务对象评分，占总分值的40%。</p>\r\n<p>昨天下午，开县迅速将网上的计算公式作了更改。其为：测评得分=(非常满意&times;1+满意&times;0.9+基本满意&times;0.7+不满意&times;0.5)&divide;得票总数&times;100。</p>\r\n<p>记者&nbsp;聂超</p>\r\n<p><strong>相关新闻</strong></p>\r\n<p><strong>垫江政府网一栏目&nbsp;为何两年半没更新？</strong></p>\r\n<p>在元旦节前，有网友在天涯重庆论坛发帖，称垫江县人民政府网上的&ldquo;政府会议&rdquo;栏目，有近两年半没更新。</p>\r\n<p>发帖者在网上发了网页截图。截图显示，最后更新时间为2008年8月14日，内容为《十五届县政府第24次常务会议纪要》；该栏目的其余内容，上网时间均为2005年、2006年。</p>\r\n<p>&ldquo;垫江政府几年都没有开过会啦？&rdquo;有网友质疑。</p>\r\n<p>昨天，垫江县政府对此回应称，2008年之后，按上级要求，县政府常务会议纪要不再以这种方式公开。目前，该县政府网正准备取消该栏目。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (60,'<p class=\"f_center\" style=\"text-align: center\"><img id=\"{19784176-6C2D-4CF2-9341-7DC7A4D3317D}\" border=\"0\" hspace=\"0\" alt=\"黄山徽州区\" align=\"center\" src=\"http://img1.cache.netease.com/catchpic/7/79/7931444CE63DB1A06E34AEC5DC1AFCF5.jpg\" /><br />\r\n这是网络上对此次招聘事件的转载（1月1日摄）</p>\r\n<p>新华网合肥1月3日电&nbsp; 在网络上引起热议的安徽黄山徽州区招聘事件有了最新进展。徽州区招聘工作领导组负责人3日对新华社&ldquo;中国网事&rdquo;记者表示，除了原定于1月3日进行的面试已经在2日公告取消外，此次事业单位招聘方案目前也已经全部终止。徽州区将积极主动按照最新公布的《安徽省事业单位公开招聘人员暂行办法》要求，完善招聘工作，重新组织实施。</p>\r\n<p>12月24日，安徽省黄山市当地一论坛上，网名为&ldquo;徽州人&rdquo;的一名网友发帖称，黄山市徽州区事业单位招聘为领导子女专设，引起了网民热议。</p>\r\n<p>针对网民的批评和质疑，徽州区人社局局长胡志成在接受&ldquo;中国网事&rdquo;记者采访时否认了&ldquo;因人设岗&rdquo;的说法。他说，此次招聘户籍限定是因为山区乡镇工作条件差，工资待遇低，过去就出现过外地人应聘后很短时间就辞职的现象，而招收本区户籍的人员更具有稳定性，能更安心地开展工作。同时，大专以上学历就可以满足工作需要。</p>\r\n<p>全程参与此次招聘监督的徽州区监察局副局长洪光武3日也告诉记者，制卷、监考、阅卷等各个环节都严格按照国家事业单位人员招聘有关政策执行，并在纪检监察部门的监督下进行。由于试题较难，考生分数普遍偏低，但这并不能说明招聘的考生知识少、素质低、能力差。</p>\r\n<p>2011年1月2日，黄山市徽州区人力资源和社会保障局发布《公告》，取消了原定于1月3日进行的此次事业单位招聘工作人员的面试工作，表示尽快按照《暂行办法》要求进一步完善招聘工作。</p>\r\n<p>记者了解到，徽州区发布、实施《关于公开招聘事业单位工作人员的通知》是在2010年12月20日。在实施过程中，安徽省委组织部、省人力资源和社会保障厅出台《安徽省事业单位公开招聘人员暂行办法》（简称《暂行办法》），并于2010年12月29日在安徽省人力资源和社会保障厅网站全文公布。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (61,'<p class=\"f_center\" style=\"text-align: center\"><img alt=\"屠宰场视频曝生猪注水 被拍方称遭诬陷\" src=\"http://img1.cache.netease.com/catchpic/E/E5/E5D5CF6AF9508A768B5F6C91D7DCF534.jpg\" /></p>\r\n<p class=\"f_center\">被打伤的看门人马福贵 记者 冀强 摄</p>\r\n<p class=\"f_center\" style=\"text-align: center\"><img alt=\"屠宰场视频曝生猪注水 被拍方称遭诬陷\" src=\"http://img1.cache.netease.com/catchpic/8/8C/8C12FB783ED8DE962CDFD1EDF585D47D.jpg\" /></p>\r\n<p class=\"f_center\" style=\"text-align: center\"><img alt=\"屠宰场视频曝生猪注水 被拍方称遭诬陷\" src=\"http://img1.cache.netease.com/catchpic/7/76/768D8619887D62F856C62FC0653ED953.jpg\" /></p>\r\n<p>\r\n<script language=\"javascript\" type=\"text/javascript\">\r\n\t\t\t\t\t\t\t\tif(picResCount>0){\r\n\t\t\t\t\t\t\t\t\tdocument.getElementById(\"picres\").style.display=\"block\";\r\n\t\t\t\t\t\t\t\t\tdocument.write(\"</p><p>\");\r\n\t\t\t\t\t\t\t\t}\r\n \t\t\t \t\t\t\t</script>\r\n</p>\r\n<p><founder></founder></p>\r\n<p>日前，记者收到了淄博一市民送来的屠宰场&ldquo;注水肉&rdquo;视频。据视频提供者杨先生介绍，这是在淄博市高青县一家生猪定点屠宰场强行拍摄的，内容是该屠宰场涉嫌制作注水猪肉。在两段时长总计达9分钟的<a href=\"http://v.163.com/\"><font color=\"#1e50a2\">视频</font></a>中，一根塑料水管被人粗暴的插入生猪嘴中，水不停地从猪嘴里溢出&hellip;山东商报&nbsp;记者&nbsp;冀强&nbsp;山东新闻网&nbsp;记者&nbsp;高太明&nbsp;联动报道</p>\r\n<p><strong>神秘视频曝光</strong></p>\r\n<p><strong>活猪注水场面</strong></p>\r\n<p>根据视频内容，记者日前来到了淄博高青县实地调查采访，却听到了多种不同的声音：被拍视频的厂家说是被人&ldquo;诬陷&rdquo;了，当地主管部门称拍摄者是&ldquo;自导自演&rdquo;，警方则表示视频拍摄者属&ldquo;团伙犯罪&rdquo;，目前已有两人被刑拘。</p>\r\n<p><strong>神秘视频曝光</strong></p>\r\n<p><strong>活猪注水场面</strong></p>\r\n<p>&ldquo;这可能是最震撼的注水肉视频了，他们注水的过程，被我们抓了个现形。&rdquo;把视频交给记者时，淄博市民杨先生神秘地对记者说。</p>\r\n<p>在第一段长达3分多钟的视频中，先是有几名工人在猪圈里忙碌，几头猪被吊在绳子上。也许是发现有人拍摄，工人们迅速将被吊起的猪放下，纷纷离开。随后，镜头转向地面，一根根散落在地上的塑料水管正在冒水。在猪圈的一角，两个塑料瓶被拍入镜头，视频中传出一个声音：&ldquo;这是药&rdquo;。</p>\r\n<p>另一段长达6分钟的视频画面比较凌乱，嘈杂的声音中，一头猪被铁钩勾住嘴，往上呈45度角吊着。随着镜头的推进，另几头被吊着的活猪出现在画面中，有人往其中一头猪嘴里插进一根塑料水管，水不停地从猪嘴里流出。视频的最后显示，屠宰场几名工作人员被手持长棍的人控制在室外，他们蹲在地上，被人用手电照着逐个辨认，其中一人被称为&ldquo;老板&rdquo;。</p>\r\n<p>视频提供者杨先生介绍说，自己也是在高青县做生猪屠宰生意的，这两段视频是在当地另一家企业&mdash;&mdash;腾达定点屠宰场拍摄的。&ldquo;他们好几年来一直做这种黑心生意，低价往外批发猪肉，把我们挤兑的没有办法。无奈之下，我们才出此下策，2010年12月17日凌晨强行翻墙进入腾达场内拍摄的，希望能够惩治他们。&rdquo;</p>\r\n<p><strong>B</strong></p>\r\n<p><strong>被拍摄方称</strong></p>\r\n<p><strong>&ldquo;遭人诬陷&rdquo;</strong></p>\r\n<p>&ldquo;三年来，我们就没做过一次这种缺德事！我们纯属被人诬陷。&rdquo;在淄博高青县腾达定点屠宰场，负责人孙增双手抱在胸前，气呼呼地说。</p>\r\n<p>据孙增介绍，他就是事发当晚出现在视频中的那名&ldquo;老板&rdquo;。&ldquo;当时我正在被窝里睡觉呢，有人破门而入，把我从被窝里拎出来了。&rdquo;孙增介绍说，被几个人架到屠宰车间时，他才发现自己的工人都被控制了。&ldquo;来的人气势汹汹，带着棍棒砍刀，大约有二三十口人吧。&rdquo;等那些人全部撤出厂区后，才打电话报了警。</p>\r\n<p>在高青县医院，记者见到了最早听到狗叫外出查看的看门人马福贵，他指着被缝了八针、诊断为颧骨粉碎性骨折的面部告诉记者：&ldquo;在北墙外我遇到两名年轻人，他们自称是&lsquo;逮兔子的&rsquo;，话刚说完，我就被他们用棍子重重的打倒在地上了。&rdquo;</p>\r\n<p>&ldquo;我们清楚是谁闯入厂区来拍摄视频。&rdquo;孙增介绍说，当晚翻墙、砸开门锁后强闯厂区的是另一家名为湾头的生猪屠宰场的老板和工人。&ldquo;因为同行，我们存在竞争关系。几年来他们一直竞争不过我们，提出和我们合并工厂也被拒绝，所以才想出这个损招来诬陷我们。&rdquo;</p>\r\n<p><strong>主管部门称视频是&ldquo;自导自演&rdquo;</strong></p>\r\n<p>&ldquo;任何一个对屠宰行业有了解的人看了视频后，都会觉得这是拍摄者自导自演。&rdquo;对于注水肉视频一事，当地生猪定点屠宰领导小组的牵头单位&mdash;&mdash;高青县财委负责人这样表示。</p>\r\n<p>记者从高青县畜牧兽医局获悉，目前高青县只有两家定点屠宰场，一家是腾达屠宰场，另一家是湾头屠宰场。按照高青县财委的说法，在这段视频曝光当天上午，他们就紧急约见了腾达和湾头两家屠宰企业的相关负责人。</p>\r\n<p>&ldquo;我们对这件事也很重视，毕竟关乎全县的猪肉食品安全。&rdquo;高青县财委表示，根据对视频的分析和双方负责人的交代以及相关证据，他们认为这段视频不是现场的真实拍摄，而是一段自导自演的视频。</p>\r\n<p>&ldquo;腾达现在占据了高青县90%以上的生猪屠宰市场，三年来共屠宰生猪14万头，从未出现过任何食品安全事故，也从未有过消费者或猪肉经营户针对该场的投诉现象，在业界有着不错的口碑。如果有问题的话，早就被发现了。&rdquo;高青县财委介绍说：&ldquo;反观湾头，因为诸多原因，他们的生意一直一般。再加上我们通过视频掌握的一些证据，我们认为这段视频是因为湾头正面竞争不过腾达，而采取的一种极端手段。&rdquo;</p>\r\n<p>高青县生猪定点屠宰指挥小组有关负责人告诉记者，视频曝光后，他们在全县展开了检查，并未发现有注水肉流入市场，视频中出现的几头&ldquo;灌水猪&rdquo;已经被无公害处理。</p>\r\n<p><strong>警方称部分拍摄者已被刑拘</strong></p>\r\n<p>记者了解到，目前高青县公安局已经对此案立案侦查。一名姓冯的副局长在接受本报记者采访时表示，视频拍摄者强行闯入腾达场内，并打伤员工的行为属于&ldquo;团伙犯罪&rdquo;，目前已经刑拘了两名当晚在外&ldquo;把风&rdquo;的犯罪嫌疑人，因此案正在侦破，具体情节并不方便透露。</p>\r\n<p>而对于视频的中&ldquo;注水&rdquo;的真伪，警方表示犯罪嫌疑人还未全部归案，视频的真伪还不能最终确定。</p>\r\n<p><strong>当地派人入场督查</strong></p>\r\n<p><strong>确保猪肉安全供应</strong></p>\r\n<p><strong>E</strong></p>\r\n<p>在视频曝光后，高清县委县政府在2010年12月17日晚连夜召开了会议，专题研究注水肉问题。由于暂时无法排除嫌疑，腾达屠宰场已经被责令&ldquo;一边生产，一边整改&rdquo;。</p>\r\n<p>记者了解到，当晚会议决定在未查清事实之前，当地主管部门加强对生猪市场的监督检查力度，在腾达、湾头两家屠宰企业各安排两名监督检疫人员，从晚八点至次日凌晨五点进行全程督查。同时，还要求两家企业晚上不允许关闭大门，以便随时接受检查。既保证当地的猪肉不断档供应，又保障大家吃上放心肉。</p>\r\n<p><strong>两厂家互指对方</strong></p>\r\n<p><strong>不正当竞争</strong></p>\r\n<p>采访中记者了解到，腾达和湾头两家生猪定点屠宰场除了在生意上互不相让以外，也在嘴皮子上打起了官司，互相指责对方不正当竞争。</p>\r\n<p>此前在将视频提供给记者时，杨先生曾对记者表示，之所以采取&ldquo;不场面&rdquo;的办法拍摄这段视频，主要是因为&ldquo;被逼的&rdquo;。杨先生对记者说，自己的几个亲戚2007年10月份开始经营湾头定点屠宰场。刚起步时效益还可以，但是因为腾达长年来生产了大量的注水猪肉，并且通过社会上的一些势力控制当地肉市，所以给当地的猪肉市场造成了严重冲击。杨先生说，很多人都去当地的相关部门反映过，但是都没有足够的证据来证明&ldquo;腾达所生产的就是注水肉&rdquo;。杨先生介绍说：&ldquo;目前来说，正常的毛猪价格一般在6.7元/斤-6.8元/斤左右，要是除去猪下货、猪皮等杂物，猪肉批发价格一般要在每斤8.8元左右才能看到利润。但现在腾达的肉批发价格却是7.5元/斤，照这个价格别说是保本了，他们厂每斤要赔1.5元左右，铁定是注水了。&rdquo;</p>\r\n<p>在采访中，腾达的负责人孙增则告诉记者，湾头方面杨先生的说法根本就是颠倒黑白。&ldquo;真正用社会闲杂人员扰乱市场的是他们，正面竞争不过我们，湾头两年前就开始出损招。&rdquo;孙增表示，目前腾达方面的猪肉出场价一般在每斤8.8元左右，走的是薄利多销的路子。&ldquo;虽然单头猪的利润不高，但每天我们能宰杀近百头猪，总利润还是比较可观的。&rdquo;</p>\r\n<p>为了验证腾达方面的说法，记者随后多次拨打湾头定点屠宰场相关人员的电话，但均无人接听。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (62,'<p><strong>作者：</strong><span style=\"font-weight: bold\">郑渝川</span></p>\r\n<p>历时一年程序，深圳市第二次保障性住房终审结果在2010年12月31日开始公示，相应名单悄然出现在深圳市住建局官网，有5293户过关、1092户被剔除。</p>\r\n<p>此次公示时间为2010年12月31日至今年1月14日，将节假日算在公示&ldquo;15个工作日&rdquo;的范畴，受到普遍怀疑和批评。记者调查发现，此次最终公示的内容里，并没有通过者的财产和收入信息；许多街道办因为元旦放假，也未挂出辖区终审结果。深圳市住宅售房管理服务中心主要官员，拒绝对采访记者解释相关问题。</p>\r\n<p>深圳市民、网民还提出了其他质疑：涉嫌违反计划生育政策的申请户获批，家住豪宅跨区、跨街道申请保障房的申请户大量存在，已住保障房的二次申请，等等。（1月2日《深圳晚报》）</p>\r\n<p>2010年3月下旬、10月下旬，深圳市第二次保障性住房初审、复审名单的公示，均引发强烈质疑。由于网民不懈穷追、媒体给力调查，&ldquo;豪宅门&rdquo;、&ldquo;豪车门&rdquo;、&ldquo;零资产&rdquo;、&ldquo;公务员&lsquo;假冒&rsquo;临时工&rdquo;等真相现世，已经严重削弱了深圳保障性住房分配的公信力，相关主管部门和一些街道、社区机构对疑难问题的解答，更是沦为坊间笑谈。</p>\r\n<p>从2010年最后一天晚上&ldquo;秘密&rdquo;挂上官网、以凑满15个公示日的终审结果来看，初审、复审过程中暴露出的许多程序性问题，并未能得到对应的完善解决&mdash;&mdash;市民质疑的违反计划生育政策申请户、豪宅申请户、已享受保障房的申请户二次申请等情况，是在该名单复审时就已经被媒体曝光过的问题。时隔两月，深圳市住建局、深圳市住宅售房管理服务中心对这些问题不作有效性释疑，也不予更正，显然是对民意和媒体监督的蔑视。</p>\r\n<p>更离谱的是，初审、复审名单中，由于申请户财产和收入信息受到普遍怀疑，不少&ldquo;零资产&rdquo;家庭事后被证明所言不实，此番终审名单中居然就不再公开这方面信息。深圳市住宅售房管理服务中心主要官员拒绝对此问题作出回答，或不接记者采访电话的做法，都在一步步坐实外界的忧虑和猜测。这难道不是心虚的表现？</p>\r\n<p>2010年7月开始实施的《深圳市保障性住房条例》，第九章&ldquo;法律责任&rdquo;中有多个条款对骗购、骗租保障性住房行为进行了界定，并明确要求将其纳入不良行为记载，向社会公开。此番终审名单公布，相关公告却不披露被剔除的1092户违反保障性住房政策的具体做法，以及主管部门依照《条例》给予的处罚，这岂不是自己立法、并且自己带头违法？</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (63,'<p>&nbsp;&nbsp;&nbsp; 2010年，不少政府部门在公共决策之前开始征集民意。上周，中国青年报社会调查中心对1655人进行的一项调查显示：84.5%的人首选&ldquo;存在走过场现象&rdquo;，其次是&ldquo;有的征集缺乏对公众的及时反馈&rdquo;(70.3%)，&ldquo;民意征集与政策制定脱节&rdquo;排在第三位(69.8%)。63.7%的人认为部分民意征集&ldquo;随意性大，缺乏统计和分析&rdquo;。（12月29日《中国青年报》）<br />\r\n<br />\r\n　　公共政策与公众利益休戚相关，征集民意、倾听民生是公共决策的必备环节。今年以来，车船税立法、拆迁条例修改、北京治理交通拥堵等民意征集活动，引起了公众的广泛关注和参与。无论是保障决策的科学性与民主性，还是回应高涨的公民意识，民调都当越来越给力、其权重也当越来越加量。<br />\r\n<br />\r\n　　遗憾的是，尽管民调的舞台是搭起来了，但&ldquo;演出流程&rdquo;远远未曾到位，有的是扯两嗓子虚与委蛇，有的是架起喇叭大搞&ldquo;假唱&rdquo;&hellip;&hellip;不仅让民调沦为形式主义的&ldquo;新代言&rdquo;，反而消弭了政府公信力。有几个核心问题越来越成为困扰民调质地的关键：一是逻辑上的正当性，现在的民调，往往是职能部门自己设置议题，然后自己再去调研，再然后自己公布结果，最后还是自己拿出决策&mdash;&mdash;&ldquo;一人操刀&rdquo;的民调、尤其是很可能出现&ldquo;不同声音&rdquo;的民调，如何保证其客观性与真实性？譬如吉林省永吉县人民政府网所做的一项关于&ldquo;满意度&rdquo;的民意调查中，就只有两个选项&mdash;&mdash;&ldquo;满意&rdquo;或者&ldquo;非常满意&rdquo;；二是操作上的科学性，民调越来越多，但是专业性堪忧，样卷设计、抽样方式、数理分析、意见权重分配等，对结果都起着至关重要的作用，粗放型的民调操作不仅不能有效汲取民间智慧，很可能在议题上横生出很多枝节、折损民调的效率，没有严谨而专业的民调设计，就不会有精确而有效的民调结果；三是民调要有宽泛的适用度，不能在制度设计基本完成之后，更不能成为试探民意的幌子，而应该贯穿公共决策的每个环节和整个过程，因为政策的稳定性是相对的，成熟的民意可以随时为失衡的政策校准。<br />\r\n<br />\r\n　　谁也不能臆断公共政策的民调就是&ldquo;走过场&rdquo;而已，但类似&ldquo;逢听必涨&rdquo;听证会般的民调，在现实生活中也是屡见不鲜。要让&ldquo;被走过场&rdquo;的民调硬气起来，关键还得在程序的公正与透明上下功夫：一方面，我们可以借鉴美国《纽约时报》社会调查中心、芝加哥大学全国民意研究中心、非营利组织皮尤(PEW)研究中心和商业性的盖洛普公司等，由政府委托独立第三方机构征集民意、解读民意；另一方面，决策需要对征集过来的民意有更透明、更细致的回应，呵护公民参与民调的热情与信心，即便是反对的声音，也当分清比重，予以说明。<br />\r\n<br />\r\n　　民调是现代公共治理的一个&ldquo;小锦囊&rdquo;，只有让有理有利有节的民调硬气起来，公民的民主参与才会与公共政策良性互动，公共利益才能在看得见的博弈中走向最大化。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (64,'<p><strong>作者：</strong><span style=\"font-weight: bold\">王石川</span></p>\r\n<p>12月27日，湖南省衡阳市衡南县松江镇一辆送小学生上学的三轮车坠河。据该县党政门户网站发布的事故通告，截至当晚6时30分许，事故共造成14名小学生死亡，6人受伤。事故车辆司机已被公安机关控制。（12月28日《羊城晚报》）</p>\r\n<p>每个公民的非正常死亡都让人黯然，而小学生的殒亡则尤其让人叹惜，短暂的人生画卷尚未铺开，便已戛然而止，留给家人的又将是多么撕心裂肺的挣扎与痛切。12&middot;27校车事故的真相有待调查，但不能不关注我们的校车安全。</p>\r\n<p>此起校车坠河事件早已埋下了伏笔。其实，所谓的校车，就是一辆农用三轮车而已，只能乘坐七八个人，却被塞进了20名学生；而且，车子改装后车厢外加了门栓，车辆行驶时一般是从外面拴着的，所以坠河后孩子无法逃生自救。还有一处细节不能不提，发生事故的车是由家长自己联系的，学校并没有配备校车。学校为何不配备校车而由家长凑钱雇车？这也许牵扯到很多层面的问题，但显然与教育投入不足有关。</p>\r\n<p>今年7月1日起，我国首部专门规范小学生校车安全的强制性国家标准《专用小学生校车安全技术条件》就已实施。该国标可圈可点，比如规定，学生座位必装安全带，还要装有类似飞机&ldquo;黑匣子&rdquo;的汽车行驶记录仪等等。在这种语境中，为何仍有大量不合格校车？</p>\r\n<p>央视主持人白岩松说他很羡慕美国到处都有黄色的校车：其一，政府出钱养校车；其二，校车要求是最安全的。很多年前美国也发生过桥梁垮塌事件，在交通高峰期塌的，60多辆车掉下去，死伤无数，其中有一辆黄色的校车，只轻伤了几个孩子，没有一人死亡，因为校车太结实了；其三，校车令行禁止，校车停下来时，会弹出一个STOP的牌子，两个方向马路上的汽车必须在25米外全部停下，什么时候孩子全下完了，司机把STOP的牌子收起来了，两边的车才可以通行，谁如果违反了规则，或者敢去超车，将被法律严惩。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (65,'<p>&ldquo;2010年度汉字盘点&rdquo;评选正在进行中，目前&ldquo;涨&rdquo;遥遥领先，超过半数。这不是偶然的，因为&ldquo;面粉涨价了，汽油涨价了，羽绒服涨价了&hellip;&hellip;除了工资，身边的一切几乎都涨价了&rdquo;，面对这样的形势，靠工薪或做点小买卖为生的普通老百姓该如何应对？有人开出了药方&mdash;&mdash;&ldquo;以勤奋工作赶超物价涨幅&rdquo;（12月 27日《工人日报》）。<br />\r\n<br />\r\n&nbsp;&nbsp;&nbsp; 这个药方让人感到哭笑不得，因为这简直是等于在对溺水者说，你要靠自己的努力挣扎来确保自己免于被淹死，虽然笔者并不愿意揣测别人的动机，但还是从这张药方当中，嗅出了一点站着说话不腰疼的味道。<br />\r\n<br />\r\n&nbsp;&nbsp;&nbsp; 普通老百姓靠勤奋工作，无论如何是追不上物价涨幅的。原因很简单，目前的通货膨胀，主要是由两个原因造成的：一是货币投放量过多，对普通老百姓来说，其面临的问题就如同面对漫过堤坝的洪水一样，除非能够抓到一块木板或者救生圈什么的，否则的话再&ldquo;勤奋&rdquo;的扑腾都无济于事；二是贫富差距太大，既得利益阶层手里掌握着大量剩余资金，也就是热钱。这些热钱由于规模特别巨大，所以完全可以操控任何一种商品的价格，用来炒房，则把全民都变成了房奴；用来炒&ldquo;蒜&rdquo;，则&ldquo;蒜你狠&rdquo;，用来炒姜，则&ldquo;姜你军&rdquo;&hellip;&hellip;价格动辄几倍地往上涨。面对这样的滔天巨浪，普通老百姓毫无博弈能力。&ldquo;勤奋工作&rdquo;有什么用呢？什么用也没有。<br />\r\n<br />\r\n&nbsp;&nbsp;&nbsp; &ldquo;以勤奋工作赶超物价涨幅&rdquo;的论调，其实还暗含了对大部分普通民众的蔑视和讽刺：似乎他们是一群不愿意勤奋工作，只知道抱怨的人。但事实上，那些无力应对物价上涨的人&mdash;&mdash;无论是起早贪黑的小商小贩，还是拖着&ldquo;亚健康&rdquo;的身体，生活在&ldquo;过劳死&rdquo;阴影下的一般白领&mdash;&mdash;都是我们这个社会最勤奋的阶层，游手好闲的恰恰是无须担心通胀的既得利益阶层。&ldquo;以勤奋工作赶超物价涨幅&rdquo;的论调，在客观上还把通胀所带来的民生问题，推到了通胀的受害者头上，似乎他们的一切苦难，都是他们不愿意&ldquo;勤奋工作&rdquo;造成的，强词夺理，莫此为甚！<br />\r\n<br />\r\n&nbsp;&nbsp;&nbsp; 通胀问题根本上是一个宏观经济政策问题，因此也应该通过政府的宏观调控来解决，各国皆然，从来没有哪个国家是靠号召民众通过&ldquo;勤奋工作&rdquo;来应对通胀的。就中国目前的具体情况而言，我认为应该尽快推出保值储蓄以稳定人心，打消民众的通胀预期，同时收紧货币，防堵热钱，严厉打击哄抬物价的投机行为，对低收入民众给予物价补贴，如此才能够有效遏制通胀，同时把通胀对一般民众生活的不利影响，降至最低。而只要人民能够安居乐业，免除了后顾之忧，则 &ldquo;勤奋工作&rdquo;也就是自然而然的事了。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (66,'<p>12月15日中国社科院公布的《2011年中国社会形势分析与预测》蓝皮书，警示伪城市化牺牲农民利益。<br />\r\n<br />\r\n　　城市化的标志是缩小城乡剪刀差、提高全体国民尤其是低收入阶层的生活质量，而不是以拥有农村或者城市户籍为标志，不是以是否住上楼房为标志。<br />\r\n<br />\r\n　　目前新一轮城市化中，农民上楼运动方兴未艾，对此我们应该保持足够的警惕。土地作为目前中国增值最快的资产品，地方政府有足够的动力将农民土地转为国有，在商品化开发之后谋求暴利。<br />\r\n<br />\r\n　　第一轮对农民的土地剥夺催肥了地方财政。据中共中央党校研究室副主任周天勇先生的估算，改革开放以来，通过低价征用制度，从农民手中转移的利益大约有15万亿人民币，而卖地补偿给农民的不到其中的5%。<br />\r\n<br />\r\n　　许多农民因征地而致贫，形成4000万失地、失保和失业农民。鉴于第一轮征地潮对农民的大规模剥夺，我们必须警惕正在紧锣密鼓推进的第二轮征地潮，以城市化为名再行剥夺农民之实。<br />\r\n<br />\r\n　　目前一些地方推进的农民上楼运动，保障农民利益主要体现在让农民拥有农业公司股权、获得城市最低保障等。农民成为市民，在城乡区别大的地区是天大的恩赐，但所有这些保障背后的实质是，农民肯定无法享受到土地作为资产品溢价的权利，转而拥有从土地转换来的公司股权、分红权，鉴于土地溢价远远高于入股公司的分红，公司前景莫测，农民对于公司的管理权与建言权存疑，农民土地折股效果很难说一定能成功。<br />\r\n<br />\r\n　　也有成功的例子，今年玛纳斯县乐土驿镇周家庄村&ldquo;乐源&rdquo;农业专业合作社给农民按股权比例分红，各利益群体相安无事，因为在当地不存在城市化过程中土地溢价的前提，农民乐意入股合作社换取分红权。但在城市郊区、矿产与林业等资源较为丰富的地区，连公务员都将自己的户口迁回农村寻求厚利，农民不能获得土地与资源溢价，尖锐的利益冲突此起彼伏。<br />\r\n<br />\r\n　　如江苏省宜兴市官林镇政府征用2800亩农地空置五年，对失地农民又不做合理补偿，当地部分农民生计所迫，2008年2月22日开始在被地方政府强行征用的土地上复耕，这些被征用的土地主要建度假村、高尔夫球场、豪华别墅，本身是国务院明令禁止的。而江苏常州北港街道邹家村委江墅村农民，不满政府&ldquo;先用后征&rdquo;的违规征用土地，也没有按照法律法规给予失地农民补偿，于2008年2月29日发生冲突。一连串的事件，说明在圈地过程中存在着&ldquo;土地吃人&rdquo;的现实。<br />\r\n<br />\r\n　　据中央农村工作领导小组副组长、办公室主任陈锡文的估计，2006年以来，中国新增的数千万城镇人口中，真正转为城市居民的农民工可谓凤毛麟角。一些地方政府以统筹城乡发展或新农村建设的名义，在农民的承包地和宅基地上打主意，意图通过村庄撤并、擅自扩大城乡建设用地增减挂钩的范围等，侵害农民的土地权利。<br />\r\n<br />\r\n　　城市化过程如果不幸沦落为新圈地运动，有可能造成城市赤贫阶层，向城市输送源源不断的无消费能力与专门技术的低端产业工人。健康的城市化过程，会在土地流转与工业化的过程中，通过土地财富的再分配，造就一批拥有基本消费能力的中产收入阶层。当农民得到土地权证，并且这份权证足以让他们以中产阶层的身份进入工业化时代，当有领导人敦促农民不要轻易出售土地证以获得最丰厚的土地溢价时，这样的城市化进程才能培养中国最急需的中产阶层与中高端技术工人。<br />\r\n<br />\r\n　　中国目前紧锣密鼓的城市化进程，事实上是1949年之后的另一次土改。当年的土改是将土地分到农民手中，公社时期前后则是将土地集中国有化，现在的土改主要是将集体土地重新流入市场进行财富再分配。<br />\r\n<br />\r\n　　无论用何种方式，剥夺土地制造贫困阶层的办法都无助于中国的经济，无助于中国中产收入社会的壮大。无论怎么样的土改，必须有利于农民的利益，有利于工业化与城市化的进程，有利于财富为民所用。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (112,'<p>2010年12月27日，几位来自东北的宇通客户因为及时提到了车，在宇通大厦楼上的餐厅推杯换盏，喜形于色，还有一些来自天南海北的客户不得不选择在专门针对客户营业的宇通大厦客房里入住，等待提车的消息。</p>\r\n<p>过去的一年，这样的热闹场景一直在宇通大厦内上演。年产销4万辆的成绩相对于乘用车企业来说微不足道，但足以保证<a href=\"http://quotes.money.163.com/0600066.html\">宇通客车</a>稳住客车行业的第一把交椅。</p>\r\n<p>在宇通客车总裁汤玉祥看来，客车市场对于中国汽车行业来说是小众市场，仅凭客车产品并不足以保证宇通客车未来的发展和壮大，宇通也在考虑在适当的时候扩展业务的范围。</p>\r\n<p>&ldquo;宇通对于未来3~5年有着清晰的规划，可以肯定的是，宇通绝不会开展轿车业务。&rdquo;汤玉祥在接受《每日经济新闻》记者（以下简称NBD）采访时表示。</p>\r\n<p><strong>考虑拓展轻型客车</strong></p>\r\n<p><strong>NBD</strong>：宇通实现了年产销4万辆，您觉得这一成绩对于客车行业的意义是什么？</p>\r\n<p><strong>汤玉祥</strong>：今年整个客车行业大概产销14万~15万辆，基础是中国经济发展的需要。宇通产销4万台，行业实现了一定的集中度，同时科学合理性提高，原来我们的集中度太低，导致行业发展不健康。</p>\r\n<p><strong>NBD</strong>：宇通2010年销量同比增长1万多辆，导致现在产能出现不足，是不是年初做计划时对2010年市场的预判有点保守？</p>\r\n<p><strong>汤玉祥</strong>：预判保守是一方面，我们没想到行业会有这么高的增长，但是我们的产能不是一天两天能建立起来的。2007年我们已经计划要把产能扩展至5万辆，但由于各种原因没有实现这一目标。</p>\r\n<p><strong>NBD</strong>：乘用车企业纷纷公布&ldquo;十二五&rdquo;规划，宇通有类似的规划么？</p>\r\n<p><strong>汤玉祥</strong>：宇通没有&ldquo;十二五&rdquo;规划，但对未来3年有一个短期的规划。客车这部分我们准备做到5万辆，加一部分<a href=\"http://quotes.money.163.com/1399941.html\">新能源</a>或者专用车可能会到6万台。为了配合这一目标的实现，我们准备再建5条生产线。</p>\r\n<p><strong>NBD</strong>：有消息说金龙正在筹划做轿车，宇通会拓展轿车业务吗？</p>\r\n<p><strong>汤玉祥</strong>：我们绝不会做轿车，但的确考虑过业务的拓展，比如轻型客车等等。</p>\r\n<p><strong>NBD</strong>：客车行业通常都是B2B的营销模式，这导致客车企业不太重视品牌建设，宇通在品牌打造方面是否有计划？</p>\r\n<p><strong>汤玉祥</strong>：品牌溢价是由市场决定的，不是我们自己来决定的。品牌建设有一个过程，虽然我们在行业内用户的知名度很高，但我感觉现在宇通还没到&ldquo;吹牛&rdquo;的时间。不过我们会陆续加大品牌推广方面的投入，比如央视的广告投放。</p>\r\n<p><strong>今年客车市场不乐观</strong></p>\r\n<p><strong>NBD</strong>：宇通的产品生命周期是怎样规划的？</p>\r\n<p><strong>汤玉祥</strong>：宇通车型更新的速度要快过乘用车，我们的产品生命周期大概在3~5年。</p>\r\n<p><strong>NBD</strong>：您对客车行业未来的发展是否作过预测?</p>\r\n<p><strong>汤玉祥</strong>：我认为客车行业会随着中国运输和全球运输的需要而发展，比如针对国内下一步公交优先、交通拥堵的问题，我们肯定会做好配合。还有一个趋势是，下一步行业集中度会逐渐提高，管理水平和工艺能力会逐步提高，宇通要起到引领行业的作用，并逐渐与国外企业接轨。</p>\r\n<p><strong>NBD</strong>：您怎么看待2011年的汽车市场？</p>\r\n<p><strong>汤玉祥：</strong>今年客车市场，以及卡车市场都不乐观。目前我们面临的最大困难就是产能不足，产能限制了我们的增长速度。有需求和订单，但是无法生产那么多车，这是很痛苦的。我认为今年的客车市场格局还会延续去年的状态。</p>\r\n<p><strong>新能源技术远未成熟</strong></p>\r\n<p><strong>NBD</strong>：包括宇通在内的国内客车企业都很重视出口，且这一块竞争非常激烈，宇通有什么样的策略？</p>\r\n<p><strong>汤玉祥</strong>：目前客车出口的确存在不规范竞争，出现了海外竞争国内化的现象，使得我们的整体出口降低了10%~20%。不规范竞争会损害中国产品的形象，因此客车出口需要严格的管理。宇通的出口采取经销和直销相结合的模式，而且以经销居多，即寻找海外销售合作伙伴。</p>\r\n<p><strong>NBD</strong>：海外业务占宇通销量的比重是多少？</p>\r\n<p><strong>汤玉祥</strong>：目前最多30%，我不会让这个比例增长到40%，因为海外市场稳定性很差，不稳定性会直接影响我们的出口业务，进而影响宇通整体的经营业绩。</p>\r\n<p><strong>NBD</strong>：目前宇通在海外有几家工厂，未来会扩大规模么？</p>\r\n<p><strong>汤玉祥</strong>：我们目前在伊朗和古巴有工厂，未来不会再增加海外工厂，主要是我们现在做海外市场的时间还很短，只有五六年。而且稳定的海外市场太少，还不具备进一步扩张的基础。</p>\r\n<p><strong>NBD：</strong>现在新能源汽车很热，宇通有什么新能源规划？</p>\r\n<div class=\"gg200x300\">&nbsp;</div>\r\n<p><strong>汤玉祥</strong>：其实我们在10年前就已经开始新能源方面的规划，但目前中国新能源技术远远没有达到成熟的程度。因此我们虽然一直在开展新能源产品方面的研究，但并没有过多地对外宣传。</p>\r\n<p><strong>NBD</strong>：宇通已经在新乡开始了新能源客车的示范运行？</p>\r\n<p><strong>汤玉祥</strong>：是的，我认为新能源汽车的应用应该从公共交通开始，而且会率先得到快速发展。我们的示范运行严格按照国家标准，在相对较大的城市进行尝试，希望通过实际运行提高我们的技术水平。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (115,'<p>据知情人士周一表示，美国著名社交网站Facebook已经从<a href=\"http://quotes.money.163.com/usstock/hq/GS.html\">高盛</a>集团和德国的一家投资公司Digital Sky Technologies手中获得了5亿美元的投资。</p>\r\n<p>在该交易中，高盛和DST对Facebook的估值达到了500亿美元，超过了eBay、<a href=\"http://quotes.money.163.com/usstock/hq/YHOO.html\">雅虎</a>、<a href=\"http://quotes.money.163.com/usstock/hq/TWX.html\">时代华纳</a>等公司的市值。</p>\r\n<p>此次与高盛达成的交易预示着Facebook的不断壮大。这些新的资金将使Facebook在争取优秀员工、开发新产品和进行潜在的收购上具备更多的弹药。此次投资还将允许老股东，包括Facebook的员工，套现至少部分股份。</p>\r\n<p>在此项交易进行的同时，美国证券交易委员会已经开始对日益受欢迎的私募股权市场中互联网公司的股份交易展开了调查，其中包括Facebook和Twttter等。一些专家表示，该调查的重点是查明某些公司是否不正当地使用私营市场以回避公开信息披露。</p>\r\n<p>尽管Facebook的高管拒绝让公司上市，但是此次投资将会增加Facebook在此方面的压力。<a href=\"http://quotes.money.163.com/usstock/hq/MSFT.html\">微软</a>公司和<a href=\"http://quotes.money.163.com/usstock/hq/GOOG.html\">谷歌</a>的股票就曾在私营市场大受欢迎，这最终迫使它们公开上市。</p>\r\n<div class=\"gg200x300\">&nbsp;</div>\r\n<p>至今，Facebook的首席执行官马克&middot;扎克伯格仍排出了公司公开上市或出售的可能。但熟悉此次筹资活动的消息人士称，Facebook的董事会已经有意在2012年考虑出售该公司。</p>\r\n<p>此次交易可能会激起人们对Facebook在公开市场的市值的讨论。尽管该公司未公布具体财务信息，但分析师预计该公司是盈利的，并且每年的营收预计可达20亿美元。</p>\r\n<p>参与该交易谈判的人士表示，根据协议条款，高盛已经向Facebook投资了4.5亿美元，而俄罗斯投资公司DST已经投资了5000万美元。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (116,'<p>热闹的2010年过去了，伴随着各式各样的盘点，给力的，V5的，淡定&hellip;&hellip;</p>\r\n<p>在2010年，很多人经历了一次标志性的选择在腾讯和360之间，这是一个艰难的选择；2010，很多人也经历了一次标志性的选择写微博。</p>\r\n<p>辞旧迎新，因为有了微博，而多了一种形式和角度。中国银行业协会首席经济学家巴曙松说了，微博不错，省去了给许多朋友发新年短信的成本。而原新浪网 CEO、现点击科技总裁王志东则在微博上表述自己的新年：&ldquo;新年第一天，晚上就想干一件最无聊的事情。结果呆想了一整晚都不知道该干吗，然后想困了，然后 睡了。&rdquo;</p>\r\n<p>封存2010，走好2011，有人梳理自己的生命意识，有人静定生慧，一起看微博上的财经大佬的新年经历，热闹之外也有冷思考</p>\r\n<p><strong>李东生黄酒会晓波</strong></p>\r\n<p>2011年1月1日，微博上来了个李东生，也就是TCL集团股份有限公司董事长，他自称&ldquo;致力于中国电子产业发展的老兵&rdquo;。一时间粉丝窜至万余人， 其中一位是TCL集团品牌管理中心总经理梁启春，他说&ldquo;有失远迎&rdquo;，李东生自己说明：&ldquo;受曹国伟（新浪CEO）邀请。&rdquo;有人说&ldquo;起步太晚&rdquo;，李东生说&ldquo;今 天开博，今后努力&rdquo;。他的夫人、普乐普公关公司总裁魏雪在微博上为他宣传，&ldquo;东生也学着戴上围脖了。&rdquo;</p>\r\n<p>果然，1月2日，李东生写微博一直发到1月3日的凌晨。他和<a href=\"http://hkquotes.money.163.com/html/000268.html\">金蝶国际</a>软件集团董事局主席徐少春沟通了高尔夫球技，他和华工的师弟师妹叙旧话新，他也遭遇了微博失灵之惑。</p>\r\n<p>他思去念新：&ldquo;我们确实和三星、索尼这些国际企业有差距，但这种差距过去十年在不断缩小；我相信再有十年，中国企业就有能力和跨国企业全面抗衡。&rdquo;</p>\r\n<p>他还感叹了2010年的最后一天，&ldquo;在北京家里，和吴晓波（财经作家）温上黄酒聊了大半天，回顾了企业这30年的沟沟坎坎。国家要强大一定要有强大 的经济支撑，而强大的经济需要强大的企业。晓波说&lsquo;这些年各种诱惑很多，能坚守做实业的不多了。这是我最佩服你的。&rsquo;　30年，我抱定实业报国的理想，今 后也义无反顾。&rdquo;</p>\r\n<p>魏雪在微博上是这样描述的：&ldquo;北京很冷，零下10度，吴晓波老师一早冒着寒风来到家里，我温了黄酒，晓波老师和我先生聊创业历程，产业未来，相谈甚欢。&rdquo;</p>\r\n<p><strong>王巍的酒茶咖啡牌</strong></p>\r\n<p>元旦之夜，中华全国工商业联合会并购公会会长王巍在微博上感叹：&ldquo;三里屯北街的商店群已经开业，几乎全球最时尚的高端品牌都入驻了。十年前还是低档酒吧，现在变化真大。忽然想起二十年前在纽约过元旦的晚上，无法想象中国会有这样的地方。&rdquo;</p>\r\n<p>对于三天假期，王巍在1月2日的深夜用微博记录，&ldquo;下午被约出去打牌，双扣。当年在哈巴雪山上向山友学会的，每年爬山时都用打牌来应对高山反应和闲 暇时光。先定的一家佛家茶馆，佛乐扰人，茶也极贵。后到上岛咖啡，主人甚至提供纸牌。打了两圈，均大胜，兆头不错。在沈计靓汤吃晚饭。回来换电脑，从索尼 到<a href=\"http://quotes.money.163.com/usstock/hq/AAPL.html\">苹果</a>机，新年礼物。明日还有一天逍遥，乐也。&rdquo;</p>\r\n<p><strong>李瑜三亚奇遇记</strong></p>\r\n<p>1月又至，去年1月，禹容网络创始人CEO、前<a href=\"http://quotes.money.163.com/usstock/hq/GAME.html\">盛大游戏</a>CEO兼董事李瑜辞职与丈夫一起创办禹容网络，元旦假日，全家在三亚度假，李瑜还在微博上写下三亚的奇遇很多圈里圈外的朋友，江苏五星投资控股集团董事长汪建国是其中之一，汪建国也在微博上发布了一起晚餐的图片。</p>\r\n<p>对此巧遇，零点研究咨询集团董事长袁岳(<a href=\"http://yuanyueblog.blog.163.com/\">博客</a>)也在微博上提议：&ldquo;如果我们与一群企业家或者一群白领相约出行，是一种社交也是一种学习，旅行是比读书更棒的学习方法呢。&rdquo;</p>\r\n<p><strong>潘海东继续&ldquo;爱问</strong>&rdquo;</p>\r\n<p>2010年12月31日，互动百科CEO潘海东在微博上以自嘲的方式来总结和畅想：&ldquo;爱互动百科，爱wiki；爱说不靠谱，爱问李彦宏，也爱&lsquo;知识奴隶&rsquo;的玻璃贴；我学系统工程，也拍时尚杂志；我不是潘总，我是海东。&rdquo;</p>\r\n<p>海东就是海东，经典总要继续。2011年1月2日，他在微博上开始揭露<a href=\"http://quotes.money.163.com/usstock/hq/BIDU.html\">百度</a>上公开的代孕广告。</p>\r\n<p><strong>地产大佬在国外</strong></p>\r\n<p>元旦假期，SOHO中国董事长潘石屹和夫人张欣在英国，北京时间2011年1月1日零时，&ldquo;我们在伦敦的守基阿芬第灵墓前，为中国祈祷、为朋友们祈 祷，为家人祈祷。&rdquo;接着，去朋友家做客，发美食微博，不亦乐乎，只是，小潘的手机出了点儿问题，&ldquo;不知是不是新年短信太多的缘故，我的手机今天彻底&lsquo;死 机&rsquo;了。只能等到回国再修了。&rdquo;</p>\r\n<p>&ldquo;2011年第一缕阳光，风急浪高&hellip;&hellip;&rdquo;深圳万科股份有限公司董事长王石在日本，他是去考察日本的建筑，谈的最多的是设计师安藤忠雄，经常在微博上 发美景美图的王石，常常是在旅途中，时值新年，他自己澄清：&ldquo;万科的董事长并不是天天旅游，还要上网看资料，回信息；召开董事会、股东大会、必要的应酬等 等。&rdquo;</p>\r\n<p><strong>三个男人回家</strong></p>\r\n<p>元旦当日，前Google全球副总裁兼中国区总裁、现创新工场CEO李开复在微博上贴出&ldquo;三个孙女陪奶奶（我妈妈）打麻将&rdquo;的照片，&ldquo;孙女每次见到奶奶，都要送奶奶一件玩具。&rdquo;</p>\r\n<div class=\"gg200x300\">&nbsp;</div>\r\n<p>1月2日，4399游戏董事长、著名天使投资人蔡文胜在微博上记下&ldquo;父亲的生日&rdquo;，&ldquo;今天是父亲的生日，回老家探望父母。我们一家有兄 弟姐妹四个加孩子们共十几个人，平时大家都居住在不同城市，很难得全部到齐了。我知道父母亲最开心的并不是给他们多少财物，而是一家人能经常聚会，聊聊 天，谈谈家常。照片（蔡文胜微博上有贴图）是父母亲和我哥哥两个孩子，妹妹两个孩子，左右二边是我的两个孩子。&rdquo;</p>\r\n<p>因为母亲生病，华远集团总裁任志强的新年有点儿累，&ldquo;新年第一天，最让全家人都高兴的事是母亲可以开口说话了。看来术后的情况良好。&rdquo;</p>\r\n<p>2010年的最后一天，任志强这样记录，&ldquo;今天是姐姐和嫂子的生日。一大家人晚上在一起聚餐。尽管昨晚一直忙到今早，哥哥，嫂子，老婆，妹妹，妹夫 们都睡眼蒙眬，但母亲的手术成功还是让大家暂时缓解了紧张的心情。姐姐为了母亲的病，刚从德国飞回北京，还在倒时差。加上下一代十多人排班，轮流去医院。 希望能岁岁平安。&rdquo;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (117,'<p>刚刚从美国读书回来的许丽准备重新装修房子，她整理出一大批闲置的二手物品，衣服、饰品、包包，甚至还有一个装猫咪用的篮子。</p>\r\n<p>如何处置它们成了一件让人头痛的事。按照美国的惯例，她登录了分类信息网站，在网上填写买卖信息，并留下联系方式，希望能够很快将杂物处理掉。</p>\r\n<p>不到两天许丽就将猫篮卖给了一个打电话来的男生，20元钱的售价，让对方兴奋不已，毕竟，一个质量过得去的猫篮，最起码也要上百元。</p>\r\n<p>除了二手物品的交换，许丽发现，她要&ldquo;过渡&rdquo;租房、买辆二手车以及找个钟点工的愿望都能通过这个网站实现。它就像一个海量信息发布的聚合平台一样。</p>\r\n<p>只找所有人需求中的最大公约数，挑出最普遍的那部分需求，这在许丽登录的百姓网的CEO王建硕看来，是分类信息网站安身立命的基础。</p>\r\n<p>&ldquo;分类广告是人类的基本需求，只是现在把原本放在平面媒体上的分类信息搬到互联网上而已，&lsquo;在线分类＋社区互动&rsquo;给了分类广告新的生存平台。&rdquo;</p>\r\n<p>提供网民生活领域的分类信息，定位本地化的生活应用服务，这就需要分类信息网站能有大而全的信息量。然而，承载这个庞大信息量的平台却简单得出乎想 象。王建硕将百姓网的模式，解读为&ldquo;只有四个网页&rdquo;，即首页、目录、信息填写、发布，但就是在这样一个简单的组合背后却是已达到年千万元级的收入规模。</p>\r\n<p><strong>分类网站大浪淘沙</strong></p>\r\n<p>10多年前，美国人克瑞格发明了一种网络表格，将各种信息在网上进行分类，给用户提供交易买卖、房屋租赁、求职招聘、求助救济等服务，　10年后， 克瑞格的公司craigslist网站年创利润已超过1000万美元，而craigslist就是分类信息网站。显然，中国的人多地广给分类信息市场的拓 展带去了更大的想象力。但在国内分类信息网站方兴未艾之时，美国的<a href=\"http://quotes.money.163.com/usstock/hq/EBAY.html\">eBay</a> ECG已成为了全球最大的分类网站集团，年收入超10亿美元。</p>\r\n<p>2005年，觊觎中国分类市场的eBay把其在该领域的第一枚中国棋子放在了客齐集身上（百姓网前身），王建硕至今仍记得5年前客齐集刚成立时，每天发布的信息只有个位数，网站浏览量更是少得可怜，对市场的培育远未开始。</p>\r\n<p>但是这一波浪潮来得很快，由于进入门槛低，在2005￣2006年之间国内分类信息网站已迅速膨胀到几千家，就连门户大哥们<a href=\"http://quotes.money.163.com/usstock/hq/SINA.html\">新浪</a>、<a href=\"http://quotes.money.163.com/usstock/hq/SOHU.html\">搜狐</a>、网易、腾讯都陆续推出了自己的分类频道。用户使用习惯及市场规模正在逐渐确立。</p>\r\n<p>然而，这并非是一个能赚快钱的行当。在经历2008年￣2009年金融危机寒冬后，散伙者、转行者比比皆是，剩下的客齐集、58同城网与赶集网仍然 固守阵地。尔后，从eBay阵营中分拆出来的客齐集正式改名为百姓网，王建硕也就此结束了其外资在华职业经理人的身份，开始自主操盘百姓网。</p>\r\n<p>谈及经历的这波浪潮，王建硕坦言这其中最大的门槛在于时间。谁能清晰地固守自有的模式，即抓住互联网的本源把分散的信息集合起来并让其流动。</p>\r\n<p>然而，要想在大浪淘沙中生存下来，分类信息网站背后的资本实力其实也不容小觑。自2008年以来，百姓网已经历两轮融资，首轮来自金沙江创投，第二轮则吸引了曾投资Twitter、Second Life等互联网新兴模式的美国VC标杆资本。</p>\r\n<p><strong>百姓网的&ldquo;固执&rdquo;</strong></p>\r\n<p>一位投资人告诉记者，规模是分类信息类网站拉开与竞争对手差距的首要前提，核心价值在于引导用户形成一定的使用黏性。</p>\r\n<p>王建硕显然也明白个中道理，大浪淘沙之后，他开始总结分类信息网站立足的基本前提，他将下一个竞争点归结于是建立在规模化基础上的一种服务比拼。</p>\r\n<p>目前房产、工作、二手车买卖、物品交易为百姓网浏览量最大的类目，虽然早有专业的房地产交易网站、招聘网站，甚至是淘宝在前，王建硕却认为百姓网与它们没有任何的竞争冲突。</p>\r\n<p>他向记者举例，比如招聘，只找需求量大的工种，厨师、服务员、工人、快递等等。&ldquo;招聘工人的数量永远比招聘程序员要多得多，企业在专业招聘网站上能招到CFO／CTO，但很少有企业会想去那里招一个普通的工人。&rdquo;他说。</p>\r\n<p>王建硕一再强调，百姓网的作用只是提供一个在本地能方便发布和查找信息的途径，最后要求能见面交易。&ldquo;一旦不是同城，不是面对面交易，不是最基本的核心需求，就是其他那些B2C网站该干的事了。&rdquo;</p>\r\n<p>但是，一个成立已有5年的互联网企业迄今为止却只有29名员工，其中绝大部分负责产品、运营和质量控制。这29人的产出却并不少。资料显示，目前百 姓网覆盖347个城市，每月浏览量近10亿次，每月新增信息量超过300万条。但与竞争对手每月砸上百万美元用于营销，建立遍布全国的销售团队相比，王建 硕却并不认同这种商业推广方式，坚持口碑效应。</p>\r\n<p>他给记者算了一笔账，维持一个销售团队运作的单人成本在2000￣3000元／月，一百通销售电话中可能就只能成交一单，这也就意味着单个付费用户的支出必须在2000元／月以上，才能维系销售团队的运作。</p>\r\n<p>&ldquo;目前百姓网每一个类目信息都按发布时间的新鲜度排序，但用户可通过支付一定费用使自己信息置顶，付费用户最便宜的是10元／30天，最贵则为300元／7天。&rdquo;他说，&ldquo;低价是因为没有销售团队，就无需将这部分的开支转嫁到用户身上。&rdquo;</p>\r\n<div class=\"gg200x300\">&nbsp;</div>\r\n<p>但一位接触过分类信息网站的业内人士告诉记者，口口相传的这种营销模式只适合于创业初期，一旦企业步入成长阶段，就有可能拖垮其试图快速前进的步伐。分类信息网站真正的价值其实与其他互联网同行并无区别，即网站的有效点击率与成交量。</p>\r\n<p>然而，王建硕却并不在乎。&ldquo;现在是分类广告网站最初期阶段，这行不是靠钱砸出的游戏，相反现阶段用户是否喜欢，能促成多少量的成交是关键。&rdquo;</p>\r\n<p>易观国际分析师任洋辉认为，现阶段分类信息网站信息同质性高，用户对网站的黏性较低。</p>\r\n<p>&ldquo;在信息严重同质的环境下，分类信息网站用户互动性不强、用户的应用体验不足都是目前制约着分类信息网站发展的问题。&rdquo;任洋辉说。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (172,'<p>【基本介绍】<br />\r\n无需付费购买，到官方指定下载地址下载安装后即可以使用 <br />\r\n具有灵活点播的功能，随点随看，时间自由掌握<br />\r\n操作简单，界面简洁明了<br />\r\n掌握最先进的P2SP传输技术，传输速度更快，更节省带宽 <br />\r\n占用CPU、内存、带宽等系统资源占用少，不影响其它操作。 <br />\r\n支持多种视频文件格式 <br />\r\n播放流畅，首次连接速度快 <br />\r\n采用最新解码技术，使网络观看拖动无延时，达到播放本地文件的效果<br />\r\n最高清画面，支持网络高清点播，最高码率达到1300。 <br />\r\n采用脉冲式连接和自动重连策略，保证用户稳定、流畅的观看。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (173,'<p>【基本介绍】<br />\r\n千千静听是一款完全免费的音乐播放软件，拥有自主研发的全新音频引擎，集播放、音效、转换、歌词等众多功能于一身。其小巧精致、操作简捷、功能强大的特点，深得用户喜爱，被网友评为中国十大优秀软件之一，并且成为目前国内最受欢迎的音乐播放软件。<br />\r\n<br />\r\n【软件特色】<br />\r\n拥有自主研发的全新音频引擎，支持DirectSound、Kernel Streaming和ASIO等高级音频流输出方式、64比特混音、AddIn插件扩展技术，具有资源占用低、运行效率高，扩展能力强等特点。<br />\r\n<br />\r\n千千静听支持几乎所有常见的音频格式，包括MP/mp3PRO、AAC/AAC+、M4A/MP4、WMA、APE、MPC、OGG、WAVE、CD、 FLAC、RM、TTA、AIFF、AU等音频格式以及多种MOD和MIDI音乐，以及AVI、VCD、DVD等多种视频文件中的音频流，还支持CUE音轨索引文件。<br />\r\n<br />\r\n通过简单便捷的操作，可以在多种音频格式之间进行轻松转换，包括上述所有格式（以及CD或DVD中的音频流）到WAVE、MP3、APE、WMA等格式的转换；通过基于COM接口的AddIn插件或第三方提供的命令行编码器还能支持更多格式的播放</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (174,'<p>【基本介绍】<br />\r\n&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;Google Chrome(谷歌浏览器)是由Google开发的一款可让您更快速、轻松且安全地使用网络的浏览器，它的设计超级简洁，使用起来更加方便。<br />\r\n<br />\r\n【软件特点】<br />\r\n1、更好的插件稳定性和性能，特别是视频方面。<br />\r\n2、速度更快。相比于第一个测试版，JavaScript V8引擎的SunSpider测试成绩已经加快了40％，V8测试成绩也提高了50％，今后还会继续改进。<br />\r\n3、书签管理器和隐私控制。通过书签导入、导出，可以在Chrome和其它浏览器之间轻松切换，另外管理大量书签也更简单了。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (176,'<p>今年1月份，泰国曼谷上空出现了十年以来的首次日食，这是一架飞机在日食背景下映衬出来的情景。宋卡太子大学（PrinceofSongklaUniversity）的萨克什恩&middot;布恩瑟温介绍称，在泰国上空，此次日环食遮住了太阳57%至80%的区域，因地理位置不同，泰国各省份所看到的遮盖区域也不尽相同。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (177,'<p><span class=\"cDGray\"><font color=\"#727171\">这些旖旎的风景，从一个侧面反映出了气候对地球环境的影响。</font></span><a class=\"cDRed\" href=\"http://news.163.com/photoview/05RQ0001/11765.html\"><font color=\"#ba2636\">[</font></a></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (178,'<p>11月9日，美国洛杉矶，池谷&mdash;村上彗星划过夜空。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (179,'<p><span class=\"cDGray\"><font color=\"#727171\">微观世界摄影大赛公布2010年获奖者名单,展示了一场视觉盛宴。</font></span><a class=\"cDRed\" href=\"http://news.163.com/photoview/05RQ0001/11433.html\"><font color=\"#ba2636\">[</font></a></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (180,'<p><span class=\"cDGray\"><font color=\"#727171\">这些怪异的冰川洞穴中环境温度有时会低至-20度以下</font></span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (181,'<p><span class=\"cDGray\"><font color=\"#727171\">在微距摄影师约翰&middot;霍尔门看来，昆虫隐藏着数不清的魅力。</font></span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (182,'<p><span class=\"cDGray\"><font color=\"#727171\">对于数百万美国人来说，秋天是狩猎季节的开始。</font></span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (183,'<p>据英国《每日邮报》报道，英国艺术家阿德里安&middot;格雷在海滩上利用石头创造出令人惊奇的艺术作品。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (184,'<p>当地时间8月26日，由印度尼西亚和美国科学家组成的探险小组宣布，他们利用无人遥控潜水器在对印尼苏拉威西岛附近的卡维奥巴拉特海底火山勘测时，意外发现一批新的海洋生物。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (185,'<p>2010年9月，&ldquo;机器宇航员2号&rdquo;将有可能前往国际空间站。该机器人成员的主要工作任务在于执行国际空间站中危险及重复的太空作业，以节省人手和时间使得空间站的其他宇航员可以从事其他太空研究工作</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (186,'<p>2010年6月25日世界捕鲸委员会（IWC）年度大会在摩洛哥阿迪加尔闭幕，这次原本旨在达成新的捕鲸管理法规的国际努力最终无果而终。这意味着，日本依然可以&ldquo;科研捕鲸&rdquo;的借口每年捕杀1500头鲸，其中包括在南大洋鲸类禁猎区捕鲸。日本打着&ldquo;科研捕鲸&rdquo;的幌子行&ldquo;商业捕鲸&rdquo;之实，为达到自己的商业目的、政治目的，日本面对国际反对舆论仍有自己强硬的理由，从另一个角度来看，这些&ldquo;理由&rdquo;是如此不堪一击，甚至充满了欺骗和谎言。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (187,'<p>6月23日第四十届格拉斯顿伯里音乐节在英国格拉斯顿伯里的沃西农场开幕。由奶农迈克尔&middot;伊维斯于1970年创办的音乐节，如今已成为欧洲最大的音乐节。(</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (188,'<p>2011年在全球各地逐渐揭开面纱，庆贺新年伊始是世界各国各地区的普遍习俗，各地民众纷纷以各种庆祝活动迎接新年的到来。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (226,'<p>大家好！今天专家答疑9:30开始，专家是好买基金(微博)研究中心研究总监乐嘉庆(微博)，敬请关注！</p>\r\n<p>[网 友] 游客314390的提问:专家你好：我定投了富国天惠、国投瑞银稳健、华夏优势均亏损，要停止定投吗</p>\r\n<p>[乐嘉庆] 你好，从你持有的基金来看在，这3只基金其实都还不错，近期的亏损很有可能是受市场普跌环境的影响，从这些基金的基金经理以及长期表现来看，还是可以继续持有。 [09:44:38]</p>\r\n<p>[网 友] 游客980176的提问:老师，我从08年开始定投国泰金龙行业，每月1000，现在的收益有8%%uFF0C是否该继续，还可以如何优化？</p>\r\n<p>[乐嘉庆] 你好，国泰金龙上涨动力一般，建议可以等市场反弹的时候市场逢高赎回。 [09:56:25]</p>\r\n<p>[网 友] 游客208717的提问:你好，我每月定投嘉实300和嘉实服务各200，华夏优势300，现在想多投一点，请问哪支基金适合多投？</p>\r\n<p>[乐嘉庆] 你好，嘉实服务比较合适。 [10:08:48]</p>\r\n<p>[网 友] 游客104574的提问:乐老师您好！富国天惠与富国天成买哪个好</p>\r\n<p>[乐嘉庆] 你好，富国天惠的基金经理比较喜欢长期稳定增长的成长股，富国天成的基金经理在选股的时候比较偏重基本面，从规模上来看，富国天惠要大于富国天成，管理的难度肯定也大于富国天成。 [10:21:34]</p>\r\n<p>[网 友] 游客746349的提问:乐总您好：已经定投富国天惠、诺安灵活配置、华安宝利、国投瑞银稳健增长半年，都处于亏损。问1：在4支中留2支，在沪深300选1支，大成标准普尔500，做1个组合；问2，现在买那个债券基金好？请推荐</p>\r\n<p>[乐嘉庆] 你好，其实这4只都不错，今年的亏损受市场普跌的影响较大，一定要留的话可以考虑一下诺安灵活配置和华安宝利。做组合的话建议留一个指数型基金就好了，嘉实沪深300不错，可以考虑。短期债市累及了不少的不利因素，因此短线投资者需要适当规避风险，尚未建仓的建议暂缓建仓步伐。如果投资期限在6个月以上的话，仍建议选择买长久期的信用债。 [10:46:15]</p>\r\n<p>[网 友] 游客136917的提问:乐总您好。泰达红利、国泰金鹰、国泰稳健、嘉实优质、汇添富价值、广发聚丰、诺安配置。选三只中长期有定投价值的基金。老师建议下。谢谢</p>\r\n<p>[乐嘉庆] 你好，从中长期的持有期来看，诺安灵活配置、嘉实价值优势和国泰稳健都不错。 [10:49:20]</p>\r\n<p>[网 友] 游客104574的提问:乐老师您好！富国天惠与富国天成想定投，买哪个好，谢谢！</p>\r\n<p>[乐嘉庆] 你好，已回答。 [10:49:32]</p>\r\n<p>[网 友] 游客611571的提问:金鹰中小盘亏损严重，还有救吗？现在都不敢补仓了？</p>\r\n<p>[乐嘉庆] 你好，金鹰中小盘主要的重仓股票并没有变化，跌无可跌之时建议耐心持有。 [10:54:37]</p>\r\n<p>[网 友] 游客989011的提问:乐总，未来定投五年以上，是定投普通指数基金还是etf指数基金好，你比较看好那个，帮我推荐两个能覆盖大中小市值的指数基金，我是激进型的 谢谢</p>\r\n<p>[乐嘉庆] 你好，首先，指数型基金不适合做长期定投，市场十年一梦回到原点，并且指数型的基金是被动复制指数的，若你是激进型风格的话，建议可以买一些偏股型基金或者混合型基金。 [11:06:56]</p>\r\n<p>[网 友] 游客168396的提问:老师好，易方达增强回报今年表现不佳，为什么专家都推荐，乐老师怎样看这只基金。</p>\r\n<p>[乐嘉庆] 你好，今年基金表现受市场影响普遍不佳，虽然这只基金今年以来收益跌破零，但在同类型债券基金中表现仍是比较好的，并且从历史业绩来看，这只基金也很不错，建议可以给与关注。 [11:14:15]</p>\r\n<p>[网 友] 游客705227的提问:乐总您好！请您分析一下151006富国天惠为何近段时间下跌如此多，是否与规模发展太快或其它原因。富国天惠还有投资价值吗？麻烦您指导一下。谢谢！</p>\r\n<p>[乐嘉庆] 你好，不可否认，规模的快速发展会给管理带来一定的困难，再一方面，近期的市场表现也不佳，股市的下跌对基金的净值会产生一定的影响。不过从基金经理人来看，富国天惠的基金经理经验丰富，对个股的研究也比较深入，可以继续持有，但建议需谨慎一些。 [11:17:11]</p>\r\n<p>[网 友] 游客104720的提问:你好，今天加仓有风险吗？我想加仓300基金？</p>\r\n<p>[乐嘉庆] 你好，不管你在何时进行投资，投资是一定会伴随相应的风险的，目前的点位是已经比较低了，可以考虑开始逐渐布局，但近期的市场日仍较震荡，建议慢慢来，不要操之过急。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (227,'<p>穿越&ldquo;地雷阵&rdquo;</p>\r\n<p>2011年，股市&ldquo;黑天鹅事件&rdquo;频发，新老基金穿越&ldquo;地雷阵&rdquo;步履艰难；不幸中雷者前赴后继，这更让幸存者如履薄冰。在上市公司地雷阵中，还有哪些地雷可能在2012年引爆？基金公司是否能够各显神通、化险为夷？</p>\r\n<p>《第一财经（微博)日报》记者试图从基金集中持股或同系抱团的个股中，提前发掘那些存在&ldquo;隐忧&rdquo;的不确定事项，其中包括行业和重点项目前景不明、重大合同不确定、应收账款快速增长且出现收回困难，以及对大客户依赖过度等。</p>\r\n<p>吉林敖东&mdash;&mdash;梦碎新药技术概念</p>\r\n<p>12月15日，吉林敖东公告称，因生物人工肝项目没有获得中国药监局的批准，为控制投资风险，保护公司利益，决定暂不追加2500万美元投资。而在此之前，该公司已经投入200万美元。</p>\r\n<p>吉林敖东指出，作为美国生命治疗公司Vital Therapies，Inc.（下称&ldquo;VTI&rdquo;）的股东，公司将密切关注生物人工肝项目的进展和报批情况。是否继续投资，公司将根据项目的进展情况与VTI公司继续协商。</p>\r\n<p>公告当日，吉林敖东股价跌停，次日继续下跌2.98%。根据吉林敖东三季报，在其前十大流通股股东中，共有7只基金（含1只指数基金）和一家券商，其中6只主动管理型基金分别来自博时基金、华夏基金、诺安基金和银华基金。</p>\r\n<p>今年5月9日，吉林敖东决定以自有资金投资200万美元购买VTI 5%~6%的股权，同时授权吉林敖东在6个月内追加2500万美元投资的投资权，双方签署的投资协议还规定，如在6个月追加投资的期限到期时，生物人工肝尚未获得中国国家药监局的批准，双方将对购买权合适的延期时间进行协商。</p>\r\n<p>横店东磁&mdash;&mdash;光伏行业&ldquo;入冬&rdquo;</p>\r\n<p>2011年以来，在全球经济增速放缓背景下，欧洲国家下调光伏补贴幅度。另外，今年10月，7家美国光伏企业申请对中国出口的太阳能电池进行反倾销和反补贴调查。至此，光伏行业遭遇了寒冬，而光伏行业上市公司也难逃严寒，开始放慢了前进步伐。</p>\r\n<p>主营业务为磁性器材的横店东磁，2009年起豪掷数十亿元进军光伏产业，当年10月，公司变更激光打印机显影辊技术改造项目，用2.617亿元投资建设100MW晶体硅太阳能电池片生产线，并于2010年3月加码投资3.16亿元建设该项目。2010年6月~11月，横店东磁再度砸入23.23亿元投入到晶体硅太阳能电池片及组件项目中。</p>\r\n<p>今年光伏行业已然步入寒冬，但就在8月，横店东磁仍然投资约21.52亿元在乌海市经开区建设年产6000吨多晶硅及光伏产业项目。至此，公司在光伏行业里的投资已达到50.54亿元，是其最近一期总资产的1.16倍，净资产的1.62倍。</p>\r\n<p>在这样的情况下，横店东磁终止了股权激励计划，同时预计公司2011全年业绩归属于上市公司股东的净利润比上年同期下降20%~50%。</p>\r\n<p>横店东磁称，&ldquo;今年以来，公司所投资的光伏产业由于受产能过剩、欧债危机、美国经济低迷等外围因素影响，致使市场需求大幅度萎缩，进而严重影响了公司今年及以后未来一两年的经营业绩。&rdquo;</p>\r\n<p>而在横店东磁最新三季报的前十大流通股股东名单中，易方达旗下的易方达积极成长、易方达价值精选和易方达深证100交易型开放式指数基金赫然在列，其中易方达积极成长和易方达价值精选是第三季度的新进股东。此外，大成蓝筹稳健、融通深证100指数和中小企业板交易型开放式指数基金等6家机构也在上述名单中。</p>\r\n<p>东方园林&mdash;&mdash;应收账款之&ldquo;惑&rdquo;</p>\r\n<p>东方园林于2009年11月登陆A股市场，募集资金84970万元。得到资本市场的助力，东方园林具备了承接大型工程项目的资金实力，自上市以来大单不断，已经累计签订大单超过100亿元，而2010年至今累计确认收入也达到约34亿元。2011年以来，该公司已公告的框架协议金额为19亿元。</p>\r\n<p>然而，伴随着营业收入的高速增长，其应收账款也高速增长。今年前三季度，公司应收账款为10.1亿元，比期初增加106.6%，现金流较为紧张。</p>\r\n<p>应收账款增加是由于东方园林回款的进度偏慢。根据公司半年报，以今年施工的最大两个项目株洲神农城和大同文赢湖为例，前者今年实现营业收入3.51亿，累计收款0.58亿，回款进度16.52%；后者回款进度28.69%，远低于公司以往50%的工程回款进度。</p>\r\n<p>回款慢一方面增加了风险，另一方面加重了公司现金流的压力。从东方园林三季报看，前三季度短期借款大幅增长879.43%，经营活动现金流量净额为-5.38亿元，为补充流动资金，公司累计向银行借款5.68亿元。</p>\r\n<p>园林工程企业因回款慢而导致的现金流紧张或许正是东方园林要面对的最大风险。</p>\r\n<p>在东方园林的股东名单中可谓机构云集。公司今年三季报显示，广发基金和易方达基金旗下分别有4只和3只基金位列其前十大流通股股东，其余三席分别为中银收益混合基金、华夏成长和社保基金一一五组合，前十大流通股股东全部为基金，合计持股1591.23万股，占总流通股本的32.52%。</p>\r\n<p>广田股份&mdash;&mdash;大客户依赖症</p>\r\n<p>建筑装饰行业的广田股份在2010年9月末上市之时就因为过度依赖恒大地产装修订单而引起市场疑虑。</p>\r\n<p>恒大地产是公司的第一大客户，2007年、2008年、2009年和2010年公司对其营业收入比重分别为22.71%、38.15%、36.83%和57.84%，且有逐年增长的趋势。而且广田股份对前五大客户的营业收入占总营业收入的比重也逐步上升，2007年至2010年分别为30.18%、51.17%、53.38%和68%。</p>\r\n<p>广田股份与恒大地产于2010年11月26日签署《战略合作协议》，由广田股份承接恒大地产及其子公司属下项目公司部分建设楼盘的建筑装修装饰工程施工业务；恒大地产每年安排约35亿元的装修施工任务给广田股份，并逐年增加约10亿元的施工任务。</p>\r\n<p>但是，分享恒大地产装饰业务蛋糕的不只是广田股份一家。2010年11月20日，金螳螂也与恒大地产签订协议，约定恒大地产每年为公司安排约30亿元的施工任务。另一建筑装饰公司亚厦股份也于2010年11月发布公告称，公司与恒大地产签署战略合作协议，后者每年将向公司提供30亿元的装修施工任务，且逐年递增约10亿元。三者中，广田股份订单期限更长，协议有效期为3年，亚厦股份和金螳螂均为2年。</p>\r\n<p>对大客户越来越重的依赖，加之政策调控和竞争对手的蚕食，恒大地产这块蛋糕到底能吃多久？这无疑成为悬在广田股份头上的达摩克利斯之剑。</p>\r\n<p>而翻看广田股份三季报的股东名单，前十大流通股股东中有9席为基金所占据，中邮基金、华宝兴业、广发基金和建信基金旗下分别有两只基金产品榜上有名，而华夏成长也名列其中。9只基金共计持股2666万股，占流通股本的26.26%。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (228,'<p>中小基金公司申请专户资格取得实质性进展。证券时报记者获悉，包括华富基金、农银汇理、申万菱信、纽银梅隆、大摩华鑫、财通基金、长安基金等在内的10余家基金管理公司已于日前获得专户牌照。而随着资格获批，不少公司的首只专户产品也在积极筹备之中，专户业务开展进入日程。</p>\r\n<p>市场人士认为，本轮面向中小型公司的专户业务开闸，既有利于基金公司发展多元的资产管理业务，也对其投研、风控提出更高要求，在竞争中准备更加充分的公司才可能在这项新业务上取得长足发展。</p>\r\n<p>10余家中小公司获专户资格</p>\r\n<p>产品走差异化道路</p>\r\n<p>证监会74号令&mdash;&mdash;《基金管理公司特定客户资产管理业务试点办法》正式实施以来，中小基金公司纷纷摩拳擦掌，欲借专户业务打开新的市场。经过两个月的等待，中小基金首批专户于日前放行。沪上一基金公司高管告诉证券时报记者：&ldquo;12家中小基金公司已获特定客户资产管理业务资格。&rdquo;</p>\r\n<p>证券时报记者也从华富基金、纽银基金、财通基金、大摩华鑫基金(微博)等公司处证实其已获专户资格，其中一些相对积极的基金公司表示将立即开展产品申报以及签约工作。并且，据此次获得资格基金公司人士介绍，由于前期准备较为充分，小基金公司专户投入实际运作可能&ldquo;会比预期更快&rdquo;。</p>\r\n<p>在大基金公司已有的专户产品格局之下，小基金公司欲走上差异化产品之路。&ldquo;这种差异化一方面在于公司资源的差异，二是公司人才格局的差异化。&rdquo;沪上一基金公司总经理告诉证券时报记者。</p>\r\n<p>据了解，目前小基金公司的产品思路主要分为两大类：一类是擅长权益类投资或具有丰富客户群体的公司，希望通过专户灵活的投资风格获得绝对收益，以稳定客户群；另一类则十分强调量化产品在专户中的前景，强化数量化投资。</p>\r\n<p>证券时报记者从华富基金了解到，针对目前专户产品在做空方面的不足，公司明确首批产品要运用期货进行对冲策略，目前公司已经与具有对冲需求的客户谈妥产品合同，在获得批文后，将第一时间上报产品方案，产品发行运作即刻开展。</p>\r\n<p>财通基金也表示，首只专户产品也在积极筹备之中，将以绝对收益为目标，根据客户需求量身定制各种产品，同时积极进行产品创新，开发类对冲产品。</p>\r\n<p>摩根士丹利华鑫基金表示，将依托股东摩根士丹利在数量化、风险管理、股指期货方面的诸多优势，对相关人员进行系统化海外培训，力争在牛熊市中均获得一定的正收益，满足客户的特定资产管理需求。</p>\r\n<p>对于在过去行业发展中落后的小基金公司而言，首批专户业务的运作成败关乎未来这一业务的前景。按照前述基金公司总经理的说法，小基金公司在首批专户运作中，更加强调的是风险控制能力，他表示：&ldquo;我们对专户部门的要求是允许一两年不盈利，但不能使客户亏损，否则未来该业务发展就画上了句号。&rdquo;</p>\r\n<p>人才、客户资源稀缺</p>\r\n<p>新老公司争夺高端客户</p>\r\n<p>证券时报记者了解到，中小公司专户资格放开后，相对于投资人才的稀缺，基金公司在大客户资源方面的匮乏也是无法绕开的一大障碍，新老公司对高端客户的争夺更加激烈。</p>\r\n<p>&ldquo;专户一对一的客户主要是基金公司自主开发维护，合作相对顺畅。而一对多客户则主要借助银行、券商等销售渠道资源，客户争夺会比较激烈，对基金公司来说需要多下工夫。&rdquo;一位新基金管理公司人士称。据她透露，银行对高端客户资源的保护非常严，基金公司想要通过银行&ldquo;挖走&rdquo;客户非常困难。</p>\r\n<p>一家基金公司市场部人士也表示，新基金公司目前在客户积累方面一穷二白，无法做到终端销售，而银行对大客户资源的信息保护也非常严密，&ldquo;这些大客户资源是银行生存的根本，例如冲存款、冲发行规模等，都需要这些资金的帮忙，因此基金公司是很难直接联系到这些客户的，更别提主动提供服务了。&rdquo;况且，与银行对高端客户的配套服务相比，基金公司还差得很远，银行就大客户资源可以提供的服务非常完备，例如标准化服务指引、针对客户的差异化服务方案等，更别提新基金公司。</p>\r\n<p>与2009年专户业务发展初期客户争夺主要靠速度、具有较大的随机性相比，如今的专户客户资源争夺更加复杂。客户对品牌要求度日益看重，对没有历史业绩、没有品牌的新基金管理公司来说，只能依靠股东。一些券商系基金公司也将专户资源锁定在股东方面客户储备。财通基金就表示，股东均来自民间资本充沛的浙江，在专户客户资源上具有一定的先天优势。</p>\r\n<p>证券时报记者还了解到，在第一批专户申报中未获得批文的公司，目前正在完善不足之处，而未赶上第一批申报的一些公司进行了后期的补充和准备，沪上一家基金公司日前已经完成了资料申报工作，正在等待监管部门的审核以及反馈意见。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (229,'<p>在政策组合拳中,人民币合格境外机构投资者(RQFII)已经正式启动,试点初期额度约人民币200亿元。</p>\r\n<p>这有利于境外人民币资金回流。尽管只有区区200亿元,试点初期额度不高,对A股影响也不大,但未来发展空间巨大。</p>\r\n<p>而且,根据规定,试点初期不少于募集规模80%的资金投资于固定收益证券,包括各类债券及固定收益类基金,不超过募集规模20%的资金可投资于股票及股票类基金。</p>\r\n<p>也就是说,试点期间能投资股票及股票类基金的RQFII资金,最多40亿元人民币。</p>\r\n<p>RQFII背后&ldquo;主力&rdquo;强大</p>\r\n<p>RQFII,即人民币合格境外机构投资者,此次试点将从基金公司、证券公司的香港子公司开始,运用其在港募集的人民币资金在经批准的人民币投资额度内开展境内证券投资业务,初期试点额度约200亿元。</p>\r\n<p>区区40亿元人民币的资金无法在A股市场掀起波澜,但200亿额度可能是RQFII探路境内证券市场的&ldquo;排头兵&rdquo;,让市场憧憬的是200亿背后庞大的&ldquo;主力军&rdquo;。</p>\r\n<p>香港坐拥大量人民币存款,且投资出路不多,每次有人民币基金出笼,虽然回报并非可观,但散户均反应热烈。。根据香港金管局的统计数字,截至9月底,香港人民币存款达6222亿元。在年初,这一数字仅为3150亿元。RQFII的推出,不仅有助于拓宽停留在香港的人民币的投资渠道,而且也有助于增强资本市场流动性。</p>\r\n<p>QFII将继续增援A股市场</p>\r\n<p>A股请来&ldquo;两大外援&rdquo;,一大是RQFII,另一个即是合格的境外机构投资者QFII。上周五证监会表示,将加快QFII的审批速度。审批将会大幅提速。预计未来两个月的审批量将为过去两年增量的总和。</p>\r\n<p>从自2002年底颁布QFII办法以来的市场表现看,QFII是长期机构投资者。国外机构对QFII的申请不断,始终保持对A股市场的热情。目前已经批准125家境外机构QFII资格,今年新增额为12亿美元,而去年是30.5亿美元,即意味着,未来俩月有越40亿美元的QFII额度。</p>\r\n<p>数据显示,截至12月2日,QFII账户总资产规模合计2655亿元,其中银行存款、股票和债券占比分别为12.2％、71.9％和13％,QFII持股市值约占流通市值的1.07%。</p>\r\n<p>在过去的三季度,A股跌跌不休,QFII的态度非常明显,对A股是减持多于增持。根据上市公司三季报公布十大流通股东对A股的持有情况看,遭到QFII抛弃或者减持的公司明显多于增持和新进的公司。QFII增持公司,应是投资者跟踪关注的重点品种之一。</p>\r\n<p>本报记者 王明彬</p>\r\n<p>三季度QFII增持幅度超30%个股一览</p>\r\n<p>简称 QFII家数 持股(万股) 流通比例(%) 环比(%)</p>\r\n<p>中国国贸2 1 327.87 1.32 274.17</p>\r\n<p>中储股份2 2 690.52 5.80 201.48</p>\r\n<p>伊利股份3 7 606.30 4.79 144.66</p>\r\n<p>马钢股份2 2 714.95 0.45 142.58</p>\r\n<p>太阳纸业1 628.40 1.93 105.22</p>\r\n<p>北新建材1 722.76 1.26 88.66</p>\r\n<p>云天化2 570.26 0.97 72.80</p>\r\n<p>山东海化2 405.02 0.45 70.20</p>\r\n<p>华域汽车1 2 796.78 1.90 54.78</p>\r\n<p>方兴科技2 382.67 3.27 53.05</p>\r\n<p>王府井2 858.19 2.05 52.44</p>\r\n<p>中青旅2 2 103.14 5.06 38.37</p>\r\n<p>双汇发展3 1 934.96 3.19 35.03<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (230,'<p>上周市场再度遭遇重挫，指数持续下跌。尽管周五出现较大程度反弹，但上证指数一度跌至十年前低点依旧让投资者错愕。十年轮回，目前市场再次回到&ldquo;低点&rdquo;，是否意味着&ldquo;底部&rdquo;已现？面对赚钱难度越来越大的股市，借道更为专业的基金能否让钱生钱更靠谱？什么样子的基金具备穿越牛熊的潜质？投资者又会做出什么样的选择？</p>\r\n<p>上周，中国证券网进行了&ldquo;股指又回到了十年前的单位，您认为目前是入市的好时机吗&rdquo;的网络互动调查，结果显示，尽管当下指数处于历史低点，但面对依旧混沌的市场行情，基民对于抄底的&ldquo;号召&rdquo;依旧态度犹豫。</p>\r\n<p>数据统计显示，有1542名网友投票选择了&ldquo;继续看空，不打算近期入市&rdquo;，选择该选项的网友人数占所有参与投票总人数的32.25%，有1370人选择了&ldquo;行情无法判断，暂时观望&rdquo;，占半壁28.66%。近半数参与调查者选择了回避或观望，显示当下市场信心依旧微弱。</p>\r\n<p>与此同时，有996名网友投票选择了&ldquo;看好债市，打算购买低风险产品&rdquo;，占投票总人数的20.83%，还有873名网友投票选择了&ldquo;指数超跌，打算近期逐步入市&rdquo;，占比仅18.26%。</p>\r\n<p>招商基金招商优势企业灵活配置拟任基金经理赵龙表示，从经济形势来看，目前已经处于持续回落阶段，CPI增速应该会持续下降，短期内抗通胀可能不是主要问题，未来的中心为保增长的可能性大，政策会进一步宽松，有利于市场见底。</p>\r\n<p>对后市行情如何演绎，赵龙表示谨慎乐观。他预期，短期内看，由于经济政策宽松的力度尚不足以抵消经济增速下滑的影响，虽然估值有支撑，但整体可能仍然较弱。在宏观经济政策回归中性的情况下，2012年股市应该有较好的投资机会，但未来的机会更多表现为结构性机会。</p>\r\n<p>&ldquo;要扭转目前股票市场的颓势，需要经济政策进一步宽松，估计这个时间在明年一季度。短期内，相对抗跌的还会是仓位不高、大消费和金融配置比较多的基金。&rdquo;赵龙表示：&ldquo;目前市场的问题是估值结构分化很严重，很多股票高估较多；而政策宽松的力度还不够大，因此，现在处于一个比较尴尬的阶段。但是，由于市场整体估值低，并且部分优质个股已经进入价值区间以内，所以目前已经临近建仓的好时机。&rdquo;</p>\r\n<p>而晨星（中国）基金评价业务负责人王蕊则更为谨慎，她判断现阶段由于国际经济形势仍未扭转，国内经济增速放暖已被确认，建议暂时静观，待经济环境出现恢复或者刺激政策出台时进行投资。</p>\r\n<p>&ldquo;根据股市逆向投资原理，在大多数投资者绝望的时候进行配置有可能获得比多数投资者大的回报，但需注意一点，就是对经济的疲软能否在中短期内结束，毕竟，长期的经济不振造就长期的股市低迷。&rdquo;</p>\r\n<p>针对当下部分投资基金而被&ldquo;套牢&rdquo;的持有人，王蕊表示，目前市场处于低位，建议多观察一段时间，判断基金业绩的下滑是投资能力水平造成的还是市场结构性下挫而导致的。对基金经理的过往业绩和投资风格也应有个大概了解。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (231,'<p>7只ETF纳入融资融券标的，成为国内指数基金发展中的一道分水岭。上证50ETF等入围者交投活跃，无论是净申购规模还是二级市场交易量都扶摇直上；形成鲜明对比的是另一些&ldquo;养在深闺&rdquo;的上市指基，日交易量只有区区几万元。</p>\r\n<p>对于投资者而言，市场上已有的上百只指数基金并非&ldquo;一个都不能少&rdquo;。相反，大多数指基将逐渐陷入&ldquo;被边缘化&rdquo;的尴尬境地。指数基金&ldquo;跑马圈地&rdquo;是否必要，已成为基金公司产品设计时必须思考清楚的问题。</p>\r\n<p>马太效应显现</p>\r\n<p>12月16日，连日的阴霾被午后一缕阳光稍稍吹开。当天市场在下午上演绝地反击，上证综指回升2.01%。刚刚被列为融资融券标的的几只ETF基金成为投资者抢反弹的利器，成交额也随之放大：当日华夏上证50ETF成交金额达10.03亿元，易方达深证100ETF成交金额也达到7.26亿元，华夏中小板ETF为1.79亿元。</p>\r\n<p>除了二级市场交易活跃，入围两融标的的ETF规模也持续扩大。上证50ETF上周申购10.76亿份，赎回4.53亿份，净申购6.23亿份；上证180ETF上周申购18.09亿份，赎回9.42亿份，净申购8.67亿份；深证100ETF上周更是大幅净申购12.24亿份，期末份额突破300亿份，创出历史新高。</p>\r\n<p>对于ETF基金来说，成为融资融券的标的物，增加了基金作为投资工具的应用。银河证券基金分析师胡立峰举例，在上证180ETF和深证100ETF成为融资融券标的前，投资者只能通过做多上证180ETF和深证100ETF，同时做空沪深300指数期货，进行单边套利。上证180ETF和深证100ETF纳入融资融券标的后，投资者可通过做空180ETF和深证100ETF，同时做多沪深300指数期货，发掘套利机会，进行双边套利。从交投数据看，被列入融资融券标的的7只ETF日成交额遥遥领先。</p>\r\n<p>然而对于另外一些ETF来说，热闹是别人的。同样是在16日，有4只ETF基金成交额不足10万元，最低的一只仅有6万元。&ldquo;在日常交易中，选择投资标的的形式上，ETF经常是首选的工具，但并非每一只ETF都受欢迎，一些冷门指数的ETF一直交易额就很少。&rdquo;一位保险机构的负责人告诉中国证券报记者。</p>\r\n<p>中国人寿资产管理有限公司副总裁王军辉则表示，从总规模来看，目前超过40亿元规模的ETF仅有4只，且均为2006年之前发行的产品，这几只基金规模较大、流动性较好。但与此同时，还有一些ETF产品流动性非常差，甚至一些小规模的产品日交易量仅有几千元，根本无法容纳机构的进入。而国内机构投资者参与ETF投资的比例更高，超过70%的交易量是机构创造的。近年来有些基金公司为避免撞车，发行一些风格化的指数，但从目前看，这些指数本来规模就小，机构的关注度也很小，这样的情况下，强者恒强、弱者更弱的马太效应就出现了。</p>\r\n<p>相比ETF基金，二级市场交易的LOF指数基金所面对的&ldquo;冷清&rdquo;更为明显。16日，规模达276亿元，沪深两市最大的指数基金嘉实沪深300，二级市场成交额仅为4379万元，却已是交投量最大的指数基金。而规模位列第二的广发中证500指数基金，当日交易额仅为78万元，另有8只LOF基金零成交。</p>\r\n<p>事实上，中国证券报记者在长期观察指数基金的二级市场交投情况中发现，&ldquo;冷热不均&rdquo;是一个长期存在的现象。而该现象背后，有着诸多深层次的原因，总结其中的经验教训，有利于市场开发出更多的精品指数基金。</p>\r\n<p>对于另外一些基金而言，场内基金规模过小也是导致场内交投不旺的直接原因。以两市规模最大的指数基金嘉实沪深300为例，上市交易的份额仅占总份额的9.51%。加上部分次新上市基金场内份额遭遇大幅缩水。2011年10月以来挂牌的上市基金中，八成基金场内份额出现减少，部分基金场内份额减少达八成以上。</p>\r\n<p>此外，2009年以后，大量的ETF基金开始有了个&ldquo;兄弟&rdquo;&mdash;&mdash;连接基金。这种形式的出现令银行渠道的投资者有可能参与ETF投资。但这也带来一个问题，大量的ETF份额在场外交易，事实上难以发挥ETF的投资优势，场内份额过少，池浅难容大鳄出入。</p>\r\n<p>结构性矛盾困扰</p>\r\n<p>事实上，自证监会单独为新基金申报开设指数基金通道之后，指数基金发行出现井喷。为了方便投资者参与，这些新创设的基金也大多可在一二级市场进行交易。据Wind统计，目前市场共有开放式指数基金153只，其中2009年以后发行的有139只，大部分的指数基金都是近两年才创设并上市的。</p>\r\n<p>但在指数基金发行热的背后，却存在很多结构性的矛盾。从最热的ETF来看，王军辉认为，机构投资者所需要的宽基指数已经基本够用，目前缺乏的是跨市场ETF、分行业ETF。尽管业界已呼吁多年，但跨市场ETF仍然未获准发行，这使得参与市场套利、期现套利以及对冲的机构投资者成本上升，也不利于其对市场做出更为快速有效的反应。</p>\r\n<p>值得注意的是，从ETF交投看，尽管竞争激烈，一些新的指数却可以获得市场积极参与，这不能不归功于指数本身独特的魅力。以创业板ETF为例，这只12月份刚上市的ETF，尽管没有列入融资融券标的物，但目前交投却在ETF中名列前茅，不到6亿元的规模，上周五成交额就达到6351万元。主要原因是该指数为两市唯一挂钩创业板的ETF，具有独特性。</p>\r\n<p>目前，一些基金经理较为看好的分行业ETF也可以考虑创设。&ldquo;近年来主动型的行业基金已经逐步在开发，指数公司也在积极开发新的行业指数，下一步可考虑开发一些被动型行业指数基金，更便于机构参与。&rdquo;深圳的一位基金公司高管告诉中国证券报记者。此外，有机构投资者建议，基金公司可以开发固定收益类或绝对收益的指数产品，该类产品在国外较为常见，但在国内却几乎是空白。</p>\r\n<p>而仔细分析上市基金的持有人结构，就可以发现，券商是新基金发行时认购的主力。不过近两年来看，这里面也有&ldquo;注水&rdquo;的成分。随着市场持续低迷，帮忙资金比例增加，有些券商资金在基金一上市，就迫不及待地在二级市场卖出，这是次新基金迅速缩水的主要原因，也会令真正的投资者退避三舍。接受中国证券报记者采访的基金公司对此却表示无奈，在发行方面，明明知道这样的手法无异于饮鸩止渴，但是为了冲首发规模，只得默认其成为业内的&ldquo;潜规则&rdquo;。</p>\r\n<p>而部分券商的短视也令投资者的需求难以得到满足。从12月初ETF开始参与融资融券的数据看，目前部分券商只提供沪深两大交易所交易最活跃的各两只ETF基金。有些ETF券商因为手中无券，无法满足具体某些券种的融券需求，单日交投连续为零。&ldquo;国外ETF有做市商制度，加上衍生品多、市场培育快，而国内缺乏做市商，加上基金公司培育二级市场的主动性很低，这些均导致一些指数只能默默无闻、交投黯淡。&rdquo;深圳交易所基金部的一位负责人告诉中国证券报记者。</p>\r\n<p>取经海外经验</p>\r\n<p>&ldquo;事实上，在亚洲比较发达的H股市场，在联交所挂牌交易的ETF基金中，交投冷热不均的现象也十分严重。其中历史较为悠久的安硕A50基金，几乎每日交易量都可以与前十大超级蓝筹媲美。而近几年一些分区域的主题ETF，有时单日交易量也不足十万港币，甚至单日出现零交易的情况也不少见。&rdquo;香港一位中资券商高级主管告诉中国证券报记者。他介绍，在挂钩大陆股票的ETF中，投资者的参与度均会不同。</p>\r\n<p>如果仅仅将交投旺盛寄托于机构投资者，实际上会出现很大的波动，这位高级主管表示。近几年来，香港也推出&ldquo;强积金&rdquo;计划，个人投资者可以通过自主选择，将个人账户的部分资金用来买基金，其中就有不少人选择了ETF基金。这对于ETF基金的管理人来说，有了持续稳定的资金申购。&ldquo;据我所知，这与大陆推行的基金定投类似，但目前基金定投很多是集中在银行渠道，上市交易基金基本上没有这个功能，券商推行也并不积极。&rdquo;这位高级主管说。他建议，可以鼓励一些有条件的企业年金做类似的尝试。</p>\r\n<p>囿于制度因素，很多基金公司希望的做市商制度、社保开放投资等仍未开闸。有基金公司建议，我国或许在指数基金授权上，可以仿效国外的成功经验，一些优秀的指数允许多家授权，而不是目前的独家创设。据中国证券报记者了解，目前国内基金公司在促进上市基金二级市场交投活跃方面主要集中在产品创新上，而产品创新也主要集中在创设分级基金上。的确，同为LOF产品，挂钩同一指数的分级指数基金交投明显超越未分级的指数基金，但如何与券商合作、促进二级市场交投，基金公司普遍尚未列到议事日程上。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (232,'<p>沉闷的新基金发行市场在年末迎来了意外惊喜。</p>\r\n<p>12月14日，浦银安盛增利分级基金正式成立，这只债券型分级基金成立规模9.05亿份。对于浦银安盛这家规模不大的基金公司而言，这样的规模实属不易。</p>\r\n<p>不过浦银安盛的&ldquo;意外&rdquo;收获并非偶然事件。比浦银安盛增利分级基金早一天成立的建信双息基金规模则达到19.5亿份，此前的鹏华丰泽基金成立规模达到29亿份，长盛同禧信用增利分级基金规模也达到19.2亿份。</p>\r\n<p>如此发行规模一扫今年全年新基金整体销售不佳的局面。统计数据显示，截至12月8日，今年以来延长募集期的基金共有15只，迷你基金更是层出不穷，部分基金公司甚至主动缩短发行期限，提前结束发行。相对这样的情况，最近成立的几只新基金无疑是非常幸运的。</p>\r\n<p>从托管银行来看，今年11月之后成立的规模超10亿份的新基金共有10只，其中的4只托管行为中国银行，2只选择了邮储银行，另外中信银行、建行、工行、招商银行各有1只。从基金投资风格来看，债券基金共有5只，被动型指数基金有3只，保本型和混合型各1只。</p>\r\n<p>业绩决定销售</p>\r\n<p>在庆祝新基金顺利发行的同时，部分基金公司也同时在总结其中的经验。</p>\r\n<p>浦银安盛相关人士对《第一财经（微博)日报》记者表示，浦银增利分级债券基金是一个封闭三年的基金，这么长的封闭期对基金公司来说是好事，但对投资者来说却未必。因而在产品设计上，采用了A、B份额合并发行模式，给投资者提供了三种灵活的交易方式。另外，在最终的销售渠道上，公司也详细考察了很久最终才确定将托管行定为上海银行。</p>\r\n<p>据介绍，很多人现在认为，银行系的基金公司发行新基金会相对容易，这其实并不准确。有股东的背景在，自然有它的好处，但从基金公司成立这几年来看，最终决定基金公司总规模的还是业绩。浦银安盛自首只基金发行到现在，也有4年时间了，公司一直上不去，首只基金业绩不行确实是大问题。</p>\r\n<p>&ldquo;但现在情况有所改观。&rdquo;上述人士表示，在新基金销售过程中，另一只债券型基金今年以来的良好表现起到了很大作用，在渠道方面向客户推销时，说服力度非常大。</p>\r\n<p>至于销售渠道，该人士表示，现在中小渠道受到越来越多的基金公司重视，但这些中小渠道其实也有各自的特点。&ldquo;比如这次我们选择上海银行做托管行，就是因为在之前的市场调研中发行，上海银行的国债销售非常好。经过后期沟通后了解到，上海银行的客户风险偏好大多偏保守，那么我们将这次的债券型分级基金放在上海银行作为主要销售渠道，成功的概率就会比其他风格偏基金的银行会更高。&rdquo;</p>\r\n<p>基金适销对路</p>\r\n<p>业内人士表示，在过去几年，建行、工行一直是基金销售的主要渠道，最近几年农行、邮储银行也发挥各自的渠道优势，基金销售规模猛增。相比较之下，一些小银行就显得有些被边缘化的趋势。不过这一现象在最近，尤其是今年以来得到改观。</p>\r\n<p>&ldquo;不是说建行、工行这些大渠道的银行理财经理不重视，而小银行的理财经理更用心。重要的原因是因为，在总共那么多人力的前提下，大渠道可能一个人要在同一时间内了解很多个产品，而小渠道一个人在同一时间只要面对很少的几个产品，在经力分配上自然会更多。&rdquo;除银行理财经理经力分配的因素外，在基金公司培训上，对大渠道的理财经理，基金公司也不可能说经常进行培训，而小渠道则方便很多，理财经理一旦遇到问题，基金公司就可以很快跟上。当理财经理对产品理解加深，同时自己有信心之后，才能很好地销售给客户。</p>\r\n<p>沪上一私募总经理也表示，销售其实还只是其中一个环境，针对不同的市场开发出适应这种市场环境的产品才是决定产品最终销售结果的因素。&ldquo;比如今年股市非常不好，你非要去推股票市场的产品，肯定不行。适应市场需求去开发产品才能保证最终的销售。&rdquo;</p>\r\n<p>今年以来各类型基金销售的统计数据显示了不同类型基金在特定的市场环境中对销售的影响之大。</p>\r\n<p>根据统计数据，今年成立的规模超过10亿份的64只新基金中，债券方向基金就占据了23只，保本型占据了12只。而规模不足1亿元的迷你型基金中，则为投资于股票市场的主动型和被动型基金所占据。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (233,'<p>离职、跳槽、歇业、奔私，一位位鲜活的基金人正如今年的生肖兔般不老实，或悲伤或欣喜地离开了之前耕耘的热土。2011年基金人才流动可谓盛况空前。</p>\r\n<p>具有喜剧效果的是，从年头传至年尾的王亚伟离职至今没有如约，而人们对数十位基金高管和核心投研人员离去或变更的新闻却早已习以为常。</p>\r\n<p>如果说2007年掀起了基金人才剧烈流动的始端，那时还能将奔私的大潮归咎为牛市放大了公募基金激励机制和治理结构的弊端，那么如今惨淡的2011年，圈中人又将给自己的离去总结出什么理由呢？</p>\r\n<p>1高管做表率</p>\r\n<p>在跳槽和离职这方面，基金高管们或主动或被动地做了表率，当了先锋。</p>\r\n<p>总经理忙着跳槽，股东忙着挑选接班人，基金公司品牌策划部忙着辟谣，媒体忙着打听消息，忙碌的2011年，近四分之一的基金公司更换了总经理，更是有包括嘉实在内的数位董事长变更，这不仅成为国内甚至世界基金业奇观，也成为本年度基金人才流动的各种&ldquo;表率&rdquo;。</p>\r\n<p>当然，肖风和成保良的离去掀起了了今年业内关于高管离职的高潮。7月，肖风离任传言终于落地，这位大佬级的一代创业者终于还是从总经理的位子上退居二线。博时基金管理发布公告，从7月28日起，总经理肖风只担任董事、副董事长，至截稿时新任总经理尚未公告。</p>\r\n<p>同样，作为国内第一家合资基金公司的总经理，自招商基金成立后便担任该公司总经理、董事的成保良也最终在年内选择离开公募基金，转投一家本外币的产业投资基金。</p>\r\n<p>至此，人们有趣地发现，基金业的第一批创业者仅剩华夏基金范勇宏孤独坚守，而很多从一线打拼起来的市场或投研干将接过了公司的大旗。</p>\r\n<p>今年8月16日，天弘基金原投资总监吕宜振出任万家基金副总，而华夏基金元老级人物郭树强也已在6月份空降天弘基金。同样，9月，申万菱信外部聘选的总经理于东升也正式就任，而他此前在汇添富任副总经理近2年。另外，方正富邦基金首任总经理是在业内颇具名气的宋宜农，而他曾经出任长盛、光大保德信和景顺长城等多家基金公司的市场总监。</p>\r\n<p>如此等等，今年基金高管们成为猎头加重关注的焦点，行业的一场剧烈人事变动高调地谢幕。而据机构统计，成立以来曾经更换过两次总经理的基金公司多达16家，包括金鹰、大成、东方、天治、国海富兰克林、华安、大成、鹏华和银河基金等，而更换过3次总经理的公司仅有宝盈基金一家。</p>\r\n<p>远去的帅将们似乎无法留恋曾经创业的舞台，用成保良的一句话说，行业面临焦灼状态，发展的瓶颈让公司的掌舵人想突破却找不到正确的方向。</p>\r\n<p>掌舵人们陷入与行业同焦灼的状态确实不是一天两天，年初时定下的6只以上基金发行目标的公司多数完成，而最终发现基金首募规模在一天天降低，从腰包掏出去的银子却日渐高涨。</p>\r\n<p>&ldquo;发一只5、6个亿的基金，成本至少在600万以上。&rdquo;一位基金公司总经理本并不愿透露这个数据。</p>\r\n<p>面对股东犀利的问话，面对持有人坚决地赎回，面对追随自己打拼多年的兄弟姐妹们，总经理们有苦难言。</p>\r\n<p>且不谈股权激励，且不论规模靠前，众多基金公司总经理们面前唯一的念想是：先生存！</p>\r\n<p>2忠诚之殇</p>\r\n<p>&ldquo;不要抱怨基民短视化，因为忠诚是互相的。&rdquo;谈及今年众多基金被投资者抛弃时，一位圈中人士评价，基金管理人和基民正像一种恋爱关系，基金经理保持长期稳定可谓一种忠诚。</p>\r\n<p>然而，和缓慢升高的离婚率相比，今年基金经理的变更率似乎更惊人。</p>\r\n<p>据统计，截至今年12月15日，公告基金经理变更的次数近400次，而光是12月以来就多达21次。而2011年以来，还有包括华富韩玮、光大保德信袁宏隆、建信汪沛、天弘吕宜振、农银汇理栾杰等多名投资总监离职。</p>\r\n<p>然而，银河证券基金研究中心的数据曾显示，在2001年基金经理变动次数仅有18次，以后逐年缓慢增加，2005年变动次数增加到了91次，2006年达到122次，2007年一季度更是创出新高，达到72次。据Wind统计数据统计，2010年共发生372次基金经理变更事项、192起基金经理离职事件。</p>\r\n<p>看来，基金经理的高速流动始于大牛市2007年，并从未停歇。</p>\r\n<p>就在当时，嘉实基金总经理赵学军曾表示，公募基金经理流动不会影响行业健康发展，认为造成他们跳槽私募的根本原因在于公募基金的治理结构存在劣势，使激励机制不能与基金经理的付出和成绩更好地联动，而且牛市也一定程度上放大了这种机制的弊端。在他看来，治理结构可以改善，激励机制也可以调整，所以这个挑战只是暂时的。</p>\r\n<p>然而，时至今日，之前所谓短暂的挑战仍然存在，并且在艰难的2011年成为人才之殇。</p>\r\n<p>翻看基金经理名录表，耳熟能详者不足20人，而行业中连续担当基金经理一职年限超过10年的仅有三位，分别是王亚伟、林彤彤和尚志民。而且除了对基金投资的高度忠诚外，这批资深基金经理中&ldquo;从一而终&rdquo;者绝对居多，其中根据统计，在基金经理岗位从业超过8年的基金经理中，王亚伟、尚志民、陈志民和汪澂等人仅有在一家基金公司任职的经历。</p>\r\n<p>然而，是什么让昨日的明星变成流星？</p>\r\n<p>归纳来看，基金经理的流动分为内部公募向专户的流动，行业内公司间跳动及向行业外流动三类。晨星（中国）认为，基金经理加速流动的主要原因在于，首先是基金公司在公募与特定客户业务方面对基金经理资源的分配，导致一些投资管理经验更为丰富的基金经理离开公募业务。</p>\r\n<p>其次，高管变更或是股东变更增加了团队的不稳定性，例如公司制度、投研文化、投资自由度以及投研流程如果在新股东或是高管介入之后有所改变，会诱发管理部门和业务部门之间的磨合，也会使基金经理由于在理念、文化等的不认同而选择离职。</p>\r\n<p>第三，激励以及考核因素。如果基金公司从股东、董事到管理层的角度对基金经理的考核期限偏短，会使基金经理无法坚守自己的投资理念。部分基金经理转向私募的原因也在于后者在理念的坚持、策略的灵活性、业绩激励等方面更具有吸引力。</p>\r\n<p>曾经的制度优势似乎并没有留住人才，今年惨淡的市场行情及基金行业管理规模的屡屡下降更是促发一部分行业人士心灰意冷的导火索。</p>\r\n<p>&ldquo;别再怪基金经理了！&rdquo;有人在微博上振臂疾呼。</p>\r\n<p>&ldquo;在资产管理行业规模快色增长的同时，人才的缺口也渐渐扩大，单独埋怨基金公司抑或那些最终没有留在行业内的人并不理智，行业的生命力才是吸引人才的最终砝码。&rdquo;一位资深行业人士认为。</p>\r\n<p>3股权扯动人心</p>\r\n<p>如果说2011年基金人才的高速流动给行业带来了挑战，那么基金公司股权的更迭必然要被&ldquo;追究&rdquo;。资本的蜂拥而至和失望退场在上演一场场好戏的同时，则无形中大大加速了行业中人才的游走和流失。</p>\r\n<p>年内最大的股权变更案例当属华夏基金，在历时2年多，各种受让方猜想中，中信证券最终将51%的股权分作5份分别转让给了4家境内企业和1家境外企业。</p>\r\n<p>虽然很难将主要人物的离职与公司股权变更画上等号，但也正在年内，华夏基金原机构总监郭树强和原固定收益投资总监杨爱斌相继离开，另谋高就。</p>\r\n<p>也正是在今年，有多达十家基金公司低调完成股权变更，其中变包括华夏、银华、东方、新华和中邮5家中资公司，和长盛、建信、光大保德信、申万菱信和平安大华5家合资公司。</p>\r\n<p>据统计，截止到9月30日，国内64家公司中就有20家的管理规模在百亿元之下，这意味着如果该公司非公募业务可供补给之外，公司很有可能当年入不敷出。这也正是不少小公司股东急于寻找下家的主要原因。</p>\r\n<p>而股权的变更一般自上而下牵扯管理层、投研团队和销售团队，而就在今年包括后台风控和基金会计甚至都出现了人手紧缺的窘态。</p>\r\n<p>然而面对股东，最难交差的还是总经理。不但没有开源，甚至节流都很困难。除了业绩原因，基金公司管理层和股东、董事会间理念和经营方式上不可协调的矛盾也是导致职业经理人被迫离职的重要原因。</p>\r\n<p>&ldquo;单纯的一个高管离开，对公司的影响尚可控制，就怕是因为一个人的离开，导致多数人员的流动，从而影响到整个团队的运作，这才是对基金公司致命的打击。&rdquo;上海证券基金研究中心李艳表示。</p>\r\n<p>然而事实上，不乏公司即将或正在经历股权变更后的人才危机。</p>\r\n<p>基金法修订能否带来曙光？</p>\r\n<p>行业生命力，给基金&ldquo;人才荒&rdquo;找到了宣泄的出口。然而，在市场话语权已失，渠道生存窘迫并已运行近13年的基金行业，是否能在已经进入最后阶段的《基金法》修订中找到一道解决人才桎梏的金钥匙？</p>\r\n<p>今年年初，《基金法》修订草案出路并向各方征求意见，其中关于员工持股的修订意见被迅速放大。其中第十四条规定：&ldquo;基金公司设立分支机构，变更持有百分之五以上股权或对公司治理有重大影响的股东或实际控制人&hellip;&hellip;实施员工持股计划，应当报国务院证券监督管理机构批准。&rdquo;</p>\r\n<p>由此，关于股权的变更和员工持股计划需要得到证监会批准的内容首次出现，也正意味着包括高管在内的员工持股有可能得到法律的认可。</p>\r\n<p>而就在近日，全国人大财经委副主任委员吴晓灵更是表示，《基金法》修改已进入最后阶段，明年将正式进入审议程序。</p>\r\n<p>基金行业高管以及基金经理人才的流失近年来成为行业&ldquo;娱乐化&rdquo;的导火索，更加成为制约行业发展、实现平稳过渡的掣肘。然而此次修订案将允许员工持股首次写入法律，在即将有法可依的股权激励和投资放开之后，公募基金人才流失是否将得到缓解？</p>\r\n<p>&ldquo;修订案把公司型基金组织形式引入国内，直接增加了股权激烈挽留人才的可能性。基金管理人可以通过持有基金的形式成为公司的股东。这与目前的私募基金有专业人士发起并参股的组织形式相靠近。&rdquo;北京某位私募基金管理人认为。</p>\r\n<p>虽然建立有效的股权激励机制，可以使人力资本价值在基金公司股权结构中得到应有体现，增强基金公司核心人员的稳定性，但是它并非阻止公募离职潮的充分条件。</p>\r\n<p>晨星通过对一些海外知名基金公司的深入分析发现，是否采取股权激励并不是优秀的激励机制的必要条件。&ldquo;股权激励实为一把双刃剑，它在给予投研人员激励的同时，更多是把他们和基金公司的利益绑定在一起而非基金持有人。在自身利益的驱使下，很容易引发由委托代理问题引发的利益冲突，投研人员可能为追求公司利益最大化而采取一些比较激进或者风险较高的策略，从而导致投资者承担过多的风险。&rdquo;</p>\r\n<p>即使激烈机制的改善能缓解人才流失的压力，但基于目前行业面临的是全方位的人才紧俏，包括高管、投研、后台和销售等岗位并非都吃员工持股这一套。</p>\r\n<p>&ldquo;例如，很多高管也是被迫离岗，股东给的3年考核不达标，而关于管理层和股东方的矛盾也甚嚣尘上。&rdquo;一位曾经的合资基金副总向记者透露。<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (234,'<div class=\"Line\">&nbsp;</div>\r\n<div id=\"Cnt-Main-Article-QQ\" bosszone=\"content\" style=\"position: relative\">\r\n<p align=\"center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic           \" r=\"1\" style=\"width: 550px\">\r\n<div class=\"mbArticleShareBtn\">&nbsp;</div>\r\n<img alt=\"捷克前总统瓦茨拉夫-哈维尔去世 享年76岁\" src=\"http://img1.gtimg.com/news/pics/hv1/106/124/931/60570001.jpg\" /></div>\r\n<p>&nbsp;</p>\r\n<p style=\"text-align: left; text-indent: 2em; font-family: 宋体; font-size: 10pt\">当地时间12月18日，捷克前总统瓦茨拉夫&middot;哈维尔去世，享年76岁。哈维尔是捷克的剧作家，曾于1993年到2002年间担任捷克共和国总统。</p>\r\n<p align=\"center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic \" r=\"1\" style=\"width: 307px\">\r\n<div class=\"mbArticleShareBtn\">&nbsp;</div>\r\n<img alt=\"捷克前总统瓦茨拉夫-哈维尔去世 享年76岁\" src=\"http://img1.gtimg.com/news/pics/hv1/107/124/931/60570002.jpg\" /></div>\r\n<p>&nbsp;</p>\r\n<p style=\"text-align: left; text-indent: 2em; font-family: 宋体; font-size: 10pt\">当地时间12月18日，捷克前总统瓦茨拉夫&middot;哈维尔去世，享年76岁。哈维尔是捷克的剧作家，曾于1993年到2002年间担任捷克共和国总统。</p>\r\n<p align=\"center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic  \" r=\"1\" style=\"width: 549px\"><img alt=\"捷克前总统瓦茨拉夫-哈维尔去世 享年76岁\" src=\"http://img1.gtimg.com/news/pics/hv1/108/124/931/60570003.jpg\" /></div>\r\n<p>&nbsp;</p>\r\n<p style=\"text-align: left; text-indent: 2em; font-family: 宋体; font-size: 10pt\">当地时间12月18日，捷克前总统瓦茨拉夫&middot;哈维尔去世，享年76岁。哈维尔是捷克的剧作家，曾于1993年到2002年间担任捷克共和国总统。</p>\r\n<p align=\"center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic  \" r=\"1\" style=\"width: 545px\"><img alt=\"捷克前总统瓦茨拉夫-哈维尔去世 享年76岁\" src=\"http://img1.gtimg.com/news/pics/hv1/109/124/931/60570004.jpg\" /></div>\r\n<p>&nbsp;</p>\r\n<p style=\"text-align: left; text-indent: 2em; font-family: 宋体; font-size: 10pt\">当地时间12月18日，捷克前总统瓦茨拉夫&middot;哈维尔去世，享年76岁。哈维尔是捷克的剧作家，曾于1993年到2002年间担任捷克共和国总统。</p>\r\n<p style=\"text-indent: 2em\"><span class=\"infoMblog\">国际在线</span>报道：当地时间12月18日，捷克前总统瓦茨拉夫&middot;哈维尔去世，享年76岁。哈维尔是捷克的剧作家，曾于1993年到2002年间担任捷克共和国总统。</p>\r\n<p style=\"text-indent: 2em\">瓦茨拉夫&middot;哈维尔1936年10月5日出生于捷克首都布拉格一个贵族企业主家庭。1957年毕业于布拉格理工大学经济系。1957至1959年服兵役。后从事戏剧创作，在ABC剧院、扎勃拉德利剧院工作，先后担任舞台技术人员、编剧、助理导演、剧作家，其间曾在音乐艺术学院戏剧系学习。1968年成为无党派人士俱乐部成员，任独立作家俱乐部(反对派组织)主席，同年起成为捷克笔会成员。</p>\r\n<p style=\"text-indent: 2em\">1968年之后，由于参加&ldquo;七七宪章&rdquo;运动并担任发言人，几次被捕入狱。1969年被禁止从事艺术活动，当工人。1970至1989年三次入狱，被关押近5年。1977年1月为&ldquo;七七宪章&rdquo;组织发起人和第一个发言人，1989年9至12月为该组织临时发言人。1989年11月，他参与创建了公民论坛，并成为公民论坛的主要代表。</p>\r\n<p style=\"text-indent: 2em\">哈维尔是捷克斯洛伐克联邦共和国最后一任总统(1989年至1992年)，也是捷克独立(1993年1月捷克和斯洛伐克分成两个独立国家)后第一任总统(1993年至2003年)。</p>\r\n</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (235,'<p style=\"text-indent: 2em\"><strong>■ &ldquo;热带风暴突然袭击菲律宾&rdquo;追踪</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>菲风暴遇难者升至652人 800人失踪 </strong></p>\r\n<p style=\"text-indent: 2em\"><strong>红十字会负责人称，死者多为妇女儿童；很多村庄被洪水冲走；两万余士兵参与救灾 </strong></p>\r\n<p style=\"text-indent: 2em\">18日，菲律宾遇热带风暴&ldquo;突然袭击&rdquo;第三天，菲南部地区因风暴带来的灾害死亡者迅速增多。截至当天，遇难者已从当初的200多人上升到652人，另有至少800人失踪。</p>\r\n<p style=\"text-indent: 2em\"><strong>睡梦中洪水夺命</strong></p>\r\n<p style=\"text-indent: 2em\">热带风暴&ldquo;天鹰&rdquo;16日晚在菲律宾南部棉兰老岛登陆，两个沿海城市&mdash;&mdash;伊利甘市和卡加延德奥罗市部分村镇受灾最重。很多村庄被突如其来的洪水冲走，附近的山体也被暴雨冲垮。由于之前没有预料到风暴如此猛烈，很多人在睡梦中就被洪水夺去了生命。</p>\r\n<p style=\"text-indent: 2em\">根据菲律宾红十字会的统计数字，截至当地时间18日，&ldquo;天鹰&rdquo;造成伊利甘等重灾区至少652人死亡，另有800多人失踪。这两个数字都比前一天的统计大幅度上升，17日，菲官方公布的死亡人数为250人，失踪400多人。菲红十字会负责人温杜林&middot;旁恩说，死者中大部分是妇女和儿童。</p>\r\n<p style=\"text-indent: 2em\"><strong>一天下了一月雨</strong></p>\r\n<p style=\"text-indent: 2em\">据悉，由于遇难者太多，伊利甘市和卡加延德奥罗市都出现了装尸袋紧张的状况，各地正在向两处紧急调运。</p>\r\n<p style=\"text-indent: 2em\">菲律宾气象台报告称，在短短12个小时里，&ldquo;天鹰&rdquo;就给灾区带来了多达一个月的平均降水量，造成巨大损失。</p>\r\n<p style=\"text-indent: 2em\">18日，菲律宾国防部长加兹明来到灾区视察救援。伊利甘市和卡加延德奥罗市的一些地区因受风暴影响，电力系统和手机信号都出现了中断现象，菲军方正在努力修复。卡加延德奥罗市的自来水系统也受到影响，大约50万人的饮水出现问题。</p>\r\n<p style=\"text-indent: 2em\"><strong>河畔住宅只剩淤泥</strong></p>\r\n<p style=\"text-indent: 2em\">负责指挥卡加延德奥罗市救援的奥斯亚斯说，由于暴雨和洪水突然到来，当地在很短时间内便变成一片泽国。街上到处都能看到建筑被毁，很多人在睡梦中就被夺去生命。奥斯亚斯称，&ldquo;很多父母正遍寻当地的殡仪馆，以寻找自己的孩子。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">阿莫尔今年21岁，他侥幸逃过一劫。他一直拼命给家里人打电话，但父母的手机一直无人接听。</p>\r\n<p style=\"text-indent: 2em\">洪水稍稍退去后，他立刻冲进了建在河边的家里，发现一切都荡然无存。父母和7个亲戚不知去向。&ldquo;我返回来的时候，只看到淤泥和膝盖那么深的洪水，其他什么都没了。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">气象专家说，虽然菲律宾每年都要遭遇大约20起热带风暴袭击，但在圣诞节前后出现如此强大风暴非常少见。（张乐）</p>\r\n<p style=\"text-indent: 2em\"><strong>■ 相关新闻</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>尚无中国公民伤亡报告</strong></p>\r\n<p style=\"text-indent: 2em\">据新华社电 18日上午，记者与中国驻宿务总领事馆取得了联系，领馆工作人员说，截至目前，尚未接到有中国公民伤亡的报告。</p>\r\n<p style=\"text-indent: 2em\">菲律宾政府目前已全面启动救灾行动。政府建立了几十个避难中心。2万多家庭已被转移至地势较高的安全地带。</p>\r\n<p style=\"text-indent: 2em\">菲律宾总统阿基诺三世要求政府各部门保证灾区所需救援物资的供应，并下令检讨政府灾害应急措施，以把&ldquo;天鹰&rdquo;造成的损失减小到最低程度。菲军方目前已向灾区派遣2万名军人参加救援行动。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (236,'<p style=\"text-indent: 2em\">法新社援引印尼官员的话报道，出事木制渔船搭载大约250人，17日前往澳大利亚圣诞岛途中在距离东爪哇省普里吉海滩74公里的水域倾覆。</p>\r\n<p style=\"text-indent: 2em\">普里吉海滩附近特伦加莱镇参与搜救的官员柯里克&middot;普尔万托说，33名落水乘客已经获救，包括30名男子、一名女子和两名儿童。他们脱水严重，疲惫不堪。&ldquo;生还者依靠6件救生衣漂浮在海面大约5个小时，直到渔民发现并救起他们。&rdquo;当地电视画面显示，十几名获救乘客挤在特伦加莱镇一家诊所，面色惊恐。移民官员在一旁登记他们的信息。</p>\r\n<p style=\"text-indent: 2em\">特伦加莱镇政府发言人约索&middot;米哈尔迪告诉媒体记者，这艘渔船核定载客人数为100人，出事时严重超载，加上遭遇强降雨和大风浪，渔船最终失去平衡并倾覆。</p>\r\n<p style=\"text-indent: 2em\"><strong>搜救受阻</strong></p>\r\n<p style=\"text-indent: 2em\">专业搜救员、军警和附近船只事发后展开联合搜救，但恶劣的天气状况令搜救困难重重。&ldquo;极端天气导致能见度降低，阻碍搜救取得进展，&rdquo;搜救员布赖恩&middot;高蒂尔告诉印尼安塔拉通讯社记者，&ldquo;必须尽快救起落水乘客，不能让他们在海里呆太久。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">普尔万托说，大约60人正展开拉网式搜救，希望尽快救起落水者。&ldquo;他们已经出海23到25个小时，（落水后）可能被冲向附近岛屿，所以搜救必须要快。&rdquo;法新社援引米哈尔迪的话报道，除集中力量搜救失踪人员，当地政府正全力安置获救乘客，为他们提供食物、水和医疗帮助。</p>\r\n<p style=\"text-indent: 2em\">另外，澳大利亚内政部18日宣布，澳方将出动飞机和海军巡逻船，协助搜救落水者。当地搜救人员说，失踪者生还希望渺茫。</p>\r\n<p style=\"text-indent: 2em\"><strong>偷渡避难</strong></p>\r\n<p style=\"text-indent: 2em\">普尔万托说，渔船乘客大多为非法移民，来自伊朗、阿富汗、巴基斯坦等地，向一些中间人支付了2500美元至5000美元不等的现金，寻求前往澳大利亚避难。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;他们从阿拉伯联合酋长国迪拜飞往（印尼首都）雅加达，随后乘旅游客车前往爪哇岛一处不明地点登船，目的地是圣诞岛。&rdquo; 生还乘客、24岁的阿富汗人伊斯马特&middot;阿丁告诉安塔拉通讯社记者，当时他们分乘4辆旅游客车，每辆搭载大约60人。</p>\r\n<p style=\"text-indent: 2em\">印尼交通部发言人班邦&middot;埃尔万说，相关部门正在核实渔船乘客信息。&ldquo;搭载非法移民的船只通常没有登记，经常超载。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">来自中东地区的偷渡者经常取道印尼前往澳大利亚。11月1日，一艘搭载非法移民的木船在西爪哇附近水域沉没，数十人死亡。新华社供本报特稿</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (237,'<p>中新网12月19日电 据外媒报道，数万名巴基斯坦人18日在城市拉合尔举行大规模集会，抗议北约和美国在巴基斯坦和阿富汗边境打死24名巴士兵。</p>\r\n<p>　　11月底，北约武装直升机袭击了巴基斯坦边境军事哨所，导致24名巴士兵死亡，13人受伤。此事在巴基斯坦引发轩然大波，巴各地反美浪潮此起彼伏。美国众议院当地时间14日通过一项议案，冻结对巴基斯坦7亿美元援助金，到巴基斯坦表明愿意协助美军对抗当地土制炸弹为止。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (238,'<p style=\"text-indent: 2em\">美国最后一批驻伊拉克部队18日跨过边界，从伊拉克进入科威特。至此，伊拉克战争划上句号。</p>\r\n<p style=\"text-indent: 2em\">这场战争，开始于莫须有的罪名，结束于留驻士兵司法豁免权的纠葛。近9年战争，并未留下一个安全、稳定的伊拉克。</p>\r\n<p style=\"text-indent: 2em\"><strong>最后一批美军部队跨过伊科边界</strong></p>\r\n<p style=\"text-indent: 2em\">当地时间18日上午7时30分（北京时间12时30分），500名美军士兵乘坐防地雷反伏击车跨过伊拉克与科威特边界。</p>\r\n<p style=\"text-indent: 2em\">这些士兵属于美军第一机械化师第三旅，是最后一批离开伊拉克的美军部队。至此，几乎所有美国士兵已撤离伊拉克。</p>\r\n<p style=\"text-indent: 2em\">留驻在伊拉克的少量美军将由美国大使馆统一管辖。这些部队分为两部分：一部分包括157名士兵，负责训练伊拉克安全部队；另一部分为一支海军陆战队分队，负责保卫美国外交使团的安全。</p>\r\n<p style=\"text-indent: 2em\">回顾近9年的伊拉克战争，开始与结尾皆难言&ldquo;完美&rdquo;。战争开始，美国的理由是消除大规模杀伤性武器的威胁，不过，事实证明，伊拉克蒙受&ldquo;不白之冤&rdquo;；战争临近结束，美军欲走还留，以伊拉克方面为留驻士兵提供司法豁免权为条件，提出一项帮助训练伊拉克安全部队的方案，但伊拉克方面完全没有挽留的意思，一口回绝。</p>\r\n<p style=\"text-indent: 2em\"><strong>美军士兵中有人欢喜，有人迷茫</strong></p>\r\n<p style=\"text-indent: 2em\">最后一批离开伊拉克的美军士兵当中，有人欢喜，有人迷茫。</p>\r\n<p style=\"text-indent: 2em\">一等兵马丁&middot;兰姆告诉法新社记者：&ldquo;这是种不错的感觉&hellip;&hellip;我们知道这是在伊拉克的最后任务。这是历史的一部分，你知道，我们是最后离开的人。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">中士蒂文&middot;席尔默现年25岁，2007年后三度赴伊拉克服役。&ldquo;对我而言，将要离开使我开心，&rdquo;他说，&ldquo;尽管我知道，我2013年可能要去阿富汗，但我现在可以有自己的生活。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">&ldquo;一旦这些战争结束，我想知道我会去干什么。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">上士克里斯蒂安&middot;舒尔茨告诉路透社记者：&ldquo;这一切就此结束是件好事，我从一开始就在这里。我看到了许多变化，看到了许多进展，也看到了许多坏事。&rdquo;</p>\r\n<p style=\"text-indent: 2em\"><strong>如今的伊拉克或许&ldquo;不是美国想要的&rdquo;</strong></p>\r\n<p style=\"text-indent: 2em\">与阿富汗战争类似，按照美国的最初想法，伊拉克战争应该在数月内结束。不过，推翻政权容易，推行美国思维并不这么简单。</p>\r\n<p style=\"text-indent: 2em\">一些分析师认为，对美国而言，打击伊拉克的真实目的是在阿拉伯世界腹地扶植一个亲西方的新政府。不过，8年过去，如今的伊拉克或许不是美国想要的那一个。</p>\r\n<p style=\"text-indent: 2em\">美国人谈及战争，进展、改变、成功等词汇往往用来形容战争的积极影响，胜利一词却鲜有提及。兑现撤军承诺的美国总统贝拉克&middot;奥巴马本月14日接受美国广播公司访问时不谈胜利，谈机会。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;我们的部队成功完成任务，给了伊拉克一个拥有成功未来的机会。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">或许，美国方面认为，萨达姆&middot;侯赛因走了，&ldquo;基地&rdquo;组织压制了，伊拉克就有了&ldquo;机会&rdquo;。不过，对伊拉克民众而言，这种机会可能难以消受。</p>\r\n<p style=\"text-indent: 2em\">战争颠覆了原有的权力架构，释放出内斗的巨大能量，各派政治势力冲突不断；伊拉克库尔德地区和中部地区一再谋求独立，各教派矛盾凸显；民众仍旧生活在恐惧之中，爆炸、袭击几乎天天发生。</p>\r\n<p style=\"text-indent: 2em\">一些分析师评价，伊拉克如今有了更多议员，却少了共同目标。美国人走了，战争的阴影还在。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (239,'<p style=\"text-indent: 2em\">【俄罗斯远东地区一座石油钻井平台18日倾覆沉没，平台上67人中14人获救，至少4人丧生、49人失踪。由于海上气候条件恶劣，救援工作目前进展缓慢。当地紧急情况部门官员说，出事钻井平台所储燃油量少，即便泄漏也不会引发环境灾难。】</p>\r\n<p style=\"text-indent: 2em\"><strong>平台倾覆</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>人员落海</strong></p>\r\n<p style=\"text-indent: 2em\">法新社援引当地紧急情况部发言人泰穆拉兹&middot;卡萨耶夫的话报道，&ldquo;科尔斯卡耶&rdquo;（又译&ldquo;克拉&rdquo;）石油钻井平台出事地点位于距萨哈林岛200公里的鄂霍次克海中心海域。</p>\r\n<p style=\"text-indent: 2em\">当时，一艘破冰船和一艘拖船正试图将钻井平台拖拽至萨哈林岛，但船只&ldquo;舷窗遭海浪和冰块冲击后破损，船舱开始进水&rdquo;，导致作业中断。平台上人员等待直升机救援时平台倾覆沉没，没有来得及登上救生筏逃生。</p>\r\n<p style=\"text-indent: 2em\">按卡萨耶夫的说法，钻井平台当天出事前已经出现故障。</p>\r\n<p style=\"text-indent: 2em\">俄罗斯新闻社的说法与法新社存在出入。这家通讯社报道，&ldquo;科尔斯卡耶&rdquo;钻井平台18日凌晨发出求救信号。一艘直升机、一艘破冰船和一艘救援船随后前往营救。</p>\r\n<p style=\"text-indent: 2em\">俄罗斯运输部说，平台上67人中53人为水手，另外14人系工人和后勤服务人员。</p>\r\n<p style=\"text-indent: 2em\"><strong>顶风搜救</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>49人失踪</strong></p>\r\n<p style=\"text-indent: 2em\">救援人员说，67人全部身着防水服和救生衣。救援人员正顶着4米高海浪和时速70公里的强风全力施救。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;14名生还者已从水中救出，&rdquo;俄罗斯水运部门在一份声明中说，&ldquo;水中还发现4人，已无任何生命迹象。&rdquo;由于天气状况恶劣，救援人员尚无法将他们拖出水。</p>\r\n<p style=\"text-indent: 2em\">49人仍旧下落不明。俄罗斯运输部说，&ldquo;阿特拉斯&rdquo;号救援船有望于18日下午到达平台沉没地点，另一艘船只&ldquo;尤里&middot;塔拉普罗夫&rdquo;号将于19日晨抵达。另外，两架直升机将相继加入救援。</p>\r\n<p style=\"text-indent: 2em\">据萨哈林州紧急情况总局负责人介绍，由于事发海域风高浪急，给救援工作带来极大难度。搜救工作预计将在天黑前被迫停止，在必要情况下将于19日早晨恢复搜救。</p>\r\n<p style=\"text-indent: 2em\"><strong>储油量少</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>无碍环境</strong></p>\r\n<p style=\"text-indent: 2em\">卡萨耶夫说，平台已完全沉没，但不会对生态环境构成威胁。&ldquo;钻井平台所储燃油量少且储藏在密闭油箱内，不会有泄漏危险。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">这座钻井平台今年9月投入钻油工作，原打算3个半月内在海平面3500米以下钻出一口油井。俄罗斯天然气工业股份公司一名发言人说，钻井平台当天出事前已完成既定作业，正在前往基地。</p>\r\n<p style=\"text-indent: 2em\">俄新社援引当地调查委员会一份声明说，将对钻井平台倾覆事件展开刑事调查，以查明平台管理人员是否存在违反安全操作条例的行为。</p>\r\n<p style=\"text-indent: 2em\">综合新华社、《中国日报》</p>\r\n<p style=\"text-indent: 2em\"><strong>■ 最新进展</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>梅德韦杰夫令</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>彻查事故原因</strong></p>\r\n<p style=\"text-indent: 2em\">据新华社电 俄罗斯总统梅德韦杰夫18日责成有关部门对&ldquo;克拉&rdquo;海上石油钻井平台沉没事故相关情况进行调查。</p>\r\n<p style=\"text-indent: 2em\">据俄新社报道，俄紧急情况部第一副部长察利科夫当天向梅德韦杰夫汇报了救援工作的进展情况。察利科夫说，尽管天气情况复杂，救援工作仍在进行当中。</p>\r\n<p style=\"text-indent: 2em\">俄事故调查委员会说，事发时，两艘船正试图将该钻井平台拖拽至萨哈林岛。对钻井平台进行牵引作业时违反安全条例以及忽视当时的恶劣天气条件是事故发生的主要原因。</p>\r\n<p style=\"text-indent: 2em\"><strong>■ 新闻链接</strong></p>\r\n<p style=\"text-indent: 2em\">&ldquo;科尔斯卡耶&rdquo;石油钻井平台1985年在芬兰建造，长69米，宽80米，最多可容纳102人。平台为一家俄罗斯油气开采企业所有，后者与俄罗斯天然气工业股份公司存在合同关系。</p>\r\n<p style=\"text-indent: 2em\">俄罗斯天然气工业股份公司主要从事天然气勘探、开采、运输、加工和销售。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (240,'<p>以色列18日晚释放了550名巴勒斯坦在押人员，标志着以方与巴勒斯坦伊斯兰抵抗运动(哈马斯)的换人协议得到全面落实。</p>\r\n<p>　　此次获释人员主要是巴勒斯坦民族解放运动(法塔赫)成员，大多是因投掷石块、燃料瓶被判刑，其中400人刑期已过大半。以监狱管理部门此前曾表示，这些人被释放后，将有41人被送往加沙，两人前往东耶路撒冷，两人前往约旦，其余的进入约旦河西岸。</p>\r\n<p>　　当天傍晚，在约旦河西岸贝图尼亚检查站等候的获释人员亲属与以军发生冲突，造成15名巴勒斯坦人和1名以军士兵轻伤。新华社记者在离贝图尼亚不远的奥弗监狱看到，直升机和瞭望气球在冲突地点上空盘旋，驻守检查站的以军发射了数枚照明弹，发射催泪弹的声音清晰可闻。以方随后改变了释放地点，部分获释人员经由一以军事基地中转回到约旦河西岸城市拉姆安拉。</p>\r\n<p>　　根据以色列和哈马斯今年10月达成的协议，以当局共释放1027名在押巴勒斯坦人员，以换取被哈马斯俘获的以军士兵沙利特。换人协议分两阶段实施，10月18日，第一批477名巴勒斯坦人重获自由，被关押了5年多的沙利特同时获释回国。(记者袁震宇 郝方甲)</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (241,'<p style=\"text-indent: 2em\"><strong>专家提醒,郊区别墅盘蚁害几率高 灭蚁最好找专业机构</strong></p>\r\n<p style=\"text-indent: 2em\">羊城晚报讯 记者赵燕华、实习生张蕾报道：&ldquo;市民发现白蚁后，最好不要自行处理，简单地喷洒药物治标不治本，应尽快找有资质的白蚁防治单位和白蚁防治专业技术人员上门进行处理。&rdquo;18 日举行的广州市白蚁防治宣传活动中，市白蚁防治行业协会相关负责人透露，与市区房屋比较而言，郊区豪宅、别墅盘出现蚁害的几率要稍高， 有的甚至已经成了白蚁的&ldquo;重灾区&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">另据悉，海珠桥、海印桥、广州大桥和解放桥这四座大桥，发现有特征明显的蚁害，已制定灭蚁方案。</p>\r\n<p style=\"text-indent: 2em\"><strong>灭蚁要找专业机构</strong></p>\r\n<p style=\"text-indent: 2em\">市房屋安全管理所所长黄光华介绍，广州市地处亚热带地区， 气候温暖潮湿，白蚁种类繁多，是全国白蚁危害最严重的城市之一。白蚁危害具备隐蔽性、广泛性以及严重性三个特征。除开每年春夏季的分飞， 白蚁其余时间都隐蔽在房屋基础、墙体构件以及树木内部，不易被发觉。广州市对白蚁预防的方针主要是&ldquo;以预防为主，防治结合，综合治理&rdquo;，对新建房屋进行白蚁预防是防治白蚁危害的主要途径。</p>\r\n<p style=\"text-indent: 2em\">黄光华表示，市民发现白蚁后，最好不要自行处理，因为白蚁触角灵敏，一旦破坏蚁路，它们就会隐蔽起来，简单地喷洒药物治标不治本，应尽快找有资质的白蚁防治单位和白蚁防治专业技术人员上门进行处理。</p>\r\n<p style=\"text-indent: 2em\"><strong>绿化带越好越要防蚁</strong></p>\r\n<p style=\"text-indent: 2em\">市白蚁防治行业协会会长胡伟权介绍，广州市发生白蚁蚁害的住宅中，以一楼的单位比例较大，而且通常都是外围绿化带比较好的地段，容易招惹白蚁顺着绿化进屋。</p>\r\n<p style=\"text-indent: 2em\">在受蚁害的房屋中，砖木结构和混凝土结构的都有。与市区房屋相比较而言，郊区豪宅、别墅盘出现蚁害的几率高。</p>\r\n<p style=\"text-indent: 2em\">目前， 广州不少别墅、豪宅已经成了白蚁的&ldquo;重灾区&rdquo;。别墅一般都建筑在水木丰盛的地方或者山上，这些地方本来就可能有白蚁。同时，现在别墅装修多采用木材，白蚁落脚机会比一般房屋多。</p>\r\n<p style=\"text-indent: 2em\"><strong>广州四大桥蚁患明显</strong></p>\r\n<p style=\"text-indent: 2em\">据了解，海珠桥、海印桥、广州大桥和解放桥这四座大桥， 也有特征明显的蚁害，要先进行灭治。</p>\r\n<p style=\"text-indent: 2em\">胡伟权告诉记者，去年普查时就发现海珠桥、海印桥、广州大桥、解放桥等四座位于市区的桥梁有白蚁危害的现象， &ldquo;虽然不会影响其结构安全&rdquo;， 但是均向桥梁主管单位报告进行处理。</p>\r\n<p style=\"text-indent: 2em\">最近一段时间， 海珠桥大修备受关注。胡伟权说，海珠桥受到白蚁侵害较为严重，&ldquo;防震枕木几乎被白蚁掏空&rdquo;。</p>\r\n<p style=\"text-indent: 2em\"><strong>小贴士</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>开发商须担灭蚁责任</strong></p>\r\n<p style=\"text-indent: 2em\">根据《广州市房屋安全管理规定》,&ldquo;新建、改建、扩建的商品房，由房地产开发企业实施白蚁预防处理； 未按规定实施白蚁预防处理的， 由房地产开发企业承担白蚁灭治责任。其他房屋未实施白蚁预防处理的， 由房屋安全责任人承担白蚁预防责任。&rdquo;</p>\r\n<p style=\"text-indent: 2em\"><strong>6月是灭白蚁好时机</strong></p>\r\n<p style=\"text-indent: 2em\">广州市白蚁防治行业协会网站：www.gzact.org.cn，电话：81081651。</p>\r\n<p style=\"text-indent: 2em\">白蚁防治专业技术人员上门处理,一般而言，喷药后约25 天左右可以灭治完白蚁，每年6 月份是灭白蚁的好时机。</p>\r\n<p style=\"text-indent: 2em\">在治理白蚁蚁害收费上， 目前没有规定统一的价格， 但一般收费均价大概是10 元/平方米。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (242,'<p style=\"text-indent: 2em\"><strong>世界经济&ldquo;寒流&rdquo;来袭，已经对我国的外向型企业产生较大影响。</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>在广州、东莞、佛山等多个城市，企业正面临订单转移、汇率波动、摩擦加剧、成本增加等难题。</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>面对复杂的国内外经济环境，珠三角的外向型企业试图在夹缝中寻找新的商机。</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>外贸形势堪比金融危机时期</strong></p>\r\n<p style=\"text-indent: 2em\">11月中旬，东莞一家服装加工厂的负责人告诉《经济参考报》记者，订单转移已经成为企业的严峻考验。</p>\r\n<p style=\"text-indent: 2em\">这位负责人说，今年前十个月，公司销售总额同比下降三四成。以往一张订单会有三四万件，现在五六千件，少的只有一千多件。由于劳动力、土地成本较低，而且语言相通，外商将简单款式衣服的订单都转移到印度、孟加拉等国家。</p>\r\n<p style=\"text-indent: 2em\">订单缩水只是外贸企业面临困境的一个侧面。记者在调研中发现，不同的外贸企业由于成本结构、产品类型的不同，而在困境的感受各有不同。在纺织、鞋类等劳动密集型产业，对劳动力短缺的痛楚感同身受；在主打欧美市场的陶瓷、玩具、L E D产业，受汇率、贸易保护主义的影响最大；在资本密集型的机械制造产业的中下游，则最先感受到资金紧缩的压力。</p>\r\n<p style=\"text-indent: 2em\">上述服装公司的负责人就对劳动力短缺的现状连连诉苦，&ldquo;厂里原来有120人，现在70多人，而真正 坐 定 在 车 位 上 的20多 个 人 ， 很 多 人 选 择 当 临 时工。原来介绍一个人进来做半年，可以拿到一两百元的 介 绍 费 ， 现 在 出 台 新 措 施 ， 介 绍 费 已 经 提 高 到1000元，但还是招不够人。希望春节过后能够通过老员工带回更多的人手。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">佛山市一家电子公司L E D事业部经理则告诉记者，人民币升值是吞噬企业利润的&ldquo;杀手&rdquo;。今年以来为此拒绝了一半左右的订单。</p>\r\n<p style=\"text-indent: 2em\">这 位 负 责 人 说 ： &ldquo; 之 前 企 业 出 口 占 总 产 值 的40 %， 今 年 这 个 数 字 不 到10 %。 人 民 币 一 直 在 升值，而外商却还坚持原来的价格。目前L E D行业的纯利润在10%左右，汇率波动这一项要吞噬我们大半的利润。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">今年以来，欧美对陶瓷、玩具等行业纷纷筑起技术和环保壁垒。中国陶瓷工业协会佛山办事处主任蓝卫兵表示，欧盟此前对我国瓷砖作出反倾销终裁，征收长达5年的69 .7%的惩罚性关税。受此影响，预计 今 年 佛 山 陶 瓷 出 口 欧 盟 将 下 降5 0 %。 更 严 重 的是，该仲裁已经在阿根廷、秘鲁等国引发效仿。</p>\r\n<p style=\"text-indent: 2em\"><strong>&ldquo;今年的外贸形势不亚于2008年金融危机。&rdquo;多位企业负责人说。</strong></p>\r\n<p style=\"text-indent: 2em\">国内原材料、劳动力成本上升、融资成本增加等因素，同样波及外向型企业。一项调查显示，62 .5%的 企 业 反 映 原 材 料 价 格 较 去 年 上 升1 5 %- 3 0 %，31 .2%的企业反映原材料价格上涨30%以上。</p>\r\n<p style=\"text-indent: 2em\">业内人士表示，在信贷规模受控的双重压力下，银行资金持续紧张，中小企业总体融资成本随之提高。为保证盈利目标，银行采用&ldquo;以价抵量&rdquo;形式，相对上年普遍提高了对中小企业贷款利率上浮水平，贷款平均利率由上年同期的基准利率上浮10%提高为基准利率上浮20%-35%。</p>\r\n<p style=\"text-indent: 2em\">在&ldquo;钱荒&rdquo;中，即使有雄心拓展的企业也不得不放慢了步伐。</p>\r\n<p style=\"text-indent: 2em\">东莞一家数码机械公司有关负责人说，公司主动转型，进军战略新兴产业，计划发展非晶硅薄膜太阳能项目，但由于银行的贷款难以申请，项目动工因此推迟了近半年。</p>\r\n<p style=\"text-indent: 2em\"><strong>广东进出口增速下降</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>寒流袭来，企业感受到了冷意。有关部门的统计数据也反映了这一变化。</strong></p>\r\n<p style=\"text-indent: 2em\">广东省外经贸厅的统计数据显示，今年1~10月，广东全省进出口7473 .7亿美元、增长19 .1%。其 中 ， 出 口4 3 3 9 .9亿 美 元 、 增 长2 0 .5 %； 进 口3133 .8亿美元、增长17 .3%。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;下半年以来，几乎每个月的进出口增速都比上月下滑一两个百分点。&rdquo;广东省外经贸厅相关负责人说。数据显示，广东省进出口总额单月计，7月同比增长13 .9%，8月同比增长12 .7%，9月同比增长8 .3%，10月同比增长7 .8%。</p>\r\n<p style=\"text-indent: 2em\">据广东省外经贸厅相关负责人介绍，当前外贸面临以下多重挑战：一是美欧债务危机导致的信心下滑、经济放缓，对外需产生直接影响。库存大量积压，基本处于&ldquo;去库存化&rdquo;的状态。例如，某跨国公司已将库存周期由8个星期调低为6个星期，下调25%。</p>\r\n<p style=\"text-indent: 2em\">二是部分企业无法转嫁或有效消化成本上升，利润下降明显。今年以来，受人民币升值、工资上涨、原材料涨价等多重因素影响，出口企业成本平均上升10%-20%。另外，当前外贸企业面临着税费多、用电难、用地难、融资难等实际困难。据调查，50 .4%的企业利润率下降，21 .7%的企业基本与2010年同期持平，仅27 .9%的企业利润率有所上升。</p>\r\n<p style=\"text-indent: 2em\">三是国际贸易保护加剧，直接冲击部分行业出口。1-9月广东省共发生贸易摩擦案17起。</p>\r\n<p style=\"text-indent: 2em\">四是部分订单转移及部分企业调整产品、市场结构，短期内进出口正经历转型&ldquo;阵痛&rdquo;。目前，部分传统优势产品的出口订单转移到东南亚国家，某著名品牌制鞋公司的全球最大生产基地从中国变成了越南。</p>\r\n<p style=\"text-indent: 2em\">此外，广东省经济和信息化委员会公布的数据也显示，广东省11月份制造业P MI指数值为49 .06，比上月回落0 .70个百分点，连续第二月处于50这个分界线以下。</p>\r\n<p style=\"text-indent: 2em\">分项指标中，11月份新订单指数、采购量指数、购进价格指数、从业人员指数在10月份大幅回落之后，又都继续回落了1 .5个百分点以上。积压订单指数和进口订单指数由前一个月的上升逆转为回落。</p>\r\n<p style=\"text-indent: 2em\">上述部门认为，这些状况表明，广东省制造业总体收缩的趋势已经比较明显，且可能持续到明年第一季度。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (243,'<p style=\"text-indent: 2em\"><strong>语文教育到了最危险的时刻</strong></p>\r\n<p style=\"text-indent: 2em\">作为《收获》杂志的编审，一位职业文字工作者，他以笔为飞刀，掷向中国的语文教育。&ldquo;语文教育到了最危险的时刻，&rdquo;他说，&ldquo;我们只能自我教育，让孩子读到世界上最好的文字。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">廖增湖有过三次难忘的作文经历。</p>\r\n<p style=\"text-indent: 2em\">第一次，女儿乔乔的班主任要求，各位家长写一篇命题作文，题目是《我看考试》，优异者选登在校园报纸上。</p>\r\n<p style=\"text-indent: 2em\">42岁的男人挠破头皮，将自己对考试的恐惧和绝望，写成文章，改了两遍，交给老师。之后，就没了下文。</p>\r\n<p style=\"text-indent: 2em\">很久以后，女儿拿回报纸，他&ldquo;拜读&rdquo;了被选上的家长文章，都是对考试热情洋溢的歌颂。他这才知道，此类应景之作，是有格式的。</p>\r\n<p style=\"text-indent: 2em\">第二次，学校作文竞赛，题目是《我的无烟童年》。女儿乔乔被老师要求参加，而父亲则得到老师暗示，必要时可以帮孩子捉刀。</p>\r\n<p style=\"text-indent: 2em\">廖坚持让女儿自己写，末了帮了点小忙，给200字的作文增删了一些句词。</p>\r\n<p style=\"text-indent: 2em\">后来竞赛成绩揭晓，女儿作文名落孙山。</p>\r\n<p style=\"text-indent: 2em\">第三次，2007年，他应江苏《现代快报<!--keyword--> (<a href=\"http://t.qq.com/xdkbnj#pref=qqcom.keyword\" target=\"_blank\" class=\"a-tips-Article-QQ\">微博</a>)<!--/keyword-->》之邀，写了一篇江苏省同题作文《怀想天空》，结果被资深语文教师判定离题。</p>\r\n<p style=\"text-indent: 2em\">三次经历不仅难忘，也颇让他尴尬。</p>\r\n<p style=\"text-indent: 2em\">廖增湖还有个身份&mdash;&mdash;作家。在圈子里，他笔名叶开，素来以阅读面宽阔文章不落俗套著称，几部长篇小说，口碑很好，被评论界称为&ldquo;上海的王朔，中国的拉伯雷&rdquo;。如今是知名文学杂志《收获》的编审。</p>\r\n<p style=\"text-indent: 2em\">他苦笑着对《中国新闻周刊》说，自己算是个职业文字工作者，每年不知要和多少名家大作过招，编改之间，从来都应付自如，颇受好评。但一旦应对女儿的语文课业，他似乎总显得捉襟见肘。</p>\r\n<p style=\"text-indent: 2em\">后来，女儿学到三年级第七单元48课《智烧敌舰》时，遇到一个问题：&ldquo;三国里谁最有智慧?&rdquo;女儿刚看过《三国演义》彩图本，觉得答案应该是：&ldquo;孔明和庞统&rdquo;。叶开在一旁看了，颇开心：&ldquo;孩子是真看明白了。&rdquo;但次日老师批改此题时，给了女儿一个大红叉，因为标准答案是：&ldquo;诸葛亮&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">这太荒诞了。叶开有些愤怒：&ldquo;这么干，简直误人子弟!&rdquo;</p>\r\n<p style=\"text-indent: 2em\">从那时起，他花更多时间留心女儿的语文课本，一看吓了一跳，很多课文品质低劣，入选名作亦遭大量篡改，&ldquo;这样的课本简直是一堆垃圾。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">他坐不住了，决定向语文课本和惯性的教学方式开战。&ldquo;牛奶的三聚氰胺伤害孩子的身体，不好的课文则败坏他们的智力和精神。&rdquo;他说。</p>\r\n<p style=\"text-indent: 2em\">2009年，他的朋友，《语文教学与研究》主编晓苏请他开个专栏，叶开正憋着一肚子话，于是一口气写了12篇专栏文章，批判语文教材和语文教育的现状。他给专栏起名&ldquo;语文之痛&rdquo;，引起各方关注，而今年前不久，又出版新书，题目直截了当&mdash;&mdash;《对抗语文》。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (244,'<p style=\"text-indent: 2em\">在国家发改委做出调查中国联通和中国电信涉嫌宽带接入领域垄断的决定一个月之后，中国电信、中国联通日前同时向国家发改委提交中止反垄断调查的申请，承认企业在互联互通及价格上存在不合理行为，同时承诺整改提速降费。</p>\r\n<p style=\"text-indent: 2em\">尽管两大巨头在宽带接入领域&ldquo;服软&rdquo;，但对于备受关注的手机话费&ldquo;服务到期余额不退&rdquo;，几家运营商始终保持高度一致，不松口。在过去的六年中，虽然这一&ldquo;最牛霸王条款&rdquo;一直遭到消费者协会和媒体轮番&ldquo;炮轰&rdquo;，但通信运营商我行我素。对此，北京市消费者协会负责人昨日向晨报记者重申立场，&ldquo;余额不退&rdquo;不合法。</p>\r\n<p style=\"text-indent: 2em\"><strong>■消费者质疑</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>运营商巧取豪夺</strong></p>\r\n<p style=\"text-indent: 2em\">根据中国移动、中国联通和中国电信三大运营商的规定，不同面值的话费充值卡有效期不同：50元面值的手机充值卡有效期为90天；500元面值有效期为540天。用户账户超出有效期，&ldquo;无论账户是否有余额，您不能呼出和接听任何电话&hellip;&hellip;&rdquo;如果过了锁定期仍未充值，&ldquo;您的号码将会自动注销，号码注销后，账户中的余额不再返还用户。&rdquo;记者昨日分别致电中国移动、中国联通和中国电信，客服人员均告诉记者，话费充值卡依然按话费面值规定了不同的有效期。</p>\r\n<p style=\"text-indent: 2em\">由于这项规定，很多人遭遇尴尬，充值卡到期手机被停机，如果不充值，剩余金额就不能继续使用。有消费者直指这一行为是&ldquo;巧取豪夺&rdquo;。在网络论坛上，有很多求助帖：&ldquo;联通手机充值卡还有近200元钱，马上就要过期了，我没这么多电话能打，大家告诉我有什么办法能把充值卡有效期延长啊？&rdquo;</p>\r\n<p style=\"text-indent: 2em\">手机&ldquo;服务到期余额不退&rdquo;的规定，老年用户反映更为强烈。北京市消协随机询问过100余位老人，近一半人表示曾有手机话费在有效期内用不完而被迫充值的经历。</p>\r\n<p style=\"text-indent: 2em\"><strong>■律师说法</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>余额所有权应归消费者</strong></p>\r\n<p style=\"text-indent: 2em\">近年来，通信商虽然对资费进行过一些调整，逐步降低了漫游费和月租费，实行了单向收费，但对手机&ldquo;服务到期余额不退&rdquo;始终未做让步。中国政法大学教授吴景明等专家认为，手机话费&ldquo;服务到期余额不退&rdquo;属于不正当得利，不符合《消费者权益保护法》、《合同法》、《物权法》等法律，严重损害了消费者的合法权益。</p>\r\n<p style=\"text-indent: 2em\">市消协专家委员会委员、律师邱宝昌说，手机账户余额所有权应归属消费者。消费者通过充值卡向手机账户充值预付话费，与通信运营商形成了一种事实上的服务合同关系。通信运营商为充值卡设定服务有效期，实际上是为合同的履行规定了期限。有效期结束，双方权利义务关系即终止，运营商可以不再向消费者提供服务。然而，服务的终止与手机账户余额的归属是两个不同的概念。电信运营商提供通信服务，应当扣除已消费手机账户话费，如果账户内还有话费余额，余额的所有权仍然属消费者所有。</p>\r\n<p style=\"text-indent: 2em\"><strong>■消协观点</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>余额不退&ldquo;不合法&rdquo;</strong></p>\r\n<p style=\"text-indent: 2em\">记者就&ldquo;服务到期余额不退&rdquo;问题多次联系采访电信运营商，均未得到正面回应。市消协负责人昨日接受采访时重申，余额不退的做法不合法。2007年，信息产业部电信管理局《关于用户手机销户要求余额退现金申诉处理的回复》明确提出：&ldquo;手机账户余额归用户所有，当用户要求对账户内金额进行支配使用时，通信运营企业应切实履行其配合义务，向用户退还账户内余额。&rdquo;今年5月，国务院办公厅转发中国人民银行、监察部等部门《关于规范商业预付卡管理意见的通知》，要求记名商业预付卡不设有效期，不记名商业预付卡有效期不得少于3年。对于超过有效期尚有资金余额的，发卡人应提供激活、换卡等配套服务。</p>\r\n<p style=\"text-indent: 2em\">市消协指出，通信资源仍具有相对独占性，与处于劣势的消费者相比，通信行业处于绝对的&ldquo;强势&rdquo;。市消协提出，电信运营商应采取措施保障消费者可以自由支配手机账户话费金额，一是按照有关规定取消设置话费有效期；二是如要设置有效期，应完善账户话费余额处理方式，在服务期满后，应当设定多种退返途径，允许消费者自由选择。晨报记者 肖丹</p>\r\n<p style=\"text-indent: 2em\"><strong>■调查数据</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>超九成人认为不合理</strong></p>\r\n<p style=\"text-indent: 2em\">去年7月，北京市消协联合搜狐网在全国范围内开展了电信行业资费民意调查，共有11284人次参与投票。调查结果显示，被调查人次的91.92%认为通信经营者&ldquo;使用期满后余额不退&rdquo;规定不合理。中国法学会消费者权益保护法研究会会长李国光表示，用户对包括&ldquo;余额到期不退&rdquo;等手机资费问题已质疑了很久，电信部门应该正视，给予明确答复。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (245,'<p style=\"text-indent: 2em\">时报讯 历时达两年多的中国足坛反赌扫黑案将于今天(19日)上午8点半，在辽宁省铁岭市中级人民法院正式开审，首个出庭受审的是曾担任中国足协裁判委员会秘书长和女子部主任的济南人张建强，与他同时受审的还有前陕西国力俱乐部董事长李志民。</p>\r\n<p style=\"text-indent: 2em\">张建强和李志民的开庭公告，早在本月15日就张贴出来了，正是这两份公告，让反赌案开审的时间得以确定，因为在此之前，曾因误传，这一引人关注的案件&ldquo;被开庭&rdquo;了多次。记者从法院张贴出的开庭公告上获知，张建强被指控的罪名是&ldquo;受贿和非国家工作人员身份受贿&rdquo;，而李志民的罪名则是&ldquo;非国家工作人员受贿&rdquo;。两人受审的时间均在今天上午8点半，张建强在第21庭，李志民在第14庭，两人并非像外界所传的那样并案审理。</p>\r\n<p style=\"text-indent: 2em\">在对张建强的指控中，出现了&ldquo;受贿&rdquo;和&ldquo;非国家工作人员受贿&rdquo;，这两者有何区别，在量刑上会有何不同呢？对此，有律师解释说，因为足管中心与足协是两块牌子、一套人马，张建强的&ldquo;受贿&rdquo;，指他在作为国家公务人员、即以足管中心工作人员的身份时所涉及的犯罪行为，而&ldquo;非国家工作人员受贿&rdquo;则是指他代表足协时的所作所为。虽然同是受贿，但两者的量刑不同，假如认定的是同一数额，&ldquo;非国家工作人员受贿&rdquo;要判5年以下的话，那么&ldquo;受贿&rdquo;则会判5年以上。</p>\r\n<p style=\"text-indent: 2em\">在他们之后，王珀、杜允琪、杨一民、王鑫<!--keyword-->(<span class=\"infoMblog\"><a class=\"a-tips-Article-QQ\" href=\"http://t.qq.com/ruojiruoli0811#pref=qqcom.keyword\" rel=\"ruojiruoli0811\" target=\"_blank\" reltitle=\"王鑫\">微博</a></span>)<!--/keyword-->等人均将逐一受审，在铁岭的这一波审判从今天开始，将持续到22日结束。而从20日到21日，丹东市中级人民法院将审理黄俊杰、周伟新、陆俊、万大雪这4名裁判。</p>\r\n<p style=\"text-indent: 2em\">据悉，此次审理将不会当庭宣判，而最为引人关注的两大前足协掌门人南勇、谢亚龙，以及前国管部官员、国足领队蔚少辉等人的名字，目前并没有出现在开庭公告中。至于他们究竟何时受审，目前说法不一。(特约记者徐平)</p>\r\n<p style=\"text-indent: 2em\"><strong>【相关链接】</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>中国足球反腐大事记</strong></p>\r\n<p style=\"text-indent: 2em\">2009年初，公安部根据国际刑警组织新加坡国家中心局发出的红色通缉令和有关请求，部署辽宁省公安机关协助调查王鑫在新加坡非法操纵足球比赛一案。专案组于同年4月在沈阳将王鑫抓获。</p>\r\n<p style=\"text-indent: 2em\">2009年10月19日，广州市足协官员杨旭因涉嫌行贿被抓。10月下旬，前广药足球俱乐部成为主要调查对象，其财务总监、俱乐部副总和相关官员被警方带走。</p>\r\n<p style=\"text-indent: 2em\">2009年11月，前辽足球员吕东被警方调查。中国足协联赛部工作人员范广鸣被警方带走协助调查。中国足协通过其官方网站发表了一份《支持抓赌打假行动，促进足球健康发展》的声明。</p>\r\n<p style=\"text-indent: 2em\">2009年11月10日，担任过多家<!--keyword--><a class=\"a-tips-Article-QQ\" href=\"http://sports.qq.com/zt2011/2011csl/index.htm\" target=\"_blank\"><!--/keyword-->中超<!--keyword--></a><!--/keyword-->球队总经理的王珀被警方调查。</p>\r\n<p style=\"text-indent: 2em\">2009年11月25日，公安机关首次披露足球打假真相。公安机关表示，已查明王鑫等人涉嫌利用商业贿赂，操纵<!--keyword--><a class=\"a-tips-Article-QQ\" href=\"http://sports.qq.com/csocce/index.htm\" target=\"_blank\"><!--/keyword-->国内足球<!--keyword--></a><!--/keyword-->比赛个别场次的犯罪事实，相关涉案人员已被公安机关依法刑事拘留。2006年8月广州医药队对山西路虎队的一场比赛被认定为假球。</p>\r\n<p style=\"text-indent: 2em\">2010年1月22日，中国足球管理层发生重大人事变动，原国家体育总局足球运动管理中心主任、党委书记南勇，副主任杨一民被免去职务，原水上运动管理中心主任韦迪接替南勇成为足管中心新主任、党委书记。</p>\r\n<p style=\"text-indent: 2em\">2010年1月27日，公安机关公布足球打假反赌案最新进展：南勇、杨一民等人被依法刑事拘留，已查明范广鸣涉嫌受贿操纵比赛。</p>\r\n<p style=\"text-indent: 2em\">2010年3月1日，公安部治安管理局证实，中国足协副主席南勇、杨一民，裁判委员会原主任张建强因操纵足球比赛涉嫌收受贿赂犯罪，经检察机关批准，予以依法逮捕。</p>\r\n<p style=\"text-indent: 2em\">2010年3月，有媒体披露陆俊、黄俊杰等裁判被警方带走协助调查。后经证实，2010年4月，陆俊被批准逮捕。</p>\r\n<p style=\"text-indent: 2em\">2010年10月5日，从公安机关足球打假反赌专案组获悉，同年9月初被公安机关立案侦查的谢亚龙、李冬生、蔚少辉因涉嫌操纵足球比赛收受贿赂犯罪，经检察机关批准，已被依法执行逮捕。</p>\r\n<p style=\"text-indent: 2em\">2011年3月30日，陆俊等曝光假球黑哨细节。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (246,'<p style=\"text-indent: 2em\"><strong>兰州窑街煤电集团金河煤矿发生冒顶事故 4人被救出井2人死亡</strong></p>\r\n<p style=\"text-indent: 2em\">新华网兰州12月19日电 （记者姜伟超）记者从甘肃省安监部门了解到，18日夜兰州窑街煤电集团金河煤矿发生冒顶事故，最终4人被救出井，2人死亡。</p>\r\n<p style=\"text-indent: 2em\">18日23时56分，兰州窑街煤电集团金河煤矿掘进四队在1496皮带机头硐室扩掘时，发生冒顶事故，当班出勤的11人中6人被埋。</p>\r\n<p style=\"text-indent: 2em\">其后，窑街煤电集团展开救援。19日2时04分救出3人，送往医院救治，其中2人已死亡。随后又救出3人，都送往煤电公司医院救治。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (247,'<p style=\"text-indent: 2em\"><strong>团上海市委开展新生代农民工精神文化生活现状调研</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>新生代农民工：文化市场与公共服务的夹心层</strong></p>\r\n<p style=\"text-indent: 2em\">&ldquo;在一家酒店做早点，每天3:00开工，10:00结束。平时空闲时间充裕，但要督促他（指她的儿子）学习，没事了就看电视剧打发时间。&rdquo;这是在上海某酒店做早餐的34岁的肖某的生活状态。她最担心自己没有文化，指导不了孩子的学习，&ldquo;同时，找起工作来也十分艰难。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">&ldquo;常上网、泡吧、唱歌、和朋友出去玩，在家会看看电视，主要是娱乐、时尚方面的。&rdquo;这是26岁在上海打理服装店的外来务工女青年的业余生活，她说，一般的信息渠道是上网、朋友介绍和身边宣传信息等。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;一年当中除了回两次家，其他时间基本上在理发店内，从早晨7点到晚上9点，没有节假日，很少出去玩。家里主要靠自己供给，特别是供养子女上学是最大的开销，所以自己在这边也尽量节省。无聊的时候会看电视，但不会去电影院。&rdquo;这是在上海开理发店的一个年轻小老板的业余生活。</p>\r\n<p style=\"text-indent: 2em\">&hellip;&hellip;</p>\r\n<p style=\"text-indent: 2em\">日前，团上海市委开展关于上海新生代农民工精神文化生活现状的调研。调研结果显示，该市的新生代农民工呈现出从适应集体生活到追求私人空间、就业选择从强调生存取向到看重发展取向的趋势，但是与此不相适应的是，在文化消费方面，新生代农民工处于文化市场与公共服务的夹心层位置，业余生活中，集体活动和社会性活动缺乏。</p>\r\n<p style=\"text-indent: 2em\">调研报告显示，与父辈农民工主要居住在集体宿舍或生产经营场所不同，只有50.1%的新生代农民工居住在工人宿舍中，有41%的新生代农民工选择与他人合租房屋或是独立租房。</p>\r\n<p style=\"text-indent: 2em\">调研人员在走访新生代农民工的住处时发现，很多青年农民工都会对房间进行精心的、个性化的布置，尤其是女性农民工，她们不是将出租房视为一个临时居所，而倾向于将其布置为一个温馨的家。值得关注的是，4.3%的受访者（39人）拥有自购房，调研人员认为，这表明外来务工人员内部也出现了一定的经济分化。</p>\r\n<p style=\"text-indent: 2em\">调研报告同时显示，有64.2%的受访者将&ldquo;赚钱养家&rdquo;作为自己来沪工作的首要动因，但是，选择&ldquo;过城市生活&rdquo;、&ldquo;见见世面&rdquo;、&ldquo;寻找发展机会&rdquo;和&ldquo;为前途考虑&rdquo;等非经济动因的受访者也多达32.9%，其中&ldquo;寻找发展机会&rdquo;占到16%，仅次于&ldquo;赚钱养家&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">对此，调研人员认为，新生代农民工外出务工的动机与父辈已经呈现一定的差异，他们更多地把进城务工看做谋求发展的途径，不仅注重工资待遇，而且也注重自身技能的提高和权利的实现。</p>\r\n<p style=\"text-indent: 2em\">尤其让调研人员感慨的是，交流中，新生代农民工也表达了对电影、音乐会、图书等文化活动的向往，但当前的文化消费市场却让低收入的他们捉襟见肘。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;一般而言，获取文化产品的主要途径不外乎两种：一是通过市场购买，譬如去电影院、剧院消费；二是通过政府提供的公共服务，譬如公共图书馆、社区文化中心。&rdquo;该调研报告指出，新生代农民工处于二者的夹缝之中，他们无力承担相对高昂的文化消费。在访谈中，有多位青年农民工表示：&ldquo;进电影院看进口大片需要80元，一般的片子也要40元。太贵了！&rdquo;但另一方面，他们又缺乏户籍身份，也无法享受由政府提供的公共文化服务项目。</p>\r\n<p style=\"text-indent: 2em\">在此次调查的受访者中，月收入在1200元~3000元的人数占76.4%，有4.4%的人月收入在1200元以下。这样的收入水平在上海几乎只能维持最基本的物质生活，无力在文化产品上有所投入。</p>\r\n<p style=\"text-indent: 2em\">同时，新生代农民工不仅活动范围狭小，而且对所在社区文化生活的参与程度也偏低。问卷显示，&ldquo;根本不知道社区有活动&rdquo;和&ldquo;知道，但没被邀请&rdquo;的受访者共占63.3%，经常参加社区活动的人仅有7.7%。</p>\r\n<p style=\"text-indent: 2em\">因此，该报告将新生代农民工称为在社区文化生活参与方面是主流社会的局外人。</p>\r\n<p style=\"text-indent: 2em\">在此报告基础上，团上海市委建议：党政机关通过制度创新，资源投入，逐步实现文化类公共服务的均等化，使新生代农民工享受与城市居民同等的公共文化资源；通过法律和制度规范，强化用工企业的社会责任，促使其进行人性化管理，通过企业文化建设使新生代农民工认同企业，并提升自身的文化素养；鼓励社会团体参与共治，工青妇等社会团体应关心、吸纳新生代农民工，为改善其精神文化生活提供资源支持。本报记者 崔玉娟</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (248,'<p style=\"text-indent: 2em\">12月12日，因牵涉&ldquo;豆腐渣&rdquo;大桥坍塌事故，海南省万宁市公路局原局长周德政、该局生产技术股原股长蔡锡雄、分管生产技术股的原副局长郑斌等3人涉嫌渎职犯罪被检察机关公诉。</p>\r\n<p style=\"text-indent: 2em\">经检察机关审查，万宁公路分局在组织实施太阳河大桥水毁修复项目过程中，未依法进行招投标。蔡锡雄和郑斌对施工单位和监理单位及授权委托人资质、资格材料没有进行认真审查、严格把关。2010年12月16日，周德政仅通过主持召开局长办公会就指定了施工承包单位和监理单位。因审查把关不严，导致不具备施工资质的朱福新(另案处理)和不具备监理工程师资格的李清太(另案处理)冒用其他公司名义，分别取得了太阳河大桥水毁修复工程的施工承包权和监理委托权。朱福新通过福建籍包工头林泉美(另案处理)临时召集不具备相关资质的施工人员进场施工。明知施工方违规操作,蔡锡雄及郑斌却没有阻止，从而导致大桥于2011年8月8日坍塌，造成两人死亡、一人轻伤、一人轻微伤，直接经济损失人民币145万元的严重后果。</p>\r\n<p style=\"text-indent: 2em\">2011年9月8日，3名犯罪嫌疑人被依法逮捕。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (249,'<div class=\"Line\">&nbsp;</div>\r\n<div id=\"Cnt-Main-Article-QQ\" bosszone=\"content\" style=\"position: relative\">\r\n<p style=\"text-align: center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic        \" r=\"1\" style=\"width: 400px\"><img alt=\"南京排水管理处原副处长受贿130万获刑逾十年\" src=\"http://img1.gtimg.com/news/pics/hv1/66/139/931/60573786.jpg\" /></div>\r\n<p>&nbsp;</p>\r\n<p align=\"center\" class=\"pictext\"><span style=\"font-size: 12px\">谢谢各位了！张叶 绘</span></p>\r\n<p style=\"text-indent: 2em\">2010年2月底，媒体报道了南京市板仓街雨污分流改造工程出现多处塌陷的问题，事故原因调查中，多处疑点直指主管这段工程的南京市市政排水管理处副处长谭沭兵。 一年后，曾任南京市排水管理处规划建设科科长、南京市给排水工程公司经理的原南京市排水管理处副处长谭沭兵，因受贿罪被法院一审判处有期徒刑10年6个月，没收个人财产60万元。</p>\r\n<p style=\"text-indent: 2em\"><strong>外围取证钓到一条大鱼</strong></p>\r\n<p style=\"text-indent: 2em\">2010年初，南京市检察院收到一封举报信，反映南京市排水管理处副处长罗某（另案处理）涉嫌经济问题。南京市检察院将举报信交建邺区检察院负责初查。</p>\r\n<p style=\"text-indent: 2em\">建邺区检察院的办案人员进行了艰苦而秘密的调查取证工作。他们历时数月，收集了上千份资料，被举报人受贿的利益链条被厘清，而且不仅查明了罗某涉嫌受贿的犯罪事实，还顺藤摸瓜带出了一条更大的鱼&mdash;&mdash;谭沭兵。</p>\r\n<p style=\"text-indent: 2em\">2010年3月8日，在前期调查的基础上，建邺区检察院以涉嫌受贿罪对谭沭兵立案侦查。谭沭兵到案后，很快交代了其在担任南京市给排水工程公司经理期间受贿十余万元的问题，但对其他犯罪事实避而不谈。当办案人员将他受贿的所有银行资料放在面前时，谭沭兵长叹一声，打消了抵抗的念头。</p>\r\n<p style=\"text-indent: 2em\">经办案人员查实，2003年至2010年，谭沭兵在担任市排水管理处规划建设科科长、南京市给排水工程公司经理及南京市排水管理处副处长的几年时间里，在主管仙林污水处理厂建设工程、锁金村地区雨污分流改造等多项排水工程期间，利用职务上的便利，为有关单位和个人在工程承接、工程建设、工程款支付结算等事项上提供帮助，收受贿赂130余万元。</p>\r\n<p style=\"text-indent: 2em\"><strong>工程项目成了他的&ldquo;摇钱树&rdquo;</strong></p>\r\n<p style=\"text-indent: 2em\">谭沭兵第一次受贿是2003年，当时他任南京排水管理处规划科科长，介绍刘某承接了一处管道工程。为了表示感谢，刘某给了他15000元现金。此后，只要手里有工程，谭沭兵都能收到工程承包商送给他的好处费。</p>\r\n<p style=\"text-indent: 2em\">2004年，谭沭兵又安排刘某做了某道路管道改造工程及城东快内环部分管道工程。这一次，刘某出手很大方，2005年7月在谭沭兵的办公室，刘某向他奉上10万元现金。</p>\r\n<p style=\"text-indent: 2em\">2008年，仙林污水处理厂某工程及南京城北某处雨污分流改造项目出水压力管道工程招投标，经谭沭兵打招呼，承包商王某以南京建设集团公司拿到了该工程。王某为了感谢谭沭兵的&ldquo;帮忙&rdquo;，2009年1月在玄武湖太阳宫附近路边，在谭沭兵的车上送给他3万元现金；一年后，在同一个地点，谭沭兵在王某的车上又收受了王某送的5万元现金。</p>\r\n<p style=\"text-indent: 2em\">2009年，南京城北某村雨污分流项目招标期间，作为项目的负责人，谭沭兵私下向自己熟悉的某路桥公司和某市政公司透露，要他们在招投标时加入一个有利于投标方的&ldquo;倾向性&rdquo;条款，两家公司按照谭沭兵的要求，把这个条款写入了正式格式文本中。最后两家公司都中标了，为了表示感谢，路桥公司先后送给谭沭兵25000元。</p>\r\n<p style=\"text-indent: 2em\"><strong>受贿几年都没有被发现，谭沭兵的胃口大了起来。</strong></p>\r\n<p style=\"text-indent: 2em\">2007年，仙林污水处理厂某项工程招投标，当时谭沭兵是仙林污水处理厂建设指挥部指挥长。在他的关照下，南京某建设集团中标，后来该项工程施工负责人季某送给谭沭兵2万元现金。2008年，该项工程开工后的一天，谭沭兵打电话给季某，约季某到瑞金路一家咖啡馆见面。两人见面后，谭沭兵要季某从工程款中提3到5个点回扣给他。季某哪敢不答应。2009年1月的一天，在奥体中心附近的路边，季某给了谭沭兵20万元现金和6条&ldquo;金南京&rdquo;香烟。</p>\r\n<p style=\"text-indent: 2em\">谭沭兵不仅直接从自己管理的工程项目中收取好处，还通过要求施工方采购他所指定厂商的设备来获取利益。</p>\r\n<p style=\"text-indent: 2em\">2009年，江心洲污水处理厂某设备招投标，制作招投标文件时，谭沭兵在招投标文件中就指定，必须使用某公司的电气设备，中标方只能按标书中规定的设备。为了表示感谢，电气设备公司谢某送给谭沭兵3万元现金。</p>\r\n<p style=\"text-indent: 2em\">就这样，几年时间里，谭沭兵一共收受了130万元好处费。</p>\r\n<p style=\"text-indent: 2em\"><strong>传言当处长受贿更疯狂</strong></p>\r\n<p style=\"text-indent: 2em\">大学毕业就投身于市政建设领域的谭沭兵，业务经验丰富，组织管理能力突出，为人也低调谦和，再加上年龄优势，一直是组织上重点培养的年轻干部。案发前几年，正逢老处长即将退休之时，虽然当时处长人选还没有确定，但谭沭兵的接班人身份被许多人所看好。与排水管理处有业务往来的工程队老板们，也&ldquo;嗅&rdquo;出了这一点，往谭沭兵办公室跑得更勤快了，每次去少不了带上一份&ldquo;礼品&rdquo;，谭沭兵也没有推辞的意思，一概照单全收。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;谭沭兵受贿的130万元里，有80余万元是在案发前两年，即传言其要当接班人的期间收取的。如果说早期谭沭兵是逐步走向堕落的话，那么案发前两年的他，就是自由落体式的坠向深渊。&rdquo;办案人员说。</p>\r\n<p style=\"text-indent: 2em\">对于自己疯狂受贿的原因，谭沭兵交代，他与前妻离异后，对与前妻所生的儿子深感愧疚，于是便把平时收受的贿赂存起来，准备供儿子出国留学使用。为了避人耳目，谭沭兵使用别人的身份证开设银行账户，自以为检察机关无法发现，便决定只承认十几万元，对银行账户的事情硬挺到底，没想到最终还是没能蒙混过关。</p>\r\n<p style=\"text-indent: 2em\">2011年3月8日，南京市建邺区法院对谭沭兵作出一审判决：被告人谭沭兵犯受贿罪，判处有期徒刑10年6个月，没收个人财产60万元。谭沭兵服判未上诉。</p>\r\n</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (250,'<div class=\"Line\">&nbsp;</div>\r\n<div style=\"position: relative\" bosszone=\"content\" id=\"Cnt-Main-Article-QQ\">\r\n<p style=\"text-align: center\">&nbsp;</p>\r\n<div style=\"width: 450px\" r=\"1\" class=\"mbArticleSharePic           \"><img src=\"http://img1.gtimg.com/news/pics/hv1/13/148/931/60576028.jpg\" alt=\"河南一红会医院疑篡改病历掩盖患者死亡真相\" /></div>\r\n<p align=\"center\" class=\"pictext\"><span style=\"font-size: 12px\">樊连花出示两份病历，内容有多处不同。</span></p>\r\n<p style=\"text-indent: 2em\"><strong>病历为啥有俩版本，这个真不能说？</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>病人家属与医院提交法院的病历内容有多处不同</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>记者采访，医院院长反问&ldquo;你认为我会给你说这些吗&rdquo;？挂掉电话</strong></p>\r\n<p style=\"text-indent: 2em\">新郑市新村镇村民邓永杰2009年3月20日患&ldquo;溃疡性结肠炎&rdquo;入住新郑红十字会肛肠医院治疗，后病情发展，转院治疗，不久病死。邓永杰妻子樊连花认为该医院在给邓永杰治疗时有过错，将其起诉到法院。&ldquo;没想到该医院向卫生局和法院出具了两份不同的病历！&rdquo;樊连花说。记者核实发现，两份病历医师签名和用药都有不同之处。</p>\r\n<p style=\"text-indent: 2em\"><strong>A</strong> <strong>&ldquo;阴阳病历&rdquo;同时现身法院</strong></p>\r\n<p style=\"text-indent: 2em\">12月16日上午，在郑州市金水区人民法院门前，新郑市新村镇邓家村村民樊连花向记者讲述了事情经过。当天，该院对樊连花诉新郑红十字会肛肠医院等3家医院承担医疗事故责任一案进行开庭审理。</p>\r\n<p style=\"text-indent: 2em\">樊连花说，2009年3月20日，她丈夫邓永杰因肚子疼到新郑红十字会肛肠医院进行治疗，被诊断为&ldquo;溃疡性结肠炎&rdquo;，遂入院治疗。一周后，丈夫因病情加重，先后转到新郑市人民医院和郑州人民医院治疗，27天后，不治身亡。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;我认为造成丈夫死亡的主要原因同新郑红十字会肛肠医院在最关键的初期阶段治疗不当有关。&rdquo;樊连花说。去年10月，她向金水区人民法院提起诉讼，将3家医院列为被告。</p>\r\n<p style=\"text-indent: 2em\">今年3月，她向新郑红十字会肛肠医院索要丈夫的病历被拒绝，只好向新郑市卫生局求助，该局从档案室拿出病历复印件给她复印了一份，并加盖了卫生局的公章。9月份，她向金水区法院提交病历时，才知道新郑红十字会肛肠医院已经向法院提交了一份病历，但两份病历的内容有很多不同之处。</p>\r\n<p style=\"text-indent: 2em\"><strong>B</strong> <strong>两病历医师和用药都有不同之处</strong></p>\r\n<p style=\"text-indent: 2em\">记者从主审法官处看到了两份不同的病历，经仔细核对，发现两份病历用药和医师签名都有不一样的地方，如在卫生局提供的病历上显示，2009年3月20日入院当天，医生给邓永杰用的药里边有个叫&ldquo;肠炎2号&rdquo;的药品，但在新郑红十字会肛肠医院向法院提交的病历中却没有用这个药。新郑红十字会肛肠医院向法院提交的病历中显示，3月24日，该院给邓永杰服用过一种药胃康胶囊，而在卫生局提供的病历上却没有看到服用这种药的记录。</p>\r\n<p style=\"text-indent: 2em\">在卫生局提供的入院病历上显示，邓永杰的主治医师是&ldquo;安巧花&rdquo;，而新郑红十字会肛肠医院向法院提交的入院病历上显示，邓永杰的主治医师为&ldquo;吴瑞芬&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">为什么邓永杰的住院病历会有两份，而且内容不同？昨天下午，记者联系了新郑红十字会肛肠医院院长安书卷，记者说明采访意图后，他反问道：&ldquo;你认为我会给你说这些吗？&rdquo;然后不等记者答话，就挂断了电话。随后记者多次拨打他的手机，他都没接听。</p>\r\n<p style=\"text-indent: 2em\">郑州市卫生局医政处的杨先生说，同一份病历上有两个不同医师单独签名，那是不允许的，出现这种情况就说明医院有篡改病历的嫌疑。</p>\r\n<p style=\"text-indent: 2em\">法庭没有对此案当庭宣判，将择日宣判。</p>\r\n</div>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (251,'<p>本报讯 (记者潘秀林)昨日，星河湾集团宣布旗下上海星河湾和浦东星河湾两个项目下调售价，将以8-8.5折或更大的优惠进行降价，同时表示将拿出超6亿元对前期老业主进行价差补偿。</p>\r\n<p>　　尽管遭遇严厉调控，但上海星河湾和浦东星河湾并非陷入成交泥塘。据悉，浦东星河湾二期今年8月开盘，均价约7.8万元/平方米，目前销售约80套；上海星河湾今年也出货100多套，均价在5.2万元/平方米左右。由于户型最小面积都在200平方米以上，两个项目均是千万元起步，一年内录得180多套的销售，在上海豪宅市场中尚处于佼佼者。</p>\r\n<p>　　对于此次降价行为，星河湾集团董事长黄文仔在新闻通稿中表示，主动下调价格是为了响应中央号召配合调控要求，让房价回归理性，而主动对老业主进行补差价，是为了回馈老业主。&ldquo;目前整个房地产行业已经遭遇寒冬，这是一个存在的事实。不降价，卖不出去；降价了，对忠诚的老业主是一种打击。每个企业对此的应对方式不一样，在星河湾看来，更多是考虑业主的利益，其他的对我们而言，都不重要。&rdquo;</p>\r\n<p>　　根据降价补偿方案，老业主必须在明年1月31日前，完成按揭办理手续；一次性付清和分期付款的，也必须满足相关的约定。</p>\r\n<p>　　星河湾还表示，此次降价仅涉及上海的两个楼盘，其他城市楼盘不会跟进。</p>\r\n<p>　　&ldquo;此前上海已经发生过多起类似的降价行为，引发老业主围攻销售中心，因此这次上海星河湾做出这么一个决定，也不是没有根据的&rdquo;，满堂红研究部高级主任肖文晓表示，此次星河湾降价幅度比较大，主要还是出于维护品牌形象的考虑，另一方面也是希望年底能够更多出货。</p>\r\n<p>　　<strong>降价潮才刚刚开始</strong></p>\r\n<p>　　<strong>&gt;&gt;上海现状</strong></p>\r\n<p>　　上周上海新建商品住宅仅成交1910套，环比下跌40.5%。另据监测显示，上周新增供应面积9.8万平方米，环比减少34.3%，商品住宅新增供应已连续四周下滑。</p>\r\n<p>　　除了星河湾，中海、保利、万科、富力等7家房企均已在上海进行大规模降价销售。今年10月，十大全国性房企在售项目的销售价格环比下跌3%，同比下跌2.1%。上海房价降价潮正在由城市外围向市中心蔓延，目前来看，上海楼市降价才刚开始。</p>\r\n<p>　　<strong>开发商个案难复制</strong></p>\r\n<p>　　<strong>&gt;&gt;北京影响</strong></p>\r\n<p>　　今年9月前后，北京也发生多起楼盘降价引发老业主维权事件。9月中旬，位于通州的京贸国际城<!--keyword-->(楼盘资料 业主论坛) <!--/keyword-->(楼盘资料业主论坛)由最高2.6万元/平方米降至均价1.4-1.5万元/平方米，致上百名老业主要求退房或补差价。</p>\r\n<p>　　&ldquo;目前来看，很少有降价开发商马上做出返还现金的补偿模式，大多数还是会以物业费或车位费等形式进行抵扣&rdquo;，亚豪机构市场总监郭毅对本报记者表示，每家房企都会按照自身情况进行降价后的处理，因此预计星河湾这种处理方式不会很快在全国范围内被效仿，仅是个案。不过，在郭毅看来，这种补偿方式并不算是很好的示范作用。</p>\r\n<p>　　&ldquo;购房人应该具有承担市场正常波动的能力，开发商不能因为害怕影响品牌就破坏市场规律、违背契约精神&rdquo;。</p>\r\n<p>　　随着年底施工款、贷款等资金回款压力增大，郭毅预计北京将会出现规模更大的降价潮。</p>\r\n<p>　　(京华时报)</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (252,'<p style=\"text-indent: 2em\">据中国之声《全国新闻联播》报道，北京表示，2012限购令不会松绑，保障房将&ldquo;以租为主&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">北京市住建委委员王荣武表示，经过一年的房地产市场调控，北京楼市价格终于实现&ldquo;稳中有降&rdquo;的目标，各项指标变化符合调控预期。</p>\r\n<p style=\"text-indent: 2em\">王荣武：目前的购房比例，刚性自住型的占到90%，我们认为调控还处在一个关键的时期，基数还不是很稳固，限购是不放松不动摇的。</p>\r\n<p style=\"text-indent: 2em\">不过王荣武却没有回避楼市降温给政府财政收入带来的负面影响。</p>\r\n<p style=\"text-indent: 2em\">王荣武：减了营业税，减了契税，有10%左右的影响。</p>\r\n<p style=\"text-indent: 2em\">北京市住建委新闻发言人秦海翔同时表示，北京市目前正在建设廉租房、经济适用房、限价商品房和公共租赁房的住房综合管理制度。</p>\r\n<p style=\"text-indent: 2em\">秦海翔：目前全市已落实公共租赁住房项目98个，房源10.3万套，加快向以租为主转变。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (253,'<p style=\"text-indent: 2em\">据中国之声《央广新闻》报道，在楼市调控严厉的大背景下，星河湾集团近日宣布旗下上海两个项目下调售价&mdash;&mdash;8-8.5折或更大的优惠，同时表示将拿出超6亿元对前期老业主进行价差补偿。</p>\r\n<p style=\"text-indent: 2em\">星河湾集团董事长黄文仔称，此次降价仅涉及上海的两个楼盘，其他城市楼盘不会跟进。对于降价是否处于资金压力，星河湾方面予以否认。</p>\r\n<p style=\"text-indent: 2em\">这种行为引来各方争议。亚豪机构市场总监郭毅表示，这种补偿方式并不算是很好的示范作用，&ldquo;购房人应该具有承担市场正常波动的能力，开发商不能因为害怕影响品牌就破坏市场规律、违背契约精神&rdquo;。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (254,'<p style=\"text-indent: 2em\"><b>实拍车型：中华V5 1.6</b><b>自动豪华型 </b><b>售价 12.98</b><b>万</b></p>\r\n<p style=\"text-indent: 2em\">中华紧凑型SUV V5已经在之前的广州车展<!--keyword-->(<span class=\"infoMblog\"><a class=\"a-tips-Article-QQ\" href=\"http://t.qq.com/autoshow-gz#pref=qqcom.keyword\" rel=\"autoshow-gz\" target=\"_blank\" reltitle=\"中国（广州）国际汽车展览会\">微博</a></span>)<!--/keyword-->上市了，新车售价10.98万-16.58万元。目前该车已经正式到店，接下来就让我们来一起感受一下来自<!--keyword--><a class=\"a-tips-Article-QQ\" title=\"华晨中华\" href=\"http://data.auto.qq.com/car_brand/114/\" target=\"_blank\"><!--/keyword-->华晨中华<!--keyword--></a><!--/keyword-->的V5。</p>\r\n<p style=\"text-indent: 2em\"><b>外观很像<a class=\"a-tips-Article-QQ\" href=\"http://data.auto.qq.com/car_serial/715/index.shtml\" target=\"_blank\">宝马X1</a></b></p>\r\n<p style=\"text-indent: 2em\">外观上，乍眼一看中华V5很像宝马X1。当然进气格栅的设计依然是典型的中华式造型，前不久在中华H530的车身上我们已经看到了相同的设计。</p>\r\n<p align=\"center\">&nbsp;</p>\r\n<div class=\"mbArticleSharePic         \" r=\"1\" style=\"width: 552px\">\r\n<div class=\"mbArticleShareBtn\"><span>转播到腾讯微博</span></div>\r\n<a href=\"http://data.auto.qq.com/car_public/1/disp_pic_nl.shtml?sid=909&amp;tid=4&amp;pid=689375\" target=\"blank\" alt=\"中华V5 1.6自动豪华型 重点图解\"><img alt=\"中华V5 1.6自动豪华型 重点图解\" src=\"http://mat1.gtimg.com/datalib_img/11-12-16/7/7a6764a3ff2f28b85bb1b67f55258ede6.jpg\" style=\"border-bottom: #000000 1px solid; border-left: #000000 1px solid; border-top: #000000 1px solid; border-right: #000000 1px solid\" /></a></div>\r\n<p>&nbsp;</p>\r\n<p style=\"text-align: center\"><span style=\"font-size: 12px\">中华V5实拍</span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (255,'<p style=\"text-indent: 2em\">本周日就是圣诞节，在北京、上海等一线城市的许多外资公司里都有着圣诞节交换礼物的习惯，笔者就在上周五准备了一份礼物与同事交换。一般来说，同事、朋友之间的圣诞礼物多半是一些价值不高的小礼品，但是对于情人、亲人或者有业务往来的伙伴来说，圣诞节送的礼物档次可以提高一些。</p>\r\n<p style=\"text-indent: 2em\">年底的时候送一些电子产品也是正常现象，有一项调查显示：平板电脑高居美国人圣诞节最<span class=\"infoDataBank\">渴望 </span>得到的礼物第二名，仅次于服装。既然平板电脑可以当成圣诞礼物，笔记本电脑又有什么不可以呢？让我们来看看近期上市的新品笔记本中，哪款适合作为圣诞礼物吧</p>\r\n<div class=\"dataSourceCardInfo\" style=\"display: none\">\r\n<div class=\"arrowBox\">\r\n<div calss=\"arrow\">&nbsp;</div>\r\n</div>\r\n<div class=\"dataloading\">&nbsp;</div>\r\n<div class=\"dataCardUserDetail\">&nbsp;</div>\r\n</div>\r\n<p>&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (256,'<p style=\"text-indent: 2em\">2011年即将结束，每年的这个时候都是购机的旺季。对于用户而言，购买手机最关心的就是价格问题，降到心理价位就要入手？NO！NO！NO！从手机市场来看，价格便宜但仍需稳定，如果今天手机报价3500元，明天就跌至3000元，这样的情况谁也不愿碰到。相反对于官方定价的手机，比如<span class=\"infoDataBank\">小米手机 </span>，1999元的售价未来很长一段时间都不会再降，因为再降价就没有利润空间了，这样来看，消费者买回来会比较踏实，不必担心三天两头价格暴降。好了闲话少说，春节即将到来，笔者也为大家盘点几款最保值智能手机，希望朋友们能够喜欢。</p>\r\n<div class=\"dataSourceCardInfo\" style=\"display: none\">\r\n<div class=\"arrowBox\">\r\n<div calss=\"arrow\">&nbsp;</div>\r\n</div>\r\n<div class=\"dataloading\">&nbsp;</div>\r\n<div class=\"dataCardUserDetail\">&nbsp;</div>\r\n</div>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (257,'<p style=\"text-indent: 2em\">蓬莱19-3油田发生溢油事故已逾半年，在渔民们纷纷对<span>康菲石油</span>公司提起索赔诉讼之时，康菲石油公司16日却表示，基本没有证据显示溢油事故对环境造成影响。</p>\r\n<p style=\"text-indent: 2em\">上述表态是康菲石油公司在接受《华尔街日报》采访时表示的。昨日，《第一财经<!--keyword-->（<span class=\"infoMblog\">微博</span>)<!--/keyword-->日报》在康菲石油公司相关负责人处得到确认，该负责人表示，康菲石油公司确实在与《华尔街日报》等国际媒体交流时发表了上述言论。</p>\r\n<p style=\"text-indent: 2em\">溢油事件发生后，康菲公司分别于7月和8月召开新闻发布会，随后，康菲公司开展了多次与各利益方的小规模交流会。<span>环保</span>组织公众环境研究中心主任马军就受邀与康菲公司负责人在12月8日进行了交流。</p>\r\n<p style=\"text-indent: 2em\">对于康菲对国际媒体表示溢油没有影响环境的行为，马军质疑称：&ldquo;康菲公司与我们会面时还表示，要对受到漏油损害的利益方做出赔偿，并为改善渤海环境作贡献，如今怎么做出前后不一致的表述呢？今年6月发生的两次溢油事故对渤海水质造成了污染是无需辩驳的事实。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">马军对记者表示，渤海是半封闭的内海，平均水深仅为20多米，溢油对渤海环境的影响是比较严重的危害。而且，溢油造成的油基泥浆至今还未完全清理干净，对环境的影响还将长期存在。他说，康菲公司对公众隐瞒不报在先，屡屡误导陈述在后，同时一再试图回避承担其造成的<span>生态</span>损害和养殖等经济利益损失，其公信力已经丧失。</p>\r\n<p style=\"text-indent: 2em\">其实，不难看出康菲公司16日的言论与其此前的公开表态存在较大矛盾。9月6日，康菲表示将设立渤海湾<!--keyword--><!--/keyword-->基金<!--keyword--><!--/keyword-->，将根据中国相关法律承担公司应尽的责任并有益于渤海湾的整体环境。康菲石油公司董事长兼首席执行官穆怀礼当时表示：&ldquo;康菲石油公司对于该事件的发生深表歉意，以及因此对中国的人民和环境产生的影响表示道歉。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">作为河北省乐亭县107名渔民向康菲石油公司提起4.9亿元经济赔偿的代理律师，赵京慰对康菲公司16日的言论评价说：&ldquo;康菲公司的自我辩解没有实质意义，法院的裁决不会以康菲的意志为转移，而是会以权威机构作出的鉴定为准。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">&ldquo;康菲公司一直没有对该基金的具体运作做出解释说明。如今，康菲前后表述自相矛盾，再次证明他们对赔偿并没有诚意，也体现了其不负责任的态度。&rdquo;赵京慰说。</p>\r\n<p style=\"text-indent: 2em\">此前，赵京慰已分别向国家<span>海洋</span>局和农业部申请了政府信息公开。国家海洋局政府信息公开答复书显示，蓬莱19-3油田溢出的油污已经在河北省乐亭县养殖区周边登陆。农业部办公厅的答复函也排除了由已知细菌、寄生虫和病毒等病原微<span>生物</span>所导致的水产品大量死亡，确认了赤潮、石油污染等原因可能导致了水产品的滞长和死亡。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (258,'<p style=\"text-indent: 2em\">随着在华项目越来越多，包括<span class=\"infoMblog\">大众汽车</span>在内的不少跨国汽车巨头陆续遇到高管人才储备断档的瓶颈。这在竞争日益激烈的中国车市，能否顺利解决这一难题，将成为影响企业在华业绩的重要因素。</p>\r\n<p style=\"text-indent: 2em\"><strong>&ldquo;中国60&rdquo;计划</strong></p>\r\n<p style=\"text-indent: 2em\">Tom（化名）是德国大众汽车总部两年前派到一汽-<!--keyword--><!--/keyword-->大众<!--keyword--><!--/keyword-->的一位主管整车技术的高管，明年年中，工作签证到期的他将回到&ldquo;狼堡&rdquo;（沃尔夫斯堡，大众汽车集团总部所在地），而他所负责的一汽-大众佛山分公司的项目则会交给一位中方经理接手。</p>\r\n<p style=\"text-indent: 2em\">作为一家深耕中国车市二十余年的汽车巨头，很长一段时间以来，大众汽车向中国合资公司派驻了大量Tom这类的外籍专家，但是随着在华启动的新项目越来越多，甚至在其他快速增长的国家同样需要外派专家。&ldquo;外脑&rdquo;数量有限的大众汽车打算改变策略。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;大众汽车集团是一个全球性的公司，不只在中国，在印度、俄罗斯和其他任何地方都有自己的分公司，随着大众汽车的壮大，德国总部一时很难派出大量外籍专家。&rdquo;大众汽车集团（中国）董事会成员、主管人力资源的狄凯思向《第一财经日报》记者坦言。</p>\r\n<p style=\"text-indent: 2em\">时值大众汽车在中国发展的发力期，今年大众汽车在中国产销量将突破200万辆，在华两个合资公司分别上马了多个新工厂，一汽-大众成都工厂刚刚投产，正在施工的包括一汽-大众佛山工厂、<!--keyword--><!--/keyword-->上海大众<!--keyword--><!--/keyword-->仪征工厂，另外，上海大众新疆工厂也已获批，一大批管理人才缺口显现。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;之前这些职位大部分是由外籍专家来担任的，现在我们希望把这些职位本土化。&rdquo;大众汽车外籍员工协调规划总监田海诺告诉本报记者。</p>\r\n<p style=\"text-indent: 2em\">大众汽车将陆续撤回一批外派专家，与此同时，一个由田海诺负责的&ldquo;中国60&rdquo;项目悄然启动。</p>\r\n<p style=\"text-indent: 2em\">到明年年中，大众汽车计划将在中国本土招聘60位高端人才，岗位是管理级别和高级管理级别。不同于以往低端岗位的人才招聘，这次职位之高史无前例，甚至有些仅低于董事会成员。</p>\r\n<p style=\"text-indent: 2em\">撤回的外派专家几乎涉及大众汽车中国公司里的所有领域，从技术到销售再到人力资源，尤其是大众汽车在中国新建的几家新工厂，高级经理级人才有大量空缺。</p>\r\n<p style=\"text-indent: 2em\">这60位通过网络、猎头等方式招聘来的人员将进行18个月的培训，这期间，大众汽车将把他们派到德国总部培训6~12个月，跟相应职位的人进行交流、沟通。培训结束后，他们会重新回到中国。目前，已经招到11位管理人才。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;从费用的角度来说，这不是一个省钱的做法，因为要花费时间和金钱来培训这些人更好地了解大众汽车集团的理念。&rdquo;田海诺告诉记者。</p>\r\n<p style=\"text-indent: 2em\">但显然，大众汽车更加看重本土化高管的优势，&ldquo;如果是外籍专家，根据政策他们在工作一定时期后进行岗位轮换。如果我们选择愿意在中国发展的人，他们会有更好的工作心态，我们也可以保证在合资厂的工作得以高质量地完成。&rdquo;</p>\r\n<p style=\"text-indent: 2em\"><strong>本土人才优势凸显</strong></p>\r\n<p style=\"text-indent: 2em\">不少在华投资的跨国车企与大众汽车有着相似的经历。一直被本土化做得不好而受诟病、采取外派高管方式管理中国市场的<!--keyword--><!--/keyword-->标致<!--keyword--><!--/keyword-->雪铁龙集团，为了更贴近中国市场，从今年起加大了本土化高管人才的&ldquo;替换&rdquo;。人力资源总监、公共事务及企业传讯总监，以及合资公司<!--keyword--><!--/keyword-->东风雪铁龙<!--keyword-->(微博)<!--/keyword-->的市场部总监、<!--keyword--><!--/keyword-->东风标致<!--keyword--><!--/keyword-->市场部总监等职务，均由本土化人才担任。</p>\r\n<p style=\"text-indent: 2em\">有一些企业则采取折中的方式，包括上海大众、<!--keyword--><!--/keyword-->斯柯达<!--keyword--><!--/keyword-->等企业，设立中外双总监制，相互制约的同时，更大程度上做到互为补充。</p>\r\n<p style=\"text-indent: 2em\">其实，除了汽车行业，移动通讯、PC、软件、连锁百货甚至飞机制造业，都已实行&ldquo;本土高层管理本土企业&rdquo;的人才策略。</p>\r\n<p style=\"text-indent: 2em\">很多外企公关、法律、市场和业务等部门的经理都由本土人员担任。&ldquo;他们更熟悉本土的情况，更有利于企业适应本土文化。&rdquo;北京外企人力资源服务有限公司总经理王一谔分析认为。</p>\r\n<p style=\"text-indent: 2em\">资深汽车分析师钟师认为，人才本地化有诸多好处，其一是可以节省成本，让老外赴华工作要承担很大的开销，也会减损中国雇员的积极性；其二是销售、市场等业务&ldquo;是很文化的东西，需要接地气，和中国人打交道处关系，外国人并不擅长&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">外资进入中国这么多年来一直注重员工的培训和各方面技能的提高，这也为人才本土化创造了条件。汽车业内人士认为，合资的模式走了二十多年，积累了一大批优秀的管理人才，本土化的高管战略足以在一定程度上加快跨国公司在中国的业务发展。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (259,'<p style=\"text-indent: 2em\"><strong>腾讯科技讯</strong>（乐天）12月18日消息，小米手机今日正式开放销售，不过，在宣布开放销售仅仅3个小时后，小米官方腾讯微博发布公告，称今天凌晨开放购买3小时，12月在线销售10万库存已售罄。小米还表示，下一轮开放购买，请关注小米论坛和官方微博的通知。</p>\r\n<p style=\"text-indent: 2em\">据了解，小米手机今日凌晨宣布所有个人用户可直接购买，还开通了企业客户采购通道，并提出了年会送员工，年终答谢送客户小米手机的口号，这也意味着小米手机正式加入了智能手机市场的争夺，1999元的价格也必将搅动智能手机市场。</p>\r\n<p style=\"text-indent: 2em\">针对个人用户，小米手机有一定限制，即每人仅限购两台。企业客户能一次性采购多部小米手机，但必须是企事业团体单位，能提供有效的营业执照等资质证明，并且一次采购10部小米手机，并且填写企业客户采购申请表给小米，小米公司资质审核通过，并且企事业团体单位付款后才能收货。</p>\r\n<p style=\"text-indent: 2em\">宣布小米手机开放购买的同时，针对市场上小米手机配件不足的情况，小米还推出包括耳机、后盖、保护壳、保护套、贴膜、贴纸、挂饰、支架、存储卡等原装配件，可供用户购买。</p>\r\n<p style=\"text-indent: 2em\">据官方介绍，用户需要到小米网生成订单后可获得小米手机，可以选择支付宝、财付通、网银等支付方式，也可以选择货到付款，支持现金支付，但不能直接去小米之家购买商品，必须在小米之家和用户确认后才能去指定的小米之家提货。</p>\r\n<p style=\"text-indent: 2em\">据小米科技副总裁黎万强<!--keyword-->(<span class=\"infoMblog\">微博</span>)<!--/keyword-->透露：&ldquo;凌晨开始，第一小时下单破50000。纪念历史的一刻。应大家要求。发张办公室图片。我们要专心把服务改善得更好。谢谢米粉的支持和厚爱。&rdquo;今天凌晨开放购买3小时，12月在线销售10万库存已售罄。</p>\r\n<p style=\"text-indent: 2em\">对此，众多&ldquo;米粉&rdquo;很愤怒，也很失望，有观点指出，小米手机获得了200万部联通定制，一切经营要给定制机让路，也有观点认为，小米是在学习苹果、魅族搞饥饿营销。不过，小米官方并未就此表态。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (295,'<p>&nbsp;LG P970也拥有着直板触控造型设计，简洁的外形方面有着不错的时尚感表现。在其正面为一块4.0英寸大小的NOVA电容<font style=\"color: #333\">触摸屏</font>，在WVGA级别分辨率的支持下，整体显示效果清晰透亮。P970搭载了Android 2.2的智能<font style=\"color: #333\">操作系统</font>，并且配置全面主流，操控性方面也有着不错的表现。而该机背后的一枚<font style=\"color: #333\">500万像素</font>摄像头，也可以带来720P视频录制功能，拍摄能力一样让人满意。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (298,'<p style=\"text-align: center\"><img alt=\"\" width=\"646\" height=\"408\" src=\"/u/cms/www/201112/1910215201yl.jpg\" /></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (315,'<p>顶尖数据恢复软件功能十分强大，恢复成功率极高，使用本软件，可以很轻松地恢复您电脑硬盘或U盘、tf卡、SD卡、索尼记忆棒等存储设备上的数据。</p>\r\n<p>　　无论是因为误删除，还是格式化，甚至是硬盘分区丢失导致的文件丢失，顶尖硬盘数据恢复软件都可以很轻松地为您恢复。</p>\r\n<p>　　顶尖数据恢复软件操作十分简单，您只需要按软件提示一步一步操作，就能恢复出你电脑上的宝贵数据，即使你是个电脑新手，也能很快地上手本软件。</p>\r\n<p>　　同时，顶尖硬盘数据恢复软件还配有详细的使用帮助、视频语音教程，你也可以按相关帮助资料来操作本软件。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (316,'<p><font size=\"4\">昨天晚饭后和张晖骑着雪地摩托车到冰山深处去了一趟，碰到了一只帝企鹅和一群十九只阿德雷企鹅。本来是想去弄点绿色冰回来的，结果也没找到具体地方。正好昨天的风还很大，有8到9级大风。冰面上很难走，雪地摩托都很难走的地方都有。有的必经之路裂开几道很宽的冰缝，让你无法穿越，即便是勉强过去，也是提心吊胆的加速冲过去。后面的路我们两个只能是走过去，大概走了有一公里才到了那座特殊的绿冰冰山。到跟前一看跟往前完全不一样了，也不只是找错地方了还是变了样子，已经不是绿色的和深蓝色的冰山了。最后干脆就放弃了取冰的计划，然后沿原路打道回站了。除了看到海豹、企鹅以外，还看到了雪燕，成群的雪燕。大风可以刮得你走不动，扬起的雪粒打在脸上也够疼的。看冰面上阳光照耀下，杨雪飞舞，很有点仙境的味道，也显露出白色沙漠的无情。今天大风仍然在继续，中铁的队员也因为风太大停止了宿舍的钢结构组装。雪龙船也早已驶过了冰区，正向着长城站驶去。昆仑站队的勇士们也是冒着大风天气，向着南极更深处进发。其他科考项目也在正常进行中</font></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (317,'<p>这次近距离的拍摄，让我荣幸地结识了美丽的母其弥雅及她的团队，她的敬业与谦逊让人印象深刻！专业的瑜伽动作与仙境般的雨林相融成的画面让人久久挥之不去！ 母其弥雅个人介绍：母其弥雅，被称为&ldquo;亚洲最美瑜伽教练&rdquo;，&ldquo;瑜伽第一美女&rdquo;。1987年5月24日出生于云南楚雄，母亲是彝族，父亲是汉族。母其弥雅容貌清丽、阳光、健康，典雅的东方气质中透着时尚的现代气息。17岁起修习瑜伽，多年的瑜伽修习使她拥有完美的黄金比例身材。无论是台上台下，她都是人们目光的焦点。母其弥雅&ldquo;文武双全&rdquo;，不仅通晓多种舞蹈，也是跆拳道高手；凭借出众的五官和完美的身材，成为广告界炙手可热的宠儿；她是专业的瑜珈指导师，结合多个瑜伽流派自创了&ldquo;弥雅瑜伽&rdquo;，目前担任 &ldquo;中印瑜伽峰会&rdquo;的首位亲善大使。 拍摄地:望天树景区（国家四A级旅游景区）位于云南省西双版纳州勐腊县境内，距景洪市134km,距国家一级口岸磨憨60km。是地球上北纬21度唯一的一片绿洲，是热带雨林国家公园的核心景区。您可以挑战&ldquo;世界第一高，中国第一条树冠走廊&rdquo;惊险刺激；和雨林巨人--望天树（ 热带雨林的标志性树种）进行天人合一对话；观赏热带雨林&ldquo;五大奇观&rdquo;--板根、绞杀、寄生附生、滴水叶尖、老茎怀春；体验被誉为&ldquo;东方亚马逊&rdquo;的南腊河热带雨林河流奇观。领略到最原汁原味的傣族、哈尼族（爱尼人、补过人、排角人）、布朗族（克木人、卡米人）、瑶族、彝族的民俗风情和原生态自然村落。 是中国唯一家从水、陆、空全方位体验热带雨林之魂</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (318,'<p>WTCC（World Touring Car Championship）即世界房车锦标赛，是FIA国际汽联于2005年新推出的一项全球性汽车赛事。赛场上的宝贝也是一道靓丽的风景线，各个体态婀娜，秀色可餐。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (319,'<p><font size=\"4\">很多人说我们80后身处在最具压力的社会环境中，刚毕业发现工作竞争异常激烈，又赶上楼价高企，物价飞涨，还得肩负照顾父母、组织家庭等重担。面对未来，许多同龄人时有抑郁和失落... </font></p>\r\n<p><font size=\"4\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 但我们细想一下，其实每时代、每个年龄层都经历过上述风浪，在此前提下，消极和悲观留在身上又有何意义呢？作为当下社会的主流，我们需要做的仅仅是自强--能力的提升，内心的强大。 </font></p>\r\n<p><font size=\"4\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拍摄这次静态电影的主要目的，是想分享自己作为80末的心路历程，同时鼓励大家一起勇敢前行，放飞梦想。 </font></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (320,'<p>360杀毒是完全免费的杀毒软件，它创新性地整合了五大领先防杀引擎，包括国际知名的 BitDefender 病毒查杀引擎、小红伞病毒查杀引擎、  360云查杀引擎、360主动防御引擎、360QVM人工智能引擎。五个引擎智能调度，为您提供全时全面的病毒防护，不但查杀能力出色，而且能第一时间防 御新出现的病毒木马。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (321,'<p><font size=\"3\">小龙人、霹雳贝贝、红孩儿......一个个耳熟能详的名字，一个个陪伴我们度过美好童年的小明星，时过境迁，如今又是什么模样？本文将带你一起领略昔日同学现在的风采。</font></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (322,'<p><span>美国旧金山圣诞老人大聚会</span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (323,'<pre style=\"word-wrap: break-word; white-space: pre-wrap\">\r\n日前，柳岩一组红黑两款长裙礼服的性感优雅写真曝光，写真中柳岩马尾低垂眼神温柔，纯红纯黑低胸长礼服上身既凸显傲人美胸，更衬托出柳岩的浓浓优雅女人味</pre>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (324,'<p>现代主义族 <br />\r\n是感觉摄影打造的一个 派 影像<br />\r\n或者说 <br />\r\n某种习惯的方式<br />\r\n或被大家认知之后的代号<br />\r\n似乎拒绝平庸<br />\r\n或其他淡俗的类型</p>\r\n<p>现代主义的方式<br />\r\n现代主义族的私人写真<br />\r\n私人加写真<br />\r\n倒是很好的定义了<br />\r\n现代主义族的色彩 形态 情绪<br />\r\n甚至<br />\r\n质地和手感上<br />\r\n都充满了现代主义味道极唯美影像<br />\r\n&nbsp;</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (325,'<p>当地时间2011年12月14日，AC米兰全队圣诞晚宴，卡萨诺携妻儿出席，而安布罗西尼、阿奎拉尼和安东尼尼等人都带着妻子女友出席，帅哥云集，太太团斗艳，抢镜十足</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (327,'<p><span>足球宝贝徐冬冬海边写真 湿身展无限魅力</span></p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (329,'<p>暮光之城</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (330,'<p>神魔大陆</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (331,'<p>万众期待，翘首以盼的QQ2011 正式版现在全员发布啦！13大全新强大功能挑战你的娱乐精神，重量级功能玩法让你乐翻天！速来体验手写、语音输入，多人视频，语音消息留言、好友桌面等酷炫新功能带来的神奇！</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (333,'<p>丰富的音乐资源</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (334,'<h3>新增功能</h3>\r\n<ol>\r\n    <li>个性化智能纠错</li>\r\n    <li>U模式部首拆分输入</li>\r\n    <li>第三方账号登录输入法</li>\r\n</ol>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (335,'<dl>\r\n    <dd>新增网页翻译功能，在翻译框内输入网址点击翻译，即可得到翻译后的该网址页面。</dd>\r\n    <dd>具有多国语言发音功能，日韩法语全部标准朗读。</dd>\r\n    <dd>轻松囊括互联网最新流行的词汇。</dd>\r\n    <dd>中英日韩法五国语言轻松查询。</dd>\r\n    <dd>通过网络查询最新翻译，无限容量词库，翻译永不过时。</dd>\r\n</dl>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (336,'<p>迅雷使用先进的超线程技术基于网格原理，能够将存在于第三方服务器和计算机上的数据文件进行有效整合，通过这种先进的超线程技术，用户能够以更快的速度从 第三方服务器和计算机获取所需的数据文件。这种超线程技术还具有互联网下载负载均衡功能，在不降低用户体验的前提下，迅雷网络可以对服务器资源进行均衡， 有效降低了服务器负载。</p>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (337,'<h2 class=\"leftboxfutitle\">YY4.0是多玩YY语音的全新版本，活动中心盛大起航，汇集YY上最好最优质的频道和活动，提供YY上最有价值的内容，不需再为找好  频道而费尽心机。</h2>',NULL,NULL,NULL);
INSERT INTO `jc_content_txt` VALUES (340,'<p>2121</p>',NULL,NULL,NULL);
/*!40000 ALTER TABLE `jc_content_txt` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_content_type
#

DROP TABLE IF EXISTS `jc_content_type`;
CREATE TABLE `jc_content_type` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(20) NOT NULL COMMENT '名称',
  `img_width` int(11) default NULL COMMENT '图片宽',
  `img_height` int(11) default NULL COMMENT '图片高',
  `has_image` tinyint(1) NOT NULL default '0' COMMENT '是否有图片',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容类型表';

#
# Dumping data for table jc_content_type
#

LOCK TABLES `jc_content_type` WRITE;
/*!40000 ALTER TABLE `jc_content_type` DISABLE KEYS */;
INSERT INTO `jc_content_type` VALUES (1,'普通',100,100,0,0);
INSERT INTO `jc_content_type` VALUES (2,'图文',143,98,1,0);
INSERT INTO `jc_content_type` VALUES (3,'焦点',280,200,1,0);
INSERT INTO `jc_content_type` VALUES (4,'头条',0,0,0,0);
/*!40000 ALTER TABLE `jc_content_type` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_contenttag
#

DROP TABLE IF EXISTS `jc_contenttag`;
CREATE TABLE `jc_contenttag` (
  `content_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  KEY `fk_jc_content_tag` (`tag_id`),
  KEY `fk_jc_tag_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容标签关联表';

#
# Dumping data for table jc_contenttag
#

LOCK TABLES `jc_contenttag` WRITE;
/*!40000 ALTER TABLE `jc_contenttag` DISABLE KEYS */;
INSERT INTO `jc_contenttag` VALUES (112,40,0);
INSERT INTO `jc_contenttag` VALUES (112,41,1);
INSERT INTO `jc_contenttag` VALUES (115,42,0);
INSERT INTO `jc_contenttag` VALUES (115,43,1);
INSERT INTO `jc_contenttag` VALUES (116,44,0);
INSERT INTO `jc_contenttag` VALUES (116,45,1);
INSERT INTO `jc_contenttag` VALUES (116,46,2);
INSERT INTO `jc_contenttag` VALUES (117,47,0);
INSERT INTO `jc_contenttag` VALUES (117,48,1);
INSERT INTO `jc_contenttag` VALUES (117,49,2);
INSERT INTO `jc_contenttag` VALUES (340,53,0);
/*!40000 ALTER TABLE `jc_contenttag` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_friendlink
#

DROP TABLE IF EXISTS `jc_friendlink`;
CREATE TABLE `jc_friendlink` (
  `friendlink_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_id` int(11) NOT NULL,
  `site_name` varchar(150) NOT NULL COMMENT '网站名称',
  `domain` varchar(255) NOT NULL COMMENT '网站地址',
  `logo` varchar(150) default NULL COMMENT '图标',
  `email` varchar(100) default NULL COMMENT '站长邮箱',
  `description` varchar(255) default NULL COMMENT '描述',
  `views` int(11) NOT NULL default '0' COMMENT '点击次数',
  `is_enabled` char(1) NOT NULL default '1' COMMENT '是否显示',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlink_id`),
  KEY `fk_jc_ctg_friendlink` (`friendlinkctg_id`),
  KEY `fk_jc_friendlink_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接';

#
# Dumping data for table jc_friendlink
#

LOCK TABLES `jc_friendlink` WRITE;
/*!40000 ALTER TABLE `jc_friendlink` DISABLE KEYS */;
INSERT INTO `jc_friendlink` VALUES (1,1,1,'JEECMS官网','http://www.jeecms.com',NULL,'jeecms@163.com','JEECMS是JavaEE版网站管理系统（Java Enterprise Edition Content Manage System）的简称。Java凭借其强大、稳定、安全、高效等多方面的优势，一直是企业级应用的首选。',3,'1',1);
INSERT INTO `jc_friendlink` VALUES (2,1,1,'JEEBBS论坛','http://bbs.jeecms.com',NULL,'jeecms@163.com','JEEBBS论坛',2,'1',10);
INSERT INTO `jc_friendlink` VALUES (3,1,2,'京东商城','http://www.360buy.com/','/u/cms/www/201112/1910271036lr.gif','','',1,'1',10);
INSERT INTO `jc_friendlink` VALUES (4,1,2,'当当网','http://www.dangdang.com/','/u/cms/www/201112/191408344rwj.gif','','',0,'1',10);
INSERT INTO `jc_friendlink` VALUES (5,1,2,'亚马逊','http://www.amazon.cn/','/u/cms/www/201112/19141012lh2q.gif','','',0,'1',10);
INSERT INTO `jc_friendlink` VALUES (6,1,2,'ihush','http://www.ihush.com/','/u/cms/www/201112/19141255yrfb.gif','','',0,'1',10);
INSERT INTO `jc_friendlink` VALUES (7,1,2,'名品打折','http://temai.dazhe.cn','/u/cms/www/201112/19141401rp2d.gif','','',0,'1',10);
INSERT INTO `jc_friendlink` VALUES (8,1,2,'优品','http://temai.dazhe.cn','/u/cms/www/201112/191415078pzs.gif','','',1,'1',10);
/*!40000 ALTER TABLE `jc_friendlink` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_friendlink_ctg
#

DROP TABLE IF EXISTS `jc_friendlink_ctg`;
CREATE TABLE `jc_friendlink_ctg` (
  `friendlinkctg_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_name` varchar(50) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlinkctg_id`),
  KEY `fk_jc_friendlinkctg_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接类别';

#
# Dumping data for table jc_friendlink_ctg
#

LOCK TABLES `jc_friendlink_ctg` WRITE;
/*!40000 ALTER TABLE `jc_friendlink_ctg` DISABLE KEYS */;
INSERT INTO `jc_friendlink_ctg` VALUES (1,1,'文字链接',1);
INSERT INTO `jc_friendlink_ctg` VALUES (2,1,'品牌专区（图片链接）',2);
/*!40000 ALTER TABLE `jc_friendlink_ctg` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_group
#

DROP TABLE IF EXISTS `jc_group`;
CREATE TABLE `jc_group` (
  `group_id` int(11) NOT NULL auto_increment,
  `group_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `need_captcha` tinyint(1) NOT NULL default '1' COMMENT '是否需要验证码',
  `need_check` tinyint(1) NOT NULL default '1' COMMENT '是否需要审核',
  `allow_per_day` int(11) NOT NULL default '4096' COMMENT '每日允许上传KB',
  `allow_max_file` int(11) NOT NULL default '1024' COMMENT '每个文件最大KB',
  `allow_suffix` varchar(255) default 'jpg,jpeg,gif,png,bmp' COMMENT '允许上传的后缀',
  `is_reg_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认会员组',
  PRIMARY KEY  (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS会员组表';

#
# Dumping data for table jc_group
#

LOCK TABLES `jc_group` WRITE;
/*!40000 ALTER TABLE `jc_group` DISABLE KEYS */;
INSERT INTO `jc_group` VALUES (1,'普通会员',10,1,1,4096,1024,'jpg,jpeg,gif,png,bmp',1);
INSERT INTO `jc_group` VALUES (2,'高级组',10,1,1,0,0,'',0);
/*!40000 ALTER TABLE `jc_group` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_guestbook
#

DROP TABLE IF EXISTS `jc_guestbook`;
CREATE TABLE `jc_guestbook` (
  `guestbook_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `guestbookctg_id` int(11) NOT NULL,
  `member_id` int(11) default NULL COMMENT '留言会员',
  `admin_id` int(11) default NULL COMMENT '回复管理员',
  `ip` varchar(50) NOT NULL COMMENT '留言IP',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `replay_time` datetime default NULL COMMENT '回复时间',
  `is_checked` tinyint(1) NOT NULL default '0' COMMENT '是否审核',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推荐',
  PRIMARY KEY  (`guestbook_id`),
  KEY `fk_jc_ctg_guestbook` (`guestbookctg_id`),
  KEY `fk_jc_guestbook_admin` (`admin_id`),
  KEY `fk_jc_guestbook_member` (`member_id`),
  KEY `fk_jc_guestbook_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='CMS留言';

#
# Dumping data for table jc_guestbook
#

LOCK TABLES `jc_guestbook` WRITE;
/*!40000 ALTER TABLE `jc_guestbook` DISABLE KEYS */;
INSERT INTO `jc_guestbook` VALUES (1,1,1,1,NULL,'127.0.0.1','2011-01-04 10:08:18',NULL,1,1);
INSERT INTO `jc_guestbook` VALUES (2,1,1,1,NULL,'192.168.0.1','2011-01-04 14:45:45',NULL,1,1);
INSERT INTO `jc_guestbook` VALUES (3,1,1,1,NULL,'192.168.0.1','2011-01-04 14:46:24',NULL,1,1);
INSERT INTO `jc_guestbook` VALUES (4,1,1,1,NULL,'192.168.0.1','2011-01-04 14:52:41',NULL,1,1);
INSERT INTO `jc_guestbook` VALUES (5,1,1,1,NULL,'192.168.0.200','2011-12-18 16:10:10',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (6,1,1,1,NULL,'192.168.0.200','2011-12-18 16:10:52',NULL,0,0);
INSERT INTO `jc_guestbook` VALUES (7,1,1,1,NULL,'192.168.0.1','2011-12-19 12:06:28',NULL,1,1);
/*!40000 ALTER TABLE `jc_guestbook` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_guestbook_ctg
#

DROP TABLE IF EXISTS `jc_guestbook_ctg`;
CREATE TABLE `jc_guestbook_ctg` (
  `guestbookctg_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `ctg_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `description` varchar(255) default NULL COMMENT '描述',
  PRIMARY KEY  (`guestbookctg_id`),
  KEY `fk_jc_guestbookctg_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS留言类别';

#
# Dumping data for table jc_guestbook_ctg
#

LOCK TABLES `jc_guestbook_ctg` WRITE;
/*!40000 ALTER TABLE `jc_guestbook_ctg` DISABLE KEYS */;
INSERT INTO `jc_guestbook_ctg` VALUES (1,1,'普通',1,'普通留言');
/*!40000 ALTER TABLE `jc_guestbook_ctg` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_guestbook_ext
#

DROP TABLE IF EXISTS `jc_guestbook_ext`;
CREATE TABLE `jc_guestbook_ext` (
  `guestbook_id` int(11) NOT NULL,
  `title` varchar(255) default NULL COMMENT '留言标题',
  `content` longtext COMMENT '留言内容',
  `reply` longtext COMMENT '回复内容',
  `email` varchar(100) default NULL COMMENT '电子邮件',
  `phone` varchar(100) default NULL COMMENT '电话',
  `qq` varchar(50) default NULL COMMENT 'QQ',
  KEY `fk_jc_ext_guestbook` (`guestbook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS留言内容';

#
# Dumping data for table jc_guestbook_ext
#

LOCK TABLES `jc_guestbook_ext` WRITE;
/*!40000 ALTER TABLE `jc_guestbook_ext` DISABLE KEYS */;
INSERT INTO `jc_guestbook_ext` VALUES (1,'湖南未来一周仍维持低温雨雪天气','湖南未来一周仍维持低温雨雪天气',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (2,'范冰冰退出娱乐圈','范冰冰退出娱乐圈',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (3,'JEECMS v3.0.2正式版发布','终于发布了',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (4,'多重压力将影响整体衣柜发展','据悉，2010年下半年以来，衣柜企业整体销售形势不容乐观。不少企业下滑幅度高达60%，在一些主流卖场里，即使排名前十名的企业也未必能盈利。在这种情况下，企业纷纷打出了涨价牌，实属无奈之举。','1111111111',NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (5,'1111111111111111111111111111111','11111111111111111111111111111111111111111111111111111111111111111111111',NULL,NULL,NULL,NULL);
INSERT INTO `jc_guestbook_ext` VALUES (6,'11111111111111111111','1111111111111111111111111111111',NULL,NULL,'1111111111',NULL);
INSERT INTO `jc_guestbook_ext` VALUES (7,'足球宝贝徐冬冬','足球宝贝徐冬冬海边写真 湿身展无限魅力足球宝贝徐冬冬海边写真 湿身展无限魅',NULL,'11@102.com','1222331155',NULL);
/*!40000 ALTER TABLE `jc_guestbook_ext` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_keyword
#

DROP TABLE IF EXISTS `jc_keyword`;
CREATE TABLE `jc_keyword` (
  `keyword_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL COMMENT '站点ID',
  `keyword_name` varchar(100) NOT NULL COMMENT '名称',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`keyword_id`),
  KEY `fk_jc_keyword_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS内容关键词表';

#
# Dumping data for table jc_keyword
#

LOCK TABLES `jc_keyword` WRITE;
/*!40000 ALTER TABLE `jc_keyword` DISABLE KEYS */;
INSERT INTO `jc_keyword` VALUES (1,NULL,'内容管理系统','<a href=\"http://www.jeecms.com/\" target=\"_blank\">内容管理系统</a>',0);
/*!40000 ALTER TABLE `jc_keyword` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_log
#

DROP TABLE IF EXISTS `jc_log`;
CREATE TABLE `jc_log` (
  `log_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `site_id` int(11) default NULL,
  `category` int(11) NOT NULL COMMENT '日志类型',
  `log_time` datetime NOT NULL COMMENT '日志时间',
  `ip` varchar(50) default NULL COMMENT 'IP地址',
  `url` varchar(255) default NULL COMMENT 'URL地址',
  `title` varchar(255) default NULL COMMENT '日志标题',
  `content` varchar(255) default NULL COMMENT '日志内容',
  PRIMARY KEY  (`log_id`),
  KEY `fk_jc_log_site` (`site_id`),
  KEY `fk_jc_log_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='CMS日志表';

#
# Dumping data for table jc_log
#

LOCK TABLES `jc_log` WRITE;
/*!40000 ALTER TABLE `jc_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_log` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_message
#

DROP TABLE IF EXISTS `jc_message`;
CREATE TABLE `jc_message` (
  `msg_id` int(11) NOT NULL auto_increment COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL default '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL default NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL default '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL default '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL default '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL default '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL default '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  PRIMARY KEY  (`msg_id`),
  KEY `fk_jc_message_user_send` (`msg_send_user`),
  KEY `fk_jc_message_user_receiver` (`msg_receiver_user`),
  KEY `fk_jc_message_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信';

#
# Dumping data for table jc_message
#

LOCK TABLES `jc_message` WRITE;
/*!40000 ALTER TABLE `jc_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_message` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_model
#

DROP TABLE IF EXISTS `jc_model`;
CREATE TABLE `jc_model` (
  `model_id` int(11) NOT NULL,
  `model_name` varchar(100) NOT NULL COMMENT '名称',
  `model_path` varchar(100) NOT NULL COMMENT '路径',
  `tpl_channel_prefix` varchar(20) default NULL COMMENT '栏目模板前缀',
  `tpl_content_prefix` varchar(20) default NULL COMMENT '内容模板前缀',
  `title_img_width` int(11) NOT NULL default '139' COMMENT '栏目标题图宽度',
  `title_img_height` int(11) NOT NULL default '139' COMMENT '栏目标题图高度',
  `content_img_width` int(11) NOT NULL default '310' COMMENT '栏目内容图宽度',
  `content_img_height` int(11) NOT NULL default '310' COMMENT '栏目内容图高度',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL default '1' COMMENT '是否有内容',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认模型',
  PRIMARY KEY  (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型表';

#
# Dumping data for table jc_model
#

LOCK TABLES `jc_model` WRITE;
/*!40000 ALTER TABLE `jc_model` DISABLE KEYS */;
INSERT INTO `jc_model` VALUES (1,'新闻','1','新闻栏目','新闻内容',139,139,310,310,1,1,0,1);
INSERT INTO `jc_model` VALUES (2,'单页','2','单页','',139,139,310,310,2,0,0,0);
INSERT INTO `jc_model` VALUES (3,'作品','3','作品栏目','作品内容',139,139,310,310,3,1,0,0);
INSERT INTO `jc_model` VALUES (4,'下载','4','下载栏目','下载内容',139,139,310,310,4,1,0,0);
INSERT INTO `jc_model` VALUES (5,'图库','5','图库栏目','图库内容',139,139,310,310,5,1,0,0);
INSERT INTO `jc_model` VALUES (6,'视频','6','视频栏目','视频内容',139,139,310,310,10,1,0,0);
INSERT INTO `jc_model` VALUES (7,'产品','7','产品栏目','产品内容',139,139,310,310,10,1,0,0);
/*!40000 ALTER TABLE `jc_model` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_model_item
#

DROP TABLE IF EXISTS `jc_model_item`;
CREATE TABLE `jc_model_item` (
  `modelitem_id` int(11) NOT NULL auto_increment,
  `model_id` int(11) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `item_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '70' COMMENT '排列顺序',
  `def_value` varchar(255) default NULL COMMENT '默认值',
  `opt_value` varchar(255) default NULL COMMENT '可选项',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) NOT NULL default '1' COMMENT '数据类型',
  `is_single` tinyint(1) NOT NULL default '1' COMMENT '是否独占一行',
  `is_channel` tinyint(1) NOT NULL default '1' COMMENT '是否栏目模型项',
  `is_custom` tinyint(1) NOT NULL default '1' COMMENT '是否自定义',
  `is_display` tinyint(1) NOT NULL default '1' COMMENT '是否显示',
  PRIMARY KEY  (`modelitem_id`),
  KEY `fk_jc_item_model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=utf8 COMMENT='CMS模型项表';

#
# Dumping data for table jc_model_item
#

LOCK TABLES `jc_model_item` WRITE;
/*!40000 ALTER TABLE `jc_model_item` DISABLE KEYS */;
INSERT INTO `jc_model_item` VALUES (1,1,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (2,1,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (3,1,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (4,1,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (5,1,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (6,1,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (7,1,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (8,1,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (9,1,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (10,1,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (11,1,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (12,1,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (13,1,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (14,1,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (15,1,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (16,1,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (17,1,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (18,1,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (19,1,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (20,1,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (21,1,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (22,1,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (23,1,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (24,1,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (25,1,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (26,1,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (27,1,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (28,1,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (29,1,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (30,1,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (31,1,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (32,1,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (33,1,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (34,1,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (35,1,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (36,1,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (37,1,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (38,1,'attachments','附件',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (39,1,'media','多媒体',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (40,1,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (42,2,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (43,2,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (44,2,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (45,2,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (46,2,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (47,2,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (48,2,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (49,2,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (50,2,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (51,2,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (52,2,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (53,2,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (54,3,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (55,3,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (56,3,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (57,3,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (58,3,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (59,3,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (60,3,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (61,3,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (62,3,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (63,3,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (64,3,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (65,3,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (66,3,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (67,3,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (68,3,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (69,3,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (70,3,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (73,3,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (74,3,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (75,3,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (76,3,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (77,3,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (78,3,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (79,3,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (80,3,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (81,3,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (82,3,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (83,3,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (84,3,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (85,3,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (86,3,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (87,3,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (88,3,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (89,3,'attachments','附件',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (90,3,'media','多媒体',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (91,3,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (92,3,'pictures','图片集',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (93,4,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (94,4,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (95,4,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (96,4,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (97,4,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (98,4,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (99,4,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (100,4,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (101,4,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (102,4,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (103,4,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (104,4,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (105,4,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (106,4,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (107,4,'channelId','栏目',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (108,4,'title','软件名称',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (109,4,'shortTitle','软件简称',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (110,4,'titleColor','标题颜色',4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (111,4,'description','摘要',5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (112,4,'author','发布人',6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (113,4,'viewGroupIds','浏览权限',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (114,4,'topLevel','固顶级别',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (115,4,'releaseDate','发布时间',9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (116,4,'typeId','内容类型',21,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (117,4,'tplContent','指定模板',22,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (118,4,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (119,4,'attachments','资源上传',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (120,4,'txt','软件介绍',20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (121,4,'softType','软件类型',12,'国产软件','国产软件,国外软件','100','3','30',NULL,NULL,6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (122,4,'warrant','软件授权',13,'免费版','免费版,共享版','','3','30','','',6,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (123,4,'relatedLink','相关链接',14,'http://','','50','3','30','','',1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (124,4,'demoUrl','演示地址',15,'http://',NULL,'60','3','30',NULL,NULL,1,0,0,1,1);
INSERT INTO `jc_model_item` VALUES (125,5,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (126,5,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (127,5,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (128,5,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (129,5,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (130,5,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (131,5,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (132,5,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (133,5,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (134,5,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (135,5,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (136,5,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (137,5,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (138,5,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (139,5,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (140,5,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (141,5,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (142,5,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (143,5,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (144,5,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (145,5,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (146,5,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (147,5,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (148,5,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (149,5,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (150,5,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (151,5,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (152,5,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (153,5,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (154,5,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (155,5,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (156,5,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (157,5,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (158,5,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (159,5,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (160,5,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (161,5,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (162,5,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (163,5,'pictures','图片集',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (164,6,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (165,6,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (166,6,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (167,6,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (168,6,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (169,6,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (170,6,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (171,6,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (172,6,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (173,6,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (174,6,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (175,6,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (176,6,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (177,6,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (178,6,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (179,6,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (180,6,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (181,6,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (182,6,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (183,6,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (184,6,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (185,6,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (186,6,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (187,6,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (188,6,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (189,6,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (190,6,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (191,6,'description','内容简介',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (192,6,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (193,6,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (194,6,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (195,6,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (196,6,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (197,6,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (198,6,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (199,6,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (200,6,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (201,6,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (202,6,'attachments','附件',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (203,6,'media','多媒体',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (204,7,'name','栏目名称',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (205,7,'path','访问路径',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (206,7,'title','meta标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (207,7,'keywords','meta关键字',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (208,7,'description','meta描述',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (209,7,'tplChannel','栏目模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (210,7,'tplContent','内容模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (211,7,'channelStatic','栏目静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (212,7,'contentStatic','内容静态化',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (213,7,'priority','排列顺序',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (214,7,'display','显示',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (215,7,'docImg','文档图片',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (216,7,'finalStep','终审级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (217,7,'afterCheck','审核后',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (218,7,'commentControl','评论',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (219,7,'allowUpdown','顶踩',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (220,7,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (221,7,'contriGroupIds','投稿权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,0,1,0,1);
INSERT INTO `jc_model_item` VALUES (222,7,'userIds','管理权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (223,7,'link','外部链接',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (224,7,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (225,7,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,1,0,1);
INSERT INTO `jc_model_item` VALUES (226,7,'channelId','栏目',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (227,7,'title','标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (228,7,'shortTitle','简短标题',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (229,7,'titleColor','标题颜色',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (230,7,'tagStr','Tag标签',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (231,7,'description','摘要',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (232,7,'author','作者',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (233,7,'origin','来源',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (234,7,'viewGroupIds','浏览权限',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (235,7,'topLevel','固顶级别',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (236,7,'releaseDate','发布时间',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (237,7,'typeId','内容类型',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (238,7,'tplContent','指定模板',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,0,0,0,1);
INSERT INTO `jc_model_item` VALUES (239,7,'typeImg','类型图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (240,7,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (241,7,'contentImg','内容图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (242,7,'txt','内容',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,1,0,0,1);
INSERT INTO `jc_model_item` VALUES (243,7,'marketprice','市场价',10,NULL,NULL,NULL,'3','30',NULL,NULL,3,1,0,1,1);
INSERT INTO `jc_model_item` VALUES (244,7,'price','价格',10,NULL,NULL,NULL,'3','30',NULL,NULL,3,1,0,1,1);
INSERT INTO `jc_model_item` VALUES (246,4,'titleImg','标题图',10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,0,0,1);
/*!40000 ALTER TABLE `jc_model_item` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_receiver_message
#

DROP TABLE IF EXISTS `jc_receiver_message`;
CREATE TABLE `jc_receiver_message` (
  `msg_re_id` int(11) NOT NULL auto_increment COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL default '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL default NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL default '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL default '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL default '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL default '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL default '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  `msg_id` int(11) default NULL COMMENT '发信的信件id',
  PRIMARY KEY  (`msg_re_id`),
  KEY `jc_receiver_message_user_send` (`msg_send_user`),
  KEY `jc_receiver_message_user_receiver` (`msg_receiver_user`),
  KEY `jc_receiver_message_site` (`site_id`),
  KEY `jc_receiver_message_message` (`msg_re_id`),
  KEY `fk_jc_receiver_message_message` (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信收信表';

#
# Dumping data for table jc_receiver_message
#

LOCK TABLES `jc_receiver_message` WRITE;
/*!40000 ALTER TABLE `jc_receiver_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_receiver_message` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_role
#

DROP TABLE IF EXISTS `jc_role`;
CREATE TABLE `jc_role` (
  `role_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `is_super` char(1) NOT NULL default '0' COMMENT '拥有所有权限',
  PRIMARY KEY  (`role_id`),
  KEY `fk_jc_role_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS角色表';

#
# Dumping data for table jc_role
#

LOCK TABLES `jc_role` WRITE;
/*!40000 ALTER TABLE `jc_role` DISABLE KEYS */;
INSERT INTO `jc_role` VALUES (1,NULL,'管理员',10,'1');
/*!40000 ALTER TABLE `jc_role` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_role_permission
#

DROP TABLE IF EXISTS `jc_role_permission`;
CREATE TABLE `jc_role_permission` (
  `role_id` int(11) NOT NULL,
  `uri` varchar(100) NOT NULL,
  KEY `fk_jc_permission_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS角色授权表';

#
# Dumping data for table jc_role_permission
#

LOCK TABLES `jc_role_permission` WRITE;
/*!40000 ALTER TABLE `jc_role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_sensitivity
#

DROP TABLE IF EXISTS `jc_sensitivity`;
CREATE TABLE `jc_sensitivity` (
  `sensitivity_id` int(11) NOT NULL auto_increment,
  `search` varchar(255) NOT NULL COMMENT '敏感词',
  `replacement` varchar(255) NOT NULL COMMENT '替换词',
  PRIMARY KEY  (`sensitivity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS敏感词表';

#
# Dumping data for table jc_sensitivity
#

LOCK TABLES `jc_sensitivity` WRITE;
/*!40000 ALTER TABLE `jc_sensitivity` DISABLE KEYS */;
INSERT INTO `jc_sensitivity` VALUES (1,'法论功','***');
/*!40000 ALTER TABLE `jc_sensitivity` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site
#

DROP TABLE IF EXISTS `jc_site`;
CREATE TABLE `jc_site` (
  `site_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL COMMENT '配置ID',
  `ftp_upload_id` int(11) default NULL COMMENT '上传ftp',
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `site_path` varchar(20) NOT NULL COMMENT '路径',
  `site_name` varchar(100) NOT NULL COMMENT '网站名称',
  `short_name` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL default 'http://' COMMENT '协议',
  `dynamic_suffix` varchar(10) NOT NULL default '.jhtml' COMMENT '动态页后缀',
  `static_suffix` varchar(10) NOT NULL default '.html' COMMENT '静态页后缀',
  `static_dir` varchar(50) default NULL COMMENT '静态页存放目录',
  `is_index_to_root` char(1) NOT NULL default '0' COMMENT '是否使用将首页放在根目录下',
  `is_static_index` char(1) NOT NULL default '0' COMMENT '是否静态化首页',
  `locale_admin` varchar(10) NOT NULL default 'zh_CN' COMMENT '后台本地化',
  `locale_front` varchar(10) NOT NULL default 'zh_CN' COMMENT '前台本地化',
  `tpl_solution` varchar(50) NOT NULL default 'default' COMMENT '模板方案',
  `final_step` tinyint(4) NOT NULL default '2' COMMENT '终审级别',
  `after_check` tinyint(4) NOT NULL default '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_relative_path` char(1) NOT NULL default '1' COMMENT '是否使用相对路径',
  `is_recycle_on` char(1) NOT NULL default '1' COMMENT '是否开启回收站',
  `domain_alias` varchar(255) default NULL COMMENT '域名别名',
  `domain_redirect` varchar(255) default NULL COMMENT '域名重定向',
  PRIMARY KEY  (`site_id`),
  UNIQUE KEY `ak_domain` (`domain`),
  KEY `fk_jc_site_config` (`config_id`),
  KEY `fk_jc_site_upload_ftp` (`ftp_upload_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS站点表';

#
# Dumping data for table jc_site
#

LOCK TABLES `jc_site` WRITE;
/*!40000 ALTER TABLE `jc_site` DISABLE KEYS */;
INSERT INTO `jc_site` VALUES (1,1,NULL,'localhost','www','JEECMS开发站','JEECMS开发站','http://','.jhtml','.html',NULL,'0','0','zh_CN','zh_CN','red',2,2,'1','1',NULL,NULL);
/*!40000 ALTER TABLE `jc_site` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site_attr
#

DROP TABLE IF EXISTS `jc_site_attr`;
CREATE TABLE `jc_site_attr` (
  `site_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_attr_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点属性表';

#
# Dumping data for table jc_site_attr
#

LOCK TABLES `jc_site_attr` WRITE;
/*!40000 ALTER TABLE `jc_site_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_site_attr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site_cfg
#

DROP TABLE IF EXISTS `jc_site_cfg`;
CREATE TABLE `jc_site_cfg` (
  `site_id` int(11) NOT NULL,
  `cfg_name` varchar(30) NOT NULL COMMENT '名称',
  `cfg_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_jc_cfg_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点配置表';

#
# Dumping data for table jc_site_cfg
#

LOCK TABLES `jc_site_cfg` WRITE;
/*!40000 ALTER TABLE `jc_site_cfg` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_site_cfg` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site_flow
#

DROP TABLE IF EXISTS `jc_site_flow`;
CREATE TABLE `jc_site_flow` (
  `flow_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `access_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '访问者ip',
  `access_date` varchar(50) default NULL COMMENT '访问日期',
  `access_time` datetime default NULL COMMENT '访问时间',
  `access_page` varchar(255) NOT NULL default '' COMMENT '访问页面',
  `referer_website` varchar(255) default NULL COMMENT '来访网站',
  `referer_page` varchar(255) default NULL COMMENT '来访页面',
  `referer_keyword` varchar(255) default NULL COMMENT '来访关键字',
  `area` varchar(50) default NULL COMMENT '地区',
  `session_id` varchar(50) NOT NULL default '' COMMENT 'cookie信息',
  PRIMARY KEY  (`flow_id`),
  KEY `fk_jc_flow_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='站点流量统计表';

#
# Dumping data for table jc_site_flow
#

LOCK TABLES `jc_site_flow` WRITE;
/*!40000 ALTER TABLE `jc_site_flow` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_site_flow` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site_model
#

DROP TABLE IF EXISTS `jc_site_model`;
CREATE TABLE `jc_site_model` (
  `model_id` int(11) NOT NULL auto_increment,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `model_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `upload_path` varchar(100) default NULL COMMENT '上传路径',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) default '1' COMMENT '0:编辑器;1:文本框;2:文本区;3:图片;4:附件',
  `is_single` tinyint(1) default '1' COMMENT '是否独占一行',
  PRIMARY KEY  (`model_id`),
  UNIQUE KEY `ak_field` (`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点信息模型表';

#
# Dumping data for table jc_site_model
#

LOCK TABLES `jc_site_model` WRITE;
/*!40000 ALTER TABLE `jc_site_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_site_model` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_site_txt
#

DROP TABLE IF EXISTS `jc_site_txt`;
CREATE TABLE `jc_site_txt` (
  `site_id` int(11) NOT NULL,
  `txt_name` varchar(30) NOT NULL COMMENT '名称',
  `txt_value` longtext COMMENT '值',
  KEY `fk_jc_txt_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点文本表';

#
# Dumping data for table jc_site_txt
#

LOCK TABLES `jc_site_txt` WRITE;
/*!40000 ALTER TABLE `jc_site_txt` DISABLE KEYS */;
/*!40000 ALTER TABLE `jc_site_txt` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_topic
#

DROP TABLE IF EXISTS `jc_topic`;
CREATE TABLE `jc_topic` (
  `topic_id` int(11) NOT NULL auto_increment,
  `channel_id` int(11) default NULL,
  `topic_name` varchar(150) NOT NULL COMMENT '名称',
  `short_name` varchar(150) default NULL COMMENT '简称',
  `keywords` varchar(255) default NULL COMMENT '关键字',
  `description` varchar(255) default NULL COMMENT '描述',
  `title_img` varchar(100) default NULL COMMENT '标题图',
  `content_img` varchar(100) default NULL COMMENT '内容图',
  `tpl_content` varchar(100) default NULL COMMENT '专题模板',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `is_recommend` tinyint(1) NOT NULL default '0' COMMENT '是否推??',
  PRIMARY KEY  (`topic_id`),
  KEY `fk_jc_topic_channel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='CMS专题表';

#
# Dumping data for table jc_topic
#

LOCK TABLES `jc_topic` WRITE;
/*!40000 ALTER TABLE `jc_topic` DISABLE KEYS */;
INSERT INTO `jc_topic` VALUES (1,NULL,'2010年南非世界杯','世界杯','世界杯','2010年世界杯将在南非约翰内斯堡拉开帷幕，32路豪强将在一个月的时间里，为大力神杯展开争夺。','http://a2.att.hudong.com/08/61/01300000406647124377613651616.jpg','http://i0.sinaimg.cn/ty/news/2010/0611/sjbsc.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (2,NULL,'上海世博会专题','世博','世博','人类文明的盛会，我们大家的世博，精彩开篇，“满月”有余。随着上海世博会的有序前行，人们从中收获的感悟也由表及里。','http://xwcb.eastday.com/c/20061116/images/00033531.jpg','/u/cms/www/201112/19151533k5ey.jpg','',10,1);
INSERT INTO `jc_topic` VALUES (3,NULL,'低碳经济','低碳','低碳','所谓低碳经济，是指在可持续发展理念指导下，通过技术创新、制度创新、产业转型、新能源开发等多种手段，尽可能地减少煤炭石油等高碳能源消耗，减少温室气体排放，达到经济社会发展与生态环境保护双赢的一种经济发展形态。','http://www.6788.cn/bscyw/upfiles/0125/1f0aaff5/fery/w1tg.jpg',NULL,'',10,0);
/*!40000 ALTER TABLE `jc_topic` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_user
#

DROP TABLE IF EXISTS `jc_user`;
CREATE TABLE `jc_user` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `rank` int(11) NOT NULL default '0' COMMENT '管理员级别',
  `upload_total` bigint(20) NOT NULL default '0' COMMENT '上传总大小',
  `upload_size` int(11) NOT NULL default '0' COMMENT '上传大小',
  `upload_date` date default NULL COMMENT '上传日期',
  `is_admin` tinyint(1) NOT NULL default '0' COMMENT '是否管理员',
  `is_viewonly_admin` tinyint(1) NOT NULL default '0' COMMENT '是否只读管理员',
  `is_self_admin` tinyint(1) NOT NULL default '0' COMMENT '是否只管理自己的数据',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`),
  KEY `fk_jc_user_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户表';

#
# Dumping data for table jc_user
#

LOCK TABLES `jc_user` WRITE;
/*!40000 ALTER TABLE `jc_user` DISABLE KEYS */;
INSERT INTO `jc_user` VALUES (1,1,'admin','admin@yahoo.com','2011-01-03','127.0.0.1','2011-12-19 18:12:14','127.0.0.1',146,9,0,0,NULL,1,0,0,0);
INSERT INTO `jc_user` VALUES (5,1,'test','test@163.com','2011-12-19 15:26:02','192.168.0.173','2011-12-19 15:26:02','192.168.0.173',0,0,0,0,'2011-12-19',0,0,0,0);
/*!40000 ALTER TABLE `jc_user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_user_collection
#

DROP TABLE IF EXISTS `jc_user_collection`;
CREATE TABLE `jc_user_collection` (
  `user_id` int(11) NOT NULL default '0' COMMENT '用户id',
  `content_id` int(11) NOT NULL default '0' COMMENT '内容id',
  PRIMARY KEY  (`user_id`,`content_id`),
  KEY `fk_jc_user_collection_con` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏关联表';

#
# Dumping data for table jc_user_collection
#

LOCK TABLES `jc_user_collection` WRITE;
/*!40000 ALTER TABLE `jc_user_collection` DISABLE KEYS */;
INSERT INTO `jc_user_collection` VALUES (1,237);
/*!40000 ALTER TABLE `jc_user_collection` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_user_ext
#

DROP TABLE IF EXISTS `jc_user_ext`;
CREATE TABLE `jc_user_ext` (
  `user_id` int(11) NOT NULL,
  `realname` varchar(100) default NULL COMMENT '真实姓名',
  `gender` tinyint(1) default NULL COMMENT '性别',
  `birthday` datetime default NULL COMMENT '出生日期',
  `intro` varchar(255) default NULL COMMENT '个人介绍',
  `comefrom` varchar(150) default NULL COMMENT '来自',
  `qq` varchar(100) default NULL COMMENT 'QQ',
  `msn` varchar(100) default NULL COMMENT 'MSN',
  `phone` varchar(50) default NULL COMMENT '电话',
  `mobile` varchar(50) default NULL COMMENT '手机',
  `user_img` varchar(255) default NULL COMMENT '用户头像',
  `user_signature` varchar(255) default NULL COMMENT '用户个性签名',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户扩展信息表';

#
# Dumping data for table jc_user_ext
#

LOCK TABLES `jc_user_ext` WRITE;
/*!40000 ALTER TABLE `jc_user_ext` DISABLE KEYS */;
INSERT INTO `jc_user_ext` VALUES (1,'JEECMS',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jc_user_ext` VALUES (5,NULL,NULL,NULL,NULL,',',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `jc_user_ext` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_user_role
#

DROP TABLE IF EXISTS `jc_user_role`;
CREATE TABLE `jc_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`user_id`),
  KEY `fk_jc_role_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户角色关联表';

#
# Dumping data for table jc_user_role
#

LOCK TABLES `jc_user_role` WRITE;
/*!40000 ALTER TABLE `jc_user_role` DISABLE KEYS */;
INSERT INTO `jc_user_role` VALUES (1,1);
/*!40000 ALTER TABLE `jc_user_role` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_user_site
#

DROP TABLE IF EXISTS `jc_user_site`;
CREATE TABLE `jc_user_site` (
  `usersite_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL default '1' COMMENT '审核级别',
  `is_all_channel` tinyint(1) NOT NULL default '1' COMMENT '是否拥有所有栏目的权限',
  PRIMARY KEY  (`usersite_id`),
  KEY `fk_jc_site_user` (`user_id`),
  KEY `fk_jc_user_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS管理员站点表';

#
# Dumping data for table jc_user_site
#

LOCK TABLES `jc_user_site` WRITE;
/*!40000 ALTER TABLE `jc_user_site` DISABLE KEYS */;
INSERT INTO `jc_user_site` VALUES (1,1,1,2,1);
/*!40000 ALTER TABLE `jc_user_site` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_vote_item
#

DROP TABLE IF EXISTS `jc_vote_item`;
CREATE TABLE `jc_vote_item` (
  `voteitem_id` int(11) NOT NULL auto_increment,
  `votetopic_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `vote_count` int(11) NOT NULL default '0' COMMENT '投票数量',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`voteitem_id`),
  KEY `fk_jc_vote_item_topic` (`votetopic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='CMS投票项';

#
# Dumping data for table jc_vote_item
#

LOCK TABLES `jc_vote_item` WRITE;
/*!40000 ALTER TABLE `jc_vote_item` DISABLE KEYS */;
INSERT INTO `jc_vote_item` VALUES (1,1,'基于java技术，安全稳定，易扩展',21,1);
INSERT INTO `jc_vote_item` VALUES (4,1,'jsp是未来发展的趋势',23,4);
INSERT INTO `jc_vote_item` VALUES (5,1,'java执行速度快，性能优良',5,5);
INSERT INTO `jc_vote_item` VALUES (6,1,'跨平台，支持windows、linux、unix',6,6);
INSERT INTO `jc_vote_item` VALUES (7,1,'学习研究',11,7);
INSERT INTO `jc_vote_item` VALUES (8,1,'其它',10,8);
/*!40000 ALTER TABLE `jc_vote_item` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_vote_record
#

DROP TABLE IF EXISTS `jc_vote_record`;
CREATE TABLE `jc_vote_record` (
  `voterecored_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `votetopic_id` int(11) NOT NULL,
  `vote_time` datetime NOT NULL COMMENT '投票时间',
  `vote_ip` varchar(50) NOT NULL COMMENT '投票IP',
  `vote_cookie` varchar(32) NOT NULL COMMENT '投票COOKIE',
  PRIMARY KEY  (`voterecored_id`),
  KEY `fk_jc_vote_record_topic` (`votetopic_id`),
  KEY `fk_jc_voterecord_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS投票记录';

#
# Dumping data for table jc_vote_record
#

LOCK TABLES `jc_vote_record` WRITE;
/*!40000 ALTER TABLE `jc_vote_record` DISABLE KEYS */;
INSERT INTO `jc_vote_record` VALUES (1,NULL,1,'2011-06-04 15:41:31','127.0.0.1','2600e4a345ba4fc289088d7abe59321c');
/*!40000 ALTER TABLE `jc_vote_record` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jc_vote_topic
#

DROP TABLE IF EXISTS `jc_vote_topic`;
CREATE TABLE `jc_vote_topic` (
  `votetopic_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `description` varchar(255) default NULL COMMENT '描述',
  `start_time` datetime default NULL COMMENT '开始时间',
  `end_time` datetime default NULL COMMENT '结束时间',
  `repeate_hour` int(11) default NULL COMMENT '重复投票限制时间，单位小时，为空不允许重复投票',
  `total_count` int(11) NOT NULL default '0' COMMENT '总投票数',
  `multi_select` int(11) NOT NULL default '1' COMMENT '最多可以选择几项',
  `is_restrict_member` tinyint(1) NOT NULL default '0' COMMENT '是否限制会员',
  `is_restrict_ip` tinyint(1) NOT NULL default '0' COMMENT '是否限制IP',
  `is_restrict_cookie` tinyint(1) NOT NULL default '0' COMMENT '是否限制COOKIE',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL default '0' COMMENT '是否默认主题',
  PRIMARY KEY  (`votetopic_id`),
  KEY `fk_jc_votetopic_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS投票主题';

#
# Dumping data for table jc_vote_topic
#

LOCK TABLES `jc_vote_topic` WRITE;
/*!40000 ALTER TABLE `jc_vote_topic` DISABLE KEYS */;
INSERT INTO `jc_vote_topic` VALUES (1,1,'您为什么选择jsp cms,java cms? ','在php cms为建站主流CMS的年代，您为什么选择jsp cms,java cms？请给出您的意见吧！',NULL,NULL,NULL,76,3,0,0,1,0,1);
/*!40000 ALTER TABLE `jc_vote_topic` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_authentication
#

DROP TABLE IF EXISTS `jo_authentication`;
CREATE TABLE `jo_authentication` (
  `authentication_id` char(32) NOT NULL COMMENT '认证ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) NOT NULL COMMENT '登录ip',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY  (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';

#
# Dumping data for table jo_authentication
#

LOCK TABLES `jo_authentication` WRITE;
/*!40000 ALTER TABLE `jo_authentication` DISABLE KEYS */;
INSERT INTO `jo_authentication` VALUES ('03958b359f384af6aa05cafed7e1331b',1,'admin','admin@yahoo.com','2011-12-19 16:51:32','127.0.0.1','2011-12-19 16:51:52');
INSERT INTO `jo_authentication` VALUES ('07e49f6f4047424aa4796bdf0e6ebd32',1,'admin','admin@yahoo.com','2011-12-19 17:14:42','192.168.0.115','2011-12-19 17:24:27');
INSERT INTO `jo_authentication` VALUES ('08c5888ed1b243409a35b03adf3c10d1',1,'admin','admin@yahoo.com','2011-12-18 15:54:57','192.168.0.200','2011-12-18 15:55:25');
INSERT INTO `jo_authentication` VALUES ('0f27c52154284a75b38ab2b3f3cdbb2f',1,'admin','admin@yahoo.com','2011-12-19 16:01:33','192.168.0.115','2011-12-19 16:01:35');
INSERT INTO `jo_authentication` VALUES ('10fcb7d93867413694fac602c962db92',1,'admin','admin@yahoo.com','2011-12-19 16:56:56','192.168.0.156','2011-12-19 16:57:26');
INSERT INTO `jo_authentication` VALUES ('11ca3e06e0454825a08dfe78f1a089d6',1,'admin','admin@yahoo.com','2011-12-19 15:23:05','192.168.0.173','2011-12-19 15:32:37');
INSERT INTO `jo_authentication` VALUES ('1c7ef6aa9d4a4ca6a385c6997788436e',1,'admin','admin@yahoo.com','2011-12-19 16:55:46','192.168.0.200','2011-12-19 17:41:06');
INSERT INTO `jo_authentication` VALUES ('2109fba7d2294bab872c98735a8f642b',1,'admin','admin@yahoo.com','2011-12-19 17:51:27','192.168.0.200','2011-12-19 18:12:07');
INSERT INTO `jo_authentication` VALUES ('285936f7a33049d993540c5e1af9f790',1,'admin','admin@yahoo.com','2011-12-18 16:19:06','192.168.0.200','2011-12-18 17:29:10');
INSERT INTO `jo_authentication` VALUES ('2ce7aea80d594bbca28b4e82f2101fc8',1,'admin','admin@yahoo.com','2011-12-19 13:54:38','0:0:0:0:0:0:0:1','2011-12-19 13:54:53');
INSERT INTO `jo_authentication` VALUES ('2e86ac4d8e10473ab1b46a55a7614706',1,'admin','admin@yahoo.com','2011-12-19 09:32:33','0:0:0:0:0:0:0:1','2011-12-19 09:33:27');
INSERT INTO `jo_authentication` VALUES ('2f9133c0d6de45c18fec31ab84522861',1,'admin','admin@yahoo.com','2011-12-19 17:43:20','192.168.0.200','2011-12-19 18:13:46');
INSERT INTO `jo_authentication` VALUES ('2faeecb1691440b38ccf22b19cbf2f10',1,'admin','admin@yahoo.com','2011-12-19 09:16:18','192.168.0.200','2011-12-19 12:04:54');
INSERT INTO `jo_authentication` VALUES ('368e0c732e2948ecb609a07536237d6b',1,'admin','admin@yahoo.com','2011-12-19 17:07:57','192.168.0.173','2011-12-19 17:38:56');
INSERT INTO `jo_authentication` VALUES ('370bcb418d794834ad380e579d8b2468',1,'admin','admin@yahoo.com','2011-12-19 13:07:54','192.168.0.121','2011-12-19 13:13:53');
INSERT INTO `jo_authentication` VALUES ('3a374211e1364e2a82b3089f37243fe1',1,'admin','admin@yahoo.com','2011-12-19 17:36:23','192.168.0.200','2011-12-19 17:36:25');
INSERT INTO `jo_authentication` VALUES ('400182d3a8ad43c3802e2550ab6df969',1,'admin','admin@yahoo.com','2011-12-18 13:59:41','192.168.0.115','2011-12-18 14:28:16');
INSERT INTO `jo_authentication` VALUES ('418876d518dc4d70a4961306fbbb3fb1',1,'admin','admin@yahoo.com','2011-12-19 12:10:23','192.168.0.121','2011-12-19 12:18:03');
INSERT INTO `jo_authentication` VALUES ('4379af8a35924fd1843b7843c6c6eabb',1,'admin','admin@yahoo.com','2011-12-18 17:02:51','192.168.0.115','2011-12-18 17:21:51');
INSERT INTO `jo_authentication` VALUES ('4526b6b278d7428fa5ed4f7bce7360e6',1,'admin','admin@yahoo.com','2011-12-19 16:24:51','0:0:0:0:0:0:0:1','2011-12-19 16:37:56');
INSERT INTO `jo_authentication` VALUES ('4f1af3fe7eef4a8dbaf94a6fd089822e',1,'admin','admin@yahoo.com','2011-12-19 08:27:59','192.168.0.1','2011-12-19 12:06:41');
INSERT INTO `jo_authentication` VALUES ('50059de26fda4e9d8d40c3ae780832c1',1,'admin','admin@yahoo.com','2011-12-19 14:53:00','192.168.0.144','2011-12-19 15:15:52');
INSERT INTO `jo_authentication` VALUES ('5834ce15301d4979a55c7efc324f0db7',1,'admin','admin@yahoo.com','2011-12-18 15:56:58','192.168.0.200','2011-12-18 16:10:53');
INSERT INTO `jo_authentication` VALUES ('583769b390cc421c9a0e3f04e866bf99',1,'admin','admin@yahoo.com','2011-12-19 16:57:33','192.168.0.1','2011-12-19 17:37:43');
INSERT INTO `jo_authentication` VALUES ('5a6e2d909e0d4e63909e520947d6428d',1,'admin','admin@yahoo.com','2011-12-19 13:43:18','192.168.0.1','2011-12-19 13:43:36');
INSERT INTO `jo_authentication` VALUES ('5cd5a51993c94b9d82b5cda39068cefe',1,'admin','admin@yahoo.com','2011-12-19 17:04:14','127.0.0.1','2011-12-19 17:38:48');
INSERT INTO `jo_authentication` VALUES ('5d76079077f24abc92897270b5df1ee0',1,'admin','admin@yahoo.com','2011-12-19 16:54:42','192.168.0.115','2011-12-19 17:14:07');
INSERT INTO `jo_authentication` VALUES ('5f16786616ac49e0a7cb38c6d36f1404',1,'admin','admin@yahoo.com','2011-12-19 16:34:16','192.168.0.200','2011-12-19 16:37:53');
INSERT INTO `jo_authentication` VALUES ('6099d4f67fe5442199e1d45b50740104',1,'admin','admin@yahoo.com','2011-12-19 15:50:31','192.168.0.1','2011-12-19 16:20:32');
INSERT INTO `jo_authentication` VALUES ('672d02990c32497da9e0ea63e2433f58',1,'admin','admin@yahoo.com','2011-12-19 15:40:16','192.168.0.144','2011-12-19 16:27:29');
INSERT INTO `jo_authentication` VALUES ('6caeed454b8446a286f26809e69c2294',1,'admin','admin@yahoo.com','2011-12-19 13:17:25','192.168.0.144','2011-12-19 13:28:21');
INSERT INTO `jo_authentication` VALUES ('6f67ee1ed77342c69e142f93a6268035',1,'admin','admin@yahoo.com','2011-12-19 09:08:05','0:0:0:0:0:0:0:1','2011-12-19 09:08:59');
INSERT INTO `jo_authentication` VALUES ('723c693bff1a4cb9ac94cc91ed2ccbe7',1,'admin','admin@yahoo.com','2011-12-19 12:01:37','192.168.0.121','2011-12-19 12:04:58');
INSERT INTO `jo_authentication` VALUES ('72aaa7bf12724b80b31e136642348e42',1,'admin','admin@yahoo.com','2011-12-19 17:37:31','192.168.0.185','2011-12-19 17:40:25');
INSERT INTO `jo_authentication` VALUES ('7ab249d7e77d44169bff20285361ed21',1,'admin','admin@yahoo.com','2011-12-19 08:36:39','192.168.0.200','2011-12-19 08:37:19');
INSERT INTO `jo_authentication` VALUES ('84a1e50c90af42a78e91abb656bfd371',1,'admin','admin@yahoo.com','2011-12-19 18:12:14','127.0.0.1','2011-12-19 18:16:17');
INSERT INTO `jo_authentication` VALUES ('85786a1bffb24295bca078d804378cc3',1,'admin','admin@yahoo.com','2011-12-19 17:08:50','192.168.0.115','2011-12-19 17:38:51');
INSERT INTO `jo_authentication` VALUES ('88f64c3344094570be9b5b1a75613392',1,'admin','admin@yahoo.com','2011-12-17 17:00:08','192.168.0.200','2011-12-17 17:27:35');
INSERT INTO `jo_authentication` VALUES ('8bd0c927ad344a27ba8adda347be3d36',1,'admin','admin@yahoo.com','2011-12-19 12:07:37','0:0:0:0:0:0:0:1','2011-12-19 12:08:37');
INSERT INTO `jo_authentication` VALUES ('90907f898cae4adf80bcb9c43fe5c1b9',1,'admin','admin@yahoo.com','2011-12-19 14:07:57','192.168.0.200','2011-12-19 14:34:00');
INSERT INTO `jo_authentication` VALUES ('91a0a99b3a2a424e9a1525b1449932e6',1,'admin','admin@yahoo.com','2011-12-19 17:38:54','192.168.0.115','2011-12-19 17:38:54');
INSERT INTO `jo_authentication` VALUES ('94b94130b24a4f44820cb03f332465b6',1,'admin','admin@yahoo.com','2011-12-19 17:38:50','192.168.0.115','2011-12-19 17:38:53');
INSERT INTO `jo_authentication` VALUES ('954e7a40edb64f83a245868ba3eaa426',1,'admin','admin@yahoo.com','2011-12-19 14:58:11','192.168.0.115','2011-12-19 16:38:48');
INSERT INTO `jo_authentication` VALUES ('981731afb8c449fabbf884c76da6a66b',1,'admin','admin@yahoo.com','2011-12-19 13:34:53','192.168.0.144','2011-12-19 14:35:02');
INSERT INTO `jo_authentication` VALUES ('9834c374e79e4024ae99862bbfc63eb2',1,'admin','admin@yahoo.com','2011-12-19 16:53:45','127.0.0.1','2011-12-19 17:39:46');
INSERT INTO `jo_authentication` VALUES ('a3739df498c5458a80676fca28888d88',1,'admin','admin@yahoo.com','2011-12-18 15:41:49','192.168.0.200','2011-12-18 16:16:40');
INSERT INTO `jo_authentication` VALUES ('acc5dc24491e4b6493500a0ffb7bc686',1,'admin','admin@yahoo.com','2011-12-19 15:44:19','192.168.0.200','2011-12-19 16:00:18');
INSERT INTO `jo_authentication` VALUES ('b12ebec7d0f74e8da9d401788015003e',1,'admin','admin@yahoo.com','2011-12-18 14:10:20','192.168.0.200','2011-12-18 15:21:14');
INSERT INTO `jo_authentication` VALUES ('b99553f3eeec4b4b936ae40b58f303cc',1,'admin','admin@yahoo.com','2011-12-19 13:34:19','192.168.0.200','2011-12-19 14:34:37');
INSERT INTO `jo_authentication` VALUES ('bd49bfb58aa04951bdc8cc6e3bc0d092',1,'admin','admin@yahoo.com','2011-12-19 10:56:05','0:0:0:0:0:0:0:1','2011-12-19 11:22:14');
INSERT INTO `jo_authentication` VALUES ('bf15996e5d6749468cbd0b62c01f4a5e',1,'admin','admin@yahoo.com','2011-12-19 12:09:06','192.168.0.115','2011-12-19 12:16:11');
INSERT INTO `jo_authentication` VALUES ('c3a4da58baf146498e07267678d497f1',1,'admin','admin@yahoo.com','2011-12-19 17:59:29','192.168.0.173','2011-12-19 18:00:20');
INSERT INTO `jo_authentication` VALUES ('c8a84c2b0be543628bc56c3546fbcd17',1,'admin','admin@yahoo.com','2011-12-19 17:38:51','192.168.0.225','2011-12-19 17:40:29');
INSERT INTO `jo_authentication` VALUES ('cf6b73ab98fd427dba624d65722ea212',1,'admin','admin@yahoo.com','2011-12-19 17:43:54','192.168.0.115','2011-12-19 18:13:58');
INSERT INTO `jo_authentication` VALUES ('d436678eefb144f09b01c71ab8e4f52d',1,'admin','admin@yahoo.com','2011-12-17 16:46:59','192.168.0.200','2011-12-17 17:38:57');
INSERT INTO `jo_authentication` VALUES ('d43b58a0f7ec4e73bdf44ced55788cd5',1,'admin','admin@yahoo.com','2011-12-19 14:36:25','192.168.0.200','2011-12-19 16:01:30');
INSERT INTO `jo_authentication` VALUES ('d4c769767a3e4d249394fc2845b96f62',1,'admin','admin@yahoo.com','2011-12-17 16:54:36','192.168.0.1','2011-12-17 17:32:45');
INSERT INTO `jo_authentication` VALUES ('daee3bfe37c6423594517a4d75b24414',1,'admin','admin@yahoo.com','2011-12-19 14:42:12','192.168.0.173','2011-12-19 14:42:59');
INSERT INTO `jo_authentication` VALUES ('e09dc6cf71e7490fa719a06e50036127',1,'admin','admin@yahoo.com','2011-12-19 14:37:50','192.168.0.200','2011-12-19 14:38:48');
INSERT INTO `jo_authentication` VALUES ('e2eb6d2f3f284a788f97610c70ed2fe9',1,'admin','admin@yahoo.com','2011-12-19 12:07:15','192.168.0.1','2011-12-19 12:07:57');
INSERT INTO `jo_authentication` VALUES ('e40ccaad98be4d6189e865e902139069',1,'admin','admin@yahoo.com','2011-12-19 10:59:45','192.168.0.144','2011-12-19 12:04:07');
INSERT INTO `jo_authentication` VALUES ('eae8b0effdca4749ab223861eadad1c2',1,'admin','admin@yahoo.com','2011-12-19 13:35:58','192.168.0.200','2011-12-19 14:35:12');
INSERT INTO `jo_authentication` VALUES ('eccd28699373486a973062fc4be2c355',1,'admin','admin@yahoo.com','2011-12-19 10:24:23','192.168.0.200','2011-12-19 10:53:07');
INSERT INTO `jo_authentication` VALUES ('ee4911e3e6be4cf2bcd68afab1b798cf',1,'admin','admin@yahoo.com','2011-12-19 16:14:40','192.168.0.121','2011-12-19 16:16:38');
INSERT INTO `jo_authentication` VALUES ('efb50d17ccad49b4a1416405f493de16',1,'admin','admin@yahoo.com','2011-12-19 17:38:55','192.168.0.115','2011-12-19 17:41:06');
INSERT INTO `jo_authentication` VALUES ('f42b437a0e9a435a9d2c768bd763e0dd',1,'admin','admin@yahoo.com','2011-12-19 09:23:23','192.168.0.144','2011-12-19 10:27:58');
INSERT INTO `jo_authentication` VALUES ('f7bb29c825884459bb50e2bfce862c25',1,'admin','admin@yahoo.com','2011-12-19 13:13:13','192.168.0.200','2011-12-19 13:24:56');
INSERT INTO `jo_authentication` VALUES ('ff9dba96c7604a15aaa2031a607b56be',1,'admin','admin@yahoo.com','2011-12-19 13:44:55','192.168.0.115','2011-12-19 14:20:35');
/*!40000 ALTER TABLE `jo_authentication` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_config
#

DROP TABLE IF EXISTS `jo_config`;
CREATE TABLE `jo_config` (
  `cfg_key` varchar(50) NOT NULL COMMENT '配置KEY',
  `cfg_value` varchar(255) default NULL COMMENT '配置VALUE',
  PRIMARY KEY  (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

#
# Dumping data for table jo_config
#

LOCK TABLES `jo_config` WRITE;
/*!40000 ALTER TABLE `jo_config` DISABLE KEYS */;
INSERT INTO `jo_config` VALUES ('email_encoding','');
INSERT INTO `jo_config` VALUES ('email_host','smtp.163.com');
INSERT INTO `jo_config` VALUES ('email_password','1');
INSERT INTO `jo_config` VALUES ('email_personal','');
INSERT INTO `jo_config` VALUES ('email_port',NULL);
INSERT INTO `jo_config` VALUES ('email_username','jeecms@163.com');
INSERT INTO `jo_config` VALUES ('login_error_interval','30');
INSERT INTO `jo_config` VALUES ('login_error_times','3');
INSERT INTO `jo_config` VALUES ('message_subject','JEECMS会员密码找回信息');
INSERT INTO `jo_config` VALUES ('message_text','感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost/member/password_reset.jspx?uid=${uid}&key=${resetKey}\r\n');
/*!40000 ALTER TABLE `jo_config` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_ftp
#

DROP TABLE IF EXISTS `jo_ftp`;
CREATE TABLE `jo_ftp` (
  `ftp_id` int(11) NOT NULL auto_increment,
  `ftp_name` varchar(100) NOT NULL COMMENT '名称',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `port` int(11) NOT NULL default '21' COMMENT '端口号',
  `username` varchar(100) default NULL COMMENT '登录名',
  `password` varchar(100) default NULL COMMENT '登陆密码',
  `encoding` varchar(20) NOT NULL default 'UTF-8' COMMENT '编码',
  `timeout` int(11) default NULL COMMENT '超时时间',
  `ftp_path` varchar(255) default NULL COMMENT '路径',
  `url` varchar(255) NOT NULL COMMENT '访问URL',
  PRIMARY KEY  (`ftp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP表';

#
# Dumping data for table jo_ftp
#

LOCK TABLES `jo_ftp` WRITE;
/*!40000 ALTER TABLE `jo_ftp` DISABLE KEYS */;
/*!40000 ALTER TABLE `jo_ftp` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_template
#

DROP TABLE IF EXISTS `jo_template`;
CREATE TABLE `jo_template` (
  `tpl_name` varchar(150) NOT NULL COMMENT '模板名称',
  `tpl_source` longtext COMMENT '模板内容',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `is_directory` tinyint(1) NOT NULL default '0' COMMENT '是否目录',
  PRIMARY KEY  (`tpl_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';

#
# Dumping data for table jo_template
#

LOCK TABLES `jo_template` WRITE;
/*!40000 ALTER TABLE `jo_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `jo_template` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_upload
#

DROP TABLE IF EXISTS `jo_upload`;
CREATE TABLE `jo_upload` (
  `filename` varchar(150) NOT NULL COMMENT '文件名',
  `length` int(11) NOT NULL COMMENT '文件大小(字节)',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `content` longblob NOT NULL COMMENT '内容',
  PRIMARY KEY  (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传附件表';

#
# Dumping data for table jo_upload
#

LOCK TABLES `jo_upload` WRITE;
/*!40000 ALTER TABLE `jo_upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `jo_upload` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table jo_user
#

DROP TABLE IF EXISTS `jo_user`;
CREATE TABLE `jo_user` (
  `user_id` int(11) NOT NULL auto_increment COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `reset_key` char(32) default NULL COMMENT '重置密码KEY',
  `reset_pwd` varchar(10) default NULL COMMENT '重置密码VALUE',
  `error_time` datetime default NULL COMMENT '登录错误时间',
  `error_count` int(11) NOT NULL default '0' COMMENT '登录错误次数',
  `error_ip` varchar(50) default NULL COMMENT '登录错误IP',
  `activation` tinyint(1) NOT NULL default '1' COMMENT '激活状态',
  `activation_code` char(32) default NULL COMMENT '激活码',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Dumping data for table jo_user
#

LOCK TABLES `jo_user` WRITE;
/*!40000 ALTER TABLE `jo_user` DISABLE KEYS */;
INSERT INTO `jo_user` VALUES (1,'admin','admin@yahoo.com','5f4dcc3b5aa765d61d8327deb882cf99','2011-01-03','127.0.0.1','2011-12-19 18:12:14','127.0.0.1',147,NULL,NULL,NULL,0,NULL,1,NULL);
INSERT INTO `jo_user` VALUES (5,'test','test@163.com','098f6bcd4621d373cade4e832627b4f6','2011-12-19 15:26:02','192.168.0.173','2011-12-19 15:26:02','192.168.0.173',0,NULL,NULL,NULL,0,NULL,1,NULL);
/*!40000 ALTER TABLE `jo_user` ENABLE KEYS */;
UNLOCK TABLES;

#
#  Foreign keys for table jc_acquisition
#

ALTER TABLE `jc_acquisition`
ADD CONSTRAINT `fk_jc_acquisition_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_acquisition_contenttype` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
ADD CONSTRAINT `fk_jc_acquisition_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_acquisition_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_acquisition_history
#

ALTER TABLE `jc_acquisition_history`
ADD CONSTRAINT `fk_jc_history_acquisition` FOREIGN KEY (`acquisition_id`) REFERENCES `jc_acquisition` (`acquisition_id`);

#
#  Foreign keys for table jc_acquisition_temp
#

ALTER TABLE `jc_acquisition_temp`
ADD CONSTRAINT `fk_jc_temp_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_advertising
#

ALTER TABLE `jc_advertising`
ADD CONSTRAINT `fk_jc_advertising_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_space_advertising` FOREIGN KEY (`adspace_id`) REFERENCES `jc_advertising_space` (`adspace_id`);

#
#  Foreign keys for table jc_advertising_attr
#

ALTER TABLE `jc_advertising_attr`
ADD CONSTRAINT `fk_jc_params_advertising` FOREIGN KEY (`advertising_id`) REFERENCES `jc_advertising` (`advertising_id`);

#
#  Foreign keys for table jc_advertising_space
#

ALTER TABLE `jc_advertising_space`
ADD CONSTRAINT `fk_jc_adspace_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_channel
#

ALTER TABLE `jc_channel`
ADD CONSTRAINT `fk_jc_channel_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`),
ADD CONSTRAINT `fk_jc_channel_parent` FOREIGN KEY (`parent_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_channel_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_channel_attr
#

ALTER TABLE `jc_channel_attr`
ADD CONSTRAINT `fk_jc_attr_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_channel_ext
#

ALTER TABLE `jc_channel_ext`
ADD CONSTRAINT `fk_jc_ext_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_channel_txt
#

ALTER TABLE `jc_channel_txt`
ADD CONSTRAINT `fk_jc_txt_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_channel_user
#

ALTER TABLE `jc_channel_user`
ADD CONSTRAINT `fk_jc_channel_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_chnl_group_contri
#

ALTER TABLE `jc_chnl_group_contri`
ADD CONSTRAINT `fk_jc_channel_group_c` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_channel_c` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_chnl_group_view
#

ALTER TABLE `jc_chnl_group_view`
ADD CONSTRAINT `fk_jc_channel_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_channel_v` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_comment
#

ALTER TABLE `jc_comment`
ADD CONSTRAINT `fk_jc_comment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_comment_reply` FOREIGN KEY (`reply_user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_comment_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_comment_user` FOREIGN KEY (`comment_user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_comment_ext
#

ALTER TABLE `jc_comment_ext`
ADD CONSTRAINT `fk_jc_ext_comment` FOREIGN KEY (`comment_id`) REFERENCES `jc_comment` (`comment_id`);

#
#  Foreign keys for table jc_config_attr
#

ALTER TABLE `jc_config_attr`
ADD CONSTRAINT `fk_jc_attr_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`);

#
#  Foreign keys for table jc_content
#

ALTER TABLE `jc_content`
ADD CONSTRAINT `fk_jc_contentchannel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
ADD CONSTRAINT `fk_jc_content_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_content_type` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
ADD CONSTRAINT `fk_jc_content_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_content_attachment
#

ALTER TABLE `jc_content_attachment`
ADD CONSTRAINT `fk_jc_attachment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_attr
#

ALTER TABLE `jc_content_attr`
ADD CONSTRAINT `fk_jc_attr_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_channel
#

ALTER TABLE `jc_content_channel`
ADD CONSTRAINT `fk_jc_channel_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_content_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_content_check
#

ALTER TABLE `jc_content_check`
ADD CONSTRAINT `fk_jc_check_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_count
#

ALTER TABLE `jc_content_count`
ADD CONSTRAINT `fk_jc_count_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_ext
#

ALTER TABLE `jc_content_ext`
ADD CONSTRAINT `fk_jc_ext_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_group_view
#

ALTER TABLE `jc_content_group_view`
ADD CONSTRAINT `fk_jc_content_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
ADD CONSTRAINT `fk_jc_group_content_v` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_picture
#

ALTER TABLE `jc_content_picture`
ADD CONSTRAINT `fk_jc_picture_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_topic
#

ALTER TABLE `jc_content_topic`
ADD CONSTRAINT `fk_jc_content_topic` FOREIGN KEY (`topic_id`) REFERENCES `jc_topic` (`topic_id`),
ADD CONSTRAINT `fk_jc_topic_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_content_txt
#

ALTER TABLE `jc_content_txt`
ADD CONSTRAINT `fk_jc_txt_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_contenttag
#

ALTER TABLE `jc_contenttag`
ADD CONSTRAINT `fk_jc_content_tag` FOREIGN KEY (`tag_id`) REFERENCES `jc_content_tag` (`tag_id`),
ADD CONSTRAINT `fk_jc_tag_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`);

#
#  Foreign keys for table jc_friendlink
#

ALTER TABLE `jc_friendlink`
ADD CONSTRAINT `fk_jc_ctg_friendlink` FOREIGN KEY (`friendlinkctg_id`) REFERENCES `jc_friendlink_ctg` (`friendlinkctg_id`),
ADD CONSTRAINT `fk_jc_friendlink_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_friendlink_ctg
#

ALTER TABLE `jc_friendlink_ctg`
ADD CONSTRAINT `fk_jc_friendlinkctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_guestbook
#

ALTER TABLE `jc_guestbook`
ADD CONSTRAINT `fk_jc_ctg_guestbook` FOREIGN KEY (`guestbookctg_id`) REFERENCES `jc_guestbook_ctg` (`guestbookctg_id`),
ADD CONSTRAINT `fk_jc_guestbook_admin` FOREIGN KEY (`admin_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_guestbook_member` FOREIGN KEY (`member_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_guestbook_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_guestbook_ctg
#

ALTER TABLE `jc_guestbook_ctg`
ADD CONSTRAINT `fk_jc_guestbookctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_guestbook_ext
#

ALTER TABLE `jc_guestbook_ext`
ADD CONSTRAINT `fk_jc_ext_guestbook` FOREIGN KEY (`guestbook_id`) REFERENCES `jc_guestbook` (`guestbook_id`);

#
#  Foreign keys for table jc_keyword
#

ALTER TABLE `jc_keyword`
ADD CONSTRAINT `fk_jc_keyword_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_log
#

ALTER TABLE `jc_log`
ADD CONSTRAINT `fk_jc_log_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_log_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_message
#

ALTER TABLE `jc_message`
ADD CONSTRAINT `fk_jc_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_model_item
#

ALTER TABLE `jc_model_item`
ADD CONSTRAINT `fk_jc_item_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`);

#
#  Foreign keys for table jc_receiver_message
#

ALTER TABLE `jc_receiver_message`
ADD CONSTRAINT `fk_jc_receiver_message_message` FOREIGN KEY (`msg_id`) REFERENCES `jc_message` (`msg_id`),
ADD CONSTRAINT `fk_jc_receiver_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_jc_receiver_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_receiver_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_role
#

ALTER TABLE `jc_role`
ADD CONSTRAINT `fk_jc_role_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_role_permission
#

ALTER TABLE `jc_role_permission`
ADD CONSTRAINT `fk_jc_permission_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`);

#
#  Foreign keys for table jc_site
#

ALTER TABLE `jc_site`
ADD CONSTRAINT `fk_jc_site_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`),
ADD CONSTRAINT `fk_jc_site_upload_ftp` FOREIGN KEY (`ftp_upload_id`) REFERENCES `jo_ftp` (`ftp_id`);

#
#  Foreign keys for table jc_site_attr
#

ALTER TABLE `jc_site_attr`
ADD CONSTRAINT `fk_jc_attr_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_site_cfg
#

ALTER TABLE `jc_site_cfg`
ADD CONSTRAINT `fk_jc_cfg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_site_flow
#

ALTER TABLE `jc_site_flow`
ADD CONSTRAINT `fk_jc_flow_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_site_txt
#

ALTER TABLE `jc_site_txt`
ADD CONSTRAINT `fk_jc_txt_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_topic
#

ALTER TABLE `jc_topic`
ADD CONSTRAINT `fk_jc_topic_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`);

#
#  Foreign keys for table jc_user
#

ALTER TABLE `jc_user`
ADD CONSTRAINT `fk_jc_user_group` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`);

#
#  Foreign keys for table jc_user_collection
#

ALTER TABLE `jc_user_collection`
ADD CONSTRAINT `fk_jc_user_collection_con` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
ADD CONSTRAINT `fk_jc_user_collection_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_user_ext
#

ALTER TABLE `jc_user_ext`
ADD CONSTRAINT `fk_jc_ext_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`);

#
#  Foreign keys for table jc_user_role
#

ALTER TABLE `jc_user_role`
ADD CONSTRAINT `fk_jc_role_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`);

#
#  Foreign keys for table jc_user_site
#

ALTER TABLE `jc_user_site`
ADD CONSTRAINT `fk_jc_site_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_user_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

#
#  Foreign keys for table jc_vote_item
#

ALTER TABLE `jc_vote_item`
ADD CONSTRAINT `fk_jc_vote_item_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`);

#
#  Foreign keys for table jc_vote_record
#

ALTER TABLE `jc_vote_record`
ADD CONSTRAINT `fk_jc_voterecord_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
ADD CONSTRAINT `fk_jc_vote_record_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`);

#
#  Foreign keys for table jc_vote_topic
#

ALTER TABLE `jc_vote_topic`
ADD CONSTRAINT `fk_jc_votetopic_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);


/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
