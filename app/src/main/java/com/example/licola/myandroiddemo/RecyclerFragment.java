package com.example.licola.myandroiddemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.RecyclerListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.adapter.RecyclerRecyclerViewAdapter;
import com.example.licola.myandroiddemo.dummy.DummyContent;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.licola.llogger.LLogger;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnRecyclerFragmentListener}
 * interface.
 */
public class RecyclerFragment extends BaseFragment {

  private static final String ARG_TITLE = "title";
  private static final String ARG_COLUMN_COUNT = "column-count";
  private int mColumnCount = 1;
  private OnRecyclerFragmentListener mListener;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
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

    Context context = view.getContext();
    RecyclerView recyclerView = view.findViewById(R.id.list);
    if (mColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
    }
    final RecyclerRecyclerViewAdapter adapter = new RecyclerRecyclerViewAdapter(DummyContent.ITEMS,
        mListener);
    recyclerView.setAdapter(adapter);

    recyclerView.setRecyclerListener(new RecyclerListener() {
      @Override
      public void onViewRecycled(ViewHolder holder) {
        LLogger.d(holder);
      }
    });

    FloatingActionButton button= view.findViewById(R.id.bt_action);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        adapter.notifyItemChanged(0);
      }
    });




    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnRecyclerFragmentListener) {
      mListener = (OnRecyclerFragmentListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnRecyclerFragmentListener");
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
  public interface OnRecyclerFragmentListener {

    void onListFragmentInteraction(DummyItem item);
  }
}
