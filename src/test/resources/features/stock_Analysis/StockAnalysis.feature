@STOCK_ANALYSIS
Feature: Stock Analysis

  Scenario: Stock Analysis of given stocks
    Given we read all the company name from excel "StockName" and sheet name "Set1"
    When landed on the google homepage
    Then capture all the stock statistics

  Scenario: Stock Analysis of given US stocks
    Given we read all the company name from excel "StockName" and sheet name "Set2"
    When landed on the google homepage
    Then capture all the stock statistics
