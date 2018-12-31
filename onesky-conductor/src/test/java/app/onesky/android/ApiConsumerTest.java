package app.onesky.android;

import org.junit.Test;

public class ApiConsumerTest {

    private String appId = "aa339d82-7365-406b-af92-f3d1f4c77651";
    private String apiKey = "06f7f71f85040b3ca951ecfb2e53b340";

    @Test
    public void getAppConfig() throws Exception {
        ApiConsumer apiConsumer = new ApiConsumer(this.appId, this.apiKey);
        apiConsumer.getAppConfigContent();
    }

    @Test
    public void getXmlString() throws Exception {
        ApiConsumer apiConsumer = new ApiConsumer(this.appId, this.apiKey);
        apiConsumer.getStringFileContent("en_US");
        apiConsumer.getStringFileContent("ru_RU");
        apiConsumer.getStringFileContent("ar_AE");
        apiConsumer.getStringFileContent("zh_Hans_CN");
        apiConsumer.getStringFileContent("zh_Hant_TW");
    }
}
