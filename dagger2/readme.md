
目前使用的 dagger2 的版本是 2.16；

## 参考文档
  
  - https://google.github.io/dagger/
  - https://medium.com/@harivigneshjayapalan/dagger-2-for-android-beginners-introduction-be6580cb3edb
  - https://blog.mindorks.com/the-new-dagger-2-android-injector-cbe7d55afa6a
  - https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe
  
 ## dagger.android
 
 Dagger 最好的工作方式是自己创建所有注入的对象;
 但在Android 中，很多类都是系统自身实例化的，例如 Activity 和 Fragment ;
 Dagger不能创建这些类的实例也就算了，还必须要在生命周期内注入才可以。就像下面的代码那样
 
 ```java
 public class FrombulationActivity extends Activity {
   @Inject Frombulator frombulator;
 
   @Override
   public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     // DO THIS FIRST. Otherwise frombulator might be null!
     ((SomeApplicationBaseType) getContext().getApplicationContext())
         .getApplicationComponent()
         .newActivityComponentBuilder()
         .activity(this)
         .build()
         .inject(this);
     // ... now you can write the exciting code
   }
 }
 ```
 
 这样就有一些小问题：
 
 每个 Activity 或 Fragment 都需要这样的重复性代码；更根本的是它让依赖者知道了它的注射器，即使这是通过接口而不是类完成的，它也违背了依赖注入的核心原则：一个类不应该知道它是如何被注入的。
 
 dagger.android 将很多的重复性代码和封装了起来并提供了简化方案；
 
 
 
 
 
 
 
 