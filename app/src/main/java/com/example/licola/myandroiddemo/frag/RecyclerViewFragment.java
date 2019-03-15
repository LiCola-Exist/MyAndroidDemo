package com.example.licola.myandroiddemo.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnFlingListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.adapter.MyItemRecyclerViewAdapter;
import com.example.licola.myandroiddemo.dummy.DummyContent;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.licola.llogger.LLogger;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentListener}
 * interface.
 */
public class RecyclerViewFragment extends BaseFragment {

  private static final String ARG_KEY = "key:title";
  // TODO: Customize parameters
  private OnListFragmentListener mListener;
  private MyItemRecyclerViewAdapter adapter;

  private SwipeRefreshLayout swipeRefreshLayout;
  private RecyclerView recyclerView;

  private LinearLayoutManager layoutManager;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecyclerViewFragment() {
  }

  public static RecyclerViewFragment newInstance(String content) {
    RecyclerViewFragment fragment = new RecyclerViewFragment();
    Bundle args = new Bundle();
    args.putString(ARG_KEY, content);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item_list, container, false);
    recyclerView = view.findViewById(R.id.list);
    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

    swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
//        LLogger.d(swipeRefreshLayout.isRefreshing());
        changeData();
      }
    });

    setRetainInstance(true);

    Context context = view.getContext();

    layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new MyItemRecyclerViewAdapter(mListener);
    recyclerView.setAdapter(adapter);
    adapter.initData(DummyContent.ITEMS);
//    recyclerView.setOnFlingListener(new MyFlingListener());

    return view;
  }


  public void changeData() {
    List<DummyItem> items = DummyContent.ITEMS;
    DummyContent.ITEMS.remove(0);
    List<DummyItem> itemsNew = DummyContent.ITEMS;
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(items, itemsNew));
    diffResult.dispatchUpdatesTo(adapter);
    swipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListFragmentListener) {
      mListener = (OnListFragmentListener) context;
    } else {
      throw new RuntimeException(
          context.toString() + " must implement OnRecyclerFragmentListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnListFragmentListener {

    void onListFragmentInteraction(DummyItem item);
  }

  public static class MyDiffCallback extends DiffUtil.Callback {

    private List<DummyItem> oldList;
    private List<DummyItem> newList;

    public MyDiffCallback(List<DummyItem> oldList, List<DummyItem> newList) {
      this.oldList = oldList;
      this.newList = newList;
    }

    @Override
    public int getOldListSize() {
      return oldList.size();
    }

    @Override
    public int getNewListSize() {
      return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return oldList.get(oldItemPosition).id.equals(newList.get(newItemPosition).id);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

      return null;
    }
  }

  private class MyFlingListener extends OnFlingListener {

    @Override
    public boolean onFling(int velocityX, int velocityY) {
      LLogger.d(velocityY);
//
      int position = layoutManager.findFirstVisibleItemPosition();
      int height = recyclerView.getHeight();
      int criticalPoint = height / 2;
      if (velocityY > criticalPoint) {
        position++;
      } else if (Math.abs(velocityX) > criticalPoint) {
        position--;
      }
//      View viewByPosition = layoutManager.findViewByPosition(position);
      recyclerView.smoothScrollToPosition(position);
      return true;
    }
  }
}
