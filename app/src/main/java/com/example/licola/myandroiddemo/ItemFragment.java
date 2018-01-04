package com.example.licola.myandroiddemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.dummy.DummyContent;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

  // TODO: Customize parameter argument names
  private static final String ARG_COLUMN_COUNT = "column-count";
  // TODO: Customize parameters
  private int mColumnCount = 1;
  private OnListFragmentInteractionListener mListener;
  private MyItemRecyclerViewAdapter adapter;

  private SwipeRefreshLayout swipeRefreshLayout;
  private RecyclerView recyclerView;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public ItemFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused") public static ItemFragment newInstance(int columnCount) {
    ItemFragment fragment = new ItemFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item_list, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.list);
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

    swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override public void onRefresh() {
        changeData();
      }
    });

    Context context = view.getContext();
    if (mColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
    }
    adapter = new MyItemRecyclerViewAdapter(mListener);
    recyclerView.setAdapter(adapter);
    adapter.initData(DummyContent.ITEMS);
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

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListFragmentInteractionListener) {
      mListener = (OnListFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(
          context.toString() + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
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
  public interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(DummyItem item);
  }

  public static class MyDiffCallback extends DiffUtil.Callback {

    private List<DummyItem> oldList;
    private List<DummyItem> newList;

    public MyDiffCallback(List<DummyItem> oldList, List<DummyItem> newList) {
      this.oldList = oldList;
      this.newList = newList;
    }

    @Override public int getOldListSize() {
      return oldList.size();
    }

    @Override public int getNewListSize() {
      return newList.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return oldList.get(oldItemPosition).id.equals(newList.get(newItemPosition).id);
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {

      return null;
    }
  }
}
