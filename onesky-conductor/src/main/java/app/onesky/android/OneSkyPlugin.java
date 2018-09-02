package app.onesky.android;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class OneSkyPlugin implements Plugin<Project> {

    private final String LOCALIZATION_TASK = "localize";
    public static OneSkyPluginExtension oneSkyPluginExtension = new OneSkyPluginExtension();

    @Override
    public void apply(Project project) {
        oneSkyPluginExtension = project.getExtensions().create("OneSky", OneSkyPluginExtension.class);
        project.getTasks().create(LOCALIZATION_TASK, LocalizationTask.class);
    }
}

