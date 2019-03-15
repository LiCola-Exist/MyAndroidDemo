package com.example.licola.myandroiddemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.RuntimeHandle;
import com.example.licola.myandroiddemo.AndroidRuntimeCode.RuntimeCode;
import com.example.licola.myandroiddemo.android.AndroidMain;
import com.example.licola.myandroiddemo.aty.BaseActivity;
import com.example.licola.myandroiddemo.componet.MyLifeCycleObserver;
import com.example.licola.myandroiddemo.dagger.ActivityComponent;
import com.example.licola.myandroiddemo.dagger.ActivitySuperComponent;
import com.example.licola.myandroiddemo.dagger.ActivitySuperUserModel;
import com.example.licola.myandroiddemo.dagger.ActivityUserModel;
import com.example.licola.myandroiddemo.dagger.DaggerActivityComponent;
import com.example.licola.myandroiddemo.dagger.DaggerActivitySuperComponent;
import com.example.licola.myandroiddemo.dagger.SuperUserModel;
import com.example.licola.myandroiddemo.dagger.UserModel;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.example.licola.myandroiddemo.frag.AnimateFragment;
import com.example.licola.myandroiddemo.frag.BaseFragment;
import com.example.licola.myandroiddemo.frag.BroadcastFragment;
import com.example.licola.myandroiddemo.frag.ConstraintLayoutFragment;
import com.example.licola.myandroiddemo.frag.DaoFragment;
import com.example.licola.myandroiddemo.frag.DialogShowFragment;
import com.example.licola.myandroiddemo.frag.DownLoadFragment;
import com.example.licola.myandroiddemo.frag.HttpFragment;
import com.example.licola.myandroiddemo.frag.IOFragment;
import com.example.licola.myandroiddemo.frag.ImageLoadFragment;
import com.example.licola.myandroiddemo.frag.LayoutFragment;
import com.example.licola.myandroiddemo.frag.ProcessViewFragment;
import com.example.licola.myandroiddemo.frag.RecyclerAdapterFragment;
import com.example.licola.myandroiddemo.frag.RecyclerViewFragment;
import com.example.licola.myandroiddemo.frag.RecyclerViewFragment.OnListFragmentListener;
import com.example.licola.myandroiddemo.frag.SocketFragment;
import com.example.licola.myandroiddemo.frag.TestFragment;
import com.example.licola.myandroiddemo.frag.ThreadFragment;
import com.example.licola.myandroiddemo.frag.ToastFragment;
import com.example.licola.myandroiddemo.frag.ViewDrawFragment;
import com.example.licola.myandroiddemo.frag.WebViewFragment;
import com.example.licola.myandroiddemo.frag.XmlFragment;
import com.example.licola.myandroiddemo.java.JavaMain;
import com.example.licola.myandroiddemo.location.LocationHelper;
import com.example.licola.myandroiddemo.location.MyLocationListener;
import com.example.licola.myandroiddemo.messenger.MessengerService;
import com.example.licola.myandroiddemo.rxjava.RxJava;
import com.example.licola.myandroiddemo.thread.MyHandler;
import com.example.licola.myandroiddemo.thread.ThreadWork;
import com.example.licola.myandroiddemo.utils.FragmentPagerRebuildAdapter;
import com.example.licola.myandroiddemo.utils.PixelUtils;
import com.example.licola.myandroiddemo.utils.WindowsController;
import com.licola.llogger.LLogger;
import com.licola.route.RouteApp;
import com.licola.route.annotation.Route;
import com.licola.route.api.Api;
import com.licola.route.api.RouterApi.Builder;
import com.tencent.mmkv.MMKV;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@RuntimeHandle()
@Route(path = "main")
public class MainActivity extends BaseActivity implements
    OnListFragmentListener {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
   * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
   * fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link
   * android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private CoordinatorLayout coordinatorLayout;
  private ViewPager mViewPager;
  private TabLayout tabLayout;
  Toolbar toolbar;
  @Inject
  UserModel userModel;
  @Inject
  SuperUserModel superUserModel;


  static Boolean value1 = true;
  static Boolean value2 = false;
  static Boolean value3 = new Boolean(true);


  private static Map<String, Class> loadMap() {
    Map<String, Class> map = new LinkedHashMap<>();
    map.put("主测试Test", TestFragment.class);
    map.put("ViewDraw", ViewDrawFragment.class);
    map.put("Download组件", DownLoadFragment.class);
    map.put("Xml解析", XmlFragment.class);
    map.put("Layout布局", LayoutFragment.class);
    map.put("Constraint布局", ConstraintLayoutFragment.class);
    map.put("RecyclerView布局", RecyclerViewFragment.class);
    map.put("RecyclerAdapter数据", RecyclerAdapterFragment.class);
    map.put("Animate动画", AnimateFragment.class);
    map.put("ProgressAnimate动画", ProcessViewFragment.class);
    map.put("Broadcast广播/本地广播", BroadcastFragment.class);
    map.put("IO", IOFragment.class);
    map.put("Socket", SocketFragment.class);
    map.put("Thread/Handler", ThreadFragment.class);
    map.put("图片加载", ImageLoadFragment.class);
    map.put("Dao数据库", DaoFragment.class);
    map.put("WebView", WebViewFragment.class);
    map.put("Http", HttpFragment.class);
    map.put("Toast提示", ToastFragment.class);
    map.put("Dialog弹框", DialogShowFragment.class);
    return map;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    WindowsController.setTranslucentWindows(this);
    setContentView(R.layout.activity_main);
    WindowsController.addStatusBarBackground(this, R.color.gray_normal_A32);

    coordinatorLayout = findViewById(R.id.main_content);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.

    FragmentManager fragmentManager = getSupportFragmentManager();
    mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, loadMap());
    // Set up the ViewPager with the sections adapter.
    mViewPager = findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    tabLayout = findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mViewPager.post(new Runnable() {
      @Override
      public void run() {
//        mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1);
        mViewPager.setCurrentItem(mSectionsPagerAdapter.getPositionByName("RecyclerView"));
//        mViewPager.setCurrentItem(6);
      }
    });

    asepctJRun();
    daggerInject();

    activityBindService();

    testSystemInfo();

    testLifeCycle();

    testView();
    testClass();

    testEventBus();

    JavaMain.main();
    AndroidMain.main(MainActivity.this);
    mainActivity();
    ThreadWork.main();
    MyHandler.main();

    RxJava.main();
    testMyLib();
    testThirdLib();

    checkPermissionByThird();
    LruCache<String, String> lruCache = new LruCache<>(100);

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        int height = mViewPager.getHeight();
        LLogger.d(height);
      }
    });

