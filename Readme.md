# 注意事项

## 说明

本项目是个人练手的小项目,用到了一些自己不够熟练的框架技术.

## 启动

> 启动本项目需要连接nacos ,从其获取配置文件.请查看 bootstrap.yml 文件内容.  
> 需要在 nacos 上创建一个配置文件 nacos-demo.yml .配置文件的 Data ID: nacos-demo  Group: nacos-test .  
> 文件内容如下:

```
nacos:
 config:
  testStr: 这里是从nacos上获取修改后的config-nacos-test

my:
  config:
    test:
      name: 'huang-nacos, 分组是: nacos-test, 命名空间id是: my-nacos-learn'
      age: 15
      addr: 'China-nacos, 分组是: nacos-test, 命名空间id是: my-nacos-learn'
```



