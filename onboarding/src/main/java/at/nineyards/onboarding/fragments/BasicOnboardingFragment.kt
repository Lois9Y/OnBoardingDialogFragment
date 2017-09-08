package at.nineyards.onboarding.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.nineyards.onboarding.R
import kotlinx.android.synthetic.main.fragment_basic_onboarding.*

/**
 * Created by Lois-9Y on 08/09/2017.
 */
class BasicOnboardingFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_basic_onboarding, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basic_image.setImageResource(arguments.getInt("imageResId"))
        basic_text.text = arguments.getString("text")
    }

    companion object {
        fun  newInstance(context: Context, imageResId :Int, text :String) : Fragment {
            val fragmentFirst = Fragment.instantiate(context,BasicOnboardingFragment::class.java.name)
            val args = Bundle()
            args.putString("text", text)
            args.putInt("imageResId", imageResId)
            fragmentFirst.arguments = args
            return fragmentFirst
        }
    }
}