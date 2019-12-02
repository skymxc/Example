# LiveData

> 奉上原文翻译地址 ： [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata)

 [`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html)  是一个持有数据的可观察类。不同于普通的可观察者，它是可感知应用组件（如，Activity ，Fragment ，Service ）的生命周期的。这就能保证 LiveData 在组件生命周期是活跃状态时更新。



LiveData 的观察者是  [`Observer`](https://developer.android.google.cn/reference/androidx/lifecycle/Observer.html) 表示的类，如果观察者（LiveData）的生命周期处于 STARTED 或 RESUMED 状态，就表示的观察者处于活跃状态。 LiveData 只会将更新通知给活跃的观察者，不活跃的观察者不会通知。



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

2. 创建有 `onChanged()` 方法的 Observer ，这个方法会在 LiveData 持有的数据更改时回调。通常是在 UI 控制器里定义 Observer，如：Activity ，Fragment。

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

这个类实现了下面这三个方法：

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

