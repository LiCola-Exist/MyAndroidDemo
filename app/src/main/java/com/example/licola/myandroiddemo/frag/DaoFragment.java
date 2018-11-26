package com.example.licola.myandroiddemo.frag;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.example.licola.myandroiddemo.R;
import com.example.licola.myandroiddemo.data.dao.DaoMaster;
import com.example.licola.myandroiddemo.data.dao.DaoMaster.DevOpenHelper;
import com.example.licola.myandroiddemo.data.dao.DaoSession;
import com.example.licola.myandroiddemo.data.dao.Note;
import com.example.licola.myandroiddemo.data.dao.NoteDao;
import com.example.licola.myandroiddemo.data.dao.NoteDao.Properties;
import com.licola.llogger.LLogger;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaoFragment extends BaseFragment {

  private static final String ARG_PARAM1 = "param1";


  private String mParam1;
  private DaoSession daoSession;

  public DaoFragment() {
    // Required empty public constructor
  }

  public static DaoFragment newInstance(String param1) {
    DaoFragment fragment = new DaoFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    DaoMaster.DevOpenHelper helper = new DevOpenHelper(getContext(), "demo.db");
    SQLiteDatabase database = helper.getWritableDatabase();
    DaoMaster daoMaster = new DaoMaster(database);
    daoSession = daoMaster.newSession();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_dao, container, false);

    rootView.findViewById(R.id.bt_insert).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Note note = new Note();
        note.setAuthor("test");
        note.setText("test:" + System.currentTimeMillis());
        long id = daoSession.insertOrReplace(note);
        LLogger.d("insertOrReplace:"+id);
      }
    });

    rootView.findViewById(R.id.bt_query).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        NoteDao noteDao = daoSession.getNoteDao();
        List<Note> notes = noteDao.queryBuilder().where(Properties.Author.eq("test"))
            .orderDesc(Properties.Id).limit(3).list();
        for (Note note : notes) {
          LLogger.d(note.toString());
        }
      }
    });

    return rootView;
  }

}
