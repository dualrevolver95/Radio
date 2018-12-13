
package com.example.manu.radiov2.Classes;

import java.util.HashMap;
import java.util.Map;

public class Datum {

    private String title;
    private String song;
    private Track track;
    private String bitrate;
    private String server;
    private String autodj;
    private String source;
    private Boolean offline;
    private String summary;
    private Integer listeners;
    private Integer maxlisteners;
    private Integer reseller;
    private Boolean serverstate;
    private Boolean sourcestate;
    private Integer sourceconn;
    private String date;
    private String time;
    private String rawmeta;
    private String mountpoint;
    private String tuneinurl;
    private String directtuneinurl;
    private String proxytuneinurl;
    private String tuneinformat;
    private String webplayer;
    private String servertype;
    private Integer listenertotal;
    private String url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAutodj() {
        return autodj;
    }

    public void setAutodj(String autodj) {
        this.autodj = autodj;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(Integer listeners) {
        this.listeners = listeners;
    }

    public Integer getMaxlisteners() {
        return maxlisteners;
    }

    public void setMaxlisteners(Integer maxlisteners) {
        this.maxlisteners = maxlisteners;
    }

    public Integer getReseller() {
        return reseller;
    }

    public void setReseller(Integer reseller) {
        this.reseller = reseller;
    }

    public Boolean getServerstate() {
        return serverstate;
    }

    public void setServerstate(Boolean serverstate) {
        this.serverstate = serverstate;
    }

    public Boolean getSourcestate() {
        return sourcestate;
    }

    public void setSourcestate(Boolean sourcestate) {
        this.sourcestate = sourcestate;
    }

    public Integer getSourceconn() {
        return sourceconn;
    }

    public void setSourceconn(Integer sourceconn) {
        this.sourceconn = sourceconn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRawmeta() {
        return rawmeta;
    }

    public void setRawmeta(String rawmeta) {
        this.rawmeta = rawmeta;
    }

    public String getMountpoint() {
        return mountpoint;
    }

    public void setMountpoint(String mountpoint) {
        this.mountpoint = mountpoint;
    }

    public String getTuneinurl() {
        return tuneinurl;
    }

    public void setTuneinurl(String tuneinurl) {
        this.tuneinurl = tuneinurl;
    }

    public String getDirecttuneinurl() {
        return directtuneinurl;
    }

    public void setDirecttuneinurl(String directtuneinurl) {
        this.directtuneinurl = directtuneinurl;
    }

    public String getProxytuneinurl() {
        return proxytuneinurl;
    }

    public void setProxytuneinurl(String proxytuneinurl) {
        this.proxytuneinurl = proxytuneinurl;
    }

    public String getTuneinformat() {
        return tuneinformat;
    }

    public void setTuneinformat(String tuneinformat) {
        this.tuneinformat = tuneinformat;
    }

    public String getWebplayer() {
        return webplayer;
    }

    public void setWebplayer(String webplayer) {
        this.webplayer = webplayer;
    }

    public String getServertype() {
        return servertype;
    }

    public void setServertype(String servertype) {
        this.servertype = servertype;
    }

    public Integer getListenertotal() {
        return listenertotal;
    }

    public void setListenertotal(Integer listenertotal) {
        this.listenertotal = listenertotal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
