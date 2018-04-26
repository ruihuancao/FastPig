package com.ruihuan.fastpig.data;

import com.google.gson.annotations.SerializedName;

/**
 * Description:
 * Data：2018/4/25-14:11
 * Author: caoruihuan
 */
public class PoetryDataBean {

    /**
     * author : {"id":12537,"intro":"無傳。","name":"李長沙"}
     * poetry : {"author":"李長沙","author_id":12537,"content":"平時已秉班揚筆，暇處不妨甘石經。|吾里忻傳日邊信，君言頻中斗杓星。|會稽夫子餘詩禮，巴蜀君平舊典型。|歷歷周天三百度，更參璿玉到虞廷。","id":1,"title":"贈談命嚴叔寓"}
     */

    @SerializedName("author")
    private AuthorBean author;
    @SerializedName("poetry")
    private PoetryBean poetry;

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public PoetryBean getPoetry() {
        return poetry;
    }

    public void setPoetry(PoetryBean poetry) {
        this.poetry = poetry;
    }

    public static class AuthorBean {
        /**
         * id : 12537
         * intro : 無傳。
         * name : 李長沙
         */

        @SerializedName("id")
        private int id;
        @SerializedName("intro")
        private String intro;
        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PoetryBean {
        /**
         * author : 李長沙
         * author_id : 12537
         * content : 平時已秉班揚筆，暇處不妨甘石經。|吾里忻傳日邊信，君言頻中斗杓星。|會稽夫子餘詩禮，巴蜀君平舊典型。|歷歷周天三百度，更參璿玉到虞廷。
         * id : 1
         * title : 贈談命嚴叔寓
         */

        @SerializedName("author")
        private String author;
        @SerializedName("author_id")
        private int authorId;
        @SerializedName("content")
        private String content;
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
