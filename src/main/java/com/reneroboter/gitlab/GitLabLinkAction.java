package com.reneroboter.gitlab;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import java.text.MessageFormat;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static org.apache.tools.ant.types.resources.MultiRootFileSet.SetType.file;

public class GitLabLinkAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();

        ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(currentProject);

        // Get all VCS roots associated with the project
        VcsRoot[] vcsRoots = vcsManager.getAllVcsRoots();

        if (vcsRoots.length > 0) {
            // Print repository information for each VCS root
            for (VcsRoot vcsRoot : vcsRoots) {
                System.out.println("VCS Root: " + vcsRoot.getPath());
                System.out.println("VCS Name: " + vcsRoot.getVcs().getName());
                System.out.println(); // Empty line for clarity
            }
        } else {
            System.out.println("No VCS roots found for the project.");
        }
        VirtualFile file = event.getDataContext().getData(com.intellij.openapi.actionSystem.PlatformDataKeys.VIRTUAL_FILE);

        VirtualFile baseDir = currentProject.getBaseDir();


        // Get the relative path of the file
        String relativePath = baseDir.toNioPath().relativize(file.toNioPath()).toString();
        System.out.println("Relative path of the current file: " + relativePath);

        StringBuilder message = new StringBuilder("Selected: \n");

        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Caret caret = editor.getCaretModel().getCurrentCaret();
        int lineNumber = caret.getVisualPosition().getLine();
        String baseUrl = "https://gitlab.real-digital.de/code/seller-deals-backend/app/{0}{1}?ref_type=heads#L{2}";
        String branch = "-/blob/main/";
        String copyToClipboard = MessageFormat.format(baseUrl, branch, relativePath, lineNumber +1);

        // baseUrl += lineNumber + 1; // start with zero
        message.append("LineNumber: ").append(lineNumber);


        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        StringSelection selection = new StringSelection(baseUrl);

        clipboard.setContents(selection, null);

        String title = event.getPresentation().getDescription();
        Messages.showMessageDialog(
                currentProject,
                message.toString(),
                title,
                Messages.getInformationIcon());
    }
}
