package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.scanning.IdentifiedIngredient;
import net.skai.chefplaneteapi.domain.scanning.ImageRequest;
import net.skai.chefplaneteapi.service.ReceiptScanningService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ReceiptScanningController extends AbstractApiController {

    public static int testCount = 0;

    private ReceiptScanningService receiptScanningService;

    private final boolean isUsingMocks;

    public ReceiptScanningController(@NotNull final ReceiptScanningService receiptScanningService,
                                     @Value("${chefplanete.isUsingMocks}") final boolean isUsingMocks) {
        this.receiptScanningService = receiptScanningService;
        this.isUsingMocks = isUsingMocks;
    }

    @PostMapping("/receipt")
    public List<IdentifiedIngredient> identifyIngredientsInReceipt(@NotNull @RequestBody final ImageRequest imageRequest) {
        if (isUsingMocks) {
            if (testCount == 0) {
                testCount = 1;
                return Arrays.asList(
                        new IdentifiedIngredient("Apples", false),
                        new IdentifiedIngredient("Grapes", false),
                        new IdentifiedIngredient("Almonds", false),
                        new IdentifiedIngredient("Milk 2%", false),
                        new IdentifiedIngredient("Butter", false),
                        new IdentifiedIngredient("Chives", false)
                );
            }
            else {
                testCount = 0;
                return Arrays.asList(
                        new IdentifiedIngredient("Oranges", false),
                        new IdentifiedIngredient("Corn", false),
                        new IdentifiedIngredient("Kidney Beans", false),
                        new IdentifiedIngredient("White Rice", false),
                        new IdentifiedIngredient("Fennel", false)
                );
            }
        }
        return receiptScanningService.identifyIngredientsFromImage(imageRequest.getBase64Image());
    }
}
