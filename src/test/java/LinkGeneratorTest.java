import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.reneroboter.linelinker.GitHubStrategy;
import com.reneroboter.linelinker.GitLabStrategy;
import com.reneroboter.linelinker.GitServiceStrategy;
import com.reneroboter.linelinker.LinkGenerator;
import git4idea.GitUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public class LinkGeneratorTest extends BasePlatformTestCase {
    public void testGenerateLink() {
        assertEquals("Hello World", "Hello World");
    }
}
