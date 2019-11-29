# 生命周期感知组件 Lifecycle



奉上翻译原文地址： [处理生命周期]( https://developer.android.google.cn/topic/libraries/architecture/lifecycle )



生命周期感知组件可以感知其他组件的生命周期，例如 Activity，Fragment等，以便于在组件的生命周期状态变化时做出相应的操作。支持生命感知的组件可以帮你更好的组织代码，让你的代码更轻，更好维护。



对于需要响应生命周期变化的组件，我们通常是在 `Activity` 和 `Fragment`  的生命周期方法里实现一些操作。然而，这种模式会导致代码不好管理，容易出现错误。通过支持生命周期的组件，可以将原本在生命周期方法里的操作移到组件内部。



`androidx.lifecycle` 包提供的接口和类可以帮助我们构建可感知生命周期的组件，这些组件就可以根据 Activity 或者 Fragment 的生命周期状态自行调整行为。



在项目添加生命周期感知组件的依赖，可以参加这个页面：[传送门]( https://developer.android.google.cn/jetpack/androidx/releases/lifecycle )



```groo
  //包含 ViewModel 和 LiveData
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"

    // 或者 - 只包含 ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"

    // Kotlin使用 lifecycle-viewmodel-ktx
 //   implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    // 或者，只包含 LiveData
    implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"

    // 或者，只有 Lifecycle（没有 LiveData,ViewModel）
    implementation "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"

  // Kotlin 使用 kapt 替代 annotationProcessor
    annotationProcessor "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"


    // 如果使用了 Java8 使用这个替代上面的 lifecycle-compiler
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

    // 可选 - ReactStreams 对 LiveData 的支持
    implementation "androidx.lifecycle:lifecycle-reactivestreams:$lifecycle_version"

    //Kotlin 使用 lifecycle-reactivestreams-ktx
    implementation "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"

    // 可选 LiveData 的测试
    testImplementation "androidx.arch.core:core-testing:$lifecycle_version"
```



如果使用的是 Kotlin 记得添加 `kotlin-kapt` 插件



 Android 框架中定义的大多数应用组件都具有生命周期。生命周期是由操作系统或框架代码管理的。

虽然组件的声明周期不由我们控制，但是我们必须尊重组件的生命周期，不然很可能会导致内存泄漏甚至崩溃。



假如我们有个 Activity 在屏幕上显示设备位置信息，最常见的实现可能就是这样了：

Kotlin

```kotlin
internal class MyLocationListener(
        private val context: Context,
        private val callback: (Location) -> Unit
) {

    fun start() {
        // 连接系统位置服务
    }

    fun stop() {
        // 断开系统位置服务
    }
}

class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this) { location ->
            // 更新 UI
        }
    }

    public override fun onStart() {
        super.onStart()
        myLocationListener.start()
        // 管理其他需要响应 Activity 生命周期的组件
    }

    public override fun onStop() {
        super.onStop()
        myLocationListener.stop()
       // 管理其他需要响应 Activity 生命周期的组件
    }
}
```



Java

```java
class MyLocationListener {
    public MyLocationListener(Context context, Callback callback) {
        // ...
    }

    void start() {
        // 连接系统位置服务
    }

    void stop() {
         // 断开系统位置服务
    }
}

class MyActivity extends AppCompatActivity {
    private MyLocationListener myLocationListener;

    @Override
    public void onCreate(...) {
        myLocationListener = new MyLocationListener(this, (location) -> {
         // 更新 UI
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        myLocationListener.start();
        // 管理其他需要响应 Activity 生命周期的组件
    }

    @Override
    public void onStop() {
        super.onStop();
        myLocationListener.stop();
        // 管理其他需要响应 Activity 生命周期的组件
    }
}
```

目前看起来这样还不错，但在真实情况下，可能还会有其他需要响应生命周期的组件，也有可能是在 `onStart()` 和 `onStop()` 。一个两个还好，如果多了的话把这些都放在生命周期方法里，就比较难以维护。



此外，这并不能保证在 Activity 或者 Fragment 停止之前启动我们的组件。特别是那些需要长期运行的操作，例如在 `onStart()` 里的检查配置操作。这就可能会出现在 `onStart()` 里的操作还未启动，而 `onStop()` 里却要停止的情况。

Kotlin

```kotlin
class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this) { location ->
            //更新 UI 
        }
    }

    public override fun onStart() {
        super.onStart()
        Util.checkUserStatus { result ->
            // 如果在活动停止后调用此回调该怎么办？
            if (result) {
                myLocationListener.start()
            }
        }
    }

    public override fun onStop() {
        super.onStop()
        myLocationListener.stop()
    }

}
```



Java

```java
class MyActivity extends AppCompatActivity {
    private MyLocationListener myLocationListener;

    public void onCreate(...) {
        myLocationListener = new MyLocationListener(this, location -> {
            // 更新  UI
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Util.checkUserStatus(result -> {
            // 如果在活动停止后调用此回调该怎么办？
            if (result) {
                myLocationListener.start();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        myLocationListener.stop();
    }
}
```

` androidx.lifecycle` 包提供了一些类和接口，可帮助你以弹性和隔离的方式解决这些问题。 

## 生命周期

 [`Lifecycle`](https://developer.android.google.cn/reference/androidx/lifecycle/Lifecycle.html)  是一个类，它持有相关组件（例如 Activity 和 Fragment）的生命周期状态信息并且可以让其他对象观察到这个状态。

`Lifecycle` 使用两个主要枚举来跟踪相关组件的生命周期状态。



**Event**

`Android` 框架和 `lifecycle` 类发出的生命周期事件。它对应到 Activity 和 fragment 里的生命周期回调。

**State**

`Lifecycle` 类跟踪的相关组件的当前生命周期状态。



![构成 Android Activity 生命周期的事件和状态](picture/lifecycle-states.png)



类可以通过添加注解来侦听组件的生命周期事件。通过调用 `Lifecycle` 的 `addObserver()` 方法传递进去一个你的观察对象即可，如下所示：

Kotlin

```kotlin
class MyObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        ...
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        ...
    }
}

myLifecycleOwner.getLifecycle().addObserver(MyObserver())
```

Java

```java
public class MyObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
        ...
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
        ...
    }
}

myLifecycleOwner.getLifecycle().addObserver(new MyObserver());
```

这个示例中，`myLifecycleOwner` 实现了 `LifecycleOwner` 接口。



## 生命周期所有者



 [`LifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html)  是一个单方法的接口，它表示这个类有生命周期。它有一个类必须实现的方法：  [`getLifecycle()`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html#getLifecycle())   。如果你想管理整个应用进程的生命周期可以看看这个  [`ProcessLifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/ProcessLifecycleOwner.html) 

 

这个接口从单个类中抽象出生命周期的所有权，例如 Activity 和 Fragment，可以与你写的组件共享生命周期。任何类都可以实现 `LifecycleOwner` 接口。

 实现 `LifecycleObserver` 的组件与实现 `LifecycleOwner` 的组件可以无缝地衔接，因为所有者可以提供生命周期，观察者可以注册该生命周期以观察。 



对于上面显示位置的例子，就可以让 ` MyLocationListener`   实现 `LifecycleObserver` ，并在 `Activity` 的生命周期方法 `onCreate()` 里初始化。这样的话 `MyLocationListener`  类就可以自给自足，在自己本身内部实现响应生命周期变化的逻辑处理。每个组件都在自己内部响应生命周期变化就让 `Activity` 和 `Fragment` 的逻辑变得很清晰。



Kotlin

```kotlin
class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this, lifecycle) { location ->
            // 更新 UI
        }
        Util.checkUserStatus { result ->
            if (result) {
                myLocationListener.enable()
            }
        }
    }
}
```



Java

```java
class MyActivity extends AppCompatActivity {
    private MyLocationListener myLocationListener;

    public void onCreate(...) {
        myLocationListener = new MyLocationListener(this, getLifecycle(), location -> {
            // 更新 UI
        });
        Util.checkUserStatus(result -> {
            if (result) {
                myLocationListener.enable();
            }
        });
  }
}
```

 一个常见的用例是，如果生命周期当前状态不佳，则避免调用某些回调。  例如，如果回调在保存活动状态后运行 `Fragment` 事务，那么它将触发崩溃，因此我们永远都不想调用该回调。 

 

为了简化此用例，`Lifecycle` 类允许其他对象查询当前状态。 通过方法: [Lifecycle.State.isAtLeast()]( https://developer.android.google.cn/reference/androidx/lifecycle/Lifecycle.State.html#isAtLeast(androidx.lifecycle.Lifecycle.State) )



Kotlin

```kotlin
internal class MyLocationListener(
        private val context: Context,
        private val lifecycle: Lifecycle,
        private val callback: (Location) -> Unit
) {

    private var enabled = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enabled) {
            // 连接
        }
    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // 如果还没连接，就去连接
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        // 如果连接了就断开
    }
}
```



Java

```java
class MyLocationListener implements LifecycleObserver {
    private boolean enabled = false;
    public MyLocationListener(Context context, Lifecycle lifecycle, Callback callback) {
       ...
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (enabled) {
           // 连接
        }
    }

    public void enable() {
        enabled = true;
        if (lifecycle.getCurrentState().isAtLeast(STARTED)) {
            // 如果还没连接，就去连接
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        // 如果连接了就断开
    }
}
```

通过以上实现，我们的 `LocationListener` 已经具备了感知生命周期的能力并且可以做出相应的操作。如果其他的 `Activity` 或者 `Fragment` 想使用它，只需要初始化它即可。其他所有操作都由 `LocationListener` 自己处理。



 如果你的库提供了需要与 Android 生命周期一起使用的类，则建议使用可识别生命周期的组件。 你的库可以轻松集成这些组件，而无需在客户端进行手动生命周期管理。 



### 自定义生命周期所有者

 支持库 26.1.0 以及更高版本中的 `Fragment` 和 `Activity` 已经实现了  [`LifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html)  接口。 

 如果想要创建 `LifecycleOwner` 的自定义类，则可以使用  [`LifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html)  类，但是需要将事件转发到该类中，如以下代码示例所示： 

Kotlin

```kotlin
class MyActivity : Activity(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    public override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
```



Java

```java
public class MyActivity extends Activity implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
```

 ## 生命周期感知组件的最佳实践 



- 尽可能的让 UI 控制器（Activity 和 Fragment） 保持精简。让 ViewModel 去获取数据，数据更改通过 LiveData 响应到视图。
- 尝试编写数据驱动的 UI ，其中 UI 控制器的职责是在数据更改时更新视图，或者将用户操作通知给 ViewModel 。
- 将数据业务逻辑放在 ViewModel 类。ViewModel 类的定位应该是 UI 控制器和应用中其他部分的连接器。但并不是说让 ViewModel 类去获取数据，相反的应该让其他合适的组件去获取数据，ViewModel 类只是把结果提供给 UI 控制器。
- 使用数据绑定库维护视图和 UI 控制器的整洁。这让视图更具声明性，并减少在 UI 控制器的更新代码。如果你倾向于使用 Java ，可以使用  [Butter Knife](http://jakewharton.github.io/butterknife/)   减少重复代码。
- 如果 UI 过于复杂，可以考试创建一个 Presenter 类管理 UI 更新，这可能更麻烦，但是可以更好的管理 UI 。
- 避免在 ViewModel 引用 View和 Activity 上下文。如果 ViewModel 生命超过 Activity （配置发生更改的情况下）可能会造成 Activity 泄漏，并且不被垃圾处理器回收。
-  使用 Kotlin 协程来管理长时间运行的任务以及可以异步运行的其他操作。 



 ##  生命周期感知组件的用例 



生命周期感知组件可以让你在各种情况下都很好的管理生命周期，例如：

-  在粗略和细粒度的位置更新之间切换。 使用生命周期感知组件在应用可见时启用细粒度的位置更新，在应用处于后台时切换到粗粒度的更新。
- 停止和开启视频缓冲。 使用支持生命周期的组件尽快开始视频缓冲，但是将播放推迟到应用程序完全启动。 还可以使用可识别生命周期的组件在应用程序销毁时终止缓冲。 
-  启动和停止网络连接。  使用可感知生命周期的组件可以在应用程序处于前台状态时实时更新（流式传输）网络数据，并在应用程序进入后台时自动暂停。 
-  暂停和恢复动画绘制。  当应用程序在后台运行时，使用生命周期感知组件处理暂停动画绘制，并在应用程序在前台运行后恢复绘制。 



## 处理停止事件



当生命周期属于 `AppCompatActivity` 或 `Fragment` 时，生命周期的状态更改为 `CREATED` ，并且在调用 `AppCompatActivity` 或 `Fragment` 的 `onSaveInstanceState（）` 时调度 `ON_STOP` 事件。 



 当通过 `onSaveInstanceState（）` 保存 `Fragment` 或 `AppCompatActivity` 的状态时，在调用 `ON_START` 之前，它的 UI 被认为是不可变的。  保存状态后尝试修改 UI 可能会导致应用程序的导航状态不一致，这就是为什么如果状态保存后应用程序运行 `FragmentTransaction` ，则 `FragmentManager` 会引发异常的原因。 详情参见： [commit()](https://developer.android.google.cn/reference/androidx/fragment/app/FragmentTransaction.html#commit()) 



 如果观察者的关联生命周期至少不是 `STARTED`， `LiveData` 不会调用观察者，从而避免了这种极端情况。  在幕后，它在决定调用其观察者之前调用 `isAtLeast（）` 判断当前状态。 



不幸的是，在 `onSaveInstanceState（）` 之后调用了 `AppCompatActivity ` 的 `onStop（）` 方法，这留下了一个空白，在该空白中，不允许 UI 状态更改，但生命周期尚未移至 `CREATED` 状态。 

 

为避免此问题，版本 `beta2` 及更低版本中的 `Lifecycle` 类将状态标记为 `CREATED` 而不调度事件，因此，即使直到系统调用了 `onStop（）` 才调度事件，任何检查当前状态的代码都将获得真实值。



 不幸的是，此解决方案有两个主要问题： 

-  在 API 级别 23 和更低级别上，Android 系统实际上会保存 `Activity` 的状态，即使该 `Activity` 已被另一个 `Activity` 部分覆盖 。 换句话说，Android 系统调用 `onSaveInstanceState（）` ，但不一定调用 `onStop（）` 。  这将创建一个可能较长的时间间隔，在该时间间隔中，即使无法修改其 UI 状态，观察者仍认为生命周期处于活动状态。 
-  任何要向 `LiveData`  类公开类似行为的类都必须实现 `Lifecycle`  beta 2 及更低版本提供的解决方法。 



>  **注意：** 为了简化流程并提供与旧版本的更好兼容性，从版本 1.0.0-rc1 开始，生命周期对象被标记为`CREATED` ，并且在调用 `onSaveInstanceState（）` 时分派 `ON_STOP` ，而无需等待对 `onStop（）` 的调用。  这不太可能影响你的代码，但是需要注意这一点，因为它与 API 级别 26 及更低级别的 `Activity` 类中的调用顺序不匹配。 



参考资料

 [Lifecycle（使用篇）](https://juejin.im/post/5db27753518825648f2ef5c9 )

---

> End
---
---

# LiveData

> 奉上原文翻译地址 ： [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata)

 [`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html)  是一个持有数据的可观察类。不同于普通的可观察者，它是可感知应用组件（如，Activity ，Fragment ，Service ）的生命周期的。这就能保证 LiveData 在组件生命周期是活跃状态时更新。



LiveData 的观察者是  [`Observer`](https://developer.android.google.cn/reference/androidx/lifecycle/Observer.html) 表示的类，如果观察者（LiveData）的生命周期处于STARTED 或 RESUMED 状态，就表示的观察者处于活跃状态。 LiveData 只会将更新通知给活跃的观察者，不活跃的观察者不会通知。



可以注册一个实现了  [`LifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html)  的类观察者。这种关联可以在生命周期处于  [`DESTROYED`](https://developer.android.google.cn/reference/androidx/lifecycle/Lifecycle.State.html#DESTROYED)  时移除观察者。这对 Activity 和 Fragment 很有用了，不用担心泄漏问题，实现安全的观察 LiveData 对象。



生命周期可参考：[生命周期感知]( https://www.cnblogs.com/skymxc/p/lifecycle.html ) 。

 ## 使用LiveData的优点

- 确保 UI 和数据状态匹配

  LiveData 遵循观察者模式。 生命周期状态更改时，LiveData 会通知 Observer 对象。你可以合并代码以更新这些 Observer 对象中的 UI 。观察者可以在每次更改应用程序时更新 UI ，而不是在每次应用程序数据更改时都更新 UI 。

- 没有内存泄漏

  观察者绑定  [`Lifecycle`](https://developer.android.google.cn/reference/androidx/lifecycle/Lifecycle.html)  对象，并在 Lifecycle 被销毁时自己清理。

- 不会由于 Activity 停止而崩溃

  如果观察者不是活跃状态，不会收到 LiveData 的任何事件，例如 Activity 处于后台堆栈时。

-  不再需要人工生命周期处理

  观察者只需要观察数据，其他关于生命周期的操作都由 LiveData 管理，LiveData 是可以感知生命周期状态变化的。

- 保证数据最新

  如果生命周期处于不活跃状态，是不会收到任何数据的。一旦从后台转变为前台也就是说一旦转变为活跃状态会立即收到最新的数据。

-  正确的配置更改

   如果由于配置更改（例如设备旋转）而重新创建 Activity 或 Fragment，则该 Activity 或 Fragment 将立即接收最新的可用数据。

-  共享资源

   可以使用单例模式扩展 LiveData 对象以包装系统服务，以便可以在应用中共享。LiveData 对象一旦连接到系统服务，然后任何需要资源的观察者都可以观看 LiveData 对象。 更详细的看下面的扩展 LiveData 。

## 使用 LiveData

按照以下步骤使用 LiveData：

1. 创建持有某类数据的 LiveData，通常是在 ViewModel 里创建。

2. 创建有 `onChanged()` 方法的 Observer ，这个方法会在 LiveData 持有的数据更改时回调。将 Observer 订阅到 LiveData ，以便于在发生更改时通知到 Observer 。通常是在 UI 控制器里定义 Observer，如：Activity ，Fragment。

3. 使用 LiveData  的  [`observe()`](https://developer.android.google.cn/reference/androidx/lifecycle/Observer.html)   将 `Observer` 对象附加到 LiveData 对象。这个 `observe()` 还接收一个  [`LifecycleOwner`](https://developer.android.google.cn/reference/androidx/lifecycle/LifecycleOwner.html) 对象。将 Observer  订阅到 LiveData ，以便于在发生更改时通知到 Observer 。通常是在 UI 控制器里订阅，如：Activity ，Fragment。

   **注意：** *可以在不关联 `LifecycleOwner` 的情况下使用  [`observeForever(Observer)`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html#observeForever(android.arch.lifecycle.Observer%3CT%3E))  注册观察者 ，这种情况下，观察者就是一直处于活跃状态。可以使用   [`removeObserver(Observer)`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html#removeObserver(android.arch.lifecycle.Observer%3CT%3E))  方法移除。*



 更新存储在 LiveData 对象中的值时，只要连接的 LifecycleOwner 处于活动状态，它就会触发所有注册的观察者。



 LiveData 允许 UI 控制器观察者订阅更新。当 LiveData 对象保存的数据更改时，UI 会自动更新以响应。



### 创建 LiveData 对象

LiveData 是一个包装器，可以包装任何数据，包括集合，如 List。LiveData 通常是定义在 ViewModel 里的，通过 getter 方法访问，如下所示：

Kotlin

```Kotlin
class NameViewModel : ViewModel() {

    // Create a LiveData with a String
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // Rest of the ViewModel...
}
```

Java

```java
public class NameViewModel extends ViewModel {

// Create a LiveData with a String
private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

// Rest of the ViewModel...
}
```



刚开始，LiveData 对象里的数据是没有初始化的。

**注意：** *为什么将更新 UI 的 LiveData 放在ViewModel的原因如下：<br/><br/>1. 避免 UI 控制器过于臃肿，现在 UI控制器只负责显示数据，不持有数据，不负责维护数据状态。<br/><br/>2. 将 LiveData 与 Activity 和 Fragment 的实例解耦，让 LiveData 在发生配置更改时继续存在。*



### 观察 LiveData 对象

通常是在应用组件的 `onCreate()` 方法里观察 LiveData，有以下几点原因：

- 确保不会在 `onResume()` 多次冗余调用
- 确保 Activity 或者 Fragment 在处于活跃状态时立马展示数据。只要 Activity 或者 Fragment 处于 CREATED 状态，会立即收到订阅的 LiveData 的最新数据。所以，这种情况只有订阅了 LiveData 才行。

通常，LiveData 在数据更改时才会发送更改，有一个例外是当观察者从非活跃状态转为活跃状态时也会接收到更改，当观察者第二次转变为活跃状态时，如果值较上次活跃状态时已更改，才会接到更新。

下面演示了怎么观察 LiveData 对象：

Kotlin

```kotlin
class NameActivity : AppCompatActivity() {

    private lateinit var model: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 得到 ViewModel.
        model = ViewModelProviders.of(this).get(NameViewModel::class.java)

        // 创建更改 UI 的观察者
        val nameObserver = Observer<String> { newName ->
            // 更改 UI
            nameTextView.text = newName
        }

        //订阅 LiveData 将 activity 作为 LifecycleOwner 传递。
        model.currentName.observe(this, nameObserver)
    }
}
```

Java

```java
public class NameActivity extends AppCompatActivity {

    private NameViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 得到 ViewModel.
        model = ViewModelProviders.of(this).get(NameViewModel.class);

        // 创建更改 UI 的观察者
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // 更改 UI
                nameTextView.setText(newName);
            }
        };

        // 订阅 LiveData 将 activity 作为 LifecycleOwner 传递。
        model.getCurrentName().observe(this, nameObserver);
    }
}
```

在使用作为参数传递的 nameObserver 调用 `observe()` 后，会立即调用 `onChanged()` ，以提供 `mCurrentName` 中存储的最新值。如果 LiveData 对象尚未在 mCurrentName 中设置值，则不会调用 `onChanged()` 。



### 更改 LiveData 对象



LiveData 没有可用的方法更存储的值。 [`MutableLiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/MutableLiveData.html) 类公开了 [`setValue(T)`](https://developer.android.google.cn/reference/androidx/lifecycle/MutableLiveData.html#setValue(T))  和  [`postValue(T)`](https://developer.android.google.cn/reference/androidx/lifecycle/MutableLiveData.html#postValue(T))  方法编辑 LiveData 存储的值，要编辑 LiveData 里存储的值，只能通过这个两个方法。通常是 把 MutableLiveData 定义在 ViewModel 内部，只对观察者暴露 LiveData 。



建立观察者关系之后，更改 LiveData 的值就会触发给所有观察者，如下所示，点击按钮就会发送给所有观察者：

Kotlin

```kotlin
button.setOnClickListener {
    val anotherName = "John Doe"
    model.currentName.setValue(anotherName)
}
```

Java

```java
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
        String anotherName = "John Doe";
        model.getCurrentName().setValue(anotherName);
    }
});
```

示例中，调用 `setValue(T)` 会触发观察者调用 `onChanged(T)` 方法并传递 “John Doe”。例子里只是在按下按钮调用的，在实际情况下， `setValue(T)`  和 `postValue(T)` 可能在很多种情况下被调用，例如 网络请求结果，数据库加载完毕等等。无论什么情况，调用这两个方法都会触发观察者去更新 UI 。

**注意：** *`setValue(T)` 必须在主线程调用，如果在其他线程则改用 `postValue(T)` 。*



### 在 Room 中使用 LiveData

  [Room持久化库](https://developer.android.google.cn/training/data-storage/room/index.html) 是支持可观察查询的，可观察查询会返回 LiveData 对象。可观察查询是 DAO 的一部分，是写在一起的。



 在更新数据库时，Room 生成所有必需的代码来更新 LiveData 对象。  需要时，生成的代码在后台线程上异步运行查询。  此模式对于在 UI 中显示的数据与数据库中存储的数据保持同步非常有用。 更详细的可以查   [Room 持久化库指南](https://developer.android.google.cn/topic/libraries/architecture/room.html).  之前翻译过一篇 [Android Room 持久化库]( https://blog.skymxc.com/2018/04/15/Room/ ) 。



###  将协程与 LiveData 一起使用

 LiveData 包括对 Kotlin 协程的支持。 更多信息参考： [将 Kotlin 与架构组件一起使用](https://developer.android.google.cn/topic/libraries/architecture/coroutines) 。



## 扩展 LiveData

 如果观察者的生命周期处于 STARTED 或 RESUMED 状态，则 LiveData 认为观察者处于活跃状态。下面的示例代码说明了如何扩展 LiveData 类：

Kotlin

```kotlin
class StockLiveData(symbol: String) : LiveData<BigDecimal>() {
    private val stockManager = StockManager(symbol)

    private val listener = { price: BigDecimal ->
        value = price
    }

    override fun onActive() {
        stockManager.requestPriceUpdates(listener)
    }

    override fun onInactive() {
        stockManager.removeUpdates(listener)
    }
}
```

Java

```java
public class StockLiveData extends LiveData<BigDecimal> {
    private StockManager stockManager;

    private SimplePriceListener listener = new SimplePriceListener() {
        @Override
        public void onPriceChanged(BigDecimal price) {
            setValue(price);
        }
    };

    public StockLiveData(String symbol) {
        stockManager = new StockManager(symbol);
    }

    @Override
    protected void onActive() {
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        stockManager.removeUpdates(listener);
    }
}
```

这个类实现了下面这个三个方法：

-  [`onActive()`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html#onActive())  当 LiveData 有活跃的观察者时，就会调用这个方法。 这意味着需要从这个方法开始观察股价更新。

-  [`onInactive()`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html#onInactive()) 当 LiveData 没有活跃的观察者时会调用这个方法。 由于没有观察者在观察，因此没有理由保持与 StockManager 服务的连接。

-  [`setValue(T)`](https://developer.android.google.cn/reference/androidx/lifecycle/MutableLiveData.html#setValue(T))  更新 LiveData 对象的值，并通知所有的观察者。

然后就可以使用 StockLiveData 了

Kotlin

```kotlin
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val myPriceListener: LiveData<BigDecimal> = ...
    myPriceListener.observe(this, Observer<BigDecimal> { price: BigDecimal? ->
        // 更新 UI.
    })
}
```

Java

```java
public class MyFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LiveData<BigDecimal> myPriceListener = ...;
        myPriceListener.observe(this, price -> {
            // 更新 UI.
        });
    }
}
```

`observer()` 传递的第一个参数 fragment 是 实现了 LifecycleWoner 的，这表示观察者绑定了这个生命周期所有者，这就意味着：

1. 当生命周期对象处于不活跃的状态时，观察者是不会收到更新的，即使值变化了。
2. 当生命周期对象被销毁，观察者也会被自动移除。



LiveData 对象具有生命周期感知这就意味着你可以在多个 Activity，Fragment 和 Service 之间共享它们。  为了简化示例，可以按如下方式将 LiveData 类实现为单例：



Kotlin

```kotlin
class StockLiveData(symbol: String) : LiveData<BigDecimal>() {
    private val stockManager: StockManager = StockManager(symbol)

    private val listener = { price: BigDecimal ->
        value = price
    }

    override fun onActive() {
        stockManager.requestPriceUpdates(listener)
    }

    override fun onInactive() {
        stockManager.removeUpdates(listener)
    }

    companion object {
        private lateinit var sInstance: StockLiveData

        @MainThread
        fun get(symbol: String): StockLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else StockLiveData(symbol)
            return sInstance
        }
    }
}
```



Java

```java
public class StockLiveData extends LiveData<BigDecimal> {
    private static StockLiveData sInstance;
    private StockManager stockManager;

    private SimplePriceListener listener = new SimplePriceListener() {
        @Override
        public void onPriceChanged(BigDecimal price) {
            setValue(price);
        }
    };

    @MainThread
    public static StockLiveData get(String symbol) {
        if (sInstance == null) {
            sInstance = new StockLiveData(symbol);
        }
        return sInstance;
    }

    private StockLiveData(String symbol) {
        stockManager = new StockManager(symbol);
    }

    @Override
    protected void onActive() {
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        stockManager.removeUpdates(listener);
    }
}
```



然后，按照以下方式使用

Kotlin

```kotlin
class MyFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        StockLiveData.get(symbol).observe(this, Observer<BigDecimal> { price: BigDecimal? ->
            // Update the UI.
        })

    }
```

Java

```java
public class MyFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        StockLiveData.get(symbol).observe(this, price -> {
            // Update the UI.
        });
    }
}
```

 多个 Activity 和 Fragment 可以观察 MyPriceListener 实例。 LiveData 仅在其中一个或多个可见且处于活动状态时才连接到系统服务。



## 转换 LiveData

如果你想在 LiveData 发送通知之前更改发送的数据，或者需要一个与分发的数据不同的值。[`Lifecycle`](https://developer.android.google.cn/reference/android/arch/lifecycle/package-summary.html) 包里提供了个  [`Transformations`](https://developer.android.google.cn/reference/androidx/lifecycle/Transformations.html) 类，它有些方法可以支持这个场景。



**[`Transformations.map()`](https://developer.android.google.cn/reference/androidx/lifecycle/Transformations.html#map(android.arch.lifecycle.LiveData%3CX%3E,%20android.arch.core.util.Function%3CX,%20Y%3E))**

   对 LiveData 对象中存储的值应用一个函数，并将结果传到下游。

 Kotlin

```kotlin
val userLiveData: LiveData<User> = UserLiveData()
val userName: LiveData<String> = Transformations.map(userLiveData) {
    user -> "${user.name} ${user.lastName}"
}
```

Java

```java
LiveData<User> userLiveData = ...;
LiveData<String> userName = Transformations.map(userLiveData, user -> {
    user.name + " " + user.lastName
});
```



**[`Transformations.switchMap()`](https://developer.android.google.cn/reference/androidx/lifecycle/Transformations.html#switchMap(android.arch.lifecycle.LiveData%3CX%3E,%20android.arch.core.util.Function%3CX,%20android.arch.lifecycle.LiveData%3CY%3E%3E)))**

与 `map()` 类似， 将函数应用于存储在 LiveData 对象中的值，然后解包并向下游分派结果。   传递给switchMap（）的函数必须返回一个LiveData对象，如以下示例所示：

Kotlin

```kotlin
private fun getUser(id: String): LiveData<User> {
  ...
}
val userId: LiveData<String> = ...
val user = Transformations.switchMap(userId) { id -> getUser(id) }
```

Java

```java
private LiveData<User> getUser(String id) {
  ...;
}

LiveData<String> userId = ...;
LiveData<User> user = Transformations.switchMap(userId, id -> getUser(id) );
```

 可以使用转换方法在观察者的整个生命周期中传递信息。 除非观察者正在观察返回的 LiveData 对象，否则不会计算转换。由于转换是懒计算的，因此与生命周期相关的行为会隐式传递，而无需其他显式调用或依赖项。



 如果你认为需要在 ViewModel 对象中使用 Lifecycle 对象，则转换可能是一个更好的解决方案。  例如，假设有一个 UI 组件，该组件接受一个地址并返回该地址的邮政编码。可以为此组件实现简单的 ViewModel，如以下示例代码所示：

Kotlin

```kotlin
class MyViewModel(private val repository: PostalCodeRepository) : ViewModel() {

    private fun getPostalCode(address: String): LiveData<String> {
        // DON'T DO THIS
        return repository.getPostCode(address)
    }
}
```



Java

```java
class MyViewModel extends ViewModel {
    private final PostalCodeRepository repository;
    public MyViewModel(PostalCodeRepository repository) {
       this.repository = repository;
    }

    private LiveData<String> getPostalCode(String address) {
       // DON'T DO THIS
       return repository.getPostCode(address);
    }
}
```



然后，UI 组件每次调用 `getPostalCode()` 时都需要从先前的 LiveData 对象中注销并注册到新实例。  此外，如果重新创建了 UI 组件，则它会触发另一个对 `repository.getPostCode()` 方法的调用，而不是使用先前调用的结果。

 相反，可以将邮政编码查找实现为地址输入的转换，如以下示例所示：

Kotlin

```kotlin
class MyViewModel(private val repository: PostalCodeRepository) : ViewModel() {
    private val addressInput = MutableLiveData<String>()
    val postalCode: LiveData<String> = Transformations.switchMap(addressInput) {
            address -> repository.getPostCode(address) }


    private fun setInput(address: String) {
        addressInput.value = address
    }
}
```

Java

```java
class MyViewModel extends ViewModel {
    private final PostalCodeRepository repository;
    private final MutableLiveData<String> addressInput = new MutableLiveData();
    public final LiveData<String> postalCode =
            Transformations.switchMap(addressInput, (address) -> {
                return repository.getPostCode(address);
             });

  public MyViewModel(PostalCodeRepository repository) {
      this.repository = repository
  }

  private void setInput(String address) {
      addressInput.setValue(address);
  }
}
```

 在这种情况下，postalCode 字段定义为 addressInput 的转换。  只要应用程序具有与 postalCode 字段关联的活跃观察者，只要 addressInput 发生更改，便会重新计算并检索该字段的值。



 此机制允许较低级别的应用程序创建按需懒计算的 LiveData 对象。  ViewModel 对象可以轻松获取对LiveData 对象的引用，然后在它们之上定义转换规则。

### 创建新的转换



 可以在应用中使用多种具体的转换，但默认情况下未提供这些转换。  要实现自己的转换，可以使用 [`MediatorLiveData`]( https://developer.android.google.cn/reference/androidx/lifecycle/MediatorLiveData.html ) 类，该类侦听其他 LiveData 对象并处理它们发出的事件。 MediatorLiveData 正确地将其状态传播到源 LiveData 对象。   要了解有关此模式的更多信息，请参见  [`Transformations`](https://developer.android.google.cn/reference/androidx/lifecycle/Transformations.html) 类的参考文档。

## 合并多个 LiveData 源

 MediatorLiveData 是 LiveData 的子类，可以合并多个 LiveData 源。  每当任何原始 LiveData 源对象发生更改时，就会触发 MediatorLiveData 对象的观察者。



 例如，如果 UI 中有一个 LiveData 对象，可以从本地数据库或网络更新该对象，则可以将以下源添加到 MediatorLiveData 对象：

-  与数据库中存储的数据关联的 LiveData 对象。
-  与从网络访问的数据关联的 LiveData 对象。

 你的 Activity 仅需要观察 MediatorLiveData 对象即可从两个来源接收更新。 更详细的文档可以参考 [架构应用指南](https://developer.android.google.cn/jetpack/docs/guide  ) 里的 [附录：公开网络状态]( https://developer.android.google.cn/jetpack/docs/guide#addendum ) 。
