package com.rbt.merchant.domain.use_case.ui_models.order_details

data class OrderDetailsDataModel(
    val order_id: Long,
    val order_status: Int,
    val order_invoice: OrderDetailsInvoiceModel,
    val order_delivery_info: OrderDetailsDeliveryInfoModel,
    val order_delivery_data: OrderDetailsDeliveryModel,
    val order_client: OrderDetailsClientModel
)
