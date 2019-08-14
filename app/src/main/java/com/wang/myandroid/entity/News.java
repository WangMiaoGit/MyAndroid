package com.wang.myandroid.entity;

import java.util.List;

public class News {

    /**
     * list : {"current_page":4,"data":[{"id":32122,"title":"MXC交易所CARM第一期净买入大赛奖励发放公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030887452-MXC%E4%BA%A4%E6%98%93%E6%89%80CARM%E7%AC%AC%E4%B8%80%E6%9C%9F%E5%87%80%E4%B9%B0%E5%85%A5%E5%A4%A7%E8%B5%9B%E5%A5%96%E5%8A%B1%E5%8F%91%E6%94%BE%E5%85%AC%E5%91%8A","newstime":"07-24 06:34"},{"id":32117,"title":"关于火币全球站恢复ZEC充提业务的公告","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000343482-%E5%85%B3%E4%BA%8E%E7%81%AB%E5%B8%81%E5%85%A8%E7%90%83%E7%AB%99%E6%81%A2%E5%A4%8DZEC%E5%85%85%E6%8F%90%E4%B8%9A%E5%8A%A1%E7%9A%84%E5%85%AC%E5%91%8A","newstime":"07-24 06:32"},{"id":32118,"title":"6万美元BTC空投   零门槛参与BTC合约大师赛","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342802-6%E4%B8%87%E7%BE%8E%E5%85%83BTC%E7%A9%BA%E6%8A%95-%E9%9B%B6%E9%97%A8%E6%A7%9B%E5%8F%82%E4%B8%8EBTC%E5%90%88%E7%BA%A6%E5%A4%A7%E5%B8%88%E8%B5%9B","newstime":"07-24 06:09"},{"id":32102,"title":"MXC交易所关于\u201c抹茶形象大使\u201d报名延时以及规则说明公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360031226271-MXC%E4%BA%A4%E6%98%93%E6%89%80%E5%85%B3%E4%BA%8E-%E6%8A%B9%E8%8C%B6%E5%BD%A2%E8%B1%A1%E5%A4%A7%E4%BD%BF-%E6%8A%A5%E5%90%8D%E5%BB%B6%E6%97%B6%E4%BB%A5%E5%8F%8A%E8%A7%84%E5%88%99%E8%AF%B4%E6%98%8E%E5%85%AC%E5%91%8A","newstime":"07-24 04:08"},{"id":32095,"title":"JEX上线周BTC期权0731公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360030884612-JEX%E4%B8%8A%E7%BA%BF%E5%91%A8BTC%E6%9C%9F%E6%9D%830731%E5%85%AC%E5%91%8A","newstime":"07-24 03:13"},{"id":32096,"title":"JEX上线月BTC期权0828公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031225111-JEX%E4%B8%8A%E7%BA%BF%E6%9C%88BTC%E6%9C%9F%E6%9D%830828%E5%85%AC%E5%91%8A","newstime":"07-24 03:13"},{"id":32090,"title":"MXC大客户第一期招募公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360031224271-MXC%E5%A4%A7%E5%AE%A2%E6%88%B7%E7%AC%AC%E4%B8%80%E6%9C%9F%E6%8B%9B%E5%8B%9F%E5%85%AC%E5%91%8A","newstime":"07-24 02:53"},{"id":32123,"title":"系统维护公告","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030758154-%E7%B3%BB%E7%BB%9F%E7%BB%B4%E6%8A%A4%E5%85%AC%E5%91%8A","newstime":"07-24 01:54"},{"id":32007,"title":"EtherFlyer第三季度下架币对清单（一）","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030463613-EtherFlyer%E7%AC%AC%E4%B8%89%E5%AD%A3%E5%BA%A6%E4%B8%8B%E6%9E%B6%E5%B8%81%E5%AF%B9%E6%B8%85%E5%8D%95-%E4%B8%80-","newstime":"07-23 11:17"},{"id":32004,"title":"什么是ZIL换链","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342862-%E4%BB%80%E4%B9%88%E6%98%AFZIL%E6%8D%A2%E9%93%BE","newstime":"07-23 11:12"},{"id":31993,"title":"MXC交易所关于提币额度调整的公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030860272-MXC%E4%BA%A4%E6%98%93%E6%89%80%E5%85%B3%E4%BA%8E%E6%8F%90%E5%B8%81%E9%A2%9D%E5%BA%A6%E8%B0%83%E6%95%B4%E7%9A%84%E5%85%AC%E5%91%8A","newstime":"07-23 10:30"},{"id":31992,"title":"交易稳定币，限时30天享0手续费！","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342762-%E4%BA%A4%E6%98%93%E7%A8%B3%E5%AE%9A%E5%B8%81-%E9%99%90%E6%97%B630%E5%A4%A9%E4%BA%AB0%E6%89%8B%E7%BB%AD%E8%B4%B9-","newstime":"07-23 10:24"},{"id":32107,"title":"BTC合约大师赛：玩BTC合约，送特斯拉Model 3！","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000339561-BTC%E5%90%88%E7%BA%A6%E5%A4%A7%E5%B8%88%E8%B5%9B-%E7%8E%A9BTC%E5%90%88%E7%BA%A6-%E9%80%81%E7%89%B9%E6%96%AF%E6%8B%89Model-3-","newstime":"07-23 09:48"},{"id":32014,"title":"MXC Labs 即将上线ZV Chain项目，并将于7月25日开启第一轮认购","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030859832-MXC-Labs-%E5%8D%B3%E5%B0%86%E4%B8%8A%E7%BA%BFZV-Chain%E9%A1%B9%E7%9B%AE-%E5%B9%B6%E5%B0%86%E4%BA%8E7%E6%9C%8825%E6%97%A5%E5%BC%80%E5%90%AF%E7%AC%AC%E4%B8%80%E8%BD%AE%E8%AE%A4%E8%B4%AD","newstime":"07-23 09:38"},{"id":31962,"title":"JEX月LTC期权0723行权公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031195811-JEX%E6%9C%88LTC%E6%9C%9F%E6%9D%830723%E8%A1%8C%E6%9D%83%E5%85%AC%E5%91%8A","newstime":"07-23 08:02"},{"id":31964,"title":"JEX周LTC期权0723行权公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031195791-JEX%E5%91%A8LTC%E6%9C%9F%E6%9D%830723%E8%A1%8C%E6%9D%83%E5%85%AC%E5%91%8A","newstime":"07-23 08:01"},{"id":32156,"title":"新币上线 - ADZB","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030003754-%E6%96%B0%E5%B8%81%E4%B8%8A%E7%BA%BF-ADZB","newstime":"07-23 07:37"},{"id":31936,"title":"JEX上线周LTC期权0730公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031192591-JEX%E4%B8%8A%E7%BA%BF%E5%91%A8LTC%E6%9C%9F%E6%9D%830730%E5%85%AC%E5%91%8A","newstime":"07-23 03:46"},{"id":31937,"title":"JEX上线月LTC期权0827公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360030855112-JEX%E4%B8%8A%E7%BA%BF%E6%9C%88LTC%E6%9C%9F%E6%9D%830827%E5%85%AC%E5%91%8A","newstime":"07-23 03:46"},{"id":31935,"title":"广告方\u201c强制放行\u201d功能介绍","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000341862-%E5%B9%BF%E5%91%8A%E6%96%B9-%E5%BC%BA%E5%88%B6%E6%94%BE%E8%A1%8C-%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D","newstime":"07-23 03:12"}],"first_page_url":"https://api.guessfun.com/api/center/news_list/3?page=1","from":61,"last_page":80,"last_page_url":"https://api.guessfun.com/api/center/news_list/3?page=80","next_page_url":"https://api.guessfun.com/api/center/news_list/3?page=5","path":"https://api.guessfun.com/api/center/news_list/3","per_page":20,"prev_page_url":"https://api.guessfun.com/api/center/news_list/3?page=3","to":80,"total":1599}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * current_page : 4
         * data : [{"id":32122,"title":"MXC交易所CARM第一期净买入大赛奖励发放公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030887452-MXC%E4%BA%A4%E6%98%93%E6%89%80CARM%E7%AC%AC%E4%B8%80%E6%9C%9F%E5%87%80%E4%B9%B0%E5%85%A5%E5%A4%A7%E8%B5%9B%E5%A5%96%E5%8A%B1%E5%8F%91%E6%94%BE%E5%85%AC%E5%91%8A","newstime":"07-24 06:34"},{"id":32117,"title":"关于火币全球站恢复ZEC充提业务的公告","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000343482-%E5%85%B3%E4%BA%8E%E7%81%AB%E5%B8%81%E5%85%A8%E7%90%83%E7%AB%99%E6%81%A2%E5%A4%8DZEC%E5%85%85%E6%8F%90%E4%B8%9A%E5%8A%A1%E7%9A%84%E5%85%AC%E5%91%8A","newstime":"07-24 06:32"},{"id":32118,"title":"6万美元BTC空投   零门槛参与BTC合约大师赛","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342802-6%E4%B8%87%E7%BE%8E%E5%85%83BTC%E7%A9%BA%E6%8A%95-%E9%9B%B6%E9%97%A8%E6%A7%9B%E5%8F%82%E4%B8%8EBTC%E5%90%88%E7%BA%A6%E5%A4%A7%E5%B8%88%E8%B5%9B","newstime":"07-24 06:09"},{"id":32102,"title":"MXC交易所关于\u201c抹茶形象大使\u201d报名延时以及规则说明公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360031226271-MXC%E4%BA%A4%E6%98%93%E6%89%80%E5%85%B3%E4%BA%8E-%E6%8A%B9%E8%8C%B6%E5%BD%A2%E8%B1%A1%E5%A4%A7%E4%BD%BF-%E6%8A%A5%E5%90%8D%E5%BB%B6%E6%97%B6%E4%BB%A5%E5%8F%8A%E8%A7%84%E5%88%99%E8%AF%B4%E6%98%8E%E5%85%AC%E5%91%8A","newstime":"07-24 04:08"},{"id":32095,"title":"JEX上线周BTC期权0731公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360030884612-JEX%E4%B8%8A%E7%BA%BF%E5%91%A8BTC%E6%9C%9F%E6%9D%830731%E5%85%AC%E5%91%8A","newstime":"07-24 03:13"},{"id":32096,"title":"JEX上线月BTC期权0828公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031225111-JEX%E4%B8%8A%E7%BA%BF%E6%9C%88BTC%E6%9C%9F%E6%9D%830828%E5%85%AC%E5%91%8A","newstime":"07-24 03:13"},{"id":32090,"title":"MXC大客户第一期招募公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360031224271-MXC%E5%A4%A7%E5%AE%A2%E6%88%B7%E7%AC%AC%E4%B8%80%E6%9C%9F%E6%8B%9B%E5%8B%9F%E5%85%AC%E5%91%8A","newstime":"07-24 02:53"},{"id":32123,"title":"系统维护公告","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030758154-%E7%B3%BB%E7%BB%9F%E7%BB%B4%E6%8A%A4%E5%85%AC%E5%91%8A","newstime":"07-24 01:54"},{"id":32007,"title":"EtherFlyer第三季度下架币对清单（一）","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030463613-EtherFlyer%E7%AC%AC%E4%B8%89%E5%AD%A3%E5%BA%A6%E4%B8%8B%E6%9E%B6%E5%B8%81%E5%AF%B9%E6%B8%85%E5%8D%95-%E4%B8%80-","newstime":"07-23 11:17"},{"id":32004,"title":"什么是ZIL换链","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342862-%E4%BB%80%E4%B9%88%E6%98%AFZIL%E6%8D%A2%E9%93%BE","newstime":"07-23 11:12"},{"id":31993,"title":"MXC交易所关于提币额度调整的公告","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030860272-MXC%E4%BA%A4%E6%98%93%E6%89%80%E5%85%B3%E4%BA%8E%E6%8F%90%E5%B8%81%E9%A2%9D%E5%BA%A6%E8%B0%83%E6%95%B4%E7%9A%84%E5%85%AC%E5%91%8A","newstime":"07-23 10:30"},{"id":31992,"title":"交易稳定币，限时30天享0手续费！","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000342762-%E4%BA%A4%E6%98%93%E7%A8%B3%E5%AE%9A%E5%B8%81-%E9%99%90%E6%97%B630%E5%A4%A9%E4%BA%AB0%E6%89%8B%E7%BB%AD%E8%B4%B9-","newstime":"07-23 10:24"},{"id":32107,"title":"BTC合约大师赛：玩BTC合约，送特斯拉Model 3！","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000339561-BTC%E5%90%88%E7%BA%A6%E5%A4%A7%E5%B8%88%E8%B5%9B-%E7%8E%A9BTC%E5%90%88%E7%BA%A6-%E9%80%81%E7%89%B9%E6%96%AF%E6%8B%89Model-3-","newstime":"07-23 09:48"},{"id":32014,"title":"MXC Labs 即将上线ZV Chain项目，并将于7月25日开启第一轮认购","img":null,"source":"MXC","source_url":"https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030859832-MXC-Labs-%E5%8D%B3%E5%B0%86%E4%B8%8A%E7%BA%BFZV-Chain%E9%A1%B9%E7%9B%AE-%E5%B9%B6%E5%B0%86%E4%BA%8E7%E6%9C%8825%E6%97%A5%E5%BC%80%E5%90%AF%E7%AC%AC%E4%B8%80%E8%BD%AE%E8%AE%A4%E8%B4%AD","newstime":"07-23 09:38"},{"id":31962,"title":"JEX月LTC期权0723行权公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031195811-JEX%E6%9C%88LTC%E6%9C%9F%E6%9D%830723%E8%A1%8C%E6%9D%83%E5%85%AC%E5%91%8A","newstime":"07-23 08:02"},{"id":31964,"title":"JEX周LTC期权0723行权公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031195791-JEX%E5%91%A8LTC%E6%9C%9F%E6%9D%830723%E8%A1%8C%E6%9D%83%E5%85%AC%E5%91%8A","newstime":"07-23 08:01"},{"id":32156,"title":"新币上线 - ADZB","img":null,"source":"以飞","source_url":"https://etherflyer.zendesk.com//hc/zh-cn/articles/360030003754-%E6%96%B0%E5%B8%81%E4%B8%8A%E7%BA%BF-ADZB","newstime":"07-23 07:37"},{"id":31936,"title":"JEX上线周LTC期权0730公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360031192591-JEX%E4%B8%8A%E7%BA%BF%E5%91%A8LTC%E6%9C%9F%E6%9D%830730%E5%85%AC%E5%91%8A","newstime":"07-23 03:46"},{"id":31937,"title":"JEX上线月LTC期权0827公告","img":null,"source":"JEX","source_url":"https://jexhelp.zendesk.com//hc/zh-cn/articles/360030855112-JEX%E4%B8%8A%E7%BA%BF%E6%9C%88LTC%E6%9C%9F%E6%9D%830827%E5%85%AC%E5%91%8A","newstime":"07-23 03:46"},{"id":31935,"title":"广告方\u201c强制放行\u201d功能介绍","img":null,"source":"火币","source_url":"https://huobiglobal.zendesk.com//hc/zh-cn/articles/360000341862-%E5%B9%BF%E5%91%8A%E6%96%B9-%E5%BC%BA%E5%88%B6%E6%94%BE%E8%A1%8C-%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D","newstime":"07-23 03:12"}]
         * first_page_url : https://api.guessfun.com/api/center/news_list/3?page=1
         * from : 61
         * last_page : 80
         * last_page_url : https://api.guessfun.com/api/center/news_list/3?page=80
         * next_page_url : https://api.guessfun.com/api/center/news_list/3?page=5
         * path : https://api.guessfun.com/api/center/news_list/3
         * per_page : 20
         * prev_page_url : https://api.guessfun.com/api/center/news_list/3?page=3
         * to : 80
         * total : 1599
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private int per_page;
        private String prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 32122
             * title : MXC交易所CARM第一期净买入大赛奖励发放公告
             * img : null
             * source : MXC
             * source_url : https://mxc-exchange.zendesk.com//hc/zh-cn/articles/360030887452-MXC%E4%BA%A4%E6%98%93%E6%89%80CARM%E7%AC%AC%E4%B8%80%E6%9C%9F%E5%87%80%E4%B9%B0%E5%85%A5%E5%A4%A7%E8%B5%9B%E5%A5%96%E5%8A%B1%E5%8F%91%E6%94%BE%E5%85%AC%E5%91%8A
             * newstime : 07-24 06:34
             */

            private int id;
            private String title;
            private Object img;
            private String source;
            private String source_url;
            private String newstime;

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

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getSource_url() {
                return source_url;
            }

            public void setSource_url(String source_url) {
                this.source_url = source_url;
            }

            public String getNewstime() {
                return newstime;
            }

            public void setNewstime(String newstime) {
                this.newstime = newstime;
            }
        }
    }
}
