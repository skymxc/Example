
## 占位符

三种占位符
glide的占位符是在 UI 线程完成加载的。另外变换也不会被应用到 占位符上。

- placehoder 加载时显示 如果没有设置 error 或 fallback 在加载出错后也将一直显示
- error 加载错误时显示
- fallback 加载的 url/model 为 null 时 显示； 设计 fallback  的主要目的是允许用户指示 null 是否为可接受的正常情况。例如，一个 null 的个人资料 url 可能暗示这个用户没有设置头像，因此应该使用默认头像。然而，null 也可能表明这个元数据根本就是不合法的，或者取不到。 默认情况下Glide将 null 作为错误处理，所以可以接受 null 的应用应当显式地设置一个 fallback Drawable


## 请求选项

Glide 大部分的请求选项都可以 通过 RequestOptions 类 和 apply() 方法完成。
常用的选项有
- 占位符 
- 转换 
- 缓存策略
- 组件特有的设置项

apply() 方法可以被重复调用多次，所以选项可以被组合使用。如果存在冲突，应用冲突的最后一个选项。

RequestOptions 中提供给了 很多常用的 静态 选项，可以直接使用.

如果你正在使用 Generated API，可以直接调用 一些方法，例如：
```
GlideApp.with(fragment)
    .load(url)
    
    .centerCrop()
  
    .into(imageView);
``` 
## 过渡选项

TransitionOptions 用于决定你的加载完成时会发生什么。
 
 使用 TransitionOption 可以应用以下变换：
 - View淡入
 - 与占位符交叉淡入
 - 或者什么都不发生
 
 如果不使用变换，你的图像将会“跳入”其显示位置，直接替换掉之前的图像。为了避免这种突然的改变，你可以淡入view，或者让多个Drawable交叉淡入，
 而这些都需要使用TransitionOptions完成。