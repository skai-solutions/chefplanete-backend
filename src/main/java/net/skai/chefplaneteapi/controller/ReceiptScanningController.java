package net.skai.chefplaneteapi.controller;

import net.skai.chefplaneteapi.domain.scanning.IdentifiedIngredient;
import net.skai.chefplaneteapi.domain.scanning.ImageRequest;
import net.skai.chefplaneteapi.service.ReceiptScanningService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceiptScanningController extends AbstractApiController {

    private ReceiptScanningService receiptScanningService;

    public ReceiptScanningController(@NotNull final ReceiptScanningService receiptScanningService) {
        this.receiptScanningService = receiptScanningService;
    }

    @PostMapping("/receipt")
    public List<IdentifiedIngredient> identifyIngredientsInReceipt(@NotNull @RequestBody final ImageRequest imageRequest) {
        return receiptScanningService.identifyIngredientsFromImage(imageRequest.getBase64Image());
    }
}
