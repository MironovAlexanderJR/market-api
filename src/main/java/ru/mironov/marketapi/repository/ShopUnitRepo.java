package ru.mironov.marketapi.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mironov.marketapi.domain.entity.ShopUnit;
import ru.mironov.marketapi.domain.entity.ShopUnitType;

@Repository
public interface ShopUnitRepo extends JpaRepository<ShopUnit, UUID> {

    List<ShopUnit> findAllByType(ShopUnitType shopUnitTyp);

}
