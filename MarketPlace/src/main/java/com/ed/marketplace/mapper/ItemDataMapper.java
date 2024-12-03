package com.ed.marketplace.mapper;

import com.ed.marketplace.entity.Item;
import com.ed.marketplace.entity.kafka.ItemDataForOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemDataMapper {

    @Mapping(source = "title", target = "itemTitle")
    ItemDataForOrder itemToItemDataForOrder(Item item);

    @Mapping(source = "itemTitle", target = "title")
    Item itemDataForOrderToItem(ItemDataForOrder itemDataForOrder);
}
