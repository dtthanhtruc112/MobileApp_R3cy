package com.example.models;

public class PromotionBanner {
    int promotionThumb;
    String promotionName;

    public PromotionBanner(int promotionThumb, String promotionName) {
        this.promotionThumb = promotionThumb;
        this.promotionName = promotionName;
    }

    public int getPromotionThumb() {
        return promotionThumb;
    }

    public void setPromotionThumb(int promotionThumb) {
        this.promotionThumb = promotionThumb;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
}
