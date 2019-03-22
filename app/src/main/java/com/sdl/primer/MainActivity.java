package com.sdl.primer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdl.primer.animator.RecyclerViewActivity;
import com.sdl.primer.ipc.TcpClientActivity;
import com.sdl.primer.livedata3.LiveDataActivity;
import com.sdl.primer.motion.ConstraintSetExampleActivity;
import com.sdl.primer.net.NetActivity;
import com.sdl.primer.newnothing.WebPActivity;
import com.sdl.primer.ui.activity.BannerActivity;
import com.sdl.primer.ui.activity.BehaviorAdvanceActivity;
import com.sdl.primer.ui.activity.CalendarActivity;
import com.sdl.primer.ui.activity.CheckPermissionsActivity;
import com.sdl.primer.ui.activity.CustomViewActivity;
import com.sdl.primer.ui.activity.ReuseBitmapActivity;
import com.sdl.primer.ui.activity.ReverseColorProgressActivity;
import com.sdl.primer.ui.activity.SampleTitleBehaviorActivity;
import com.sdl.primer.ui.activity.ScrollerViewActivity;
import com.sdl.primer.ui.activity.SimpleSuspensionActivity;
import com.sdl.primer.ui.activity.SlidingPaneLayoutActivity;
import com.sdl.primer.ui.activity.ViewDragHelperActivity;
import com.sdl.primer.worker.WorkerActivity;
import com.sdl.primer.worker.blur.BlurActivity;
import com.sdl.primer.worker.blur.SelectImageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * viewDragHelper界面
     * @param view
     */
    public void viewDragHelper(View view){
        jumpActivity(ViewDragHelperActivity.class);
    }

    /**
     * 简单的自定义view
     * @param view
     */
    public void customView(View view){
        jumpActivity(CustomViewActivity.class);
    }

    /**
     * 两种颜色交互的进度条
     * @param view
     */
    public void reverseCircleProgress(View view){
        jumpActivity(ReverseColorProgressActivity.class);
    }

    /**
     * 检查权限
     * @param view
     */
    public void checkPermissions(View view){
        jumpActivity(CheckPermissionsActivity.class);
    }

    /**
     * webp图片
     * @param view
     */
    public void webP(View view){
        jumpActivity(WebPActivity.class);
    }

    /**
     * v4包下的左滑动
     * @param view
     */
    public void slidingPaneLayout(View view){
        jumpActivity(SlidingPaneLayoutActivity.class);
    }

    /**
     * 测试socket
     * @param view
     */
    public void testSocket(View view){
        jumpActivity(TcpClientActivity.class);
    }

    /**
     * recycler item 动画
     * @param view
     */
    public void recyclerViewAnim(View view){
        jumpActivity(RecyclerViewActivity.class);
    }

    /**
     * scroller view
     * @param view
     */
    public void scrollerView(View view){
        jumpActivity(ScrollerViewActivity.class);
    }

    /**
     * net
     * @param view
     */
    public void net(View view){
        jumpActivity(NetActivity.class);
    }

    /**
     * 使用inBitmap
     * @param view
     */
    public void reuseBitmap(View view){
        jumpActivity(ReuseBitmapActivity.class);
    }

    /**
     * 仿魅族日历控件
     * @param view
     */
    public void calendar(View view) {
        jumpActivity(CalendarActivity.class);
    }

    /**
     * 简单的自定义behavior
     * @param view
     */
    public void simpleTitleBehavior(View view) {
        jumpActivity(SampleTitleBehaviorActivity.class);
    }

    /**
     * 自定义behavior进阶
     * @param view
     */
    public void behaviorAdvance(View view) {
        jumpActivity(BehaviorAdvanceActivity.class);
    }

    /**
     * 轮播图
     * @param view
     */
    public void banner(View view) {
        jumpActivity(BannerActivity.class);
    }

    /**
     * liveData
     * @param view
     */
    public void liveData(View view) {
        jumpActivity(LiveDataActivity.class);
    }

    /**
     * recyclerView的悬浮
     * @param view
     */
    public void simpleSuspension(View view) {
        jumpActivity(SimpleSuspensionActivity.class);
    }

    /**
     * worker
     * @param view
     */
    public void worker(View view) {
        jumpActivity(SelectImageActivity.class);
    }

    public void constraint(View view) {
        jumpActivity(ConstraintSetExampleActivity.class);
    }

    public void jumpActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}
