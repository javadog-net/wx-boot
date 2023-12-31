## 前言
### 🍊缘由
起因于本狗上一个项目本打算采用**微信公众号网页授权登录**做用户鉴权，但最终因公众号是**未认证的订阅号**，**无权限获取用户信息**，所以改变思路，采用登录注册方式实现用户区分。但在开发中，学习了**微信网页授权登录**流程，特此分享，带你手把手操作，让我们一起捋清授权的逻辑。
*******
### ⏲️建议阅读时长
约20分钟
*******
### 🎯主要目标
1. **测试公众号**实现**网页授权**并获取**用户基本信息**
2. **灵活掌握微信接口文档**，熟练对接相关接口
3. 整理授权逻辑，前后端对接熟悉流程
******

### 👨‍🎓试用人群
1. 对于**微信公众号网页授权**流程模糊或初次接触
2. 做过相关流程但想回顾熟悉复盘
******
### 🎁快速链接
公众号：**JavaDog程序狗**
**在公众号，发送【wx】 ，无任何套路即可获得**

![在这里插入图片描述](https://img.javadog.net/blog/wx-boot/68fe503c323845c5b20d29cc385492e4_tplv-k3u1fbpfcp-zoom-1.png)



******
###  🍯猜你喜欢
|序号| 分类| 文章描述 | 
| --- | ----- |  ----- |
| 1 | 项目实战 | [SpringBoot+uniapp+uview2打造一个企业黑红名单吐槽小程序](https://blog.csdn.net/baidu_25986059/article/details/129263139) |

### 🍩水图
![](https://img.javadog.net/blog/wx-boot/95e596f301254f99820935ecbb62de35_tplv-k3u1fbpfcp-zoom-1.gif)
******

## 正文
### 📝准备
#### 1.服务号/订阅号(已认证)
> 网页授权接口权限只有**服务号**或者**已认证的订阅号**有权限，也就是需要每年缴纳300块钱费用认证过才会有权限，否则无法在正式公众号调用此接口。 
> 🚨 切记无权限时调用会提示【微信公众号授权scope参数错误或没有scope权限】，只可在**测试公众号进行调试**！！！


#### 2.开发工具
|序号| 分类| 工具| 
| --- | ----- |  ----- |
| 1 | 后端 |  IDEA |
| 2 | 前端 | uniapp  |
| 3 | 前端 | 微信开发者工具|

#### 3.接口文档

[微信官方公众号文档](https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html)

#### 4.网页授权获取用户基本信息配置
> 以测试号进行展示操作步骤

##### 在公众号左侧菜单【开发工具】=》【公众平台测试帐号】
![](https://img.javadog.net/blog/wx-boot/249afad807604c98bd8c78752f51b309_tplv-k3u1fbpfcp-zoom-1.png)
##### 在网页服务-网页账号-修改
![](https://img.javadog.net/blog/wx-boot/a869467da5ab4553a82955b40c182b9c_tplv-k3u1fbpfcp-zoom-1.png)
##### 修改OAuth2.0网页授权【授权回调页面域名】
> **切记不要带http**，此处地址填写本地ip即可。可在键盘通过【win+R】，输入【cmd】，确定后输入ipconfig，复制出IPv4地址即可。或者启动uniapp后，控制台打印的地址，**以上方法仅限本地调试**。

![](https://img.javadog.net/blog/wx-boot/88b53cbbb8fd48a3bc4ecf5bea32e8f7_tplv-k3u1fbpfcp-zoom-1.png)

![](https://img.javadog.net/blog/wx-boot/c3467a6748664f199499ee0495b3f686_tplv-k3u1fbpfcp-zoom-1.png)

![](https://img.javadog.net/blog/wx-boot/7d6d2c4de6ec4ee1b87c25474def0952_tplv-k3u1fbpfcp-zoom-1.png)


******
### 🔛问题剖析
#### 1.什么是网页授权？
##### 官网解释
> 如果用户在**微信客户端**中访问**第三方网页**，公众号可以通过微信**网页授权机制**，来获取用户基本信息，进而实现业务逻辑。

##### 简单理解
> 通俗来说，就是我们通过**微信内置浏览器**去访问其他网站时，可以通过**微信授权策略**，在目标网站可以获取微信用户的基本信息，如昵称，头像等

##### 举个栗子 🌰
>  通过**微信内打开csdn分享链接**，当跳转到csdn网页时，csdn就可以通过**网页授权**，直接拿到我们微信的昵称，头像信息等，完成**自主授权注册**

#### 2.网页授权流程步骤？
##### 1. 用户同意授权，获取code
> 前端引导用户进入授权页面同意授权，获取code

![](https://img.javadog.net/blog/wx-boot/9b9704739e5b4e4aba94a609603a5281_tplv-k3u1fbpfcp-zoom-1.png)
##### 2. 通过 code 换取网页授权access_token
> 通过前端获取的code，**调用我们自己服务器接口**，通过**后台使用code调取微信接口**，换取**access_token(网页授权接口调用凭证)**，access_token是调用用户信息及其他接口所必需的参数

##### 3. 刷新access_token（如果需要）
> 由于**access_token**拥有较短的**有效期**，当**access_token超时**后，可以使用**refresh_token**进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要**用户重新授权**

##### 4. 拉取用户信息
> 开发者可以通过**access_token**和 **openid** 拉取用户信息

🎯 如果看不懂不要划走，下面才是重点讲解
🎯 如果看不懂不要划走，下面才是重点讲解
🎯 如果看不懂不要划走，下面才是重点讲解
******

### 👼开始
#### 简易流程图
> 为了方便理解，画了一个简易流程图，有疑惑别急，下面还有现实列子解释版

![](https://img.javadog.net/blog/wx-boot/bd3cc64d9739428e827fb0ee6d9f54ab_tplv-k3u1fbpfcp-zoom-1.png)

> 举个实际购买**苹果手机**例子，可能不太贴合，但有助与理解

![](https://img.javadog.net/blog/wx-boot/b49a49cc87174affb2b0c1f971fbde41_tplv-k3u1fbpfcp-zoom-1.png)
******
#### 步骤
> 根据下图进行步骤分析

![](https://img.javadog.net/blog/wx-boot/064c3020e03740508f26627b35af7d17_tplv-k3u1fbpfcp-zoom-1.png)
##### 1.步骤一

 前端uniapp中**新建授权页**

> 页面进入后，onLoad直接跳转微信接口地址，微信方通过appId获取code，并**携带code**根据**redirect_uri返回到当前页面**，此刻**地址栏中就包含着code信息**

**跳转前**
![](https://img.javadog.net/blog/wx-boot/855631b33bf84c03a616921bf7ef5f7e_tplv-k3u1fbpfcp-zoom-1.png)

**跳转后**
![](https://img.javadog.net/blog/wx-boot/455a06460e15495b932bef57742c3487_tplv-k3u1fbpfcp-zoom-1.png)

关键代码

```js
//通过微信官方接口获取code之后，会重新刷新设置的回调地址【redirect_uri】,之后会继续进入到onload()方法
location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" +
local + "&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect""
```
**appid** ：公众号对应的appId
**redirect_uri**：页面回跳地址，也就是当前页面的地址，此地址也就是在上方**准备4**中OAuth2.0网页授权【授权回调页面域名】填入的值。微信根据这个回调地址进行携带code回跳。

其他参数可看官方参数说明

![](https://img.javadog.net/blog/wx-boot/d299334767994f25b46b9fa8ef3a2651_tplv-k3u1fbpfcp-zoom-1.png)
******
##### 2.步骤二
1.后端提供接口，**入参**为上一步的**code**，**通过code**调取微信接口**换取access_token**。此处后端使用了[weixin-java-mp](https://github.com/Wechat-Group/WxJava)组件，方便对接微信接口。

```java
@Api(tags = "网页授权控制器")
@RestController
@RequestMapping("/login")
public class AuthController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "网页授权获取用户基本信息", notes = "网页授权获取用户基本信息")
    @GetMapping
    public ResponseData login(@RequestParam String code) throws WxErrorException {
        return userService.auth(code);
    }
}
```

2.前端带着code，调用1中的/login接口
```js
const res = await this.$api.login({
		code
	})
	if (!res.success) {
		uni.$u.toast(res.message)
		return
	}
	// 用户信息
	this.userInfo = res.data;
}
```
******
##### 3.步骤三
将步骤二后端拿到的**access_token**，调用微信**获取用户信息**，将用户信息返回给前端

```java
 WxOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        try {
        	// 获取accessToken 
            accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            log.info("AccessToken={}" + accessToken);
            // 获取用户信息
            wxMpUser = wxMpService.getUserService().userInfo(accessToken.getOpenId());
            log.info("wxMpUser={}", JSONUtil.toJsonStr(wxMpUser));
        } catch (WxErrorException e) {
            return ResponseData.error(e.getMessage());
        }
        return ResponseData.success(wxMpUser);
```
******

### ❌常见错误
#### 错误1：redirect_uri 参数错误
> 微信中【OAuth2.0网页授权】-**授权回调页面域名配置错误**

![](https://img.javadog.net/blog/wx-boot/682c922564aa475f84ee227c2ea88b7f_tplv-k3u1fbpfcp-zoom-1.png)

![](https://img.javadog.net/blog/wx-boot/ac65218982d241be8d24449fcf2cb3fe_tplv-k3u1fbpfcp-zoom-1.png)
#### 错误2：oauth_code已使用
> **code每次使用换取access_token后便失效**，再次调用则会报错，请**根据自己业务**，进行缓存比较，**防止重复调用**

![](https://img.javadog.net/blog/wx-boot/6f81c3a97b6d4510994e810c2a6f90cc_tplv-k3u1fbpfcp-zoom-1.png)
## 总结
本文虽然篇幅长，但是实现功能简单，主要目的是展示**网页授权**的流程与思路，对于微信对接，本人建议多查看**微信官方文档**，写的很详细，通读过几遍后就会对疑问点有些许领悟，希望能对大家有所帮助。

![](https://img.javadog.net/blog/wx-boot/7bc0f05773c2436c84562cc0b3e61a70_tplv-k3u1fbpfcp-zoom-1.jpg)


| JavaDog| 狗屋地址 |
| :----:| :----: | 
| 个人博客 | [https://www.javadog.net](https://www.javadog.net) | 
| 公众号 | [https://mp.weixin.qq.com/s/_vgnXoQ8FSobD3OfRAf5gw](https://mp.weixin.qq.com/s/_vgnXoQ8FSobD3OfRAf5gw) | 
| CSDN  | [https://blog.csdn.net/baidu_25986059](https://blog.csdn.net/baidu_25986059) | 
| 掘金 | [https://juejin.cn/user/2172290706716775](https://juejin.cn/user/2172290706716775)| 
| 知乎 | [https://www.zhihu.com/people/JavaDog](https://www.zhihu.com/people/JavaDog) | 
| 简书| [https://www.jianshu.com/u/1ff9c6bdb916](https://www.jianshu.com/u/1ff9c6bdb916) | 
| gitee|[https://gitee.com/javadog-net](https://gitee.com/javadog-net)  | 
| GitHub|[https://github.com/javadog-net](https://github.com/javadog-net)| 
