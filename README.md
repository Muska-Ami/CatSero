![Logo](https://repository-images.githubusercontent.com/460782323/eee698e3-0952-472d-96d2-d08c784c0cc2)

# CatSero `v2`

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/babcf1e300a44e3684e88840e2b2b803)](https://www.codacy.com/gh/XiaMoHuaHuo-CN/CatSero/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=XiaMoHuaHuo-CN/CatSero&amp;utm_campaign=Badge_Grade)[![Java CI with Maven - Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml/badge.svg?branch=main)](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)  
一个基于MiraiMC的QQ群功能&MC功能插件  
支持的Minecraft版本: 1.8+ **注意: 基于Spigot-1.13.2 API开发，不保证更低版本可用性**  
开发版构建结果请前往Actions - [Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)

- 发布页
    - MCBBS发布页：[https://www.mcbbs.net/thread-1342417-1-1.html](https://www.mcbbs.net/thread-1342417-1-1.html)
    - KLPBBS发布页：[https://klpbbs.com/thread-43498-1-1.html](https://klpbbs.com/thread-43498-1-1.html)

## Tip

本插件会覆盖JSON解析库 `com.alibaba.fastjson` `com.alibaba.fastjson2` 等FastJSON的相关类

### v1使用文档

请见此处 [https://github.com/XiaMoHuaHuo-CN/CatSero/blob/v1/README.md](https://github.com/XiaMoHuaHuo-CN/CatSero/blob/v1/README.md)  
**v1不受支持**

## 功能

- 聊天转发（支持TrChat）
- 玩家死亡转发
- 玩家加入/退出转发
- TPS获取
- 玩家达成进度转发
- 获取在线玩家
- 欢迎新群员
- 白名单

## 配置文件

#### config.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# CatSero Plugin Config
# Generate by CatSero v@plugin.version@

# 语言文件
locale: zh_CN

# bStats
bstats: true

# 检查更新
check-update:
  # 功能开关
  # true | false
  enable: true
  # 检查更新间隔
  # 单位: 秒
  interval: 3600
  # 版本模式
  mode: latest
  # 检查更新服务器API地址，一般情况请勿修改
  api-url: https://mcp.huahuo-cn.tk/api/CatSero/version

# JDBC
jdbc:
  # SQLite JDBC class名称
  sqlite-class-name: "org.sqlite.JDBC"
  # MySQL JDBC class名称
  mysql-class-name: "com.mysql.jdbc.Driver"
  # 数据储存方法
  # sqlite | mysql
  type: sqlite
  # MySQL设置
  mysql-config:
    # 地址
    host: localhost
    # 端口
    port: 3306
    # 用户名
    username: catsero
    # 密码
    password: 123456
    # 数据库名
    database: catsero
    # 时区
    timezone: "Asia/Shanghai"
    # 使用Unicode
    unicode: true
    # 数据库编码格式
    encoding: "UTF-8"
    # 使用SSL连接
    ssl: false

# 自定义QQ命令头
command-prefix:
  # 功能开关
  # true | false
  enable: false
  # 命令头
  prefix: ""
```

</details>

#### uses-config.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# CatSero UsesConfig
# Generate by CatSero v@plugin.version@

# 所有的发送至QQ群的消息都支持mirai码
# 参见：
# https://docs.mirai.mamoe.net/Messages.html#mirai-%E7%A0%81
# https://docs.mirai.mamoe.net/Messages.html#%E6%B6%88%E6%81%AF%E5%85%83%E7%B4%A0
# https://docs.mirai.mamoe.net/Messages.html#%E6%B6%88%E6%81%AF%E9%93%BE%E7%9A%84-mirai-%E7%A0%81

# 聊天转发
chat-forward:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 自动清理样式代码
  clean-stylecode:
    to-mc: false
    to-qq: true
  # 消息头检测
  # 只有消息以这个字符串开头才会被转发
  header:
    # 功能开关
    # true | false
    enable: false
    # 消息头设置
    prefix:
      to-mc: "#"
      to-qq: "#"
  # 允许游戏内玩家使用mirai码
  allow-miraicode: false
  # 过滤器
  # 检测到消息内含有列表中的文本
  filter:
    # 功能开关
    # true | false
    enable: false
    # 列表
    list:
      # 原生的列表
      via:
        to-mc: [ ]
        to-qq: [ ]
      # 从外部导入
      import:
        # 本地
        local: [ ]
        # 远程
        # 此处使用的是TrChat的默认远程源
        # 感谢南城提供的词库
        # CDN JsDelivr
        remote:
          - "https://cdn.jsdelivr.net/gh/Yurinann/Filter-Thesaurus-Cloud@main/database.json"
    # 替换成的字符
    replace: "**"
  # 使用MiraiMC内置绑定数据库查询QQ发言者名称
  use-bind: true
  # 格式
  format:
    # 内置占位符:
    # - %sender_permission%  发言者群权限(member，admin，owner)
    # - %name%  发言者名称
    # - %message%  消息
    to-mc: "&e[&aQQ&e]&e[&d%sender_permission%&e]&b%name%&r: %message%"
    # 内置占位符:
    # - %sender_permission%  发言者权限(player，admin)
    # - %name%  发言者名称
    # - %display_name%  发言者游戏中显示名称
    # - %message%  消息
    to-qq: "[MC][%sender_permission%]%name%: %message%"

# 发送玩家加入/退出消息
send-player-join-quit:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 格式
  # 内置占位符:
  # - %player% 加入玩家名称
  format:
    # 加入
    join: "%player%加入了游戏"
    # 退出
    quit: "%player%退出了游戏"
  # 需要拥有权限才会发送
  need-permission: false

# 发送玩家死亡消息
send-player-death:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 格式
  # 内置占位符:
  # - %player%  玩家名
  # - %message%  死亡消息
  format: "%player%死了,因为\n%message%"
  # 需要拥有权限才会发送
  need-permission: false

# 新人加入群欢迎
new-group-member:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 格式
  # 内置占位符:
  # - %at%  @新成员
  # - %code%  新成员QQ号
  format: "欢迎%at%（%code%）加入本群!"

# 玩家解锁进度转发
send-advancement:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 格式
  # 内置占位符:
  # = %player%  玩家名
  # - %name%  进度名
  # - %description%  进度描述
  format: "%player%达成了进度: %name%\n描述: %description%"
  # 需要拥有权限才会发送
  need-permission: false

# TPS获取
get-tps:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group

# 在线玩家获取
get-online-list:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 格式
  format:
    # 无论是否有玩家在线都会发送
    # 内置占位符:
    # - %count%  当前在线玩家数
    # - %max%  最大在线玩家数
    0: |-
      当前在线: %count%
      最大在线: %max%
    # 当有玩家在线才发送
    # 内置占位符:
    # - %count%  当前在线玩家数
    # - %max%  最大在线玩家数
    # - %list%  当前在线玩家列表
    1: |-
      玩家列表: %list%

# QQ白名单
qwhitelist:
  # 功能开关
  # true | false
  enable: false
  # Bot & Group设置
  var:
    # BotID
    bot: hello-bot
    # GroupID
    group: hello-group
  # 自助申请白名单
  self-application:
    # 功能开关
    # true | false
    enable: false
    # 申请格式
    # 内置占位符:
    # - %name%  玩家名（只能设置一个）
    format: "!申请白名单 %name%"
```

</details>

#### extra-configs/trchat.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# 聊天转发
chat-forward:
  # 需要转发的频道
  channel:
    - Normal
```

</details>

#### extra-configs/command-alias.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# CatSero QQ CommandAlias Config
# Generate by CatSero v@plugin.version@

# 功能开关
# true | false
enable: false

# 别名列表
tps:
  - ctps
list:
  - clist
whitelist:
  - cwl
```

</details>

#### Bot & Group & QQOp配置指南

<details>
<summary>点此展开/收起</summary>

##### 添加指南

<details>
<summary>点此展开/收起</summary>

### Bot配置指南

Bot配置位于`mirai-configs/bot.yml`  
首次打开，您应该会看到如下内容

```yaml
list:
  hello-bot: 123456789
```

list下的`example`与`example2`即为BotID  
创建格式为`<id>: <Bot QQ号>`

### Group配置指南

Group配置位于`mirai-configs/group.yml`  
首次打开，您应该会看到如下内容

```yaml
list:
  hello-group: 123456789
```

list下的`example`与`example2`即为GroupID  
创建格式为`<id>: <群号>`

### QQOp配置指南

QQOp配置位于`mirai-configs/qq-op.yml`  
首次打开，您应该会看到如下内容

```yaml
list:
  - 123456789
```

您只需要按照YAML数组格式添加用户QQ号即可
</details>

##### 使用指南

<details>
<summary>点此展开/收起</summary>

打开`uses-config.yml`，您应该会发现每个功能下会有一个`var`:

```yaml
demo-use:
  var:
    bot: hello-bot
    group: hello-group
```

`var`内的`bot`即为Bot配置中的BotID
同理`group`即为Group配置中的GroupID
</details>
</details>

## 命令

注：以`*`开头的为开发版本特性

### Minecraft

| 命令                                  | 说明           |
|-------------------------------------|--------------|
| /catsero version                    | 插件版本以及信息     |
| /catsero reload                     | 重载config.yml |
| /cms \<BotID> \<GroupID> \<message> | 发送群消息        |

### QQ

_要触发命令前必须使用前缀`!`或`/`_

| 命令                                                          | 说明          |
|-------------------------------------------------------------|-------------|
| !catsero tps around                                         | 获取TPS(概数)   |
| !catsero tps accurate                                       | 获取TPS(精确)   |
| !catsero list                                               | 列出服务器上的所有玩家 |
| !catsero whitelist add \<Player>                            | 添加白名单       |
| !catsero whitelist change \<PlayerOldName> \<PlayerNewName> | 更新白名单       |
| !catsero whitelist remove \<Player>                         | 移除白名单       |

<!--
| !catsero pm ban \<player> \(reason) | 封禁一个玩家              |
| !catsero pm unban \<player>         | 解除封禁一名玩家            |
| !catsero pm pardon \<player>        | 解除封禁一名玩家            |
| !catsero pm op \<player>            | 将一名玩家设置为OP          |
| !catsero pm unop \<player>          | 取消一名玩家OP            |
| !catsero pm deop \<player>          | 取消一名玩家OP            |
| !catsero pm kick \<player> (reason) | 踢出一名玩家              |
| !catsero cmd \<command>             | 以控制台身份执行Minecraft命令 |
-->

# bStats

<a href="https://bstats.org/plugin/bukkit/CatSero/14767">![https://bstats.org/plugin/bukkit/CatSero/14767](https://bstats.org/signatures/bukkit/CatSero.svg)</a>

## 权限

| 权限                                 | 说明              |
|------------------------------------|-----------------|
| catsero.*                          | 所有权限，默认无        |
| catsero.admin                      | 管理权限，默认OP       |
| catsero.cms                        | 使用CMS命令权限，默认无   |
| catsero.send-player-join-quit      | 玩家加入/退出转发权限，默认无 |
| catsero.send-player-join-quit.join | 玩家加入游戏转发权限，默认OP |
| catsero.send-player-join-quit.quit | 玩家退出游戏转发权限，默认OP |
| catsero.send-death                 | 玩家死亡转发权限，默认OP   |
