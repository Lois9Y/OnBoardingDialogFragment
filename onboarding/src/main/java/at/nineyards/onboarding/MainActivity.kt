package at.nineyards.onboarding

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import at.nineyards.onboarding.fragments.BasicOnboardingFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Lois-9Y on 08/09/2017.
 */
class MainActivity : AppCompatActivity() {
    private var dialog : OnBoardingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        basic_launch.setOnClickListener {
            dialog = OnBoardingDialog().apply {
                addFragment(BasicOnboardingFragment.newInstance(
                        this@MainActivity,
                        R.drawable.ic_one,
                        this@MainActivity.resources.getString(R.string.basic_text,"Page 1 of the onboarding process"))
                )
                addFragment(BasicOnboardingFragment.newInstance(
                        this@MainActivity,
                        R.drawable.ic_two,
                        this@MainActivity.resources.getString(R.string.basic_text,"Page 2 of the onboarding process"))
                )
                addFragment(BasicOnboardingFragment.newInstance(
                        this@MainActivity,
                        R.drawable.ic_three,
                        this@MainActivity.resources.getString(R.string.basic_text,"Page 3 of the onboarding process"))
                )
                finishCallback = this@MainActivity::dismissOnBoardingDialog
            }
            showOnBoardingDialog()
        }
    }

    fun showOnBoardingDialog() {
        dialog?.show(supportFragmentManager, "dialog_fragment")
    }

    fun dismissOnBoardingDialog() {
        dialog?.let {
            dialog ->
            dialog.dismiss()
            this@MainActivity.dialog = null
        }
    }

}
