package at.nineyards.onboarding.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.nineyards.onboarding.R

/**
 * Created by Lois-9Y on 11/09/2017.
 */
class SalesPitchFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sales_pitch, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun  newInstance(context: Context) =  Fragment.instantiate(context,SalesPitchFragment::class.java.name)
    }
}