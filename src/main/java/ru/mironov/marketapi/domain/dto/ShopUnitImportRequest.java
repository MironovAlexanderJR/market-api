package ru.mironov.marketapi.domain.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(value = PRIVATE)
public class ShopUnitImportRequest {

    @NotNull
    List<ShopUnitImport> items;

    @NotBlank
    String updateDate;
}