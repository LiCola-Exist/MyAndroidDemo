package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.RecyclerListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.dummy.DummyContent;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.example.licola.myandroiddemo.view.CustomLayoutManager;
import com.licola.llogger.LLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items. <p /> Activities containing this fragment MUST
 * implement
 */
public class RecyclerFragment extends BaseFragment {

  private static final String ARG_TITLE = "title";
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int mColumnCount = 1;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
   * screen orientation changes).
   */
  public RecyclerFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static RecyclerFragment newInstance(String title, int columnCount) {
    RecyclerFragment fragment = new RecyclerFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TITLE, title);
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

    SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

    Context context = view.getContext();
    RecyclerView recyclerView = view.findViewById(R.id.list);
    if (mColumnCount <= 1) {
//      recyclerView.setLayoutManager(new LinearLayoutManager(context));
      recyclerView.setLayoutManager(new CustomLayoutManager());
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
    }
    final RecyclerRecyclerViewAdapter adapter = new RecyclerRecyclerViewAdapter(DummyContent.ITEMS);
    recyclerView.setAdapter(adapter);

    swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        //刷新数据 先清除 当前adapter数据集合已经为空  如果不及时加入新数据 后续的滑动尝试获取旧内容就会越界溢出错误
//        adapter.clear();
      }
    });

    recyclerView.setRecyclerListener(new RecyclerListener() {
      @Override
      public void onViewRecycled(ViewHolder holder) {
        LLogger.d(holder);
      }
    });

    FloatingActionButton button = view.findViewById(R.id.bt_action);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        List<DummyItem> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
          data.add(DummyContent.createDummyItem(i));
        }
        adapter.notifyAllData(data);
      }
    });

    recyclerView.addOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        checkScrollEndByScroll(recyclerView);
        checkScrollEndByPosition(recyclerView);
      }
    });
    return view;
  }

  private static void checkScrollEndByPosition(RecyclerView recyclerView) {
    LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof LinearLayoutManager) {
      int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
          .findLastVisibleItemPosition();
      LLogger.d("目前可见的last位置：" + lastVisibleItemPosition);
    }
  }

  private static void checkScrollEndByScroll(RecyclerView recyclerView) {
    int scrollExtent = recyclerView.computeVerticalScrollExtent();//View的占据的高度
    int scrollOffset = recyclerView.computeVerticalScrollOffset();//滑动偏移量
    int scrollRange = recyclerView.computeVerticalScrollRange();//View的实际内容范围

    if (scrollExtent + scrollOffset >= scrollRange) {
      LLogger.d("已经滑动到底");
    }

    LLogger.d(scrollExtent, scrollOffset, scrollRange);
  }


  public class RecyclerRecyclerViewAdapter extends
      RecyclerView.Adapter<RecyclerRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public RecyclerRecyclerViewAdapter(List<DummyItem> items) {
      mValues = items;
    }

    public void notifyAllData(List<DummyItem> data) {
      mValues.clear();
      mValues.addAll(data);
      notifyDataSetChanged();
    }

    public void clear() {
      mValues.clear();
    }

    public void notifyByPosition(int position) {
      mValues.set(position, new DummyItem(String.valueOf(position), "new Content", "new Details"));
      notifyItemChanged(position);
    }

    @Override
    public RecyclerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.fragment_recycler, parent, false);
      ViewHolder viewHolder = new RecyclerRecyclerViewAdapter.ViewHolder(view);
      LLogger.d(viewHolder);
      return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      LLogger.d(holder, position);
      final DummyItem dummyItem = mValues.get(position);
      holder.mItem = dummyItem;
      holder.mIdView.setText(dummyItem.id);
      holder.mContentView.setText(dummyItem.content);

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          notifyByPosition(position);
        }
      });
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      public final View mView;
      public final TextView mIdView;
      public final TextView mContentView;
      public DummyItem mItem;

      public ViewHolder(View view) {
        super(view);
        mView = view;
        mIdView = (TextView) view.findViewById(R.id.item_number);
        mContentView = (TextView) view.findViewById(R.id.content);
      }

      @Override
      public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
      }
    }
  }
}
