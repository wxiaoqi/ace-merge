# AG-Merge
Spring Cloud 跨服务数据聚合框架

# 解决问题
解决Spring Cloud服务拆分后分页数据的属性或单个对象的属性拆分之痛,
支持对静态数据属性(数据字典)、动态主键数据进行自动注入和转化, 其中聚合的静态数据会进行`一级混存`(guava).

# 示例
具体示例代码可以看`ace-merge-demo`模块
```
|------- ace-eureka  注册中心
|------- ace-data-merge-demo  查询数据,此处聚合示例
|------- ace-data-provider 数据提供者
```

## Maven添加依赖
```
<dependency>
    <groupId>com.github.wxiaoqi</groupId>
    <artifactId>ace-merge-core</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```
## 启动类加注解
```
@EnableAceMerge
```
## application.yml配置
```
# 跨服务数据合并
merge:
  enabled: true
  guavaCacheNumMaxSize: 1000
  guavaCacheRefreshWriteTime: 10 # min
  guavaCacheRefreshThreadPoolSize: 10
  aop: # 启动注解的方式,自动聚合
    enabled: true

```

## 代码示例(`@MergeField`标志对象的数据需要聚合)
```
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface MergeField {
    /**
     * 查询值
     * @return
     */
    String key() default "";

    /**
     * 目标类
     * @return
     */
    Class<? extends Object> feign() default Object.class;

    /**
     * 调用方法
     * @return
     */
    String method() default "";

    /**
     * 是否以属性值合并作为查询值
     * @return
     */
    boolean isValueNeedMerge() default false;
}
```
- 聚合对象

```
public class User {
    private String name;
    // 需要聚合的属性
    @MergeField(key="test", feign = IService2.class,method = "writeLog")
    private String sex;
    // 需要聚合的属性
    @MergeField(feign = IService2.class,method = "getCitys",isValueNeedMerge = true)
    private String city;

    public User(String name, String sex, String city) {
        this.name = name;
        this.sex = sex;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```

- 聚合数据来源方法(示例为通过FeignClient,也可以是本地的spring bean对象)

特别要求:入参必须为一个String,返回值必须为Map<String,String>.
其中返回值的构成,就是聚合对象属性的key和对应的value.

```
@FeignClient("test")
public interface IService2 {
    @RequestMapping("car/do")
    public Map<String, String> writeLog(String test);

    @RequestMapping("car/city")
    public Map<String, String> getCitys(String ids);
}

```

对应的远程服务接口
```
/**
 * @author ace
 * @create 2017/11/20.
 */
@RestController
@RequestMapping("car")
public class Service2Rest {
    private Logger logger = LoggerFactory.getLogger(Service2Rest.class);

    @RequestMapping("do")
    public Map<String,String> writeLog(String test){
        logger.info("service 2 is writing log!");
        Map<String,String> map = new HashMap<String, String>();
        map.put("man","男");
        map.put("woman","女");
        return map;
    }

    @RequestMapping("city")
    public Map<String,String> getCity(String ids){
        logger.info("service 2 is writing log!"+ids);
        Map<String,String> map = new HashMap<String, String>();
        map.put("1","广州");
        map.put("2","武汉");
        return map;
    }
}
```
- 聚合对象的Biz类(下面的方式是采用aop扫描注解的方式)

```
@Service
@Slf4j
public class UserBiz {
    @Autowired
    private MergeCore mergeCore;
    /**
     *     aop注解的聚合方式
     *     其中聚合的方法返回值必须为list,
     *     如果为复杂对象,则需要自定义自己的聚合解析器(实现接口IMergeResultParser)
     */
    @MergeResult(resultParser = TestMergeResultParser.class)
    public List<User> getAopUser() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        return users;
    }

    /**
     * 手动聚合方式
     * @return
     */
    public List<User> getUser(){
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 1000; i > 0; i--) {
            users.add(new User("zhangsan" + i, "man", "1"));
            users.add(new User("lisi" + i, "woman", "2"));
            users.add(new User("wangwu" + i, "unkonwn", "2"));
        }
        try {
            // list 聚合
            mergeCore.mergeResult(User.class,users);
            // 单个对象聚合
//            mergeCore.mergeOne(User.class,users.get(0));
        } catch (Exception e) {
            log.error("数据聚合失败",e);
        }finally {
            return users;
        }
    }
}
```
