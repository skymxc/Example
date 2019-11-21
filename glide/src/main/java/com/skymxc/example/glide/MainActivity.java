package com.skymxc.example.glide;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.skymxc.example.glide.module.GlideApp;
import com.skymxc.example.glide.module.GlideRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener {

    public static final String TOP_URL = "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg";

    static List<String> list = new ArrayList<>();

    static {
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/80d67054-2fcf-47d7-863f-3bd5d87ebd3f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/615ccabd-c2fd-44d5-a3f3-ba5840a0696a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f01ce6fe-e4d9-4606-89ae-98e193462da2.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/2e78b36e-87e5-4393-86f4-98202a826cab.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/78c7f15a-601b-42b4-b0d6-8d788a8d74bc.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/53ee042a-112f-439d-97b2-92a003ac97ac.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/7f152750-bbfe-404d-b90e-f743c0bf2da8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/270f135e-a186-4d5d-bf08-aca0cb24dada.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/16a9e5d3-770f-4199-b6d0-3303b0d5fa5a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/a38bf818-b7c7-4a86-946f-312bd56dde59.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d399cd88-8ad2-4b49-b06a-e05760623ea1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/cecaa505-6c9b-436c-969e-a6578a289c14.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/20437559-466c-41a1-9cf3-0bcf4b499618.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/167e3c2f-c612-44ff-9bb1-2d23f55f2722.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/faef441e-6d97-4495-bfa5-98c80d7ca158.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/3954a401-93ce-44bc-b224-d1cdb505d7e4.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/0c6d581b-1ec8-4135-af2c-515e95c10aca.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/7c7bc98a-2331-4dfd-a15c-2cdeff796d86.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/8663de81-089f-4a60-9d40-4ca92e928475.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/15737456-777b-459b-9c91-5b7575146398.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1a9719ef-a1a0-43b3-bfb3-b4ea92311b80.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f9605c27-0130-4056-81a1-95f4672a5bdf.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ee3568df-6ac8-44f3-a552-a68a6e6386a7.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/52cfc71f-b367-40c5-b68b-4366eaf09a83.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/4bd61ff2-b2dc-4af5-8b60-f9a02f4b21a7.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/b9aae53d-ceb7-4868-b6ab-53b64b8424fd.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/3712959c-6210-4626-91e8-02c863bb2997.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/aeb0ff29-6805-4a11-8d07-66e811e73f55.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/07de5c7e-fb99-4545-9b0e-f81000d889c9.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/30d9b7a9-53a9-490d-984f-c4bb94f01ee4.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f30b2060-4970-439c-a688-d6b067fa0989.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/a81bb25a-bee8-48e2-82f9-ef6eaa020384.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/231d8e29-3ca9-4858-9157-93e32ca59ff6.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/0e6e885c-72bd-481d-b5a8-d41b014b5e2b.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/300d0667-6ac9-406c-856b-ba931381145a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1e2d3717-7959-4dd8-9ca8-feedbd884e36.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/03453897-e430-4a3b-a7cc-29e347914192.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/2ed63f27-dac0-4e7e-a5ee-4a89de67803f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f69812d6-6be7-4133-b877-9bb67cc63f76.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/171ad138-78c2-4a05-95f2-1ba2d3f49293.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/db90d917-aec3-46a2-9502-91e7b5dac8b1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/bcf6a686-b18e-4a1f-ad96-7301f5a7a109.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/85e9558a-9d36-4267-bc94-0524063b184c.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/5061facf-0ba6-4dee-bfab-a506ea81af19.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/b13693bb-84f9-4b62-9f97-14d8405ad9a9.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/2932444b-dcb7-4da2-b898-4cdcaa4bac65.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9b5f7826-d154-4ed7-beaa-43f2f2629e1f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/018bda28-47c0-4f30-a4e6-78e50c26e399.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1cff734e-ec75-4b40-b0a9-4ca9c6e18ac0.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/dde64b2b-c9f6-45f0-80af-c4e47f9dfbf5.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/dcdfe2e7-1c88-4030-a434-b8f5b3a1cd9b.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f8d5349f-31a1-4a9b-96fb-bbcd150555a1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/c38953af-dd53-4092-bb54-7952235e0723.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/6347ed7f-56b7-4004-86c9-a403f8b24c36.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ff3775c2-a9d6-44cf-b03c-5f3f346572f7.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/a72057c0-4b40-4c16-806e-7efcd8e3b783.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/cc06b757-9afb-45d0-9bbe-f915570f4c44.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f7ea3883-f6cd-4a5e-ba80-718fa3ce3908.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/bd624580-d1f9-4ae7-b6c5-4e8d9815989d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1d9a170c-d545-4493-8bd0-6ea937962922.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d8dc646f-6d31-4547-8b62-c0a66d95c423.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ed66093e-855d-47d2-a238-8819825ea53e.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e0c1ac19-f403-4f7c-9e9d-39181cd365e3.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/07a98a9b-00a6-49cb-ac89-9145523a56c8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/76a3b5ef-f1ea-4e77-a692-54a530e0ec7a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e4d7f224-de2e-4c8e-837d-afdc1e81cea9.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/59df0fde-1748-47ae-9401-70584f3d9803.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/6bc2edb0-30cb-408e-b74f-f2abea2b103f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/a51083a2-6365-42e4-81b4-1f819036af90.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/613b45fd-3125-4c91-83c1-906f55d92dd8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/0fe61416-e5f3-497a-9ba9-57bca450a145.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/5a1e00b8-868b-4e1f-817f-b823d1df2c7a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9de55aa5-5b90-4711-8b4a-063a47dc5fc6.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f707dd2d-70ee-4ce5-9c6c-bedca68ffff2.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d3c27cbf-5b3a-4993-94b3-f949ec59dd9d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/42aece52-be0f-4fb8-bbf7-37aea58ff375.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/a3813c4d-129a-418b-84ee-7553e6672c43.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/8ec88209-2651-42fd-80e5-40d28630bcd2.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/46cdc353-44f0-4262-afe7-e5714e2a9426.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/cb686cf0-f14a-460d-9d8c-5f94d192b9a1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d8c51313-9e76-4e74-a858-ec8e78873eaf.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/cc3e0910-7bba-4399-8dce-5355a0d27853.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1538733f-4346-4546-a5bc-3cf815872e36.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9c21affe-5911-4bc2-abf0-7db7037e31c5.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1e85d8a7-e768-4e1d-aa36-20d7bdb8d5b1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/45867868-d31c-4b58-bb12-ef1e3b512758.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/5cb8f835-bbd5-4ad6-b639-75a33e768efe.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ae6f563f-178c-419a-aabb-322b818e78df.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/c6977b08-3aba-4ee7-a3c7-0ae4f5da540f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1aeabacc-9742-4cfc-bc7f-ccc01edaf84d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/157176c1-a13a-409c-8d0e-e8b20b822dca.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/cfbd92d4-a0f2-4ea6-95fb-7ac15b06d213.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/0097622c-596c-4cda-adb1-28a01321506f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f2ec892c-9f67-46b6-a196-e81b2bd2df13.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d5843ae5-6cc9-46cf-b39d-5dad38a059b8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/84f972b3-fb1e-4a35-8a3e-05aaaa703df8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e95634d5-4c75-41f0-ab28-643654df12a6.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e2bbbdf7-98eb-475e-8031-5d3306c9868b.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/00e60c12-7143-43f1-9b86-927b9547fc6e.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/28faec75-4748-4353-a781-6281beb40fca.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/77600197-106e-44cf-beb1-4fb5e0681b26.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/3536793b-e570-4777-afdc-cb85ae6784ed.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/54a4b6d2-7062-489a-82b1-5df3c6c9e4f0.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e8d7d2c3-fa58-4835-9710-39d24796350c.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d2e9f7a4-1a24-41bc-b85e-6e37ccb8b358.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/c66f5f1d-2dcb-4bcf-91cc-cb4cae8e47b5.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/b3e96dbf-95e2-46a5-9cc4-e0603bf0c87d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/7974d3f1-1c43-455a-813a-7b2e3bb4b57e.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/343b13fd-6390-4352-9cf7-e3c4a2e115e8.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/561b1683-8fae-4e80-bcbe-284eca5e0ad3.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/772fb8ae-4e5c-4742-8a51-93e9b5889542.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d366f6af-e26b-488e-adcc-55cf40d21256.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/94020503-e03d-4edf-ad5e-e83d1178a05c.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/5b42692f-9d70-4fe1-8e3f-a7b1dbf0a7ae.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/2f6c954c-be27-44fa-b838-ae17ea1aba09.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/c7074ad7-eb8f-4a94-a0a1-c2bdf5a10a4f.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/8a13244b-93ca-4f20-b4d1-f7eb7b4143a3.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ec1b5bad-36a2-461a-a77a-c9a67697482a.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/893f01dc-65ae-4eeb-94b8-db6ff548ce3d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/abcf8e09-2841-4b0d-9d8a-41abef806d78.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/45b3bf34-bb67-461c-a025-f8ca81f9fb02.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/fd58e1ee-ad0e-4145-859c-5f19130ca017.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/bfa4c2c2-c438-4a98-ac7d-a0b76040ca48.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/39610eee-6ea4-46e6-b5c4-3d00b56478ec.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/00efe5f2-9773-46ac-a585-6a536632d821.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/3f75b8d2-c158-4642-b9fc-3824210eec47.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d3b704e0-3025-46e5-83ba-52a60ceff476.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/20ea8b04-f1a2-4940-b9bf-6b2bcef8e5c2.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/dd6474ec-aff4-4849-905e-a1b850f4f0c7.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/085f6690-ba9c-4c71-b0d5-edf43761beb9.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/1e76ee10-4b2e-4ee2-8056-5716defdbeba.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/4fe65f12-3692-4ee6-8b6f-28ec0b0b87a1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/3dfb2c92-6c53-4127-91e6-dbefeeafd964.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/ef81ae99-d0eb-4442-a01c-2c76bd17ef68.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/5243007b-37a6-4800-969d-e86e4453448b.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/59c22984-72bf-4088-8cf9-c0eddafcdd46.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/75e0d5d0-454b-4959-a5e6-8d6d4a48c7b4.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9a1f0bae-0b44-4416-8715-9316838e6f62.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9dcc885a-c3a3-4527-9a8d-3d6e4d690ae7.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/2660c348-bf75-4fad-aae7-c14b5bcd7bc1.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d9c297fc-acce-46be-aa30-1c579b33d5a4.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/d6c43854-c6d2-4824-894f-6f85627c892d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/9d7179a5-b580-4caa-8be6-78fde44f8887.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/f0418199-eace-48ec-8148-23c95a42118c.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/56467b10-726f-439f-95d4-9c574b526fde.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/fd79fb4b-0dc2-4348-8a1c-ba0a9febd54e.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e7dd7c0f-cbde-4ff7-b6c7-dd7fd8b4367d.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/54f435b3-c51e-4cb1-b4d5-e6e793035fb9.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/6e135dfe-bad1-4d97-8523-a85424b99405.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/daa351a1-45ac-4109-870a-c20f9b1a9a2c.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/dda01847-837c-4cfe-8f90-65b2869724fc.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e73af245-af1d-4fba-8838-0a7535422ad4.jpg");
        list.add("http://sxjt.sintoon.cn:89//sphoto/14/14210/1421025/X597141025/2017/e3653b58-5ee6-4bbe-9af9-07e22057b2e5.jpg");
        list.add("https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg");

    }


    ImageView mIVLoop;
    CheckBox mCheckboxControl;
    SeekBar mSeek;

    ViewPager mPager;

    ImageView mIvTestPlaceholder;

    List<PictureFragment> fragments = new ArrayList<>();
    PicturePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIVLoop = findViewById(R.id.iv_loop);
        mCheckboxControl = findViewById(R.id.checkbox_play_control);
        mSeek = findViewById(R.id.seek);
        mIvTestPlaceholder = findViewById(R.id.iv_test_placeholder);
        mCheckboxControl.setOnCheckedChangeListener(this);
        mSeek.setOnSeekBarChangeListener(this);
        mSeek.setMax(list.size());
        mSeek.setProgress(playIndex);
        preload();

        //不能使用 gif 做 placeholder
        GlideApp.with(this)
                .load(list.get(0))
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mIVLoop);

        mPager = findViewById(R.id.pager_container);

        int size = list.size();
        for (int i = 0; i < size; i++) {
            String url = list.get(i);
            fragments.add(PictureFragment.newInstance(url, "" + i));
        }
        adapter = new PicturePagerAdapter(fragments, getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(this);

        testPlaceholder();
    }

    private void testPlaceholder(){
        //还有一种是 thumb
        //使用动态图做为占位图的方法。
        final ObjectAnimator anim = ObjectAnimator.ofInt(mIvTestPlaceholder, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        final ImageView.ScaleType scaleType = mIvTestPlaceholder.getScaleType();
        mIvTestPlaceholder.setScaleType(ImageView.ScaleType.FIT_CENTER);
        String path = "https://miro.medium.com/max/8660/1*LDZ4zbr3KpaVLV2XG6Uj4Q.jpeg";
        GlideApp.with(this)
                .load(path)
                .placeholder(R.drawable.tk_loading_rotate)
                .error(R.drawable.error)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        mIvTestPlaceholder.setScaleType(scaleType);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        mIvTestPlaceholder.setScaleType(scaleType);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mIvTestPlaceholder);
    }

    @Override
    public void onBackPressed() {
        handler.sendEmptyMessage(EVENT_Finish);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    Timer timer;
    TimerTask timerTask;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_Finish:
                    stopPlay();
                    playIndex = 0;
                    mCheckboxControl.setChecked(false);
                    break;
                case EVENT_PLAY:
                    String url = (String) msg.obj;
                    int index = msg.arg1;
                    mSeek.setProgress(index);
                    showImage(url);
                    showImagePager(index);
                    Log.e(MainActivity.class.getSimpleName(), "handleMessage: 显示第 " + index + " 张");
                    if (index + 10 >= preloadIndex) {
                        preload();
                    }
                    break;
            }
            return false;
        }
    });

    private void showImage(String url) {
        //解决闪烁问题，使用当前做为 placeholder
        GlideApp.with(MainActivity.this)
                .load(url)
                .error(R.drawable.error)
                .placeholder(mIVLoop.getDrawable())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                        Log.e(MainActivity.class.getSimpleName(), "onLoadFailed: "+e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mIVLoop);
    }

    private void showImagePager(int index) {
        String url = list.get(index);
        int currentItem = mPager.getCurrentItem();

//        Drawable drawable = fragments.get(currentItem).getDrawable();
//        fragments.get(currentItem).showImage(url, drawable);
        mPager.setCurrentItem(index,false);
    }

    private void stopPlay() {
        if (null != timerTask) {
            timerTask.cancel();
            if (null != timer) {
                timer.cancel();
            }
            timer = null;
            timerTask = null;
        }
        handler.removeCallbacksAndMessages(null);
    }

    static final int EVENT_PLAY = 1;
    static final int EVENT_Finish = 2;

    //3 秒间隔
    private int interval = 1;
    private int playIndex = 0;

    private void startPlay() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (list.size() > playIndex) {
                    String s = list.get(playIndex);
                    handler.obtainMessage(EVENT_PLAY, playIndex, 0, s).sendToTarget();
                    playIndex++;

                } else {
                    handler.sendEmptyMessage(EVENT_Finish);
                }
            }
        };
        timer.schedule(timerTask, 0, interval * 1000);
    }

    private int preloadIndex = 0;

    /**
     * 每次预加载十张
     */
    protected void preload() {
        if (preloadIndex == list.size() - 1) {
            return;
        }
        int temp = preloadIndex;
        if (preloadIndex + 10 < list.size()) {
            preloadIndex += 10;
        } else {
            preloadIndex = list.size() - 1;
        }
        Log.e(MainActivity.class.getSimpleName(), "preload: " + preloadIndex);
        for (int i = temp; i < preloadIndex; i++) {
            GlideApp.with(this)
                    .load(list.get(i))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .preload();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (progress >= list.size()) {
                handler.sendEmptyMessage(EVENT_Finish);
                return;
            }
            playIndex = progress;
           handler.obtainMessage(EVENT_PLAY,playIndex,0).sendToTarget();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        String str = String.format("当前：%d, positionOffset: %f , positionOffsetPixels: %d", position, positionOffset, positionOffsetPixels);
//        Log.e(MainActivity.class.getSimpleName(), "onPageScrolled: " + str);

    }

    @Override
    public void onPageSelected(int position) {
        String str = String.format("position: %d",position);
        Log.e(MainActivity.class.getSimpleName(), "onPageSelected: "+str);
        mSeek.setProgress(position);
        adapter.setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        String str = String.format("state: %d",state);
        Log.e(MainActivity.class.getSimpleName(), "onPageScrollStateChanged: "+str);
        if (mCheckboxControl.isChecked()){
            if (state!=0){
                stopPlay();
            }else{
                startPlay();
            }
        }
    }

}
