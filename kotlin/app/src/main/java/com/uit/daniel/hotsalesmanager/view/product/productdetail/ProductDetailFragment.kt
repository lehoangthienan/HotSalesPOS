package com.uit.daniel.hotsalesmanager.view.product.productdetail

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import com.uit.daniel.hotsalesmanager.view.order.orderproduct.OrderProductActivity
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment() {

    private val priceUtils = PriceUtils()
    private var productId: String = ""
    private var product = Product(
        "1",
        "Iphone Xs Max",
        15000000,
        50,
        "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
        "TiKi",
        "Theo chia sẻ của Apple, A12 Bionic gồm CPU 6 nhân, GPU 4 nhân và chip xử lý AI Neural Engine 8 nhân. Ngoài ra con chip này còn tích hợp rất nhiều tính năng xử lý như xử lý hình ảnh, video, âm thanh…\n" +
                "\n" +
                "Với thiết kế và công nghệ mới, A12 Bionic có thể mang tới hiệu năng CPU nhanh hơn tới 15% mà vẫn tiết kiệm 40% điện năng so với A11 Bionic. GPU trên A12 cũng nhanh hơn tới 50%. Riêng chip Neural Engine có thể xử lý 5 nghìn tỷ lệnh mỗi giây. Việc trang bị chip A12 Bionic cũng giúp iPhone xử lý được bộ nhớ lên tới 512 GB.\n" +
                "\n" +
                "Trong thực tế, việc sử dụng chip mới sẽ giúp ứng dụng mở nhanh hơn, hiệu năng từ các chương trình thực tế ảo cũng sẽ tăng. Tuy nhiên sẽ rất khó để nhận biết sự khác biệt nếu không dùng hai máy cùng lúc, bởi iPhone X đã có hiệu năng rất mạnh mẽ."
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductDetail()
        initView()
        addEvents()
    }

    private fun getProductDetail() {
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tvProductName.text = product.name
        try {
            ivProduct?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(product.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
        tvPrice.text = product.price?.let { priceUtils.setStringMoney(it) }
        tvPriceDiscount.text = priceUtils.setStringMoney(product.discount?.let {
            product.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        tvPercentDiscount.text = product.discount.toString() + "%"
        tvBranchName.text = product.branch
        tvContent.text = product.content
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
        }
        btOrder.setOnClickListener {
            startOrderActivity()
        }
    }

    private fun startOrderActivity() {
        val intent = Intent(activity, OrderProductActivity::class.java)
        intent.putExtra("ID", productId)
        activity.startActivity(intent)
    }

}