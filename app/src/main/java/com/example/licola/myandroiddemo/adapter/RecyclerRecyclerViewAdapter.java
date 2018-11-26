package com.example.licola.myandroiddemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.frag.RecyclerFragment.OnRecyclerFragmentListener;
import com.example.licola.myandroiddemo.dummy.DummyContent.DummyItem;
import com.licola.llogger.LLogger;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnRecyclerFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecyclerRecyclerViewAdapter extends
    RecyclerView.Adapter<RecyclerRecyclerViewAdapter.ViewHolder> {

  private final List<DummyItem> mValues;
  private final OnRecyclerFragmentListener mListener;

  public RecyclerRecyclerViewAdapter(List<DummyItem> items,
      OnRecyclerFragmentListener listener) {
    mValues = items;
    mListener = listener;
  }



  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_recycler, parent, false);
    ViewHolder viewHolder = new ViewHolder(view);
    LLogger.d(viewHolder);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    LLogger.d(holder,position);
    final DummyItem dummyItem = mValues.get(position);
    holder.mItem = dummyItem;
    holder.mIdView.setText(dummyItem.id);
    holder.mContentView.setText(dummyItem.content);

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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
