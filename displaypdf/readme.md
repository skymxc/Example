# Android 上预览 PDF 文件

最近在 手机上要显示 PDF 文件，在搜索引擎上找到了很多方案，大体上有以下几种：
- 使用提供的在线服务，例如 Google 文档预览服务，mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url="+ pdfUrl);
- 使用 AndroidPdfViewer，这是一个 GitHub 上开源的库，除了体积大点别的都挺好， https://github.com/barteksc/AndroidPdfViewer
- 使用 Moliza 开源的 Pdf.js 这个库是很强大的，配合 WebView ，可以支持预览，缩放，翻页等等功能
- 使用 腾讯浏览服务 https://x5.tencent.com/

我是选择了 pdf.js 这个库，使用 WebView 配合 H5 页面，可以做到随意的自定义，并且体积很小，放在服务器的话就更小了。

- 使用资源：https://github.com/mozilla/pdf.js
- 版本 版本是 2.3.200


> 在这里记录下使用的过程也方便后来者。

考虑到网络不稳定的情况，所以我把 js 库下载下来了，不介意的可以直接使用网络库

- https://www.jsdelivr.com/package/npm/pdfjs-dist
- https://cdnjs.com/libraries/pdf.js
- https://unpkg.com/pdfjs-dist/



刚开始使用时，直接使用提供的 API 创建 canvas ，每一页创建一个 canvas 然后排列下来，因为有其他的内容要显示。
开发测试的时候因为文件小，并且是在电脑上浏览的没有发现什么问题，在手机上测试的时候使用了一个稍微大点的文件，内存立马就爆了。
因为是在加载完成后，每页都创建一个 canvas 显示，没有做到逐页加载，也没有做任何循环使用和销毁的处理，内存就爆了。

在 pdf.js 的例子里看到了在手机上使用的例子，就改了改，它这个做到了循环使用 canvas，并且是逐页加载。
修改之后在手机上使用 32M 的文件轻松无压力。[示例，传送门](https://github.com/mozilla/pdf.js/tree/master/examples/mobile-viewer)

![memory.png](https://github.com/skymxc/Example/blob/master/displaypdf/images/memory.png)

简单写了个 Demo :https://github.com/skymxc/Example/tree/master/displaypdf

assets 目录是修改后的页面。


显示网络文件还是要下载到本地才可以，因为跨域访问的限制问题。

![memory.png](https://github.com/skymxc/Example/blob/master/displaypdf/images/screen-0.jpg)
![memory.png](https://github.com/skymxc/Example/blob/master/displaypdf/images/screen-1.jpg)


End



