# Switch 自定义样式

先上效果

![效果](https://github.com/skymxc/Example/blob/master/widget-switch/image/switch_screen.png)

<br/>
自定义样式就是 更改字体，按钮，轨道样式。

![switch](https://github.com/skymxc/Example/blob/master/widget-switch/image/switch.png)



## 使用的属性
- thumb 按钮
- track 轨道
- showText 是否显示文字，默认不显示 在 false 时，设置了下面两个也不会显示
- textOn  checked= true 时的文字
- textOff checked =false 时的文字
- checked 是否选中
- switchTextAppearance 字体样式




## 更改字体

字体样式通过 switchTextAppearance 属性 指定一个主题

这里以更改字体颜色 为例

> 1. 在 res/color/ 下新建一个文件命名为 switch_selector.xml


声明了两种状态下的颜色
```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:color="@color/colorPrimaryDark" android:state_checked="true"/>
    <item android:color="@android:color/darker_gray" android:state_checked="false"/>

</selector>
```

> 2. 在 styles.xml 里声明一个样式 继承自 style/TextAppearance

设置 textColor 属性为刚才定义的文件
```xml
    <style name="SwitchTheme" parent="@android:style/TextAppearance">
        <item name="android:textColor">@color/switch_selector</item>
    </style>
```

style/TextAppearance 里有很多属性，可以设置
```xml
    <style name="TextAppearance">
        <item name="textColor">?textColorPrimary</item>
        <item name="textColorHighlight">?textColorHighlight</item>
        <item name="textColorHint">?textColorHint</item>
        <item name="textColorLink">?textColorLink</item>
        <item name="textSize">16sp</item>
        <item name="textStyle">normal</item>
    </style>

```

> 3.为 switchTextAppearance 设置值
```
    <Switch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:showText="true"
        android:textOff="off"
        android:textOn="on"
        android:checked="true"
        android:switchTextAppearance="@style/SwitchTheme" />
```

> 4. end

## 更改按钮的样式

按钮是通过 thumb 属性设置的，我是通过定义 shape 图形 更改的样式，用图片替换也是可以的。

> 1. 在 drawable 里定义两个文件 thumb_normal.xml thumb_checked.xml 两个文件，分别是 选中和没选中时的图形

在文件里可以设置尺寸大小

thumb_normal.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <size
        android:width="30dp"
        android:height="30dp" />
    <solid android:color="@android:color/white" />

    <stroke
        android:width="1dp"
        android:color="@android:color/darker_gray" />
</shape>
```

thumb_checked.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <size
        android:width="30dp"
        android:height="30dp" />
    <solid android:color="@android:color/white" />

    <stroke
        android:width="1dp"
        android:color="@color/colorPrimaryDark" />

</shape>
```
> 2. 定义一个 selector 指定在 选中和没选中的样式

thumb_selector.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:drawable="@drawable/thumb_normal" android:state_checked="false"/>

    <item android:drawable="@drawable/thumb_checked" android:state_checked="true"/>
</selector>
```

> 3. 设置给 thumb 属性

```
    <Switch
        android:id="@+id/switch_no_native"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:thumb="@drawable/thumb_selector" />
```
## 更改轨道的值 track

更改轨道的样式和按钮的样式 大同小异，都是指定对应状态的样式

> 1. 定义两个文件 分别是轨道的两个状态 track_normal.xml track_checked.xml

track_normal.xml 没选中的
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">

    <solid android:color="@android:color/darker_gray" />

    <corners android:radius="30dp"/>

</shape>
```

track_checked.xml 选中的
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">

    <solid android:color="@color/colorPrimaryDark"/>

    <corners android:radius="30dp"/>
</shape>
```
> 2. 定义 track_selector.xml 对应两个状态

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:drawable="@drawable/track_normal" android:state_checked="false"/>

    <item android:drawable="@drawable/track_checked" android:state_checked="true"/>
</selector>
```

> 3. 设置给  track 属性

```xml
    <Switch
        android:id="@+id/switch_no_native"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:thumb="@drawable/thumb_selector"
        android:track="@drawable/track_selector" />
```

## 遗留问题

轨道的高度 不知道在哪里设置，我目前的方法是通过设置 thumb 的尺寸。

