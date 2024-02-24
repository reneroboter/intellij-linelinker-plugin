package com.reneroboter.linelinker;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.ex.EditorGutterComponentEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcsUtil.VcsFileUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GitHubStrategy implements GitServiceStrategy {

    @Override
    public String buildLink(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();
        VirtualFile file = event.getDataContext().getData(com.intellij.openapi.actionSystem.PlatformDataKeys.VIRTUAL_FILE);

        GitRepositoryManager repositoryManager = GitRepositoryManager.getInstance(currentProject);
        ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(currentProject);
        VirtualFile vcsRoot = vcsManager.getVcsRootFor(file);

        if (vcsRoot == null) {
            // todo throw error
            return "";
        }

        GitRepository repository = repositoryManager.getRepositoryForFileQuick(vcsRoot);

        if (repository == null) {
            // todo throw error
            return "";
        }
        Optional<GitRemote> originRepository = repository.getRemotes().stream()
                .filter(remote -> "origin".equals(remote.getName()))
                .findFirst();

        String relativePath = VcsFileUtil.relativePath(vcsRoot,file);

        int gutterLineNumber = event.getDataContext().getData(EditorGutterComponentEx.LOGICAL_LINE_AT_CURSOR) + 1;

        String baseUrl = "https://" + originRepository.get().getFirstUrl()
                .replace("git@", "")
                .replace(".git", "")
                .replace(":", "/")
                .concat("/")
                .concat(repository.getCurrentBranch().toString().replace("refs/heads", "blob"))
                .concat("/")
                .concat(relativePath)
                .concat("#L" + gutterLineNumber);

        // https://github.com/reneroboter/intellij-linelinker-plugin/blob/main/gradlew#L1
        return baseUrl;
    }
}
