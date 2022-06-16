package ru.mironov.marketapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.mironov.marketapi.domain.entity.ShopUnitType;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(value = PRIVATE)
public class ShopUnitImport {

    @NotBlank
    UUID id;

    @NotNull
    String name;

    UUID parentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer price;

    @Enumerated(EnumType.STRING)
    ShopUnitType type;

}
