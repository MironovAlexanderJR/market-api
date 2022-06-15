package ru.mironov.marketapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class ShopUnit {

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    private UUID parentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", locale = "US")
    private ZonedDateTime date;

    @Column(columnDefinition = "NUMERIC(64)")
    private Integer price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "id_parentId",
            joinColumns = @JoinColumn(name = "parentId"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<ShopUnit> children = null;

    public void addShopUnit(ShopUnit shopUnit) {
        if (children == null)
            children = new ArrayList<>();

        children.add(shopUnit);
    }

    public void deleteFromParentChildren(ShopUnit shopUnit) {
        this.children.remove(shopUnit);
    }
}