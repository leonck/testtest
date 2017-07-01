package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-26 10:59:54
 */
public class HomeBannerResult {
    //banner
    public List<ListhomeBanner> homeBanner;

    public List<ListhomeBanner> getHomeBanner() {
        return homeBanner;
    }

    public void setHomeBanner(List<ListhomeBanner> homeBanner) {
        this.homeBanner = homeBanner;
    }
}