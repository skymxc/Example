# Dagger2 

> 当前分支为 dagger_android_simplified dagger.android 的简化版本


SubComponent 的作用 只是连接 module 和 activity || fragment 的桥梁；换句话说 是连接 依赖 和 依赖者 的桥梁；
在 complicated 分支上 使用这个 是为了更好的理解和使用 Dagger2 的新版本；

这里把 SubComponent 这样的重复代码 消除掉了，使用 @ContributeAndroidInjector 替代；

Dagger2 文档 -> https://google.github.io/dagger/android

以 OneFragment 举例

原来的


```java

@Subcomponent(modules = OneModule.class)
public interface OneComponent extends AndroidInjector<OneFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<OneFragment> {

    }
}

//在 activity 级别的 Component 添加映射

@Module(subcomponents = {OneComponent.class,TwoComponent.class})
public abstract class SecondFragmentBuilder {
    @Binds
    @IntoMap
    @FragmentKey(OneFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindOneFragment(OneComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(TwoFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindTwoFragment(TwoComponent.Builder builder);

}

```

简化后

```java
//删除  OneComponent

//修改 SecondFragmentBuilder 使用  @ContributesAndroidInjector 

@Module()
public abstract class SecondFragmentBuilder {
  

    @ContributesAndroidInjector(modules = OneModule.class)
    abstract OneFragment bindOneFragment();

    @ContributesAndroidInjector(modules = TwoModule.class)
    abstract TwoFragment bindTwoFragment();
}
```

---

关于 APPComponent ，可以继承自 dagger.android 中的 AndroidInjector<> 其中 已经定义好了 inject(T); 消除了这个重复代码；AndroidInjector.Builder 是同样的。


原来的

```java
@APPScoped
@Component(modules = {APIModule.class,
        APPModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class})
public interface APPComponent {

    void inject(MyApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        APPComponent build();
    }
}

```
 
 改造后
 
 ```java
 @APPScoped
 @Component(modules = {APIModule.class,
         APPModule.class,
         AndroidInjectionModule.class,
         ActivityBuilder.class})
 public interface APPComponent extends AndroidInjector<MyApplication> {
 
 
 }

 ```
 
 ---
 
 关于 MyApplication ：
 
 现在 实现了  HasActivityInjector 是因为 有 Activity 需要 注入；
 如果有 Service，ContentProvide BroadcastReceiver 等 就需要 实现  
 HasFragmentInjector,
 HasServiceInjector,
 HasBroadcastReceiverInjector,
  HasContentProviderInjector 这些接口，用到哪个就需要实现哪个。
  
  另外 还需要在 Application 中 注入一个 属性 例如
  
  DispatchingAndroidInjector<Activity> androidInjector;
  
  如果需要 注射 Service 就需要 另一个了
  @Inject DispatchingAndroidInjector<Service> serviceInjector;
  
  dagger.android 为我们封装好了一个 DaggerApplication ，上述的这些都封装了进去；
  
  可以看下 DaggerApplication 的源码 就可以清楚的明白什么意思;
  
  如果你的 Application 本来没有其他任务例如 继承自 MultiDexApplication;就可以直接继承 DaggerApplication 来省略这些代码；
  
  ```java
  @Beta
  public abstract class DaggerApplication extends Application
      implements HasActivityInjector,
          HasFragmentInjector,
          HasServiceInjector,
          HasBroadcastReceiverInjector,
          HasContentProviderInjector {
  
    @Inject DispatchingAndroidInjector<Activity> activityInjector;
    @Inject DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;
    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject DispatchingAndroidInjector<Service> serviceInjector;
    @Inject DispatchingAndroidInjector<ContentProvider> contentProviderInjector;
    private volatile boolean needToInject = true;
  
    @Override
    public void onCreate() {
      super.onCreate();
      injectIfNecessary();
    }
  
    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * DaggerApplication}. Typically, that injector is a {@link dagger.Component}.
     */
    @ForOverride
    protected abstract AndroidInjector<? extends DaggerApplication> applicationInjector();
  
    /**
     * Lazily injects the {@link DaggerApplication}'s members. Injection cannot be performed in {@link
     * Application#onCreate()} since {@link android.content.ContentProvider}s' {@link
     * android.content.ContentProvider#onCreate() onCreate()} method will be called first and might
     * need injected members on the application. Injection is not performed in the constructor, as
     * that may result in members-injection methods being called before the constructor has completed,
     * allowing for a partially-constructed instance to escape.
     */
    private void injectIfNecessary() {
      if (needToInject) {
        synchronized (this) {
          if (needToInject) {
            @SuppressWarnings("unchecked")
            AndroidInjector<DaggerApplication> applicationInjector =
                (AndroidInjector<DaggerApplication>) applicationInjector();
            applicationInjector.inject(this);
            if (needToInject) {
              throw new IllegalStateException(
                  "The AndroidInjector returned from applicationInjector() did not inject the "
                      + "DaggerApplication");
            }
          }
        }
      }
    }
  
    @Inject
    void setInjected() {
      needToInject = false;
    }
  
    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
      return activityInjector;
    }
  
    @Override
    public DispatchingAndroidInjector<Fragment> fragmentInjector() {
      return fragmentInjector;
    }
  
    @Override
    public DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
      return broadcastReceiverInjector;
    }
  
    @Override
    public DispatchingAndroidInjector<Service> serviceInjector() {
      return serviceInjector;
    }
  
    // injectIfNecessary is called here but not on the other *Injector() methods because it is the
    // only one that should be called (in AndroidInjection.inject(ContentProvider)) before
    // Application.onCreate()
    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
      injectIfNecessary();
      return contentProviderInjector;
    }
  }

  ```
  
  改造前 
  ```java
  public class MyApplication extends Application implements HasActivityInjector {
  
      // 不要使用 AndroidInjector<Activity>；
      @Inject DispatchingAndroidInjector<Activity> androidInjector;
      @Override
      public void onCreate() {
          super.onCreate();
          DaggerAPPComponent.builder().build().inject(this);
  
          DaggerApplication
      }
  
  
      @Override
      public DispatchingAndroidInjector<Activity> activityInjector() {
          return androidInjector;
      }
  }

  ```
  
  使用 DaggerApplication 后
  
  ```java
  public class MyApplication extends DaggerApplication {
  
      @Override
      protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
          return DaggerAPPComponent.builder().build();
      }
  
  
  }
  ```
  
  使用 AndroidInjector<> ，DaggerApplication,DaggerActivity DaggerXxx etc..确实很方便，但也有很多不便，要知道 Java 是单继承的。
  
  我觉得 按需实现接口的方式可能更好一点。 All in all ：看需求而定。
  
  
  ## 简化版步骤
  
  ### Application 层的 Component
  
  
  APPComponent.class
  ```java
  @APPScoped
  @Component(modules = {APIModule.class,
          APPModule.class,
          AndroidInjectionModule.class,
          ActivityBuilder.class})
  public interface APPComponent extends AndroidInjector<MyApplication> {
  
  
  }
  ```
  
  ActivityBuilder.class 映射到我们的 Android组件 省略了 模块中 subcomponents ={}
  ```java
  @Module
  public abstract class ActivityBuilder {
  
      @ContributesAndroidInjector(modules = MainModule.class)
      abstract MainActivity bindMainActivity();
  
      @ContributesAndroidInjector(modules = {SecondModule.class,SecondFragmentBuilder.class})
      abstract SecondActivity bindSecondActivity();
  
  }

  ```
