# GreenDaoDemo
# 史上最牛逼的Android数据库 GreenDao3.1 详细使用教程

### GreenDao 介绍：

greenDAO是一个对象关系映射（ORM）的框架，能够提供一个接口通过操作对象的方式去操作关系型数据库，它能够让你操作数据库时更简单、更方便。如下图所示：

![img](https://upload-images.jianshu.io/upload_images/2495529-1f486a075b3541e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/640)

官网地址：[http://greenrobot.org/greendao/](https://link.jianshu.com?t=http://greenrobot.org/greendao/)
Github地址：[https://github.com/greenrobot/greenDAO](https://link.jianshu.com?t=https://github.com/greenrobot/greenDAO)

### GreenDao 优点：

1.性能高，号称Android最快的关系型数据库
2.内存占用小
3.库文件比较小，小于100K，编译时间低，而且可以避免65K方法限制
4.支持数据库加密  greendao支持SQLCipher进行数据库加密 有关SQLCipher可以参考这篇博客[Android数据存储之Sqlite采用SQLCipher数据库加密实战](https://link.jianshu.com?t=http://www.cnblogs.com/whoislcj/p/5511522.html)
5.简洁易用的API

### GreenDao 3.1改动：

使用过GreenDao的同学都知道，3.0之前需要通过新建GreenDaoGenerator工程生成Java数据对象（实体）和DAO对象，非常的繁琐而且也加大了使用成本。
GreenDao  3.0最大的变化就是采用注解的方式通过编译方式生成Java数据对象和DAO对象。总体来说,GreenDao3.1在配置上相对于2.0要简单的多。

# GreenDao 3.1使用方式：

### 第一步 先在  项目的Project  的 buil.gradle 里 在buildscript- repositories添加配置mavenCentral()，

在dependencies 里添加
classpath'org.greenrobot:greendao-gradle-plugin:3.1.0'

```
buildscript {  
    repositories {        
           jcenter()       
           mavenCentral()   
   }    
dependencies {        
    classpath 'com.android.tools.build:gradle:2.1.0'         
    classpath 'org.greenrobot:greendao-gradle-plugin:3.1.0'  
  }

}


```

![img](https://upload-images.jianshu.io/upload_images/2495529-16840d34cb25bf5c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

第二步  在自己想要用的
GreenDao Module 里的  dependencies 里添加

```
compile'org.greenrobot:greendao:3.1.0'
compile'org.greenrobot:greendao-generator:3.1.0'


```

![img](https://upload-images.jianshu.io/upload_images/2495529-6275903be0557bd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

android 里 添加

```
greendao{
schemaVersion1   
daoPackage'com.afa.tourism.greendao.gen'
targetGenDir'src/main/java'
}


```

**schemaVersion**： 数据库schema版本，也可以理解为数据库版本号
**daoPackage**：设置DaoMaster、DaoSession、Dao包名
**targetGenDir**：设置DaoMaster、DaoSession、Dao目录
**targetGenDirTest**：设置生成单元测试目录
**generateTests**：设置自动生成单元测试用例

头部 添加

```
applyplugin:'org.greenrobot.greendao'


```

![img](https://upload-images.jianshu.io/upload_images/2495529-aeaefcdb02537639.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

Paste_Image.png

### 第三步  创建新的实体类

在实体类 上方 写 上  @Entity  即可！然后导包而且不需要写set get 方法。  他自动帮我们生成.. 是不是很牛逼哦？

```
/** * Created by AFa on 2016/8/23. *
/@Entity
public class User {   
 @Id(autoincrement = true)    
private Long id;    
private String name;    
private String age;    
private String sex;    
private String salary;
}

```

![img](https://upload-images.jianshu.io/upload_images/2495529-a212936d125d5914.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/636)

Paste_Image.png

### 然后运行项目即可！

![img](https://upload-images.jianshu.io/upload_images/2495529-e99b0b84b29bb8eb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

Paste_Image.png

#### 1.）实体@Entity注解

**schema**：告知GreenDao当前实体属于哪个schema
**active：**标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
**nameInDb：**在数据中使用的别名，默认使用的是实体的类名
**indexes：**定义索引，可以跨越多个列
**createInDb：**标记创建数据库表**

#### 2.）基础属性注解

**@Id :**主键 Long型，可以通过@Id(autoincrement = true)设置自增长
**@Property：**设置一个非默认关系映射所对应的列名，默认是的使用字段名举例：@Property (nameInDb="name")
**@NotNul：**设置数据库表当前列不能为空
**@Transient**：添加次标记之后不会生成数据库表的列

#### 3.)索引注解

**@Index：**使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
**@Unique：**向数据库列添加了一个唯一的约束

#### 4.）关系注解

**@ToOne：**定义与另一个实体（一个实体对象）的关系
**@ToMany：**定义与多个实体对象的关系

(一) @Entity 定义实体
@nameInDb 在数据库中的名字，如不写则为实体中类名
@indexes 索引
@createInDb 是否创建表，默认为true,false时不创建
@schema 指定架构名称为实体
@active 无论是更新生成都刷新
(二) @Id
(三) @NotNull 不为null
(四) @Unique 唯一约束
(五) @ToMany 一对多
(六) @OrderBy 排序
(七) @ToOne 一对一
(八) @Transient 不存储在数据库中
(九) @generated 由greendao产生的构造函数或方法

# GreenDao3.1的使用

(首先封装到Application 中，为了方便)

![img](https://upload-images.jianshu.io/upload_images/2495529-d94a48dbc13bf0e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

```
public class AFaApplication extends Application{   

private static AFaApplication application;   
private DaoMaster.DevOpenHelper mHelper;    
private SQLiteDatabase db;    
private DaoMaster mDaoMaster;
private DaoSession mDaoSession;
   
@Override  
public void onCreate() {       
super.onCreate();      
application = this;     
setDatabase();   
 }  
 
public static AFaApplication getApplication() {      
return application;   
 }   

 /**     * 设置greenDao     */  

 private void setDatabase() {  

  // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。      
  // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。       
 // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。       
 // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。       
 mHelper = new DaoMaster.DevOpenHelper(this,"notes-db", null);     
 db =mHelper.getWritableDatabase();      
 // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。       
 mDaoMaster = new DaoMaster(db);       
 mDaoSession = mDaoMaster.newSession();   

 }   

 public DaoSession getDaoSession() {      
return mDaoSession;   
 }  

 
public SQLiteDatabase getDb() {     
return db;   
 }

}


```

在Activity 中初始化

![img](https://upload-images.jianshu.io/upload_images/2495529-464f32d811aca81e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

Paste_Image.png

```
dao =AFaApplication.getApplication().getDaoSession().getUserDao();



```

#### 增删改查操作

```
//增

private void insertData() { 
name = et_Name.getText().toString().trim();    
age = et_age.getText().toString();    
sex = et_sex.getText().toString().trim();    
salary = et_Salary.getText().toString().trim();    
User insertData = new User(null, name, age, sex, salary);    
dao.insert(insertData);
}

```

```
//删

private voiddeleteData(Long id){
userInfoDao.deleteByKey(id);
}

```

```
//改

private void updateData(int i) {    
name = et_Name.getText().toString().trim();    
age = et_age.getText().toString();    
sex = et_sex.getText().toString().trim();    
salary = et_Salary.getText().toString().trim();    
User updateData = new User(listsUser.get(i).getId(), name, age, sex, salary);    dao.update(updateData);
}

```

```
//查

private void queryData() {    
List<User> users = dao.loadAll();    
String userName = "";    
for (int i = 0; i < users.size(); i++) {        
userName += users.get(i).getName() + ",";    
}    
Toast.makeText(this, "查询全部数据==>" + userName, Toast.LENGTH_SHORT).show();
}

```

# 不废话了，直接上效果图



# 还可以执行sql 语句

```
 String sql ="insert into user values (null,'111')";
 AFaApplication.getApplication().getDaoSession().getDatabase().execSQL(sql);
```