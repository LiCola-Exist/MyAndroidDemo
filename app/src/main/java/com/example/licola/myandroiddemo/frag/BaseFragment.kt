package com.example.licola.myandroiddemo.frag

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.licola.llogger.LLogger


/**
 * Created by 李可乐 on 2016/12/9 0009.
 */
abstract class BaseFragment : Fragment() {

    protected var mContext: Context? = null

    protected var viewRoot: View? = null

    private var mUnbinder: Unbinder? = null

    protected abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) {
            LLogger.d(this)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewRoot = inflater.inflate(layoutId, container, false)
        mUnbinder = ButterKnife.bind(this, viewRoot!!)
        if (DEBUG) {
            LLogger.d(this)
        }
        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onStart() {
        super.onStart()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (DEBUG) {
            LLogger.d(this)
        }
        if (mUnbinder != null) {
            mUnbinder!!.unbind()
        }
        viewRoot = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (DEBUG) {
            LLogger.d(this)
        }
    }

    companion object {

        private val DEBUG = false

        private val Life = "FragLife:"
    }
}