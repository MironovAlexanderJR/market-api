package ru.mironov.marketapi.controller;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mironov.marketapi.domain.dto.ShopUnitDto;
import ru.mironov.marketapi.domain.dto.ShopUnitImportRequest;
import ru.mironov.marketapi.domain.exception.ValidationFailedException;
import ru.mironov.marketapi.domain.mapper.ShopUnitMapper;
import ru.mironov.marketapi.service.ShopUnitServiceImpl;


@RestController
@RequiredArgsConstructor
public class ShoUnitController {

    private final ShopUnitServiceImpl shopUnitService;
    private final ShopUnitMapper shopUnitMapper;

    @PostMapping("/imports")
    public void importingNewProductsAndOrCategories(
            @Valid @RequestBody ShopUnitImportRequest shopUnitImportRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationFailedException();
        shopUnitService.importingNewProductsAndOrCategories(shopUnitImportRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategoryOrProductById(@PathVariable UUID id) {
        shopUnitService.deleteShopUnitById(id);
    }

    @GetMapping("/nodes/{id}")
    public ShopUnitDto getShopUnitById(@PathVariable UUID id) {
        return shopUnitMapper.toDto(shopUnitService.getShopUnit(id));
    }
}