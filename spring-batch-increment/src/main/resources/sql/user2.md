getEdgeUser
===
*查询user数据

select * from test_user1

getEdgeIncreUser
===
* 查询user数据

select * from test_user1
WHERE 1=1
@if(!isEmpty(lastUpdateTime)){
AND (sys_create_time >= #lastUpdateTime# OR sys_update_time >= #lastUpdateTime#)
@}

getTargetEdgeIncreUser
===
* 查询user数据

select * from test_user1
WHERE 1=1
@if(!isEmpty(lastUpdateTime)){
AND (sys_create_time >= #lastUpdateTime# OR sys_update_time >= #lastUpdateTime#)
@}

insertEdgeIncreUser
===
* 插入数据

insert into test_user1(id,name,phone,
@if(!isEmpty(title)){
title,
@}
email,gender,date_of_birth
,sys_create_time,sys_create_user,sys_update_time,sys_update_user)
values (#id#,#name#,#phone#
@if(!isEmpty(title)){
,#title#
@}
,#email#,#gender#,#dateOfBirth#
,#sysCreateTime#,#sysCreateUser#,#sysUpdateTime#,#sysUpdateUser#)
ON DUPLICATE KEY UPDATE
id = VALUES(id),
name = VALUES(name),
phone = VALUES(phone),
@if(!isEmpty(title)){
title = VALUES(title),
@}
email = VALUES(email),
gender = VALUES(gender),
date_of_birth = VALUES(date_of_birth),
sys_create_time = VALUES(sys_create_time),
sys_create_user = VALUES(sys_create_user),
sys_update_time = VALUES(sys_update_time),
sys_update_user = VALUES(sys_update_user)

insertTargetIncreUser
===
* 插入数据

insert into test_user1(id,name,phone,
@if(!isEmpty(title)){
title,
@}
email,gender,date_of_birth
,sys_create_time,sys_create_user,sys_update_time,sys_update_user)
values (#id#,#name#,#phone#
@if(!isEmpty(title)){
,#title#
@}
,#email#,#gender#,#dateOfBirth#
,#sysCreateTime#,#sysCreateUser#,#sysUpdateTime#,#sysUpdateUser#)
ON DUPLICATE KEY UPDATE
id = VALUES(id),
name = VALUES(name),
phone = VALUES(phone),
@if(!isEmpty(title)){
title = VALUES(title),
@}
email = VALUES(email),
gender = VALUES(gender),
date_of_birth = VALUES(date_of_birth),
sys_create_time = VALUES(sys_create_time),
sys_create_user = VALUES(sys_create_user),
sys_update_time = VALUES(sys_update_time),
sys_update_user = VALUES(sys_update_user)

insertUser
===
* 插入数据

insert into test_user1(id,name,phone,title,email,gender,date_of_birth
,sys_create_time,sys_create_user,sys_update_time,sys_update_user)
values (#id#,#name#,#phone#,#title#,#email#,#gender#,#dateOfBirth#
,#sysCreateTime#,#sysCreateUser#,#sysUpdateTime#,#sysUpdateUser#)
