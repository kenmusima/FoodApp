package com.foodapp.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.navArgs
import com.foodapp.R
import com.foodapp.data.db.entity.Order
import com.foodapp.databinding.ActivityPaymentBinding
import com.foodapp.ui.adapter.ExpandableListAdapter
import com.foodapp.ui.payment.MpesaFragment
import com.foodapp.ui.payment.PaymentViewModel
import com.foodapp.ui.payment.VisaFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private val args: PaymentActivityArgs by navArgs()
    private val viewModel: PaymentViewModel by viewModels()
    private var expandableListDetail = HashMap<String, List<Order>>()
    private var titleList: List<String>? = null
    private var adapter: ExpandableListAdapter? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolBar)

        supportActionBar?.setHomeButtonEnabled(true)

        lifecycle.coroutineScope.launch {
            viewModel.setData(args.orders).collect { orderList ->
                expandableListDetail["Orders"] = orderList
                titleList = ArrayList(expandableListDetail.keys)
                adapter = ExpandableListAdapter(this@PaymentActivity, titleList as ArrayList<String>, expandableListDetail)
                binding.orders.setAdapter(adapter)

                val totalCost = orderList.sumOf { it.price }
            }
        }
        setUpPayment()
    }

    private fun setUpPayment() {
        val paymentItems = resources.getStringArray(R.array.payments_options)
        val adapter = ArrayAdapter(this, R.layout.list_item_payment, paymentItems)
        binding.paymentAutocompleteTxtview.setAdapter(adapter)

        binding.paymentAutocompleteTxtview.doAfterTextChanged {
            val paymentType = it.toString()
            if (paymentType == "MPESA") {
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.paymentFragmentContainer, MpesaFragment()).commitNow()
            } else {
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(
                    R.id.paymentFragmentContainer,
                    VisaFragment()
                ).commitNow()
            }
        }
    }
}