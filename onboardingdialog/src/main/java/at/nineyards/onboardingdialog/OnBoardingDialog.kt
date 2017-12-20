/*
 * Copyright (C) 9Y Media Group GmbH - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alois Wollersberger <alois.wollersberger@9yards.at> Sep 7, 2017
 */

package at.nineyards.onboardingdialog

import android.animation.ArgbEvaluator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog_onboarding.*

/**
 * Created by Lois-9Y on 07/09/2017.
 */
class OnBoardingDialog : DialogFragment() , ViewPager.OnPageChangeListener{


    var startColor : Int? = null
    var endColor : Int? = null
    private var pagerAdapter : FragmentPagerAdapter? = null

    private val fragmentList = mutableListOf<Fragment>()
    private val indicatorList = mutableListOf<ImageView>()
    private val argbEvaluator =  ArgbEvaluator()

    var finishCallback : (()-> Unit)? = null
    var skipCallback : (()-> Unit)? = null
    var skipText: String? = null
    var finishText: String? = null
    var nextText: String? = null

    var fullScreen = false

    fun clearFragments() = fragmentList.clear()
    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)
    fun addFragments(fragments : List<Fragment>) =fragmentList.addAll(fragments)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.dialog_onboarding, container, false)
        val indicatorContainer = v?.findViewById(R.id.indicator_container)

        (indicatorContainer as LinearLayout).let {
            if(fragmentList.size > 1)
                for (i in 0 until fragmentList.size) { addIndicator(indicatorContainer, i) }
        }
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(startColor == null ) startColor = ResourcesCompat.getColor(resources, R.color.primaryColor_600, null)
        if(endColor == null ) endColor  = ResourcesCompat.getColor(resources, R.color.primaryColor_800, null)
        pagerAdapter = OnBoardingAdapter(childFragmentManager)

        view_pager_container?.adapter = pagerAdapter
        view_pager_container?.addOnPageChangeListener(this)
        view_pager_container?.currentItem = 0

        startColor?.let { dialog_container?.setBackgroundColor(it) }

        if(skipText != null) {
            intro_btn_skip.setOnClickListener { skipCallback?.invoke() }
            skipText?.let { intro_btn_skip.text = it }
        } else {
            intro_btn_skip.visibility = View.GONE
        }

        intro_btn_finish.setOnClickListener { finishCallback?.invoke() }
        finishText?.let { intro_btn_finish.text = it }

        if(nextText!= null){
            intro_btn_next_text.text = nextText
            intro_btn_next_text.visibility = View.VISIBLE
            intro_btn_next.visibility = View.GONE
            intro_btn_next_text.setOnClickListener { view_pager_container?.currentItem?.let { view_pager_container?.currentItem = it+1 } }
        } else {
            intro_btn_next.setOnClickListener { view_pager_container?.currentItem?.let { view_pager_container?.currentItem = it + 1 } }
        }

        onPageSelected(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fullScreen)
            setStyle(DialogFragment.STYLE_NORMAL,R.style.fullscreen_dialog)
    }

    override fun onStart() {
        super.onStart()
        if(fullScreen)
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun selectPage(position : Int){
        view_pager_container?.currentItem = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position < fragmentList.size - 1 ) {
            dialog_container?.setBackgroundColor(argbEvaluator.evaluate((position.toFloat() + positionOffset) / fragmentList.size.toFloat(),
                    startColor, endColor) as Int)
        }
    }

    override fun onPageSelected(position: Int) {
        if(nextText!= null){
            intro_btn_next_text.visibility = if (position+1 == pagerAdapter?.count) View.GONE else View.VISIBLE
        } else {
            intro_btn_next.visibility = if (position + 1 == pagerAdapter?.count) View.GONE else View.VISIBLE
        }
        intro_btn_finish.visibility = if (position+1 == pagerAdapter?.count) View.VISIBLE else View.GONE
        skipText?.let { intro_btn_skip.visibility = if (position + 1 == pagerAdapter?.count) View.GONE else View.VISIBLE }
        if(indicatorList.size > 0) {
            startColor?.let {
                indicatorList.forEach { it.setColorFilter(ResourcesCompat.getColor(resources,R.color.indicatorInActive, null),PorterDuff.Mode.MULTIPLY) }
                indicatorList[position].setColorFilter(ResourcesCompat.getColor(resources,R.color.indicatorActive, null),PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    private inner class OnBoardingAdapter(fm : FragmentManager):FragmentPagerAdapter(fm){
        override fun getItem(position: Int) =
                if (position < fragmentList.size ) fragmentList[position] else null
        override fun getCount(): Int = fragmentList.size
    }

    private fun  addIndicator (container : LinearLayout, position :Int){
        val indicator = LayoutInflater.from(context).inflate(R.layout.view_indicator, container,false)
        indicatorList.add(position,indicator as ImageView)
        container.addView(indicator)
    }
}