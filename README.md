# avatars
保存 GitHub/V2EX/gravatar identicon “几何图案”风格的头像到本地文件夹。

![20210926162620](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210926162620.png)
# 使用说明

```
java -jar app.jar saveFolder downloadNumber
```

参数说明：

1. `saveFolder`: 保存的文件夹
   1. Windows CMD 或 Powershell 使用 `d:\aaa` 这种形式，Windows GitBash 使用 `/d/aaa` 这种形式，Windows WSL 使用 `/mnt/d/aaa` 这种形式（tips: Windows 路径名不区分大小写）
   2. macOS、Linux 使用 `mnt/d/aaa` 这种形式
2. `downloadNumber`: 下载的头像数量

比如在 Windows 10 cmd 下执行：`java -jar app.jar d:\aaa 10` 将会在 D 盘的 aaa 文件夹下保存 10 张头像文件。

程序只接收这两个参数，有且仅有这两个参数，如果输入错误程序返回以下提示

> Usage: java -jar app.jar saveFolder downloadNumber, example: java -jar app.jar d:\aaa 10

程序说明：

每 2 秒下载一张头像，文件命名风格是 `yyyy-MM-dd.HH-mm-ss.png`，比如 `2021-09-26.14-13-35.png`。

非常感谢 API 提供者提供如此实用的 [API](https://api.prodless.com/avatar.png)

![20210926163201](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210926163201.png)

![20210926163227](https://cdn.jsdelivr.net/gh/hellodk34/image@main/img/20210926163227.png)
