package com.application.seb.projet5_mynews.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse implements java.io.Serializable {


    @SerializedName("response")
    @Expose
    private TheResponse theResponse;

    public TheResponse getTheResponse() {
        return theResponse;
    }

    public class TheResponse {

        @SerializedName("docs")
        @Expose
        private List<Doc> docs = null;

        public List<Doc> getDocs() {
            return docs;
        }
    }

    public class Doc {

        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;
        @SerializedName("web_url")
        @Expose
        private String webUrl;
        @SerializedName("snippet")
        @Expose
        private String snippet;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("section_name")
        @Expose
        private String sectionName;
        @SerializedName("_id")
        @Expose
        private String id;

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public String getSnippet() {
            return snippet;
        }

        public String getPubDate() {
            return pubDate;
        }

        public String getSectionName() {
            return sectionName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }

    public class Multimedium {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }
    }
}