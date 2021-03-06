package com.shopapp.ui.category

import android.content.Context
import android.view.View
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.shopapp.TestShopApplication
import com.shopapp.gateway.entity.SortType
import com.shopapp.test.MockInstantiator
import com.shopapp.ui.const.Constant.DEFAULT_PER_PAGE_COUNT
import com.shopapp.ui.product.ProductDetailsActivity
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_lce.*
import kotlinx.android.synthetic.main.layout_lce.*
import kotlinx.android.synthetic.main.view_base_toolbar.view.*
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = TestShopApplication::class)
class CategoryActivityTest {

    private lateinit var context: Context
    private lateinit var activity: CategoryActivity

    @Before
    fun setUpTest() {
        context = RuntimeEnvironment.application.baseContext
        val intent = CategoryActivity.getStartIntent(context, MockInstantiator.newCategory())
        activity = Robolectric.buildActivity(CategoryActivity::class.java, intent)
            .create()
            .resume()
            .visible()
            .get()
    }

    @Test
    fun shouldSetTitleOnCreate() {
        assertEquals(MockInstantiator.DEFAULT_TITLE, activity.toolbar.toolbarTitle.text)
    }

    @Test
    fun shouldLoadCategoryOnCreate() {
        verify(activity.presenter).loadProductList(
            DEFAULT_PER_PAGE_COUNT,
            null,
            MockInstantiator.DEFAULT_ID,
            SortType.NAME
        )
    }

    @Test
    fun shouldLoadCategory() {
        activity.loadData()
        verify(activity.presenter, times(2)).loadProductList(
            DEFAULT_PER_PAGE_COUNT,
            null,
            MockInstantiator.DEFAULT_ID,
            SortType.NAME
        )
    }

    @Test
    fun shouldShowSortPopupWhenSortLayoutClicked() {
        activity.sortLayout.performClick()

        val popup = Shadows.shadowOf(RuntimeEnvironment.application).latestPopupWindow
        Assert.assertNotNull(popup)
    }

    @Test
    fun shouldShowContentView() {
        val size = 5
        val productList = MockInstantiator.newList(MockInstantiator.newProduct(), size)
        activity.showContent(productList)
        assertEquals(View.GONE, activity.emptyView.visibility)
        assertEquals(false, activity.swipeRefreshLayout.isRefreshing)
        assertEquals(size, activity.recyclerView.adapter.itemCount)
    }

    @Test
    fun shouldStartProductDetailsActivity() {
        val productMock = MockInstantiator.newProduct()
        activity.onItemClicked(productMock, 0)
        val startedIntent = Shadows.shadowOf(activity).nextStartedActivity
        val shadowIntent = Shadows.shadowOf(startedIntent)
        assertEquals(productMock.id, startedIntent.extras.getString(ProductDetailsActivity.EXTRA_PRODUCT_ID))
        assertEquals(ProductDetailsActivity::class.java, shadowIntent.intentClass)
    }

    @Test
    fun shouldReloadProductWithNewSortType() {
        activity.onSortTypeChanged(SortType.RECENT)
        verify(activity.presenter).loadProductList(
            DEFAULT_PER_PAGE_COUNT,
            null,
            MockInstantiator.DEFAULT_ID,
            SortType.RECENT
        )
    }

    @After
    fun tearDown() {
        activity.finish()
    }

}
