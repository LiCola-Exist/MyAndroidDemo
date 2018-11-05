package com.example.licola.myandroiddemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.RuntimeHandle;
import com.example.licola.myandroiddemo.AndroidRuntimeCode.RuntimeCode;
import com.example.licola.myandroiddemo.ListFragment.OnListFragmentListener;
import com.example.licola.myandroiddemo.RecyclerFragment.OnRecyclerFragmentListener;
import com.example.licola.myandroiddemo.android.AndroidMain;
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
import com.example.licola.myandroiddemo.java.JavaMain;
import com.example.licola.myandroiddemo.location.LocationHelper;
import com.example.licola.myandroiddemo.location.MyLocationListener;
import com.example.licola.myandroiddemo.messenger.MessengerService;
import com.example.licola.myandroiddemo.receiver.MainLocalBroadcastReceiver;
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
import java.io.File;
import java.util.Arrays;
import java.util.UUID;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@RuntimeHandle()
@Route(name = "main")
public class MainActivity extends BaseActivity implements
    OnListFragmentListener, OnRecyclerFragmentListener {

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

  static final String[] titles = {"BottomSheetFragment", "Test", "View", "Download", "Xml",
      "Constraint", "MyRecyclerView", "Animate", "ProgressView", "Module", "ImageView", "IO",
      "Socket", "Thread", "Recycler", "Dao", "layout", "WebView","Text","Http"};

  MainLocalBroadcastReceiver receiver;


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
    mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, titles.length);
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
        mViewPager.setCurrentItem(findTitlePosition("WebView"));
//        mViewPager.setCurrentItem(10);
      }

      private int findTitlePosition(String target) {

        int index = 0;
        for (String title : titles) {
          if (target.toLowerCase().equals(title.toLowerCase())) {
            return index;
          }
          index++;
        }

        return 0;
      }
    });

    asepctJRun();
    daggerInject();

    activityBindService();

    testSystemInfo();

    testLocalBroad();

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

  private void mainActivity() {

    tabLayout.post(new Runnable() {
      @Override
      public void run() {
        int statusBarHeight = WindowsController.getStatusBarHeight(MainActivity.this);
        int fixDp = PixelUtils.dp2px(MainActivity.this, 48);
        int tabLayoutHeight = tabLayout.getHeight();
        LLogger.d(statusBarHeight,fixDp, tabLayoutHeight);
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

    int color = ContextCompat.getColor(this, R.color.orange_normal);
    int alpha = (int) (1 * 255.0f + 0.5f);
//      FF9800
    int argb = Color.argb(alpha, 255, 152, 0);

    int px = PixelUtils.dp2px(this, 10);

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
    String uuId = UUID.randomUUID().toString();
    LLogger.d("UUID:"+uuId);

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
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public static class SectionsPagerAdapter extends FragmentPagerRebuildAdapter<BaseFragment> {


    public SectionsPagerAdapter(FragmentManager fm, int pageSize) {
      super(fm, pageSize);
    }

    @Override
    protected BaseFragment createFragment(int position) {
      BaseFragment fragment = null;
      String title = titles[position];
      switch (position) {
        case 0:
          fragment = BottomSheetFragment.newInstance(title);
          break;
        case 1:
          fragment = TestFragment.newInstance(title);
          break;
        case 2:
          fragment = ViewFragment.newInstance(title);
          break;
        case 3:
          fragment = DownLoadFragment.newInstance(title);
          break;
        case 4:
          fragment = XmlFragment.newInstance(title);
          break;
        case 5:
          fragment = ConstraintLayoutFragment.newInstance(title);
          break;
        case 6:
          fragment = ListFragment.newInstance(1);
          break;
        case 7:
          fragment = AnimateFragment.newInstance(title);
          break;
        case 8:
          fragment = ProcessViewFragment.newInstance(title);
          break;
        case 9:
          fragment = ModuleFragment.newInstance(title);
          break;
        case 10:
          fragment = ImageViewFragment.newInstance(title);
          break;
        case 11:
          fragment = IOFragment.newInstance(title);
          break;
        case 12:
          fragment = SocketFragment.newInstance(title);
          break;
        case 13:
          fragment = ThreadFragment.newInstance(title);
          break;
        case 14:
          fragment = RecyclerFragment.newInstance(title, 1);
          break;
        case 15:
          fragment = DaoFragment.newInstance(title);
          break;
        case 16:
          fragment = LayoutFragment.newInstance(title);
          break;
        case 17:
          fragment = WebViewFragment.newInstance(title);
          break;
        case 18:
          fragment=TextFragment.newInstance(title);
          break;
        case 19:
          fragment=HttpFragment.newInstance(title);
          break;
      }

      return fragment;
    }

    @Override
    protected void bindFragment(BaseFragment fragment, int position) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
      return titles[position];
    }
  }
}
