# 生物识别,指纹识别

指纹识别从 6.0 开始支持,在 9.0的时候被废弃 换成 生物识别.

## 指纹识别
(Google 介绍)[https://developer.android.com/about/versions/marshmallow/android-6.0?hl=zh-cn#fingerprint-authentication]


关键类 `FingerprintManager` [FingerprintManager](https://developer.android.com/reference/android/hardware/fingerprint/FingerprintManager.html?hl=zh-cn)

这个类负责对指纹硬件的访问.需要硬件的支持.

有三个公开方法
- void authenticate() 启动指纹识别
- boolean hasEnrolledFingerprints() 判断手机是否已经有录入的指纹 (至少得有一个)
- boolean isHardwareDetected() 判断是否有指纹感应区(硬件支持)

嵌套的类
- FingerprintManager.AuthenticationCallback 回调
- FingerprintManager.AuthenticationResult 结果
- 	FingerprintManager.CryptoObject 加密支持

### 简单的使用步骤
1. 权限
```
<uses-permission
        android:name="android.permission.USE_FINGERPRINT" />
```
2. 判断是否硬件支持
3. 判断是否已录入指纹
4. 识别
5. 回调后关闭

## 生物识别

关键类 BiometricPrompt https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt.html

Android P 之后系统封装了一个 Dialog .可以通过 BiometricPrompt.Builder 设置 标题,描述等等,


两个重载方法
- 	authenticate();

### 简单步骤
1. 权限
```
<uses-permission android:name="android.permission.USE_BIOMETRIC"/>
```
2. 构建 BiometricPrompt
3. 开始识别

## 参考资料
https://www.jianshu.com/p/e8c6938d1b27

keystore 的解释
https://blog.csdn.net/lintcgirl/article/details/51355203

