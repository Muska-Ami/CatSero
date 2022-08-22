![Logo](https://repository-images.githubusercontent.com/460782323/9e8de950-9a9b-4063-b180-ac1e3c2c6a14)

# CatSero

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/babcf1e300a44e3684e88840e2b2b803)](https://www.codacy.com/gh/XiaMoHuaHuo-CN/CatSero/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=XiaMoHuaHuo-CN/CatSero&amp;utm_campaign=Badge_Grade)[![Java CI with Maven - Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml/badge.svg?branch=main)](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)  
一个基于MiraiMC的QQ群功能&MC功能插件  
支持的Minecraft版本: 1.8+  
开发版构建结果请前往Actions - [Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)

- 发布页
    - MCBBS发布页：[https://www.mcbbs.net/thread-1342417-1-1.html](https://www.mcbbs.net/thread-1342417-1-1.html)
    - KLPBBS发布页：[https://klpbbs.com/thread-43498-1-1.html](https://klpbbs.com/thread-43498-1-1.html)

## 帮助改进代码

如果你想帮助改进代码，请先Fork本库，然后修改，接着开PR

## 未来功能

注：以`*`开头的为开发版本特性

### 待修复/实现

- [ ] QQ群娱乐功能(遥遥无期，XD，希望有dalao PR一下)
- [ ] 自动回复功能
- [x] QQ群-Minecraft消息互转(异步)

### 已经实现

- [x] 命令Tab补全
- [x] Q群解析Minecraft命令
- [x] QQ群自定义命令头
- [x] QQ命令解析调度器
- [x] 加入/退出转发权限控制
- [x] 自动检查更新
- [x] Minecraft玩家加入/退出通知到QQ
- [x] QQ群Ban人
- [x] Ping功能(Minecraft内/QQ群内)
- [x] QQ群给予OP
- [x] QQ群移除OP
- [x] 天气获取
- [x] 迎新功能
- [x] PlaceholderAPI变量支持
- [x] Punycode功能
- [x] QQ群踢人
- [x] TPS获取功能

## 命令

注：以`*`开头的为开发版本特性  

### Minecraft

| 命令                                 | 说明                           |
|------------------------------------|------------------------------|
| /catsero reload                    | 重载配置文件                       |
| /catsero ping <地址>                 | Ping某一个地址                    |
| /catsero weather <中国大陆城市>          | 获取某个城市的天气                    |
| /catsero punycode <文本> \[urlmode\] | Punycode文本 `[urlmode]`:URL模式 |
| /cms send <消息>                     | 发送群消息                        |
| /cms sendcustom <机器人号> <群号> <消息>  | 使用指定机器人向指定服务器发送消息            |

### QQ

注：
1. 要触发命令前必须使用前缀`!`或`/`
2. 命令头部分可自行定义

| 命令                                | 说明                           |
|-----------------------------------|------------------------------|
| !catsero ping <地址>                 | Ping某一个地址                    |
| !catsero weather <中国大陆城市>          | 获取某个城市的天气                    |
| !catsero setop <玩家名>               | 给予一个玩家OP                     |
| !catsero removeop <玩家名>            | 移除一个玩家OP                     |
| !catsero kick <玩家名>                | 踢出一个在线玩家                     |
| !catsero ban <玩家名>                 | 封禁一个玩家                       |
| !catsero unban <玩家名>               | 解封一个玩家                       |
| !catsero punycode <文本> \[urlmode\] | Punycode文本 `[urlmode]`:URL模式 |
| !catsero tps                       | 获取服务器TPS                     |
| !catsero dispatchcmd <Minecraft命令> | 执行Minecraft命令 `<Minecraft命令>`:请使用`+`代替空格 |

# bStats

<a href="https://bstats.org/plugin/bukkit/CatSero/14767">![https://bstats.org/plugin/bukkit/CatSero/14767](https://bstats.org/signatures/bukkit/CatSero.svg)</a>

# 一点说明

## 为什么pre版本的Releases有时会跳过一个版本?

本项目不会发布所有pre版本的构建，请自行去Actions下载

## 权限

| 权限                                 | 说明               |
|------------------------------------|------------------|
| catsero.*                          | 所有权限，默认无         |
| catsero.admin                      | 管理权限，默认OP        |
| catsero.send-player-join-quit      | 玩家加入/退出转发权限，默认无 |
| catsero.send-player-join-quit.join | 玩家加入游戏转发权限，默认OP  |
| catsero.send-player-join-quit.quit | 玩家退出游戏转发权限，默认OP  |
