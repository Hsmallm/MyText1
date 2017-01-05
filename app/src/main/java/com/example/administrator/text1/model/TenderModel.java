package com.example.administrator.text1.model;

import java.util.List;

/**
 * Created by hzhm on 2016/11/9.
 */

public class TenderModel {

    public long sysDate;
    /**
     * 累计成交笔数
     */
    public String dealNumber;


    public PageInfoBean pageInfo;
    /**
     * 累计成交金额
     */
    public String dealMoney;

    public int canInvestLoanCount;


    public List<LoanVoListBean> loanVoList;

    public static class PageInfoBean {
        public int pageIndex;
        public int pageSize;
        public int total;
    }

    public static class LoanVoListBean {
        /**
         * 加息年利率
         */
        public double addRate;
        /**
         * 加息年利率
         */
        public String addRateStr;
        /**
         * 标的可投份额
         */
        public int availShare;
        /**
         * 年化收益
         */
        public double baseRate;
        /**
         * 编号
         */
        public String id;
        /**
         * 投资进度
         */
        public String investProgress;
        /**
         * 标的金额
         */
        public double loanMoney;
        /**
         * 标的名称
         */
        public String loanTitle;
        /**
         * 标的编号
         */
        public String loanTitleNo;
        /**
         * 投标开始时间
         */
        public long startBiddingDate;
        /**
         * 标的状态
         */
        public String status;
        /**
         * 标的状态
         */
        public String statusStr;
        /**
         * 标的期限
         */
        public int term;
    }
}
