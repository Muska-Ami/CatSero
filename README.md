![Logo](https://repository-images.githubusercontent.com/460782323/eee698e3-0952-472d-96d2-d08c784c0cc2)

# 学业繁忙，维护推缓

如果我没有回你大概是在学习

# CatSero `v2`

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/babcf1e300a44e3684e88840e2b2b803)](https://www.codacy.com/gh/XiaMoHuaHuo-CN/CatSero/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=XiaMoHuaHuo-CN/CatSero&amp;utm_campaign=Badge_Grade)[![Java CI with Maven - Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml/badge.svg?branch=main)](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)  
一个基于MiraiMC的QQ群功能&MC功能插件  
支持的Minecraft版本: 1.8+ **注意: 基于Spigot-1.13.2 API开发，不保证更低版本可用性**  
开发版构建结果请前往Actions - [Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)

- 发布页
    - MCBBS发布页：[https://www.mcbbs.net/thread-1342417-1-1.html](https://www.mcbbs.net/thread-1342417-1-1.html)
    - KLPBBS发布页：[https://klpbbs.com/thread-43498-1-1.html](https://klpbbs.com/thread-43498-1-1.html)

### v1使用文档

请见此处 [https://github.com/XiaMoHuaHuo-CN/CatSero/blob/v1/README.md](https://github.com/XiaMoHuaHuo-CN/CatSero/blob/v1/README.md)  
**v1不受支持**

## 都有什么功能？

- 玩家死亡转发
- 聊天转发兼容TrChat
- QQ群-Minecraft消息互转
- 加入/退出转发权限控制
- 玩家加入退出转发
- 迎新功能
- PlaceholderAPI变量支持
- 获取TPS
- 获取在线玩家
- 管理在线玩家
- (实验性)以控制台身份执行Minecraft命令
- (实验性,Actions)白名单

## 配置文件

#### config.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# CatSero配置文件

# 语言文件
locale: zh_CN
# 接受bStats统计(https://bstats.org)
# true | false
allow-bstats: true

# 自定义命令头
qq-command-prefix:
  # 功能开关
  # true | false
  enabled: false
  # 格式
  format: ""
```

</details>

#### uses-config.yml

<details>
<summary>点此展开/收起</summary>

```yaml
# CatSero功能设置

# 发送玩家加入/退出消息
send-player-join-quit:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
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

# 聊天转发
chat-forward:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
  # 格式
  # 内置占位符:
  # - %name%  (To MC)发送者名称
  # - %code%  (To MC)发送者QQ号
  # - %message%  消息内容
  # - %player%  (To QQ)发送玩家名称
  # - %channel%  (To QQ | TrChat Only)聊天频道ID
  format:
    # 发送到Minecraft
    to-mc: |-
      &e[&aQQ&e]&r%name%(%code%):
      %message%
    # 发送到QQ
    to-qq: |-
      [MC]%player%:
      %message%
  # 清理消息中的格式代码
  clean-colorcode: true
  # 关键词检测
  filter:
    # 功能开关
    # true | false
    enabled: false
    # 关键词列表
    list:
      - "傻逼"
      - "fuck"
    # 只将关键词变为"***"而不是取消该条消息的转发
    # true | false
    replace-only: false
  # 聊天前缀
  prefix:
    # 功能开关
    # true | false
    enabled: false
    # 格式
    format:
      # 发送到Minecraft
      to-mc: "#"
      # 发送到QQ
      to-qq: "#"

# 发送玩家死亡消息
send-player-death:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
  # 格式
  # 内置占位符:
  # - %player%  玩家名
  # - %deathmes%  死亡消息
  format: "%player%死了,因为\n%deathmes%"

# 新人加入群欢迎
new-group-member-message:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
  # 格式
  # 内置占位符:
  # - %at%  @新成员
  # - %code%  新成员QQ号
  format: "欢迎%at%（%code%）加入本群!"
  
# TPS获取
get-tps:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example

# 在线玩家获取
get-online-players:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
  # 格式
  # 内置占位符:
  # - %count%  当前在线玩家数
  # - %max%  最大在线玩家数
  # - %list%  当前在线玩家列表
  format: |-
    当前在线: %count%
    最大在线: %max%
    玩家列表: %list%

# 玩家管理器
player-manager:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
  # 启用的工具
  # 支持：ban, op, kick
  tools:
    - ban
    - op
    - kick
  # 子功能配置
  configs:
    # Ban工具
    ban-tool:
      # Ban命令
      ban:
        # 默认原因
        default-reason: "你已被此服务器封禁"
        # 使用自定义命令而不是Bukkit内置封禁
        custom-command:
          # 功能开关
          # true | false
          enabled: false
          # 封禁命令
          # 内置占位符:
          # - %player%  玩家名
          # - %reason%  原因
          command: "ban %player% %reason%"
      # UnBan命令
      unban:
        # 使用自定义命令而不是Bukkit内置封禁
        custom-command:
          # 功能开关
          # true | false
          enabled: false
          # 封禁命令
          # 内置占位符:
          # - %player%  玩家名
          command: "pardon %player%"
    # Kick工具
    kick-tool:
      # Kick命令
      kick:
        # 默认原因
        default-reason: "你已被踢出"

# QQ群执行Minecraft命令
# 正在实验
dispatch-command:
  # 功能开关
  # true | false
  enabled: false
  # Bot & Group设置
  var:
    # BotID
    bot: example
    # GroupID
    group: example
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
  example: 123456789
  example2: 123456789
```

list下的`example`与`example2`即为BotID  
创建格式为`<id>: <Bot QQ号>`  
您可以新建若干Bot，例如，让我们把文件改为

```yaml
list:
  hello-bot: 123456789
```

### Group配置指南

Group配置位于`mirai-configs/group.yml`  
首次打开，您应该会看到如下内容

```yaml
list:
  example: 123456789
  example2: 123456789
```

list下的`example`与`example2`即为GroupID  
创建格式为`<id>: <群号>`  
您可以新建若干Group，例如，让我们把文件改为

```yaml
list:
  hello-group: 123456789
```

### QQOp配置指南

QQOp配置位于`mirai-configs/qq-op.yml`  
您只需要按照YAML数组格式添加用户QQ号即可
</details>

##### 使用指南

<details>
<summary>点此展开/收起</summary>

打开`uses-config.yml`，您应该会发现每个功能下会有一个`var`:

```yaml
example:
  var:
    bot: example
    group: example
```

`var`内的`bot`即为Bot配置中的BotID
同理`group`即为Group配置中的GroupID
</details>
</details>

## 命令

注：以`*`开头的为开发版本特性

### Minecraft

| 命令                                  | 说明       |
|-------------------------------------|----------|
| /catsero version                    | 插件版本以及信息 |
| /catsero reload                     | 重载配置文件   |
| /cms \<message> \<BotID> \<GroupID> | 发送群消息    |

### QQ

_要触发命令前必须使用前缀`!`或`/`_

| 命令                                  | 说明                  |
|-------------------------------------|---------------------|
| !catsero tps round                  | 获取TPS(概数)           |
| !catsero tps accurate               | 获取TPS(精确)           |
| !catsero list                       | 列出服务器上的所有玩家         |
| !catsero pm ban \<player> \(reason) | 封禁一个玩家              |
| !catsero pm unban \<player>         | 解除封禁一名玩家            |
| !catsero pm pardon \<player>        | 解除封禁一名玩家            |
| !catsero pm op \<player>            | 将一名玩家设置为OP          |
| !catsero pm unop \<player>          | 取消一名玩家OP            |
| !catsero pm deop \<player>          | 取消一名玩家OP            |
| !catsero pm kick \<player> (reason) | 踢出一名玩家              |
| !catsero cmd \<command>             | 以控制台身份执行Minecraft命令 |

# bStats

<a href="https://bstats.org/plugin/bukkit/CatSero/14767">![https://bstats.org/plugin/bukkit/CatSero/14767](https://bstats.org/signatures/bukkit/CatSero.svg)</a>

## 权限

| 权限                                 | 说明                         |
|------------------------------------|----------------------------|
| catsero.*                          | 所有权限，默认无                   |
| catsero.admin                      | 管理权限，默认OP                  |
| catsero.send-player-join-quit      | 玩家加入/退出转发权限，默认无            |
| catsero.send-player-join-quit.join | 玩家加入游戏转发权限，默认OP            |
| catsero.send-player-join-quit.quit | 玩家退出游戏转发权限，默认OP            |
| catsero.cms                        | 使用CMS命令权限，默认无              |
