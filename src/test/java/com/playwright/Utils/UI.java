package com.playwright.Utils;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UI {
    private Page page;

    public UI(Page page) {
        this.page = page;
    }

    public void highlightElementByGreen(ElementHandle element) {
        scrollIntoView(element);
        page.evaluate("element => element.style.border = '5px solid green'", element);
    }

    public void highlightElementByGreen(Locator locator) {
        scrollIntoView(locator);
        locator.evaluateAll("elements => elements.forEach(element => element.style.border = '5px solid green')");
    }

    public void highlightElementByGreen(String selector) {
        scrollIntoView(selector);
        page.evaluate("selector => { const elements = document.querySelectorAll(selector); elements.forEach(element => element.style.border = '5px solid green'); }", selector);
    }

    public void highlightElementByRed(ElementHandle element) {
        scrollIntoView(element);
        page.evaluate("element => element.style.border = '5px solid red'", element);
    }

    public void highlightElementByRed(Locator locator) {
        scrollIntoView(locator);
        locator.evaluateAll("elements => elements.forEach(element => element.style.border = '5px solid red')");
    }

    public void highlightElementByRed(String selector) {
        scrollIntoView(selector);
        page.evaluate("selector => { const elements = document.querySelectorAll(selector); elements.forEach(element => element.style.border = '5px solid red'); }", selector);
    }

    private void scrollIntoView(ElementHandle element) {
        element.scrollIntoViewIfNeeded();
    }

    private void scrollIntoView(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    private void scrollIntoView(String selector) {
        page.evaluate("selector => { document.querySelector(selector).scrollIntoViewIfNeeded(); }", selector);
    }
}
