package com.example.rese_yl.ui.fragments

import FirestoreClass
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.rese_yl.R
import com.example.rese_yl.models.Product
import com.example.rese_yl.ui.activities.CartListActivity
import com.example.rese_yl.ui.activities.SearchActivity
import com.example.rese_yl.ui.activities.SettingsActivity
import com.example.rese_yl.ui.adapters.DashboardItemsListAdapter
import com.example.rese_yl.ui.adapters.DashboardItemsPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.Timer
import java.util.TimerTask

class DashboardFragment : BaseFragment() {

    private var timer: Timer? = null
    private var currentPage = 0
    private var mDashboardItemsList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        startAutoSlider()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_search ->{
                startActivity(Intent(activity, SearchActivity::class.java))
                return true
            }

            R.id.action_settings -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }

            R.id.action_cart -> {

                startActivity(Intent(activity, CartListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        if(timer!=null){
            stopAutoSlider()
        }
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    private fun getDashboardItemsList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)

        val customFont = Typeface.createFromAsset(context?.assets, "Bangers-Regular.ttf")
        tv_discount.typeface = customFont
    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {
        hideProgressDialog()


        if (dashboardItemsList.size > 0) {
            val randomProducts = dashboardItemsList.shuffled().take(3) // Extract 3 random products
            rv_dashboard_items.visibility = View.VISIBLE
            tv_no_dashboard_items_found.visibility = View.GONE
            view_pager.visibility = View.VISIBLE
            dots_indicator.visibility = View.VISIBLE
            discount_container.visibility = View.VISIBLE
            tv_discount.visibility = View.VISIBLE

            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
            rv_dashboard_items.setHasFixedSize(true)

            val dashboardAdapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            rv_dashboard_items.adapter = dashboardAdapter

            mDashboardItemsList = randomProducts as ArrayList<Product>
            val viewPager: ViewPager2 = view_pager
            val adapter = DashboardItemsPagerAdapter(randomProducts)
            viewPager.adapter = adapter

            val dotsIndicator: DotsIndicator = dots_indicator
            dotsIndicator.setViewPager2(viewPager)

        } else {
            view_pager.visibility = View.GONE
            discount_container.visibility = View.GONE
            dots_indicator.visibility = View.GONE
            rv_dashboard_items.visibility = View.GONE
            tv_discount.visibility = View.GONE
            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }
    }

    private fun startAutoSlider() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    if (currentPage == mDashboardItemsList.size) {
                        currentPage = 0
                    }
                    view_pager.setCurrentItem(currentPage++, true)
                }
            }
        }, 3000)
    }
    private fun stopAutoSlider() {
        timer?.cancel()
    }

}