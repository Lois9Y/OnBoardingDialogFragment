package at.nineyards.onboarding

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import at.nineyards.onboarding.fragments.BasicOnboardingFragment
import at.nineyards.onboarding.fragments.SalesPitchFragment
import at.nineyards.onboardingdialog.OnBoardingDialog

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
                skipCallback = {skip()}
                skipText = "SKIP"
                isCancelable = false
                //nextText = "NEXT"
            }
            showOnBoardingDialog()
        }

        sales_pitch.setOnClickListener {
            dialog = OnBoardingDialog().apply {
                addFragment(SalesPitchFragment.newInstance(this@MainActivity))
                finishText = "Buy now"
                finishCallback = this@MainActivity::dismissOnBoardingDialog
                fullScreen = true
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
