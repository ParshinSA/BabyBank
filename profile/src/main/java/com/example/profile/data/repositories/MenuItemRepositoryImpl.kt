package com.example.profile.data.repositories

import com.example.profile.data.data_source.RemoteMockDataSource
import com.example.profile.data.toMenuItemDomain
import com.example.profile.domain.models.MenuItemDomain
import com.example.profile.domain.models.MenuTypeDomain
import com.example.profile.domain.repositories.MenuItemRepository
import com.example.profile.presentation.models.RequestMenu
import io.reactivex.Single
import javax.inject.Inject

class MenuItemRepositoryImpl @Inject constructor(
    private val remoteMockDataSource: RemoteMockDataSource
) : MenuItemRepository {

    private val allMenus = mutableMapOf<MenuTypeDomain, List<MenuItemDomain>>()

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>> {
        val keyMenu = request.menuType

        return if (allMenus.containsKey(keyMenu))
            Single.just(allMenus.getValue(keyMenu))
        else remoteMockDataSource.getMenu(request)
            .map { operationMenuItemList ->
                val menuItemDomainList = operationMenuItemList.map { menuItemDto ->
                    menuItemDto.toMenuItemDomain()
                }
                allMenus[keyMenu] = menuItemDomainList
                menuItemDomainList
            }
    }

}