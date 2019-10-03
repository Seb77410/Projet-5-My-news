package com.application.seb.projet5_mynews.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStoriesResponse {

    @SerializedName("results")
    @Expose
    private List<TopStoriesResult> topStoriesResults = null;

    public List<TopStoriesResult> getTopStoriesResults() {
        return topStoriesResults;
    }

    public void setTopStoriesResults(List<TopStoriesResult> topStoriesResults) {
        this.topStoriesResults = topStoriesResults;
    }


    public class TopStoriesResult {

        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("created_date")
        @Expose
        private String publishedDate;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;

        public String getSection() {
            return section;
        }

        public String getSubsection() {return subsection;}

        public String getUrl() {
            return url;
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

        public List<Multimedium> getMultimedia() {
            return multimedia;
        }


    }

    public class Multimedium {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
