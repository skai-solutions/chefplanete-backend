package net.skai.chefplaneteapi.domain.scanning;

import org.jetbrains.annotations.NotNull;

public class ImageRequest {

    private final String base64Image;

    private final String imageType;

    public ImageRequest(@NotNull final String base64Image, @NotNull final String imageType) {
        this.base64Image = base64Image;
        this.imageType = imageType;
    }

    public String getImageType() {
        return imageType;
    }

    public String getBase64Image() {
        return base64Image;
    }
}
