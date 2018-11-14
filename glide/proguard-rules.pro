-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# 如果你的 target API 低于 Android API 27，请添加：

#-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# VideoDecoder 使用 API 27 的一些接口，这可能导致 proguard 发出警告，尽管这些 API 在旧版 Android 设备上根本不会被调用。

#如果你使用 DexGuard 你可能还需要添加：
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule