/*
 * Copyright (C) 9Y Media Group GmbH - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alois Wollersberger <alois.wollersberger@9yards.at> Sep 7, 2017
 */

package at.nineyards.onboarding

import android.animation.ArgbEvaluator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog_onboarding.*

/**
 * Created by Lois-9Y on 07/09/2017.
 */
class OnBoardingDialog : DialogFragment() , ViewPager.OnPageChangeListener{


    private var startColor : Int? = null
    private var endColor : Int? = null
    private var pagerAdapter : FragmentPagerAdapter? = null
    var finishCallback : (()-> Unit)? = null
    private val fragmentList = mutableListOf<Fragment>()
    private val indicatorList = mutableListOf<ImageView>()
    private val argbEvaluator =  ArgbEvaluator()


    fun clearFragments() = fragmentList.clear()
    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)
    fun addFragments(fragments : List<Fragment>) =fragmentList.addAll(fragments)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.dialog_onboarding, container, false)
        val indicatorContainer = v?.findViewById<LinearLayout>(R.id.indicator_container)
        indicatorContainer?.let {
            for (i in 0 until fragmentList.size) { addIndicator(indicatorContainer, i) }
        }
        isCancelable = false
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startColor = ResourcesCompat.getColor(resources, R.color.primaryColor, null)
        endColor  = ResourcesCompat.getColor(resources, R.color.primaryColor_800, null)
        pagerAdapter = OnBoardingAdapter(childFragmentManager)
        view_pager_container?.adapter = pagerAdapter
        view_pager_container?.addOnPageChangeListener(this)
        view_pager_container?.currentItem = 0
//        onPageSelected(0)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position < fragmentList.size - 1 ) {
            view_pager_container?.setBackgroundColor(argbEvaluator.evaluate((position.toFloat() + positionOffset) / fragmentList.size.toFloat(),
                    startColor, endColor) as Int)
        }
    }

    override fun onPageSelected(position: Int) {
        intro_btn_next.visibility = if (position+1 == pagerAdapter?.count) View.GONE else View.VISIBLE
        intro_btn_finish.visibility = if (position+1 == pagerAdapter?.count) View.VISIBLE else View.GONE
        intro_btn_skip.visibility = if (position+1 == pagerAdapter?.count) View.GONE else View.VISIBLE

        indicatorList.forEach { it.setImageResource(R.drawable.indicator_unselected) }
        indicatorList[position].setImageResource(R.drawable.indicator_selected)
    }

    private inner class OnBoardingAdapter(fm : FragmentManager):FragmentPagerAdapter(fm){
        override fun getItem(position: Int) =
                if (position <fragmentList.size) fragmentList[position] else null
        override fun getCount(): Int = fragmentList.size
    }

    private fun  addIndicator (container : LinearLayout, position :Int){
        val indicator = LayoutInflater.from(context).inflate(R.layout.view_indicator, container,false)
        indicatorList.add(position,indicator as ImageView)
        container.addView(indicator)
    }
}