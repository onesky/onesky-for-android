package app.onesky.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalizationTask extends DefaultTask {

    private final String STRING_FILENAME = "strings.xml";
    private final String LOCALIZATION_EFFECT_TYPE = "display-language";

    private String projectPath = getProject().getProjectDir().getPath();

    @TaskAction
    public void taskAction() throws Exception {
        if (OneSkyPlugin.oneSkyPluginExtension.appId.isEmpty()) {
            throw new Exception("App Id must not be empty");
        }
        if (OneSkyPlugin.oneSkyPluginExtension.apiKey.isEmpty()) {
            throw new Exception("API key must not be empty");
        }

        ApiConsumer apiConsumer = new ApiConsumer(OneSkyPlugin.oneSkyPluginExtension.appId, OneSkyPlugin.oneSkyPluginExtension.apiKey);
        String appConfigContent = apiConsumer.getAppConfigContent();
        checkResponse(appConfigContent);
        writeStringFiles(appConfigContent);
    }

    private void writeStringFiles(String appConfigContent) throws Exception {

        JsonParser jsonParser = new JsonParser();
        JsonElement rootConfigElement = jsonParser.parse(appConfigContent);
        JsonArray selectors = rootConfigElement.getAsJsonObject().getAsJsonObject("app").getAsJsonArray("selectors");

        for (JsonElement selector : selectors) {
            if (selector.getAsJsonObject().get("type").getAsString().equals(LOCALIZATION_EFFECT_TYPE)) {

                String defaultLanguageId = selector.getAsJsonObject().get("defaultValue").getAsString();
                JsonArray locales = selector.getAsJsonObject().getAsJsonArray("locales");
                for (JsonElement locale : locales) {
                    if (locale.isJsonNull()) continue;
                    String languageId = locale.getAsJsonObject().getAsJsonPrimitive("id").getAsString();
                    String platformLocale = locale.getAsJsonObject().getAsJsonPrimitive("platformLocale").getAsString();

                    try {
                        ApiConsumer apiConsumer = new ApiConsumer(OneSkyPlugin.oneSkyPluginExtension.appId, OneSkyPlugin.oneSkyPluginExtension.apiKey);
                        String stringFileContent = apiConsumer.getStringFileContent(languageId);

                        if (languageId.equals(defaultLanguageId)) {
                            String defaultFilePath = this.projectPath + File.separator + OneSkyPlugin.oneSkyPluginExtension.resourceDirectoryRelativePath + File.separator + "values";
                            writeStringFile(stringFileContent, defaultFilePath);
                        }

                        String filePath = this.projectPath + File.separator + OneSkyPlugin.oneSkyPluginExtension.resourceDirectoryRelativePath + File.separator + "values-" + platformLocale;
                        writeStringFile(stringFileContent, filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void writeStringFile(String stringFileContent, String filePath) throws Exception{
        File stringFileDirectory = new File(filePath);
        stringFileDirectory.mkdirs();

        File file = new File(stringFileDirectory, STRING_FILENAME);
        System.out.println(stringFileDirectory.getPath());

        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        fileOutputStream.write(stringFileContent.getBytes());
        fileOutputStream.close();
    }

    private void checkResponse(String appConfigContent) throws Exception {

        JsonParser jsonParser = new JsonParser();
        JsonElement rootConfigElement = jsonParser.parse(appConfigContent);
        JsonArray errors = rootConfigElement.getAsJsonObject().getAsJsonArray("errors");
        if (errors != null) {
            for (JsonElement error : errors) {
                throw new Exception(error.getAsJsonObject().get("message").getAsString());
            }
        }
    }
}
