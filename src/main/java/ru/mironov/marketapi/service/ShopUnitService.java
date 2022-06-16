package ru.mironov.marketapi.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.mironov.marketapi.domain.dto.ShopUnitImportRequest;
import ru.mironov.marketapi.domain.dto.ShopUnitStatisticResponse;
import ru.mironov.marketapi.domain.entity.ShopUnit;
import ru.mironov.marketapi.domain.entity.ShopUnitType;
import ru.mironov.marketapi.domain.exception.NotFoundException;
import ru.mironov.marketapi.domain.exception.ValidationFailedException;
import ru.mironov.marketapi.domain.mapper.ShopUnitMapper;
import ru.mironov.marketapi.domain.mapper.ShopUnitStatisticUnitMapper;
import ru.mironov.marketapi.repository.ShopUnitRepo;

@Service
@Primary
@RequiredArgsConstructor
public class ShopUnitService {

    private final ShopUnitRepo shopUnitRepo;
    private final ShopUnitMapper shopUnitMapper;
    private final ShopUnitStatisticUnitMapper shopUnitStatisticUnitMapper;

    @Transactional
    public void importingNewProductsAndOrCategories(ShopUnitImportRequest shopUnitImportRequest) {
        if (shopUnitImportRequest.getItems().size() < 1)
            throw new ValidationFailedException();

        for (int i = 0; i < shopUnitImportRequest.getItems().size(); i++) {
            ShopUnit shopUnit = shopUnitMapper.fromShopUnitImport(shopUnitImportRequest.getItems().get(i));
            if (shopUnit.getName() == null || shopUnit.getId() == null)
                throw new ValidationFailedException();

            if (shopUnit.getParentId() == null) {
                shopUnit.setDate(ZonedDateTime.parse(shopUnitImportRequest.getUpdateDate()));

                shopUnitRepo.save(shopUnit);
            }
            else {
                ShopUnit parentShopUnit = shopUnitRepo.findById(shopUnit.getParentId())
                                .orElseThrow(NotFoundException::new);

                setCorrectDate(ZonedDateTime.parse(shopUnitImportRequest.getUpdateDate()), shopUnit);

                parentShopUnit.addShopUnit(shopUnit);
                shopUnitRepo.save(parentShopUnit);
            }
        }
    }

    @Transactional
    public void deleteShopUnitById(UUID id) {
        ShopUnit shopUnit = shopUnitRepo.findById(id).orElseThrow(NotFoundException::new);
        if (shopUnit.getParentId() != null) {
            ShopUnit parentShopUnit = shopUnitRepo.findById(shopUnit.getParentId()).orElseThrow(NotFoundException::new);
            parentShopUnit.deleteFromParentChildren(shopUnit);
        }
        shopUnitRepo.delete(shopUnit);
    }

    public ShopUnit getShopUnit(UUID id) {
        ShopUnit shopUnit = shopUnitRepo.findById(id).orElseThrow(NotFoundException::new);
        setAveragePrice(shopUnit);
        return shopUnit;
    }

    public ShopUnitStatisticResponse findAllByDate(ZonedDateTime date) {

        List<ShopUnit> shopUnitList = shopUnitRepo.findAllByDateAndType(date, ShopUnitType.OFFER);

        if (shopUnitList.size() < 1) {
            throw new NotFoundException();
        }

        return ShopUnitStatisticResponse
                .builder()
                .items(shopUnitList.stream()
                        .map(shopUnitStatisticUnitMapper::fromShopUnit)
                        .collect(Collectors.toList()))
                .build();

    }

    private int[] setAveragePrice(ShopUnit shopUnit) {

        int sum = 0;
        int counterOffers = 0;
        List<ShopUnit> shopUnitList = shopUnit.getChildren();

        for (ShopUnit unit : shopUnitList) {
            if (unit.getType().equals(ShopUnitType.OFFER)) {
                sum += unit.getPrice();
                counterOffers++;
            } else {
                if (unit.getChildren() != null) {
                    int[] resultSumAndCounterOffers = setAveragePrice(unit);
                    sum += resultSumAndCounterOffers[0];
                    counterOffers += resultSumAndCounterOffers[1];
                }
            }
        }
        if (sum != 0) {
            shopUnit.setPrice(sum / counterOffers);
        }
        return new int[]{sum, counterOffers};
    }

    private void setCorrectDate(ZonedDateTime date, ShopUnit shopUnit) {
        shopUnit.setDate(date);

        if (shopUnit.getParentId() != null) {
            setCorrectDate(date, shopUnitRepo.findById(shopUnit.getParentId())
                    .orElseThrow(NotFoundException::new));
        }
    }
}