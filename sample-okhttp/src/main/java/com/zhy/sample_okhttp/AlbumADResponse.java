package com.zhy.sample_okhttp;

import com.google.gson.annotations.SerializedName;

/**
 * @author caicai
 * @create 2019/4/18
 * @Describe
 */
public class AlbumADResponse {

    /**
     * isShow : false
     * default : {"thumbnail":"https://mjxc.yun2win.com/images/tv_ad.jpg","imgUrl":"https://mjxc.yun2win.com/images/tv_ad.jpg","linkUrl":""}
     * showTime : 10
     */

    private boolean isShow;
    @SerializedName("default")
    private DefaultBean defaultX;
    private int showTime;

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public DefaultBean getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(DefaultBean defaultX) {
        this.defaultX = defaultX;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public static class DefaultBean {
        /**
         * thumbnail : https://mjxc.yun2win.com/images/tv_ad.jpg
         * imgUrl : https://mjxc.yun2win.com/images/tv_ad.jpg
         * linkUrl :
         */

        private String thumbnail;
        private String imgUrl;
        private String linkUrl;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