//    initLocation();

  }

  private void checkPermissionByThird() {
    AndPermission.with(mContext)
        .runtime()
        .permission(Permission.WRITE_EXTERNAL_STORAGE)
        .onGranted(new Action<List<String>>() {
          @Override
          public void onAction(List<String> permissions) {
            LLogger.d(permissions);
          }
        })
        .onDenied(new Action<List<String>>() {
          @Override
          public void onAction(List<String> permissions) {
            LLogger.d(permissions);
          }
        }).start();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return super.onKeyDown(keyCode, event);
  }

  private void mainActivity() {

    tabLayout.post(new Runnable() {
      @Override
      public void run() {
        int statusBarHeight = WindowsController.getStatusBarHeight(MainActivity.this);
        int fixDp = PixelUtils.dp2px(MainActivity.this, 48);
        int tabLayoutHeight = tabLayout.getHeight();
        LLogger.d(statusBarHeight, fixDp, tabLayoutHeight);
      }
    });
  }

  private void testClass() {
    Uri uri = Uri.parse("http://www.github.com");
    LLogger.d(uri);
    LLogger.d(uri.toString());
  }

  private LocationHelper mLocationHelper;


  private void initLocation() {
    mLocationHelper = new LocationHelper(this);
    mLocationHelper.initLocation(new MyLocationListener() {
      @Override
      public void updateLastLocation(Location location) {
        LLogger.d(location.toString());
      }

      @Override
      public void updateLocation(Location location) {

      }

      @Override
      public void updateStatus(String provider, int status, Bundle extras) {

      }

      @Override
      public void updateGpsStatus(GpsStatus gpsStatus) {
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        int height = mViewPager.getHeight();
        LLogger.d(height);
//        LLogger.trace();
      }
    });
    mViewPager.post(new Runnable() {
      @Override
      public void run() {
        int height = mViewPager.getHeight();
        LLogger.d(height);
//        LLogger.trace();
      }
    });
  }

  private void testMyLib() {

//    Thread thread=new MyThread();
//    thread.start();
//
//    KLog.file("file-tag",getCacheDir(),"test file write");
    LLogger.d("test log");
    LLogger.d(System.currentTimeMillis());

    Api api = new Builder(getApplication())
        .addRouteRoot(new RouteApp.Route())
        .build();
  }

  private void testThirdLib() {
    String rootDir = MMKV.initialize(this);
    LLogger.d(rootDir);

    MMKV mmkv = MMKV.defaultMMKV();
    mmkv.encode("input", "input_value");
    String dValue = mmkv.decodeString("input");
    LLogger.d(Arrays.toString(mmkv.allKeys()), dValue);

    mmkv.removeValueForKey("input");
    LLogger.d(Arrays.toString(mmkv.allKeys()));
  }

  private void testEventBus() {
    EventBus eventBus = EventBus.getDefault();

    eventBus.register(this);

    String event = "123";
    eventBus.postSticky(event);

  }


  @Subscribe()
  public void onEvent(String event) {
    LLogger.d(event);
  }

  @Subscribe()
  public void onEvent(CharSequence event) {
    LLogger.d(event);
  }

  private void testView() {

    int screenHeight = PixelUtils.getScreenHeight(this);
    int screenWidth = PixelUtils.getScreenWidth(this);
    LLogger.d("screenHeight:" + screenHeight + " screenWidth:" + screenWidth);
    int parse6Color = Color.parseColor("#FFFFFF");
    int parse8Color = Color.parseColor("#FFFFFFFF");
    int color = ContextCompat.getColor(this, R.color.white_normal);
    LLogger.d(parse6Color, parse8Color, color);
    int alpha = (int) (1 * 255.0f + 0.5f);
//      FF9800
    int argb = Color.argb(alpha, 255, 152, 0);

    int px = PixelUtils.dp2px(this, 10);


  }

  private void testLifeCycle() {
    MyLifeCycleObserver.addObserver(this);
  }


  private void testSystemInfo() {
    String uuId = UUID.randomUUID().toString();
    LLogger.d("UUID:" + uuId);

    LLogger.d("Build:" + Build.MANUFACTURER);

    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    int memoryClass = manager.getMemoryClass();
    int largeMemoryClass = manager.getLargeMemoryClass();
    LLogger.d("memoryClass:" + memoryClass + " largeMemoryClass:" + largeMemoryClass);

    String packageCodePath = this.getPackageCodePath();
    String packageResourcePath = this.getPackageResourcePath();
    String codeCacheDir = this.getCodeCacheDir().getAbsolutePath();
    LLogger.d("packageCodePath:" + packageCodePath + " packageResourcePath:" + packageResourcePath
        + " codeCacheDir:" + codeCacheDir);

    File cacheDir = this.getCacheDir();
    LLogger.d("cacheDir:" + cacheDir);

    LLogger.d(getResources().getDisplayMetrics().toString());

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

    ActivitySuperComponent superComponent = DaggerActivitySuperComponent.builder()
        .activitySuperUserModel(new ActivitySuperUserModel())
        .build();

    ActivityComponent component = DaggerActivityComponent.builder()
        .activitySuperComponent(superComponent)
        .activityUserModel(new ActivityUserModel())
        .build();

    component.inject(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }


  @Override
  protected void onDestroy() {
    unbindService(mConnection);
    super.onDestroy();
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
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public static class SectionsPagerAdapter extends FragmentPagerRebuildAdapter<BaseFragment> {

    private Map<String, Class> classMap;

    private List<String> keyTitles;

    public SectionsPagerAdapter(FragmentManager fm, Map<String, Class> classMap) {
      super(fm, classMap.size());
      this.classMap = classMap;
      keyTitles = new ArrayList<>(classMap.keySet());
    }

    @Override
    protected BaseFragment createFragment(int position) {
      String key = keyTitles.get(position);

      Class targetClass = classMap.get(key);

      return invokeFragmentInstance(key, targetClass);
    }

    @Override
    protected void bindFragment(BaseFragment fragment, int position) {

    }

    @Override
    public CharSequence getPageTitle(int position) {

      return keyTitles.get(position);
    }

    public int getPositionByName(String name) {
      for (int i = 0; i < keyTitles.size(); i++) {
        String title = keyTitles.get(i);
        if (title.toLowerCase().contains(name.toLowerCase())) {
          return i;
        }
      }
      return 0;
    }
  }

  private static BaseFragment invokeFragmentInstance(String key, Class targetClass) {

    Method newInstanceMethod = null;
    try {
      newInstanceMethod = targetClass.getDeclaredMethod("newInstance", String.class);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    Object fragment = null;
    try {
      fragment = newInstanceMethod.invoke(null, key);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    if (fragment instanceof BaseFragment) {
      return (BaseFragment) fragment;
    }
    throw new IllegalArgumentException("错误的key-value配置");
  }
}
