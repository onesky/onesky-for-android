package app.onesky.android;

import org.junit.Test;

public class ApiConsumerTest {

    private String appId = "94a3ecf8-0134-4c3b-a3a8-b6c90f14d57a";
    private String apiKey = "1234";

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
