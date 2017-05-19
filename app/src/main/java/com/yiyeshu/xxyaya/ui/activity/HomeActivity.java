package com.yiyeshu.xxyaya.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.weavey.loading.lib.LoadingLayout;
import com.yiyeshu.xxyaya.R;
import com.yiyeshu.xxyaya.base.BaseActivity;
import com.yiyeshu.xxyaya.ui.fragment.MainFragment;
import com.yiyeshu.common.utils.ViewUtil;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    private static String TAG = "HomeActivity";

    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private FragmentManager mFragmentManager;    //fragment管理器
    private Fragment mCurrentFragment;
    private MenuItem mPreMenuItem;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.loading)
    LoadingLayout mLoading;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void beforeInit() {
        mFragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void setUpView(Bundle savedInstanceState) {
        mToolbar.setTitle("首页");

        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        setNavigationViewItemClickListener();
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);

        mLoading.setOnReloadListener(new LoadingLayout.OnReloadListener() {

            @Override
            public void onReload(View v) {
                Toast.makeText(HomeActivity.this, "重试", Toast.LENGTH_SHORT).show();
            }
        });
        mLoading.setStatus(LoadingLayout.Success);

    }

    @Override
    protected void initData() {
        initDefaultFragment();
    }

    //设置默认显示第一个fragment，左侧菜单栏默认选中第一个
    private void initDefaultFragment() {
        mCurrentFragment=ViewUtil.createFragment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content,mCurrentFragment,mCurrentFragment.getClass().getSimpleName()).commit();
        mPreMenuItem = mNavigationView.getMenu().getItem(0);
        mPreMenuItem.setChecked(true);
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle("首页");
                        switchFragment(MainFragment.class);
                        break;
                    case R.id.navigation_item_ganhuo:
                        mToolbar.setTitle("干货福利");
                        //switchFragment(GanHuoFragment.class);
                        break;
                    case R.id.navigation_item_blog:
                        mToolbar.setTitle("我的博客");
                      //  switchFragment(BlogFragment.class);
                        break;
                    case R.id.navigation_item_custom_view:
                        mToolbar.setTitle("自定义View");
                       // switchFragment(CustomViewFragment.class);
                        break;
                    case R.id.navigation_item_snackbar:
                        mToolbar.setTitle("Snackbar演示");
                        //switchFragment(SnackBarFragment.class);
                        break;
                    case R.id.navigation_item_switch_theme:
                        mToolbar.setTitle("主题换肤");
                       // switchFragment(ChangeSkinFragment.class);
                        break;
                    case R.id.navigation_item_about:
                        mToolbar.setTitle("关于");
                       // switchFragment(AboutFragment.class);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mPreMenuItem = item;
                return false;
            }
        });
    }

    //切换Fragment
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtil.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
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
            startActivityWithoutExtras(SettingActivity.class);
        } else if (id == R.id.action_about) {
            startActivityWithoutExtras(AboutActivity.class);
        }


        return super.onOptionsItemSelected(item);
    }


}
