# jwt-demo
一个jwt简单的demo项目
### 启动 jwt-demo项目

尝试请求url：  http://localhost:8080/jwt/hello    没有jwt token，会直接返回`{ "message": "jwt token is null",code": 401}`

### 获取token

```shell
curl --location --request POST 'http://localhost:8080/jwt/login' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'username=metinkong' --data-urlencode 'password=113711'
```

返回值：

```json
{
    "message": "登陆成功！",
    "code": "200",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRpbmtvbmciLCJpYXQiOjE2NDk2NzEwNTgsImV4cCI6MTY0OTY3Mjg1OH0._6RjpNgdn2TAEOYb2eTWA3UdRnIwG9tSIcna9YnT-tx_1csV1NWAdhk2wUn41Rc7akTRuAoXG5G5OKqRqX-FWw"
}
```



### 验证token

带上jwt token请求hello接口，可以正常访问

```shell
curl --location --request GET 'http://localhost:8080/jwt/hello' --header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRpbmtvbmciLCJpYXQiOjE2NDk2NzEwNTgsImV4cCI6MTY0OTY3Mjg1OH0._6RjpNgdn2TAEOYb2eTWA3UdRnIwG9tSIcna9YnT-tx_1csV1NWAdhk2wUn41Rc7akTRuAoXG5G5OKqRqX-FWw'
```

