package com.reneroboter.linelinker;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import git4idea.GitUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public class LinkGenerator {
    public GitServiceStrategy strategy;

    public void setStrategy(GitServiceStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy(@NotNull AnActionEvent event) {
        return this.strategy.buildLink(event);
    }
    private Optional<GitRemote> findOriginRepositoryByProject(Project project) {
        ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(project);
        GitRepository gitRepository = GitUtil.getRepositoryManager(project).getRepositoryForRootQuick(vcsManager.getVcsRootFor(project.getBaseDir()));

        return gitRepository.getRemotes().stream()
                .filter(remote -> "origin".equals(remote.getName()))
                .findFirst();
    }

    public String generateLink(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Optional<GitRemote> originRepository = this.findOriginRepositoryByProject(project);

        if (originRepository.get().getFirstUrl().contains("gitlab")) {
            this.setStrategy(new GitLabStrategy());
        }

        if (originRepository.get().getFirstUrl().contains("github")) {
            this.setStrategy(new GitHubStrategy());
        }

        return this.executeStrategy(event);
    }

}
