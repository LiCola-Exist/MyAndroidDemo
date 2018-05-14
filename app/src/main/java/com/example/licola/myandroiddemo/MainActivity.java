package com.example.licola.myandroiddemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.RuntimeHandle;
import com.example.licola.myandroiddemo.AndroidRuntimeCode.RuntimeCode;
import com.example.licola.myandroiddemo.ItemFragment.OnListFragmentInteractionListener;
import com.example.licola.myandroiddemo.componet.MyLifeCycleObserver;
import com.example.licola.myandroiddemo.dagger.SuperUserModel;
import com.example.licola.myandroiddemo.dagger.UserModel;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.example.licola.myandroiddemo.java.JavaMain;
import com.example.licola.myandroiddemo.messenger.MessengerService;
import com.example.licola.myandroiddemo.receiver.MainLocalBroadcastReceiver;
import com.example.licola.myandroiddemo.thread.MyHandler;
import com.example.licola.myandroiddemo.thread.ThreadWork;
import com.example.licola.myandroiddemo.utils.FragmentPagerRebuildAdapter;
import com.example.licola.myandroiddemo.utils.Logger;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.licola.llogger.LLogger;
import com.socks.library.KLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

@RuntimeHandle()
public class MainActivity extends BaseActivity implements
    OnListFragmentInteractionListener {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;
  Toolbar toolbar;
  @Inject
  UserModel userModel;
  @Inject
  SuperUserModel superUserModel;


  static Boolean value1 = true;
  static Boolean value2 = false;
  static Boolean value3 = new Boolean(true);

  static final String[] title = {"BottomSheetFragment", "Test", "View", "Download", "Xml",
      "Constraint", "RecyclerView", "Animate", "ProgressView", "Module", "ImageView", "IO",
      "Socket", "Thread"};

  MainLocalBroadcastReceiver receiver;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), title.length);
    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mViewPager.post(new Runnable() {
      @Override
      public void run() {
        mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1);
//        mViewPager.setCurrentItem(7);
      }
    });

    asepctJRun();
    daggerInject();

    activityBindService();

    testSystemInfo();

    testLocalBroad();

    testLifeCycle();

    testView();

    testEventBus();

    JavaMain.main();
    ThreadWork.main();
    MyHandler.main();
    testLog("name");
  }


  private void testLog(String type) {

//    Thread thread=new MyThread();
//    thread.start();
//
//    KLog.file("file-tag",getCacheDir(),"test file write");
    LLogger.d("test");
  }

  private void testEventBus() {
    EventBus eventBus = EventBus.getDefault();

    eventBus.register(this);

    eventBus.post("123");
  }


  @Subscribe
  public void onEvent(String event) {
    Logger.d(event);
  }

  private void testView() {

    int screenHeight = PixelUtils.getScreenHeight(this);
    Logger.d("screenHeight:" + screenHeight);

    int color = ContextCompat.getColor(this, R.color.orange_normal);
    int alpha = (int) (1 * 255.0f + 0.5f);
//      FF9800
    int argb = Color.argb(alpha, 255, 152, 0);
  }

  private void testLifeCycle() {
    MyLifeCycleObserver.addObserver(this);
  }

  private void testLocalBroad() {
    receiver = new MainLocalBroadcastReceiver();
    LocalBroadcastManager.getInstance(this)
        .registerReceiver(receiver, new IntentFilter(Intent.ACTION_PICK_ACTIVITY));
  }

  private void testSystemInfo() {
    Logger.d("Build:" + Build.MANUFACTURER);

    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    int memoryClass = manager.getMemoryClass();
    int largeMemoryClass = manager.getLargeMemoryClass();
    Logger.d("memoryClass:" + memoryClass + " largeMemoryClass:" + largeMemoryClass);

    String packageCodePath = this.getPackageCodePath();
    String packageResourcePath = this.getPackageResourcePath();
    String codeCacheDir = this.getCodeCacheDir().getAbsolutePath();
    Logger.d("packageCodePath:" + packageCodePath + " packageResourcePath:" + packageResourcePath
        + " codeCacheDir:" + codeCacheDir);

    File cacheDir = this.getCacheDir();
    Logger.d("cacheDir:" + cacheDir);
    ClassLoader classLoader = this.getClassLoader();
    while (classLoader != null) {
      Logger.d(classLoader.toString());
      classLoader = classLoader.getParent();
    }
  }

  private void activityBindService() {
    Intent intent = new Intent(this, MessengerService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  private void asepctJRun() {
    RuntimeCode runtimeCode = new RuntimeCode();
    runtimeCode.init();
  }

  private void daggerInject() {
    //        ActivitySuperComponent superComponent = DaggerActivitySuperComponent.builder()
    //                .activitySuperUserModel(new ActivitySuperUserModel())
    //                .build();
    //
    //        ActivityComponent component = DaggerActivityComponent.builder()
    //                .activitySuperComponent(superComponent)
    //                .activityUserModel(new ActivityUserModel())
    //                .build();
    //
    //        component.inject(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  @Override
  protected void onDestroy() {
    unbindService(mConnection);
    super.onDestroy();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    //permissionFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private Messenger mService;

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      Message message = Message.obtain(null, 100);
      Bundle data = new Bundle();
      data.putString("msg", "hello this is client");
      message.setData(data);
      try {
        mService.send(message);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  public void onListFragmentInteraction(DummyItem item) {
    Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public static class SectionsPagerAdapter extends FragmentPagerRebuildAdapter<Fragment> {


    public SectionsPagerAdapter(FragmentManager fm, int pageSize) {
      super(fm, pageSize);
    }

    @Override
    protected Fragment createFragment(int position) {
      Fragment fragment = null;
      switch (position) {
        case 0:
          fragment = BottomSheetFragment.newInstance(title[position]);
          break;
        case 1:
          fragment = TestFragment.newInstance(title[position]);
          break;
        case 2:
          fragment = ViewFragment.newInstance(title[position]);
          break;
        case 3:
          fragment = DownLoadFragment.newInstance(title[position]);
          break;
        case 4:
          fragment = XmlFragment.newInstance(title[position]);
          break;
        case 5:
          fragment = ConstraintLayoutFragment.newInstance(title[position], null);
          break;
        case 6:
          fragment = ItemFragment.newInstance(1);
          break;
        case 7:
          fragment = AnimateFragment.newInstance(title[position]);
          break;
        case 8:
          fragment = ProcessViewFragment.newInstance(title[position]);
          break;
        case 9:
          fragment = ModuleFragment.newInstance(title[position]);
          break;
        case 10:
          fragment = ImageViewFragment.newInstance(title[position]);
          break;
        case 11:
          fragment = IOFragment.newInstance(title[position]);
          break;
        case 12:
          fragment = SocketFragment.newInstance(title[position]);
          break;
        case 13:
          fragment = ThreadFragment.newInstance(title[position]);
          break;
      }
      return fragment;
    }

    @Override
    protected void bindFragment(Fragment fragment, int position) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
      return title[position];
    }
  }
}
