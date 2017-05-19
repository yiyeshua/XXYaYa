package com.yiyeshu.xxyaya.bean;

import java.util.List;

/**
 * Created by lhw on 2017/5/19.
 */
public class Book {

    /**
     * count : 2
     * start : 0
     * total : 1024
     * books : [{"rating":{"max":10,"numRaters":20,"average":"5.1","min":0},"subtitle":"A Programmer's Guide","author":["J.F. DiMarzio"],"pubdate":"2008-7-30","tags":[{"count":16,"name":"Android","title":"Android"},{"count":6,"name":"Mobile","title":"Mobile"},{"count":4,"name":"Google","title":"Google"},{"count":3,"name":"Phone","title":"Phone"},{"count":2,"name":"技术","title":"技术"},{"count":1,"name":"Android.A.Programmers.Guide","title":"Android.A.Programmers.Guide"},{"count":1,"name":"programming","title":"programming"},{"count":1,"name":"力的来源!","title":"力的来源!"}],"origin_title":"","image":"https://img3.doubanio.com/mpic/s4259006.jpg","binding":"Paperback","translator":[],"catalog":"","pages":"400","images":{"small":"https://img3.doubanio.com/spic/s4259006.jpg","large":"https://img3.doubanio.com/lpic/s4259006.jpg","medium":"https://img3.doubanio.com/mpic/s4259006.jpg"},"alt":"https://book.douban.com/subject/3134548/","id":"3134548","publisher":"McGraw-Hill Osborne Media","isbn10":"0071599886","isbn13":"9780071599887","title":"Android","url":"https://api.douban.com/v2/book/3134548","alt_title":"","author_intro":"","summary":"Master the Android mobile development platform  Build compelling Java-based mobile applications using the Android SDK and the Eclipse open-source software development platform. Android: A Programmer's Guide shows you, step-by-step, how to download and set up all of the necessary tools, build and tune dynamic Android programs, and debug your results. Discover how to provide web and chat functions, interact with the phone dialer and GPS devices, and access the latest Google services. You'll also learn how to create custom Content Providers and database-enable your applications using SQLite.  Install and configure Java, Eclipse, and Android plugin  Create Android projects from the Eclipse UI or command line  Integrate web content, images, galleries, and sounds  Deploy menus, progress bars, and auto-complete functions  Trigger actions using Android Intents, Filters, and Receivers  Implement GPS, Google Maps, Google Earth, and GTalk Build interactive SQLite databases, calendars, and notepads  Test applications using the Android Emulator and Debug Bridge","price":"USD 39.99"},{"rating":{"max":10,"numRaters":0,"average":"0.0","min":0},"subtitle":"App Development & Programming Guide: Learn In A Day!","author":["Os Swift"],"pubdate":"2015-10-2","tags":[],"origin_title":"","image":"https://img1.doubanio.com/mpic/s28348439.jpg","binding":"Paperback","translator":[],"catalog":"","pages":"180","images":{"small":"https://img1.doubanio.com/spic/s28348439.jpg","large":"https://img1.doubanio.com/lpic/s28348439.jpg","medium":"https://img1.doubanio.com/mpic/s28348439.jpg"},"alt":"https://book.douban.com/subject/26671781/","id":"26671781","publisher":"CreateSpace Independent Publishing Platform","isbn10":"1517640091","isbn13":"9781517640095","title":"Android","url":"https://api.douban.com/v2/book/26671781","alt_title":"","author_intro":"","summary":"","price":"USD 28.97"}]
     */

