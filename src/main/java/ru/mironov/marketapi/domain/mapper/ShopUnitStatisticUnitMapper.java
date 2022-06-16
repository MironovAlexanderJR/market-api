package ru.mironov.marketapi.domain.mapper;

import org.mapstruct.Mapper;
import ru.mironov.marketapi.domain.dto.ShopUnitStatisticUnit;
import ru.mironov.marketapi.domain.entity.ShopUnit;

@Mapper
public interface ShopUnitStatisticUnitMapper {

    ShopUnitStatisticUnit fromShopUnit(ShopUnit source);
}
