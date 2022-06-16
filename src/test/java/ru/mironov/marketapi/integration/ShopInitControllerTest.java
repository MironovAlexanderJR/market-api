package ru.mironov.marketapi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mironov.marketapi.domain.dto.ShopUnitDto;
import ru.mironov.marketapi.domain.dto.ShopUnitImportRequest;
import ru.mironov.marketapi.domain.dto.ShopUnitStatisticResponse;
import ru.mironov.marketapi.service.ShopUnitService;

public class ShopInitControllerTest extends AbstractTest {

    private final String BASE_ID = "df634f0e-0815-41f9-93d1-301f503b5991";

    @Autowired
    private ShopUnitService shopUnitService;

    @AfterEach
    void deleteEntities() {
        shopUnitService.deleteShopUnitById(UUID.fromString(BASE_ID));
    }

    @Test
    public void testImportAndGetShopUnit() {

        ShopUnitImportRequest shopUnitImportRequest1 = getClassPathResourceAsObject(
                "/request/import-and-get/first_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitImportRequest shopUnitImportRequest2 = getClassPathResourceAsObject(
                "/request/import-and-get/second_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitImportRequest notValidShopUnitImportRequest = getClassPathResourceAsObject(
                "/request/import-and-get/not_valid_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitDto shopUnitDto1 = getClassPathResourceAsObject(
                "/excepted/import-and-get/first_shop_unit_dto.json", new TypeReference<ShopUnitDto>() {});

        ShopUnitDto shopUnitDto2 = getClassPathResourceAsObject(
                "/excepted/import-and-get/second_shop_unit_dto.json", new TypeReference<ShopUnitDto>() {});

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(notValidShopUnitImportRequest)
                .exchange()
                .expectStatus().isBadRequest();

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest1)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/nodes/{id}", BASE_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitDto.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitDto1));

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest2)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/nodes/{id}", BASE_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitDto.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitDto2));
    }

    @Test
    public void testDeleteShopUnit() {

        final String notFoundId = "96c3b21f-3841-4fca-913c-fa700046b666";
        final String bookId1 = "96c3b21f-3841-4fca-913c-fa700046b6c2";
        final String books = "96c3b21f-3841-4fca-913c-fa700046b6c1";

        ShopUnitImportRequest shopUnitImportRequest1 = getClassPathResourceAsObject(
                "/request/delete/delete_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitDto shopUnitDto1 = getClassPathResourceAsObject(
                "/excepted/delete/first_shop_unit_dto.json", new TypeReference<ShopUnitDto>() {});

        ShopUnitDto shopUnitDto2 = getClassPathResourceAsObject(
                "/excepted/delete/second_shop_unit_dto.json", new TypeReference<ShopUnitDto>() {});

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest1)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .delete()
                .uri("/delete/{id}", notFoundId)
                .exchange()
                .expectStatus().isNotFound();

        webTestClient
                .delete()
                .uri("/delete/{id}", bookId1)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/nodes/{id}", BASE_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitDto.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitDto1));

        webTestClient
                .delete()
                .uri("/delete/{id}", books)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/nodes/{id}", BASE_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitDto.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitDto2));
    }

    @Test
    public void testSales() {

        final String nonExistentDate = "2022-02-01T12:00:00.000Z";
        final String firstDate = "2022-02-02T12:00:00.000Z";
        final String secondDate = "2022-02-03T12:00:00.000Z";

        ShopUnitImportRequest shopUnitImportRequest1 = getClassPathResourceAsObject(
                "/request/sales/first_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitImportRequest shopUnitImportRequest2 = getClassPathResourceAsObject(
                "/request/sales/second_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitImportRequest shopUnitImportRequest3 = getClassPathResourceAsObject(
                "/request/sales/third_shop_unit_import_request.json", new TypeReference<ShopUnitImportRequest>() {});

        ShopUnitStatisticResponse shopUnitStatisticResponse1 = getClassPathResourceAsObject(
                "/excepted/sales/first_shop_unit_statistic_response.json", new TypeReference<ShopUnitStatisticResponse>() {});

        ShopUnitStatisticResponse shopUnitStatisticResponse2 = getClassPathResourceAsObject(
                "/excepted/sales/second_shop_unit_statistic_response.json", new TypeReference<ShopUnitStatisticResponse>() {});

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest1)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest2)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/imports").build())
                .bodyValue(shopUnitImportRequest3)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/sales")
                        .queryParam("date", nonExistentDate)
                        .build())
                .exchange()
                .expectStatus().isNotFound();

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/sales")
                        .queryParam("date", firstDate)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitStatisticResponse.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitStatisticResponse1));

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/sales")
                        .queryParam("date", secondDate)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShopUnitStatisticResponse.class)
                .value(result -> Assertions.assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .isEqualTo(shopUnitStatisticResponse2));
    }
}