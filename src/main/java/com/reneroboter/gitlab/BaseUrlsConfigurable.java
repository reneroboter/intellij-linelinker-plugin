package com.reneroboter.gitlab;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BaseUrlsConfigurable implements Configurable {

    private JPanel mainPanel;
    private JTextField textField;
    private String storedValue;

    @Nullable
    @Override
    public JComponent createComponent() {
        mainPanel = new JPanel();
        textField = new JTextField(20);
        mainPanel.add(textField);
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return !textField.getText().equals(storedValue);
    }

    @Override
    public void apply() {
        storedValue = textField.getText();
    }

    @Override
    public void reset() {
        textField.setText(storedValue);
    }

    @Override
    public String getDisplayName() {
        return "Your GitLab project URL e.g: https://gitlab.real-digital.de/code/seller-deals-backend/app/";
    }
}