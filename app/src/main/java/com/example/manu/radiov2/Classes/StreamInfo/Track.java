
package com.example.manu.radiov2.Classes.StreamInfo;

import com.example.manu.radiov2.Classes.StreamInfo.Playlist;

import java.util.HashMap;
import java.util.Map;

public class Track {

    private String artist;
    private String title;
    private String album;
    private Integer royaltytrackid;
    private Integer started;
    private Integer id;
    private Integer length;
    private Playlist playlist;
    private String buyurl;
    private String imageurl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getRoyaltytrackid() {
        return royaltytrackid;
    }

    public void setRoyaltytrackid(Integer royaltytrackid) {
        this.royaltytrackid = royaltytrackid;
    }

    public Integer getStarted() {
        return started;
    }

    public void setStarted(Integer started) {
        this.started = started;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getBuyurl() {
        return buyurl;
    }

    public void setBuyurl(String buyurl) {
        this.buyurl = buyurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
