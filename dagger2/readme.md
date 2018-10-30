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
 
     @Component.Builder
     abstract class Builder extends AndroidInjector.Builder<MyApplication> {
     }
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
  
  dagger.android 为我们封装好了一个 DaggerApplication ，上述的这些都封装了进去
  
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