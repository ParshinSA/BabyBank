package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.interf.MockDataSource
import com.example.babybank.data.toMenuItemDomain
import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.MenuTypeDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import io.reactivex.Single
import javax.inject.Inject

class MenuItemRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource
) : MenuItemRepository {

    private val allMenus = mutableMapOf<MenuTypeDomain, List<MenuItemDomain>>()

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>> {
        val keyMenu = request.menuType

        return if (allMenus.containsKey(keyMenu))
            Single.just(allMenus.getValue(keyMenu))
        else mockDataSource.getMenu(request)
            .map { operationMenuItemList ->
                val menuItemDomainList = operationMenuItemList.map { menuItemDto ->
                    menuItemDto.toMenuItemDomain()
                }
                allMenus[keyMenu] = menuItemDomainList
                menuItemDomainList
            }
    }

}