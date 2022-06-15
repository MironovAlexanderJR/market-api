package ru.mironov.marketapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.mironov.marketapi.domain.entity.ShopUnitType;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = PRIVATE)
public class ShopUnitDto {

    UUID id;
    String name;
    ShopUnitType type;
    UUID parentId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", locale = "US")
    ZonedDateTime date;
    Integer price;
    List<ShopUnitDto> children;
}