APPModule.class 凡是参数要转换为返回数据的 可直接使用 @Binds   
  ```java
  @Module()
  public abstract class APPModule {
  
  
      @Binds //@Binds 注解委托代理的 抽象方法；必须是抽象方法；必须有一个参数，参数必须能转换为 返回类型 不然无法生成代码就会报错。
      @APPScoped
      abstract Context provideContext(Application application);
  
  
  }

  ```
  
  ### Activity 层
  
  因为 SubComponent 是重复性代码，每个Activity都需要而且只是连接 module的桥梁，所以省略了，
  原本的 ActivityModule 直接定义到 了ActivityBuilder.clas 中的 @ContributesAndroidInjector(modules=ActivityModule.class) 中。
  
  现在只需要定义 Activity 层的 Module 即可；
  
  以 MainModule.class 示例 
  ```java
  @Module
  public class MainModule {
  
      @Provides
      public MainView provideMainView(MainActivity activity){
          return activity;
      }
  
      @Provides
      public MainPresenter provideMainPresenter(MainView view, ApiService apiService){
          return new MainPresenter(view,apiService);
      }
  
  }

  ```
  
  
  
  ### Fragment 层
  
  和 Activity 层是一样的 也将重复性代码省略 直接更改了 SecondFragmentBuilder.class
  
  所以 Activity 层的 SecondFragmentBuilder.class 如下
  
  ```java
  @Module()
  public abstract class SecondFragmentBuilder {
  
  
      @ContributesAndroidInjector(modules = OneModule.class)
      abstract OneFragment bindOneFragment();
  
      @ContributesAndroidInjector(modules = TwoModule.class)
      abstract TwoFragment bindTwoFragment();
  }

  ```
  
  
  ----
  
  将类图组织好之后就是 构建注入 了。
  
  ### 注入
  
  #### Application 注入
  
  直接继承自 DaggerApplication ;这是一个 dagger.android 中定义好的类 是 HasActivityInjector,
                                                                HasFragmentInjector,
                                                                HasServiceInjector,
                                                                HasBroadcastReceiverInjector,
                                                                HasContentProviderInjector 
 这些接口的默认实现；如果没有其他任务可直接继承这个类。注入代码已经封装在了里面。
 如果有其他需求，不能直接继承，可以按需实现实现接口
                                                          
  MyApplication.class
  ```java
  public class MyApplication extends DaggerApplication {
  
      @Override
      protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
          return DaggerAPPComponent.builder().build();
      }
  
  }
  ```
  
  #### Activity 注入
  
  Activity 内没有 Fragment 层的话还是按照原来的注入 在 setContentView() 前使用 ` AndroidInjection.inject(this);`
  
  如果Activity 内有 Fragment 层的需要注入可以 直接继承自 DaggerAppCompatActivity || DaggerActivity ;看你用的时哪个包下的了。
  同样的，如果不能继承，可以按需实现接口；
 
   这两个类都是  HasFragmentInjector, HasSupportFragmentInjector 接口的实现类。将注入代码都封装好了。
   具体的可以看看这些类的源码；
   ```java
   @Beta
   public abstract class DaggerAppCompatActivity extends AppCompatActivity
       implements HasFragmentInjector, HasSupportFragmentInjector {
   
     @Inject DispatchingAndroidInjector<Fragment> supportFragmentInjector;
     @Inject DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;
   
     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
       AndroidInjection.inject(this);
       super.onCreate(savedInstanceState);
     }
   
     @Override
     public AndroidInjector<Fragment> supportFragmentInjector() {
       return supportFragmentInjector;
     }
   
     @Override
     public AndroidInjector<android.app.Fragment> fragmentInjector() {
       return frameworkFragmentInjector;
     }
   }

   ```
 #### Fragment 注入
 
  和上面的一样，都有封装的类可以使用 `DaggerFragment`;
  
  -----
  
  到这就配置好了，可以直接构建了。
  
  