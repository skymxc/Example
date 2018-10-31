> 当前分支 是 dagger.android 的 复杂分支 也是详细分支。


目前使用的 dagger2 的版本是 2.16；

## 参考文档
  
  - https://google.github.io/dagger/
  - https://medium.com/@harivigneshjayapalan/dagger-2-for-android-beginners-introduction-be6580cb3edb
  - https://blog.mindorks.com/the-new-dagger-2-android-injector-cbe7d55afa6a 
  - https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe 一步一步的讲解 很深入。
  - https://android.jlelse.eu/android-and-dagger-2-10-androidinjector-5e9c523679a3 新方式和老方式对比着讲解。
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
 
 下面以一个 activity 注入的例子来看一下 是怎么简化的。
 
 First of all, 添加 dagger.android  依赖；当前我使用的是 2.16； 最新版本以 [GitHub](https://github.com/google/dagger) 的版本号为准
 
 ```groovy
 dependencies {
   compile 'com.google.dagger:dagger-android:2.16'
   compile 'com.google.dagger:dagger-android-support:2.16' // if you use the support libraries
   annotationProcessor 'com.google.dagger:dagger-android-processor:2.16'
 }
 ```
 
 Then, 创建 Component；添加 AndroidInjectionModule 和 Builder；
 
 Application 将通过 APPComponent 构建一张类图。
 
 APPComponent 的上部有一个 @Component。当 APPComponent 使用其模块构建时，我们的类图中将包含模块中的所有实例。
 例如：如果 APIModule 中提供了 APIService，当拥有 APIModule 的组件构建时 我们将获取 APIService 的实例。
 
 - @Component 组件；组件拥有一张类图；我们构建组件，组件会通过其给定模块提供注入需要的实例；
 - @Component.Builder 有时我们需要绑定一些实例到 Component上；在这个Demo里我通过 @Component.Builder 创建一个接口，可以添加任何想添加的方法到构建器上。
 
 *如果想为 Component 创建 构建者，构建者必须有一个返回 Component 的 build()*
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
 
 - APIModule & APPModule 是我自己创建的提供一些 application 级别的实例
 - AndroidInjectionModule 是 dagger.android 的内部类，不需要我们创建。它提供了我们需要的 Android组件
 - ActivityBuilder 这个是我们自己创建的给定模块，将我们所有的Android组件映射到这里，在编译时 Dagger 就会知道。在这个 Demo里 我有两个组件；
 
 在使用 Dagger 时，我们可以将 程序分为 三层
 
 - Application Component
 - Activity Component
 - Fragment Component
 
 程序只有一个 Application 类，这也是为什么我们有一个 APPComponent；这个组件负责提供 application 范围内的 实例（例如 OKhttp，Database 等）；
 这个 APPComponent 组件 是 Dagger 类图的根；
 
 在我们这个 Demo 里有三个模块。
 
 ActivityBuilder.class 
 ```java
 /**
  * Created by mxc on 2018/10/30.
  * description: 映射到自己的 activity
  *
  */
 @Module
 public abstract class ActivityBuilder {
 
     @Binds
     @IntoMap
     @ActivityKey(MainActivity.class)
     abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainComponent.Builder builder);
 
     @Binds
     @IntoMap
     @ActivityKey(SecondActivity.class)
     abstract AndroidInjector.Factory<? extends Activity> bindSecondActivity(SecondComponent.Builder builder);
 
 
 
 }

 ```
 APPModule.class
 ```java
 /**
  * subcomponents 注册了 main 和 second
  */
 @Module(subcomponents = {MainComponent.class, SecondComponent.class})
 public abstract class APPModule {
 
 
     @Binds //@Binds 注解委托代理的 抽象方法；必须是抽象方法；必须有一个参数，参数必须能转换为 返回类型 不然无法生成代码就会报错。
     @APPScoped
     abstract Context provideContext(Application application);
 
 
 }

 ```
 
 Then, 创建 SubComponent（子组件）； 
 
 如果想将我们的 Activity 附加到 Dagger 类图中以便从类图中获取实例，只需为它创建一个 `@SubComponent` 即可。
 
 使用`@SubComponent`注解后要让 Dagger 知道 SubComponent 的信息。
 
 所有的 子组件必须要让它的祖先（Component）知道。  
 
 ```java
 @Subcomponent(modules = MainModule.class)
 public interface MainComponent  extends AndroidInjector<MainActivity> {
 
     @Subcomponent.Builder
     abstract class Builder extends AndroidInjector.Builder<MainActivity>{
 
     }
 }
 ```
 
 在目前的 Demo 里有 MainComponent 和 SecondComponent 两个使用 `@SubComponent` 的类。
 他们都有自己的模块和自己的组件。
 
 - MainComponent 就像上面的定义的那样，他们只是连接模块的桥梁，但这里有一个重大的改变，不再需要添加 `inject()和 build()`。
 在他们的父类 `AndroidInjector` 中已经定义好了，`AndroidInjector` 是 dagger.android 内部定义好的一个类。
 
 - MainModule 这个模块提供 MainActivity 范围内的 实例（例如 MainPresenter MainView等）；
 
 可以看到下面我们使用 MainActivity做了参数，它的实例是怎么被 Dagger拿到的呢？
 **在通过  <MainActivity> 创建 MainComponent 时拿到的，Dagger 会将 activity的实例附加到 我们的 类图上，所以能够使用它。**
 MainModule.class
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
 
 创建完 Activity 层的 Component后要将 Activity 和 SubComponent 添加到 Application 层的 Component 中。
 
 - 让 Dagger 知道 Android 组件的信息，可以看 上面的 ActivityBuilder.class 
 - 让 Component 感知 SubComponent 的信息 看上面的 APPModule.class
 
  ---
   关于 Fragment 层的 Component 
   
   通过上面的 Application 和 Activity 层的关系和操作应该不难猜出 Fragment 应该怎么做了吧。
   
   举一反三，同样的，创建 Fragment 层的 Component & Module；
   然后 在 Activity 层 创建 FragmentBuilder.class 将 Fragment 映射进来作为 ActivityComponent的模块  
   最后将 FragmentComponent 添加到 ActivityComponent 的module中。具体的我在 Demo中有写。可以在GitHub上看看。
   
   Finally, 注入。 构建完组件的类图后 就可以注入了
   
   DispatchingAndroidInjector<T>
   
 ```java
 public class MyApplication extends Application implements HasActivityInjector {
 
     // 不要使用 AndroidInjector<Activity>；
     @Inject DispatchingAndroidInjector<Activity> androidInjector;
     @Override
     public void onCreate() {
         super.onCreate();
         DaggerAPPComponent.builder().application(this).build().inject(this);
     }
 
 
     @Override
     public DispatchingAndroidInjector<Activity> activityInjector() {
         return androidInjector;
     }
 }

 ```
 
 因为 Application 有两个 Activity，所以 实现了 `HasActivityInjector` 接口；
 同样的 如果 Activity 中 有几个 Fragment呢 
 
 ```java
 public class DetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {
 
     @Inject
     DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
 
     //simplified
   
     @Override
     public AndroidInjector<Fragment> supportFragmentInjector() {
         return fragmentDispatchingAndroidInjector;
     }
 }
 ```
   
  如果你的 Activity 中 没有 Fragment 就不需要实现 HasSupportFragmentInjector 了
  
  AndroidInjection.inject(this)
  
  前面所有的工作都是为了注入，但 Activity 和Fragment 都不应该知道 他们是如何被注入的，那么现在该怎么做呢？
  
  在 Activity 
  ```java
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      AndroidInjection.inject(this);
      super.onCreate(savedInstanceState);
  }
  ```
  
  在 Fragment
  
 
 ```java
 @Override
 public void onAttach(Context context) {
     AndroidSupportInjection.inject(this);
     super.onAttach(context);
 }
 ```
 
 配置完成了，Build一下 看看有没有出错，解决不了的 欢迎交流。
 
 这种操作，看着好像更加的复杂了，别着急，这里我只是为了更好的理解 所以 步骤比较复杂，下面我将使用 最简便的方式。
 
 创建 Application 级别的 Component
 
 ```groovy

```
 
 
 源代码 地址 https://github.com/skymxc/Example 