    private int count;
    private int start;
    private int total;
    private List<BookBean> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }

    public static class BookBean {
        /**
         * rating : {"max":10,"numRaters":20,"average":"5.1","min":0}
         * subtitle : A Programmer's Guide
         * author : ["J.F. DiMarzio"]
         * pubdate : 2008-7-30
         * tags : [{"count":16,"name":"Android","title":"Android"},{"count":6,"name":"Mobile","title":"Mobile"},{"count":4,"name":"Google","title":"Google"},{"count":3,"name":"Phone","title":"Phone"},{"count":2,"name":"技术","title":"技术"},{"count":1,"name":"Android.A.Programmers.Guide","title":"Android.A.Programmers.Guide"},{"count":1,"name":"programming","title":"programming"},{"count":1,"name":"力的来源!","title":"力的来源!"}]
         * origin_title :
         * image : https://img3.doubanio.com/mpic/s4259006.jpg
         * binding : Paperback
         * translator : []
         * catalog :
         * pages : 400
         * images : {"small":"https://img3.doubanio.com/spic/s4259006.jpg","large":"https://img3.doubanio.com/lpic/s4259006.jpg","medium":"https://img3.doubanio.com/mpic/s4259006.jpg"}
         * alt : https://book.douban.com/subject/3134548/
         * id : 3134548
         * publisher : McGraw-Hill Osborne Media
         * isbn10 : 0071599886
         * isbn13 : 9780071599887
         * title : Android
         * url : https://api.douban.com/v2/book/3134548
         * alt_title :
         * author_intro :
         * summary : Master the Android mobile development platform  Build compelling Java-based mobile applications using the Android SDK and the Eclipse open-source software development platform. Android: A Programmer's Guide shows you, step-by-step, how to download and set up all of the necessary tools, build and tune dynamic Android programs, and debug your results. Discover how to provide web and chat functions, interact with the phone dialer and GPS devices, and access the latest Google services. You'll also learn how to create custom Content Providers and database-enable your applications using SQLite.  Install and configure Java, Eclipse, and Android plugin  Create Android projects from the Eclipse UI or command line  Integrate web content, images, galleries, and sounds  Deploy menus, progress bars, and auto-complete functions  Trigger actions using Android Intents, Filters, and Receivers  Implement GPS, Google Maps, Google Earth, and GTalk Build interactive SQLite databases, calendars, and notepads  Test applications using the Android Emulator and Debug Bridge
         * price : USD 39.99
         */

        private RatingBean rating;
        private String subtitle;
        private String pubdate;
        private String origin_title;
        private String image;
        private String binding;
        private String catalog;
        private String pages;
        private ImagesBean images;
        private String alt;
        private String id;
        private String publisher;
        private String isbn10;
        private String isbn13;
        private String title;
        private String url;
        private String alt_title;
        private String author_intro;
        private String summary;
        private String price;
        private List<String> author;
        private List<TagsBean> tags;
        private List<?> translator;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getOrigin_title() {
            return origin_title;
        }

        public void setOrigin_title(String origin_title) {
            this.origin_title = origin_title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn10() {
            return isbn10;
        }

        public void setIsbn10(String isbn10) {
            this.isbn10 = isbn10;
        }

        public String getIsbn13() {
            return isbn13;
        }

        public void setIsbn13(String isbn13) {
            this.isbn13 = isbn13;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlt_title() {
            return alt_title;
        }

        public void setAlt_title(String alt_title) {
            this.alt_title = alt_title;
        }

        public String getAuthor_intro() {
            return author_intro;
        }

        public void setAuthor_intro(String author_intro) {
            this.author_intro = author_intro;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<String> getAuthor() {
            return author;
        }

        public void setAuthor(List<String> author) {
            this.author = author;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<?> getTranslator() {
            return translator;
        }

        public void setTranslator(List<?> translator) {
            this.translator = translator;
        }

        public static class RatingBean {
            /**
             * max : 10
             * numRaters : 20
             * average : 5.1
             * min : 0
             */

            private int max;
            private int numRaters;
            private String average;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getNumRaters() {
                return numRaters;
            }

            public void setNumRaters(int numRaters) {
                this.numRaters = numRaters;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/spic/s4259006.jpg
             * large : https://img3.doubanio.com/lpic/s4259006.jpg
             * medium : https://img3.doubanio.com/mpic/s4259006.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class TagsBean {
            /**
             * count : 16
             * name : Android
             * title : Android
             */

            private int count;
            private String name;
            private String title;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
