package com.reneroboter.linelinker;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import io.perfmark.Link;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GutterPopupAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();

        LinkGenerator linkGenerator = new LinkGenerator();
        String generatedLink = "";
        generatedLink = linkGenerator.generateLink(event);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(generatedLink);
        clipboard.setContents(selection, null);

        String title = event.getPresentation().getDescription();
        Messages.showMessageDialog(
                currentProject,
                generatedLink,
                title,
                Messages.getInformationIcon());
    }
}
