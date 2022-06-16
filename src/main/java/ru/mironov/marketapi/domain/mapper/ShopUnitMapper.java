package ru.mironov.marketapi.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ru.mironov.marketapi.domain.dto.ShopUnitDto;
import ru.mironov.marketapi.domain.dto.ShopUnitImport;
import ru.mironov.marketapi.domain.entity.ShopUnit;

@Component
public class ShopUnitMapper {

    public ShopUnit fromShopUnitImport(@Valid ShopUnitImport source) {
        if ( source == null ) {
            return null;
        }

        ShopUnit shopUnit = new ShopUnit();

        shopUnit.setId( source.getId() );
        shopUnit.setName( source.getName() );
        shopUnit.setType( source.getType() );
        shopUnit.setParentId( source.getParentId() );
        shopUnit.setPrice( source.getPrice() );

        return shopUnit;
    }

    public ShopUnitDto toDto(ShopUnit source) {
        if (source == null) {
            return null;
        } else {
            ShopUnitDto.ShopUnitDtoBuilder shopUnitDto = ShopUnitDto.builder();
            shopUnitDto.id(source.getId());
            shopUnitDto.name(source.getName());
            shopUnitDto.type(source.getType());
            shopUnitDto.parentId(source.getParentId());
            shopUnitDto.date(source.getDate());
            shopUnitDto.price(source.getPrice());
            List<ShopUnit> list = source.getChildren();
            if (list != null && list.size() >= 1) {
                shopUnitDto.children(new ArrayList<>(list.stream().map(this::toDto).collect(Collectors.toList())));
            }else
                shopUnitDto.children(null);

            return shopUnitDto.build();
        }
    }
}
