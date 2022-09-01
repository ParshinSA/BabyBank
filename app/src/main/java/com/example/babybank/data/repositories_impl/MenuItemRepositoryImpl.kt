package com.example.babybank.data.repositories_impl

import com.example.babybank.data.data_source.RemoteMockDataSource
import com.example.babybank.data.toMenuItemDomain
import com.example.babybank.domain.models.MenuItemDomain
import com.example.babybank.domain.models.RequestMenu
import com.example.babybank.domain.repositories_intf.MenuItemRepository
import io.reactivex.Single
import javax.inject.Inject

class MenuItemRepositoryImpl @Inject constructor(
    private val dataSource: RemoteMockDataSource
) : MenuItemRepository {

    override fun getMenu(request: RequestMenu): Single<List<MenuItemDomain>> {
        return dataSource.getMenu(request)
            .map { operationMenuItemList ->
                operationMenuItemList.map { menuItemDto ->
                    menuItemDto.toMenuItemDomain()
                }
            }
    }

}