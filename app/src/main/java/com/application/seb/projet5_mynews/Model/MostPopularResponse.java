package com.application.seb.projet5_mynews.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MostPopularResponse {

    /**
     * No args constructor for use in serialization
     */
    public MostPopularResponse() {
    }
    @SerializedName("results")
    @Expose
    private List<MostPopularResult> results = null;

    public List<MostPopularResult> getResults() {
        return results;
    }


    public class MediaMetadatum {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {return url;}

    }
    public class Medium {

        @SerializedName("media-metadata")
        @Expose
        private List<MediaMetadatum> mediaMetadata = null;

        public List<MediaMetadatum> getMediaMetadata() { return mediaMetadata; }
    }

    public class MostPopularResult {
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        public String getUrl() {
            return url;
        }

        public String getSection() {
            return section;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<Medium> getMedia() {return media;}

    }
}

