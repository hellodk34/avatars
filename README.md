# avatars

gravatar 风格头像快速生成器。基于随机邮箱生成以下类型的头像

- identicon style: a geometric pattern based on an email hash
- retro style: awesome generated, 8-bit arcade-style pixelated faces
- robohash style: a generated robot with different colors, faces, etc

![20220408210043](https://pic.dogimg.com/2022/04/08/62503207ea4c5.png)

# 前言

本项目之前用了一个 [API](https://api.prodless.com/avatar.png) ，但是现在该 API 已经停止服务了。现借助 gravatar 官方的服务写了此程序。个人感觉现在注册的网站越来越多，很多网站注册后默认不提供头像，需要用户自行上传，我经常为找不到合适的头像发愁，每个网站使用相同的头像又觉得泄露了隐私，于是我就产生了这样的需求，生成这些静态文件后保存在本地文件夹中，以后设置头像时随便挑选一个喜欢的即可。

# 使用说明

![20220408211743](https://pic.dogimg.com/2022/04/08/62503603930d3.png)

```
java -jar app.jar saveFolder downloadNumber TYPE
```

参数说明：

1. `SAVE_FOLDER`: 准备保存的文件夹
   1. Windows CMD 或 Powershell 使用 `d:\aaa` 这种形式，Windows GitBash 使用 `/d/aaa` 这种形式，Windows WSL 使用 `/mnt/d/aaa` 这种形式（tips: Windows 路径名不区分大小写）
   2. macOS、Linux 使用 `/mnt/ssd/aaa` 这种形式
2. `DOWNLOAD_NUMBER`: 下载的头像数量
3. `TYPE`: 风格类型，取值有 identicon | retro | robohash

比如在 Windows 10 cmd 下执行：`java -jar d:\app.jar d:\aaa 10 robohash` 将会在 D 盘的 aaa 文件夹下保存 10 张 robohash 风格的头像文件。

程序说明：

每 2 秒下载一张头像，文件命名风格是 `yyyy-MM-dd.HH-mm-ss.png`，比如 `2021-09-26.14-13-35.png`。

目前可选的 Gravatar 服务地址有

- 官方地址: https://www.gravatar.com/avatar/
- 极客族: https://sdn.geekzu.org/avatar/
- loli: https://gravatar.loli.net/avatar/
- V2EX: https://cdn.v2ex.com/gravatar/

**本程序已封装好的 jar 包中使用的是极客族的源（适合国内用户使用）。**

如果使用下来网络超时建议 clone 本项目自行修改程序中 `serverUrl` 的值再编译打包成 jar 包执行 `mvn clean package -Dmaven.test.skip=true -U`。

欢迎使用。
