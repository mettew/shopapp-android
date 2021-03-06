package com.shopapp.data.impl

import com.shopapp.gateway.entity.CartProduct
import com.shopapp.domain.database.Dao
import com.shopapp.domain.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class CartRepositoryImpl(private val dao: Dao) : CartRepository {

    override fun getCartProductList(): Observable<List<CartProduct>> {
        return dao.getCartDataList()
    }

    override fun addCartProduct(cartProduct: CartProduct): Single<CartProduct> {
        return dao.addCartProduct(cartProduct)
    }

    override fun deleteProductFromCart(productVariantId: String): Completable {
        return dao.deleteProductFromCart(productVariantId)
    }

    override fun deleteAllProductsFromCart(): Completable {
        return dao.deleteAllProductsFromCart()
    }

    override fun changeCartProductQuantity(productVariantId: String, newQuantity: Int): Single<CartProduct> {
        return dao.changeCartProductQuantity(productVariantId, newQuantity)
    }

}