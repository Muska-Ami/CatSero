![Logo](https://repository-images.githubusercontent.com/460782323/9e8de950-9a9b-4063-b180-ac1e3c2c6a14)

# CatSero

[![Java CI with Maven - Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml/badge.svg?branch=main)](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)  
一个基于MiraiMC的QQ群功能&MC功能插件  
支持的Minecraft版本: 1.8+  
开发版构建结果请前往Actions - [Build](https://github.com/XiaMoHuaHuo-CN/CatSero/actions/workflows/builder.yml)
MCBBS发布页：[https://www.mcbbs.net/thread-1342417-1-1.html](https://www.mcbbs.net/thread-1342417-1-1.html)

## 帮助改进代码

如果你想帮助改进代码，请先Fork本库，然后修改，接着开PR

## 未来功能

### 插件功能

- [x] QQ群-Minecraft消息互转(异步)
- [x] Minecraft玩家加入/退出通知到QQ
- [x] QQ群踢人
- [x] QQ群Ban人
- [ ] QQ群娱乐功能(遥遥无期，XD，希望有dalao PR一下)
- [x] Ping功能(Minecraft内/QQ群内)
- [x] QQ群给予OP
- [x] 天气获取
- [x] 迎新功能
- [x] 自定义玩家加入/退出游戏全体消息
- [x] 多群/多Bot/多群OP
- [x] PlaceholderAPI变量支持
- [ ] 自动回复功能
- [x] PunyCode功能

### 其他

- [x] 自动检查更新

# 命令

### Minecraft

| 命令                                      | 说明                           |
|-----------------------------------------|------------------------------|
| /catsero reload                         | 重载config.yml                 |
| /catsero ping <地址>                      | Ping某一个地址                    |
| /catsero weather <中国大陆城市>               | 获取某个城市的天气                    |
| /catsero punycode <文本> \[urlmode\]      | Punycode文本 \[urlmode\]:URL模式 |
| /csm <Bot账号(Code)> <Group群号(Code)> <消息> | 使用指定Bot发送到指定群指定消息            |

### QQ

| 命令                                | 说明                            |
|-----------------------------------|-------------------------------|
| catsero ping <地址>                 | Ping某一个地址                     |
| catsero weather <中国大陆城市>          | 获取某个城市的天气                     |
| catsero setop <玩家名>               | 给予一个玩家操作员权限                   |
| catsero kick <玩家名>                | 踢出一个在线玩家                      |
| catsero punycode <文本> \[urlmode\] | Punycode文本 \[urlmode\]:URL模式  |

# bStats

<a href="https://bstats.org/plugin/bukkit/CatSero/14767">![https://bstats.org/plugin/bukkit/CatSero/14767](https://bstats.org/signatures/bukkit/CatSero.svg)</a>

# 一点说明

## 为什么pre版本的Releases有时会跳过一个版本?

本项目不会发布所有pre版本的构建，请自行去Actions下载

## 权限

| 权限            | 说明        |
|---------------|-----------|
| catsero.admin | 管理权限，默认OP |
