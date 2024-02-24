package com.reneroboter.linelinker;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public interface GitServiceStrategy {
    String buildLink(@NotNull AnActionEvent event);
}
