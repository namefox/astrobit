package com.astrobit.hub.pages;

import com.astrobit.hub.Page;

import javax.swing.*;
import java.awt.*;

public class ExtensionsTabbedPane extends Page {

    private Page page;

    public ExtensionsTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        page = new ExtensionsPage();
        page.onShow();

        tabbedPane.addTab("Installed", page);
        tabbedPane.addTab("Marketplace", new MarketplacePage());

        tabbedPane.addChangeListener(e -> {
            page.reset();
            page = (Page) tabbedPane.getSelectedComponent();
            page.onShow();
        });

        setLayout(new BorderLayout());
        add(tabbedPane);
    }

    @Override
    public void onShow() {
        if (page != null) page.onShow();
    }

    @Override
    public void reset() {
        if (page != null) page.reset();
    }
}